<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE>
<html>
	<head>
		<title>科目汇总列表</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<style type="text/css">
			.btn-div .more-btn{
				margin-left: 4px;
				float: left;
			}
			.btn-div .show-btn{s
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
			.table_content .ls_list tr td{
				padding-right:10px;
			}
		</style>
		
		<script type="text/javascript" >
		var pzsum = '';
		var fjsum = '';
			$(function(){
				//清空 并 初始化查询期
				initSearchForm();
			
			    myCustomScrollbar({
			    	obj:"#content1",//页面内容绑定的id
			    	url:webPath+"/cwKemuHuiZong/findByPageAjax",//列表数据查询的url
			    	tableId:"tablekemuhuizong0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	//data:{"weeks":"201612"},//指定参数
			    	ownHeight:true,
			    	callback:function(options,data){
				    	//凭证总张数：11张，附件总张数
						pzsum = data.pzsum;
						fjsum = data.fjsum;
						$("#pzsum").text(pzsum);
						$("#fjsum").text(fjsum);
					
			    	//$('#content1 tbody').append('<tr><td width="10%" align="center"></td><td width="20%" align="left">总计</td><td width="10%" align="center">0.00</td><td width="10%" align="center">0.00</td></tr>');
						//var sum1 =  $(".loadCount").text();
						//var temsum = sum1-1;
						//$(".loadCount").text(temsum)
						//alert(sum1);
						//alert('${dataMap.sum}')
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
				 //初始化更多查询 控件
				MoreSearch.init();
				$('.footer_loader').remove();
			 });
			 //获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#cwListForm').serializeArray());
			}
		</script>
	<script type="text/javascript" >
		var date="${date}";
	</script>
	</head>
	<body>

	<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<ol class="breadcrumb pull-left">
						<li><a
							href="${webPath}/component/finance/othreport/CwBooksEntrance.jsp"  style="color:#32b5cb;">账簿</a></li>
						<li class="active">科目汇总表</li>
					</ol>
<!-- 					<button type="button" class="btn btn-default" onclick="">打印</button> -->
<!-- 					<button type="button" class="btn btn-default" onclick="">导出</button> -->
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
			
					<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
				 		<div class="mod-toolbar-top">
							<div class="left">
							<!-- 	查询标题 -->
								<span class="txt fl">科目汇总表</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<form id="cwListForm">
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
<!-- 											<ul class="filter-list" id="more-conditions" style="display: none;"> -->
											<ul class="filter-list" id="more-conditions" >
												<li class="li-one-wrap">
													<label for="#filter-fromSubject">科目:</label>
													<!-- <input type="text" class="form-control form-warp"  value=""  name="accNo" id="accNo" autocomplete="off"> -->
													<input class="form-control form-warp"  type="text" onclick="autoComPleter(this, '1',changeFont)" id="accNo" name="accNo"  autocomplete="off"  />
													<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
													
												</li>
												
												 <li class="li-one-wrap">
													<label for="#filter-fromLevel">包含凭证:</label>
													<input type="checkbox" name="noAccountpz" id="noAccountpz" value="1" onclick="checkcwpz(this);">未记账凭证</input>
													<input type="checkbox" name="wrongpz" id="wrongpz" value="1" disabled="disabled">错误凭证</input>
												</li>
												 <li class="li-one-wrap">
													<label for="#filter-fromLevel">条件选择:</label>
													<input type="checkbox" name="showTabKemu" id="showTabKemu" value="1">显示表外类科目</input>
												</li>
												
											</ul>
											<br>
										</form>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a> 
<!-- 											<a href="#" id="conditions-trigger" class="conditions-trigger" tabindex="-1">更多条件<b></b></a>  -->
											<a class="ui-btn ui-btn-sp" id="filter-submit" onclick="sure_Search();">确定</a> 
											<a class="ui-btn" id="filter-reset" onclick="initSearchForm();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
								<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl" id="refresh"><b></b></a>
								<span class="txt fl">「科目：<font id="fontAccName" >未选</font>」</span>
								<span class="txt fl">「凭证总张数：<font id="pzsum" color="black" weight="bold"></font> 张，附件总张数：<font id="fjsum" color="black" weight="bold"></font> 张」</span>
							</div>
						</div>
					
						</div>
					</div>
					
				</div>
			</div>
		
		<!--页面显示区域-->
		<div id="content1" class="table_content"  style="height: auto;width:1000px;">
		</div>
	</body>	
	<script type="text/javascript">
	var searchWeek = date;
		function initSearchForm (){
			//把查询条件中参数 清空，并初始化查询条件
				$('#accNo').val('');
				$('#cwListForm').find(':checkbox').each(function(){
					$(this).attr('checked', false);
				});
				$('#beginWeek').val(searchWeek);
				$('#endWeek').val(searchWeek);
				$('#selected-period').html(joinToWeek(searchWeek)); 
				
				$('#fontAccName').text("未选");
		}
		//组装周期
			function joinToWeek(weeks){
				return "<span>" + weeks.substring(0, 4) + "年" + weeks.substring(4) + "期<span>";
			}
			
			//更多条件查询：点击确定以后
			function sure_Search(){
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if(checkQueryWeeks(begin, end)){
					if(begin == end){
						$('#selected-period').html(joinToWeek(begin));
					}else{
						$('#selected-period').html(joinToWeek(begin) + " 至 " + joinToWeek(end));
					}
					updateTableData();//重新加载列表数据
					MoreSearch.colseMoreBtn();
				}
			}
		
		/*未记账凭证和错误凭证选择*/
		function checkcwpz(obj){
		if(obj.checked){
			$("input[name='wrongpz']")[0].disabled=false;
			$("input[name='wrongpz']")[0].checked=true;
		}else{
			$("input[name='wrongpz']")[0].disabled=true;
			$("input[name='wrongpz']")[0].checked=false;
		}
	}
			//刷新数据列表
			function reloadJsp(){
			$('#fontAccName').text("未选");
				clearSearchForm();
				updateTableData();//重新加载列表数据
				initSearchForm();
			}
	
	//重置查询条件
			function clearSearchForm(){
				//把查询条件中参数 清空，并初始化查询条件
				$('#cwListForm').find('input').each(function(){
					$(this).val('');
				});
				initSearchForm();
				
				//$("#accNoName").html('科目:');
			}
	</script>
	<%-- <script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_List.js"></script> --%>
	<script type="text/javascript">
	$('.comitem_select').on('click', function(){
					
					openComItemDialog('1', function(data){
						if(data){
							$('#accNo').val(data.id);
							$('#fontAccName').text(data.id+" "+data.name);
						}
					});
		
				})
	function changeFont(selected){
		//alert(JSON.stringify(selected))
		$('#accNo').val(selected.accNo);
		$('#fontAccName').text(selected.accNo+" "+selected.accName);
		}	
	
</script>
	
</html>
