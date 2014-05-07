<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <script type="text/javascript">
    		function startDownload(){
    			var startDate = $('#date_start').datebox('getValue');
    			var endDate = $('#date_end').datebox('getValue');
    			var cardNo = $('#mainCard').val();
    			var pwd =    $('#card_password').val();
    			if(startDate == "" || startDate == null){
    				alert("开始日期不能为空");
    			}else if(endDate == "" || endDate == null){
    				alert("结束日期不能为空");
    			}else if(pwd == "" || pwd == null){
    				alert("密码不能为空");
    			}else{
    				$.ajax({
	    			  type:'POST',
					  url: '<%=basePath%>card/gas_card_consumption_log/startDownload.htm',
					  data:'startDate='+startDate+'&endDate='+endDate+'&cardNo='+cardNo+'&pwd='+pwd,
					  success: function(data){
					  }
					});
					$('#hczd_sys_win_edit').window('close');
    			}
    			
    		}
    		
    		/* 日期字段判断 */
    		$('#date_start').datebox({    
   				onSelect: function(date){
 					 if(date >= new Date()){
				 	 	alert("开始日期必须小于今天");
				 	 	$('#date_start').datebox('setValue', "");	
				 	 }
    			}
			}); 
			$('#date_end').datebox({    
   				onSelect: function(date){
   					var endDate = $('#date_end').datebox('getValue');
   					var startDate = $('#date_start').datebox('getValue');
 					 if(endDate < startDate){
				 	 	alert("结束日期必须大于等于开始日期");
				 	 	$('#date_end').datebox('setValue', "");	
				 	 }
    			}
			}); 
   		  </script>
   <table>
  		<tr>
  			<input type="hidden" id="mainCard" value="${hz_mainCard.card_no}" />
  			<td>主卡卡号：</td>
  			<td>
  				${hz_mainCard.card_no}
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
  			<td>消费数据当前下载状态：</td>
  			<td><strong>手动</strong></td>
  			<td colspan="2"></td>
  		</tr>
  			<tr>
  			<!-- <td>验证码：</td>
  			<td><input type="text" name="val_code" id="val_code1" value="" size="6"><a href="javascript:getsinopec_val_code();">点击获取验证码</a></td> -->
  			<td colspan="4" align="center">
  				<a id="btnSinopicDownload" href="javascript:void(0);" onclick="startDownload();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">启动下载</a> 
  			</td>
  		</tr>
  	</table>
