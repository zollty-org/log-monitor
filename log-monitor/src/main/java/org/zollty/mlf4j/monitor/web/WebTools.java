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
package org.zollty.mlf4j.monitor.web;

import org.zollty.log.LogFactory;
import org.zollty.log.Logger;
import org.zollty.mlf4j.base.Const;
import org.zollty.mlf4j.base.exception.MlfI18nException;

/**
 * @author zollty
 * @since 2014-6-10
 */
public class WebTools {
    
    private static Logger LOG = LogFactory.getLogger();

    public interface Service1 {
        void service() throws MlfI18nException;
    }
    
    public interface Service2 {
        String service() throws MlfI18nException;
    }
    
    public static String doService(Service1 service){
        String ret;
        try {
            service.service();
            ret = Const.OK;
        }
        catch (MlfI18nException e) {
            ret = Const.ERROR + e.getMessage();
        }
        catch (Exception e) {
            LOG.error(e);
            ret = Const.ERROR + e.getMessage();
        }
        return ret;
    }
    
    public static String doService(Service2 service) {
        String ret;
        try {
            ret = service.service();
        }
        catch (MlfI18nException e) {
            ret = Const.ERROR + e.getMessage();
        }
        catch (Exception e) {
            LOG.error(e);
            ret = Const.ERROR + e.getMessage();
        }
        return ret;
    }
    
}
