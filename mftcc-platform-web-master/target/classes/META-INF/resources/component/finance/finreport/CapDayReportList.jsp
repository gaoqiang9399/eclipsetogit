<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<style type="text/css">
.btn-div .more-btn {
	margin-left: 4px;
	float: left;
}

.btn-div .show-btn {
	float: left;
}

.more-btn>.dropdown_ul {
	min-width: 68px;
	right: 0;
	left: auto;
}

.more-btn>.dropdown_ul>li>a {
	padding: 5px 5px;
}

.more-btn>.dropdown_ul>li>a:HOVER {
	background: #e3e7eb;
}

#m1,#m2,#m3,#m4 {
	vertical-align: middle;
}
#tablist th{
	height: 30px;
   	line-height: 30px;
}

#tablist tr td{
	border: 1px solid #e9ebf2;
}
#tablist>thead>tr>th{
	font-weight:bold;
} 

</style>
<title></title>

</head>
<body>

	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<!-- 					<button type="button" class="btn btn-info" onclick="">打印</button> -->
				<!-- 					<button type="button" class="btn btn-info" onclick="">导出</button> -->
				<ol class="breadcrumb pull-left">
					<li><a href="${webPath}/component/finance/othreport/CwBooksEntrance.jsp" style="color: #32b5cb;">账簿</a></li>
					<li class="active">资金日报表</li>
				</ol>
			</div>
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">

				<div class="col-xs-9 column mysearch-div" id="pills">
					<!-- 更多查询条件功能开始 -->
					<div class="mod-toolbar-top">
						<div class="left">
							<!-- 	查询标题 -->
							<span class="txt fl" id="style">现金日报表</span>
							<div class="ui-btn-menu fl" id="filter-menu">
								<!-- 显示条件 -->
								<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b> </span>
								<!-- 弹窗  -->
								<div class="search_con">
									<form id="cwListForm">
										<!-- 主要查询条件 -->
										<ul class="filter-list" id="filter-period">
											<li class="li-one-wrap"><label for="#filter-fromLevel">报表类型:</label> <input type="radio" name="style" value="0" id="xj" onclick="creatSelect('0')">现金日报表</input> <input type="radio" name="style" value="1" id="yh" onclick="creatSelect('1')">银行日报表</input></li>
											<li class="li-one-wrap"><label for="#filter-fromSubject">科目:</label> <select id="accNo" name="accNo" class="form-control form-warp" autocomplete="off">
													<option></option>
											</select></li>
											<li class="li-one-wrap"><label>日期:</label> <input type="text" class="form-control form-warp cw-week" value="" name="date" id="date" autocomplete="off" onclick="fPopUpCalendarDlg(this);"></li>
										</ul>
										<!-- 展开的更多条件  -->
<!-- 										<ul class="filter-list" id="more-conditions" style="display: none;"> -->
										<ul class="filter-list" id="more-conditions" >
											<li class="li-one-wrap"><label for="#filter-fromLevel">包含凭证:</label> <input type="checkbox" name="noAccountpz" id="noAccountpz" value="1" onclick="checkcwpz(this);">未记账凭证</input> <input type="checkbox" name="wrongpz" id="wrongpz" value="1" disabled="disabled">错误凭证</input></li>
										</ul>
										<br>
									</form>
									<!-- 展开收起、重置、确定按钮 -->
									<div class="btns">
<!-- 										<a href="#" id="conditions-trigger" class="conditions-trigger" tabindex="-1">更多条件<b></b></a> -->
										<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a>
										 <a class="ui-btn ui-btn-sp" id="filter-submit" onclick="sure_Search();">确定</a> <a class="ui-btn" id="filter-reset" onclick="initSearchForm();" tabindex="-1" style="display: inline;">重置</a>
									</div>
								</div>
							</div>
							<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl" id="refresh"><b></b></a>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content cw_voucher_list" style="height: auto;">
				<table id="tablist" class="table table-bordered ls_list" width="100%">
					<thead>
						<tr align="center">
							<th colspan="2" rowspan="2" id="m1">项目</th>
							<th colspan="3">金额</th>
							<th rowspan="2" id="m2">备注</th>
						</tr>
						<tr align="center">
							<th>本日</th>
							<th>本月累计</th>
							<th>本年累计</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	//查询条件的form表单的jquery
	var date = "${dataMap.date}";
	var accNo = "${dataMap.accNo}";
	$(function() {
		//清空 并 初始化查询期
		initSearchForm();
		//加载列表
// 		myCustomScrollbar({
// 			obj : "#content",//页面内容绑定的id
// 			url:webPath+"/capDayReport/getCapDayReportDataAjax",//列表数据查询的url
// 			tableId : "tablecapDayReport0001",//列表数据查询的table编号
// 			tableType : "thirdTableTag",//table所需解析标签的种类
// 			pageSize : 1000,//加载默认行数(不填为系统默认行数)
// 			ownHeight : true,
// 			callback : function() {
// 				$('#selected-period').html($("#date").val() + " " + $("#accNo").val());
// 			}//方法执行完回调函数（取完数据做处理的时候）
// 		});
		
		getCapDayList();
		
		$('.footer_loader').remove();
		//初始化更多查询 控件
		MoreSearch.init();
// 		initTable();
	})
	
	
	
	
	//初始化报表
	function getCapDayList() {
		var $body =  $('.cw_voucher_list > table > tbody');
		$body.html('');
		$.ajax({
			url : '${webPath}/capDayReport/getCapDayReportDataAjax',
			data : {ajaxData : JSON.stringify($('#cwListForm').serializeArray())},
			dataType : 'json',
			type : 'post',
			success : function(data) {
				var list = data.list;
				for(var i in list){
					$body.append('<tr><td align="center">' + list[i].busType + '</td><td align="center">' + list[i].otherCom + '</td><td align="right">' + list[i].dAmt + '</td><td align="right">' + list[i].mAmt + '</td><td align="right">' + list[i].yAmt + '</td><td align="left">' + list[i].remark + '</td></tr>');
				}
				mergeCells($body)
			}		
		});
	}
	
	
	function mergeCells(obj){
		$('#selected-period').html($("#date").val() + " " + $("#accNo").val());
		$(obj).find('tr:gt(1)').each(function(){
			$(this).children('td:eq(0)').attr('colspan', '2');
			$(this).children('td:eq(3)').attr('colspan', '3').attr('align', 'center');
			$(this).children('td:eq(1),td:gt(3)').remove();
		})
	}

	//获取 查询条件（方法名固定写法）
	function getFilterValArr() {
		return JSON.stringify($('#cwListForm').serializeArray());
	}
	//下拉框
	function creatSelect(flag) {
		var htmlstr = "";
		if(flag=="1"){
			$("#style").html("银行日报表");
		}else{
			$("#style").html("现金日报表");
		}
		$.ajax({
			url : '${webPath}/capJournal/getSelectDateAjax',
			data : 'style=' + flag,
			dataType : 'json',
			async : false,
			type : 'post',
			success : function(data) {
				$.each(data.list, function(i, item) {
					htmlstr += "<option value=\""+item.accNo+"\">["
							+ item.accNo + "]" + item.accName + "</option>";
				});
				$("#accNo").html(htmlstr);
				style = flag;
			}
		});
	}
	//初始化条件表单
	function initSearchForm() {
		creatSelect('0');
		$("#date").val(date);
		$("#accNo").val(accNo);
		$('#selected-period').html(date + " " + accNo);
		$("#xj")[0].checked = true;
	}

	//点击确定执行查询
	function sure_Search() {
		if ($("#accNo").val() == "") {
			//alert("请输入科目号！");
			alert(top.getMessage("NOT_FORM_EMPTY", '科目号'), 1);
			return false;
		}
		getCapDayList();
// 		updateTableData();//重新加载列表数据
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
	//刷新按钮
	function reloadJsp() {
		initSearchForm();
		getCapDayList();
// 		updateTableData();//重新加载列表数据
	}
</script>
</html>
