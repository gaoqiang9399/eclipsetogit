<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>进销存概况页面</title>
<%
	String layout = "layout/view";
%>
<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${webPath}/component/pss/js/echarts-3.7.2.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
<script type="text/javascript" src="${webPath}/component/pss/general/js/PssQueryEntrance.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/component/pss/general/css/PssParamEntrance.css" />
<link rel="stylesheet" href="${webPath}/component/pss/images/font-awesome-4.7.0/css/font-awesome.min.css" />
<style type="text/css">
	
</style>
</head>

<body class="modal-open" style="background-color:#FFF">
<div id="container" class="container">
	<div id="firstRow" class="row clearfix">
        <div id="storeWarning" class="btn btn-app" onclick="PssQueryEntrance.openLabel('1');" style="float:left">
            <span class="badge" style="background-color:#EB7f7E"></span>
            <div><i class="i i-fangkuai3" style="color:#EB7f7E"></i></div>
            <div>库存预警</div>
        </div>
        <div id="unSendSaleBill" class="btn btn-app" onclick="PssQueryEntrance.openLabel('2');" style="float:left">
            <span class="badge" style="background-color:#3DB599"></span>
            <div><i class="i i-cangKu" style="color:#3DB599"></i></div>
            <div>未发货销货订单</div>
        </div>
        <div id="unExamBuyBill" class="btn btn-app" onclick="PssQueryEntrance.openLabel('3');" style="float:left">
            <span class="badge" style="background-color:#9F79EE"></span>
            <div><i class="i i-bianji2" style="color:#9F79EE"></i></div>
            <div>未审核购货订单</div>
        </div>
        <div id="unExamSaleBill" class="btn btn-app" onclick="PssQueryEntrance.openLabel('4');" style="float:left">
            <span class="badge" style="background-color:#48D1CC"></span>
            <div><i class="i i-budgetmanage" style="color:#48D1CC"></i></div>
            <div>未审核销货订单</div>
        </div>
        <div id="unExamBuyOrder" class="btn btn-app" onclick="PssQueryEntrance.openLabel('5');" style="float:left">
            <span class="badge" style="background-color:#EEAD0E"></span>
            <div><i class="i i-dengji" style="color:#EEAD0E"></i></div>
            <div>未审核购货单</div>
        </div>
    </div>
    <div id="secondRow" class="row clearfix">
    	<div id="reportArea" class="col-md-8 col-md-offset-2 column mysearch1">
        	<div class="search-title">
                <ul class="nav nav-tabs">
                    <li id="menu-sale"><a class="active">销售</a></li>
                    <li id="menu-buy"><a>采购</a></li>
                    <li id="menu-store"><a>仓库</a></li>
                </ul>
            </div>
            <div id="mine-sale" class="mysearch-content" style="display:block;">
                <div id="mine-sale1">
                    
                </div>
            </div>
            <div id="mine-buy" class="mysearch-content" style="display:none;">
                
            </div>
            <div id="mine-store" class="mysearch-content" style="display:none;">
                <div id="mine-store1">
                    
                </div>
            </div>
        </div>
        <div id="infoArea" class="col-md-4 col-md-offset-2 column mysearch2">
        	<div class="search-title">
                <ul class="nav nav-tabs">
                    <!-- <li id="menu-fuc"><a class="active" style="padding:10px 35.1px;border-radius:0px;border:none;width:107.2px;">功能</a></li>
                    <li id="menu-data"><a style="padding:10px 35.1px;border-radius:0px;border:none;">数据</a></li> -->
                    <li id="menu-fuc" class="li-active" style="width: 104.5px; height: 40px; text-align: center; line-height: 272%; float: left; cursor: pointer;">功能</li>
                    <li id="menu-data" style="width: 104.5px; height: 40px; text-align: center; line-height: 272%; float: left; cursor: pointer;">数据</li>
                </ul>
            </div>
            
            <div id="home-content">
                <div id="home-quick-link" class="home-quick-link">
                    <div>
                        <ul id="home-link-item">
                            <li id="purchase/purchase" class="home-link-item-li">
                                <div class="i i-tihuo" style="color: #7CFC00;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('1');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('1');">购货单</div>
                            </li>
                            <li id="sales/sales" class="home-link-item-li">
                                <div class="i i-selfinc" style="color: #FF7256;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('2');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('2');">销货单</div>
                            </li>
                            <li id="storage/transfers" class="home-link-item-li">
                                <div class="i i-assets" style="color: #FFC125;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('3');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('3');">调拨单</div>
                            </li>
                            <li id="money/receipt" class="home-link-item-li">
                                <div class="i i-banian" style="color: #6495ED;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('4');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('4');">收款单</div>
                            </li>
                            <li id="storage/inventory" class="home-link-item-li">
                                <div class="i i-assets" style="color: #76EE00;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('5');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('5');">盘点</div>
                            </li>
                            <li id="purchase/purchaseView" class="home-link-item-li">
                                <div class="i i-tihuo" style="color: #EE00EE;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('6');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('6');">以销定购</div>
                            </li>
                            <li id="setting/accounts" class="home-link-item-li">
                                <div class="i i-banian" style="color: #4876FF;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('7');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('7');">付款单</div>
                            </li>
                            <li id="setting/addedServiceList" class="home-link-item-li">
                                <div class="i i-banian" style="color: #9400D3;font-size: 48px;text-align: center;cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('8');"></div>
                                <div class="text" style="cursor: pointer;" onclick="PssQueryEntrance.openQuickLink('8');">核销单</div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="home-key-data" class="home-key-data">
                    <div style="position:absolute;top:9%;">
                        <ul id="home-keydata" class="home-key-data-ul">
                            <li id="storage/initialBalance" class="home-key-data-li">
                                <div class="home-key-data-li-div">
                                    <span id="storeCountText" style="float:left;">库存总量：</span>
                                    <span id="storeCountValue" style="float:right;"></span>
                                </div>
                                <div class="home-key-data-li-div">
                                    <span id="storeCostText" style="float:left;">库存成本：</span>
                                    <span id="storeCostValue" style="float:right;"></span>
                                </div>
                            </li>
                            <li id="money/cashBankJournal" class="home-key-data-li">
                                <div class="home-key-data-li-div">
                                    <span id="cashText" style="float:left;">现金：</span>
                                    <span id="cashValue" style="float:right;"></span>
                                </div>
                                <div class="home-key-data-li-div">
                                    <span id="depositText" style="float:left;">银行存款：</span>
                                    <span id="depositValue" style="float:right;"></span>
                                </div>
                            </li>
                            <li id="sales/contactDebt" class="home-key-data-li">
                                <div class="home-key-data-li-div">
                                    <span id="cusDebtText" style="float:left;">客户欠款：</span>
                                    <span id="cusDebtValue" style="float:right;"></span>
                                </div>
                                <div class="home-key-data-li-div">
                                    <span id="supplierDebtText" style="float:left;">供应商欠款：</span>
                                    <span id="supplierDebtValue" style="float:right;"></span>
                                </div>
                            </li>
                            <li id="sales/salesSummary" class="home-key-data-li">
                                <div class="home-key-data-li-div">
                                    <span id="saleIncomeText" style="float:left;">销售收入(本月)：</span>
                                    <span id="saleIncomeValue" style="float:right;"></span>
                                </div>
                                <div class="home-key-data-li-div">
                                    <span id="saleGrossText" style="float:left;">销售毛利(本月)：</span>
                                    <span id="saleGrossValue" style="float:right;"></span>
                                </div>
                            </li>
                            <li id="purchase/puSummary" class="home-key-data-li">
                                <div class="home-key-data-li-div">
                                    <span id="buyAmountText" style="float:left;">采购金额(本月)：</span>
                                    <span id="buyAmountValue" style="float:right;"></span>
                                </div>
                                <div class="home-key-data-li-div">
                                    <span id="goodsTypeText" style="float:left;">商品种类(本月)：</span>
                                    <span id="goodsTypeValue" style="float:right;"></span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(function(){
		PssQueryEntrance.init();
		PssQueryEntrance.initReport();
		PssQueryEntrance.initKeyDataCount();
	});
	var webPath = '${webPath}';	
</script>
</body>
</html>