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
package org.zollty.mlf4j.base.exception;


/**
 * @author zollty 
 * @since 2013-01-16
 */
public class ErrorCode {
    
    private ErrorCode(){
    }
    /**
     * ReadMe：
     * ----每一条错误信息应该包括：错误代码和注释。
     * 
     * ----命名规范如下：
     * '英文字母'开头，长度为6，英文字母用于区别错误所属的模块(或类型)
     * 例如：EC开头的错误信息EC0000、EC0001等，代表公用的、常见的一些错误类型。
     * 
     * ----特别提醒：
     *  一定要写注释，方便自己和别人快速查看！
     */
    
    // EC开头 -- 公用的、常见的错误类型
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /** 未知错误，参见：{} */
    public static final String EC0000 = "EC0000";
    
    /** 未定义错误，参见：{} */
    public static final String EC0001 = "EC0001";
    
    /** 无效的参数：{} */
    public static final String EC0002 = "EC0002";
    
    
    // E0开头 -- 未详细分类的错误信息
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~     

    
    // MFC开头 -- MLF4J Config相关的错误信息
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
//    /** 无法获取配置文件，原因: {} */
//    public static final String MFC001 = "MFC001";
    
}
