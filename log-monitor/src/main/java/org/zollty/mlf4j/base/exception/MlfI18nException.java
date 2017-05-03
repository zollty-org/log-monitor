/* 
 * == Mlf4j (Monitoring Logging Facade for Java) ==
 * ============为监控而生的通用日志工具库===========
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.zollty.mlf4j.base.exception;

import java.util.Locale;

import org.zollty.framework.util.MvcUtils;
import org.zollty.mlf4j.base.util.LocaleUtils;
import org.zollty.mlf4j.base.util.PropertiesTools;
import org.zollty.util.BasicCheckedException;

/**
 * @author zollty
 * @since 2014-5-7
 */
public class MlfI18nException extends BasicCheckedException {

    private static final long serialVersionUID = -856748734106714570L;
    private final String errorCode;
    private final String[] params;
    
    /** 
     * 构造函数
     * @param errorCode 错误代码
     **/
    public MlfI18nException(String errorCode){
        super();
        this.errorCode = errorCode;
        this.params = null;
    }
    
    /** 
     * 构造函数 
     * @param errorCode 错误代码
     * @param args 占位符参数--[ 变长参数，用于替换message字符串里面的占位符"{}" ]
     */
    public MlfI18nException(String errorCode, String... args){
        super();
        this.errorCode = errorCode;
        this.params = args;
    }

    @Override
    public String getMessage(){
        return getMessage(Locale.US);
    }
    
    public String getMessage(Locale localLocale){
        return  MvcUtils.StringUtil.replaceParams( PropertiesTools.getMsg(errorCode, localLocale) , params);
    }
    
    public String getMessage(String localLocale){
        return  MvcUtils.StringUtil.replaceParams( PropertiesTools.getMsg(errorCode, LocaleUtils.toLocale(localLocale)) , params);
    }
    
    /**
     * 获取组装好的完整信息：错误类+错误代码+错误信息，用于调试或View层展示
     * @return
     */
    @Override
    public String toString(){
        return getMessage(Locale.US);
    }
    
    public String getErrorCode() {
        return errorCode;
    }

    public String[] getParams() {
        return params;
    }

}
