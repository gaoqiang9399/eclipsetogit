<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfOaCounttransForm" theme="simple" name="operform" action="${webPath}/mfOaCounttrans/insertAjax">
							<dhcc:bootstarpTag property="formchangemoney0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfOaCounttransInsert.myInsertAjax('#MfOaCounttransForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<!-- /factor/WebRoot/component/oa/changemoney/js/MfOaCounttrans_Insert.js -->
	<script type="text/javascript" src="${webPath}/component/oa/changemoney/js/MfOaCounttrans_Insert.js"></script>
	<script type="text/javascript">
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 			theme:"minimal-dark",
// 			updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=transferOutAccountNumber]").on('keyup input',function(){
	           var  accountNo =$(this).val();
	           if(/\S{5}/.test(accountNo)){
	            	this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
	           }
		});
		$("input[name=transferInAccountNumber]").on('keyup input',function(){
	           var  accountNo =$(this).val();
	           if(/\S{5}/.test(accountNo)){
	            	this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
	           }
		});
	</script>
</html>
