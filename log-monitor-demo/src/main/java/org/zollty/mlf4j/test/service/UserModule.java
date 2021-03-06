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

import org.zollty.log.LogFactory;
import org.zollty.log.Logger;


/**
 * @author zollty
 * @since 2014-6-4
 */
public class UserModule {
    
    private static Logger log = LogFactory.getLogger("mod.user.LoggerTest");
    
    public static void doTest(){
        System.out.println("===================USER-MODULE-ST===================");
        log.trace("---------------------"+UserModule.class.getSimpleName());
        log.debug("---------------------"+UserModule.class.getSimpleName());
        log.info("---------------------"+UserModule.class.getSimpleName());
        log.warn("---------------------"+UserModule.class.getSimpleName());
        log.error("---------------------"+UserModule.class.getSimpleName());
        System.out.println("===================USER-MODULE-EN===================");
    }

}
