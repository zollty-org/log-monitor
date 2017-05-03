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
package org.zollty.mlf4j.monitor.service.mlf4j.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zollty.framework.core.annotation.Component;
import org.zollty.framework.util.MvcUtils;
import org.zollty.log.LogFactory.LogManager;
import org.zollty.log.LogUtils;
import org.zollty.log.LoggerExeInfo;
import org.zollty.log.LoggerManager;
import org.zollty.log.LoggerManager.LoggerExeCountComparator;
import org.zollty.log.LoggerWrapper;
import org.zollty.mlf4j.base.exception.MlfI18nException;
import org.zollty.mlf4j.monitor.service.mlf4j.IMlf4jConfigService;
import org.zollty.util.NestedRuntimeException;
import org.zollty.util.json.SimpleJSON;

/**
 * @author zollty
 * @since 2014-6-4
 */
@Component("mlf4jConfigService")
public class Mlf4jConfigServiceImpl implements IMlf4jConfigService {
    
    @Override
    public void refreshMlf4jConfig(String configStr) throws MlfI18nException {
        InputStream in = MvcUtils.IOUtil.getInputStreamFromString(configStr, "UTF-8");
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            throw new NestedRuntimeException(e);
        }
        Map<String, String> pmap = LogUtils.covertProperties2Map(props);
        LogManager.refreshZolltyLogConfig(pmap);
    }

    @Override
    public String getMlf4jConfig() throws MlfI18nException {
        
        return LogManager.getConfigContent();
    }

    @Override
    public String showAllLoggers() throws MlfI18nException {
        List<SimpleJSON> loggerList = new ArrayList<SimpleJSON>();
        synchronized (LogManager.cacheLoggerMap) {
            for(Map.Entry<String, LoggerWrapper> en: LogManager.cacheLoggerMap.entrySet()){
                loggerList.add(SimpleJSON.getInstance().addItem(en.getKey(), ""));
            }
        }
        return SimpleJSON.toSimpleJSONArray(loggerList).toString();
    }

    @Override
    public boolean removeLoggerFromCache(String loggerName) {
        return LogManager.cacheLoggerMap.remove(loggerName)==null?false:true;
    }

    @Override
    public String showLoggerExeInfo() {
        Collection<LoggerExeInfo> loges = LoggerManager.getLoggerExeMap().values();
        LoggerExeInfo[] logArray = new LoggerExeInfo[loges.size()];
        List<LoggerExeInfo> logList = Arrays.asList(loges.toArray(logArray));
        Collections.sort(logList, new LoggerExeCountComparator()); // 排序
        
        List<SimpleJSON> loggerList = new ArrayList<SimpleJSON>();
        for(LoggerExeInfo value: logList){
            loggerList.add(SimpleJSON.getInstance().addItem("name", value.getName())
                    .addItem("trace", value.getTraceCount())
                    .addItem("debug", value.getDebugCount())
                    .addItem("info", value.getInfoCount())
                    .addItem("warn", value.getWarnCount())
                    .addItem("error", value.getErrorCount())
                    );
        }
        
        return SimpleJSON.toSimpleJSONArray(loggerList).toString();
    }
    

}
