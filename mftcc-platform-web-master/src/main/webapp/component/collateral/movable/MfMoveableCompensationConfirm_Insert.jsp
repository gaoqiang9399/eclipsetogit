<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfMoveableCompensationConfirmForm" theme="simple" name="operform" action="${webPath}/mfMoveableCompensationConfirm/insertAjax">
							<dhcc:bootstarpTag property="formmovablecompensationconf0002" mode="query"/>
						</form>
					</div>
					<div id="goodsDetailListdiv" class="bigform_content col_content" style="display:none">
						<div class="title"><h5 >货物明细<a href="javascript:void(0);" onclick="MfMoveableCompensationConfirm.pledgeBillInput()">新增</a> 	<a href="${webPath}/pledgeBaseInfoBill/printPledgeBillTemplate" class="link"> 下载模版</a> <a href="javascript:void(0);" onclick="MfMoveableCompensationConfirm.fileExport()">上传</a></h5></div>
							<div id="goodsDetailList" class="table_content">
								<dhcc:tableTag paginate="mfEvalIndexSubList" property="tabledlpledgebaseinfobill0006" head="true" />
							</div> 
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfMoveableCompensationConfirm.ajaxCompensationConfirmSave('#MfMoveableCompensationConfirmForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
		<script type="text/javascript" src="${webPath}/component/collateral/js/MfMoveableCompensationConfirm.js"></script>
	<script type="text/javascript">
		var appId='${appId}';
		var busPleId='${busPleId}';
		$(function(){
			MfMoveableCompensationConfirm.init();
		});
	</script>
</html>
