<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>盘点</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<link rel="stylesheet" href='${webPath}/component/pss/stock/css/PssStock_common.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
			ajaxData = JSON.parse(ajaxData);
			//var storeIds = '${dataMap.storeIds}';

			$(function() {
				//初始赋值
				var checkStockNoVal = $(
						"#formpsscheckstockbill0002 input[name='checkStockNo']")
						.val();
				$("#top-checkStockNo").val(checkStockNoVal);
				var checkStockDateVal = $(
						"#formpsscheckstockbill0002 input[name='checkStockDate']")
						.val();
				$("#top-billDate").val(checkStockDateVal);
				var storehouseIdVal = $(
						"#formpsscheckstockbill0002 input[name='storehouseId']")
						.val();
				$("#top-storehouseId").val(storehouseIdVal);
				
				//初始化仓库
				/* if (storeIds) {
					var storeIdArr = eval(storeIds);
					var opt = $('#top-storehouseId')[0];
					opt.options.length = 0;
					for ( var i = 0; i < storeIdArr.length; i++) {
						opt.add(new Option(storeIdArr[i].storehouseName,
								storeIdArr[i].storehouseId));
					}
				} */
				$("#top-storehouseId").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.storehouse,
 					changeCallback : function (obj, elem) {
						//$("input[name=supplierName]").val(obj.data("text"));
					} 
				});
				var storehousePopsDiv = $("#top-storehouseId").nextAll("div .pops-value").get(0);
				if(storehousePopsDiv != undefined){
					var storehouseLeft = $(storehousePopsDiv).position().left;
					var storehouseSelectDiv = $("#top-storehouseId").next("div .pops-select").get(0);
					$(storehouseLeft).width(120);
					$(storehouseSelectDiv).css({'left':storehouseLeft});
					$(storehouseSelectDiv).width(228); //selectDiv比PopsDiv长28px
					$(storehouseSelectDiv).find("li").width(180);
					$(storehouseSelectDiv).find("div .pops-search").width(228);
				}
				
				myCustomScrollbar({
					obj : "#content",//页面内容绑定的id
					url : webPath+"/pssCheckStockDetailBill/getCheckStockDetailBillListAjax",//列表数据查询的url
					tableId : "tablepsscheckstockdetailbill0001",//列表数据查询的table编号
					tableType : "thirdTableTag",//table所需解析标签的种类
					pageSize : 10000,//加载默认行数(不填为系统默认行数)
					ownHeight : true,
					callback : function(options, data) {
						PssStockCommon.calChkStockResult("storeStockQty",
								"checkStockQty", "profitLossQty", ajaxData.pssConfig);
						var tabHeadThs = new Array("checkStockQty");
						Pss.pssAddTabMustEnter(tabHeadThs, "#content");
						
						$('.footer_loader').remove();

						$("table.table-float-head").remove();
					}//方法执行完回调函数（取完数据做处理的时候）
				});
				
				$('.pss-date').on('click', function() {
					fPopUpCalendarDlg({
						isclear : false,
						choose : function(data) {
						}
					});
				});

				$('.pss_detail_list').css('height', 'auto');
				$('#mCSB_1').css('height', 'auto');

				myCustomScrollbarForForm({
					obj : ".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				
			});

			//获取 查询条件（方法名固定写法）
			function getFilterValArr() {
				return JSON.stringify($('#formpsscheckstockbill0002').serializeArray());
			};

			function my_Search_Chk() {
				//单据日期
				var topBillDateVal = $("#top-billDate").val();
				var $topBillDateId = $("#formpsscheckstockbill0002 input[name='top-billDate']");
				$topBillDateId.val(topBillDateVal);
				
				//仓库
				var storehouseIdVal = $("#top-storehouseId").val();
				var $storehouseId = $("#formpsscheckstockbill0002 input[name='storehouseId']");
				$storehouseId.val(storehouseIdVal);
				
				var goodsNameVal = $("#top-goodsName").val();
				var $goodsName = $("#formpsscheckstockbill0002 input[name='goodsName']");
				$goodsName.val(goodsNameVal);
				
				if ($("#zeroStock").is(':checked')) {
					var zeroStockVal = $("#zeroStock").val();
					var $zeroStock = $("#formpsscheckstockbill0002 input[name='zeroStock']");
					$zeroStock.val(zeroStockVal);
				} else {
					var $zeroStock = $("#formpsscheckstockbill0002 input[name='zeroStock']");
					$zeroStock.val("");
				}
				
				myCustomScrollbar({
					obj : "#content",//页面内容绑定的id
					url : webPath+"/pssCheckStockDetailBill/findByPageAjax",//列表数据查询的url
					tableId : "tablepsscheckstockdetailbill0001",//列表数据查询的table编号
					tableType : "thirdTableTag",//table所需解析标签的种类
					pageSize : 10000,//加载默认行数(不填为系统默认行数)
					ownHeight : true,
					callback : function(options, data) {
						PssStockCommon.calChkStockResult("storeStockQty",
								"checkStockQty", "profitLossQty", ajaxData.pssConfig);
						var tabHeadThs = new Array("checkStockQty");
						Pss.pssAddTabMustEnter(tabHeadThs, "#content");
						
						//清空数据（清空form中盘点编号即可）
						//$("#formpsscheckstockbill0002 input[name='checkStockNo']").val("");
						$("#top-checkStockNo").val("");
						
						$('.footer_loader').remove();
						
						$("table.table-float-head").remove();
					}//方法执行完回调函数（取完数据做处理的时候）
				});
				
				$('.pss_detail_list').css('height', 'auto');
				$('#mCSB_1').css('height', 'auto');
			};
			
			//校验是否填写盘点库存
			var validateChkStockResult = function(chgTdName) {
				var chgThIndex = $(
						"#tablist>thead th[name='" + chgTdName + "']").index();
				var trs = $("#tablist>tbody tr");
				var boo = false;
				$(trs).each(
					function(index, tr) {
						var chgVal = $(tr).children(
								"td:eq(" + chgThIndex + ")").html();
						if (chgVal != '') {
							boo = true;
							return false;
						}
					});
				return boo;
			};
			
			//保存
			function saveOrder(obj) {
				LoadingAnimate.start();
				var flag = validateChkStockResult("checkStockQty");
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if (billDateVal == null || billDateVal == "") {
						LoadingAnimate.stop();
						alert("单据日期不能为空！", 1);
						return;
					}
					var $billDate = $("#formpsscheckstockbill0002 input[name='checkStockDate']");
					$billDate.val(billDateVal);

					var checkStockNoVal = $("#top-checkStockNo").val();
					var $checkStockNo = $("#formpsscheckstockbill0002 input[name='checkStockNo']");
					$checkStockNo.val(checkStockNoVal);

					var storehouseIdVal = $("#top-storehouseId").val();
					var $storehouseId = $("#formpsscheckstockbill0002 input[name='storehouseId']");
					$storehouseId.val(storehouseIdVal);

					var url = $(obj).attr("action");
					var dataParm = JSON.stringify($(obj).serializeArray());

					var jsonArr = new Array();
					jsonArr = PssStockCommon.tableJsonDeal();
					
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
								if(data.data.failed !=''){
									window.top.alert(data.data.failed ,2);
								}else{
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "盘点"), 1);
									$("#top-checkStockNo").val(data.checkStockNo);
								}
							} else if (data.flag == "error") {
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url), 0);
						}
					});
				} else {
					LoadingAnimate.stop();
					alert("请先填写盘点库存", 1);
				}
			};
			
			//生成盘点单据
			function generateChkBill(obj) {
				LoadingAnimate.start();
				var flag = validateChkStockResult("checkStockQty");
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if (billDateVal == null || billDateVal == "") {
						LoadingAnimate.stop();
						alert("单据日期不能为空！", 1);
						return;
					}
					var $billDate = $("#formpsscheckstockbill0002 input[name='checkStockDate']");
					$billDate.val(billDateVal);
					
					var checkStockNoVal = $("#top-checkStockNo").val();
					var $checkStockNo = $("#formpsscheckstockbill0002 input[name='checkStockNo']");
					$checkStockNo.val(checkStockNoVal);
					
					var storehouseIdVal = $("#top-storehouseId").val();
					var $storehouseId = $("#formpsscheckstockbill0002 input[name='storehouseId']");
					$storehouseId.val(storehouseIdVal);
					
					var url = webPath+"/pssCheckStockBill/generateChkBillAjax";
					var dataParm = JSON.stringify($(obj).serializeArray());
					
					var jsonArr = new Array();
					jsonArr = PssStockCommon.tableJsonDeal();
					
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
								if(data.data.failed !=''){
									window.top.alert(data.data.failed ,2);
								}else{
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "盘点"), 1);
									myclose_click();
								}
							} else if (data.flag == "error") {
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url), 0);
						}
					});
				} else {
					LoadingAnimate.stop();
					alert("请先填写盘点库存", 1);
				}
			};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-check-stock-insert">
					<button type="button" class="btn btn-primary" onclick="saveOrder('#formpsscheckstockbill0002');">保存盘点结果</button>
				</dhcc:pmsTag>
				<!-- <button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出系统库存</button>
				<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导入盘点库存</button> -->
				<dhcc:pmsTag pmsId="pss-check-stock-generate">
					<button type="button" class="btn btn-default" onclick="generateChkBill('#formpsscheckstockbill0002');">生成盘点单据</button>
				</dhcc:pmsTag>
			</div>
		</div>
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="col-md-12 column">
						<div class="search-div">
							<div class="col-xs-11 column mysearch-div" id="pills">
								<div class="mod-toolbar-top">
									<div class="left">
										<span class="txt f1">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
										<span class="txt">仓库：</span>
										<!-- <select class="items-btn" name="top-storehouseId" id="top-storehouseId" autocomplete="off" style="width:130px;"></select> -->
										<input class="items-btn" id="top-storehouseId" name="top-storehouseId"/>
										<span class="txt">商品：</span>
										<input class="items-btn" type="text" id="top-goodsName" name="top-goodsName" placeholder="商品名称" size="20" style="background: #FFF !important;">
										<input type="checkbox" id="zeroStock" name="zeroStock" value="1" />
										<span class="txt">零库存</span>
										<a class="ui-btn" onclick="my_Search_Chk();" id="psssearchchk">查询</a>
									</div>
									<div class="right" style="display:none">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-checkStockNo" name="top-checkStockNo"/>
									</div>
								</div>
							</div>
						</div>
						<!--页面显示区域-->
						<div class="pss-bigform-table">
							<div id="content" class="table_content pss_detail_list"></div>
						</div>
						<div class="pss-bigform-form">
							<div class="bootstarpTag">
								<form method="post" enctype="multipart/form-data" id="formpsscheckstockbill0002" theme="simple" name="operform" action="${webPath}/pssCheckStockBill/saveOrderAjax">
									<dhcc:bootstarpTag property="formpsscheckstockbill0002" mode="query" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>