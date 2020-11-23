<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
</head>
<script type="text/javascript" src="${webPath}/component/pact/feerefund/js/MfBusFeeCollect_Insert.js"></script>

<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
		<div class="col-md-10 col-md-offset-1 margin_top_20">
			<div class="bootstarpTag fourColumn">
				<form method="post" id="detailForm" theme="simple" name="operform" action="">
					<dhcc:bootstarpTag property="formfeeCollectDetail" mode="query"/>
				</form>
			</div>
			<div class="row clearfix">
				<%@ include file="/component/model/templateIncludePage.jsp"%>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfBusFeeCollect_Insert.myclose_reload();"></dhcc:thirdButton>
	</div>
</div>
</body>
<script>
	var nodeNo = '${nodeNo}';
    var temBizNo = '${chargeId}';
    var approvalNodeNo ;
    var querySaveFlag_pl ;
    $(function () {
        MfBusFeeCollect_Insert.init();
    })

</script>
</html>
