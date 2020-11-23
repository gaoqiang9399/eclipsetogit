<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>征信结果</title>
		<!-- <style type="text/css">
			.footer_loader,.backToTop{
				bottom:50px;
			}
		</style> -->
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/CreditResultHis.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
	</head>
	<script type="text/javascript">
		var cusNo="${cusNo}";
		var appId="${appId}";
		var queryId="${queryId}";
		var busEntrance="${busEntrance}";
		var creditQueryFlag="${creditQueryFlag}";
		var cusBaseType="${mfCusCustomer.cusBaseType}";
		/* var ajaxData = JSON.parse('${ajaxData}'); */
		$(function(){
			CreditResultHis.init();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container block-left padding_right_10">
	   		<div class="scroll-content">
				<!-- 客户业务汇总信息 -->
		   		<div id="cusAppLyInfo" class="col-md-12 column ">
					<div class="btn-div">
						<dhcc:pmsTag pmsId="start-of-biz">
							<button type="button" class="btn btn-primary" onclick="MfCreditQueryRecordInfo.creditQueryForBaiHang();">百行征信查询</button>
						</dhcc:pmsTag>
					</div>
					<div class="row clearfix bg-white tabCont">
						<div class="col-md-12 column">
							<div class="search-div">
								<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=查询客户名称/查询时间"/>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div id="creditQueryHisContentList" class="table_content"  style="height: auto;">
								<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
							</div>
						</div>
					</div>
				</div>
		   		<!-- 征信结果 -->
		   		<div id="creditContent" class="col-md-8 col-md-offset-2 margin_top_20" style="display:none">
					<div class="bootstarpTag" id="creditContent">
						
					</div>
				</div>
	   		</div>
	   		<%-- <div id="closeButton" class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div> --%>
	   		<div id="backButton" class="formRowCenter" style="display:none">
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="CreditResultHis.back();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
</html>