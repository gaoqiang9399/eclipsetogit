<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" >
			var accNo = '${dataMap.accNo}';
			var accName = '${dataMap.accName}';
			var beginWeek = '${dataMap.beginWeek}';
			var endWeek = '${dataMap.endWeek}';
			var date = '${date}';
			
			//公式页面点查询明细分类帐yht bug修改
		var dFlag='${param.dFlag}';
		var dCom='${param.dCom}';
		var dStartWeek='${param.dStartWeek}';
		var dEndWeek='${param.dEndWeek}';
		var dComName='${param.dComName}';
		
			$(function(){
			//yhtbug修改
				if(dFlag=='y'){
					$(".tabCont").empty();
					$(".tabCont").attr("style","height:100px;");
					var showWeek=dStartWeek.substring(0,4)+"年"+dStartWeek.substring(4,6)+"期"+"—"+dEndWeek.substring(0,4)+"年"+dEndWeek.substring(4,6)+"期";
					$(".tabCont").html("<p align='center' style='padding-top:25px;font-weight:bolder;'>"+dComName+"明细分类帐</p><p align='center' style='font-weight:bold;'>"+showWeek+"</p>");
					//加载列表
					myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/detailAccount/findByPageAjax?accNo="+dCom+"&beginWeek="+dStartWeek+"&endWeek="+dEndWeek+"&dFlag="+dFlag,//列表数据查询的url
				    	tableId:"tabledetailAccount0001",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:1000,//加载默认行数(不填为系统默认行数)
				    	ownHeight:true,
				    	callback:function(){
	// 			    		$("table").tableRcswitcher({
	// 			    		name:"demoPosition"});
				    		$('.footer_loader').remove();
			    			//凭证详情
							$('.openLink').off('click').on('click', function(){
								var voucherid = $(this).data('voucherid') + '';
								top.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherEdit?voucherNo='+voucherid), '凭证详情',null);
							});
				    	}//方法执行完回调函数（取完数据做处理的时候）
				     });
				}else{
					//初始化查询期
					initSearchForm();
					//加载列表
					myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/detailAccount/findByPageAjax",//列表数据查询的url
				    	tableId:"tabledetailAccount0001",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:1000,//加载默认行数(不填为系统默认行数)
				    	data:{"accNo":accNo,"beginWeek":beginWeek,"endWeek":endWeek},//指定参数
				    	ownHeight:true,
				    	callback:function(){
	// 			    		$("table").tableRcswitcher({
	// 			    		name:"demoPosition"});
				    		$('.footer_loader').remove();
			    			//凭证详情
							$('.openLink').off('click').on('click', function(){
								var voucherid = $(this).data('voucherid') + '';
								top.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherEdit?voucherNo='+voucherid), '凭证详情',null);
							});
				    	}//方法执行完回调函数（取完数据做处理的时候）
				    });
					//初始化更多查询 控件
					MoreSearch.init();
				}
			});
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#detailAccountListForm').serializeArray());
			}
			
			//关闭弹窗
			function closeCallBack() {
				myclose();
				reloadJsp();
			};
			
			function my_Search(){
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if(checkQueryWeeks(begin, end)){
					showInitWeek();
					updateTableData();//重新加载列表数据
					MoreSearch.colseMoreBtn();
				}
			}
			
			//初始化查询条件
			function initSearchForm(){
				//把查询条件中参数 清空，并初始化查询条件
				$('#beginWeek').val(beginWeek);
				$('#endWeek').val(endWeek);
				if(accNo !=''){
					$('#accNo').val(accNo);
					$('#accName').val(accName);
					$('#fontAccName').text(accNo + " " + accName);
				}else{
					$('#fontAccName').text("未选 ");
				}
				showInitWeek();
			}
			
			//格式化顶部查询条件
			function showInitWeek(){
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if(begin!='' && end!=''){
					$('#selected-period').html('');
					if(begin == end){
						$('#selected-period').html("<span>"+joinToWeek(begin)+"</span>");
					}else{
						$('#selected-period').html("<span>"+joinToWeek(begin) + " 至 " + joinToWeek(end)+"</span>");
					}
				}
			}
			
			//重置查询条件
			function clearSearchForm(){
				//把查询条件中参数 清空，并初始化查询条件
				$('#detailAccountListForm').find('input').each(function(){
					$(this).val('');
				});
				initSearchForm();
			}
			
			//组装周期
			function joinToWeek(weeks){
				return  weeks.substring(0, 4) + "年" + weeks.substring(4) + "期";
			}
			//刷新数据列表
			function reloadJsp(){
				clearSearchForm();
				updateTableData();//重新加载列表数据
			}
		</script>
		<style>
			.table_content .ls_list tr td{
				padding-right:10px;
			}
			.voucherLink{
			    cursor: pointer;
			    text-decoration: underline;
			    margin: 5px;
			    color: #555;
			}
			.voucherLink:hover{
			    text-decoration: underline;
			}
		</style>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
			<div class="btn-div">
				<ol class="breadcrumb pull-left">
					<li><a
						href="${webPath}/component/finance/othreport/CwBooksEntrance.jsp"  style="color:#32b5cb;">账簿</a></li>
					<li class="active">明细账查询</li>
				</ol>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<!-- 更多查询条件功能开始 -->
						<div class="mod-toolbar-top">
							<div class="left">
								<!-- 查询标题 -->
								<span class="txt fl">明细账查询</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<form id="detailAccountListForm">
											<!-- 主要查询条件 -->
											<ul class="filter-list" id="filter-period">
												<li class="li-two-wrap">
													<label>会计期间:</label> 
													<input type="text" class="form-control form-warp cw-week" readonly name="beginWeek" id="beginWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
													<span>至</span> 
													<input type="text" class="form-control form-warp cw-week" readonly name="endWeek" id="endWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
												</li>
												<li class="li-one-wrap">
													<label for="#filter-fromSubject">科目:</label>
											 		<input class="form-control form-warp"  type="text" onclick="autoComPleter(this, '0', changeFont)" id="accNo" name="accNo"  autocomplete="off"  />
													<span class="glyphicon glyphicon-search search-addon comitem_select" ></span>
											 	</li>
											</ul>
											<!-- 展开的更多条件  -->
											<ul class="filter-list" id="more-conditions" style="display: none;">
											</ul>
											<br>
											<input type="hidden" class="form-control form-warp"  name="fromWhere" id='fromWhere' value="${dataMap.fromWhere}">
										</form>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
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
				<div id="content" class="table_content account_voucher_list"  style="height: auto;">
					<!--待定是否放置自定义table标签?-->
<%-- 					<dhcc:tableTag property="tablevoucher0001" paginate="CwVoucherMstList" head="true"></dhcc:tableTag> --%>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$('.comitem_select').on('click', function(){
		openComItemDialog('0', function(data){
			if(data){
				$('#accNo').val(data.id);
				$('#fontAccName').text(data.id+" "+data.name);
			}
		});
	})
	
	function changeFont(selected){
		$('#fontAccName').text(selected.accNo+" "+selected.accName);
		$('#accNo').val(selected.accNo);
	}			
	
</script>

</html>