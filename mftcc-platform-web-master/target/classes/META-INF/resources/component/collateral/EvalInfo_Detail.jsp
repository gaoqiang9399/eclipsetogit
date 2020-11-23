<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
				
				<script type="text/javascript"  src='${webPath}/component/collateral/js/EvalInfo.js'> </script>
				<script type="text/javascript">
					$(function() {
						init();
					});
					
					function validateAmount(){
						var num  = $("input[name=evalAmount]").val();
						if(num.length!=0){
							var reg = /^\-[,,0-9]*.?[0-9]*$/;
							if(reg.test(num)){
								alert("评估价值不能输入负数", 0);
								$("input[name=evalAmount]").val("");
							}
						}
					}
					function validateRate(){
						var num  = $("input[name=mortRate]").val();
						if(num.length!=0){
							var reg = /^\-[0-9]*.?[0-9]*$/;
							if(reg.test(num)){
								alert("抵质押率不能输入负值", 0);
								$("input[name=mortRate]").val("");
							}
						}
					}
					function validateMonth(){
						var num  = $("input[name=validTerm]").val();
						if(num.length!=0){
							var reg = /^\-[0-9]*$/;
							if(reg.test(num)){
								alert("评估有效期不能输入负值", 0);
								$("input[name=validTerm]").val("");
							}
						}
					}
					
					function calConfirmAmount(){
						var value = $("input[name=evalAmount]").val().replace(/,/g,"");
						if(value.length == 0){
							alert("请先输入评估价值",0);
							$("input[name=mortRate]").val("");
						}
						var rate = $("input[name=mortRate]").val();
						$("input[name=confirmAmount]").val(value*rate/100);
					};
				</script>
	</head>
		<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
			            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalInfoUpdate" theme="simple" name="operform" action="${webPath}/evalInfo/updateAjax">
							<dhcc:bootstarpTag property="formdlevalinfo0003" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="saveEvalInfo('#evalInfoUpdate','update')"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>