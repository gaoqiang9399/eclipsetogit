<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src='${webPath}/component/app/js/MfTuningReport_Detail.js'></script>
	
	<script type="text/javascript">
		var reportId = '${reportId}';
		$(function(){
			MfTuningReport_Detail.init();
		});
		function insertTuning(obj){
			MfTuningReport_Detail.insertTuning(obj);
		}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="inputCommonForm" theme="simple" name="operform" action="${webPath}/mfTuningReport/updateAjax">
							<dhcc:bootstarpTag property="formappreport0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="insertTuning('#inputCommonForm')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfTuningReport_Detail.closeMyOnly()"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
