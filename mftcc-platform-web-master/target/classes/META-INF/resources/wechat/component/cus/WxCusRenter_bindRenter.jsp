<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include/pub_wx.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>用户信息绑定</title>
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
	    background: url(wechat/images/enterLogo.png) no-repeat;
	}
	.content-padded{
		padding: 15px;
	}
</style>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<span class="enterLogo"></span>
			<h2 class="weui_msg_title">用户绑定</h2>
			<p class="weui_msg_desc">东华软件股份公司</p>
		</div>
		<div class="weui_text_area">
			<form action="">
			 <wx:wxformTag property="cus0001"/>
				<%-- <div class="weui_cells weui_cells_form">
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">真实姓名</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input name="cusName" class="weui_input" type="text"
								placeholder="请输入真实姓名">
						</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">证件类型</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select" name="idType">
								<option selected="" value="0">选择</option>
							</select>
						</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">证件号码</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input name="idNo" class="weui_input" type="text"
								placeholder="请输入证件号码">
						</div>
					</div>
				</div>
				<input type="hidden" name="openId"
					value="<s:property value="openId"/>" />
				<div class="content-padded">
					<button type="button" onclick="ajaxSubmit(this.form);"
						class="weui_btn weui_btn_primary">确认绑定</button>
					<a href="WxCusRenterAction_login.action?openId=<s:property value="openId"/>" class="weui_btn weui_btn_plain_primary">已绑定直接登录</a>
				</div> --%>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		/* $(function() {
			$.getJSON("wechat/include/parm_dic.json", "", function(data) {
				$("select[name=idType]").empty();
				$.each(data["ID_TYPE"], function(i, node) {
					$("select[name=idType]").append(
							'<option value="'+node.optCode+'">' + node.optName
									+ '</option>')
				});
			});
		}); */
		function ajaxSubmit(obj) {
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url : "WxCusRenterActionAjax_bindRenterAjax.action",
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
						$.alert(data.content, data.msg, function() {
							//window.top.location.href="WxCusRenterAction_login.action";
							window.top.loadPage("WxCusRenterAction_login.action",1000);
						});
					} else if (data.flag == "error") {
						$.alert(data.content, data.msg);
					}
				},
				error : function(data) {
					$.alert(data);
				}
			});
		}
	</script>
</body>
</html>