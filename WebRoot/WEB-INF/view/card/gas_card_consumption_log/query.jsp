<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主卡信息列表</title>
    
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
			    url:'card/gas_card_consumption_log/ajax_list_mainCard.htm', 
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
			    	text:'启动下载',
					iconCls: 'icon-ok',
					handler: function(){
						if($('#dg').datagrid('getSelected')){
							var obj = $('#dg').datagrid('getSelected');
							if(obj.state==0 || obj.state ==4){
								hczd_sys.window.edit('选择下载条件','card/gas_card_consumption_log/toDownload.htm?id='+obj.id,600,200);
							}else{
								$.messager.alert('提示','当前项已经在下载中..');
							}
						}else{
							$.messager.alert('提示','请选择一项进行操作！');
						}
					}
				},'-',{
			    	text:'实时控制台',
					iconCls: 'icon-tip',
					handler: function(){
						if($('#dg').datagrid('getSelected')){
							var obj = $('#dg').datagrid('getSelected');
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
						window.open('card/gas_card_consumption_log/console_info.htm?cardNo='+obj.card_no+'&dateStr='+obj.dateStr,'newwindow'+obj.card_no,'height=500,width=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
						}else{
							$.messager.alert('提示','请选择一项进行操作！');
						}
					}
				},'-',{
			    	text:'下载缓存数据',
					iconCls: 'icon-ok',
					handler: function(){
						hczd_sys.window.edit('下载缓存数据','card/gas_card_consumption_log/list_cache_main_card_excel.htm',700,400);
					}
				},'-',{
					text:'帮助',
					iconCls: 'icon-help',
					handler: function(){alert('尚未添加帮助说明...');}
				}],
				selectOnCheck:true,
			    columns:[[  
			        {field:'id',title:'编号',width:50,checkbox:true},  
			        {field:'card_no',title:'主卡卡号',width:160},
			        {field:'supplier_name',title:'供应商',width:120},
			        {field:'state',title:'下载状态',width:50,formatter:function(value,rowindex,rowdata){
			        	if(value == 0){
			        		return '<span style="color:green;">正常</span>';
			        	}else if(value==1){	
			        		return '<span style="color:blue;">下载中..</span>';
			        	}else if(value==2){
			        		return '<span style="color:blue;">合并文件中..</span>';
			        	}else if(value==3){
			        		return '<span style="color:blue;">去除重复数据中..</span>';
			        	}else if(value==4){
			        		return '<span style="color:red;">下载失败</span>';
			        	}
			         }},
			        {field:'dateStr',title:'',width:150}
			    ]]  
			});
			
			//定时刷新下载状态
			setInterval("state()",10000); 
			
		});
		
		//搜索加载数据
		function search_data(){
			//设置分页时查询条件
			$('#dg').datagrid({
				queryParams: {
					name:$('#main_card_no').val()
				}
			});
			$('#dg').datagrid('load',{
				name:$('#main_card_no').val()
			});
		}
		function state(){
			$('#dg').datagrid('reload');
		}
	</script>
  </head>
  
  <body>
  	<div id="cc" class="easyui-layout" style="width:100%;height:100%; padding: 0;margin: 0;" fit="true">  
	  	 <div data-options="region:'north',title:'搜索',split:true,collapsible:false" style="height:80px;padding: 10px;">
	  	 	主卡卡号：<input type="text" value="" name="keyword" id="main_card_no" />
	  	 	<a id="btn" href="javascript:void(0);" onclick="search_data()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  	 </div>
	  	 <div data-options="region:'center'">
	  	 	<table id="dg" fit="true"></table>
	  	 </div>
  	</div>
  </body>
</html>
