<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="btn-div">
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/cwVoucherMst/getListPage">凭证查询</a></li>
						<li class="active">现金流量分析</li>
					</ol>
				</div>
				<div class="search-div">
					<form id="cwListForm">
						<div class="col-xs-9 column mysearch-div" id="pills">
							<!-- 更多查询条件功能开始 -->
							<div class="mod-toolbar-top">
								<div class="left">
									<!-- 查询标题 -->
									<span class="txt fl" id="report_title">现金流量分析</span>
									<div class="ui-btn-menu fl" id="filter-menu">
										<!-- 显示条件 -->
										<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b> </span>
										<!-- 弹窗  -->
										<div class="search_con">
											<!-- 主要查询条件 -->
											<ul class="filter-list" id="filter-period">
												<li class="li-one-wrap"><label>查询周期:</label> <input type="text" class="form-control form-warp cw-week" readonly name="week" id="week" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();"></li>
											</ul>
											<!-- 展开收起、重置、确定按钮 -->
											<div class="btns">
												<a class="ui-btn ui-btn-sp" id="filter-submit" onclick="searchData();">确定</a> <a class="ui-btn" id="filter-reset" onclick="clearSearchForm();" tabindex="-1" style="display: inline;">重置</a>
											</div>
										</div>
									</div>
									<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl" id="refresh"><b></b></a>
								</div>
							</div>
							<!-- 更多查询条件功能结束 -->
						</div>
						<div class="col-xs-3 column znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i> <input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索" name="search"> <span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content cw_voucher_list" style="height: auto;">
					<!--待定是否放置自定义table标签?-->
					<%-- 					<dhcc:tableTag property="tablevoucher0001" paginate="CwVoucherMstList" head="true"></dhcc:tableTag> --%>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var myDate = new Date();
	var mon = myDate.getMonth() + 1;
	if (mon < 10) {
		mon = "0" + mon;
	}

	var weeks = myDate.getFullYear()+""+ mon;
	$(function() {
		clearSearchForm();
		//加载列表
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/cwVoucherMst/getCashFlowAnalysisListAjax",//列表数据查询的url
			tableId : "tablecashflowanalasis",//列表数据查询的table编号
			tableType : "tableTag",//table所需解析标签的种类
			// 			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			pageSize : 10,//加载默认行数(不填为系统默认行数)
			ownHeight : true,
			callback : function() {
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		//初始化更多查询 控件
		MoreSearch.init();
	});

	//获取 查询条件（方法名固定写法）
	function getFilterValArr() {
		return JSON.stringify($('#cwListForm').serializeArray());
	}
	function searchData() {
		updateTableData();//重新加载列表数据
		$('#selected-period').html(joinToWeek($('#week').val()));
		MoreSearch.colseMoreBtn();
	}
	//现金流量分析窗口
	function toAnalysis(url) {
		window.parent.openBigForm(webPath+"/"+url, '现金流量分析', closeCallBack);
	}

	function clearSearchForm() {
		$('#week').val(weeks);
		$('#selected-period').html(joinToWeek(weeks));
	}
	//组装周期
	function joinToWeek(weeks) {
		
		return "<span>" + weeks.substring(0, 4) + "年" + weeks.substring(4)
				+ "期<span>"; 
	}
	function reloadJsp() {
		clearSearchForm();
		updateTableData();//重新加载列表数据
	}
	//关闭弹窗
	function closeCallBack() {
		updateTableData();//重新加载列表数据
	};
</script>
</html>