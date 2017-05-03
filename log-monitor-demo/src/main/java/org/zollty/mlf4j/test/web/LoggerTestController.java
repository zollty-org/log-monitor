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
package org.zollty.mlf4j.test.web;

import org.zollty.framework.mvc.View;
import org.zollty.framework.mvc.annotation.Controller;
import org.zollty.framework.mvc.annotation.RequestMapping;
import org.zollty.framework.mvc.view.BlankView;
import org.zollty.mlf4j.test.service.LoggerTest;
import org.zollty.mlf4j.test.service.OrderModule;
import org.zollty.mlf4j.test.service.UserModule;

/**
 * @author zollty
 * @since 2014-6-4
 */
@Controller
public class LoggerTestController { 
    
    @RequestMapping("GET:/mlf4j/loggerTest1")
    public View testLog1(){
        
        LoggerTest.doTest();
        
        return new BlankView();
    }

    
    @RequestMapping("GET:/mlf4j/loggerTest2")
    public View testLog2(){
        
        OrderModule.doTest();
        UserModule.doTest();
        
        return new BlankView();
    }

}
