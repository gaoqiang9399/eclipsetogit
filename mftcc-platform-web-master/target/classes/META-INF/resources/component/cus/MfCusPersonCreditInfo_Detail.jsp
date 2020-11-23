<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>--%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonCreditInfo.js'> </script>
	</head>
	<script type="text/javascript">
        var projectName = '${projectName}';
        $(function() {
            init();
        });
	</script>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="saveMfCusPersonCreditInfo" theme="simple" name="operform" action="${webPath}/mfCusPersonCreditInfo/updateAjax">
						<dhcc:bootstarpTag property="formcusCreditBase" mode="query"/>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="saveMfCusPersonCredit('#saveMfCusPersonCreditInfo','insert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>

	</body>
</html>