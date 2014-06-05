<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml"><head id="Head1"><meta http-equiv="Content-Type" content="text/html; charset=GBK"><meta http-equiv="pragma " content="no-cache"><meta http-equiv="Cache-Control " content="no-cache,must-revalidate"><meta http-equiv="expires " content="Wed,26 Feb 1978 08:21:57 GMT "><meta name="DESCRIPTION" content="福建省高速公路闽通卡服务中心"><meta name="keywords" content="高速,公路,闽通卡,福建,"><meta http-equiv="x-ua-compatible" content="ie=7"><title>
	闽通卡网上营业厅
</title><link href="css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/Marquee.js"></script>
<script type="text/javascript" src="js/mootools.js"></script>
<script type="text/javascript" src="Scripts/jquery-1.4.1.js"></script>
<script type="text/javascript" src="js/imageMenu.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

        $("#menu li").prepend("<span></span>"); //Throws an empty span tag right before the a tag

        $("#menu li").each(function () { //For each list item...
            var linkText = $(this).find("a").html(); //Find the text inside of the a tag
            $(this).find("span").show().html(linkText); //Add the text in the span tag
        });

        $("#menu li").hover(function () {	//On hover...
            $(this).find("span").stop().animate({
                marginTop: "-47" //Find the span tag and move it up 40 pixels
            }, 250);
        }, function () { //On hover out...
            $(this).find("span").stop().animate({
                marginTop: "0" //Move the span back to its original state (0px)
            }, 250);
        });

        /*	2nd example	*/
        $("#menu li").prepend("<span></span>"); //Throws an empty span tag right before the a tag

        $("#menu li").each(function () { //For each list item...
            var linkText = $(this).find("a").html(); //Find the text inside of the a tag
            $(this).find("span").show().html(linkText); //Add the text in the span tag
        });

        $("#menu li").hover(function () {	//On hover...
            $(this).find("span").stop().animate({
                marginTop: "-47" //Find the span tag and move it up 40 pixels
            }, 250);
        }, function () { //On hover out...
            $(this).find("span").stop().animate({
                marginTop: "0" //Move the span back to its original state (0px)
            }, 250);
        });
    });
</script>




    
</head>

<body >
<form method="post" action="http://www.fjetc.com/UserChecking.aspx?CheckUrl=1%e2%88%9dOWtPWUZab1hzand4alMydlpKVXBSRFFsU1B2eDV4SlVJeTF1VHh3a0NCVERjV2FFNG5pWGRJdENCNnFoZXRxc08rVEVkNVZ4enA5NEd1ZW9heW5QUlFCNzNjRFdUcE9JSmVlSStuQnlEWFRLU0VKbDNMNG9nUT09" id="form1">
	<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>
	
	<table>
		<tr>
			<td>登录类型</td>
			<td>
				<select name="ctl00$MainContent$logintype">
					<option selected="selected" value="-1">请选择登录类型</option>
					<option value="0">客户ID登录</option>
					<option value="1">客户证件登录</option>
					<option value="2">闽通卡卡号登录</option>
					<option value="3">车牌号码登录</option>
				</select>
			</td>
			<td>账户</td>
			<td>
				<input name="ctl00$MainContent$txtNumber" type="text" id="MainContent_txtNumber">
			</td>
			<td>密码</td>
			<td>
				<input name="ctl00$MainContent$txtpassWord" type="password" id="MainContent_txtpassWord">
			</td>
			<td colspan="2">
				<input  type="submit" value="tijiao"/>
			</td>
		</tr>
	</table>
</form>
</body></html>
