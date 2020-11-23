<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>商品库存预警配置</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/purchases/css/PssBuyOrder_Add.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<style type="text/css">
			.pss-date {
				background-color: #FFF;
				cursor: auto;
				background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right 0 no-repeat #FFF;
			}
			.pops-value {
				display: inline-block;
				margin-right: 10px;
				padding: 0 13px;
				height: 28px;
				/* border: 1px solid #c3c7cb; */
				border: 0px;
				border-radius: 2px;
				/* background: #fafdff; */
				background: none;
				vertical-align: middle;
				cursor: pointer;
				width: 169px;
				padding-top: 3px;
			}
		</style>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
			var pssConfig={};
			pssConfig.numDecimalDigit=0;
		    ajaxData =JSON.parse(ajaxData);
		    var storeIds = '${dataMap.storeIds}';
		    
			$(function() {
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssAlloTransDetailBill/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepssallotransdetailbill0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:5,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence");
			    		//var moneyThs = new Array("unitPrice", "taxUnitPrice", "discountAmount", "amount", "taxAmount", "totalPriceWithTax");
			    		//addMoneyEvent(moneyThs, 2);
			    		//var doubleThs = new Array("discountRate", "taxRate");
			    		//addDoubleEvent(doubleThs, 6);
			    		//var stringThs = new Array("goodsId", "unitId", "storehouseId", "memo");
			    		var stringThs = new Array("memo");//"outStorehouseId"
			    		Pss.addStringEvent(stringThs);
			    		var quantityThs = new Array("goodsQty");
			    		Pss.addQuantityEvent(quantityThs, pssConfig);
			    		
			    		PssStockCommon.addSelectGoodsEvent("goodsId");
			    		
			    		PssStockCommon.addSelectStoresEvent(new Array("inStorehouseId", "outStorehouseId"));
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    
			    //$('.footer_loader').remove();
			    
				$('.pss-date').on('click', function() {
					fPopUpCalendarDlg({
						isclear : true,
						/* min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
						max: currDate + ' 23:59:59', //最大日期 */
						choose : function(data) {
						}
					});
				});
				
			});
									
			/* function storeChange(obj){
				obj.text($('#storeId option:selected').val());
			}; */
			
			//保存
			function saveOrder(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssallotransbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var alloTransNoVal = $("#top-alloTransNo").val();
					if(alloTransNoVal == null || alloTransNoVal == ""){
						alert("单据编号不能为空！",1);
						return;
					}
					var $alloTransNo = $("#formpssallotransbill0002 input[name='alloTransNo']");
					$alloTransNo.val(alloTransNoVal);

					var url = $(obj).attr("action");
					var dataParm = JSON.stringify($(obj).serializeArray());

					/* var jsonStr = "[";
					var names = "";
					$(".pss-bigform-table table thead th").each(function() {
						names += ",\"" + $(this).attr("name") + "\"";
					});
					names = "[" + names.substring(1) + "]";
					jsonStr += names + ",";
					var values = "";
					$(".pss-bigform-table table tbody tr").each(function() {
						values = "";
						$(this).find("td").each(function() {
							values += ",\"" + $(this).text() + "\"";
						});
						values = "[" + values.substring(1) + "]";
						jsonStr += values + ",";
					});
					jsonStr = jsonStr.substring(0, jsonStr.length - 1);
					jsonStr += "]"; */
					//var jsonArr  = JSON.parse(jsonStr);     // 字符串转为json对象
					//var jsonArr = eval('(' + jsonStr + ')');
					
					//隐藏域中值暂时无法获取到
					var jsonArr = new Array();
					$(".pss-bigform-table table tbody tr").each(function(trIndex, tr) {
						var jsonObj = new Object();
						$(".pss-bigform-table table thead th").each(function(thIndex, th) {
							jsonObj[$(th).attr("name")] = $(tr).children("td:eq(" + thIndex + ")").text();
						});
						jsonArr.push(jsonObj);
					});

					LoadingAnimate.start();
					jQuery.ajax({
						url : url,
						data : {
							ajaxData : dataParm,
							"jsonArr" : JSON.stringify(jsonArr)
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "调拨单"), 1);
								window.location.href = webPath+"/pssAlloTransBill/getById?alloTransNo="+alloTransNoVal;
							} else if(data.flag == "error"){
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url));
						}
					});
				}
			};
			
			//保存并新增
			function saveAndAddOrder(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					var $billDate = $("#formpssallotransbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var alloTransNoVal = $("#top-alloTransNo").val();
					var $alloTransNo = $("#formpssallotransbill0002 input[name='alloTransNo']");
					$alloTransNo.val(alloTransNoVal);

					var url = $(obj).attr("action");
					var dataParm = JSON.stringify($(obj).serializeArray());

					/* var jsonStr = "[";
					var names = "";
					$(".pss-bigform-table table thead th").each(function() {
						names += ",\"" + $(this).attr("name") + "\"";
					});
					names = "[" + names.substring(1) + "]";
					jsonStr += names + ",";
					var values = "";
					$(".pss-bigform-table table tbody tr").each(function() {
						values = "";
						$(this).find("td").each(function() {
							values += ",\"" + $(this).text() + "\"";
						});
						values = "[" + values.substring(1) + "]";
						jsonStr += values + ",";
					});
					jsonStr = jsonStr.substring(0, jsonStr.length - 1);
					jsonStr += "]"; */
					//var jsonArr  = JSON.parse(jsonStr);     // 字符串转为json对象
					//var jsonArr = eval('(' + jsonStr + ')');
					
					//隐藏域中值暂时无法获取到
					var jsonArr = new Array();
					$(".pss-bigform-table table tbody tr").each(function(trIndex, tr) {
						var jsonObj = new Object();
						$(".pss-bigform-table table thead th").each(function(thIndex, th) {
							jsonObj[$(th).attr("name")] = $(tr).children("td:eq(" + thIndex + ")").text();
						});
						jsonArr.push(jsonObj);
					});

					LoadingAnimate.start();
					jQuery.ajax({
						url : url,
						data : {
							ajaxData : dataParm,
							"jsonArr" : JSON.stringify(jsonArr)
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								//初始化查询
								window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "调拨单"), 1);
								window.location.href = webPath+"/pssAlloTransBill/input";
							} else if(data.flag == "error"){
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url));
						}
					});
				}
			};
			
			//保存并新增
			function auditOrder(obj) {
				alert('建设中，敬请关注...',1);
			};
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<button type="button" class="btn btn-primary" onclick="saveAndAddOrder('#formpssallotransbill0002');">保存并新增</button>
						<button type="button" class="btn btn-default" onclick="saveOrder('#formpssallotransbill0002');">保存</button>
						<button type="button" class="btn btn-default" onclick="auditOrder('#formpssallotransbill0002');">审核</button>
					</div>
				</div>
				<div class="search-div">
					<div class="col-xs-11 column mysearch-div" id="pills">
						<div class="mod-toolbar-top">
							<div class="left">
								<span class="txt">单据日期：</span>
								<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" size="15" readonly value="${dataMap.currDate }"></input>
							</div>
							<div class="right">
								<span class="txt">单据编号：</span>
								<input class="items-btn" type="text" id="top-alloTransNo" name="top-alloTransNo" value="${dataMap.alloTransNo} "/>
							</div>
						</div>
					</div>
				</div>
				<hr></hr>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="pss-bigform-table">
			<div id="content" class="table_content" style="height:auto; width:auto;">
			</div>
		</div>
	</body>
</html>