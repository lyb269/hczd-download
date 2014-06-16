<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="image/vnd.microsoft.icon" rel="shortcut icon" href="images/favicon.ico">
	<script type="text/javascript" src="js/hczd-sys.js"></script>
	 <script type="text/javascript">
    		function startDownload(){
    			var startDate = $('#date_start').datebox('getValue');
    			var endDate = $('#date_end').datebox('getValue');
    			if(startDate == "" || startDate == null){
    				alert("开始日期不能为空");
    			}else if(endDate == "" || endDate == null){
    				alert("结束日期不能为空");
    			}else if(startDate > endDate){
    				alert("开始日期必须小于结束日期");
    			}else{
    				$('#btn_download').html("<span class='l-btn-left'><span class='l-btn-text icon-ok l-btn-icon-left'>下载中..</span></span>");
    				$.ajax({
	    			  type:'POST',
					  url: '<%=basePath%>access/access_card/startDownload.htm',
					  data:'startDate='+startDate+'&endDate='+endDate,
					  success: function(data){
						if(data =="200"){
							alert("下载成功！");
						}else if(data="none_msg"){
							alert("该时间段没有通行记录！");
						}else{
							alert("下载出错！");
						}
						$('#btn_download').html("<span class='l-btn-left'><span class='l-btn-text icon-ok l-btn-icon-left'>下载</span></span>");
					  }
					});
					
					$('#dg').datagrid('reload');
    			}
    			
    		}
    		
			function console(){
				window.open('access/access_card/console_info.htm','height=500,width=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
			}
   		  </script>
  </head>
  
  <body>
  	<fieldset>
  		<legend>闽通卡消费数据下载</legend>
	    <table>
	  		<tr>
	  			<td>开始日期：</td>
	  			<td><input id="date_start" name="date_start" type="text" class="easyui-datebox" required="required"></input> </td>
	  			<td>结束日期：</td>
	  			<td><input id="date_end" name="date_end" type="text" class="easyui-datebox" required="required"></input> </td>
	  		</tr>
	  			<tr>
	  			<!-- <td>验证码：</td>
	  			<td><input type="text" name="val_code" id="val_code1" value="" size="6"><a href="javascript:getsinopec_val_code();">点击获取验证码</a></td> -->
	  			<td colspan="4" align="center">
	  				<a id="btn_download" href="javascript:void(0);" onclick="startDownload();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">下载</a> 
	  				<a id="btn_console" href="javascript:void(0);" onclick="console();" class="easyui-linkbutton" data-options="iconCls:'icon-tip'">实时控制台</a> 
	  			</td>
	  		</tr>
	  	</table>
  	</fieldset>
  </body>
</html>
