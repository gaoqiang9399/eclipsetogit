<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js"></script>
		<script type="text/javascript">
		var id = "${id}";
        var ajaxData = '${ajaxData}';
        ajaxData = eval("("+ajaxData+")");
        //角色列表
        var roleArray =ajaxData.role;
        var opNoType = '${opNoType}';
        var scNo = "0000000001";
        var relNo = "${id}";
        var docParm = 'cusNo=' + id + '&relNo=' + relNo + '&scNo=' + scNo;
		/*var dataDocParm={
				relNo:id,
				docType:'cusHead',
				docTypeName:"",
				docSplitName:"头像",
				query:''
			};*/
			$(function() {
				MfTrenchUser_Insert.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">渠道分润比例表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfTrenchUserForm" theme="simple" name="operform" action="${webPath}/mfTrenchUser/insertAjax">
							<dhcc:bootstarpTag property="formTrenchUserBase" mode="query"/>
						</form>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
								<%--<%@ include file="/component/include/biz_node_plugins.jsp"%>--%>
						</div>
					</div>	
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfTrenchUser_Insert.insertAjax('#MfTrenchUserForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
