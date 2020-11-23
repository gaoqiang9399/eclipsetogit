<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<script type="text/javascript" src='${webPath}/component/collateral/js/pleWarrantForm.js'> </script>
	<script type="text/javascript">
		var appId = '${appId}';
		var pactId = '${pactId}';
		var cusNo='${cusNo}';
		var scNo ='${scNo}';//客户要件场景
		var docParm = "cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
		
		$(function() {
			pleWarrant.init();
		});
		
		function getLegalIdType() {
			pleWarrant.getLegalIdType();
		};

		function ifGroupCustomer(obj) {
			pleWarrant.ifGroupCustomer(obj);
		}

		function insertWarrantInfo(obj) {
			pleWarrant.insertWarrantInfo(obj);
		}
		
		function getCusMngNameDialog(userInfo) {
			pleWarrant.getCusMngNameDialog(userInfo);
		};
		
		//从集团客户放大镜赋值给表单属性
		function getGroInfoArtDialog(groupInfo) {
			selectAreaCallBack.getGroInfoArtDialog(groupInfo);
		};
		
		function selectAreaCallBack() {
			pleWarrant.selectAreaCallBack(areaInfo);
		};
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
		            	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="certiInfoInsert" theme="simple" name="operform" action="${webPath}/certiInfo/insertAndDocommit">
							<dhcc:bootstarpTag property="formPle00001" mode="query"/>
						</form>	
					</div>
				</div>	
				<div class="row clearfix">
					<div class="col-md-12  column margin_top_20" >
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertWarrantInfo('#certiInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
