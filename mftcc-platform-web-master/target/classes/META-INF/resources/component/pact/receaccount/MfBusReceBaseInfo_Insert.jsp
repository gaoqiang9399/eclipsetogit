<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="receInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusReceBaseInfo/insertAjax">
						<dhcc:bootstarpTag property="formrecebaseinfo" mode="query"/>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusReceBaseInfo_Insert.ajaxInsert('#receInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceBaseInfo_Insert.js"></script>
<script type="text/javascript">
    var appId='${appId}';
    var cusNo='${cusNo}';
    var ajaxData = '${ajaxData}';
    ajaxData = JSON.parse(ajaxData);
	$(function() {
        MfBusReceBaseInfo_Insert.init();
	});
</script>	
</html>
