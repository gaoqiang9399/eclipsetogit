<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="pss-supplier-info-insert">
						<button type="button" class="btn btn-primary" onclick="PssSupplierInfo.supplierInfoInsert();">新增</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pss-supplier-info-enable">
						<span id="closeSpan">
							<button type="button" class="btn btn-default" onclick="PssSupplierInfo.batchEnableSupplierInfo();">
								启用
								<span class="triangle-down"></span>
							</button>
							<button id="enableButton" type="button" class="pss-hide-btn" onclick="PssSupplierInfo.batchCloseSupplierInfo();">
								关闭
								<span class="triangle-none"></span>
							</button>
						</span>
					</dhcc:pmsTag>
					<!-- <dhcc:pmsTag pmsId="pss-supplier-info-export">
						<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出</button> 
						<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导入</button>
					</dhcc:pmsTag>  -->
					<dhcc:pmsTag pmsId="pss-supplier-info-delete">
						<button type="button" class="btn btn-default" onclick="PssSupplierInfo.deleteBatch();">删除</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=供应商编号/供应商名称" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/pss/js/PssSupplierInfo.js"></script>
	<script type="text/javascript">
	$(function(){
		PssSupplierInfo.init();
	});
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optCode" : "enabledStatus",
		"optName" : "启用状态",
		"parm" : ${pssEnabledStatusJsonArray},
		"dicType" : "y_n"
	}
	];
	</script>
</html>