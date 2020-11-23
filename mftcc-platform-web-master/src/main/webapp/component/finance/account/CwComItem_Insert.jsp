<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	<style type="text/css">
	.seqspan{
		 		margin-left: 20px;
	}
	</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="account_form"  theme="simple" name="operform" action="${webPath}/cwComItem/insertAjax">
							<dhcc:bootstarpTag property="formaccount0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#account_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		//jsp加载完成后调用此function 
		  var path = '${webPath}'; 
		$(function(){
			//处理辅助项
			//dealCashType();
			CwComItemInsertJs.init();
		});
	</script>
	<script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_Insert.js"></script>
	
</html>
