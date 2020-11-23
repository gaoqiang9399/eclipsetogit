<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		
	<%-- 	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" /> --%>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/cashier/css/CwCashierJournal_List.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/cashier/js/CwCashierJournal_List.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		
		<script type="text/javascript" >
			var weeks = '${dataMap.weeks}';
			$(function(){
				clearSearchForm();
				var ajaxData = '${ajaxData}';
				var uid = '${uid}';
				ajaxData = eval("("+ajaxData+")");
				$("input[name=accountNo]").val(uid);
			 	$("input[name=accountNo]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//dan选选
					items:ajaxData.accountData,
					changeCallback : function (obj) {//回调
						$("input[name=accountName]").val(obj.data("text"));
						var objid= obj.val();
						$("input[name=accountNo]").val(objid);
						updateTableData();//重新加载列表数据
					},
					addBtn:{//添加扩展按钮
						"title":"添加账户",
						"fun":function(d){
							toCashierAccount();
						}
					}
				});
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwCashierJournal/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecashier0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:999999,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options,data){
			    		addTDEvent();
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
// 				$(".table-float-th").unbind();
				//$('.table-float-head').remove();
			}
			
			function toCashierAccount(){
				window.location.href= "${webPath}/cwCashierAccount/getListPage";
			}
			function toCashierReport(){
				window.location.href= "${webPath}/cwCashierJournal/toCashierReport";
			}
			//重置
			function initSearchForm(){
				$("#weeks").val(weeks);
			}
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div column">
				<button type="button" class="btn btn-primary" onclick="toCashierAccount();">添加账户</button>
				<button type="button" class="btn btn-primary " onclick="toCashierReport();">核对总账</button>
			</div>
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">
				<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
					<div class="mod-toolbar-top">
						<div class="left">
							<form id="cwListForm">
								<!-- 	查询标题 -->
								<span class="txt fl">出纳日记账</span>
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
								<span class="txt fl">账户:</span> 
								
								<input name="accountNo" id="accountNo" class="items-btn menu-btn fl">
								
								<%-- <select class="items-btn menu-btn fl" name="accountNo" id="accountNo" autocomplete="off" onchange="my_Search()">
									<c:forEach value="dataMap.accounts" var="act">
										<option value='<s:property value="#act.uid"/>'><s:property value="#act.accountNo"/> <s:property value="#act.accountName"/></option>
									</c:forEach>
								</select>  --%>
<!-- 								<a onclick="my_Search();" class="ui-btn ui-btn-refresh fl" id="refresh">查询</a>  -->
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto; width: 1000px;">
		</div>
	</body>	
</html>
