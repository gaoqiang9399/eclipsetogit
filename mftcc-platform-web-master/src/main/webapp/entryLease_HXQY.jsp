<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Random"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%@ page import="config.YmlConfig" %>
<%@ page import="app.base.SpringUtil" %>
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
	if(loginColor==null || loginColor.equals("") || loginColor.equals("null")){
		loginColor = "Cred";
	}
	if(sysFrame==null || sysFrame.equals("") || sysFrame.equals("null")){
		sysFrame = "main";
	}
	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
	System.out.println(mfSysCompanyMst.getLoginBackgroundImg());
	System.out.println(mfSysCompanyMst.getLoginPageLogoLocation());
	YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
	String bzLogo=ymlConfig.getUpload().get("coreLogoFilePath");
	String bzLogin=ymlConfig.getUpload().get("coreLoginFilePath");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="${webPath}">

	<title>核心企业平台</title>
	<META NAME="GENERATOR" Content="Microsoft FrontPage 4.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="shortcut icon" type="image/x-icon" href="${webPath}/favicon.ico" />
	<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
	<script src="${webPath}/themes/login/js/jquery.easing.1.3.js" type="text/javascript" language="javascript"></script>
	<script src="${webPath}/UIplug/userAgent/userAgent.js" language="javascript" type="text/javascript"></script>
	<!--bootstap库	-->
	<script src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js" language="javascript" type="text/javascript"></script>
	<!-- <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script> -->
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
			color: #888888;
		}
		/*.form-control:-ms-input-placeholder {!* IE10+ *!*/
		/*color: #fff;*/
		/*}*/
		/*.form-control:-moz-placeholder {  !* Firefox4-18 *!*/
		/*color: #fff;*/
		/*}*/
		/*.form-control::-moz-placeholder { !*  Firefox19+ *!*/
		/*color: #fff;*/
		/*}*/
		body{
			background-image: url("${webPath}/<%=bzLogin%>");
			background-size:cover;
		}
		iframe{
			height:225px;
		}

		@-webkit-keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
		@keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
	</style>
</head>
<body style="min-height: 530px;">
<div style="display: none;">
	<OBJECT id="InfoSecNetSign1" codeBase="NetSign.dll#version=1,8,23,11" data="DATA:application/x-oleobject;BASE64,xDi5YpBBN0+M8KkrCpHMdwADAACgRwAA/wIAAA==" classid="clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77" VIEWASTEXT width="693" height="29"></OBJECT>
</div>
<script type="text/javascript">
	//logo特效
	/* function logoTextChange(){
          $('.logotext').fitText(1,{minFontSize:24}).textillate({in: {effect: 'flipInX'}});
          $('.welcomeText').fitText(1, {maxFontSize: 16}).textillate({initialDelay: 1000, in: {effect: 'fadeIn'}});
      }  */
	$(function () {
		$(".loginFormDiv").css({padding: "5px", height: "10px"}).animate({padding: "35px", height: '274px', opacity: 'show'}, 600, function () {
			var errorMsgs = '${errorMsgs}';
			if (errorMsgs !== undefined && errorMsgs != null && errorMsgs != "") {
				var actionmsg = "";
				var errorMsgsArray = eval('(' + errorMsgs + ')');
				$.each(errorMsgsArray, function (index, msg) {
					actionmsg += msg;
				});
				if (actionmsg != "") {
					$("#password-error").text(actionmsg);
					$("#password-error").css("display", "block");
					setTimeout(function(){
						animateDom('#password-error');
					} ,200);
				}
			} else {
				$("#IdInput").focus();
			}
		});
	});

	function getImg(fileName){//初始化使用
		var src="";
		if(fileName==""||fileName==null){//还未上传图片

		}else{//已上传图片
			src=webPath+"/MfSysCompanyMs/viewImage?uploadFileName="+fileName+"&type=loginLogo&rundom="+Math.random();;//返回流
		}
		return src;
	}
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
<div class="col-md-4 col-sm-6 col-xs-10" style="margin: 0 auto; min-width: 500px;">
	<div style="margin: 35% auto;text-align: center;">
		<div class="logo-div" >
			<div class="logo-img">
				<img src ="${webPath}/<%=bzLogo%>" style="max-width: 160px; max-height:80px;">
				<br>
				<span style="margin-top:25px;font-size: 25px;color:#020521">核心企业平台</span>
			</div>
		</div>
		<div class="loginFormDiv" style="margin: 30px 15px;padding:35px; background-color:rgba(80, 67, 67, 0.22);height:10px;">
			<div class="login-div">
				<form action="${webPath}/sysCoreLogin/userLogin" name="LoginForm" method="post">
					<div class="form-group IdInputDiv" style="position: relative;margin-bottom: 0px;">
						<input type="text" class="form-control" style="background:#E5EBF1;font-size:15px; border: none;color:#212121;,height: 40px;padding-left: 34px;" placeholder="请输入账户" id="IdInput" name="opNo" autocomplete="off" value="${opNo}">
						<span class="glyphicon glyphicon-user form-control-feedback" style="left:0px;top:3px;font-size:16px;color:#71AE92;"></span>
						<div style="height: 20px;color: #f34d00;" id="opname-error" class="error-div"></div>
					</div>
					<div class="form-group PwdInputDiv" style="position: relative;margin-bottom: 0px;">
						<input type="password" class="form-control"  style="background:#E5EBF1; font-size:15px;border: none;color:#212121;,height: 40px;padding-left: 34px;"  placeholder="请输入密码" id="PwdInput" name="passwordhash" autocomplete="off" value="${passwordhash}">
						<span class="glyphicon glyphicon-lock form-control-feedback" style="left:0px;top:3px;font-size:16px;color:#71AE92 "></span>
						<div style="position: relative;height: 28px;color: #f34d00;" id="password-error" class="error-div"></div>
					</div>
					<input type="hidden" id="dnValue" name="dnValue">
					<div class="form-group">
						<input type="button" id="logonBtn" class="form-control" value="登录" style="background:#006E39;border: none;height: 40px;font-size: 16px;letter-spacing: 5px;color:#E7EEE9" onclick="chklogon();">
					</div>
					<!--手机扫码登陆备用-->
					<%--
                    <div style="text-align: right;"><span class="erweima-tips" onclick="changeLoginType();">扫码登录更安全</span></div>
                     --%>
					<!-- 微信登录 -->
					<!-- <div style="text-align: right;">
                    <a id="toWxLogin" style="cursor:pointer;text-decoration:none;color:#fff;" class="iconfont" title="微信登录">
                        <a style="text-decoration:none;color:#fff;" href="https://open.weixin.qq.com/connect/qrconnect?appid=wx5cb0d93ea0e9df3a&redirect_uri=http://oauth.mftcc.cn/oauth/weixin/scf/login&response_type=code&scope=snsapi_login#wechat_redirect"  target="_blank" class="iconfont" title="微信登录">
                            <img style="width:30px;"  src="themes/factor/images/icon32_wx_logo.png"/> 微信
                        </a>
                    </div> -->
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
		$("#opname-error").text("");
		$("#password-error").text("");
		//TODO 此处增加校验
		var pname = document.getElementById('IdInput').value;
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
		if(!pwd || pwd==""){
			/* $("#loginInfo").css("display","block");
            $("#loginInfo").text("请输入密码!"); */
			$("#password-error").text("请输入密码!");
			document.getElementById('PwdInput').focus();
			animateDom(".PwdInputDiv");
			return false;
		}
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

	// 根据code值查询信息
	function  getMsgByCode(code){
		if(code=='-10000'){return '没有可用内存';}
		if(code=='-10001'){return '输入参数为空';}
		if(code=='-10002'){return 'Base64解码失败';}
		if(code=='-10003'){return 'Base64编码失败';}
		if(code=='-10004'){return '用户取消';}
		if(code=='-10005'){return '找不到证书';}
		if(code=='-10006'){return '缺少数据';}
		if(code=='-10007'){return '数据类型错误';}
		if(code=='-10008'){return '消息类型错误';}
		if(code=='-10009'){return '消息错误';}
		if(code=='-10010'){return '证书的签名错误';}
		if(code=='-10011'){return '证书过期';}
		if(code=='-10012'){return '证书已废止';}
		if(code=='-10013'){return '证书不可信任';}
		if(code=='-10014'){return '上级证书未发现';}
		if(code=='-10015'){return '没有找到匹配私钥';}
		if(code=='-10016'){return '证书解析错误';}
		if(code=='-10017'){return '证书签名非法';}
		if(code=='-10018'){return '打开证书存储区错误';}
		if(code=='-10019'){return '获得CSP失败';}
		if(code=='-10020'){return '签名失败';}
		if(code=='-10021'){return '验签失败';}
		if(code=='-10022'){return '加密失败';}
		if(code=='-10023'){return '解密失败';}
	}

	function changeCodeImg(){
		document.getElementById("codeImage").src = webPath+"CodeImage.jsp?rdm=" + Math.random();
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

	$(function(){
		// 给登录表单绑定回车事件。
		$("#IdInput").bind("keydown",function(e){
			if (e.keyCode == 13) {
				if (this.value == "") {
					animateDom(".IdInputDiv");
					return false;
				}
				$("#opname-error").text("");
				$("#PwdInput").focus();
			}
		});
		$("#PwdInput").bind("keydown",function(e){
			if (e.keyCode == 13) {
				if ($("#IdInput").val() == "") {
					$("#IdInput").focus();
					animateDom(".IdInputDiv");
					return false;
				}
				if (this.value == "") {
					animateDom(".PwdInputDiv");
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
