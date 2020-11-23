<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/cashier/css/CwCashierJournal_List.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysPenaltyChild_List.js"></script>
		
		<script type="text/javascript" >
			$(function(){
				var idMain = '${idMain}';
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfSysPenaltyChild/findByPageAjax?idMain="+idMain,//列表数据查询的url
			    	tableId:"tablepenaltychild0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:999999,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options,data){
			    		addTDEvent();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			   
			    $('.footer_loader').remove();
			 });
			
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#penaltyChilidListForm').serializeArray());
			} 
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<!-- <div class="btn-div column">
				<button type="button" class="btn btn-primary" onclick="toCashierAccount();">添加账户</button>
			</div> -->
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">
				<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
					<div class="mod-toolbar-top">
						<div class="left">
							<form id="penaltyChilidListForm">
								<!-- 	查询标题 -->
								<span class="txt fl">产品名称:${kindName}</span>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
		<!-- <div class="formRowCenter">
		   <input type="button" value="保存" onclick="saveSysOrg('#saveSysOrgForm');">
		   <input type="button" value="关闭" onclick="myclose_click();" class="cancel">
		</div> -->
	</body>	
</html>
