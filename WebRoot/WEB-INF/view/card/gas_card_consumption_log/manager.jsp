<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>消费数据管理</title>
    <script type="text/javascript" src="js/hczd-sys.js"></script>
    <script type="text/javascript">
    	function startDownload(){
    		$.post('card/gas_card_consumption_log/startDownload.htm',
				{
					'startDate':$('#date_start').datebox('getValue'),
					'endDate':$('#date_end').datebox('getValue'),
					'cardNo':$('#main_card_no').val(),
					'pwd':$('#card_password').val()
				 },
				function(result){
					console.log(result);
				},
				"json"
			);
    	}
    </script>
  </head>
  <body>
   	<fieldset>
	       <legend>智能抓取消费数据</legend>
	        	<table border="0" width="49%" style="float: left;">
	        		<tbody><tr>
	        			<td>主卡列表：</td>
	        			<td>
	        				<select id="main_card_no">
	        					<option value="">-选择主卡-</option>
	        					
	        						<option value="1000113500002425882">1000113500002425882_中石化龙岩</option>
	        					
	        						<option value="1000113500002372932">1000113500002372932_中石化三明</option>
	        					
	        						<option value="1000113500002347521">1000113500002347521_中石化漳州</option>
	        					
	        						<option value="1000113500002298433">1000113500002298433_中石化福州</option>
	        					
	        						<option value="1000113500002263744">1000113500002263744_中石化南平</option>
	        					
	        						<option value="1000113500002105436">1000113500002105436_中石化厦门</option>
	        					
	        						<option value="1000113500002089133">1000113500002089133_中石化宁德</option>
	        					
	        						<option value="1000113500002053812">1000113500002053812_中石化莆田</option>
	        					
	        						<option value="1000113500002047173">1000113500002047173_中石化莆田</option>
	        					
	        						<option value="1000113500002045125">1000113500002045125_中石化泉州</option>
	        					
	        						<option value="1000113500001966117">1000113500001966117_中石化厦门</option>
	        					
	        				</select>
	        			</td>
	        			<td>请输入主卡密码：</td>
	        			<td><input type="text" name="password" id="card_password" value="851019"></td>
	        		</tr>
	        		<tr>
	        			<td>开始日期：</td>
	        			<td><input id="date_start" name="date_start" type="text" class="easyui-datebox" required="required"></input> </td>
	        			<td>结束日期：</td>
	        			<td><input id="date_end" name="date_end" type="text" class="easyui-datebox" required="required"></input> </td>
	        		</tr>
	        		<tr>
	        			<!-- <td>验证码：</td>
	        			<td><input type="text" name="val_code" id="val_code1" value="" size="6"><a href="javascript:getsinopec_val_code();">点击获取验证码</a></td> -->
	        			<td></td>
	        			<td></td>
	        			<td colspan="2">
	        				<a id="btnSinopicDownload" href="javascript:void(0);" onclick="startDownload()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">启动下载</a> 
	        				<a href="card/gas_card_consumption_log/list_cache_main_card_excel.htm?type=sinopic" target="_blank" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">下载缓存数据</a>  
	        				<a id="btnConsoleInfo" href="javascript:window.open('card/gas_card_consumption_log/console_info.htm','newwindow','height=500,width=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');" class="easyui-linkbutton" data-options="iconCls:'icon-tip'">
	        					实时控制台
	        				</a> 
	        			</td>
	        		</tr>
	        		<tr>
	        			<td>消费数据当前下载状态：</td>
	        			<td><strong>手动</strong></td>
	        			<td colspan="2"></td>
	        		</tr>
	        	</tbody></table>
	        	<table width="50%" border="0" style="float: left;padding: 10px;line-height: 26px;border-left: 2px solid #123456;">
	        		<tbody><tr>
	        			<td>当前步骤：<div id="s_step">初始化。。。</div>&nbsp;&nbsp;&nbsp;<div id="down_link"></div>
	        			</td>
	        		</tr>
	        	</tbody>
	        </table>
	     </fieldset>
  </body>
</html>
