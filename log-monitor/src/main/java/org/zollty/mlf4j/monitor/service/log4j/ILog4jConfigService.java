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
package org.zollty.mlf4j.monitor.service.log4j;

import org.zollty.mlf4j.base.exception.MlfI18nException;
import org.zollty.mlf4j.monitor.service.ILogConfigService;

/**
 * @author zollty
 * @since 2014-5-8
 */
public interface ILog4jConfigService extends ILogConfigService {
    
    void modifyAppenderLevel(String name, String level) throws MlfI18nException;
    
    void modifyLoggerLevel(String name, String level) throws MlfI18nException;
    
    void modifyRootLoggerLevel(String level) throws MlfI18nException;
    
    String showConfigDetails(String loggerName) throws MlfI18nException;
    
    String showConfigFileContent() throws MlfI18nException;
    
    void refreshLog4jConfig(String configStr) throws MlfI18nException;

}
