<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Random"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
/* 这一句是测试用的 2017年11月9日 15:44:23 */
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//移植的代码 待确定结构后精简
 String color = "blue";
    if (request.getCookies()!=null) {
	    for(Cookie c : request.getCookies()){
		    if("color".equals(c.getName())){
			    color = c.getValue();
			    break;
			}
		}
	}
    String loginColor = "Cred";
    String sysFrame = "main";
    Cookie cookies[]=request.getCookies();
    Cookie sCookie;
    if(cookies!=null && cookies.length>0){
    	for(int i=0;i<cookies.length;i++){
    		sCookie=cookies[i];
    		if("loginColor".equals(sCookie.getName())){
    			loginColor = sCookie.getValue();
    		}else if("sysFrame".equals(sCookie.getName())){
    			sysFrame = sCookie.getValue();
    		}
    	}
    }
    if(loginColor==null || "".equals(loginColor) || "null".equals(loginColor)){
    	loginColor = "Cred";
    }
    if(sysFrame==null || "".equals(sysFrame) || "null".equals(sysFrame)){
    	sysFrame = "main";
    }
    MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
    %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${webPath}">
    
    <title><%= StringUtil.KillEmpty(mfSysCompanyMst.getSystemName(), "金融业务系统")%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="<%= StringUtil.KillEmpty(mfSysCompanyMst.getSystemName(), "金融业务系统")%>">
	<link rel="shortcut icon" type="image/x-icon" href="${webPath}/favicon.ico" />
	<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
	<script src="${webPath}/themes/login/js/jquery.easing.1.3.js" type="text/javascript" language="javascript"></script>
	<script src="${webPath}/UIplug/userAgent/userAgent.js" language="javascript" type="text/javascript"></script>
	<!--bootstap库	-->
	<script src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js" language="javascript" type="text/javascript"></script>
	<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
	<!-- placeholder兼容 -->
	<script type="text/javascript" src="${webPath}/component/include/jquery.placeholder.min.js"></script>
	
	<link rel="stylesheet" href="${webPath}/themes/login/css/entor.css" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/factor.css" />
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="${webPath}UIplug/html5shiv/html5shiv.min.js"></script>
      <script src="${webPath}UIplug/html5shiv/respond.min.js"></script>
    <![endif]-->
    
	<!-- logo 文字特效  -->
<%-- 	<script src="${webPath}themes/login/js/jquery.fittext.js"></script> --%>
<%-- 	<script src="${webPath}themes/login/js/jquery.lettering.js"></script> --%>
<%-- 	<script src="${webPath}themes/login/js/jquery.textillate.js"></script> --%>
	
	<!--引用icomoon字体图标-->
	<link href="${webPath}/UIplug/iconmoon/style.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		.form-control::-webkit-input-placeholder {
			color: #fff;
		}
		.form-control:-ms-input-placeholder {/* IE10+ */
			color: #fff;
		}
		.form-control:-moz-placeholder {  /* Firefox4-18 */
			color: #fff;
		}
		.form-control::-moz-placeholder { /*  Firefox19+ */
			color: #fff;
		}
		body{
			FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#1ae3ed,endColorStr=#089BA3); /*IE 6 7 8*/ 
			
			background: -ms-linear-gradient(right top, #1ae3ed,  #089BA3);        /* IE 10 */
		
			background:-moz-linear-gradient(right top,#1ae3ed,#089BA3);/*火狐*/ 
		
			background:-webkit-gradient(linear, 100% 0%, 0% 100%,from(#1ae3ed), to(#089BA3));/*谷歌*/ 
			
			background: -webkit-gradient(linear, 100% 0%, 0% 100%, from(#1ae3ed), to(#089BA3));      /* Safari 4-5, Chrome 1-9*/
			
			background: -webkit-linear-gradient(right top, #1ae3ed, #089BA3);   /*Safari5.1 Chrome 10+*/
			
			background: -o-linear-gradient(right top, #1ae3ed, #089BA3);  /*Opera 11.10+*/
		}
		iframe{
			height:225px;
		}
		.star{position: absolute; top: 0; left: 0; width: 100%; height: 100%;}
		.star>div{position: absolute; }
		.star>div:nth-child(5n){ width:2px; height: 2px; border-radius: 2px; background:#ddd; -webkit-animation:shine 1s linear 1s infinite; animation:shine 1s linear 1s infinite;}
		.star>div:nth-child(5n+1){ width:4px; height: 4px; border-radius: 4px; background:#fff; -webkit-animation:shine 1s linear 2s infinite; animation:shine 1s linear 2s infinite;}
		.star>div:nth-child(5n+2){ width:2px; height: 2px; border-radius: 2px; background:#fff; -webkit-animation:shine 1s linear 3s infinite; animation:shine 1s linear 3s infinite;}
		.star>div:nth-child(5n+3){ width:4px; height: 4px; border-radius: 4px; background:#fff; -webkit-animation:shine 1s linear 1.5s infinite; animation:shine 1s linear 1.5s infinite;}
		.star>div:nth-child(5n+4){ width:1px; height: 1px; border-radius: 2px; background:#fff; -webkit-animation:shine 1s linear 2.5s infinite; animation:shine 1s linear 2.5s infinite;}
		.star>div:nth-child(1){top: 20%; left: 80%;}
		.star>div:nth-child(2){top: 30%; left: 20%;}
		.star>div:nth-child(3){top: 50%; left: 40%;}
		.star>div:nth-child(4){top: 70%; left: 50%;}
		.star>div:nth-child(5){top: 10%; left: 60%;}
		.star>div:nth-child(6){top: 20%; left: 30%;}
		.star>div:nth-child(7){top: 30%; left: 10%;}
		.star>div:nth-child(8){top: 40%; left: 50%;}
		.star>div:nth-child(9){top: 60%; left: 10%;}
		.star>div:nth-child(10){top: 10%; left: 50%;}
		.star>div:nth-child(11){top: 40%; left: 30%;}
		.star>div:nth-child(12){top: 70%; left: 20%;}
		.star>div:nth-child(13){top: 20%; left: 40%;}
		.star>div:nth-child(14){top: 40%; left: 70%;}
		.star>div:nth-child(15){top: 60%; left: 60%;}
		.star>div:nth-child(16){top: 20%; left: 40%;}
		.star>div:nth-child(17){top: 50%; left: 95%;}
		.star>div:nth-child(18){top: 70%; left: 20%;}
		.star>div:nth-child(19){top: 20%; left: 40%;}
		.star>div:nth-child(20){top: 65%; left: 15%;}
		.star>div:nth-child(21){top: 35%; left: 55%;}
		.star>div:nth-child(22){top: 75%; left: 65%;}
		.star>div:nth-child(23){top: 25%; left: 25%;}
		.star>div:nth-child(24){top: 45%; left: 90%;}
		.star>div:nth-child(25){top: 25%; left: 75%;}
		.star>div:nth-child(26){top: 75%; left: 75%;}
		.star>div:nth-child(27){top: 45%; left: 65%;}
		.star>div:nth-child(28){top: 25%; left: 35%;}
		.star>div:nth-child(29){top: 65%; left: 55%;}
		.star>div:nth-child(30){top: 55%; left: 45%;}
		.star>div:nth-child(31){top: 75%; left: 75%;}
		.star>div:nth-child(32){top: 25%; left: 25%;}
		.star>div:nth-child(33){top: 35%; left: 85%;}
		.star>div:nth-child(34){top: 35%; left: 45%;}
		.star>div:nth-child(35){top: 15%; left: 25%;}
		.star>div:nth-child(36){top: 55%; left: 45%;}
		.star>div:nth-child(37){top: 75%; left: 55%;}
		.star>div:nth-child(38){top: 25%; left: 55%;}
		.star>div:nth-child(39){top: 55%; left: 65%;}
		.star>div:nth-child(40){top: 65%; left: 25%;}
		@-webkit-keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
		@keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
	</style>
  </head>
  <body style="min-height: 530px;">
    <script type="text/javascript">
  //logo特效
 /* function logoTextChange(){
       $('.logotext').fitText(1,{minFontSize:24}).textillate({in: {effect: 'flipInX'}});
       $('.welcomeText').fitText(1, {maxFontSize: 16}).textillate({initialDelay: 1000, in: {effect: 'fadeIn'}});
   }  */
    $(function(){
    
	    $(".loginFormDiv").css({padding:"5px", height:"10px"}).animate({padding:"35px", height: '274px',opacity: 'show'}, 600, function(){
	    	$("#IdInput").focus();
	    });
         if($("#hasErrors").length>0) {
		    	var actionmsg ="";
		    	
		    	var errorMsgs = '${errorMsgs}';
				if(errorMsgs!==undefined&&errorMsgs!=null&&errorMsgs!=""){
					var errorMsgsArray =  eval('(' + errorMsgs + ')');
					$.each(errorMsgsArray,function(index,msg){
						if(actionmsg===undefined){
							actionmsg=msg;
						}else{
							actionmsg+=msg;
						}
					});
				}
				if(actionmsg!=""){
		            $("#loginInfo").text(actionmsg);
		            $("#loginInfo").css("display","block");
		            var opNo = "${opNo}";
		            var passwordhash = "${passwordhash}";
		            if($.trim(actionmsg).indexOf("密码错误")!=-1){
		            	setTimeout("animateDom('.PwdInputDiv')",1000);
		                $("#IdInput").val(opNo);
		                $("#opNo").val(opNo);
						$("#PwdInput").val(passwordhash);
					}else{
		            	setTimeout("animateDom('.IdInputDiv')",1000);
		                if($.trim(actionmsg).indexOf("无法登录")!=-1) {	//用户已失效或被注销
		                	$("#IdInput").val(opNo);
			                $("#opNo").val(opNo);
							$("#PwdInput").val(passwordhash);
		                }else {											//用户不存在或是
			               	$("#IdInput").val(opNo);
							$("#PwdInput").val(passwordhash);
	              	}
	              }
	          }
	       }
 	 });
  	
 	function getImg(fileName){//初始化使用
 		var src="";
 		if(fileName==""||fileName==null){//还未上传图片
 			
 		}else{//已上传图片
 			src=webPath+"/MfSysCompanyMs/viewImage?uploadFileName="+fileName+"&type=loginLogo&rundom="+Math.random();;//返回流
 		}
 		return src;
 	}
  /*   function checkExist(obj){
    	dwr.engine.setAsync(false);
    	SysUserDwr.checkExist(obj.value,function(data){
    		if(data!=null){
    			obj.value = data.opName;
    			$("#opNo").val(data.opNo);
    		}else{
    			obj.value =null;
    			$("#opNo").val(null);
    		}
    	})
    } */
    </script>
    <div class="star">
                        <!-- 10 -->
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <!-- 20 -->
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <!-- 30 -->
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <!-- 40 -->
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                </div>
    <div class="col-md-4 col-sm-3 col-xs-1"></div>
    <div class="col-md-4 col-sm-6 col-xs-10" style="margin: 8% auto 0 auto; min-width: 460px;">
	    <div style="margin:7% auto;text-align: center;"> 
		    <div class="logo-div" style="margin-bottom: 35px;">
		    	<img src = "${webPath}/<%= mfSysCompanyMst.getLoginPageLogoImg() %>" style="max-width: 200px; max-height:100px;">
		    	<div style="display: inline-block;letter-spacing:2px;font-size: 26px;color: white;" id="systemName"><%= mfSysCompanyMst.getSystemName() %></div>
		    </div>
			<!-- 初始时隐藏+高度10px；通过动画达到274px+渐显 -->
		    <div class="loginFormDiv" style="margin: 0 15px;padding:35px; background-color: #109AA4;height: 10px; display: none;">
				<div class="login-div" style="">
					<form action="/mfTrenchUser/trench" name="LoginForm" method="post">
						<div class="form-group IdInputDiv" style="position: relative;margin-bottom: 0px;">
							<input type="text" class="form-control" style="background: #0A7279;border: none;color: #fff;height: 40px;padding-left: 34px;" placeholder="账户" id="IdInput" name="opName" autocomplete="off">
							<span class="glyphicon glyphicon-user form-control-feedback" style="left:0px;top:4px;font-size:16px;color:white;"></span>
							<input name="opNo" type="hidden" id="opNo" value=""/>
							<div style="height: 20px;color: #f34d00;" id="opname-error" class="error-div"></div>
						</div>
						<div class="form-group PwdInputDiv" style="position: relative;margin-bottom: 0px;">
							<input type="password" class="form-control"  style="background: #0A7279;border: none;color: #fff;height: 40px;padding-left: 34px;"  placeholder="密码" id="PwdInput" name="passwordhash" autocomplete="off"> 
							<span class="glyphicon glyphicon-lock form-control-feedback" style="left:0px;top:4px;font-size:16px;color:white;"></span>
							<div style="height: 20px;color: #f34d00;" id="password-error" class="error-div"></div>
						</div>
						<div class="form-group">
							<input type="button" id="logonBtn" class="form-control" value="登录" style="background: #00F0FF;border: none;height: 40px;font-size: 16px;letter-spacing: 5px;" onclick="chklogon();"> 
						</div>
						<!--手机扫码登陆备用-->
						<%-- 						
						<div style="text-align: right;"><span class="erweima-tips" onclick="changeLoginType();">扫码登录更安全</span></div>
						 --%>
						<!-- 微信登录 -->
						<div style="text-align: right; display:none">
						<a id="toWxLogin" style="cursor:pointer;text-decoration:none;color:#fff;" class="iconfont" title="微信登录">
							<!-- <a style="text-decoration:none;color:#fff;" href="https://open.weixin.qq.com/connect/qrconnect?appid=wx5cb0d93ea0e9df3a&redirect_uri=http://oauth.mftcc.cn/oauth/weixin/scf/login&response_type=code&scope=snsapi_login#wechat_redirect"  target="_blank" class="iconfont" title="微信登录"> -->
								<img style="width:30px;"  src="${webPath}/themes/factor/images/icon32_wx_logo.png"/> 微信
							</a>
						</div>
						<!-- 增加cwView支持，可以直接调转到财务界面 -->
						<input type="hidden" value="view"   id="sysFrame" name="frame"/>
					</form>
					<input type="hidden" id="CodeInput" class="form-control validate" name="code">
				</div>
				<div id="wx-login-div" style="display: none;margin-top: -30px;height: 250px">
					<div id="wx_login_container"  style="height: 225px"></div>
					<a id="toPwdLogin" style="cursor:pointer; float:right;margin-right:20px; text-decoration:none;color:#fff;" class="iconfont" title="密码登录" >账号密码登录</a>
				</div>
				<div class="erweima-div" style="margin:auto; width: 138px;display: none;">
					<!-- <img src="themes/factor/images/erweima.png"> -->
					<div style="background-color: white;background: url('${webPath}/themes/factor/images/erweima.png') no-repeat;height: 128px;width: 128px;border: 10px solid white;"></div>
					<div style="margin-left: 15px;margin-top: 20px;color: white;font-size: 12px;letter-spacing: 1px;cursor: pointer;" onclick="changeLoginType();">
						使用账号登录
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-4 col-sm-3 col-xs-1"></div>
    <script type="text/javascript">
	function submitForm(evt){
		//IE & Firefox兼容处理
		var evt = evt ? evt : (window.event ? window.event : null);
		if(evt.keyCode == 13){
			chklogon();
		};
	}
	function changeLoginType(){
		if($(".login-div").is(":hidden")){
			$(".login-div").show();
			$(".erweima-div").hide();
		}else{
			$(".login-div").hide();
			$(".erweima-div").show();
		}
	};
	//登录
	function chklogon(){
		$("#logonBtn").attr("disabled", true).val("正在登录...");
		if (doChkLogon() === false) {
			$("#logonBtn").removeAttr("disabled").val("登录");
		}
	}
	function doChkLogon () {
		//TODO 此处增加校验
		var pname = document.getElementById('opNo').value;
		var pwd= document.getElementById('PwdInput').value;
		//var codeInput = document.getElementById('CodeInput').value;
		
		if(!pname || pname==""){
			/* $("#loginInfo").css("display","block");
			$("#loginInfo").text("请输入用户名!"); */
			$("#opname-error").text("请输入用户名!");
			document.getElementById('IdInput').focus();
			animateDom(".IdInputDiv");
			return false;
		}
		$("#opname-error").text("");
		if(!pwd || pwd==""){
			/* $("#loginInfo").css("display","block");
			$("#loginInfo").text("请输入密码!"); */
			$("#password-error").text("请输入密码!");
			document.getElementById('PwdInput').focus();
			animateDom(".PwdInputDiv");
			return false;
		}
		$("#password-error").text("");
		$(".error-div").empty();
//		if(!codeInput || codeInput==""){
//			$("#loginInfo").css("display","block");
//			$("#loginInfo").text("请输入验证码!");
//			document.getElementById('CodeInput').select();
	///		return false;
//		}
		
// 		if($.isIE()&&window.browser['version']=="8.0"){
// 			document.getElementById("sysFrame").value = "viewIE8";
// 		}else{
// 			document.getElementById("sysFrame").value = "view";
// 		}
/* 		document.getElementById("sysFrame").value = $("#frameSelect").attr("value"); */
		document.LoginForm.submit();
	}
	function changeCodeImg(){
		document.getElementById("codeImage").src = "<%=basePath %>CodeImage.jsp?rdm=" + Math.random();
		document.getElementById("CodeInput").value="";
		document.getElementById("CodeInput").focus();
	}

	//------------------初始化-----------------------
		// 用户名密码输入框鼠标事件。
		$(".input_div").each(function(){
		//	var $inputDiv = $(this);
			var $inputInput = $(this).find("input");
			var $inputLabel = $(this).find("label");
			
			if($inputInput.val()=="" || $inputInput.val()==null){
				$inputLabel.show();
			}else{
				$inputLabel.hide();
			}
				
			$inputInput.focus(function(){
				$(this).attr("class","login_input_focus");
				$inputLabel.hide();
			}).blur(function(){
				$(this).attr("class","login_input");
				if($inputInput.val()=="" || $inputInput.val()==null){
					$inputLabel.show();
				}
			});
			
			$(".input_code").focus(function(){
				$(this).attr("class","input_code_focus");
			}).blur(function(){
				$(this).attr("class","input_code");
			});
		});

         function animateDom(dom){
            $(dom).stop()
                    .animate({ left: "-15px" }, 70).animate({ left: "15px" }, 70)
                    .animate({ left: "-5px" }, 70).animate({ left: "5px" }, 70)
                    .animate({ left: "0px" }, 50, function(){
	                    $(dom).find("input:visible").focus();
                    });
        }
        
        //检查用户是否存在
        function checkExist(){
        	//用户名是否重复
        	var isRepeat = false;
      	  	var isExists = false;
      	  	var isTrenchDisable = true;//用户所在渠道商状态是否正常，true正常 false终止或暂停
      	  	var isUserDisable = true;//用户状态是否为激活状态，true激活false未激活

			jQuery.ajax({
				url : webPath+'mfTrenchUser/checkTrenchUserExistAjax',
				type : "POST",
				dataType : "json",
				data : {
					"val" : $("#IdInput").val()
				},
				async : false,
				success : function(data) {
					if (data != null && data != "") {
						var flag = data.flag;
						var webCusLineReg = data.webCusLineReg;
						if (flag == "success") {
							$("#IdInput").val(webCusLineReg.cusNickname);
							$("#opNo").val(webCusLineReg.cusAccount);
							isExists = true;
						} else if (flag == "trenchDisable") {
							isExists = true;
							isTrenchDisable = false;
							$("#IdInput").val("");
							$("#opNo").val(null);
						} else if (flag == "userDisable") {
							isExists = true;
							isUserDisable = false;
							$("#IdInput").val("");
							$("#opNo").val(null);
						} else if (flag == "repeat") {
							isRepeat = true;
							$("#IdInput").val("");
							$("#opNo").val(null);
						} else if (flag == "no") {
							$("#IdInput").val("");
							$("#opNo").val(null);
						}
					} else {
						$("#IdInput").val("");
						$("#opNo").val(null);
					}
				}
			});

			if (!isExists) {
				if(isRepeat){
					$("#opname-error").text("存在重复用户名！请使用手机号或者用户号进行登录");
				}else{
					$("#opname-error").text("用户不存在！");
				}
				animateDom(".IdInputDiv");
				return false;
			} else {
				if(!isTrenchDisable){
					$("#opname-error").text("用户所在渠道商已终止或暂停！请联系渠道商");
					return false;
				}
				if(!isUserDisable){
					$("#opname-error").text("用户未激活！");
					return false;
				}
				$("#opname-error").text("");
				return true;
			}
        }
        
        
        $(function(){
        	// 给登录表单绑定回车事件。
        	$("#IdInput").bind("keydown",function(e){
        		if (e.keyCode == 13) {
	        		if (this.value == "") {
	        			return false;
	        		}
					$("#PwdInput").focus();
				}
        	}).blur(function(){
        		if ($("#IdInput").val()) {
					checkExist();
        		}
			});
        	$("#PwdInput").bind("keydown",function(e){
        		if (e.keyCode == 13) {
	        		if (this.value == "") {
	        			return false;
	        		}
	        		if ($("#IdInput").val() == "") {
	        			$("#IdInput").focus();
	        			return false;
	        		}
					chklogon();
				}
        	});
        	 $(function(){   
       	        window.addEventListener("storage", function(event){    
       	         	alert(event.key + "=" + event.newValue);    
       	        });     
       	    });  
        	/* $(".dropdown-menu").find("a").bind("click",function(){
        		var val =$(this).attr("value");
        		var btn = $(this).parent().parent().parent().find(".setValue");
        		btn.attr("value",val);
        		btn.html($(this).html());
        	}); */
        	$("#toWxLogin").click(function(){
        		 var obj = new WxLogin({
                     id:"wx_login_container", 
                     appid: "wx5cb0d93ea0e9df3a", 
                     scope: "snsapi_login", 
                     redirect_uri: "https://oauth.mftcc.cn/oauth/weixin/scf/login",
                     state: "",
                     style: "white",
                     href: "https://oauth.mftcc.cn/oauth/css/wxlogin.css"
                   });
        		 $("#wx-login-div").show();
        		 $(".login-div").hide();
        	})
        	$("#toPwdLogin").click(function(){
        		 $("#wx-login-div").hide();
        		 $(".login-div").show();
        	})
        	/*  <a style="text-decoration:none;color:#fff;" href="https://open.weixin.qq.com/connect/qrconnect?appid=wx5cb0d93ea0e9df3a&redirect_uri=http://oauth.mftcc.cn/oauth/weixin/scf/login&response_type=code&scope=snsapi_login#wechat_redirect"  target="_blank" class="iconfont" title="微信登录"> */
        });
	</script>
  </body>
</html>
