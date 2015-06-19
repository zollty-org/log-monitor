<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>日志配置</title>
		<script type="text/javascript">
		
		</script>
	<style type="text/css">
	  table {margin-top: 20px }
	</style>
	</head>
	<body>
	
		<div style="color:red">
			<strong>log4j中的RootLoger[根日志记录器]：</strong>&nbsp;&nbsp;所有普通的Logger的parent都是它，一般情况我们系统创建的Logger对象都是委托RootLogger把日志记录到appender中去的。
			<br/><strong>日志信息输出的流向是:</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			普通的Logger(如:com.travelsky.User)--n:1-- > RootLogger[单例]--1:n--->Appender(目的源如:控制台)
			<br/><strong>普通的Logger、RootLogger、Appender：</strong>都有自己的日志级别开关，用于控制经过自己这边日志信息输出。如: 如果把RootLogger的日志级别设置为OFF，那么就所有的日志都不记录了。除非普通的Logger设置了自己的Appender。
		</div>
	
		<table border="1" width="80%" bgcolor="#E8E8E8" cellpadding="0" bordercolor="#0000FF" bordercolorlight="#7D7DFF" bordercolordark="#0000A0">
			<form name="" action="<%=path%>/logcfg.cfg" method="post">
				<tr>
				   <td><input type="text" value="<c:out value='${param.queryArg}' escapeXml='true' />" name="queryArg" size="50"/>输入日志名称,如:com.travelsky</td>
				   <td><input type="submit" value="查询" /></td>
				</tr>
			</form>
		</table>
	
		<table border="1" width="100%" style="margin-top:0px" bgcolor="#E8E8E8" cellpadding="0" bordercolor="#0000FF" bordercolorlight="#7D7DFF" bordercolordark="#0000A0">
			<tr>
				<th bgColor='#8080ff'>
					RootLoger的名称
				</th>
				<th bgColor='#8080ff'>
					RootLoger的级别
				</th>
				<th bgColor='#8080ff'>
					操作
				</th>
			</tr>
			<tr>
				<form name="" action="<%=path%>/logcfg.cfg"
						method="post">
					<input type="hidden" value="rootLog" name="type" />
					<input type="hidden" value="modify" name="operType" />
					<input type="hidden" value="<c:out value='${rootLogger.name}' escapeXml='true' />" name="name" />
					<input type="hidden" value="<c:out value='${param.queryArg}' escapeXml='true' />" name="queryArg"/>
					<td  ${param.name==rootLogger.name ? 'bgcolor=red' : ''}>
						<c:out value="${rootLogger.name}" escapeXml="true" />
					</td>
					<td>
						<select name="level">
						    <option value="ALL" ${rootLogger.level=='ALL'?'selected':''}>
								ALL
							</option>
							<option value="TRACE" ${rootLogger.level=='TRACE'?'selected':''}>
								TRACE
							</option>
							<option value="DEBUG" ${rootLogger.level=='DEBUG'?'selected':''}>
								DEBUG
							</option>
							<option value="INFO" ${rootLogger.level=='INFO'?'selected':''}>
								INFO
							</option>
							<option value="WARN" ${rootLogger.level=='WARN'?'selected':''}>
								WARN
							</option>
							<option value="ERROR" ${rootLogger.level=='ERROR'?'selected':''}>
								ERROR
							</option>
							<option value="FATAL" ${rootLogger.level=='FATAL'?'selected':''}>
								FATAL
							</option>
							<option value="OFF" ${rootLogger.level=='OFF'?'selected':''}>
								OFF
							</option>
						</select>
					</td>
					<td>
						<input type="submit" value="修改" />
					</td>
				</form>	
			</tr>	
		</table>
	    <c:if test="${not empty appList}">
			<table border="1" width="100%" style="margin-top:0px" bgcolor="#E8E8E8" cellpadding="0" bordercolor="#0000FF" bordercolorlight="#7D7DFF" bordercolordark="#0000A0">
				<tr>
					<th bgColor='#8080ff'>
						appender的名称(日志输出目的源如: 文件，控制台等)
					</th>
					<th bgColor='#8080ff'>
						appender的级别(此目的源允许通过的最低日志级别,NULL和ALL表示所有日志都通过)
					</th>
					<th bgColor='#8080ff'>
						操作
					</th>
				</tr>
				<c:forEach items="${appList}" var="appender">
					<form name="" action="<%=path%>/logcfg.cfg"
						method="post">
						<input type="hidden" value="modify" name="operType" />
						<input type="hidden" value="appender" name="type" />
						<input type="hidden" value="<c:out value='${appender.name}' escapeXml='true' />" name="name" />
						<input type="hidden" value="<c:out value='${param.queryArg}' escapeXml='true' />" name="queryArg"/>
					<tr>
						<td  ${param.name==appender.name ? 'bgcolor=red' : ''}>
							<c:out value="${appender.name}" escapeXml="true" />
						</td> 
						<td>
							<select name="level">
								<option value="NULL" ${appender.level=='NULL'?'selected':''}>
									NULL
								</option>
							    <option value="ALL" ${appender.level=='ALL'?'selected':''}>
									ALL
								</option>
								<option value="TRACE" ${appender.level=='TRACE'?'selected':''}>
									TRACE
								</option>
								<option value="DEBUG" ${appender.level=='DEBUG'?'selected':''}>
									DEBUG
								</option>
								<option value="INFO" ${appender.level=='INFO'?'selected':''}>
									INFO
								</option>
								<option value="WARN" ${appender.level=='WARN'?'selected':''}>
									WARN
								</option>
								<option value="ERROR" ${appender.level=='ERROR'?'selected':''}>
									ERROR
								</option>
								<option value="FATAL" ${appender.level=='FATAL'?'selected':''}>
									FATAL
								</option>
								<option value="OFF" ${appender.level=='OFF'?'selected':''}>
									OFF
								</option>
							</select>
						</td>
						<td>
							<input type="submit" value="修改" />
						</td>
					</tr>
					</form>
				</c:forEach>
			</table>
		</c:if>

		<c:if test="${not empty logList}">
			<table border="1" width="100%" style="margin-top:0px" bgcolor="#E8E8E8" cellpadding="0" bordercolor="#0000FF" bordercolorlight="#7D7DFF" bordercolordark="#0000A0">
				<tr>
					<th bgColor='#8080ff'>
						logger的名称(普通的Logger)
						<c:if test="${bigCount}">
							系统logger对象个数超过了${levelCount}个，请在上面输入查询条件，以便缩小范围
						</c:if>
					</th bgColor='#8080ff'>
					<th bgColor='#8080ff'>
						logger的级别
					</th >
					<th bgColor='#8080ff'>
						操作
					</th>
				</tr>
				<c:forEach items="${logList}" var="logger">
					<form name="" action="<%=path%>/logcfg.cfg"
						method="post">
						<input type="hidden" value="modify" name="operType" />
						<input type="hidden" value="logger" name="type" />
						<input type="hidden" value="<c:out value='${param.queryArg}' escapeXml='true' />" name="queryArg"/>
						<input type="hidden" value="<c:out value='${logger.name}' escapeXml='true' />" name="name" />
					<tr>
						<td  ${param.name==logger.name ? 'bgcolor=red' : ''}>
							<c:out value="${logger.name}" escapeXml="true" />
						</td>
						<td>
							<select name="level">
								<option value="NULL" ${logger.level=='NULL'?'selected':''}>
									NULL
								</option>
							    <option value="ALL" ${logger.level=='ALL'?'selected':''}>
									ALL
								</option>
								<option value="TRACE" ${logger.level=='TRACE'?'selected':''}>
									TRACE
								</option>
								<option value="DEBUG" ${logger.level=='DEBUG'?'selected':''}>
									DEBUG
								</option>
								<option value="INFO" ${logger.level=='INFO'?'selected':''}>
									INFO
								</option>
								<option value="WARN" ${logger.level=='WARN'?'selected':''}>
									WARN
								</option>
								<option value="ERROR" ${logger.level=='ERROR'?'selected':''}>
									ERROR
								</option>
								<option value="FATAL" ${logger.level=='FATAL'?'selected':''}>
									FATAL
								</option>
								<option value="OFF" ${logger.level=='OFF'?'selected':''}>
									OFF
								</option>
							</select>
						</td>
						<td>
							<input type="submit" value="修改" />
						</td>
					</tr>
					</form>
				</c:forEach>
			</table>
		</c:if>
	</body>
</html>
