
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfRepaymentTail.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/initRepaymentDoc.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<!-- 弹层关闭的方法 -->
<script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'>
</script>
<title>尾款结付</title>
</head>
<script type="text/javascript">
    var docParm = "";//查询文档信息的url的参数
    var webPath = '${webPath}';
    var overFlag = '${overFlag}';
	$(function() {
		//隐藏相关还款信息
        MfRepaymentTail.init();
	});
</script>
<body class="body_bg">

<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<!-- <div class="form-title">五级分类认定汇总申请表</div> -->
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="tailRepayment" theme="simple" name="operform" action="${webPath}/mfRepayment/tailAjax">
					<dhcc:bootstarpTag property="formtailRepaymentBase" mode="query"/>
				</form>
			</div>
			<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;">

			</div>
			<!-- 还款资料 -->
			<div class="col-md-12 col-md-offset-0 column" >
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="MfRepaymentTail.ajaxInsert('#tailRepayment')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();;"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>