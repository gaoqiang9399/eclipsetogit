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
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/jquery.jqprint-0.3.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/browser.js"></script>
		<style type="text/css">
		.itemS1{
			padding-left: 5px;
			color:black\9\0;
		}
		.itemS2{ 
			padding-left: 30px;
		}
		.itemS3{
			padding-left: 45px;
		}
		.itemS4{
			padding-left: 55px;
		}
		.itemS5{
			padding-left: 65px;
		}
		.itemS6{
			padding-left: 75px;
		}
		.strong{
			font-weight: bold;
		}
		a {
			color:black;
			text-decoration: underline;
		} 
		.table_content .ls_list tr td{
			padding-right:10px;
		}
		.table_content .ls_list tr th{
		    font-size: 14px;
			height: 47px; 
			line-height: 47px; 
			text-align: center; 
			white-space: nowrap; 
			padding: 0px 5px; 
			cursor: default;
			font-weight: bold;
			box-sizing: border-box;
        	color: #000000;
		}
		.table_content .ls_list tr th:HOVER{
			 font-size: 14px;
			 font-weight: bold;
			 padding: 0px 5px; 
		}
		.editbox {
		    width: 100%;
		    height: 30px; 
		    line-height: 30px; 
		    border-width: 1px;
		    border-style: solid;
		    border-color: rgb(221, 221, 221);
		    border-image: initial;
		    padding: 6px 4px;
		}
		
		.tableborder tr td {
    		border-left: 1px solid #000000;
    		border-bottom: 1px solid #000000;
    		border-right:1px solid #000000;
    		border-top:1px solid #000000;
			/* border-left: 1px solid #000000;
   			border-bottom: 1px solid ##000000; */
		}
	 .printtable tr td {
    	border-left: 1px solid #000000 !important;
    	border-bottom: 1px solid #000000 !important;
	 } 
	 .printtable {
    	/* border:1px solid #000000;
    	border-collapse: collapse;
		border:1px solid #000000; */
	 } 
		/* body{
			    font-family: "Microsoft YaHei","Helvetica Neue", Helvetica, Arial, sans-serif;
		} */
		
		
		</style>
<script type="text/javascript" >
	var reports = {'001':'资产负债表查询', '002':'现金流量表查询', '003':'利润表查询', '004':'营业费用明细表查询', '005':'利润及利润分配表查询'};
	
	var reportTypeId = '${dataMap.reportTypeId}';
	var hcUseFlag = '${dataMap.hcUseFlag}';
	var weeks = '${dataMap.weeks}';
	var comName = '${dataMap.comName}';
	var searchWeek = '${dataMap.weeks}';
	var tableHtml = '';
	var reportsTitle = {'001':'资产负债表', '002':'现金流量表', '003':'利润表', '004':'营业费用明细表', '005':'利润及利润分配表'};
	//var reportsTitle = {'001':'资产负债表'+weeks, '002':'现金流量表'+weeks, '003':'利润表'+weeks, '004':'营业费用明细表'+weeks, '005':'利润及利润分配表'+weeks};
	var zcfzHead = {'1': '年初数', '2': '期末数'};
	var otherHead = {'1': '本期数', '2': '上期数', '3': '本年数'};

	$(function() {
		$('#report_title').text(reports[reportTypeId]);
		$('#report_big').text(reports[reportTypeId]);
		clearSearchForm();
		if ('001' == reportTypeId) {
			//加载列表
			myCustomScrollbar({
				obj : "#content",//页面内容绑定的id
				url:webPath+"/cwReportItem/getSearchReportListAjax",//列表数据查询的url
				tableId : "tablefinreport0001",//列表数据查询的table编号
				tableType : "thirdTableTag",//table所需解析标签的种类
				data : {},//指定参数
				ownHeight : true,
				callback : function(options, data) {
					tableHtml = data.tableHtml;
					getTableTr();
					changeTableTh();
					searchWeek = $('#weeks').val();
					$('#selected-period').html(joinToWeek(searchWeek));
					$('.table-float-head').remove();
					//创建凭证打印html
					createReportPrintHtml();
					//addTDEvent();
				}//方法执行完回调函数（取完数据做处理的时候）
			});
		} else {
			//加载列表
			myCustomScrollbar({
				obj : "#content",//页面内容绑定的id
				url:webPath+"/cwReportItem/getSearchReportListAjax",//列表数据查询的url
				tableId : "tablefinreport0002",//列表数据查询的table编号
				tableType : "thirdTableTag",//table所需解析标签的种类
				data : {},//指定参数
				ownHeight : true,
				callback : function(options, data) {
					tableHtml = data.tableHtml;
					getTableTr();//显示行次
					changeTableTh();
					searchWeek = $('#weeks').val();
					$('#selected-period').html(joinToWeek(searchWeek));
					$('.table-float-head').remove();
					//创建凭证打印html
					createReportPrintHtml();
				}//方法执行完回调函数（取完数据做处理的时候）
			});
		}
		//初始化更多查询 控件
		MoreSearch.init();
		$('.footer_loader').remove();
		$('.table-float-head').remove();

	});

	function resetReportData() {
		var dataParm = JSON.stringify($('#cwReportForm').serializeArray());
		jQuery.ajax({
			url : webPath+'/cwReportItem/resetReportDataAjax',
			data : {
				ajaxData : dataParm
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_REFRESH"), 1);
					updateTableData();//重新加载列表数据
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	}

	//yht修改bug(资产负债表表头)
	function changeTableTh() {
		// 			var zcfzHead = {'1': '年初数', '2': '期末数'};
		// 			var otherHead = {'1': '本期数', '2': '上期数', '3': '本年数'};
		var basePValue = getShowType();
		var arr = basePValue.split("@");
		var head = {};
		if ('001' == reportTypeId) {
			head = zcfzHead;
			$("th[name=itemAAmt]").html(head[arr[0]]);
			$("th[name=itemBAmt]").html(head[arr[1]]);
			$("th[name=itemAAmt2]").html(head[arr[0]]);
			$("th[name=itemBAmt2]").html(head[arr[1]]);
		} else {
			head = otherHead;
			$("th[name=itemAAmt]").html(head[arr[0]]);
			$("th[name=itemBAmt]").html(head[arr[1]]);
		}
	}

	function getShowType() {
		var bbShow = '';
		$.ajax({
			url : webPath+'/cwReportItem/getReportAccountAjax',
			data : 'reportTypeId=' + reportTypeId + '&searchWeek='
					+ searchWeek,
			dataType : 'json',
			async : false,
			type : 'POST',
			success : function(data) {
				bbShow = data.cwReportAcount.bbShow;
			}
		})
		return bbShow;
	}
	//获取 查询条件（方法名固定写法）
	function getFilterValArr() {
		return JSON.stringify($('#cwReportForm').serializeArray());
	}

	function my_Search() {
		searchWeek = $('#weeks').val();
		updateTableData();//重新加载列表数据
		MoreSearch.colseMoreBtn();
		$(".table-float-th").unbind();
		// 			$('.table-float-head').remove();
	}

	function clearSearchForm() {
		$('#weeks').val(weeks);
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
		$(".table-float-th").unbind();
		//$('.table-float-head').remove();
	}

	function ajaxShowItemView(obj, ajaxUrl) {
		var txt = $(obj).find('span').text();
		top.openBigForm(webPath+ajaxUrl, txt + '--详情', closeCallBack, 72);
		/* createShowDialog(ajaxUrl,txt+'--详情','90','90',function(){
				// updateTableData();//重新加载列表数据
		});  */
	}

	function closeCallBack() {
		myclose();
	};

	//展示计算公式
	function ajaxShowItemCalc(itemno, bblb, showType, rlType) {
		var ajaxData = {
			'reportTypeId' : reportTypeId,
			'reportItemId' : itemno,
			'bbLb' : bblb,
			'weeks' : searchWeek,
			'showType' : showType,
			'rlType' : rlType
		};
		var ajaxUrl = webPath+"/cwReportItem/toItemCalcPage?ajaxData="
				+ JSON.stringify(ajaxData);
		window.parent.openBigForm(encodeURI(ajaxUrl), '金额公式', closeCallBack, 60, 60);

		// 			//yhtbug修改
		// 			layer.open({
		// 	        type: 2,
		// 	        title: "金额公式",
		// 	        offset:'30px',
		// 	        maxmin: true,
		// 	        shadeClose: true, //点击遮罩关闭层
		// 	        area : ['70%' , '50%'],
		// 	        content: encodeURI(ajaxUrl)
		// 		 });

	}
	// 			function insertAjax(obj){
	// 				ajaxInput(obj,"${webPath}/cwReportItem/insertAjax?reportTypeId=<s:property value='reportTypeId'/>reportItemId=<s:property value='reportItemId'/>");
	// 			}
</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List" />
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<ol class="breadcrumb pull-left">
						<li><a
							href="${webPath}/component/finance/finreport/CwReportEntrance.jsp"  style="color:#32b5cb;">报表</a></li>
						<li class="avtive" id="report_big"></li>
					</ol>
				<button type="button" class="btn btn-default" onclick="reportPrint()">打印</button>
				<button type="button" class="btn btn-default" onclick="downLoadExcelCw();">导出</button>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-4 column mysearch-div" id="pills">
						<!-- 更多查询条件功能开始 -->
						<div class="mod-toolbar-top">
							<div class="left">
								<!-- 查询标题 -->
								<span class="txt fl" id="report_title"></span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<form id="cwReportForm">
											<!-- 主要查询条件 -->
											<ul class="filter-list" id="filter-period">
												<li class="li-one-wrap"><input type="hidden"
													name="reportTypeId" id="reportTypeId"
													value="${dataMap.reportTypeId}"> <input
													type="hidden" name="bbLb" id="bbLb" value="03"> <label>查询周期:</label>
													<input type="text" class="form-control form-warp cw-week" readonly
													name="weeks" id="weeks" autocomplete="off"
													onclick="laydatemonth(this);" onkeydown="enterKey();">
												</li>
											</ul>
										</form>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
											<a class="ui-btn ui-btn-sp" id="filter-submit"
												onclick="my_Search();">确定</a> <a class="ui-btn"
												id="filter-reset" onclick="clearSearchForm();" tabindex="-1"
												style="display: inline;">重置</a>
										</div>
									</div>
								</div>
								<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl"
									id="refresh"><b></b></a>
							</div>
							<div class="search-title hidden"><span value=""></span></div>
						</div>
						<!-- 更多查询条件功能结束 -->
					</div>
					<div class="col-xs-3 column znsearch-div pull-right">
						<button type="button" class="btn btn-primary pull-right" onclick="resetReportData()">重新生成</button>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content cw_voucher_list" style="height: auto;">
					<!--待定是否放置自定义table标签?-->
					<%-- <c:if test='${dataMap.reportTypeId=="001"}'>
						<dhcc:tableTag property="tablefinreport0001" paginate="CwSearchReportList" head="true"></dhcc:tableTag>
					</c:if>
					<c:if test='${dataMap.reportTypeId!="001"}'>
						<dhcc:tableTag property="tablefinreport0002" paginate="CwSearchReportList" head="true"></dhcc:tableTag>
					</c:if> --%>
				</div>
			</div>
		</div>
	</div>
	<div id="reportPrint"></div>
</body>

<script type="text/javascript">
//利润表显示
	function getTableTr(){
		var tit = "";
		if(reportTypeId=='001'){
   			tit = "showName,zcTr,itemAAmt,itemBAmt,fzTr,itemAAmt2,itemBAmt2,showName2";
			//$('#batchRe_btn').removeAttr('disabled').addClass('btn-primary');
   		}else if(reportTypeId=='003'){
   			tit = "showName,lrTr,itemAAmt,itemBAmt";
			//$('#batchRe_btn').removeAttr('disabled').addClass('btn-primary');
   		}else{
   			tit = "showName,xjllTr,itemAAmt,itemBAmt";
			//$('#batchRe_btn').attr('disabled', true).removeClass('btn-primary');
   		}
   		 if(hcUseFlag!=1){
   			tit = "showName,itemAAmt,itemBAmt,itemAAmt2,itemBAmt2,showName2";
   		} 
   		
		$(".search-title").find("span").attr("value", tit);
		showTable(false, ''); 
	}
	//报表导出的功能，导入到Excel表中
	function downLoadExcelCw(){
	
		//var ajaxData = getFilterValArr();
		//alert(ajaxData+"====")
		var bbLb = $("#bbLb").val();
		//window.location.href=jsonUrl;
		window.location.href=webPath+"/cwReportItem/downReportToExcel?reportTypeId="+ reportTypeId+"&bbLb="+bbLb+"&weeks="+weeks;
	}
	

	function createReportPrintHtml() {
		var reportTitle = "<div  style=\"text-align: center;font-size: 20px;display: block;\">"
				+ reportsTitle[reportTypeId] + "</div>";
		$("#reportPrint").html(reportTitle);
		var reportweeks = "<div><span  style=\"text-align: left;font-size: 15px;display: block; float:left \">制表机构："
				+ comName + "</span>";
		reportweeks += "<span  style=\"text-align: right;font-size: 15px;display: block;\">报表周期："
				+ weeks + "</span></div>";
		$("#reportPrint").append(reportweeks);

		$("#reportPrint").append(tableHtml);
		$("#reportPrint").find('table').removeClass("ls_list").addClass(
				"printtable").attr("style", 'display:true');
		$("#reportPrint").hide();
		
		//
		var basePValue = getShowType();
		var arr = basePValue.split("@");
		var head = {};
		if ('001' == reportTypeId) {
			head = zcfzHead;
			$("#reportPrint").find("th[name=itemAAmt]").html(head[arr[0]]);
			$("#reportPrint").find("th[name=itemBAmt]").html(head[arr[1]]);
			$("#reportPrint").find("th[name=itemAAmt2]").html(head[arr[0]]);
			$("#reportPrint").find("th[name=itemBAmt2]").html(head[arr[1]]);
		} else {
			head = otherHead;
			$("#reportPrint").find("th[name=itemAAmt]").html(head[arr[0]]);
			$("#reportPrint").find("th[name=itemBAmt]").html(head[arr[1]]);
		}

	}
	//报表打印方法
	function reportPrint() {

		//  var reportTitle = "<span>"+reportsTitle[reportTypeId]+"</span>";
		/// $("#reportPrint").append(reportTitle);
		// $("#reportPrint").append(tableHtml);
		$("#reportPrint").show();
		// $("#reportPrint").find('table').attr("style",'display:true'); 
		// $("#reportPrint").find('table td').attr("border",'1'); 
		$("#reportPrint").find('table th').css("border", '1px solid #000');
		$("#reportPrint").find('table td').css("border", '1px solid #000');
		//$("#reportPrint").find('table').removeClass("ls_list"); 
		// $("#reportPrint").find('table').addClass("printtable"); 
		if (reportTypeId == '003') {
			$("#reportPrint").find('tr').each(function(i) {
				$("#reportPrint").find('th:eq(' + 2 + ')').hide();
				$(this).children("td").first().next("td").next("td").hide();
			});
			if (hcUseFlag != 1) {
				$("#reportPrint").find('tr').each(function(i) {
					$("#reportPrint").find('th:eq(' + 1 + ')').hide();
					$(this).children("td").first().next("td").hide();
				});

			}
		} else if (reportTypeId == '002') {
			$("#reportPrint").find('tr').each(function(i) {
				$("#reportPrint").find('th:eq(' + 1 + ')').hide();
				$(this).children("td").first().next("td").hide();

			});
			if (hcUseFlag != 1) {

				$("#reportPrint").find('tr').each(
						function(i) {
							$("#reportPrint").find('th:eq(' + 2 + ')').hide();
							$(this).children("td").first().next("td")
									.next("td").hide();
						});
			}
		} else if (reportTypeId == '001') {
			if (hcUseFlag != 1) {

				$("#reportPrint").find('tr').each(
						function(i) {
							$("#reportPrint").find('th:eq(' + 1 + ')').hide();
							$("#reportPrint").find('th:eq(' + 5 + ')').hide();
							$(this).children("td").first().next("td").hide();
							$(this).children("td").first().next("td")
									.next("td").next("td").next("td")
									.next("td").hide();
						});
			}
		}

		$("#reportPrint").jqprint({});//报表打印
		// $("#reportPrint").html("");
		$("#reportPrint").hide();

	}
	
</script>
</html>