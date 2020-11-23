<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>修改</title>
		<script type="text/javascript">
			var contactsTelOld = "${mfCusCustomer.contactsTel}";
		</script>
		
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript">
			var cusNo = "${cusNo}";
			var ajaxData = '${ajaxData}';
			var projectName = '${projectName}';
            var netCheck = '${netCheck}';
            var editFlag = '${editFlag}';
			ajaxData = JSON.parse(ajaxData);
			$(function(){
				init("update");
				var idType = $("input[name='idType']").val();
				if (typeof(editFlag) != "undefined" && editFlag == "query") {
					$("input[name='cusName']").next().remove();
				}else{
					var $idNum = $("input[name='idType']").parents("table").find("input[name=idNum]")[0];
					if(idType=="0"){//身份证样式格式
						//如果是身份证，添加校验
						changeValidateType($idNum, "idnum");
					}else{
						changeValidateType($idNum, "");
					}
				}
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" name="operform" action="${webPath}/mfCusCustomer/updateForBusAjax">
							<dhcc:bootstarpTag property="formcus00004" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
				<c:if test='${editFlag != "query"}'>
					<dhcc:thirdButton value="保存" action="保存" onclick="cusInfoSave('#cusInsertForm','update');"></dhcc:thirdButton>
				</c:if>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
