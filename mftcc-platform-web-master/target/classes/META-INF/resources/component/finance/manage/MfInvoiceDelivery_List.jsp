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
			url:webPath+"/mfInvoiceDelivery/findListByAjax",//列表数据查询的url
			tableId:"tablemfinvoicedeliveryList",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{cusNo:cusNo},//指定参数
			pageSize:30,//加载默认行数(不填为系统默认行数)
			callback:function(){
				$('#content tbody tr').each(function(index,item){//给编辑按钮绑定单击事件
					var billSts = $(item).find("input[type=hidden][name=billSts]").val();
					if(billSts != "4"){
						$(item).find("input[type=checkbox]").attr("disabled","disabled")
					}
				})
			}
		});
	 });

	//寄送登记
	function postRegist(obj,lk) {
		top.openBigForm(webPath + lk, "寄送登记", function () {
			window.location.reload();
		});
	}

	//批量收妥确认
	function receiveConfim(lk) {
		window.top.alert("确定收妥确认？",2,function(){
			LoadingAnimate.start();
			var id = lk.split("?")[1].split("=")[1];
			$.ajax({
				type : "POST",
				data:{
					id : id
				},
				url : "${webPath}/mfInvoiceDelivery/receiveConfimAjax",
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

	//批量收妥确认
	function deliveryBills() {
		window.top.alert("确定批量收妥确认？",2,function(){
			var idStr = "";
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				val = this.value.split('&') [0] ;
				idStr=idStr+","+val.split("=")[1];
			});
			if(idStr==""){
				window.top.alert(top.getMessage("FIRST_SELECT_FIELD","发票寄送的数据"), 0);
				return false;
			}
			idStr=idStr.substr(1);
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ids : idStr
				},
				url : "${webPath}/mfInvoiceDelivery/deliveryBillsAjax",
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
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div style="display:none;">
				<input name="cusName" type="hidden"></input>
				<input name="cusNo" type="hidden"></input>
			</div>
			<div class="btn-div">
				<%--<dhcc:pmsTag pmsId="auth-credit-list-add">
					<button type="button" class="btn btn-primary" onclick="deliveryBills();">批量收妥确认</button>
				</dhcc:pmsTag>--%>
					<button type="button" class="btn btn-primary" onclick="deliveryBills();">批量收妥确认</button>
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
			  "optName": "合同金额",
			  "parm": [],
			  "optCode":"pactAmt",
			  "dicType":"num"
		  }, {
			  "optName": "签订日期",
			  "parm": [],
			  "optCode":"signDate",
			  "dicType":"date"
		  }, {
			  "optName": "合同期限",
			  "parm": [],
			  "optCode":"term",
			  "dicType":"num"
		  },{
			  "optCode" : "pactSts",
			  "optName" : "业务阶段",
			  "parm" : [ {
				 "optName" : "未完善",
				 "optCode" : "0"
			   }, {
				 "optName" : "未提交",
				 "optCode" : "1"
			   }, {
				 "optName" : "流程中",
				 "optCode" : "2"
			  }, {
				 "optName" : "退回",
				 "optCode" : "3"
			  }, {
				 "optName" : "审批通过",
				 "optCode" : "4"
			  }, {
				 "optName" : "已否决",
				 "optCode" : "5"
			  }, {
				 "optName" : "已完结",
				 "optCode" : "6"
			  } ],
			  "dicType" : "y_n"
		  },{
			  "optName": "开始日期",
			  "parm": [],
			  "optCode":"beginDate",
			  "dicType":"date"
		  },
	  ];
</script>
</html>
