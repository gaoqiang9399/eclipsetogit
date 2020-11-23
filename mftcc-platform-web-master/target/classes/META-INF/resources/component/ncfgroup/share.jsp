<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<%
	//String agenciesUid = (String) request.getAttribute("agenciesUid");
	String agenciesUid=request.getParameter("agenciesUid");
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>分享</title>
<style>
	body{
		background:url("/factor/component/ncfgroup/img/share.png") no-repeat;
		background-size:100% 100%;
		background-attachment: fixed;
	}
	.share{
		position:relative;
		top:43%;
	}
	.shengqing-btn{
		width:80%;
		height:40px;
		margin-left:10%;
		border-radius:8px;
		border:0px;
		margin-bottom:20px;
		font-size:26px;
		color:#ffffff;
		background-color:#feae0f;
		font-size:22px;
	}
	.applyAgree{
		text-align:center;
		font-size:18px;
	}
	.phone-input,.name-input{
		color:#acacac;
		font-size:18px;
		padding-left:5%;
		width:75%;
		height:40px;
		margin-left:10%;
		border-radius:8px;
		border:0px;
		margin-bottom:15px;
	}
	.code-input{
		color:#acacac;
		padding-left:5%;
		font-size:18px;
		/* font: normal 22px Arial; */
		width:75%;
		height:40px;
		margin-left:10%;
		border-radius:8px;
		border:0px;
		margin-bottom:15px;
	}
	.getcode-input{
		display:inline-block;
		font-size:18px;
		/* font: normal 22px Arial; */
		position:absolute;
		right:13%;
		height:40px;
		line-height:40px;
		color:#025dc6;
	/* 	font: 400 13.3333px Arial; */
	}
	.getcode-image{
		display:inline-block;
		position:absolute;
		right:10%;
		height:40px;
		line-height:40px;
		color:#025dc6;
		width:100px;
	}
</style>
</head>

<body ontouchstart>
	<div class="share">
		<div class="phone">
			<input type="tel" class="phone-input" name="" id="phone" value="" placeholder="请输入手机号" />
		</div>
		<div class="name">
			<input type="text" class="name-input" name="" id="name" value="" placeholder="请输入姓名" />
		</div>
		<div class="code" id="randomImage">
			<input type="text" class="code-input" name="" id="random" value="" placeholder="请输入右边数字" />
			<input type="image" id="randomCode" width="100px" class="getcode-image" src="" onclick="getRandom();" title=" "/>
		</div>
		<div class="code">
			<input type="text" class="code-input" name="" id="code" value="" placeholder="验证码" />
			<span id="getcodeBtn" class="getcode-input">获取验证码</span>
			<!-- <button id="getcodeBtn" class="code-btn">获取验证码</button> -->
		</div>
		<button id="bdBtn" class="shengqing-btn">立即申请</button>
		<div class="applyAgree">
			<span style="color:#ffffff">申请即同意</span>
			<a style="color:#feae0f">《阿尔法信服务协议》</a>
		</div>
	</div>
	<script type="text/javascript">
	var agenciesUid='<%=agenciesUid%>';
	$(function() {
		//获取图片验证码
	    getRandom();
		//获取验证码
		$("#getcodeBtn").on("click", function(){
			var phone = $("#phone").val();
			var random=$("#random").val();
			if(""==phone){
				$.toast("手机号不能为空");
				return;
			}
			if(!checkMobileOnly(document.getElementById("phone"))){
				$.toast("请输入正确手机号码");
				return;
			}
			if(""==random){
				$.toast("图片验证码不能为空", "text");
				return;
			}
			
			$.ajax({
				   type: "POST",
				   async:false,
				   url:webPath+'/webCusLineReg/getWxPhoneVerifyAjax',
				   data: {
					   tel:phone,
					   random:random
				   },
				   dataType:"json",
				   success: function(data){
					   console.log( "Data: " + data );
					   if(data.errorCode=="00000"){
					   		//获取验证码倒计时
						    disableWait(60,document.getElementById("getcodeBtn"),"秒后可重发");
						   $.toast("验证码已发送");
					   }
				   }
			});
		});
		
		//绑定按钮
		$("#bdBtn").on("click", function(){
			var phone1 = $("#phone").val();
			var code1 = $("#code").val();
			var cusName = $("#name").val();
			var random=$("#random").val();
			if(""==phone1){
				$.toast("手机号不能为空", "text");
				return;
			}
			if(!checkMobileOnly(document.getElementById("phone"))){
				$.toast("请输入正确手机号码", "text");
				return;
			}
			if(""==cusName){
				$.toast("姓名不能为空", "text");
				return;
			}
			if(""==random){
				$.toast("请输入图片验证码", "text");
				return;
			}
			if(""==code1){
				$.toast("验证码不能为空", "text");
				return;
			}
			$.ajax({
				   type: "POST",
				   async:false,
				   url: webPath+'/webCusLineReg/shareWxLoginAjax',
				   data: {
					   tel:phone1,
					   type:"2",
					   verifyNum:code1,
					   agenciesUid:agenciesUid,
					   cusName:cusName,
					   random:random
				   },
				   dataType:"json",
				   success: function(data){
					   console.log( data.errorCode);
					   if(data.errorCode=="00000"){
						    if (CheckIsIOS()) {
						        //如果是IOS
						        window.location.href="http://url.cn/50fIHn4";
						    }
						    if (CheckIsAndroid()) {
						        //如果是Android
						        window.location.href="http://sj.qq.com/myapp/detail.htm?apkName=com.mftcc.wangxinapp";
						    } 
					   }else{
						   $.toast(data.errorMsg, "cancel");
					   }
				   },
				   error:function(){
					   $.toast("申请失败error!", "cancel");
				   }
			});
		});
		
	});
	function disableWait(t, obj, waitMessage) {
	    var v = obj.innerText;
	    var i = setInterval(function() {
	        if (t > 0) {
                obj.innerText = (--t) + waitMessage;
	            obj.disabled = true;
	        } else {
	            window.clearInterval(i);
	            obj.innerText = v;
	            obj.disabled = false;
	        }
	    }, 1000);
	}
	function CheckIsAndroid() {
	    var browser = {
	        versions: function () {
	            var u = navigator.userAgent, app = navigator.appVersion;
	            return { //移动终端浏览器版本信息 
	                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
	                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
	                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器 
	                iPad: u.indexOf('iPad') > -1, //是否iPad 
	            };
	        }(),
	    }
	    if (browser.versions.android){
	    	return true;
	    }else{
	    	return false;
	    }
	}

	function CheckIsIOS() {
	    var browser = {
	        versions: function () {
	            var u = navigator.userAgent, app = navigator.appVersion;
	            return { //移动终端浏览器版本信息 
	                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
	                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
	                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器 
	                iPad: u.indexOf('iPad') > -1, //是否iPad 
	            };
	        }(),
	    }
	    if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
	        return true;
	    }else{
	      return false;
	    };
	};
	/**
	 * 校验手机号码
	 * @param telno 要检验的数据
	 */
	function checkMobileOnly(ele) {
		telno = trim(ele.value);
		if (typeof(telno) == 'undefined' || telno == null || telno == "") {
			return true;
		}
		var patrnmobile = /^\d{11}$/;
		if (!patrnmobile.test(telno)) {
			ele.focus();
			ele.value = "";
			return false;
		}
		ele.value = telno;
		return true;
	}
	/* 获取图片验证码 */
	function getRandom(){
		var d = new Date();
		var time=d.getTime();
	 	var url=webPath+'/webCusLineReg/getRandom?'+time;
		$("#randomCode").attr("src",url);
	}
	</script>
</body>

</html>