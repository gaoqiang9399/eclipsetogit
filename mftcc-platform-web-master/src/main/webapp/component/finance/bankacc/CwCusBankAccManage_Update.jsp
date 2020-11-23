<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
		function ajaxSaveBankAcc(obj) {
			var accountNo = $("input[name='accountNo']").val();
			$("input[name='accountNo']").val(accountNo.trim().replace(/\s/g, ""));
			var url = $(obj).attr("action");
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.success) {
							top.window.flag = true;
							window.top.alert("操作成功！", 1);
							myclose_click();
						} else {
							alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_SAVE"), 0);
					}
				});
			} else {
				alert(top.getMessage("FAILED_SAVE"), 0);
			}
		}
		function getBankByCardNumber(obj) {
			var identifyNumber = obj.value.trim().replace(/\s/g, "");
			$.ajax({
				url : webPath + "/bankIdentify/getByIdAjax",
				data : {
					identifyNumber : identifyNumber
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						BASE.removePlaceholder($("input[name=bankNumbei]"));
						BASE.removePlaceholder($("input[name=bank]"));
						$("input[name=bankNumbei]").val(data.bankId);
						$("input[name=bank]").val(data.bankName);
					} else {
						$("input[name=bankNumbei]").val("");
						$("input[name=bank]").val("");
					}
				}
			});
		}
		var autoComData = [];
		$(function(){
			$(".bankNo").each(function(i, item) {
				var itemBankNo = $(item).find("input").val();
				var itemBankNoHtml = $(item).find("input").val();
				if(/\S{5}/.test(itemBankNo)){
					$(item).find("input").val(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
				}
			});
			url = webPath+"/cwCusBankAccManage/getAccNameDataSourceAjax";
			$.getJSON(url, {}, function(data){
				autoComData = data.items;
				for(var item in autoComData){
					autoComData[item].label = autoComData[item].accountName;
				}
			});
		});
		function checkOrg(obj) {
			prodAutoMenu(obj,autoComData,function(data){
				$(obj).val(data.accountName);
			},null,'',false);
		}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="bankacc_form"  theme="simple" name="operform" action="${webPath}/cwCusBankAccManage/updateAjax">
							<dhcc:bootstarpTag property="formbankacc0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSaveBankAcc('#bankacc_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
