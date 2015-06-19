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
package org.zollty.mlf4j.monitor.service.mlf4j;

import org.zollty.mlf4j.base.exception.MlfI18nException;


/**
 * @author zollty
 * @since 2014-6-4
 */
public interface IMlf4jConfigService {
    
    String getMlf4jConfig() throws MlfI18nException;
    
    void refreshMlf4jConfig(String configStr) throws MlfI18nException;
    
    String showAllLoggers() throws MlfI18nException;
    
    boolean removeLoggerFromCache(String loggerName);
    
    String showLoggerExeInfo();
    
}
