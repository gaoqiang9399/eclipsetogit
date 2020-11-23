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
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
				<form  method="post" id="cusBankAccInsert" theme="simple" name="operform" action="${webPath}/mfCusBankAcceptanceBill/insertAjax">
					<dhcc:bootstarpTag property="formcusbankbill0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusBankAccInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
<script type="text/javascript">

	$(function() {
		//var url = $(".tab-content form").attr("action") + '?formId='+'${formId}';
		//$(".tab-content form").attr("action",url);
// 		$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	function getBankByCardNumber(obj){
		var identifyNumber = obj.value;
		$.ajax({
			url:webPath+"/bankIdentify/getByIdAjax",
			data:{identifyNumber:identifyNumber},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name=bankNumbei]").val(data.bankId);
					$("input[name=bank]").val(data.bankName);
				}else{
					$("input[name=bankNumbei]").val("");
					$("input[name=bank]").val("");
				}
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		}
	 function insertCallBack() {
		top.addFlag = true;
		top.formOrTable = "table";
		myclose_click();
	}; 
</script>
</body>
</html>
