<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo='${cusNo}';
			var appId='${appId}';
			var entrance = "business";
			var formId='${formId}';
			var entrFlag='${entrFlag}';
			var busModel='${mfBusApply.busModel}';
			var classId='${classId}';
			var isQuote="0";
			var ajaxData = '${ajaxData}';
		    ajaxData = eval("("+ajaxData+")");
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/insertCollateralAndEvalAjax">
							<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						</form>
						<input type="hidden" name = "isQuote"/>
						<input type="hidden" name = "entrFlag"/>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="CollateralInsert.insertCollateralBase('#pledgeBaseInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_evalInsert.js"></script>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	<script type="text/javascript">
		$(function() {
			CollateralInsert.init();
			//泓兴源车辆质押修改默认类别为车辆
			if(busModel=='32'){
				$("input[name=classId]").val("17061310382529920");
			}
			$("input[name=classId]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				items:ajaxData.collClass
			});
		});
	</script>
</html>
