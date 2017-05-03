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
import org.zollty.framework.mvc.annotation.URIParam;
import org.zollty.framework.mvc.view.JspView;

/**
 * @author zollty
 * @since 2014-6-3
 */
@Controller
public class ViewForwardController {
    
    @RequestMapping("/mlf4j/vf/[path]")
    public View sForward1(@URIParam("path") String path){
        return new JspView("/"+path+".jsp");
    }

}
