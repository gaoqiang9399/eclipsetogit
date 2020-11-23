<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>新增</title>
	<script type="text/javascript" src="${webPath}/component/pact/protect/js/MfAssetProtectRecord_Insert.js"></script>
	<script type="text/javascript">
        var protectId='${protectId}';
        var aloneFlag = true;
        var docParm = "";
        var dataDocParm={
            relNo:protectId,
            docType:"assetProtectDoc",
            docTypeName:"资产资料",
            docSplitName:"资产资料",
            query:''
        };
        $(function() {
            //滚动条
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
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
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="MfAssetProtectRecordInsert.updateAssetDizhaiAjax()"></dhcc:thirdButton>
		<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
