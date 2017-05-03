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
package org.zollty.mlf4j.test.service;

import java.util.logging.Logger;

import org.zollty.log.jul14.SLF4JBridgeHandler;

/**
 * @author zollty
 * @since 2014-6-17
 */
public class JULTest {
    
    private static Logger LOG = Logger.getLogger(JULTest.class.getName());
    
    private static void setUp() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    
    public static void main(String[] args) {
        setUp();
        
        String msg = "======================================";
        
        LOG.finest(msg);
        LOG.finer(msg);
        LOG.fine(msg);
        LOG.info(msg);
        LOG.warning(msg);
        LOG.severe(msg);
        LOG.severe(msg);
    }

}
