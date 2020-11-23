<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增供应商</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/PssSupplierInfo_Detail.js"></script>
		<style type="text/css">
			/* .pss-bigform-form {
				margin-bottom: -60px;
				padding-bottom: 60px;
			} */
			.pss-bigform-form > div {
				width: 90%;
			}
		</style>
		<script type="text/javascript">
			ajaxData = JSON.parse('${ajaxData}');
			saveOrEditFlag = '${saveOrEditFlag}';
			$(function() {
				PssSupplierInfo_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="pss-bigform-form" style="padding-bottom:0;margin-bottom:0;">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formpsssupplierinfo0002" theme="simple" name="operform" action="${webPath}/pssSupplierInfo/saveSupplierInfoAjax">
								<dhcc:bootstarpTag property="formpsssupplierinfo0002" mode="query" />
							</form>
						</div>
						<div id="content" class="table_content pss_detail_list">
						</div>
					</div>
					<!-- <div class="pss-bigform-table">
						<div id="content" class="table_content pss_detail_list">
						</div>
					</div> -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:pmsTag pmsId="pss-supplier-info-insert">
					<dhcc:thirdButton value="保存" action="保存" onclick="PssSupplierInfo_Detail.saveSupplierInfo('#formpsssupplierinfo0002');"></dhcc:thirdButton>
				</dhcc:pmsTag>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>