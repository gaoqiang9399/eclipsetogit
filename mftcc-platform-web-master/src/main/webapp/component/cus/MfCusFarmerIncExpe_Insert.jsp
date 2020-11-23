<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusFarmerIncExpeInsert.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
	<script type="text/javascript">
	var projectName = '${projectName}';
	var cusNo;
    var pageView = '';
		$(function() {
            cusNo=$("input[name=cusNo]").val();
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		});
		function asdInsert(){
			ajaxInsertCusForm('#saveMfCusFarmerIncExpe');
		}
    function insertAndAdd(){
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusFarmerIncExpe/input?cusNo="+cusNo;
        ajaxInserAndAddCusForm('#saveMfCusFarmerIncExpe',inputUrl);
    }
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<%--<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>--%>
						<form  method="post" id="saveMfCusFarmerIncExpe" theme="simple" name="operform" action="${webPath}/mfCusFarmerIncExpe/insertAjax">
							<dhcc:bootstarpTag property="formcusIncExpeBase" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
    			<dhcc:thirdButton value="保存" action="保存" onclick="asdInsert();"></dhcc:thirdButton>
				<dhcc:pmsTag pmsId="return-page">
					<dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
				</dhcc:pmsTag>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    	</div>	
		</div>
		
	</body>
</html>
