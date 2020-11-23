<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusBankAccManage.js?v=${cssJsVersion}"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
				<form  method="post" id="cusBankAccUpdate" theme="simple" name="operform" action="${webPath}/mfCusBankAccManage/updateAjax">
					<dhcc:bootstarpTag property="formcusbank00003" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="updateCusFormFormat('#cusBankAccUpdate');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
		<script type="text/javascript">
		$(function() {
					 
	        var formatAccountNo = $("input[name='accountNo']").val().replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
	    	$("input[name='accountNo']").val(formatAccountNo);
	    	
// 			$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
	    	$("input[name='accountNo']").on('keyup input',function(){
	           var  accountNo =$(this).val();
	           if(/\S{5}/.test(accountNo)){
	            	this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
	       	 	}
        	});
	    	 //账户用途选择组件
        $("select[name=useType]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//单选
					addBtn:{//添加扩展按钮
						"title":"新增",
						"fun":function(hiddenInput, elem){
							$(elem).popupSelection("hideSelect", elem);
							BASE.openDialogForSelect('新增账户用途','FUND_ACC_TYPE', elem);
						}
					}
			});
			
			
		});
		function updateCusFormFormat(obj){
			var accountNo = $("input[name='accountNo']").val();
			$("input[name='accountNo']").val(accountNo.trim().replace(/\s/g,""));
			ajaxInsertCusForm(obj);
		}
		function updateCallBack(){
			top.addFlag = true;
			
			myclose_click();
		};
		</script>
	</body>
</html>