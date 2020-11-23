<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<style type="text/css">
.content-box {
	width: 90%;
}
.cons_head {
height: 30px;
margin-top: 30px;
}
.infoTitle{
margin-top: 20px;
margin-bottom: 20px;
}
.add_class {
    color: #32b5cb;
    margin-top: -20px;
}
.i-add:hover {
	background-color:#32b5cb;
 	color: #fff;
}
.i-add{
	cursor:hand;
    color: #32b5cb;
    font-size: 15px;
    margin-left: 10px;
}
</style>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-title">资产入库</div>
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="OaConsInsert" theme="simple"
						name="operform" action="${webPath}/mfOaCons/insertAjax">
						<dhcc:bootstarpTag property="formconsumable0002" mode="query" />
					</form>
					<form method="post" id="OaConsInsertDetil1" theme="simple" style="display:none;">
						<dhcc:bootstarpTag property="formconsumable0003" mode="query" />
					</form>
					<form method="post" id="OaConsInsertDetil2" theme="simple" style="display:none;">
						<dhcc:bootstarpTag property="formconsumable0004" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
			<a href="${webPath}/mfOaCons/getListPage">
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel"></dhcc:thirdButton>
			</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
<script type="text/javascript"
	src="${webPath}/component/oa/consumable/js/MfOaConsInsert.js"></script>
<script type="text/javascript">
	OaConsInsert.path = "${webPath}";
	var ajaxData = '${ajaxData}';
	    ajaxData = eval("("+ajaxData+")");
	$(function() {
		OaConsInsert.init();
	});
	$(document.body).height($(window).height());
</script>
</html>
