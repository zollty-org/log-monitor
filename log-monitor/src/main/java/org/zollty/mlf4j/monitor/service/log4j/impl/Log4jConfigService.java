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
package org.zollty.mlf4j.monitor.service.log4j.impl;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.Log4jEntityResolver;
import org.apache.log4j.xml.SAXErrorHandler;
import org.w3c.dom.Document;
import org.zollty.framework.core.annotation.Component;
import org.zollty.framework.util.MvcUtils;
import org.zollty.log.Log4jLogger;
import org.zollty.log.LogFactory;
import org.zollty.mlf4j.base.exception.MlfI18nException;
import org.zollty.mlf4j.base.util.PropertiesTools;
import org.zollty.mlf4j.monitor.service.log4j.ILog4jConfigService;
import org.zollty.util.json.SimpleJSON;

/**
 * @author zollty
 * @since 2014-5-8
 */
@Component("log4jConfigService")
public class Log4jConfigService implements ILog4jConfigService {
    
    private static org.zollty.log.Logger log = LogFactory.getLogger();

    @SuppressWarnings("rawtypes")
    @Override
    public void modifyAppenderLevel(String name, String level) throws MlfI18nException {

        // 当前操作是针对 Appender的
        AppenderSkeleton app = (AppenderSkeleton) LogManager.getRootLogger().getAppender(name);
        if (app == null || !app.getName().equals(name)) {
            Enumeration loggers = LogManager.getLoggerRepository().getCurrentLoggers();
            if (loggers != null) {
                while (loggers.hasMoreElements()) {
                    Logger lo = (Logger) loggers.nextElement();
                    app = (AppenderSkeleton) lo.getAppender(name);
                    if(app!=null && app.getName().equals(name)){
                        break;
                    }
                }
            }
            if (app == null || !app.getName().equals(name)) {
                return;
            }
        }
        if ("OFF".equals(level)) {
            // 不允许任何级别日志输出
            app.setThreshold(Level.OFF);
        }
        else if ("FATAL".equals(level)) {
            // 允许FATAL 和比FATAL级别大的日志输出
            app.setThreshold(Level.FATAL);
        }
        else if ("ERROR".equals(level)) {
            // 允许Error 和比error级别大的日志输出
            app.setThreshold(Level.ERROR);
        }
        else if ("WARN".equals(level)) {
            // 允许WARN 和比INFO级别大的日志输出
            app.setThreshold(Level.WARN);
        }
        else if ("INFO".equals(level)) {
            // 允许INFO 和比INFO级别大的日志输出
            app.setThreshold(Level.INFO);
        }
        else if ("DEBUG".equals(level)) {
            // 允许DEBUG 和比DEBUG级别大的日志输出
            app.setThreshold(Level.DEBUG);
        }
        else if ("TRACE".equals(level)) {
            // 允许TRACE 和比TRACE级别大的日志输出
            app.setThreshold(Level.TRACE);
        }
        else if ("ALL".equals(level)) {
            // 允许所有级别大的日志输出
            app.setThreshold(Level.ALL);
        }
        else if ("NULL".equals(level)) {
            // 允许所有级别大的日志输出
            app.setThreshold(null);
        }

    }

    @Override
    public void modifyLoggerLevel(String name, String level) throws MlfI18nException {
        // 得到当前的日志对象
        Logger log = LogManager.exists(name);
        doModifyLoggerLevel(log, level);
    }

    @Override
    public void modifyRootLoggerLevel(String level) throws MlfI18nException {
        // 得到当前的日志对象
        Logger log = Logger.getRootLogger();
        if ("NULL".equals(level)) {
            return; // rootlogger不能设置为NULL
        }
        doModifyLoggerLevel(log, level);
    }

    private void doModifyLoggerLevel(Logger log, String level) throws MlfI18nException {
        // ERROR,INFO,DEBUG,TRACE,OFF
        if (log != null && level != null && !level.trim().equals("")) {
            if ("OFF".equals(level)) {
                // 不允许任何级别日志输出
                log.setLevel(Level.OFF);
            }
            else if ("FATAL".equals(level)) {
                // 允许FATAL 和比FATAL级别大的日志输出
                log.setLevel(Level.FATAL);
            }
            else if ("ERROR".equals(level)) {
                // 允许Error 和比error级别大的日志输出
                log.setLevel(Level.ERROR);
            }
            else if ("WARN".equals(level)) {
                // 允许WARN 和比INFO级别大的日志输出
                log.setLevel(Level.WARN);
            }
            else if ("INFO".equals(level)) {
                // 允许INFO 和比INFO级别大的日志输出
                log.setLevel(Level.INFO);
            }
            else if ("DEBUG".equals(level)) {
                // 允许DEBUG 和比DEBUG级别大的日志输出
                log.setLevel(Level.DEBUG);
            }
            else if ("TRACE".equals(level)) {
                // 允许TRACE 和比TRACE级别大的日志输出
                log.setLevel(Level.TRACE);
            }
            else if ("OFF".equals(level)) {
                // 不允许任何级别日志输出
                log.setLevel(Level.OFF);
            }
            else if ("ALL".equals(level)) {
                // 允许所有级别大的日志输出
                log.setLevel(Level.ALL);
            }
            else if ("NULL".equals(level)) {
                // 允许所有级别大的日志输出
                log.setLevel(null);
            }
        }
    }

    /**
     * 配置页面 最多显示的日志记录器的信息 条数 最少显示1 最大显示1000
     */
    private static int levelCount = 201;

    @SuppressWarnings("rawtypes")
    @Override
    public String showConfigDetails(String loggerName) throws MlfI18nException {
        List<SimpleJSON> logList = new ArrayList<SimpleJSON>();
        int count = 0;
        int flag = 0;
        Enumeration enmu = LogManager.getLoggerRepository().getCurrentLoggers();
        while (enmu.hasMoreElements()) {
            if (count == levelCount) {
                flag = 1;
                break;
            }
            Logger logger = (Logger) enmu.nextElement();
            if (loggerName != null && !loggerName.trim().equals("")) {
                if (logger.getName().toUpperCase().indexOf(loggerName.toUpperCase()) != -1) {
                    Level level = logger.getLevel();
                    count++;
                    logList.add(SimpleJSON.getInstance().addItem("name", logger.getName())
                            .addItem("level", level != null ? level.toString() : "NULL"));
                }
            }
            else {
                Level level = logger.getLevel();
                count++;
                logList.add(SimpleJSON.getInstance().addItem("name", logger.getName())
                        .addItem("level", level != null ? level.toString() : "NULL"));
            }

        }

        Logger rootlog = LogManager.getRootLogger();
        Level rootlevel = rootlog.getLevel();
        SimpleJSON ret = SimpleJSON
                .getInstance()
                .addItem("logList", SimpleJSON.toSimpleJSONArray(logList))
                .addItem("bigCount", flag)
                .addItem("levelCount", levelCount)
                .addItem("rootLogger",
                        SimpleJSON.getInstance().addItem("name", rootlog.getName())
                                .addItem("level", rootlevel != null ? rootlevel.toString() : "NULL"));

        Set<AppenderSkeleton> asSet = new TreeSet<AppenderSkeleton>(new Comparator<AppenderSkeleton>() {
            // appender的名称排序
            @Override
            public int compare(AppenderSkeleton app1, AppenderSkeleton app2) {
                return app1.getName().compareTo(app2.getName()); // 升序
            }
        });
        // ----@Bgn 有待优化 [所有Logger遍历效率较低]
        Enumeration loggers = LogManager.getLoggerRepository().getCurrentLoggers();
        if (loggers != null) {
            while (loggers.hasMoreElements()) {
                Logger lo = (Logger) loggers.nextElement();
                Enumeration eapp = lo.getAllAppenders();
                while (eapp.hasMoreElements()) {
                    asSet.add((AppenderSkeleton) eapp.nextElement());
                }
            }
        }
        enmu = LogManager.getRootLogger().getAllAppenders();
        if (enmu != null) {
            while (enmu.hasMoreElements()) {
                asSet.add((AppenderSkeleton) enmu.nextElement());
            }
        }
        // ----@End 有待优化
        List<SimpleJSON> appList = new ArrayList<SimpleJSON>();
        for(AppenderSkeleton app: asSet){
            appList.add(SimpleJSON.getInstance().addItem("name", app.getName())
                  .addItem("level", app.getThreshold() != null ? app.getThreshold().toString() : "NULL"));
        }
        ret.addItem("appList", SimpleJSON.toSimpleJSONArray(appList));
        
        return ret.toString();
    }
    
    /* (non-Javadoc)
     * @see com.travelsky.mlf4j.monitor.service.log4j.ILog4jConfigService#showConfigFileContent()
     */
    @Override
    public String showConfigFileContent() throws MlfI18nException {
        try {
            return PropertiesTools.getResoureFileContent(Log4jLogger.configConfigLocation);
        }catch (Exception e) {
            try {
                return PropertiesTools.getResoureFileContent("log4j.xml");
            }catch (Exception ex) {
                log.error(e);
            }
            log.error(e);
            return "can't get config file both log4j.xml and "+Log4jLogger.configConfigLocation;
        }
    }

    /* (non-Javadoc)
     * @see com.travelsky.mlf4j.monitor.service.log4j.ILog4jConfigService#refreshLog4jConfig(java.lang.String)
     */
    @Override
    public void refreshLog4jConfig(String configStr) throws MlfI18nException {
       doConfigure(configStr);
    }
    
    private void doConfigure(String configStr){
        new DOMConfigurator().doConfigure(MvcUtils.IOUtil.getInputStreamFromString(configStr, "UTF-8"), 
                LogManager.getLoggerRepository());
    }
    
    protected void doConfigureDom(String configStr){
        DocumentBuilderFactory dbf = null;
        try { 
          dbf = DocumentBuilderFactory.newInstance();
          log.debug("Standard DocumentBuilderFactory search succeded.");
          log.debug("DocumentBuilderFactory is: "+dbf.getClass().getName());
        } catch(FactoryConfigurationError fce) {
          Exception e = fce.getException();
          log.debug("Could not instantiate a DocumentBuilderFactory.", e);
          throw fce;
        }
        
        try {
          dbf.setValidating(true);

          DocumentBuilder docBuilder = dbf.newDocumentBuilder();

          docBuilder.setErrorHandler(new SAXErrorHandler());      
          docBuilder.setEntityResolver(new Log4jEntityResolver());
           
          Document doc = docBuilder.parse(MvcUtils.IOUtil.getInputStreamFromString(configStr, "UTF-8"));   
          new DOMConfigurator().doConfigure(doc.getDocumentElement(), 
                  LogManager.getLoggerRepository());
        } catch (Exception e) {
            if (e instanceof InterruptedException || e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
          // I know this is miserable...
          log.error("Could not parse "+ configStr + ".", e);
        }
    }
    
}
    
    
