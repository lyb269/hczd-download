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
		$(function(){
			/**
			*数据显示
			**/
			$('#dg').datagrid({  
			    url:'access/access_card/ajax_list.htm', 
			    striped:true,
			    loadMsg:'亲，正在加载ing...',
			    idField:'id',
			    pagination:true,
			    singleSelect:true,
			    fitColumns:true,
			    rownumbers:true,
			    pageSize:15,
			    pageList:[10,15,20,25,30,50],
			    toolbar: [{
			    	text:'单张下载',
					iconCls: 'icon-ok',
					handler: function(){
						if($('#dg').datagrid('getSelected')){
							var obj = $('#dg').datagrid('getSelected');
							hczd_sys.window.edit('选择下载条件','access/access_card/toDownload.htm?id='+obj.id,500,150);
						}else{
							$.messager.alert('提示','请选择一项进行操作！');
						}
					}
				},'-',{
			    	text:'全部下载',
					iconCls: 'icon-ok',
					handler: function(){
							hczd_sys.window.edit('选择下载条件','access/access_card/toDownload.htm',500,150);
					}
				},'-',{
			    	text:'实时控制台',
					iconCls: 'icon-tip',
					handler: function(){
						/* 	hczd_sys.window.edit(obj.card_no+'实时控制台','card/gas_card_consumption_log/console_info.htm?cardNo='+obj.card_no,600,600);  */
							/* var title = obj.card_no+'实时控制台';
							var url = 'card/gas_card_consumption_log/console_info.htm?cardNo='+obj.card_no; 
							var width = 600; 
							var height = 400; 
							var id = obj.card_no;
							$('body').append('<div id="' + id +'" data-options="iconCls:' + "'icon-edit'" +'"></div>');
							$('#' + id).window({
								title:title,
							    width:width,  
							    height:height, 
							    href:url,
							    modal:false
							}); */
						window.open('access/access_card/console_info.htm','height=500,width=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
					}
				},'-',{
					text:'帮助',
					iconCls: 'icon-help',
					handler: function(){alert('尚未添加帮助说明...');}
				}],
				selectOnCheck:true,
			    columns:[[  
			        {field:'id',title:'编号',width:50,checkbox:true},  
			        {field:'card_no',title:'卡号',width:160},
			        {field:'vehicle_no',title:'车牌号',width:150},
			        {field:'registerdate',title:'注册日期',width:150},
			        {field:'balance',title:'余额',width:150},
			        {field:'customer_name',title:'客户',width:150},
			        {field:'haspwd',title:'是否包含密码',width:150}
			    ]]  
			});
			
			
		});
		
		//搜索加载数据
		function search_data(){
			//设置分页时查询条件
			$('#dg').datagrid({
				queryParams: {
					card_no:$('#card_no').val(),
					vehicle_no:$('#vehicle_no').val()
				}
			});
			$('#dg').datagrid('load',{
				card_no:$('#card_no').val(),
				vehicle_no:$('#vehicle_no').val()
			});
		}
	</script>
  </head>
  
  <body>
  	<div id="cc" class="easyui-layout" style="width:100%;height:100%; padding: 0;margin: 0;" fit="true">  
	  	 <div data-options="region:'north',title:'搜索',split:true,collapsible:false" style="height:80px;padding: 10px;">
	  	 	卡号：<input type="text" value="" name="keyword" id="card_no" />
	  	 	车牌号：<input type="text" value="" name="keyword" id="vehicle_no" />
	  	 	<a id="btn" href="javascript:void(0);" onclick="search_data()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  	 </div>
	  	 <div data-options="region:'center'">
	  	 	<table id="dg" fit="true"></table>
	  	 </div>
  	</div>
  </body>
</html>
