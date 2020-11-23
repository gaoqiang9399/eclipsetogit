<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<script type="text/javascript" src="${webPath}/component/pss/information/js/PssSettleAccounts_List.js"></script>

<script type="text/javascript">
	$(function(){
		pssSettleAccounts_List.init();
		
		$('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: false,
				/* min: currDate.substring(0, 8) + '01 00:00:00', //最小日期 */
				max: '${dataMap.pssSettleAccountsDate}', //最大日期 
				choose:function(data){
				}	
			});
		});
	});
</script>
</head>
<body>
   <div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<%-- <div class="btn-div">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-allo-trans-insert">
							<button type="button" class="btn btn-primary" onclick="addATB();">新增</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-allo-trans-check">
							<span id="pssChkATB">
								<button type="button" class="btn btn-default" onclick="checkPssATB();">
									审核
									<span class="triangle-down"></span>
								</button>
								<button id="pssHideChkATB" type="button" class="pss-hide-btn" onclick="reCheckPssATB();">
									反审核
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-allo-trans-print">
							<button type="button" class="btn btn-default" onclick="batchPrintBill();">打印</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-allo-trans-export">
							<span id="pssImpATB">
								<button type="button" class="btn btn-default" onclick="importPssATB();">
									导入
									<span class="triangle-down"></span>
								</button>
								<button id="pssHideImpATB" type="button" class="pss-hide-btn" onclick="exportPssATB();">
									导出
									<span class="triangle-none"></span>
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-allo-trans-delete">
							<button type="button" class="btn btn-default" onclick="deleteBatch();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div> --%>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pss_settle_accounts">
						<div class="mod-toolbar-top">
							<div class="left">
								<form id="pssSAListForm">
									<span class="txt">日期：</span>
									<input class="items-btn pss-date" type="text" id="pssSettleAccountsDate" name="pssSettleAccountsDate" readonly value="${dataMap.pssSettleAccountsDate}">
									<dhcc:pmsTag pmsId="pss-settle-accounts-btn">
									<a class="ui-btn" onclick="pssSettleAccounts_List.settleaccounts();" id="senior_psssearch">结账</a>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="pss-anti-settlement-btn">
									<a class="ui-btn" onclick="pssSettleAccounts_List.antiSettlement();" id="psssearch">反结账 </a>
									</dhcc:pmsTag>
								</form>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号/备注" />
				</div> --%>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
		<%-- <%@ include file="/component/include/PmsUserFilter.jsp"%> --%>
	</div>
</body>
</html>