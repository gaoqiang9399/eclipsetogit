<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKind.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 column margin_top_20">
						<div class="bootstarpTag fourColumn">
	            				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="kindForm-edit" theme="simple" name="operform" action="${webPath}/mfSysKind/updateAjax">
								<dhcc:bootstarpTag property="formsyskind0003" mode="query"/>
							</form>	
						</div>
					</div>
			</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="更新" action="更新" onclick="saveKindInfo('#kindForm-edit')"></dhcc:thirdButton>
			<dhcc:thirdButton value="删除" action="删除" typeclass="cancel" onclick="deleteKind()"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	<script type="text/javascript">
	var path= '${webPath}';
	$(function(){
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
		var ajaxData = '${ajaxData}';
	    ajaxData = JSON.parse(ajaxData);
	   	initKindConfig(ajaxData,'update','${brNoName}','${mfSysKind.brNo}','${mfSysKind.vouTypeDef}','${mfSysKind.pactPropertyDef}');
	});	
		
	</script>
	</body>
</html>