<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCreditAgencies_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript">
            var bankInitFlag = 0;//判断银行选择组件是否初始化
            var areaInitFlag = 0;//判断银行区域选择组件是否初始化
            var agenciesInitFlag = 0;//判断银行支行选择组件是否初始化
            var adaptationKindNo = '${adaptationKindNo}';
            var creditSum = '${creditSum}';
			$(function(){
                MfCusCreditAgencies_Insert.detailinit();
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
						<form method="post" id="MfCusCreditAgenciesForm" theme="simple" name="operform" action="${webPath}/mfCusCreditApply/agenciesUpdateAjax">
							<dhcc:bootstarpTag property="formcreditAgenciesBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="更新" action="更新" onclick="MfCusCreditAgencies_Insert.ajaxUpdate('#MfCusCreditAgenciesForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
