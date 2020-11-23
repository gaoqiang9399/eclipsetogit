<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增账户</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/PssAccounts_Detail.js"></script>
		
		<script type="text/javascript">
			$(function() {
				PssAccount_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container form-container">
		
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="pss-bigform-form" style="padding-bottom:0;margin-bottom:0;">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formpssaccounts0002" theme="simple" name="operform" action="${webPath}/pssAccounts/saveAccountAjax">
								<dhcc:bootstarpTag property="formpssaccounts0002" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<div class="formRowCenter">
				<dhcc:pmsTag pmsId="pss-accounts-insert">
					<dhcc:thirdButton value="保存" action="保存" onclick="PssAccounts_Detail.saveAccount('#formpssaccounts0002');"></dhcc:thirdButton>
				</dhcc:pmsTag>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>