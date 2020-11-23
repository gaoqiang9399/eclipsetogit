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
						<form method="post" id="ecamTempCongInsertForm" theme="simple" name="operform" action="${webPath}/mfExamineTemplateConfig/insertAjax">
							<dhcc:bootstarpTag property="formexatemp0002" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" typeclass="ajaxInsert"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
	<script type="text/javascript">
			var kindList  ="";
			var riskConfigList  ="";
			var kindNo = "";
			var riskWarmModelNo ="";
			var cusTypes = JSON.parse('${dataMap.cusTypeItems}');
			var kindNos = JSON.parse('${dataMap.kindNoItems}');
			var repayTypes = JSON.parse('${dataMap.repayTypeItems}');
            var templateType = JSON.parse('${templateType}');
		</script>
		<script type="text/javascript" src='${webPath}/component/examine/js/MfExamineTemplateConfig.js'> </script> 
		<script type="text/javascript">
		$(function(){
		    if (templateType=='1'){
                $("select[name=templateType]").val(templateType);
                $("select[name=templateType]").attr("disabled","disabled");
			}else {
                $("select[name=templateType] option[value='1']").remove();
			}

			MfExamineTemplateConfig.init();
		});
		</script>
</html>
