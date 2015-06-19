package org.zollty.log.servlet;

/**
 * 用于在页面进行信息展示的java bean
 * @author bqsheng
 *
 */
public class DLogInfoBean {
	
	//private String name="";
	
	private String name="";
	/**
	 * 当前级别
	 * FATAL,ERROR,WARN,INFO,DEBUG,TRACE,OFF
	 */
	private String level;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
