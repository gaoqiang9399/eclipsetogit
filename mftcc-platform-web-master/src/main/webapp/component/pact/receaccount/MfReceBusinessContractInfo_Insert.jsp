<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfReceBusinessContractInfo_Insert.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
            var ajaxData = '${ajaxData}';
            ajaxData = JSON.parse(ajaxData);
            $(function(){
                MfReceBusinessContractInfo_Insert.init();
            });

		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfReceBusContractForm" theme="simple" name="operform" action="${webPath}/mfReceBusinessContractInfo/insertAjax">
							<dhcc:bootstarpTag property="formreceBusContractInfo" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfReceBusinessContractInfo_Insert.insertAjax('#MfReceBusContractForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
