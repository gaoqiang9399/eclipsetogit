<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>查询条件列表</title>
	<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
	<!-- 科目树 -->
	<style type="text/css">
		* {
			    -webkit-box-sizing: content-box;
			    -moz-box-sizing: content-box;
			    box-sizing: content-box;
		}
		body{background-color:#fff;}

		#searchDiv {
		    padding: 5px 15px;
		    min-width: auto;
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		#searchDiv input{
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		
		.btn-info{
			margin: 5px 15px;
		}
	</style>
	<script type="text/javascript">
    var defValue = '${param.conditionVal}';
	var brNoDef = '${param.brNoDef}';
	var txType = '${param.txType}';
	$(function(){
        var tableId = '${param.tableId}';
		//var tableId = "";// 1-部门 2-操作员 3-数据字典 4-业务品种 5-押品类别 6-借据 8-客户姓名9-渠道来源 10-经销商 14-票据类型
		var ajaxData = JSON.stringify(brNoDef);
		//加载列表
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfReportQueryConditionUser/getReportSqlListDataAjax?",//列表数据查询的url
	    	data:{ajaxData:ajaxData},
	    	tableId:tableId,//列表数据查询的table编号
	    	tableType:"tableTag",//table所需解析标签的种类
//		    	myFilter:false, //是否有我的筛选(列表列动态切换)
	    	pageSize:1000,//加载默认行数(不填为系统默认行数)
	    	data:{},//指定参数
	    	ownHeight:true,
	    	callback:function(){
	    		$("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="checkAllBox()">全选</a>');
	    	}//方法执行完回调函数（取完数据做处理的时候）
	    });
	    $("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="checkAllBox()">全选</a>');
		checkDefBox(tableId);
	});
	
	//打开列表,默认选中之前已经选择的值
	function checkDefBox(){
		var defValueArray = defValue.split(",");
		if(txType == '4' || txType == '8' || txType == '9'){//业务品种
			$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				//如果编号相同,选择
				if($.inArray($(this).parents('tr').find('td').eq(1).html(),defValueArray)!=-1){
					$(this).prop("checked",true);
				}
    		});
		}else{
			$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				//如果编号相同,选择
				if($.inArray($(this).parents('tr').find('td').eq(1).find('input').val(), defValueArray)!=-1){
					$(this).prop("checked",true);
				}
    		});
		}
	}
	
	//全选
	function checkAllBox(){
		var checkAllFlag = true;//全选标志
		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
			if(!$(this).prop("checked")){
				checkAllFlag = false;
				return false;
			}
    	});
    	if(checkAllFlag){
    		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				$(this).prop("checked",false);
    	 	});
    	}else{
    		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				$(this).prop("checked",true);
    	 	});
    	}
	}
	
	//获取 查询条件（方法名固定写法）
	function getFilterValArr(){
		$('#customQuery').val($("#filter_in_input").val());
		return JSON.stringify($('#assistForm').serializeArray());
	}
	
	/* function OnDblClick(event, ajaxUrl) {
		callBackThis(event);
	};
	
	function selectThis(dom){
		var tr=$(dom).parents("tr")[0];
		callBackThis(tr);
	}
	function callBackThis(dom){
		var obj = {};
		obj.txCode = $(dom).children('td').eq(0).text();
		obj.txName = $(dom).children('td').eq(1).text();
		top.cwBackData = obj;//获取摘要返回父页面
		$(top.window.document).find("#showDialog .close").click();//关闭此页面
		//给单击进来的文本框赋值
	} */
	function showValue(){
		var brObj = {};
		var no = '';
		var name = '';
		if(txType == '4'  || txType == '8' || txType == '9'|| txType == '38'|| txType == '39'|| txType == '44'){//业务品种
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				no += $(this).parent().parent().find('td').eq(1).html()+",";
				name += $(this).parents("tr").find('td').eq(2).html()+",";
    		});
		}else if(txType == '7'|| txType == '24'){//借据号、客户号、票据号
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				no += $(this).parent().parent().find('td').eq(1).html()+",";
				name += $(this).parents("tr").find('td').eq(1).html()+",";
    		});
		}else{
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				no += $(this).parent().parent().find('td').eq(1).find('input').val()+",";
				name += $(this).parents("tr").find('td').eq(2).html()+",";
    		});
		}
    	if(no!=''){
    		no = no.substring(0,no.length-1);
    		name = name.substring(0,name.length-1);
    	}
    	brObj.no = no;
    	brObj.name = name;
    	callBackAllValue(brObj);
	}
	
	function callBackAllValue(brObj){
		/* var obj = {};
		obj.txCode = $(dom).children('td').eq(0).text();
		obj.txName = $(dom).children('td').eq(1).text(); */
		//top.cwBackData = obj;//获取摘要返回父页面
		top.brNoData = brObj;
		$(top.window.document).find("#showDialog .close").click();//关闭此页面
	}
	
	</script>
</head>
<body>
<div class="assist-dialog">
	<!--页面显示区域-->
	<div class="row column" >
		<div class="col-xs-5">
			<!-- <input type="button" class="btn btn-info hidden" id="add_btn" onclick="showAjaxInput();" value="新增"> -->
		</div> 
		<div id="searchDiv" class="col-xs-7 znsearch-div">
			<form id="assistForm">
				<div class="hidden">
					<input type="hidden" name="keyName" id="keyName" value="${param.keyName}">
					<input type="hidden" name="txType" id="txType" value="${param.txType }">
					<input type="hidden" name="brNo" id="brNo" value="${param.brNoDef }">
					<input type="hidden" name="customQuery" id="customQuery" value="">
				</div>
				<div class="input-group pull-right">
					<i class="i i-fangdajing"></i>
					<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索" onkeypress="if(event.keyCode==13) {filter_btn_search.click();return false;}">
					<span class="input-group-addon" id="filter_btn_search">搜索</span>
				</div>
			</form>
		</div>
	</div>
	<div class="row clearfix content_table">
		<div class="col-md-12 column ">
			<div id="content" class="table_content cw_assist_list"  style="height: auto;">
				<!--待定是否放置自定义table标签?-->
<%-- 					<dhcc:tableTag property="tablevoucher0001" paginate="CwVoucherMstList" head="true"></dhcc:tableTag> --%>
				
			</div>
		</div>
	</div>
	<div class="formRowCenter" style="height:80px;">
		<dhcc:thirdButton value="保存" action="保存" onclick="showValue();"></dhcc:thirdButton> 
	</div>
</div>
</body>
</html>