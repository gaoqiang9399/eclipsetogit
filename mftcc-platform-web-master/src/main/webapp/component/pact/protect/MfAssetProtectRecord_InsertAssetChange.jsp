<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pact/protect/js/MfAssetProtectInsertChange.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js"></script>
		<script type="text/javascript">
			var ajaxCollClassData='${ajaxCollClassData}';
			var ajaxOverduePactData='${ajaxOverduePactData}';
			var cusData='${cusData}';
			ajaxCollClassData=JSON.parse(ajaxCollClassData);
			ajaxOverduePactData=JSON.parse(ajaxOverduePactData);
			cusData=JSON.parse(cusData);
			var protectId='${protectId}';
			var entrFlag="assetProtect";
			var cusNo='${mfAssetProtectRecord.cusNo}';
			var cusName='${mfAssetProtectRecord.cusName}';
			var pledgeNoStr='${pledgeNoStr}';
			var appId="";
			var aloneFlag = true;
			var dataDocParm={
				relNo:protectId,
				docType:"assetProtectDoc",
				docTypeName:"资产资料",
				docSplitName:"资产资料",
				query:''
			};
			$(function() {
				MfAssetProtectInsertChange.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div id="assetProtect-div" class="scroll-content">
				<div id="assetProtectForm" class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfAssetProtectRecordForm" theme="simple" name="operform" action="${webPath}/mfAssetProtectRecord/insert">
							<dhcc:bootstarpTag property="formassetprotect0001" mode="query"/>
						</form>
						<form method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfAssetProtectRecord/insert">
							<dhcc:bootstarpTag property="formassetbaseinfo" mode="query"/>
						</form>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfAssetProtectInsertChange.saveAssetProtectAjax()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
