<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>会计科目列表</title>
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
	var txType = '${param.txType}';
	$(function(){
		if(txType!='1000001' && txType!='1000002' && txType!='1000003'){
			$('#add_btn').removeClass('hidden');
		}
		//加载列表
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/cwFicationData/getFicationDataAjax",//列表数据查询的url
	    	tableId:"tableficationdata0002",//列表数据查询的table编号
	    	tableType:"tableTag",//table所需解析标签的种类
//		    	myFilter:false, //是否有我的筛选(列表列动态切换)
	    	pageSize:10,//加载默认行数(不填为系统默认行数)
	    	data:{},//指定参数
	    	ownHeight:true,
	    	callback:function(){
	    		
	    	}//方法执行完回调函数（取完数据做处理的时候）
	    });
	});
	
	//获取 查询条件（方法名固定写法）
	function getFilterValArr(){
		$('#customQuery').val($("#filter_in_input").val());
		return JSON.stringify($('#assistForm').serializeArray());
	}
	
	function OnDblClick(event, ajaxUrl) {
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
	}
	function ajaxInsertForm(obj){
		var txCode = $('#txCode').val();
		if(txCode==''){
			alert(top.getMessage("NOT_FORM_EMPTY", "编号"), 1);
			return false;
		}
		var txName = $('#txName').val();
		if(txName==''){
			alert(top.getMessage("NOT_FORM_EMPTY", "名称"), 1);
			return false;
		}
// 		ajaxSaveFour(this.form,'${webPath}/cwFicationData/insertAjax');
		var dataParam = {'txType': $('#txType').val(), 'txCode':txCode, 'txName':txName};
		jQuery.ajax({
			url : '${webPath}/cwFicationData/insertDialogAjax',
			data : {ajaxData : JSON.stringify(dataParam)},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					updateTableData();//重新加载列表数据
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	function ajaxCloseForm(obj){
		updateTableData();//重新加载列表数据
	}
	
	function showAjaxInput(){
		var table = $('#tablist>tbody');
		var $tr = $('<tr></tr>');
		var $td1 = $('<td><input type="text" title="编号：" id="txCode" name="txCode" maxlength="50" style="width:100px;"></td>');
		var $td2 = $('<td><input type="text" title="名称：" id="txName" name="txName" maxlength="50"><button class="btn btn-info btn-xs" onclick="ajaxInsertForm(this)">保存</button><button class="btn btn-xs" onclick="ajaxCloseForm(this)">取消</button></td>');
		$tr.append($td1).append($td2);
		table.prepend($tr);
		
	}
	</script>
</head>
<body>
<div class="assist-dialog">
	<!--页面显示区域-->
	<div class="row column" >
		<div class="col-xs-5">
			<input type="button" class="btn btn-info hidden" id="add_btn" onclick="showAjaxInput();" value="新增">
		</div>
		<div id="searchDiv" class="col-xs-7 znsearch-div">
			<form id="assistForm">
				<div class="hidden">
					<input type="hidden" name="txType" id="txType" value="${param.txType }">
					<input type="hidden" name="customQuery" id="customQuery" value="">
				</div>
				<div class="input-group pull-right">
					<i class="i i-fangdajing"></i>
					<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
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
</div>
</body>
</html>