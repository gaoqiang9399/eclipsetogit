<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%> 
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/othreport/css/serch_form.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/othreport/js/comItemZoom.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	
		<style type="text/css">
			#tablist>thead>tr>th{
				font-weight:bold;
			}
			.table_content .ls_list tr td{
				padding-right:10px;
			} 
		</style>
		<script type="text/javascript" >
			var searchWeek = '${dataMap.maxCloseWeek}';
			function detailAccountGetListPage(obj,url){
				window.location.href=webPath+url;
			}
			$(function(){
				//清空 并 初始化查询期
				initSearchForm();
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/generalLedger/getGeneralLedgerAjax",//列表数据查询的url
			    	tableId:"tablegeneralLedger0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
// 			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:10,//加载默认行数(不填为系统默认行数)
			    	data:{"week":searchWeek},//指定参数
			    	ownHeight:true,
			    	callback:function(){
// 			    		$("table").tableRcswitcher({
// 			    		name:"demoPosition"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
				$('.table-float-head').remove();
				$('.footer_loader').remove();
				//初始化更多查询 控件
				MoreSearch.init();
				validateOrderNo($('#beginOrderNo'));
				validateOrderNo($('#endOrderNo'));
			});
			
			//层级验证(只能输入1-10)
			function validateOrderNo(obj){
				$(this).bind('keyup', function () {
					var reg = new RegExp("^(?:[1-9]?|10)$");
					//var reg = new RegExp("^[1-9]*$");
				    var orderNo = $(obj).val();
				    if(!reg.test(orderNo)){
				       $(obj).val('');
				    }
				});
			}
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#generalListForm').serializeArray());
			} 
			
			function my_Search(){
				/**格式化顶部查询条件*/
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if(checkQueryWeeks(begin, end)){
					if(begin!='' && end!=''){
						if(begin == end){
							$('#selected-period').html(joinToWeek(begin));
						}else{
							$('#selected-period').html(joinToWeek(begin) + " 至 " + joinToWeek(end));
						}
					}
					$('#queryButtonFlag').val(1);
					updateTableData();//重新加载列表数据
					MoreSearch.colseMoreBtn();
				}
			}
			
			//重置查询条件
			function clearSearchForm(){
				//把查询条件中参数 清空，并初始化查询条件
				$('#generalListForm').find('input').each(function(){
					$(this).val('');
				});
				$('#selected-period').html(joinToWeek(searchWeek));
				initSearchForm();
				$('#fontAccName').text("未选");
			}
			
			//初始化查询条件
			function initSearchForm(){
				//把查询条件中参数 清空，并初始化查询条件
				$('#generalListForm').find('input').each(function(){
					$(this).val('');
				});
				$('#beginWeek').val(searchWeek);
				$('#endWeek').val(searchWeek);
				$('#selected-period').html(joinToWeek(searchWeek));
			}
			
			//组装周期
			function joinToWeek(weeks){
				return "<span>" + weeks.substring(0, 4) + "年" + weeks.substring(4) + "期<span>";
			}
			//刷新数据列表
			function reloadJsp(){
				clearSearchForm();
				updateTableData();//重新加载列表数据
			}
			
		</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List"/>
	<div class="container">
		<div class="row clearfix ">
			<div class="col-md-12 column">
			<div class="btn-div">
				<ol class="breadcrumb pull-left">
					<li><a    
						href="${webPath}/component/finance/othreport/CwBooksEntrance.jsp"  style="color:#32b5cb;">账簿</a></li>
					<li class="active">总账查询</li>
				</ol>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-12 column mysearch-div" id="pills">
						<!-- 更多查询条件功能开始 -->
						<div class="mod-toolbar-top">
							<div class="left">
								<!-- 查询标题 -->
								<span class="txt fl">总账查询</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<form id="generalListForm">
											<!-- 主要查询条件 -->
											<ul class="filter-list" id="filter-period">
												<li class="li-two-wrap">
													<label>会计期间:</label> 
													<input type="text" class="form-control form-warp cw-week" readonly name="beginWeek" id="beginWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
													<span>至</span> 
													<input type="text" class="form-control form-warp cw-week" readonly name="endWeek" id="endWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
												</li>
											</ul>
											<!-- 展开的更多条件  -->
											<ul class="filter-list" id="more-conditions">
<!-- 										<ul class="filter-list" id="more-conditions" style="display: none;"> -->
												<li class="li-one-wrap">
													<label for="#filter-fromLevel">科目:</label> 
													<input class="form-control form-warp"  type="text" onclick="autoComPleter(this, '1',changeFont)" id="accNo" name="accNo"  autocomplete="off"  />
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="lrkm"></span>
												</li>
												<li class="li-two-wrap">
													<label for="#filter-fromLevel">科目级次:</label> 
													<input type="text" class="form-control form-warp" id="beginOrderNo" name="beginOrderNo" autocomplete="off" maxlength="1">
													<span>至</span> 
													<input type="text" class="form-control form-warp" name="endOrderNo" id="endOrderNo" autocomplete="off"  maxlength="2">
													<input type="hidden" name="queryButtonFlag" id="queryButtonFlag" value="0">
												</li>
											</ul>
											<br>
										</form>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a> 
<!-- 											<a href="#" id="conditions-trigger" class="conditions-trigger" tabindex="-1">更多条件<b></b></a>  -->
											<a class="ui-btn ui-btn-sp" id="filter-submit" onclick="my_Search();">确定</a> 
											<a class="ui-btn" id="filter-reset" onclick="clearSearchForm();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
								<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl" id="refresh"><b></b></a>
								<span class="txt fl">「科目：<font id="fontAccName" >未选</font>」</span>
							</div>
						</div>
						<!-- 更多查询条件功能结束 -->
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content general_List" style="height: auto;" >
					<!--待定是否放置自定义table标签?-->
<%-- 					<dhcc:tableTag property="tablevoucher0001" paginate="CwVoucherMstList" head="true"></dhcc:tableTag> --%>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$('.comitem_select').on('click', function(){
					//var forId = $(this).attr('for');
					openComItemDialog('1', function(data){
						if(data){
							$('#accNo').val(data.id);
							$('#fontAccName').text(data.id+" "+data.name);
						}
					});
				})
	function changeFont(selected){
		//alert(JSON.stringify(selected))
		$('#fontAccName').text(selected.accNo+" "+selected.accName);
		$('#accNo').val(selected.accNo);
	}	
</script>
</html>