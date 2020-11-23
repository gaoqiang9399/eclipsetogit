<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	Object themeObj = session==null?null:session.getAttribute("color");
	String theme = themeObj==null?"blue":((String)themeObj);
%>
<html>
	<head>
		<title>一般异常</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<style type="text/css">
			html {
				margin:0;
				padding:0;
				border:0;
				-webkit-text-size-adjust:100%;
				-ms-text-size-adjust:100%;
				font-size:12px;
			}
			body {
				font-family: "Microsoft YaHei","Helvetica Neue", Helvetica, Arial, sans-serif;
				font-size:12px;
				line-height: 24px;
				background-color:#f3f3f3;
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
				color:#6f6f6f;
				width:100%;
				height:100%;
			}
			#wrapbc {
				position:absolute;
				width:530px;
				height:300px;
				top:50%;
				left:50%;
				margin-left:-265px;
				margin-top:-150px;
			}
			.picbc {
				width:150px;
				height:184px;
				background-image:url(./layout/view/page/imgs/pic.png);
				margin-left:auto;
				margin-right:auto;
			}
			.picbc2 {
				width:210px;
				height:150px;
				background-image:url(./layout/view/page/imgs/weixiubg.png);
				margin-left:auto;
				margin-right:auto;
			}
			.textbc {
				width:530px;
				height:90px;
			}
			.textbc h1 {
				font-size:30px;
				color:#f48429;
				text-align:center;
			}
			.textbc h2 {
				font-size:16px;
				text-align:center;
				font-weight:normal;
			}
			.redbc {
				color:#dd3900;
			}
			.textbc input{ width:100px; height:30px; margin:30px 0 0 215px; border:none; border-radius:4px; background:#1e95cd;
			text-align:center; color:#fff; font-size:14px;  }
			.textbc input:hover{ background:#2ea4dc; cursor:pointer;}
			.textbc input:active{ background:#1489c0;}
		</style>
		<script type="text/javascript" language="javascript">
		function funcExit() {
			//window.history.back();
				window.top.location.href = "/sysLogin/sessionInvalid";
		}
			$(function(){
				funcExit();
				var $msg = $(".actionMessage");
				var title = $msg.find("li").eq(0).text();
				var content = $msg.find("li").eq(1).text();
				$("#wrapbc").find(".textbc h1").html(title);
				$("#wrapbc").find(".textbc h2").html(content);
			});
</script>
	</head>
	<body>
		<div id="wrapbc">
		  <div class="picbc2"></div>
		  <div class="textbc">
		    <h1></h1>
		    <h2></h2>
		    <input type="button" value=" 退 出 " onclick="javascript:funcExit()" />
		  </div>
		</div>
		<div style="display:none">
		</div>
	</body>
</html>
