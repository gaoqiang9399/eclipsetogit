<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<style type="text/css">
			.textbox{
				display: table-cell;
			}
			#tablist tr td{
				border: 1px solid #e9ebf2;
				height: 40px;
				padding: 0 5px;
			}
			.pad15{
				padding-left: 15px !important;
			}
			.editbox {
				width: 100%;
				border: 1px solid #ddd;
				height: 39px;
				line-height: 39px;
				padding: 6px 4px
			}
			
			.editbox:focus {
				border: 1px solid #00b8ec;
			}
			
			.cw-date-icon{
			    cursor: auto;
			    background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right no-repeat #FFF;
			}
			.voucherLink{
			    cursor: auto;
			    text-decoration: underline;
			    margin: 5px;
			    color: #555;
			}
			.voucherLink:hover{
			    text-decoration: underline;
			}
						
		
		</style>
		<script type="text/javascript" >
			var weeks = '${weeks}';
			$(function(){
				clearSearchForm();
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwCashierJournal/getCashierReportAjax",//列表数据查询的url
			    	tableId:"tablecashier0003",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:999999,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options,data){
			    		$('#tablist>tbody').find('tr').each(function(){
			    			if($(this).children('td:eq(0)').text()=='差额'){
			    				$(this).css({'font-weight': 'bold', 'color': '#000','background-color': '#E0F4F7'});
			    			}
			    		})
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    
			    $('.footer_loader').remove();
			  	//初始化更多查询 控件
				MoreSearch.init();
			 });
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#cwListForm').serializeArray());
			}
			
			function my_Search(){
				updateTableData();//重新加载列表数据
				MoreSearch.colseMoreBtn();
				$('#selected-period').html(joinToWeek($('#weeks').val()));
			}
			
			function clearSearchForm(){
				$('#weeks').val(weeks);
				$('#selected-period').html(joinToWeek(weeks));
			}
			//组装周期
			function joinToWeek(weeks){
				return "<span>" + weeks.substring(0, 4) + "年" + weeks.substring(4) + "期<span>";
			}
			function reloadJsp(){
				clearSearchForm();
				updateTableData();//重新加载列表数据
			}
			
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<ol class="breadcrumb pull-left">
					<li><a href="${webPath}/cwCashierJournal/getListPage">出纳日记账</a></li>
					<li class="active">核对总账</li>
				</ol>
			</div>
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">
				<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
					<div class="mod-toolbar-top">
						<div class="left">
							<form id="cwListForm">
								<!-- 	查询标题 -->
								<span class="txt fl">核对总账</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b> </span>
									<!-- 弹窗  -->
									<div class="search_con">
										<!-- 主要查询条件 -->
										<ul class="filter-list" id="filter-period">
											<li class="li-one-wrap"><label>查询周期:</label> <input type="text" class="form-control form-warp cw-week" readonly name="weeks" id="weeks" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();"></li>
										</ul>
										<!-- 展开的更多条件  -->
										<ul class="filter-list" id="more-conditions">
<!-- 											<li class="li-one-wrap"><label for="#filter-fromLevel">包含凭证:</label> <input type="checkbox" name="noAccountpz" id="noAccountpz" value="1" onclick="checkcwpz(this);">未记账凭证</input> <input type="checkbox" name="wrongpz" id="wrongpz" value="1" disabled="disabled">错误凭证</input></li> -->
										</ul>
										<br>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
<!-- 											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a> -->
											 <a class="ui-btn ui-btn-sp" id="filter-submit" onclick="my_Search();">确定</a> 
											 <a class="ui-btn" id="filter-reset" onclick="initSearchForm();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
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
	</body>	
</html>
