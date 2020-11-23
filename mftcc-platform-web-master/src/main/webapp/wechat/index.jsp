<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/pub_wx.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>登录</title>
<style type="text/css">
	*{
		font-family: '微软雅黑';
	}
	.weui_msg{
		padding-top: 0px;
	}
	.weui_msg .weui_icon_area{
		color:#fff;
		padding:30px 0;
		margin-bottom:0px;
		background-color: #3282cb;
	}
	.weui_msg .weui_msg_desc{
		color:#fff;
	}
	.enterLogo {
	    display: block;
	    margin: 0 auto;
	    width: 74px;
	    height: 64px;
	    background: url(images/enterLogo.png) no-repeat;
	}
	.content-padded{
		padding: 15px;
	}
	.login-btn{
		margin: 15px 0px;
	}
</style>
</head>
<body>
<div class="weui_msg">
  <div class="weui_icon_area">
  	<span class="enterLogo"></span>
  	<h2 class="weui_msg_title">东华软件股份公司</h2>
    <p class="weui_msg_desc">欢迎回来，请登录！</p>
  </div>
  <div class="weui_text_area">
  <wx:wxformTag property="login0001"/>
  		<%-- <div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">用户名</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" id="account" type="text" value="0001" placeholder="请输入用户名">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">密码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="password" id="password" value="000000" placeholder="请输入密码">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">验证码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input id="codeInput" class="weui_input" type="text" placeholder="请输入验证码">
				</div>
				<div class="weui_cell_ft">
					<img title="不区分大小写" id="codeImage" src="<%=basePath%>CodeImage.jsp" onclick="changeCodeImg();" />
				</div>
			</div>
		</div> --%>
  </div>
<!--   <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="javascript:;" id="login" class="weui_btn weui_btn_primary">登录</a>
    </p>
  </div> -->
</div>
	<script>
		<%-- function changeCodeImg(){
			document.getElementById("codeImage").src = "<%=basePath %>CodeImage.jsp?rdm=" + Math.random();
			document.getElementById("codeInput").value="";
			document.getElementById("codeInput").focus();
		} --%>
		$(function(){
			var accountBox = document.getElementById('account');
			var passwordBox = document.getElementById('password');
			var codeInputBox = document.getElementById('codeInput');
			$("#login").bind('click', function() {
				if (accountBox.value=="") {
					$.alert("用户名不能为空！", "提示", function() {
						accountBox.focus();
						});
					return
				}
				if (passwordBox.value=="") {
					$.alert("密码不能为空！", "提示", function() {
						passwordBox.focus();
						});
					return
				}
				/* if (codeInputBox.value=="") {
					$.alert("验证码不能为空！", "提示", function() {
						codeInputBox.focus();
						});
					return
				} */
				var loginInfo = {
					opNo: accountBox.value,
					passwordhash: passwordBox.value,
				//	code:codeInputBox.value
				};
				$.ajax({
					type:"post",
					async:false,
					cache:false,
					url:webPath+"WxSysLoginActionAjax_userLoginAjax.action",
					dataType:"json",
					data:loginInfo,
					success:function(jsonData){
						if(jsonData.flag =="success"){
							window.$.toast(jsonData.msg);
							window.top.loadPage(jsonData.url,1000);
						}else{
							$.toast(jsonData.msg, "forbidden");
						}
					},
					error:function(){
						alert("error");
					}
				});
			});
		})
			
	</script>
</body>
</html>