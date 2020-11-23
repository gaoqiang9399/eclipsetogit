<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head >
		<title>列表表单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleBill_List.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			var basePath = '<%=basePath %>';
			
			$(function() {
				PssSaleBill_List.init();
			});
		</script>
	</head>
<body class="overflowHidden">
   	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-saleBill-list-add">
							<button type="button" class="btn btn-primary" onclick="PssSaleBill_List.addSaleBill();">新增</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-saleBill-list-audit">
							<span id="auditSpan">
								<button type="button" class="btn btn-default" onclick="PssSaleBill_List.batchAuditSaleBill();">
									审核
									<span class="triangle-down"></span>
								</button>
								<button id="reverseAuditButton" type="button" class="pss-hide-btn" onclick="PssSaleBill_List.batchReverseAuditSaleBill();">
									反审核
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-saleBill-list-print">
							<button type="button" class="btn btn-default" onclick="PssSaleBill_List.batchPrintBill();">打印</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-saleBill-list-import">
							<span id="importSpan">
								<button type="button" class="btn btn-default" onclick="PssSaleBill_List.importExcel();">
									导入
									<span class="triangle-down"></span>
								</button>
								<button id="exportButton" type="button" class="pss-hide-btn" onclick="PssSaleBill_List.exportExcel();">
									导出
									<span class="triangle-none"></span>
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-saleBill-list-delete">
							<button type="button" class="btn btn-default" onclick="PssSaleBill_List.batchDeleteSaleBill();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号/客户名/备注"/>
				</div>
			</div>	
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content pss_list">
				</div>
			</div>
		</div>
    </div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		filter_dic = [ {
			"optCode" : "billDate",
			"optName" : "单据日期",
			"dicType" : "date"
		}, {
			"optCode" : "salerNo",
			"optName" : "销售人员",
			"parm" : ${salerNoJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "auditStsed",
			"optName" : "是否审核",
			"parm" : ${auditStsedJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "receivedState",
			"optName" : "收款状态",
			"parm" : ${receivedStateJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "regOpNo",
			"optName" : "制单人",
			"parm" : ${regOpNoJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "auditOpNo",
			"optName" : "审核人",
			"parm" : ${auditOpNoJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "lstModOpNo",
			"optName" : "最后修改人",
			"parm" : ${lstModOpNoJsonArray},
			"dicType" : "y_n"
		}];
	</script>
</html>