<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactChange_Insert.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactChange_List.js"></script>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
        var maxAmt = null;
        var minAmt = null;
        var minTerm = null;
        var maxTerm = null;
        var minFincRate = null;
        var maxFincRate = null;
        var termType = null;
        $(function(){
            MfBusPactChange_Insert.init();
            MfBusPactChange_Insert.selectChangePact();
        });
        function checkByKindInfo(obj){
            MfBusPactChange_Insert.checkByKindInfo(obj);
		}
        function checkTerm(obj){
            MfBusPactChange_Insert.checkTerm(obj);
        }
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<!-- <div class="form-title">合同变更历史表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusPactChangeForm" theme="simple" name="operform" action="${webPath}/mfBusPactChange/insertAjax">
							<dhcc:bootstarpTag property="formchangePactBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusPactChange_Insert.ajaxSave('#MfBusPactChangeForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfBusPactChange_Insert.myclose_reload();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
