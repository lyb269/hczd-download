function $importJS(path){
	document.write("<script type='text/javascript' src='"+path+"'></script>");
}
function $importCSS(path){
	document.write("<link rel='stylesheet' type='text/css' href='"+path+"'>");
}

$importCSS('jquery-easyui/themes/default/easyui.css');
$importCSS('jquery-easyui/themes/icon.css');
$importCSS('css/style.css');
$importJS('js/jquery-1.6.min.js');
$importJS('jquery-easyui/js/jquery.easyui.min.js');
$importJS('jquery-easyui/local/easyui-lang-zh_CN.js');

/**
 * 屏幕兼容性控制
 * */
var screen_width = screen.width;
var screen_height = screen.height;
var FileUploadProgress_Integral;
function getWidth(percent){ 
    return document.body.clientWidth*percent; 
} 


if (window.navigator.userAgent.indexOf("MSIE")>=1){
	var IE1024="";
	var IE800="";
	var IE1152="";
	var IEother="";

	ScreenWidth(IE1024,IE800,IE1152,IEother);
}else{
	if(window.navigator.userAgent.indexOf("Firefox")>=1){
		// 如果浏览器为Firefox
		var Firefox1024="";
		var Firefox800="";
		var Firefox1152="";
		var Firefoxother="";

		ScreenWidth(Firefox1024,Firefox800,Firefox1152,Firefoxother);
	}else{
		// 如果浏览器为其他
		var Other1024="";
		var Other800="";
		var Other1152="";
		var Otherother="";
		ScreenWidth(Other1024,Other800,Other1152,Otherother);
	}
}

function ScreenWidth(CSS1,CSS2,CSS3,CSS4){
	if ((screen.width == 1024) && (screen.height == 768)){
		setActiveStyleSheet(CSS1);
	}else{
		if ((screen.width == 800) && (screen.height == 600)){
			setActiveStyleSheet(CSS2);
		}else{
			if ((screen.width == 1152) && (screen.height == 864)){
				setActiveStyleSheet(CSS3);
			}else{
				setActiveStyleSheet(CSS4);
			}
		}
	}
}
function setActiveStyleSheet(title){ 
	  // document.getElementsByTagName("link")[0].href="style/"+title; 
	}

//处理没有图片的情况和图片查看
function img_oper(){
	$("img").each(function (i,e){
        var imgsrc = $(e).attr("src");
        $(e).css("cursor","pointer");
        $(e).load(function(){}).error(function() {
           $(e).attr("src","images/lack_pic.png");
        }) ;
    });
    $("img").click(function(){
		window.open('customer/customer/imgView.htm?file_path=' + $(this).attr("src"),'_blank');
	});
}

/**
 * 改良自定义控件
 * **/

var hczd_sys = {
		loader:{
			show:function(){
				var msg = arguments[0] ? arguments[0] : '正在处理，请稍候。。。';
				$("<div class=\"datagrid-mask\" style='z-index:10000;'></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
				$("<div class=\"datagrid-mask-msg\" style='z-index:10000;'></div>").html(msg).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
			},
			close:function(){
				$('.datagrid-mask').remove();
				$('.datagrid-mask-msg').remove();
			}
		},
		window:{
			/**新建窗体**/
			edit:function(){
				var title = arguments[0] ? arguments[0] : '添加';
				var url = arguments[1] ? arguments[1] : '#'; 
				var width = arguments[2] ? arguments[2] : 600; 
				var height = arguments[3] ? arguments[3] : 400; 
				var id = arguments[4] ? arguments[4] : 'hczd_sys_win_edit';
				$('body').append('<div id="' + id +'" data-options="iconCls:' + "'icon-edit'" +'"></div>');
				$('#' + id).window({
					title:title,
				    width:width,  
				    height:height, 
				    href:url,
				    modal:true
				});
			},//end add
			/**详细页面**/
			detail:function(){
				var title = arguments[0] ? arguments[0] : '详细';
				var url = arguments[1] ? arguments[1] : '#'; 
				var width = arguments[2] ? arguments[2] : 600; 
				var height = arguments[3] ? arguments[3] : 400; 
				var id = arguments[4] ? arguments[4] : 'hczd_sys_win_detail'; 
				$('body').append('<div id="' + id +'" data-options="iconCls:' + "'icon-tip'" +'"></div>');
				$('#' + id).window({
					title:title,
				    width:width,  
				    height:height, 
				    href:url,
				    modal:true
				});
			},
			/**创建frame**/
			editFrame:function (){
				var title = arguments[0] ? arguments[0] : '详细';
				var url = arguments[1] ? arguments[1] : '#'; 
				var width = arguments[2] ? arguments[2] : 600; 
				var height = arguments[3] ? arguments[3] : 400; 
				var id = arguments[4] ? arguments[4] : 'hczd_sys_win_detail'; 
				$('body').append('<div id="' + id +'" data-options="iconCls:' + "'icon-tip'" +'">'+hczd_sys.window.createFrame(url)+'</div>');
				$('#' + id).window({
					title:title,
				    width:width,  
				    height:height, 
				    //href:url,
				    modal:true,
				    onClose:function(){
				    	$('#' + id).remove();
				    }
				});

			},
			/***创建iframe内容**/
			createFrame : function (url) {
				var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
						+ '" style="width:100%;height:100%;"></iframe>';
				return s;
			}
		},//end window
		widgets : {
			changeTwoDecimal:function(){
				var f_x = arguments[0];
				if(isNaN(f_x)){
					return "0.00";
				}
				var f_x = Math.round(f_x*100)/100;
				return f_x;
			}
		},
		Arabia_to_Chinese : function(numberValue){
			var numberValue=new String(Math.round(numberValue*100)); // 数字金额  
			var chineseValue=""; // 转换后的汉字金额  
			var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字  
			var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位  
			var len=numberValue.length; // numberValue 的字符串长度  
			var Ch1; // 数字的汉语读法  
			var Ch2; // 数字位的汉字读法  
			var nZero=0; // 用来计算连续的零值的个数  
			var String3; // 指定位置的数值  
			if(len>15){  
			alert("超出计算范围");  
			return "";  
			}  
			if (numberValue==0){  
			chineseValue = "零元整";  
			return chineseValue;  
			}  
			String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值  
			for(var i=0; i<len; i++){  
			String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值  
			if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){  
			if ( String3 == 0 ){  
			Ch1 = "";  
			Ch2 = "";  
			nZero = nZero + 1;  
			}  
			else if ( String3 != 0 && nZero != 0 ){  
			Ch1 = "零" + String1.substr(String3, 1);  
			Ch2 = String2.substr(i, 1);  
			nZero = 0;  
			}  
			else{  
			Ch1 = String1.substr(String3, 1);  
			Ch2 = String2.substr(i, 1);  
			nZero = 0;  
			}  
			}  
			else{ // 该位是万亿，亿，万，元位等关键位  
			if( String3 != 0 && nZero != 0 ){  
			Ch1 = "零" + String1.substr(String3, 1);  
			Ch2 = String2.substr(i, 1);  
			nZero = 0;  
			}  
			else if ( String3 != 0 && nZero == 0 ){  
			Ch1 = String1.substr(String3, 1);  
			Ch2 = String2.substr(i, 1);  
			nZero = 0;  
			}  
			else if( String3 == 0 && nZero >= 3 ){  
			Ch1 = "";  
			Ch2 = "";  
			nZero = nZero + 1;  
			}  
			else{  
			Ch1 = "";  
			Ch2 = String2.substr(i, 1);  
			nZero = nZero + 1;  
			}  
			if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上  
			Ch2 = String2.substr(i, 1);  
			}  
			}  
			chineseValue = chineseValue + Ch1 + Ch2;  
			}  
			if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”  
			chineseValue = chineseValue + "整";  
			}  
			return chineseValue;  
		},
		form:{
			//区域控件
			area:function(){
				var id = arguments[0] ? arguments[0] : 'sel_area';
				var level = arguments[1] ? arguments[1] : 2;
				var extension = arguments[2] ? arguments[2] : '';
				var urlprovince = arguments[3] ? arguments[3] : ('common/dict_province/ajax_list.htm' + (extension ? '?type=1' : ''));
				var urlcity = arguments[4] ? arguments[4] : ('common/dict_city/ajax_list.htm' + (extension ? '?type=1&' : '?'));
				var urldistrict = arguments[5] ? arguments[5] : ('common/dict_district/ajax_list.htm' + (extension ? '?type=1&' : '?'));
				if(level > 3){
					$('#' + id).after('<input id="' + id + '_sale_input_select" />');
				}
				if(level > 2){
					$('#' + id).after('<select id="' + id + '_sale_district_select"><option value="选择区域">选择区域</option></select>');
				}
				//添加城市
				if(level > 1){
					$('#' + id).after('<select id="' + id + '_sale_city_select"><option value="选择城市">选择城市</option></select>');
				}
				$('#' + id).after('<select id="' + id + '_sale_province_select"><option value="选择省份">选择省份</option></select>');
				//省份下拉框
				$('#' + id + '_sale_province_select').combobox({  
				    url:urlprovince,  
				    valueField:'provinceId',  
				    textField:'provinceName',
				    onSelect:function(event){
				    	if(level > 1){
				    		$('#' + id + '_sale_city_select').combobox('reload',urlcity + 'province_id=' + $(this).combobox('getValue'));
				    		if(level > 1){
				    			$('#' + id + '_sale_city_select').combobox('reset');
				    		}
				    		if(level > 2){
				    			$('#' + id + '_sale_district_select').combobox('reset');
				    		}
				    		if(level > 3){
				    			$('#' + id + '_sale_input_select').val('');
				    		}
				    	}
				    	//选择省份
				    	$('#' + id).val($(this).combobox('getText') + ' ');
				    }
				});
				//城市
				if(level > 1){
					$('#' + id + '_sale_city_select').combobox({  
					    valueField:'cityId',  
					    textField:'cityName',
					    onSelect:function(event){
					    	//选择城市
					    	$('#' + id).val($('#' + id).val() + $(this).combobox('getText') + " ");
					    	if(level > 2){
					    		$('#' + id + '_sale_district_select').combobox('reload',urldistrict + 'city_id=' + $(this).combobox('getValue'));
					    		$('#' + id + '_sale_district_select').combobox('reset');
					    	}
					    	if(level > 3){
				    			$('#' + id + '_sale_input_select').val('');
				    		}
					    }
					});
				}
				//区域
				if(level > 2){
					$('#' + id + '_sale_district_select').combobox({  
					    valueField:'districtName',  
					    textField:'districtName',
					    onSelect:function(event){
					    	//选择区域
					    	$('#' + id).val($('#' + id).val() + $(this).combobox('getText') + ' ');
					    	if(level > 3){
				    			$('#' + id + '_sale_input_select').val('');
				    		}
					    }
					});
				}
				//详细地址
				if(level > 3){
					$('#' + id + '_sale_input_select').change(function(){
						//选择区域
				    	$('#' + id).val($('#' + id).val() + $(this).val());
					});
				}
			},//end area
		fileUpload:function(){
			var id = arguments[0] ? arguments[0] : 'sel_area';
			var basePath = arguments[1] ? arguments[1] : '';
			var filePath = arguments[2] ? arguments[2] : '';
			var fileName = arguments[3] ? arguments[3] : '';
			//首次启动定时监听程序
			if(!FileUploadProgress_Integral){
				FileUploadProgress_Integral = setInterval('FileUploadProgress("' + basePath + '","'+id+'")', 1000);
			}
			$('#' + id).append('<iframe style="display: none;" name="t_' + id + '_id"></iframe>' +
			    	'<form action="' + basePath + 'file/file_upload/upload_file.htm" method="post" target="t_' + id + '_id" enctype="multipart/form-data">' +
		    		'<input type="file" id="fld_' + id + '" name="fileUpload" /><div style="border: 1px black solid;width:100px;height:10px;border:"><div id="div_'+id+'" style="width:0%;height:100%;background-color:#fc0;"></div><label id="progress_">&nbsp;</label></div>' +
		    		'<input type="hidden" name="filePath" value="' + filePath + '" />' +
		    		'<input type="hidden" name="fileName" value="' + fileName + '" />' +
		    	'</form>');
			/**
			 * 添加失去焦点方法
			 */
			$('#fld_' + id).live('change',function(){
				$("#div_"+id).css("width","0%");
				$('#progress_' + id).html("0%");
				if($(this).val()){
					$(this).parent('form').submit();
					//设置获取进度数
					$(this).parent('form')
					.find('[id^="progress_"]')
					.attr('id','progress_1');
				}
			});
		}//end fileUpload
	}
};

/**
 * 
 * 文件上传监听方法
 */

function FileUploadProgress(basePath,id){
	$.post(basePath + 'file/file_upload/ajax_upload_status.htm',function(data){
		var t = eval(data);
		if(t){
			//创建显示进度条的位置
			var pro = $('#progress_' + t.pItems);
			//当前进度
			var p = parseInt((t.pBytesRead / t.pContentLength ) * 100);
			if($(pro).html()){
				$(pro).html(p + '%');
				$("#div_"+id).css("width",String(p)+"%");
				if(p == '100'){
					//$(pro).attr('id','progress_');
					$("#div_"+id).css("background-color","green");
					clearInterval(FileUploadProgress_Integral);
				}
			}
		}
	});
}//end FileUploadProgress

/**
 * 权限控制
 * @param uri
 * @param menutext
 */
function authority(uri,menutext,id){
	$.ajax({
		type:'post',
		url:'authority/power/ajax_check_auth.htm',
		data:{
			uri:uri
		},
		dataType:'json',//服务器返回的数据类型
		success:function(data){
			if(!data){
				//销毁菜单
				var dpanel = $('#'+id).datagrid('getPanel');
				var toolbar = dpanel.children("div.datagrid-toolbar");
				var cbtn = null;
				if (typeof menutext == "number") {
					cbtn = toolbar.find("td").eq(menutext).find('span.l-btn-text');
				} else if (typeof menutext == "string") {
					cbtn = toolbar.find("span.l-btn-text:contains('" + menutext + "')[innerHTML='" + menutext + "']");
				}
				if (cbtn && cbtn.length > 0) {
					var sep = cbtn.closest('td').next().find("div.datagrid-btn-separator");
					if(sep && sep.length > 0){
						sep.closest('td').remove();
					}
					cbtn.closest('td').remove();
				}
				
			}
		},
		error:function(){
			
		}
	});	
}

