<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>--%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详情</title>
	</head>
	<script type="text/javascript">
        $(function() {
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
        });
	</script>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="saveMfCusReputationInfo" theme="simple" name="operform" action="${webPath}/mfCusReputationInfo/updateAjax">
						<dhcc:bootstarpTag property="formcusReputationBase" mode="query"/>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#saveMfCusReputationInfo');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
</html>