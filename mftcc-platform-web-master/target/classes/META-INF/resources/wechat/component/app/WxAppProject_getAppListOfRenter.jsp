<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include/pub_wx.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>业务申请信息</title>
<style type="text/css">
	.weui_panel_bd .weui_media_hd i.i{
		font-size: 35px;
	    width: 40px;
	    height: 40px;
	    margin-left: 5px;
	    margin-top: 2px;
	}
	.weui_media_bd small{
	    float: right;
	    border: 1px solid;
	    padding: 0 10px;
	    border-radius: 40px;
	}
</style>
</head>
<body>
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_hd">业务申请信息</div>
		<div id="parselist" class="weui_panel_bd">
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			new Parselist({
				actionUrl:webPath+"WxAppProjectActionAjax_getAppListOfRenterAjax.action"
			})
		});
	</script>
</body>
</html>