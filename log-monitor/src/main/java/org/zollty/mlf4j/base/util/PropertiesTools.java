/* 
 * == Mlf4j (Monitoring Logging Facade for Java) ==
 * ============为监控而生的通用日志工具库===========
 * Version 1.0
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.zollty.mlf4j.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.zollty.log.LogFactory;
import org.zollty.log.Logger;
import org.zollty.util.BasicRuntimeException;
import org.zollty.util.NestedRuntimeException;

/**
 * @author zollty
 * @since 2014-1-21
 */
public class PropertiesTools {

    private static final Logger LOG = LogFactory.getLogger();
    
    private static final String PROP_FILE_NAME = "mlf4j_Messages";
    private static final String MSG_FILE_SUFFIX = "Messages";
    private static Map<String, ResourceBundle> msgBundleMap = new HashMap<String, ResourceBundle>();

    /**
     * 获取国际化messages
     */
    public static String getMsg(String module, final String key, Locale localLocale) {
        String fileName = module + MSG_FILE_SUFFIX;
        ResourceBundle msgBundle = msgBundleMap.get(fileName + localLocale.toString());
        if (msgBundle == null) {
            try {
                msgBundle = ResourceBundle.getBundle(fileName, localLocale);
                msgBundleMap.put(fileName + localLocale.toString(), msgBundle);
            } catch (Exception e) {
                LOG.error(e);
                return null;
            }
        }
        try {
            return msgBundle.getString(key);
        } catch (java.util.MissingResourceException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * 获取国际化messages
     */
    public static String getMsg(String key, Locale localLocale) {
        ResourceBundle msgBundle = msgBundleMap.get(localLocale.toString());
        if (msgBundle == null) {
            try {
                msgBundle = ResourceBundle.getBundle(PROP_FILE_NAME, localLocale);
                msgBundleMap.put(localLocale.toString(), msgBundle);
            } catch (Exception e) {
                LOG.error(e);
                return null;
            }
        }
        try {
            return msgBundle.getString(key);
        } catch (java.util.MissingResourceException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * 获取配置文件输入流 （使用Java自带的ClassLoader#getResourceAsStream()方法，带缓存）
     * 
     * @author zollty
     */
    public static InputStream getResourceInputStream(String resourcePath) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (in == null){
            return PropertiesTools.class.getClassLoader().getResourceAsStream(resourcePath);
        }
        return in;
    }
    
    
    /**
     * 从properties文件中获得字符串形式的内容 [转化UNICODE编码为汉字显示，以免被filter拦截(\u7b2c)]
     * 
     * @param propFileName properties文件名
     * @return properties文件内容
     */
    public static String getResoureFileContent(String propFileName) {
        InputStream tInputStream = PropertiesTools.getResourceInputStream(propFileName);
        if (tInputStream == null) {
            throw new BasicRuntimeException("can't get io stream of file [{}]", propFileName);
        }
        try {
            BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
            StringBuffer tStringBuffer = new StringBuffer();
            String sTempOneLine = new String("");
            while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                tStringBuffer.append(sTempOneLine).append("\r\n");
            }
            return ascii2Native(tStringBuffer.toString());
        }
        catch (Exception ex) {
            throw new NestedRuntimeException(ex);
        }
    }

    private static String ascii2Native(String str) {
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf("\\u");
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf("\\u", begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    /**
     * 转化String为char
     * 
     * @param str
     */
    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!"\\u".equals(str.substring(0, 2))) {
            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

}
