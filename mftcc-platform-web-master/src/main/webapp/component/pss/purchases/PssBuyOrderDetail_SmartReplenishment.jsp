<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
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
		<script type="text/javascript" src="${webPath}/component/pss/purchases/js/PssBuyOrderDetail_SmartReplenishment.js"></script>
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
				PssBuyOrderDetail_SmartReplenishment.init();
			});
		</script>
	</head>
<body class="overflowHidden">
   <div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<button type="button" class="btn btn-primary" onclick="PssBuyOrderDetail_SmartReplenishment.showFormulaBox()">计算公式</button>
						<dhcc:pmsTag pmsId="pss-smartReplenishment-list-generateBuyOrder">
							<button type="button" class="btn btn-primary" onclick="PssBuyOrderDetail_SmartReplenishment.generateBuyOrder();">生成购货订单</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-smartReplenishment-list-export">
							<button type="button" class="btn btn-default" onclick="PssBuyOrderDetail_SmartReplenishment.exportExcel();">导出</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=商品编号/名称"/>
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
		<div class="powerFloat-gray-box dn" id="formulaBox"
			style="position: absolute; left: 120px; top: 20px; z-index: 999; display: none;">
			<p style="font-size: 13px; color: #4d4d4d;">
				<label>
					<span style="font-size: 14px; color: #4d4d4d; font-weight: bold">建议采购量</span>
					= 销售在订量 - 库存余额
				</label> 
				<span style="margin-top: 8px;">
					<span id="purNum"> - 
						<label class="chk checked">
							<input type="checkbox" id="isSubtractBuyOrderQuantity" checked="checked"> 
							采购在订量 +
						</label>
					</span> 
					<span id="minStore">
						<label class="chk">
							<input type="checkbox" id="isAddMinimumStockQuantity" checked="checked">
							最低库存
						</label>
					</span>
				</span>
			</p>
		</div>
		<div id="tipsBox" class="shadow opera-log-box dn" style="position: absolute; left: 700px; top: 50px; z-index: 999; display: none;">
			<p style="white-space: nowrap;">销售在订量：统计该商品所有关联的已生效销货订单的订购数量；</p>
			<p>采购在订量：统计该商品所有关联的已生效采购订单的订购数量；</p>
			<p>最低库存：取商品管理中设置的库存预警值（总仓预警、分仓预警或按属性预警）。</p>
		</div>
	</div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		filter_dic = [ {
			"optCode" : "storehouseId",
			"optName" : "仓库",
			"parm" : ${storehouseJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "goodsType",
			"optName" : "商品类别",
			"parm" : ${goodsTypeJsonArray},
			"dicType" : "y_n"
		}];
	</script>
</html>