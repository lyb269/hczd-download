<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <script type="text/javascript">
    		function startDownload(){
    			var rq = $('#p_month').datebox('getValue');
    			var main_card = $('#p_main_card').val();
    			var card_no =    $('#p_card_no').val();
    			var code =    $('#p_code').val();
    			if(rq == "" || rq == null){
    				alert("下载月份不能为空");
    			}else{
    				$.ajax({
	    			  type:'POST',
					  url: '<%=basePath%>card/petro/startDownload.htm',
					  data:'month='+rq+'&main_card='+main_card+'&card_no='+card_no+'&code='+code,
					  success: function(data){
					  }
					});
					$('#hczd_sys_win_edit').window('close');
					$('#dg').datagrid('reload');
    			}
    			
    		}
			
   		  </script>
   		<fieldset>
	        	<legend>中石油消费数据下载</legend>
	        	<table border="0"  style="float: left;">
	        		<tr>
	        			<input type="hidden" id="p_main_card" value="${main_card}" name="p_main_card"/>
	        			<td>主卡卡号：</td>
	        			<td colspan="3">
							${main_card}
	        			</td>
	        		</tr>
	        		<tr>
	        			<td>下载月份：</td>
	        			<td><input id="p_month" name="p_month" type="text" class="easyui-datebox" required="required"
	        				data-options="formatter:function(date){return date.getFullYear()+'-'+((date.getMonth()+1)<10?'0'+(date.getMonth()+1):(date.getMonth()+1));}"></input>
						</td>
	        			<td>下载卡号：</td>
	        			<td><input id="p_card_no" name="p_card_no" type="text" /></td>
	        		</tr>
	        		<tr>
	        			<td>验证码：</td>
        				<td align="left" colspan="2">
        					<input type="text" id="p_code"/>
        					<a href="javascript:$('#yzm').attr('src','card/petro/getYzm.htm');">
        					<img id="yzm" name="yzm" style="text-align: center;" src="card/petro/getYzm.htm">
        					</a>
        				</td>
	        			<td>
	        				<a id="btnSinopicDownload" href="javascript:void(0);" onclick="startDownload();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">启动下载</a>
	        			</td>
	        		</tr>
	        		<tr>
	        		</tr>
	        	</table>
	      </fieldset>