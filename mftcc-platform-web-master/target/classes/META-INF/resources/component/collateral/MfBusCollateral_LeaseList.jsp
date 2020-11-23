<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/collateral/js/MfBusCollateralList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var entranceType='${entranceType}';
    var classFirstNo='F';
    var tableId  = "tableleaseInfoBaseList";
	$(function() {
        MfBusCollateralList.init();
	});


</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
 					<div class="col-md-2">
						<c:if test='${subEntrance ne "query"}'>
							<dhcc:pmsTag pmsId="lease-lease-add-btn">
								<button type="button" class="btn btn-primary" onclick="MfBusCollateralList.pledgeInsert(this);">新增</button>
							</dhcc:pmsTag>
						</c:if>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">租赁物管理</span>
					</div>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=资产名称/关联客户"/>
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
	filter_dic = [ 
	{
		"optCode" : "keepStatus",
		"optName" : "租赁物状态",
		"parm" : ${keepStatusJsonArray},
		"dicType" : "y_n"
	},{
		"optCode" : "importDate",
		"optName" : "入库日期",
		"dicType" : "date"
	},{
		"optCode" : "exportDate",
		"optName" : "出库日期",
		"dicType" : "date"
	},{
		"optCode" : "pledgeMethod",
		"optName" : "担保方式",
		"parm" : ${pledgeMethodJsonArray},
		"dicType" : "y_n" 
	},{
		"optCode" : "regDate",
		"optName" : "登记日期",
		"dicType" : "date"
	}];
</script>
</html>
