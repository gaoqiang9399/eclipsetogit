<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
	<link rel="stylesheet" href="css/PssParamEntrance.css" />
	<style type="text/css">
		.re_dialog .ui_icon {
		    vertical-align: top;
		}
		.re_dialog .ui_icon img {
		    width: 48px;
		    height: 48px;
		}
		.re_dialog .ui_icon_bg {
		    margin: 20px 15px 20px 15px;
		}
		.re_dialog .ui_content {
		    display: inline-block;
		    zoom: 1;
		    text-align: left;
		    font-size: 14px;
		    line-height: 1.8;
		}
		.re_dialog .re-initialize h4 {
		    color: red;
		    margin-bottom: 10px;
		    font-size: 16px;
		    font-weight: 700;
		}
		.re_dialog .re-initialize ul li {
		    list-style: square;
		}
		.re_dialog .re-initialize ul {
		    padding-left: 20px;
		    margin-bottom: 10px;
		}
		
		.re_dialog .re-initialize .check-confirm {
	 	    visibility: hidden; 
		    color: red;
		    font-size: 12px;
		}
		
		.re_dialog .ui_buttons {
		    padding: 4px 8px;
		    text-align: right;
		    white-space: nowrap;
		}
	
		.sysInitForm{
			padding: 15px 0;
		}
		.sysInitForm .form_item{
			text-align: center;
			margin-top: 15px;
		}
		.sysInitForm input{
			width: 200px;
			display: inline-block;
		}
		.sysInitForm select{
			width: 200px;
			display: inline-block;
		}
	</style>
	<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/pssGoods/getListPage';
			break;
		case "2":
			window.location = webPath+'/pssStorehouse/getListPage';
			break;
		case "3":
			window.location = webPath+'/pssConfig/getConfPage';
			break;
		case "4":
			window.location = webPath+'/pssBillnoConf/getListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/pssCustomerInfo/getListPage';
			break;
		case "6":
			window.location = webPath+'/pssSupplierInfo/getListPage';
			break;
		case "7":
			window.location = webPath+'/pssUnit/getListPage';
			break;
		case "8":
			window.location = webPath+'/sysOrg/listSysOrg';
			break;
		case "9":
			window.location = webPath+'/pssAccounts/getListPage';
			break;
		case "10":
			window.parent.openBigForm(webPath+'/pssRecalculateCost/getListPage', '成本重算', function(){});
			break;
		case "11":
			window.location = webPath+'/pssSettleAccounts/getListPage';
			break;
		}
	}
</script>
</head>
<body >
<div class="container">
	<dhcc:pmsTag pmsId="pss-goods-menu">
		<div class="btn btn-app" id="goodsInfo" onclick="openReport(this, '1','商品管理');">
			<div><i class="i i-rss"></i></div>
			<div><i class="i i-fangkuai3" style="color: #43ACBC;"></i></div>
			<div>商品管理</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-storehouse-menu">
		<div class="btn btn-app" id="stockInfo" onclick="openReport(this, '2','仓库管理');">
			<div><i class="i i-cangKu" style="color: #43ACBC;"></i></div>
			<div>仓库管理</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-customer-menu">
		<div class="btn btn-app" id="psscustomerinfo" onclick="openReport(this, '5','客户管理');">
			<div><i class="i i-selcus" style="color: #43ACBC;"></i></div>
			<div>客户管理</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-supplier-menu">
		<div class="btn btn-app" id="psscustomerinfo" onclick="openReport(this, '6','供应商管理');">
			<div><i class="i i-selcus" style="color: #43ACBC;"></i></div>
			<div>供应商管理</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-billrule-menu">
		<div class="btn btn-app" id="pssbillno" onclick="openReport(this, '4','单据编码生成规则');">
			<div><i class="i i-bianji2" style="color: #43ACBC;"></i></div>
			<div>单据编号生成规则</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-unit-menu">
		<div class="btn btn-app" id="pssunit" onclick="openReport(this, '7','计量单位');">
			<div><i class="i i-bi1" style="color: #43ACBC;"></i></div>
			<div>计量单位</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-baseconf-menu">
		<div class="btn btn-app" id="pssconf" onclick="openReport(this, '3','基础设置');">
			<div><i class="i i-chilun" style="color: #43ACBC;"></i></div>
			<div>基础设置</div>
		</div>
	</dhcc:pmsTag>	
	<dhcc:pmsTag pmsId="pss-organization-menu">
		<div class="btn btn-app" id="sysorg" onclick="openReport(this, '8','机构设置');">
			<div><i class="i i-chilun" style="color: #43ACBC;"></i></div>
			<div>机构设置</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-account-menu">
		<div class="btn btn-app" id="pssaccount" onclick="openReport(this, '9','账户设置');">
			<div><i class="i i-zhanghu" style="color: #43ACBC;"></i></div>
			<div>账户设置</div>
		</div>	
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-recalculationcost-menu">
		<div class="btn btn-app" id="pssrecalculationcost" onclick="openReport(this, '10','重算成本');">
			<div><i class="i i-fanzhuanrang" style="color: #43ACBC;"></i></div>
			<div>重算成本</div>
		</div>
	</dhcc:pmsTag>
	<dhcc:pmsTag pmsId="pss-settleaccounts-menu">
		<div class="btn btn-app" id="psssettleaccounts" onclick="openReport(this, '11','结账反结账');">
			<div><i class="i i-fengdang" style="color: #43ACBC;"></i></div>
			<div>结账/反结账</div>
		</div>
	</dhcc:pmsTag>	
</div>
</body>
</html>