<%-- <%@page import="app.component.sys.entity.SysOpAreaConfig"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String theme = "Cred";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>融资租赁系统</title>
<link href="<%=basePath %>layout/menu/themes/main.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>layout/menu/themes/theme_<%=theme %>/Css/sysUI_<%=theme %>.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>UIplug/jqueryUI/smoothness/jquery.ui.theme.css"type="text/css" rel="stylesheet" />
<link href="<%=basePath %>UIplug/jqueryUI/smoothness/jquery.ui.core.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>UIplug/jqueryUI/smoothness/jquery.ui.dialog.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>UIplug/jqueryUI/smoothness/jquery.ui.button.css" type="text/css" rel="stylesheet" />
<script src="<%=basePath %>layout/menu/js/jquery-1.8.0.min.js" language="javascript" type="text/javascript" ></script>
<script src="<%=basePath %>UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js" language="javascript" type="text/javascript"></script>
<%	String[] ary = (String[])session.getAttribute("roleNo");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ary.length; i++){
		sb. append(ary[i]);
		}
		String roleNo = sb.toString();
String modeStr = (String)session.getAttribute("modeStr");
String cifDealerNo = (String)session.getAttribute("dealerCifNo");
//System.out.println(cifDealerNo);
	if(!(roleNo.indexOf("SH004")>-1)&&!(roleNo.indexOf("SH003")>-1)){
 %>
<jsp:include page="talkjs.jsp"></jsp:include>
<%} %>
<script type="text/javascript">
var talkStatus= "";
	$(document).ready(function(){
		var headHeight = 59;
		var iframeHeight;
		var flag=true;
		//通过鼠标事件来控制显隐性
		$("#header_switch").mouseenter(function(){
			$("#header_switch").removeClass("header_show header_hide sysMenuLeft");
			if(flag){
				$("#header_switch").addClass("header_show");
			}else{
				$("#header_switch").addClass("header_hide");
			}	
		});
		
		
		$("#header_switch").toggle(function(){
			$(".header").animate({top:'-34'},450,function(){
			$("#header_switch").addClass("sysMenuLeft");
			});
			iframeHeight = $("#contFrame").height();
			$("#header_switch").removeClass("header_show sysMenuLeft");
			$("#header_switch").animate({top:'36'});
			$("#contFrame").animate({marginTop:'-34',height:iframeHeight+34},450);
			flag=false;
		},function(){
			$(".header").animate({top:'0'},450,function(){
			$("#header_switch").addClass("sysMenuLeft");
			});
			iframeHeight = $("#contFrame").height();
			$("#header_switch").removeClass("header_hide sysMenuLeft");
			$("#header_switch").animate({top:'36'});
			$("#contFrame").animate({marginTop:'0',height:iframeHeight-34},450);
			flag=true;
		});
		
	    $("#header_switch").mouseleave(function(){
			$("#header_switch").addClass("sysMenuLeft");
			$("#header_switch").removeClass("header_show header_hide");	
		});
		<!--end-->
		
//		$("#sysSet").hover(function(){
//			$(".sysSetList").slideDown("fast");
//		}, function() {
//			$(".sysSetList").fadeOut("fast");
//		});
		$("#sysSet").mouseenter(function(){
			$(".sysSetList").slideDown("fast");
		});
		$("#sysSet").mouseleave(function(){
			$(".sysSetList").fadeOut("fast");
		});
		
		$(".sysQuit").click(function(){
			window.top.location.href = "${webPath}/sysLogin/logout.action";
		});
		
		$(".sysMenuBody").width($(".header").width() - 491);
		$("#contFrame").attr("height", ($(window).height()-62)+"px");
		$(window).resize(function(){
			$("#contFrame").attr("height", ($(window).height()-62)+"px");
			$(".sysMenuBody").width($(".header").width() - 491);
		});
		//-----------------------一级菜单特效--------------------------
		var w = $(".sysMenuBody").width();
		var show_num = Math.ceil((w-100)/100);
		var len = $("div.sysMenuBody").find("a").size();
		$("#menuScrol").attr("class","menuScrolNone");// 按钮隐藏
		$("#menuScrolBack").attr("class","menuScrolBackNone");
			
		for ( var i = 1; i <= len - show_num; i++) {
			$("#el" + (show_num + i)).hide();// 根据<a>标签的id 大于第show_num个的标签 隐藏
		}
		var s = 1;
		var j = len - show_num;
		var page = 1;
		var maxpage = Math.ceil(len/show_num);
		if(maxpage>1){
			$("#menuScrol").attr("class","menuScrol");// 按钮显示
		}
		
		$("#menuScrol").click(function() { 
			if($("#menuScrol").attr("class")=="menuScrolNone"){
				return;
			}
			for ( var i = 1; i <= len; i++) {
				$("#el" + i).show(0);
			}
			$(".sysMenuBody a").animate({left : "-=" + (100*show_num-100) +"px"},500);
			page ++;
			if(page>=maxpage){
				$("#menuScrol").attr("class","menuScrolNone");
			}
			$("#menuScrolBack").attr("class","menuScrolBack");
			for ( var i = 1; i <= len - (show_num*page-page+1); i++) {
				$("#el" + ((show_num*page-page+1) + i)).hide();// 根据<a>标签的id 大于第show_num个的标签 隐藏
			}
		});
				
		$("#menuScrolBack").click(function() { 
			if($("#menuScrolBack").attr("class")=="menuScrolBackNone"){
				return;
			}
			for ( var i = 1; i <= len; i++) {
				$("#el" +  i).show(0);
			}
			$(".sysMenuBody a").animate({left : "+=" + (100*show_num-100) +"px"},500);
			page --;
			if(page<=1){
				$("#menuScrolBack").attr("class","menuScrolBackNone");
			}
			$("#menuScrol").attr("class","menuScrol");
			for ( var i = 1; i <= len - (show_num*page-page+1); i++) {
				$("#el" + ((show_num*page-page+1) + i)).hide(0);// 根据<a>标签的id 大于第show_num个的标签 隐藏
			}
				
		 });
		
		$(".sysMenuBody a").click(function(){
			$(".sysMenuBody").find("a").removeAttr("class");
			$(this).attr("class","sysMenuBodyDown");
			$("#contFrame").focus();
		});
			
		//-------------系统皮肤设置-------------------------
 		$("#skin").click(function(){
			$( "#dialog-skin" ).dialog("open");
		});
					
		$("#dialog-skin").dialog({
			autoOpen: false,
			resizable: false,
			width:370,
			height:320,
			modal: true,
			buttons: {
				"确定": function() {
					$(this).dialog("close");
					$(".skin_color li").each(function(){
						if($(this).attr("class") && $(this).attr("class").indexOf("current")>=0){
							window.location.href="updateColor.action?op.color="+$(this).attr("alt");
							return false;
						}
					});
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		//------------------密码修改----------------------------
		function showPwdMsg(str){
			$("#pwd-msg-note").text(str);
			$("#pwd-msg-note").show("normal",function(){
				setTimeout(function(){
					$("#pwd-msg-note").hide("slow",null);
				},2000);
			});
		}
		$("#dialog-pwd").dialog({
			autoOpen: false,
			resizable: false,
			width:370,
			height:220,
			modal: true,
			buttons: {
				"确定": function() {
					var opwd_2013 = document.getElementById("opwd_2013").value;
					var npwd_2013 = document.getElementById("npwd_2013").value;
					var dnpwd_2013 = document.getElementById("dnpwd_2013").value;
					if(opwd_2013 == ""){
						showPwdMsg("您输入的原密码为空,不能修改!");
						return false;
					}
					if(npwd_2013.length < 6){
						showPwdMsg("新密码不能少于6个字符!");
						return false;
					}
					if(opwd_2013 == npwd_2013){
						showPwdMsg("新密码不能和原密码相同!");
						return false;
					}
					if(npwd_2013 != dnpwd_2013){
						showPwdMsg("您输入的新密码和确认密码不一致,不能修改!");
						return false;
					}
					$.ajax({
				   		type:"POST",
				   		url:webPath+"/TblOrgUserAction_changePwdByAjax.action",
				   		data:"changePWInfo="+opwd_2013+"/"+npwd_2013,
				   		success:function(data){
				   			if(data != null && data != "" && data != "undefined" && data == "changeOK"){
				   				alert("密码修改成功,请使用新密码重新登录!");
				   				window.top.location.href = "${webPath}/creditapp/logout.action";
				   			} else {
				   				showPwdMsg(data);
				   			}
				   		}
				   	});
					
				},
				"取消": function() {
					$(this).dialog("close");
				}
			}
		});
		$("#changepwd").click(function(){
			var msg = '<table style="position:relative; left:40px;top:20px"> ' +
			  '<tr><td width="80" style="font:12px   Verdana,   Geneva,   Arial,   Helvetica,   sans-serif;color:black">原密码</td><td><input id="opwd_2013" type="password" maxlength="20" size="25"/></td></tr>' +
			  '<tr><td style="font:12px   Verdana,   Geneva,   Arial,   Helvetica,   sans-serif;color:black">新密码</td><td><input id="npwd_2013" type="password" maxlength="20" size="25"/></td></tr>' +
			  '<tr><td style="font:12px   Verdana,   Geneva,   Arial,   Helvetica,   sans-serif;color:black">确认新密码</td><td><input id="dnpwd_2013" type="password" maxlength="20" size="25"/></td></tr>' +
			  '<tr><td colspan="2" align="center"><span id="pwd-msg-note" style="display: none;color:#ff3424;text-align:center;"></span></td></tr></table>';
			$("#dialog-pwd").html(msg);
			$("#dialog-pwd").dialog("open");
		});	
		
		//------------------系统锁屏----------------------------
		var locked = false;
		var lockPwd = null;
		function showLockMsg(str){
			$("#lock-msg-note").text(str);
			$("#lock-msg-note").show("normal",function(){
				setTimeout(function(){
					$("#lock-msg-note").hide("slow",null);
				},2000);
			});
		}
		function func_lock(){
			$(document.body).append("<div class='ui-widget-overlay' style='z-index:20;' id='lock-bg-div'></div>");
			$("#lock-name").text("输入解锁密码:");
			$("#lockPwdInput").val("");
			locked = true;
		}
		function func_unlock(){
			$("#lock-bg-div").remove();
			$("#lock-name").text("输入锁屏密码:");
			$("#lockPwdInput").val("");
			locked = false;
		}
		$("#dialog-lock").dialog({
			autoOpen: false,
			resizable: false,
			closeOnEscape: false,
			width:370,
			height:170,
			modal: true,
			close:function(){
				if(locked){
					setTimeout(function(){
						if(locked){
							$("#dialog-lock").dialog("open");
						}
					},2000);
				}
			},
			buttons: {
				"确定": function() {
					var lockPwdInput = $("#lockPwdInput").val();
					if(locked){
						if(lockPwdInput && lockPwdInput.length>0){
							if(lockPwdInput==lockPwd){
								func_unlock();
								$("#dialog-lock").dialog("close");
							}else {
								showLockMsg("解锁密码不正确,请重新输入!");
							}
						}else {
							showLockMsg("请输入解锁密码!");
						}
					}else {
						if(lockPwdInput && lockPwdInput.length>0){
							lockPwd = lockPwdInput;
							locked = true;
							func_lock();
						}else {
							showLockMsg("请输入锁屏密码!");
						}
					}
				}
			}
		});
		$("#lockScreen").click(function(){
			var msg = '<table style="position:relative; left:40px;top:20px"> ' +
			  '<tr><td width="80" style="font:12px Verdana,Geneva,Arial,Helvetica,sans-serif;color:black" id="lock-name">输入锁屏密码:</td><td><input id="lockPwdInput" type="password" maxlength="20" size="25"/></td></tr>' +
			  '<tr><td colspan="2" align="center"><span id="lock-msg-note" style="display: none;color:#ff3424;text-align:center;"></span></td></tr></table>';
			$("#dialog-lock").html(msg);
			$("#dialog-lock").dialog("open");
		});	
		
		//----------------------------------------------------
		$("#dialog-msg").dialog({
			autoOpen: false,
			resizable: false,
			draggable: true,
			width:370,
			height:160,
			modal: true,
			position: {
				my: "center",
				at: "center",
				of: window,
				collision: "fit",
				// Ensure the titlebar is always visible
				using: function(pos) {
					var topOffset = $(this).css(pos).offset().top;
					if (topOffset < 0) {
						$(this).css("top", pos.top-topOffset);
					}
				}
			},
			buttons: {
				"关闭": function() {
					$(this).dialog( "close" );
				}
			}
		});
//-------------系统颜色选中事件-------------------------
		$(".skin_color li").click(function(){
			$(".skin_color li").removeClass();
			$(this).addClass("current");
		});
//-------------切换模式事件-------------------------
		$("#modeType").click(function(){
			var str = "";
			var data = "<%=modeStr%>";
			if(!data==""){
				if(data.indexOf(';',0)){
					var arr = data.split(';');
					for(var i=0;i<arr.length;i++){
						str += '<option value ="'+ arr[i].split("=")[0] + '">' + arr[i].split("=")[1] + '</option>';
					}
				}else{
					str += '<option value ="'+ data.split("=")[0] + '">' + data.split("=")[1] + '</option>';
				}
			}else{
				str = data;
			}
			
			var msg = '<table style="position:relative; left:10px;top:20px"> ' +
			  '<tr><td width="80" style="font:12px Verdana,Geneva,Arial,Helvetica,sans-serif;color:black" id="lock-name">选择模式</td><td><select id="ModeInput" class="login_input">' +
			  	str +
			  '</td></tr>' +
			  '<tr><td colspan="2" align="center"><span id="lock-msg-note" style="display: none;color:#ff3424;text-align:center;"></span></td></tr></table>';
			$("#dialog-mode").html(msg);
			$("#dialog-mode").dialog("open");
		});	
<%
if(!(roleNo.indexOf("SH004")>-1)&&!(roleNo.indexOf("SH003")>-1)){
%>
//-------------聊天用户列表展开-------------------------
		$(".chat_list>li").live("click",function(){
			$(".chat_list>li").removeAttr("class");
			$(this).addClass("current");
			var lititle = $(this).attr("title");
			var user = $.trim($(this).text());
			liclick(lititle,user);
		});
		
		$("#chat_search").focus(function(){
				$(this).addClass("chat_search_focus");
			}).blur(function(){
				$(this).removeAttr("class");
		});

		$(".chat > h1").toggle(
			function(){
				$(".chat_body").slideDown();
			},
			function(){
				$(".chat_body").slideUp();
				$(".talk_body").slideUp();
				$(".talk").hide("fast");
				talkStatus = "";
			}
		);
	<%}%>
		
	});
	
//-------------------------初始化结束-----------------------
</script>
</head>
<body scroll="no" >
<div id="dialog-skin" title="系统皮肤设置" style="display: none;">
	<ul class="skin_color">
		<li alt="blue"<%if("blue".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#004a8e;">经典蓝</a></li>
		<li alt="purple"<%if("purple".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#970086;">尊贵紫</a></li>
		<li alt="green"<%if("green".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#0f8d00;">苹果绿</a></li>
		<li alt="red"<%if("red".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#b60045;">浪漫红</a></li>
		<li alt="cyan"<%if("cyan".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#006a63;">沧桑绿</a></li>
		<li alt="orange"<%if("orange".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#aa3a00;">深桔红</a></li>
		<li alt="Cred"<%if("Cred".equals(theme)){%> class="current"<%} %>><a href="#" style="background:#7e2121;">中国红</a></li>
	</ul>
</div>
<div id="dialog-mode" title="切换模式" style="display: none;text-align:left;white-space:normal;width:370px;">
</div>
<div id="dialog-msg" title="系统提示" style="display: none;text-align:left;white-space:normal;width:370px;">
</div>
<div id="dialog-pwd" title="密码修改" style="display: none;text-align:left;white-space:normal;width:370px;">
</div>
<div id="dialog-lock" title="锁定屏幕" style="display: none;text-align:left;white-space:normal;width:370px;">
</div>
<div  class="header">
  <div class="sysLogo">
 	 <img src="<%=basePath %>layout/menu/themes/images/headerR_bg2.png" />
  </div>
   <div class="sysSet">
    <div class="user" id="user" title="当前日期:2015-12-18&#10;机构:东华软件&#10;机构号:0000&#10;操作员号:0000">
    <span class="ico_user"></span>&nbsp;您好：系统管理员
    </div>
	<div class="sysSetBtn" id="sysSet" title="系统设置">
	<ul class="sysSetList" >
		<li class="firstLi"></li>
		<!-- 
		<li><a href="javascript:void(0)" id="skin" style="border-top:none;"><span class="ico_skin"></span>系统换肤</a></li>
		 -->
		<li id="lockScreen"><a href="javascript:void(0)"><span class="ico_lock"></span>锁定屏幕</a></li>
		<li id="changepwd"><a href="javascript:void(0)"><span class="ico_pass"></span>修改密码</a></li>
		<li><a href="javascript:void(0)" style="border-bottom:none;" onclick="getHelp();"><span class="ico_help"></span>系统帮助</a></li>
		<li><a href="javascript:void(0)" style="border-bottom:none;" onclick="create_tools();"><span class="ico_help"></span>设置帮助</a></li>
		<li><a href="javascript:void(0)" style="border-bottom:none;" onclick="openSelect(this);"><span class="ico_help"></span>帮助等级</a></li>
		<li class="lastLi"></li>
	</ul>
	</div>
	<div class="sysQuit" title="退出系统"></div>
  </div>
  
  <div class="sysMenuLeft" id="header_switch"></div>
   <div class="sysMenu">
        <div class="sysMenuBody">
        <ul class="scrol" style="margin:0 5px; position:absolute; float:left; width:3000px;">
<%
   java.util.List sysMenuLev1List =(java.util.List)session.getAttribute("sysMenuLev1List");
   String menuName = "";
	 for(int i=0;i<sysMenuLev1List.size();i++){
	 	 app.component.sys.entity.SysMenu sysMenu= (app.component.sys.entity.SysMenu)sysMenuLev1List.get(i);
%>
	
	  <li style="float:left;">
   		<a<%if(i==0){ %> class="sysMenuBodyDown"<%} %> href="<%=basePath %>layout/menu/left.jsp?id=<%=sysMenu.getMenuNo() %>" id="el<%=i+1 %>" target="leftFrame"><div class="ico<%=sysMenu.getMenuNo() %>"></div><span><%=sysMenu.getMenuName() %></span></a>	
	</li>
	 <% } %>
	</ul>
	  <div class="menuScrolBack" id="menuScrolBack"></div>
	  <div class="menuScrol" id="menuScrol"></div>
        </div>
   </div>
</div>
<%if(!(roleNo.indexOf("SH004")>-1)&&!(roleNo.indexOf("SH003")>-1)){
 %>
<div class="chat">
	<h1>用户列表（<label id="sumusers"></label>）</h1>
    <div class="chat_body" >
		<ul class="chat_list">
			
		</ul>
		<div class="chat_search">
			<input type="text" maxlength="20" id="chat_search" onkeyup="dosearchuser()"/>
		</div>
	</div>
</div>

<div class="talk" id="talk" title="" style="display:none;">
    <div class="talk_body">
		<ul class="talk_list">
			<li height="30px" class="talk_title">
			<div class="title_lt">与&nbsp;<label id="talkobj"></label>&nbsp;聊天中</div>
			<div class="title_rt" onclick="queryMsg(this);"><a href="javascript:void(0);">消息记录</a></div>
			<div onclick="closeDia(this);" class="title_ct"></div>
			</li>
			<li height="169px">
				<table width="299px" height="169px" cellpadding="0" cellspacing="0">
				<tr height="169px"><td width="298px">
				<div class="talkDivShow"></div>
				</td></tr>
				<tr height="1px"></tr>
				</table>
			</li>
				<textarea name="messageInput" class="messageInput"></textarea> 
				<input name="sendbutton" type="button" value="发送" class="talk_button" />
				<input type="hidden" name="targetUser"/>
		</ul>
	</div>
</div>
<div class="noticeDiv" id="noticeDiv">
<label id="newMsgNum"></label>
</div>
<%} %>
	<iframe id="contFrame" name="contFrame" src="<%=basePath %>layout/menu/main20.jsp" width="100%" height="250px" frameborder="0" scrolling="none"/>
</body>
</html>
