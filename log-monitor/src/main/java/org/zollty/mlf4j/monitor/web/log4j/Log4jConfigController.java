/* 
 * Copyright (C) 2012-2014 TravelSky Technology Limited.
 * 
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
 * Create by Zollty Tsou (zouty@travelsky.com, http://blog.csdn.net/zollty)
 */
package org.zollty.mlf4j.monitor.web.log4j;

import javax.servlet.http.HttpServletRequest;

import org.zollty.framework.core.annotation.Inject;
import org.zollty.framework.mvc.View;
import org.zollty.framework.mvc.annotation.Controller;
import org.zollty.framework.mvc.annotation.HttpParam;
import org.zollty.framework.mvc.annotation.RequestMapping;
import org.zollty.framework.mvc.view.JsonView;
import org.zollty.framework.mvc.view.TextView;
import org.zollty.framework.util.MvcUtils;
import org.zollty.mlf4j.base.Const;
import org.zollty.mlf4j.base.exception.MlfI18nException;
import org.zollty.mlf4j.monitor.service.log4j.ILog4jConfigService;
import org.zollty.mlf4j.monitor.web.WebTools;
import org.zollty.mlf4j.monitor.web.WebTools.Service1;
import org.zollty.mlf4j.monitor.web.WebTools.Service2;

/**
 * @author zollty
 * @since 2014-5-8
 */
@Controller
public class Log4jConfigController {
    
    @Inject("log4jConfigService")
    private ILog4jConfigService log4jConfigService;
    
    @RequestMapping("/mlf4j/log4j/modify-appender-level")
    public View modifyAppenderLevel(HttpServletRequest request){
        
        final String name = request.getParameter("name");
        final String level = request.getParameter("level");
        return new TextView(WebTools.doService((new Service1() {
            @Override
            public void service() throws MlfI18nException {
                log4jConfigService.modifyAppenderLevel(name, level);
            }
        })));
    }
    
    @RequestMapping("/mlf4j/log4j/modify-logger-level")
    public View modifyLoggerLevel(HttpServletRequest request){
        final String name = request.getParameter("name");
        final String level = request.getParameter("level");
        return new TextView(WebTools.doService((new Service1() {
            @Override
            public void service() throws MlfI18nException {
                log4jConfigService.modifyLoggerLevel(name, level);
            }
        })));
    }
    
    @RequestMapping("/mlf4j/log4j/modify-rootLogger-level")
    public View modifyRootLoggerLevel(HttpServletRequest request){
        final String level = request.getParameter("level");
        return new TextView(WebTools.doService((new Service1() {
            @Override
            public void service() throws MlfI18nException {
                log4jConfigService.modifyRootLoggerLevel(level);
            }
        })));
    }
    
    @RequestMapping("/mlf4j/log4j/show-config-details")
    public View showConfigDetails(HttpServletRequest request){
        final String loggerName = request.getParameter("loggerName");
        return new JsonView(WebTools.doService((new Service2() {
            @Override
            public String service() throws MlfI18nException {
                return log4jConfigService.showConfigDetails(loggerName);
            }
        })));
    }
    
    @RequestMapping("/mlf4j/log4j/show-config-file")
    public View showConfigFileContent(HttpServletRequest request) {
        return new TextView(WebTools.doService((new Service2() {
            @Override
            public String service() throws MlfI18nException {
                return log4jConfigService.showConfigFileContent();
            }
        })));
    }
    
    
    @RequestMapping("/mlf4j/log4j/refresh-config")
    public View getMlf4jConfig(@HttpParam("configStr") final String configStr) {
        String ret;
        if(MvcUtils.StringUtil.isNotBlank(configStr)){
            ret = WebTools.doService(new Service1() {
                @Override
                public void service() throws MlfI18nException {
                    log4jConfigService.refreshLog4jConfig(configStr);
                }
            });
        } else {
            ret = Const.ERROR + "configStr is blank!";
        }

        return new TextView(ret);
    }
}
