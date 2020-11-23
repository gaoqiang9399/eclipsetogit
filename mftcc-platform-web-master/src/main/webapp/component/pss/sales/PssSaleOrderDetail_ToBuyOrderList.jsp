<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head >
		<title>以销订购列表</title>
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleOrderDetail_ToBuyOrderList.js"></script>
		<style type="text/css">
			#tablist>thead>tr>th{
				font-weight: bold;
				font-size: 13px;
				height: 40px;
		    	line-height: 40px;
			}
			
			.pss_list .ls_list tr td{
				border-left: 1px solid #e9ebf2;
			}
			
			.pss_list .ls_list thead th{
				padding: 0 3px;
			}
		</style>
		<script type="text/javascript" >
		    var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
			$(function() {
				PssSaleOrderDetail_ToBuyOrderList.init();
			});
		</script>
	</head>
<body class="overflowHidden">
   <div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-saleToBuyOrder-list-add">
							<button type="button" class="btn btn-primary" onclick="PssSaleOrderDetail_ToBuyOrderList.generateBuyOrder();">生成购货订单</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=商品名称/销售订单号"/>
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
			"optCode" : "deliveryDate",
			"optName" : "交货日期",
			"dicType" : "date"
		}];
	</script>
</html>