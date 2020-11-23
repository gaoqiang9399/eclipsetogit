<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/auth/js/MfBusBreed_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript">
            var breedInitFlag = 0;//判断业务品种选择组件是否初始化
            var adaptationKindNo = '${adaptationKindNo}';
            var creditSum = '${creditSum}';
            var creditAppId = '${creditAppId}';
			$(function(){
                MfBusBreed_Insert.initDetail();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title"></div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusCreditBreedForm" theme="simple" name="operform" action="${webPath}/mfCusCreditApply/breedUpdateAjax">
							<dhcc:bootstarpTag property="formcreditBreedBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusBreed_Insert.ajaxUpdate('#MfCusCreditBreedForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
