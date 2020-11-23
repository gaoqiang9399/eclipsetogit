<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src='${webPath}/component/app/js/MfTuningReport_Insert.js'></script>
	
	<script type="text/javascript">
		var cusNo='${cusNo}';
		var appId = '${appId}';
		var templateBizConfigId = '${templateBizConfigId}';
		$(function(){
			MfTuningReport_Insert.init();
		});
		function insertTuning(obj){
			MfTuningReport_Insert.insertTuning(obj);
		}
		function selectReportType(){
			MfTuningReport_Insert.selectMode();
		}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="inputCommonForm" theme="simple" name="operform" action="/${webPath}mfTuningReport/insertAndCommitAjax">
							<dhcc:bootstarpTag property="formappreport0002" mode="query" />
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="insertTuning('#inputCommonForm')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfTuningReport_Insert.closeMyOnly()"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
