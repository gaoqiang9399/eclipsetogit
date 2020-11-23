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
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">报表查询条件配置表</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfReportQueryConditionConfigForm" theme="simple" name="operform" action="${webPath}/mfReportQueryConditionConfig/insert">
							<dhcc:bootstarpTag property="formreportConfig0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave('#MfReportQueryConditionConfigForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
