<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
<script type="text/javascript" >
	var storeIds = '${dataMap.storeIds}';
	
	$(function() {
		//初始化仓库
		/* if(storeIds){
			var storeIdArr = eval(storeIds);
			var opt = $('#storehouseId')[0];
			opt.options.length = 0;
			for(var i = 0;i < storeIdArr.length; i++){
				opt.add(new Option(storeIdArr[i].storehouseName,storeIdArr[i].storehouseId));
			}
		} */
		
		//加载列表
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssCheckStockBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepsscheckstockbill0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight:100//顶部区域的高度，用于计算列表区域高度。
		});
		
		/* $('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: true,
				min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
				max: currDate + ' 23:59:59', //最大日期 
				choose:function(data){
				}	
			});
		}); */
		
	});

	//获取 查询条件（方法名固定写法）
	/* function getFilterValArr() {
		return JSON.stringify($('#pssCSBListForm').serializeArray());
	};
	
	function my_Search(){
		updateTableData();
	}; */
	
	//新增
	function addCSB(){
		top.isFresh = true;
		window.parent.openBigForm(webPath+'/pssCheckStockBill/input', '盘点', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
	//详情
	function getCKB(url){
		top.isFresh = true;
		window.parent.openBigForm(url, '盘点', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
	//盘点单据
	function getCKBOther(url){
		top.isFresh = true;
		window.parent.openBigForm(url, '盘点单据', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-check-stock-insert">
							<button type="button" class="btn btn-primary" onclick="addCSB();">盘点</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<%-- <div class="search-div">
					<div class="col-xs-10 column mysearch-div" id="pills">
						<div class="mod-toolbar-top">
							<div class="left">
								<form id="pssCSBListForm">
									<input class="items-btn" type="text" id="pssQuery" name="pssQuery" placeholder="单据号" size="25">
									<span class="txt">日期：</span>
									<input class="items-btn pss-date" type="text" id="pssStartDate" name="pssStartDate" readonly value="${dataMap.pssStartDate }">
									<span class="txt">至</span>
									<input class="items-btn pss-date" type="text" id="pssEndDate" name="pssEndDate" readonly value="${dataMap.pssEndDate }">
									<span class="txt">仓库：</span>
									<select class="items-btn" name="storehouseId" id="storehouseId" autocomplete="off" style="width:130px;"></select>
									<!-- <a class="ui-btn" onclick="senior_Search();" id="senior_psssearch">高级搜索</a> -->
									<a class="ui-btn" onclick="my_Search();" id="psssearch">查询</a>
								</form>
							</div>
						</div>
					</div>
				</div> --%>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号" />
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "单据日期",
		"parm" : [],
		"optCode" : "checkStockDate",
		"dicType" : "date"
	},{
		"optCode" : "storehouseId",
		"optName" : "仓库",
		"parm" : ${pssStorehouseJsonArray},
		"dicType" : "y_n"
	}
	];
</script>
</html>