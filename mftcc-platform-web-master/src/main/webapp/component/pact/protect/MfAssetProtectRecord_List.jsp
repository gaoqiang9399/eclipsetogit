<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/protect/js/MfAssetProtectRecord_list.js"></script>
		<script type="text/javascript" >
			$(function(){
				MfAssetProtectRecord.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfAssetProtectRecord.addAssetProtectRecord();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">资产保全</span>
						</div>
					</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=资产名称/合同编号"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "资产价值",
		"parm" : [],
		"optCode" : "assetAmt",
		"dicType" : "num"
	},/* {
		"optName" : "资产类别",
		"parm" : [],
		"optCode" : "assetType",
		"dicType" : "y_n"
	}, */ {
		"optName" : "处置方式",
		"parm" : ${handleTypeJsonArray},
		"optCode" : "handleType",
		"dicType" : "y_n"
	}, {
		"optName" : "资产状态",
		"parm" : ${debtAssetsJsonArray},
		"optCode" : "assetState",
		"dicType" : "y_n"
	}
	];
</script>
</html>
