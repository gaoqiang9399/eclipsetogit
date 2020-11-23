<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
	var cusNo = '${cusNo}';
	$(function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/cwBillManage/findPactListByAjax",//列表数据查询的url
			tableId:"tablecwbillmanageList",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{cusNo:cusNo},//指定参数
			pageSize:30,//加载默认行数(不填为系统默认行数)
			callback:function(){
				$('#content tbody tr').each(function(index,item){//给编辑按钮绑定单击事件
					var billSts = $(item).find("input[type=hidden][name=billSts]").val();
					if(billSts != "2"){
						$(item).find("input[type=checkbox]").attr("disabled","disabled")
					}
				})
			}
		});
	 });

	function cwBillApply(obj,lk) {
		top.openBigForm(webPath + lk, "开票信息", function () {
			window.location.reload();
		});
	}

	// 根据到账ID查看发票列表
	function lookInvoice(obj,lk) {
		dialog({
			id:'lookInvoiceList',
			title:"选择发票",
			url:webPath+lk,
			width:900,
			height:400,
			backdropOpacity:0,
			onshow:function(){

			},onclose:function(){

			}
		}).showModal();
	}

	//批量开票
	function batchDelivery() {
		window.top.alert("确定批量费用开票？",2,function(){
			var idStr = "";
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				val = this.value.split('&') [0] ;
				idStr=idStr+","+val.split("=")[1];
			});
			if(idStr==""){
				window.top.alert(top.getMessage("FIRST_SELECT_FIELD","费用开票的数据"), 0);
				return false;
			}
			idStr=idStr.substr(1);
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ids : idStr
				},
				url : "${webPath}/cwBillManage/batchDelivery",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
						window.location.reload();
					}else{
						window.top.alert(data.msg,0);
					}
				},error : function(xmlhq, ts, err) {
					loadingAnimate.stop();
				}
			});
		});
	}
</script>
</head>
<body class="overflowHidden">
<div class="container" >
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div style="display:none;">
				<input name="cusName" type="hidden"></input>
				<input name="cusNo" type="hidden"></input>
			</div>
			<div class="btn-div">
				<%--<dhcc:pmsTag pmsId="auth-credit-list-add">
					<button type="button" class="btn btn-primary" onclick="batchDelivery();">批量开票</button>
				</dhcc:pmsTag>--%>
				<button type="button" class="btn btn-primary" onclick="batchDelivery();">批量开票</button>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;max-height: calc(100% - 140px);">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [
		{
			"optName": "发票类型",
			"parm": ${typeJsonArray},
			"optCode":"invoiceType",
			"dicType":"y_n"
		},{
			"optName" : "部门",
			"parm" : [],
			"optCode" : "brName",
			"dicType" : "val"
		},{
			"optName": "状态",
			"parm": ${statusJsonArray},
			"optCode":"billSts",
			"dicType":"y_n"
		},{
			"optCode" : "collectTime",
			"optName" : "费用确认日期",
			"dicType" : "date"
		}
	];
</script>
</html>
