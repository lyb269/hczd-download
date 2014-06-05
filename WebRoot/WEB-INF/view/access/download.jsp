<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <script type="text/javascript">
    		function startDownload(){
    			var startDate = $('#date_start').datebox('getValue');
    			var endDate = $('#date_end').datebox('getValue');
    			var cardNo = $('#mainCard').val();
    			if(startDate == "" || startDate == null){
    				alert("开始日期不能为空");
    			}else if(endDate == "" || endDate == null){
    				alert("结束日期不能为空");
    			}else{
    				$.ajax({
	    			  type:'POST',
					  url: '<%=basePath%>access/access_card/startDownload.htm',
					  data:'startDate='+startDate+'&endDate='+endDate+'&cardNo='+cardNo,
					  success: function(data){
						if(data =="200"){
							alert("下载成功！");
						}else if(data="none_msg"){
							alert("该时间段没有通行记录！");
						}else{
							alert("下载出错！");
						}
					  }
					});
					$('#hczd_sys_win_edit').window('close');
					$('#dg').datagrid('reload');
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
   		<c:if test="${!empty hz_access_card}">
	  		<tr>
	  			<input type="hidden" id="mainCard" value="${hz_access_card.card_no}" />
	  			<td>卡号：</td>
	  			<td>
	  				${hz_access_card.card_no}
	  			</td>
	  			<td>车牌号：</td>
	  			<td>
	  				${hz_access_card.vehicle_no}
	  			</td>
	  		</tr>
  		</c:if>
  		<c:if test="${empty hz_access_card}">
  			<input type="hidden" id="mainCard" value="" />
  		</c:if>
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
  				<a id="btnSinopicDownload" href="javascript:void(0);" onclick="startDownload();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">下载</a> 
  			</td>
  		</tr>
  	</table>
