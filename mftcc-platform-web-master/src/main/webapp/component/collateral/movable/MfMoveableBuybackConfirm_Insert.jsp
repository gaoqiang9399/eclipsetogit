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
						<form  method="post" id="MfMoveableBuybackConfirmForm" theme="simple" name="operform" action="${webPath}/mfMoveableBuybackConfirm/insertAjax">
							<dhcc:bootstarpTag property="formbuybackconf0002" mode="query"/>
						</form>
					</div>
					<div id="goodsDetailListdiv" class="bigform_content col_content" style="display:none">
						<div class="title"><h5 >货物明细</h5></div>
							<div id="goodsDetailList" class="table_content padding_0">
								<dhcc:tableTag paginate="goodsDetailList" property="tabledlpledgebaseinfobill0005" head="true" />
							</div> 
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfMoveableBuybackConfirm.ajaxBuybackConfirmSave('#MfMoveableBuybackConfirmForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/MfMoveableBuybackConfirm.js"></script>
	<script type="text/javascript">
		var appId='${appId}';
		var busPleId='${busPleId}';
		var pledgeNo=$("input[name=pledgeNo]").val();
		$(function(){
			MfMoveableBuybackConfirm.init();
			MfMoveableBuybackConfirm.getPledgeData();
			MfMoveableBuybackConfirm.refreshGoodsDetailList(pledgeNo);
		});
	</script>
</html>
