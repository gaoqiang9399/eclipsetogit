<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			function saveMsgVar(obj){
			var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					ajaxInsertCusForm(obj);
				}
			};
		</script>
	</head>
		<body class="overflowHidden bg-white">
			<div class="container form-container">
				<div class="scroll-content">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
			            	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="varForm" theme="simple" name="operform" action="${webPath}/mfMsgVar/insertAjax">
								<dhcc:bootstarpTag property="formmsgconf0002" mode="query"/>
							</form>
						</div>
					</div>
				</div>
				
		
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="saveMsgVar('#varForm')"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>