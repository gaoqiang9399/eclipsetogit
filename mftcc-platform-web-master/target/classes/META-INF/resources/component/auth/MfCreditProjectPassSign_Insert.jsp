<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCreditProjectPassSign_Insert.js'></script>
		<script  type="text/javascript">
			var cusNo = '${cusNo}';
			var currBrNo = '${currBrNo}';
			var creditAppId = '${creditAppId}';
			var wkfAppId = '${wkfAppId}';
			var relNo = creditAppId;
			var appId =creditAppId;
			//var sysUserList = JSON.parse('${sysUserList}');
			$(function(){
				MfCreditProjectPassSign_Insert.init();				
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
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">立项授信传签表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="operform" theme="simple" name="operform" action="${webPath}/mfCreditProjectPassSign/updateAjax">
							<dhcc:bootstarpTag property="formpassSignBase" mode="query"/>
						</form>
					</div>
					<div class="bigform_content col_content">
						<!-- <div class="title"><h5 >检查指标子项</h5>
						</div> -->
						<div id=passSignList" class="table_content">
							<dhcc:tableTag paginate="mfCreditProjectPassSignList" property="tablepassSignInputList" head="true" />
						</div>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCreditProjectPassSign_Insert.ajaxInsert('#operform')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
