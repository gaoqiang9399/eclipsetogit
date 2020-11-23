<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript">
			$(function(){
				var accountType = $('input[name=accountType]').val();
				if('1'==accountType) {
					$('#account_form').find('tr:eq(2)').remove();
				}
				//自动带出
				$('input[name=accName]').on('click', function(){
					var input = $(this);
					autoComPleter(this, '2', function(data){
						input.val(data.accNo + '/' + data.accName);
					})
				});
			})
		
		
			function ajaxAddAcount(form){
				
				var flag = submitJsMethod($(form).get(0), '');
				if(flag){
					var url = $(form).attr("action");
					var dataParam = JSON.stringify($(form).serializeArray());
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								myclose_click();
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
				
			}
			
			function comCallBack(obj){
				$('input[name=accName]').val(obj.showName);
				$('input[name=accHrt]').val(obj.id);
			}
			
		
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="account_form"  theme="simple" name="operform" action="${webPath}/cwCashierAccount/insertAjax">
							<dhcc:bootstarpTag property="formcashier0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxAddAcount('#account_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
