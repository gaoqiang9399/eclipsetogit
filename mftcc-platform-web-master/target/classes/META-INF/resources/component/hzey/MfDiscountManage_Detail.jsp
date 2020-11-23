<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>个人新增优惠券</title>
		<script type="text/javascript" src="${webPath}/component/hzey/js/MfDiscountManage.js"></script>
		<script type="text/javascript" >
			var cusNo = "${cusNo}";
			$(function(){
				/* 获取客户优惠券列表*/
				MfDiscountManage.getMfDiscountManageList();
				myCustomScrollbarForForm({
					obj:".report_content",
					advanced : {
						updateOnContentResize : true
					}
				});
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfDiscountManageForm1" theme="simple" name="operform" action="${webPath}/mfDiscountManage/insertAjax">
							<dhcc:bootstarpTag property="formhzeydiscount0002" mode="query"/>
						</form>
					</div>
				</div>
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="list-table margin_left_15" id="mfDiscountManageList">
						<dhcc:tableTag property="tablehzeydiscount0001" paginate="mfDiscountManageList" head="true"></dhcc:tableTag>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="新增" action="新增" onclick="MfDiscountManage.ajaxDiscountSave('#MfDiscountManageForm1')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
