<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link type="text/css" rel="stylesheet"
	href="UIplug/umeditor-dev/themes/default/_css/umeditor.css" />
	
	<script type="text/javascript"
	src="${webPath}/component/oa/consumable/js/MfOaConsAppInsert.js"></script>

<style type="text/css">
.cons_content{
	padding-left: 5%;
    padding-top: 2%;
    margin-top: 3%;
    border: solid 1px #949191;
    width: 100%;
    /* height: 25%; */
}
.cons_table{
    width: 100%;
    font-size: 14px;
    /* height: 100%; */
}
.cons_table input{
	border: none;
}
/* .rows .input-box textarea {
    border: 1px solid #ccc;
    width: 90%;
    height: 58px;
    font-size: 14px;
    margin-left: 15px;
 }
 .input-box.remark_div{
	border: none;
    margin: 0 auto;
    width: 100%;
 } */
 /* .formRowCenter{
 	padding-top: 40px;
 } */
</style>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="OaConsAppInsert" theme="simple"
					name="operform" action="${webPath}/mfOaCons/updateAjax">
					<dhcc:bootstarpTag property="formconsumable0002" mode="query" />
				</form>
				<center>
				<div id="cons_content" class="cons_content" style="display: none;">
					<table class="cons_table" cellspacing="0" cellpadding="10">
						<tr>
							<td>当前库存：</td><td><input name="storeNum" readonly="readonly" dataType="1"/></td>
							<td>申领类型：</td><td><input name="appTypeName" readonly="readonly"/></td>
						</tr>
						<tr>
							<td>计量单位：</td><td><input name="unit" readonly="readonly"/></td>
							<td>规格型号：</td><td><input name="specification" readonly="readonly"/></td>
						</tr>
						<tr>
							<td>单价（元）：</td><td><input name="price" readonly="readonly" dataType="1"/></td>
							<td>使用状态：</td><td><input name="useState" readonly="readonly"/></td>
						</tr>
						<tr>
							<td>序列号：</td><td><input name="barCode" readonly="readonly" dataType="1"/></td>
						</tr>
					</table>
				</div>
				</center>
			</div>
			
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
		</div>
</body>

<script type="text/javascript">
	$(function() {
		OaConsAppInsert.init();
	});
	$(document.body).height($(window).height());
</script>
</html>
