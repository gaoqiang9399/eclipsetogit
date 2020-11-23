<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE>
<html>
	<head>
		<title>辅助核算余额表列表</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<style type="text/css">
			.btn-div .more-btn{
				margin-left: 4px;
				float: left;
			}
			.btn-div .show-btn{
				float: left;
			}
			.more-btn > .dropdown_ul{
				min-width: 68px;
				right: 0;
				left: auto;
			}
			.more-btn > .dropdown_ul > li > a{
				padding: 5px 5px;
			}
			.more-btn > .dropdown_ul > li > a:HOVER{
				background: #e3e7eb;
			}
			#tablist th{
				height: 30px;
		    	line-height: 30px;
			}
			
			#tablist tr td{
				border: 1px solid #e9ebf2;
			}
			.items-btn{
				display: inline-block;
				margin-right: 10px;
				padding-left:13px; 
				width:80px;
				height: 28px;
				border: 1px solid #c3c7cb;
				border-radius: 2px;
				background: #fafdff;
				vertical-align: middle;
				cursor: pointer
			}
			#tablist>thead>tr>th{
				font-weight:bold;
			} 
			.table_content .ls_list tr td{
				padding-right:10px;
			}
		</style>
		<script type="text/javascript" >
			var fications = '${dataMap.fications}';
			$(function(){
				//初始化辅助核算查询条件
				if(fications){
					var pact = eval(fications);
					var opt = $('#txType')[0];
					opt.options.length=0;
					for(var i=0; i<pact.length; i++){
						opt.add(new Option(pact[i].typeName, pact[i].txType));
					}
				}
				//清空 并 初始化查询期
				initSearchForm();
			    myCustomScrollbar({
			    	obj:"#content1",//页面内容绑定的id
			    	url:webPath+"/assistAccountDetail/getAccountBalanceAjax",//列表数据查询的url
			    	tableId:"tableassistAccountBalance0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	//data:{"weeks":"201612"},//指定参数
			    	ownHeight:true,
			    	callback:function(options,data){
			    	//$('#content1 tbody').append('<tr><td width="10%" align="center"></td><td width="20%" align="left">总计</td><td width="10%" align="center">0.00</td><td width="10%" align="center">0.00</td></tr>');
				
					$('.footer_loader').hide();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    $(".table-float-head").hide();
			    dealTableTh();
				 //初始化更多查询 控件
				MoreSearch.init();
				$('.comitem_select').on('click', function() {
					openComItemDialog('3', function(data) {
						if (data) {
							$('#accNo').val(data.id);
							$('#fontAccName').text(data.id + " " + data.name);
						}
					});
				})
				$('#valueNo_search').on('click', function() {
					var txType = $('#txType').val();
					openAssistDialog(txType, function(data) {
						if (data) {
							//$('#accNo').val(data.txCode);
							$('#txCode').val(data.txCode);
							$('#fontAccName').text(data.txCode + " " + data.txName);
						}
					});
				})
			 });
			 //获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#cwListForm').serializeArray());
			}
		</script>
	</head>
	<body>

	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<!-- 					<button type="button" class="btn btn-default" onclick="">打印</button> -->
				<!-- 					<button type="button" class="btn btn-default" onclick="">导出</button> -->
				<ol class="breadcrumb pull-left">
					<li><a href="${webPath}/component/finance/othreport/CwBooksEntrance.jsp" style="color: #32b5cb;">账簿</a></li>
					<li class="active">辅助核算余额表</li>
				</ol>
			</div>
			<!-- 我的筛选选中后的显示块 -->
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">
				<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
					<div class="mod-toolbar-top">
						<div class="left">
							<form id="cwListForm">
								<!-- 	查询标题 -->
								<span class="txt fl">辅助核算余额表</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b> </span>
									<!-- 弹窗  -->
									<div class="search_con">
										<!-- 主要查询条件 -->
										<ul class="filter-list" id="filter-period">
											<li class="li-two-wrap"><label>会计期间:</label> <input type="text" class="form-control form-warp cw-week" readonly name="opnWeek" id="opnWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();"> <span>至</span> <input type="text" class="form-control form-warp cw-week" readonly name="endWeek" id="endWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();"></li>
											<li class="li-one-wrap"><label for="#filter-fromLevel">辅助项目:</label> <input type="text" class="form-control form-warp" name="txCode" id="txCode" autocomplete="off"> <span class="glyphicon glyphicon-search search-addon" id="valueNo_search" for="txCode"></span></li>
										</ul>
										<!-- 展开的更多条件  -->
										<ul class="filter-list" id="more-conditions">
											<li class="li-one-wrap"><label for="#filter-fromSubject">科目:</label> <input class="form-control form-warp" type="text" onclick="autoComPleter(this, '3',changeFont)" id="accNo" name="accNo" autocomplete="off" /> <span class="glyphicon glyphicon-search search-addon comitem_select"></span></li>
<!-- 											<li class="li-one-wrap"><label for="#filter-fromLevel">包含凭证:</label> <input type="checkbox" name="noAccountpz" id="noAccountpz" value="1" onclick="checkcwpz(this);">未记账凭证</input> <input type="checkbox" name="wrongpz" id="wrongpz" value="1" disabled="disabled">错误凭证</input></li> -->
										</ul>
										<br>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a> <a class="ui-btn ui-btn-sp" id="filter-submit" onclick="sure_Search();">确定</a> <a class="ui-btn" id="filter-reset" onclick="initSearchForm();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
								<span class="txt fl">辅助类别:</span> <select class="items-btn menu-btn fl" name="txType" id="txType" autocomplete="off"></select> <a onclick="sure_Search();" class="ui-btn ui-btn-refresh fl" id="refresh">查询</a> <span class="txt fl">「辅助项目：<font id="fontAccName">未选</font>」
								</span>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--页面显示区域-->
	<div id="content1" class="table_content" style="height: auto;"></div>
</body>
<script type="text/javascript">
	var searchWeek = '${dataMap.weeks}';
	function initSearchForm() {
		//把查询条件中参数 清空，并初始化查询条件
		$('#cwListForm').find('input').each(function() {
			$(this).val('');
		});
		$('#txType')[0].selectedIndex = 2;
		$('#opnWeek').val(searchWeek);
		$('#endWeek').val(searchWeek);
		$('#selected-period').html(joinToWeek(searchWeek));
	}
	//组装周期
	function joinToWeek(weeks) {
		return "<span>" + weeks.substring(0, 4) + "年" + weeks.substring(4)
				+ "期<span>";
	}
	
	function dealTableTh() {
		var floatTr = $("#tablist>thead").children("tr");
		floatTr.find('th:lt(2)').remove();
		var head = $("<tr>"
				+ "<th scope='col'  align='center' rowspan='2'>编码</th>"
				+ "<th scope='col'  align='center' rowspan='2'>项目名称</th>"
				+ "<th scope='col'  align='center' colspan='2'>期初余额</th>"
				+ "<th scope='col'  align='center' colspan='2'>本期发生额</th>"
				+ "<th scope='col'  align='center' colspan='2'>本年累计发生额</th>"
				+ "<th scope='col'  align='center' colspan='2'>期末余额</th>"
				+ "</tr>");
		floatTr.before(head);
	}
	
	//更多条件查询：点击确定以后
	function sure_Search() {
		var begin = $('#opnWeek').val();
		var end = $('#endWeek').val();
		if(checkQueryWeeks(begin, end)){
			if (begin == end) {
				$('#selected-period').html(joinToWeek(begin));
			} else {
				$('#selected-period').html(
						joinToWeek(begin) + " 至 " + joinToWeek(end));
			}
			updateTableData();//重新加载列表数据
			MoreSearch.colseMoreBtn();
		}
	}
	
	/*未记账凭证和错误凭证选择*/
	function checkcwpz(obj) {
		if (obj.checked) {
			$("input[name='wrongpz']")[0].disabled = false;
			$("input[name='wrongpz']")[0].checked = true;
		} else {
			$("input[name='wrongpz']")[0].disabled = true;
			$("input[name='wrongpz']")[0].checked = false;
		}
	}
	
	//刷新数据列表
	function reloadJsp() {
		initSearchForm();
		updateTableData();//重新加载列表数据
		$('#fontAccName').text("未选");
	}
	
	function changeFont(selected) {
		//alert(JSON.stringify(selected))
		$('#accNo').val(selected.accNo);
	//		$('#fontAccName').text(selected.accNo + " " + selected.accName);
	}
</script>

</html>
