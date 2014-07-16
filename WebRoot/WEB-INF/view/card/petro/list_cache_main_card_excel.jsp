<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <fieldset>
  	<legend>缓存中的消费数据列表如下：  </legend>
  		<c:if test="${!empty files}">
		  	<table>
		  		<tr>
		  			<th>文件名</th>
		  			<th>最后修改时间</th>
		  			<th>下载信息</th>
		  		</tr>
			   <c:forEach items="${files}" var="f" varStatus="ff">
		  		<tr>
					<td><a href="card/petro/downloadCache.htm?name=${f.f.getName()}" title="下载" >${f.f.getName()}</a></td>
					
					<td style="padding-left: 50px;">
						<%-- <fmt:formatDate value="${f.lastModified()}" type="both" dateStyle="java.lang.Long"/>  --%>
						<script type="text/javascript"> 
							var str=${f.f.lastModified()}; 
							var d = new Date();
							d.setTime(str);
							var count = ${ff.count};
							$("#modiDate"+count).val(d.toLocaleString()); 
						/* 	document.write(d.toLocaleString()); */
						</script>
						<input type="text" id="modiDate${ff.count}" style="border: 0px;width: 200px;" disabled="disabled"/>
					</td>
					<td>
						${f.date_msg}
					</td>
				</tr>
			   </c:forEach> 
		  </table>
	  </c:if>
	  <c:if test="${empty files}">
	  	暂无
	  </c:if>
  </fieldset>
