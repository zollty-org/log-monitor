package org.zollty.log.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 动态修改log4j内部参数，以便实现动态修改日志级别，关闭日志输出源
 *
   OFF_INT = Integer.MAX_VALUE;
   FATAL_INT = 50000;
   ERROR_INT = 40000;
   WARN_INT  = 30000;
   INFO_INT  = 20000;
   DEBUG_INT = 10000;
   FINE_INT = DEBUG_INT;
   ALL_INT = Integer.MIN_VALUE;
 * 
 * @author bqsheng
 */
@SuppressWarnings("serial")
public class LogConfigAction extends HttpServlet {
    /**
	 * 配置页面 最多显示的日志记录器的信息 条数
	 * 最少显示1 最大显示1000
	 */
	private static int levelCount=201;
	/**
	 * 相应日志显示页面
	 */
	private static String logCfgJspPath;

	public void init() throws ServletException {
		super.init();
		String tmp=this.getInitParameter("logCfgJspPath");
		if(tmp==null||tmp.trim().equals("")){
			tmp="/jsp/logConfig.jsp";
		}else{
			logCfgJspPath=tmp;
		}
		String showCount=this.getInitParameter("showCount");
		if(showCount!=null&&!"".equals(showCount.trim())){
			int c=Integer.parseInt(showCount);
			if(c<=0){
				levelCount=201;
			}else if(c>1001){
				levelCount=1000;
			}else{
				levelCount=c;
			}
		}
	}
	
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		String operType=request.getParameter("operType");
		if("modify".equals(operType)){
			modifyConfig(request);
		}
		showConfig(request);
		request.getRequestDispatcher(logCfgJspPath).forward(request, response);
	}

	private void modifyConfig(ServletRequest request) {
		//appender or logger
		String type=request.getParameter("type");
		///Appender 或者 Logger的名称
		String name=request.getParameter("name");
		//ERROR,INFO,DEBUG,TRACE,OFF
		String level=request.getParameter("level");	
		if("appender".equals(type)){
			//当前操作是准对 Appender的
			AppenderSkeleton app = (AppenderSkeleton) LogManager.getRootLogger().getAppender(name);
			if(app==null){
				return;
			}
			if (app.getName().equals(name)) {
				if ("OFF".equals(level)) {
					// 不允许任何级别日志输出
					app.setThreshold(Level.OFF);
				} else if ("FATAL".equals(level)) {
					// 允许FATAL 和比FATAL级别大的日志输出
					app.setThreshold(Level.FATAL);
				} else if ("ERROR".equals(level)) {
					// 允许Error 和比error级别大的日志输出
					app.setThreshold(Level.ERROR);
				} else if ("WARN".equals(level)) {
					// 允许WARN 和比INFO级别大的日志输出
					app.setThreshold(Level.WARN);
				} else if ("INFO".equals(level)) {
					// 允许INFO 和比INFO级别大的日志输出
					app.setThreshold(Level.INFO);
				} else if ("DEBUG".equals(level)) {
					// 允许DEBUG 和比DEBUG级别大的日志输出
					app.setThreshold(Level.DEBUG);
				} else if ("TRACE".equals(level)) {
					// 允许TRACE 和比TRACE级别大的日志输出
					app.setThreshold(Level.TRACE);
				} else if ("ALL".equals(level)) {
					// 允许所有级别大的日志输出
					app.setThreshold(Level.ALL);
				} else if ("NULL".equals(level)) {
					// 允许所有级别大的日志输出
					app.setThreshold(null);
				}
			}
		}else if("logger".equals(type)||"rootLog".equals(type)){///当前操作是对Logger进行修改的
			//得到当前的日志对象
			Logger log=null;
			if("rootLog".equals(type)){
				log=Logger.getRootLogger();
				if("NULL".equals(level)){
					return; //rootlogger不能设置为NULL
				}
			}else{
				log=LogManager.exists(name);
			}
			//ERROR,INFO,DEBUG,TRACE,OFF
			if(log!=null&&level!=null&&!level.trim().equals("")){
				if("OFF".equals(level)){
					//不允许任何级别日志输出
					log.setLevel(Level.OFF);
				}else if("FATAL".equals(level)){
					//允许FATAL 和比FATAL级别大的日志输出
					log.setLevel(Level.FATAL);
				}else if("ERROR".equals(level)){
					//允许Error 和比error级别大的日志输出
					log.setLevel(Level.ERROR);
				}else if("WARN".equals(level)){
					//允许WARN 和比INFO级别大的日志输出
					log.setLevel(Level.WARN);
				}else if("INFO".equals(level)){
					//允许INFO 和比INFO级别大的日志输出
					log.setLevel(Level.INFO);
				}else if("DEBUG".equals(level)){
					//允许DEBUG 和比DEBUG级别大的日志输出
					log.setLevel(Level.DEBUG);
				}else if("TRACE".equals(level)){
					//允许TRACE 和比TRACE级别大的日志输出
					log.setLevel(Level.TRACE);
				}else if("OFF".equals(level)){
					//不允许任何级别日志输出
					log.setLevel(Level.OFF);
				}else if("ALL".equals(level)){
					//允许所有级别大的日志输出
					log.setLevel(Level.ALL);
				}else if("NULL".equals(level)){
					//允许所有级别大的日志输出
					log.setLevel(null);
				}
			}
		}
	}

	/**
	 * 把日志记录器信息进行读取，
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private void showConfig(ServletRequest request) {
        String queryArg = request.getParameter("queryArg");
        Enumeration enmu = LogManager.getLoggerRepository().getCurrentLoggers();
        List logList = new ArrayList();
        int count = 0;
        int flag = 0;
        Set levelNames = new HashSet(10);
        while (enmu.hasMoreElements()) {
            if (count == levelCount) {
                flag = 1;
                break;
            }
            Logger logger = (Logger) enmu.nextElement();
            if (queryArg != null && !queryArg.trim().equals("")) {
                if (logger.getName().toUpperCase().indexOf(queryArg.toUpperCase()) != -1) {
                    DLogInfoBean iBean = new DLogInfoBean();
                    iBean.setName(logger.getName());
                    Level level = logger.getLevel();
                    iBean.setLevel(level != null ? level.toString() : "NULL");
                    count++;
                    logList.add(iBean);
                    levelNames.add(iBean.getLevel());
                }
            }
            else {
                DLogInfoBean iBean = new DLogInfoBean();
                iBean.setName(logger.getName());
                Level level = logger.getLevel();
                iBean.setLevel(level != null ? level.toString() : "NULL");
                count++;
                logList.add(iBean);
                levelNames.add(iBean.getLevel());
            }
        }

        request.setAttribute("logList", logList);
        request.setAttribute("bigCount", flag + "");
        request.setAttribute("levelCount", levelCount + "");
        Logger log = LogManager.getRootLogger();
        DLogInfoBean logBean = new DLogInfoBean();
        logBean.setName(log.getName());
        Level level = log.getLevel();
        logBean.setLevel(level != null ? level.toString() : "NULL");
        request.setAttribute("rootLogger", logBean);

        // enmu=LogManager.getRootLogger().getAllAppenders();
        Map<AppenderSkeleton, AppenderSkeleton> amap = new HashMap<AppenderSkeleton, AppenderSkeleton>();
        Enumeration loggers = LogManager.getLoggerRepository().getCurrentLoggers();
        if (loggers != null) {
            while (loggers.hasMoreElements()) {
                Logger lo = (Logger) loggers.nextElement();
                Enumeration eapp = lo.getAllAppenders();
                while (eapp.hasMoreElements()) {
                    AppenderSkeleton as = (AppenderSkeleton) eapp.nextElement();
                    amap.put(as, as);
                }
            }
        }
        enmu = LogManager.getRootLogger().getAllAppenders();
        if (enmu != null) {
            while (enmu.hasMoreElements()) {
                AppenderSkeleton as = (AppenderSkeleton) enmu.nextElement();
                amap.put(as, as);
            }
        }
        Iterator<AppenderSkeleton> it = amap.keySet().iterator();
        List<DLogInfoBean> appList = new ArrayList<DLogInfoBean>();
        while (it.hasNext()) {
            AppenderSkeleton app = (AppenderSkeleton) it.next();
            DLogInfoBean iBean = new DLogInfoBean();
            iBean.setName(app.getName());

            iBean.setLevel(app.getThreshold() != null ? app.getThreshold().toString() : "NULL");
            appList.add(iBean);
        }
        // enmu=LogManager.getRootLogger().getAllAppenders();
        // if(enmu!=null){
        // while(enmu.hasMoreElements()){
        // AppenderSkeleton app=(AppenderSkeleton)enmu.nextElement();
        // DLogInfoBean iBean=new DLogInfoBean();
        // iBean.setName(app.getName());
        //
        // iBean.setLevel(app.getThreshold()!=null?app.getThreshold().toString():"NULL");
        // appList.add(iBean);
        // }
        // }
        request.setAttribute("appList", appList);
    }
}
