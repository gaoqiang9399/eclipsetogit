<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="ecamTempCongInsertForm" theme="simple" name="operform" action="${webPath}/mfExamineTemplateConfig/updateAjax">
							<dhcc:bootstarpTag property="formexatemp0002" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" typeclass="ajaxUpdate"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="删除" action="删除" typeclass="delete" onclick="MfExamineTemplateConfig.ajaxDeleteThis()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript">
			var kindList  ="";
			var riskConfigList  ="";
			var riskWarmModelNo ='${mfExamineTemplateConfig.riskWarmModelNo}';
			var kindNo = '${mfExamineTemplateConfig.kindNo}';
			var templateId = '${mfExamineTemplateConfig.templateId}';
			var cusTypes = JSON.parse('${dataMap.cusTypeItems}');
			var kindNos = JSON.parse('${dataMap.kindNoItems}');
			var repayTypes = JSON.parse('${dataMap.repayTypeItems}');
		</script>
		<script type="text/javascript" src='${webPath}/component/examine/js/MfExamineTemplateConfig.js'> </script>
		<script type="text/javascript">
		$(function(){
			MfExamineTemplateConfig.init();
            $("select[name=templateType]").attr("disabled","disabled");
		});
		</script>
</html>