
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayment_MfRepaymentJsp.css" />
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfRepaymentJsp.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfRepaymentJspForBuy.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/repayMent.js"></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/initRepaymentDoc.js"></script>
<!-- 还款方式 为利随本清 并且 利随本清利息收取方式 是 3-按还款本金收取利息（例如还本1000 收 利息 =1000*（还款日期-还款计划开始日期）*日利率）  时使用  -->
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfLsbqRaymentJsp.js"></script>
<!-- 弹层关闭的方法 -->
<script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'>
</script>
<title>买方还款</title>
</head>
<script type="text/javascript">
    //"YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin"
    var payOrder = "${mfBusAppKind.repaymentOrderStr}" ;//代表还款顺序  应该取传过来的值
	var p8044 = "2";// 还款总额保留小数舍入 0-取底1-取顶2-四舍五入  应该取传过来的值
	var appId="${mfBusFincApp.appId}";
	var fincId="${mfBusFincApp.fincId}";
	var docParm = "";//查询文档信息的url的参数
	var webPath = '${webPath}';
	var repayDate = '${mfRepaymentBean.shangCiHuanKuanRiQi}';
	$(function() {
		//隐藏相关还款信息
		hideRepayInfo();
		initRepaymentDoc.initDoc("normalDoc");
        MfRepaymentJspForBuy.init();
	});


</script>
<body class="overflowHidden bg-white">

<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<!-- <div class="form-title">五级分类认定汇总申请表</div> -->
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="buyerRepayment" theme="simple" name="operform" action="${webPath}/mfRepayment/repaymentAjaxForBuy">
					<dhcc:bootstarpTag property="formbuyerRepaymentBase" mode="query"/>
				</form>
			</div>
			<!-- 还款资料 -->
			<div class="col-md-10 col-md-offset-1 column" >
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="MfRepaymentJspForBuy.ajaxInsert('#buyerRepayment')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();;"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>