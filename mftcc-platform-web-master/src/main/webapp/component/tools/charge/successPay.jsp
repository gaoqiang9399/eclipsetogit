<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		<%
			String orderID = request.getParameter("orderID");
			String money_new = request.getParameter("money_new");
		%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/include/common.jsp"%>
<script type="text/javascript">
		$(function(){
			var orderID = "<%=orderID%>";
			var money_new = "<%=money_new%>";
			$("#orderID").html(orderID);
			$("#money_new").html(money_new);
			$("#money").html(money_new);
			top.dialog.getCurrent().returnValue="0000";
			var i = 18;
			setInterval(function(){
				i=i-1;
				$('#countdown').html(i);
				if(i==0){
				top.dialog.getCurrent().close().remove();
				}
			}, 1000);
		});
</script>
<style type="text/css"> 
	.sc_pay{
		width: 162px;
		position: absolute;
		left: 360px;
		top: 230px;
		font-size: 18px;
	}
	</style>

</head>
<body>
	<div style="width: 100%; height: auto;">
		<div class="er_cont" id="money-btm">
			<div class="cont_center" style="height: 94px;">
				<div class="center">
					<div class="pull-left">
						<div style="padding-left: 94px;">
						<img style="padding-right: 32px;" height="90px" src="<%=request.getContextPath() %>/component/tools/image/successPay.png"/>
						<span style="position: absolute;top: 32px;font-size: 30px;">充值成功</span></div>
					</div>
				</div>
			</div>
			<div class="cont_btom">
				<div style="text-align: center;">
				<p><span style="font-size: 19px;">北京微金时代</span></p>
				<p>¥<span id = "money"></span></p>
				</div>
				<div>
					<table style="margin: auto;background-color: #F2F2F2;">
						<tr>
							<td>收款账户：</td>
							<td>北京微金时代科技有限公司</td>
						</tr>
						<tr>
							<td>订单类型：</td>
							<td>在线充值</td>
						</tr>
						<tr>
							<td>订单编号：</td>
							<td id="orderID"></td>
						</tr>
						<tr>
							<td>支付金额：</td>
							<td><span id="money_new"></span>元</td>
						</tr>
					</table>
				</div>
				<div style="text-align: center;margin-top: 44px;"><span id="countdown">18</span>秒后自动返回主页</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>