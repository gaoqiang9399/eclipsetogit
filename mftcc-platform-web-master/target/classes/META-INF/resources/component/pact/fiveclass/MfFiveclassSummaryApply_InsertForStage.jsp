<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ include file="/component/wkf/wkf_bus_base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/pact/css/MfPactFiveclass_commonForBatch.css" />
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_commonForBatch.js'></script>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfFiveclassSummaryApply_Insert.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var ajaxData = ${ajaxData};
			var processId ="2018081320353408";
			$(function(){
				MfFiveclassSummaryApply_Insert.init();
			 });
		
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">五级分类认定汇总申请表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfFiveclassSummaryApplyForm" theme="simple" name="operform" action="${webPath}/mfFiveclassSummaryApply/insertAjaxForStage">
							<dhcc:bootstarpTag property="formfiveclassApplyStageBase" mode="query"/>
						</form>
					</div>
					<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;">
				
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfFiveclassSummaryApply_Insert.ajaxInsert('#MfFiveclassSummaryApplyForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfFiveclassSummaryApply_Insert.close();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
