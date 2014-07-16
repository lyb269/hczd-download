<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath%>">
    
    <title>${main_card}实时控制台:${dateStr}</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="image/vnd.microsoft.icon" rel="shortcut icon" href="images/favicon.ico">
	<script type="text/javascript" src="js/hczd-sys.js"></script>
		<script type="text/javascript">
			$(function(){
				//定时刷新控制台信息
				setInterval("console_info()",1000);
			});
		//控制台输出数据
			function console_info(){
				$.ajax({
					url:'<%=basePath%>card/petro/ajax_msg_console.htm',
					data:"cardNo="+$("#cardNo").val(),
					success:function(data){
						$("#console_show").html(data.show_msg);
					}
				});
			}
		</script>
		
	</head>
	<body>
	  	<input type="hidden" value="${ main_card}" id="cardNo" />
	  	<div style="OVERFLOW-Y: auto;background-color: black;color: white;height:100%;padding:0;" id="console_show"></div>
	</body>
</html>