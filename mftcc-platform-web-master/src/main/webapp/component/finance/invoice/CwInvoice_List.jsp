<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
	<style>
		table {
			border-collapse: collapse;
			border-spacing: 0;
			margin: 0 0 30 0;
			font-size:12px;
			border:none;
		}
	</style>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/commondy.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/fpdy2.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/LodopFuncs.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/print.js"></script>
<script type="text/javascript" >
	$(function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/cwInvoice/findInvoiceListByAjax",//列表数据查询的url
			tableId:"tablecwinvoiceList",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			callback:function(){
				$('#content tbody tr').each(function(index,item){//给编辑按钮绑定单击事件
					/*var billSts = $(item).find("input[type=hidden][name=billSts]").val();
					if(billSts != "2"){
						$(item).find("input[type=checkbox]").attr("disabled","disabled")
					}*/
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

	// 预览
	function preview() {
		var ids = $('.table_content #tab').find($('input[type=checkbox]:checked'));
		if(ids.length == 0){
			window.top.alert("请选择一条记录", 0);
			return false;
		}
		if(ids.length > 1){
			window.top.alert("只能选择一条记录", 0);
			return false;
		}
		var val = ids[0].value.split('&') [0] ;
		var invoiceId = val.split("=")[1];

		LoadingAnimate.start();
		$.ajax({
			type : "POST",
			url : "${webPath}/cwInvoice/getPrintInfoById/"+invoiceId,
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				// 根据发票类型代码，判断是专票或者普票
				var jsonObj = JSON.parse(data.data);
				if(jsonObj.fplxdm == "004"){
					console.log("专票预览");
					printZzP_sz(data.data,true,-4,-19.5);
				}else if(jsonObj.fplxdm == "007"){
					console.log("普票预览");
					printZpP_sz(data.data,true,-6.5,-19.5);
				}
			},error : function(xmlhq, ts, err) {
				LoadingAnimate.stop();
			}
		});
	}

	// 打印
	function print() {
		var ids = $('.table_content #tab').find($('input[type=checkbox]:checked'));
		if(ids.length == 0){
			window.top.alert("请选择一条记录", 0);
			return false;
		}
		if(ids.length > 1){
			window.top.alert("只能选择一条记录", 0);
			return false;
		}
		var val = ids[0].value.split('&') [0] ;
		var invoiceId = val.split("=")[1];

		LoadingAnimate.start();
		$.ajax({
			type : "POST",
			url : "${webPath}/cwInvoice/getPrintInfoById/"+invoiceId,
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				// 根据发票类型代码，判断是专票或者普票
				var jsonObj = JSON.parse(data.data);
				if(jsonObj.fplxdm == "004"){
					console.log("专票打印");
					printZzP_sz(data.data,false,-4,-19.5);
				}else if(jsonObj.fplxdm == "007"){
					console.log("普票打印");
					printZpP_sz(data.data,false,-6.5,-19.5);
				}
			},error : function(xmlhq, ts, err) {
				LoadingAnimate.stop();
			}
		});
	}

	// 批量打印
	function batchPrint() {
		window.top.alert("确定批量打印？",2,function(){
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
				url : "${webPath}/cwInvoice/batchPrint",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						var jsonArray =  eval("("+data.data+")");
						console.log("批量打印张数："+jsonArray.length)
						for(var i=0; i<jsonArray.length; i++){
							var jsonObj = jsonArray[i];
							var jsonString = JSON.stringify(jsonObj);
							if(jsonObj.fplxdm == "004"){
								console.log("批量-专票打印");
								printZzP_sz(jsonString,false,-4,-19.5);
							}else if(jsonObj.fplxdm == "007"){
								console.log("批量-普票打印");
								printZpP_sz(jsonString,false,-6.5,-19.5);
							}
						}
					}else{
						var msg = JSON.parse(data.msg);
						window.top.alert(msg.message,0);
					}
				},error : function(xmlhq, ts, err) {
					loadingAnimate.stop();
				}
			});
		});
	}

	// 导出excel
	function exportExcel() {
		var idStr = "";
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
			val = this.value.split('&') [0] ;
			idStr=idStr+","+val.split("=")[1];
		});
		if(idStr==""){
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","要导出的发票信息"), 0);
			return false;
		}
		idStr=idStr.substr(1);
		//LoadingAnimate.start();
		window.location.href = webPath+"/cwInvoice/exportExcel?ids="+idStr;
	}

</script>
</head>
<body class="overflowHidden">
<div class="container">
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
				<button type="button" class="btn btn-primary" onclick="preview();">预览</button>
				<button type="button" class="btn btn-primary" onclick="print();">打印</button>
				<button type="button" class="btn btn-primary" onclick="batchPrint();">批量打印</button>
				<button type="button" class="btn btn-primary" onclick="exportExcel();">导出</button>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=购方名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
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
			"parm": ${invoiceTypeCodeArray},
			"optCode":"fplxdm",
			"dicType":"y_n"
		},{
			"optName" : "购方名称",
			"optCode" : "gfmc",
			"dicType" : "val"
		},{
			"optCode" : "kprq",
			"optName" : "开票日期",
			"dicType" : "date"
		}
	];
</script>
</html>
