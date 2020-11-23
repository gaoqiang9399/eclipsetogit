<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>

	</head>
	<script type="text/javascript" src="${webPath}/component/cus/cooperating/js/MfCusCooperativeAgency_Insert.js"></script>
	<script type="text/javascript">
        $(function() {
            MfCusCooperativeAgency_Insert.init();
        });

        function selectAreaProviceCallBack(areaInfo){
            $("input[name=careaProvice]").val(areaInfo.disNo);
            $("input[name=careaCity]").val(areaInfo.disName);
        };
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form id="MfCusCooperativeAgency"  method="post" theme="simple" name="operform" action="${webPath}/mfCusCooperativeAgency/updateAjax">
							<dhcc:bootstarpTag property="formcuscoop00003" mode="query" />
						</form>	
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfCusCooperativeAgency_Insert.ajaxSave('#MfCusCooperativeAgency');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>