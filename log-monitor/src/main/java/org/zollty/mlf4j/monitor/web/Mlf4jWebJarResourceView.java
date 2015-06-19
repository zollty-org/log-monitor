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
package org.zollty.mlf4j.monitor.web;

import org.zollty.framework.mvc.view.AbstractStaticResourceView;

/**
 * @author zollty
 * @since 2014-6-3
 */
public class Mlf4jWebJarResourceView extends AbstractStaticResourceView {
    
    private static String viewPathPrefix = "classpath:/META-INF/resources/mlf4j/";
    
    public Mlf4jWebJarResourceView(String shortPath) {
        super(shortPath);
    }
    
    @Override
    public String getViewPathPrefix(){
        return viewPathPrefix;
    }
    
}
