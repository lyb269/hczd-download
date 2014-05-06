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
							hczd_sys.window.edit('选择下载条件','card/gas_card_consumption_log/toDownload.htm?id='+obj.id,600,200);
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
							hczd_sys.window.edit('实时控制台','card/gas_card_consumption_log/console_info.htm?cardNo='+obj.card_no,600,600); 
							/* window.open("card/gas_card_consumption_log/console_info.htm?cardNo="+obj.card_no,'','width=200,height=100') ;  */
						}else{
							$.messager.alert('提示','请选择一项进行操作！');
						}
					}
				},'-',{
			    	text:'下载缓存数据',
					iconCls: 'icon-ok',
					handler: function(){
						hczd_sys.window.edit('下载缓存数据','card/gas_card_consumption_log/list_cache_main_card_excel.htm',600,400);
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
			        		return '<span style="color:red;">下载中..</span>';
			        	}else if(value==2){
			        		return '<span style="color:red;">合并文件中..</span>';
			        	}else if(value==3){
			        		return '<span style="color:red;">去除重复数据中..</span>';
			        	}
			         }}
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
