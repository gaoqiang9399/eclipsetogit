<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户充值</title>
<script type="text/javascript">
	function pay(){
		var payValue = $("#payValue").val();
		if(payValue==null||payValue==""){
			alert("请输入充值金额后再试！",1);
			return false;
		}
		var getPath = '';
		var allLocal = window.location;// 获取全部地址 
		var localStr = allLocal.toString();// 转换为字符串
		// 获取http:
		var loc = localStr.split("/"); // 转为字符串
		var local1 = loc[0];
		// 获取域名
		var loc2 = localStr.split("/"); // 转为字符串
		var realm_ = loc2.slice(2, 3);
		getPath = local1 + "//" + realm_;
 		DIALOG.open(getPath   
				+'/factor/component/tools/charge/WXPay.jsp?payValue='+payValue,window,
				'dialogHeight:440px;dialogWidth:560px;help：no;status:no;title:微信充值;',function(){
					if(top.dialog.getCurrent().returnValue=="0000"){
					$("#back").click();
					}
				}
		);
	}
 		/* top.openBigForm("${webPath}/accountPay/getBarcode?payValue="+payValue,"微信充值",function(){
 			alert("111");
 		},'40','70'); */
</script>
<style type="text/css">
	.pay_out{
		margin: auto;
		width: 302px;
		padding-top: 11%;
		}
	li{
		list-style: none;
	}
	ul{
		padding: 0px;
	}
</style>
</head>
<body>
	<div class="pay_out">
		<div class="pay_list">
			<ul>
				<li>
					<label>账户余额：</label>
					<span>${manageDetil.balance}&nbsp;元</span>
				</li>
				<li>
					<label>充值金额：</label>
					<input id="payValue" type="text" />
					<span>元</span>
				</li>
				<li>
					<label>赠送金额：</label>
					<span>${manageDetil.buckAmt}&nbsp;元</span>
				</li>
			</ul>
		</div>
		<div class="pay_info">
			<h3>说明：</h3>
			<p>目前仅支持微信支付在线充值，充值成功后</p>
			<p>充值金额将自动计入账户余额。</p>
		</div>
		<div class="pay_btn" align="center">
			<a class="btn btn-primary" href="" onclick="pay();return false;">充值</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" href="${webPath}/accountPay/getDetilPage?licinesNo=0"><span id="back">返回</span></a>
		</div>
	</div>	
</body>
</html>