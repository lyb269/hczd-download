<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>${cardNo}消费数据下载实时</title>
		<script type="text/javascript" src="js/hczd-sys.js"></script>
		<script type="text/javascript">
			$(function(){
				//定时刷新控制台信息
				setInterval("console_info()",1000);
			});
		//控制台输出数据
			function console_info(){
				alert(1);
				$.ajax({
					url:'<%=basePath%>/hczd-download/card/gas_card_consumption_log/ajax_msg_console.htm',
					data:"cardNo="+$("#cardNo").val(),
					success:function(data){
						alert(data.show_msg);
						$("#console_show").html(data.show_msg);
					}
				});
			}
		</script>
	</head>
	<body>
	  	<input type="hidden" value="${ cardNo}" id="cardNo" />
	  	<div style="OVERFLOW-Y: auto;background-color: black;color: white;height:100%;padding:0;" id="console_show"></div>
  	</body>
</html>