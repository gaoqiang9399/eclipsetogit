<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>其他入库单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    //var otherInTypes = '${dataMap.otherInTypes}';
		    //var storeIds = '${dataMap.storeIds}';
		    
			$(function() {
				//客户新组件
				$("input[name=supplier]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.cus,
 					changeCallback : function (obj, elem) {
						$("input[name=supplierName]").val(obj.data("text"));
					} 
				});
				
				//初始化业务类别
				/* if(otherInTypes){
					var pact = eval(otherInTypes);
					var opt = $('#otherInType')[0];
					opt.options.length=0;
					for(var i=0; i<pact.length; i++){
						opt.add(new Option(pact[i].optName, pact[i].optCode));
					}
				} */
				$("#otherInType").val("0");
				$("#otherInType").popupSelection({
					searchOn:false,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.poiTypeArray,
					changeCallback : function (obj, elem) {
						
					}
				});
				var otherInTypePopsDiv = $("#otherInType").nextAll("div .pops-value").get(0);
				if(otherInTypePopsDiv != undefined){
					var otherInTypeLeft = $(otherInTypePopsDiv).position().left;
					var otherInTypeSelectDiv = $("#otherInType").next("div .pops-select").get(0);
					$(otherInTypePopsDiv).width(80);
					$(otherInTypeSelectDiv).css({'left':otherInTypeLeft});
					$(otherInTypeSelectDiv).width(108); //selectDiv比PopsDiv长28px
					$(otherInTypeSelectDiv).find("li").width(60);
				}
				
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssOtherStockInDetailBill/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepssotherstockindetailbill0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence", "goodsName", ["storehouseName"], "unitName");
			    		
			    		var stringThs = new Array("memo");
			    		Pss.addStringEvent(stringThs);
			    		
			    		var quantityThs = new Array("otherInQty");
			    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig, calInStockCostByQty);
			    		
			    		Pss.addGoodsEvent("goodsName", "goodsId", PssStockCommon.afterCheckGoods);
						Pss.addStorehouseEvent("storehouseName", "storehouseId", PssStockCommon.afterSelectStorehouse);
			    		
			    		PssStockCommon.calInStockCost("otherInQty", "inUnitCost", "inCost", ajaxData.pssConfig, "mul");
			    		
			    		var tabHeadThs = new Array("goodsName", "storehouseName", "otherInQty", "inUnitCost");
						Pss.pssAddTabMustEnter(tabHeadThs, "#content");
						
						Pss.dealSearchFlagHead("searchFlag", "goodsId", "#content");
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    
				$('.footer_loader').remove();
			    
			    $("table.table-float-head").remove();
			    
				$('.pss-date').on('click', function() {
					fPopUpCalendarDlg({
						isclear : true,
						choose : function(data) {
						}
					});
				});
				
				$('.pss_detail_list').css('height', 'auto');
			    $('#mCSB_1').css('height', 'auto');
			    
			    myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				
			});
			
			function calInStockCostByQty(obj, qtyTh, qtyVal, pssConfig){
				PssStockCommon.calInStockCostPlus(obj, qtyTh, qtyVal, pssConfig, "inUnitCost", "inCost", 'mul');
			};
			
			//选择完商品调用
			/* function afterCheckGoods($tr, trIndex, goods) {
				//仓库
				var storehouseNameThIndex = $("#tablist>thead th[name=storehouseName]").index();
				var $storehouseNameTd = $tr.children("td:eq(" + storehouseNameThIndex + ")");
				var storehouseIdThIndex = $("#tablist>thead th[name=storehouseId]").index();
				var $storehouseIdTd = $tr.children("td:eq(" + storehouseIdThIndex + ")");
				$storehouseIdTd.children("input[name=storehouseId]").val(goods.storehouseId);
				var $storehouseInput = $("<input style='display:none;' id='storehouseInput" + trIndex + "'></input>");
				$storehouseNameTd.children().remove();
				$storehouseNameTd.append($storehouseInput);
				if (goods.storehouseId.length > 0) {
					$storehouseNameTd.children("input").val(goods.storehouseId);
				}
				$storehouseNameTd.children("input").popupSelection({
					searchOn:false, //启用搜索
					inline:true, //下拉模式
					multiple:false, //多选
					items:ajaxData.storehouse,
					changeCallback : function (obj, elem) {
						$storehouseIdTd.children("input[name=storehouseId]").val(obj.val());
					}
				});
			}; */
			
			//保存
			function saveOrder(obj) {
				LoadingAnimate.start();
				//校验：商品必输，仓库必输，数量必输，入库单价
				var tabHeadThs = new Array("goodsName", "storehouseName", "otherInQty", "inUnitCost");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssotherstockinbill0002 input[name='billDate']");
					$billDate.val(billDateVal);
					
					var otherStockInNoVal = $("#top-otherStockInNo").val();
					if(otherStockInNoVal == null || otherStockInNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $otherStockInNo = $("#formpssotherstockinbill0002 input[name='otherStockInNo']");
					$otherStockInNo.val(otherStockInNoVal);
					
					var supplierVal = $("#supplier").val();
					var $supplier = $("#formpssotherstockinbill0002 input[name='supplierId']");
					$supplier.val(supplierVal);
					
					var otherInTypeVal = $("#otherInType").val();
					var $otherInType = $("#formpssotherstockinbill0002 input[name='otherInType']");
					$otherInType.val(otherInTypeVal);
					
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
									$('button[type="button"]').attr("disabled", true);
									top.isFresh = true;//回调时刷新
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "其他入库单"), 1);
									window.location.href = webPath+"/pssOtherStockInBill/getById?otherStockInId="+data.otherStockInId;
								}
							} else if(data.flag == "error"){
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url), 0);
						}
					});
				}else{
					LoadingAnimate.stop();
				}
			};
			
			//保存并新增
			function saveAndAddOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，仓库必输，数量必输，入库单价
				var tabHeadThs = new Array("goodsName", "storehouseName", "otherInQty", "inUnitCost");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssotherstockinbill0002 input[name='billDate']");
					$billDate.val(billDateVal);
					
					var otherStockInNoVal = $("#top-otherStockInNo").val();
					if(otherStockInNoVal == null || otherStockInNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $otherStockInNo = $("#formpssotherstockinbill0002 input[name='otherStockInNo']");
					$otherStockInNo.val(otherStockInNoVal);
					
					var supplierVal = $("#supplier").val();
					var $supplier = $("#formpssotherstockinbill0002 input[name='supplierId']");
					$supplier.val(supplierVal);
					
					var otherInTypeVal = $("#otherInType").val();
					var $otherInType = $("#formpssotherstockinbill0002 input[name='otherInType']");
					$otherInType.val(otherInTypeVal);

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
									$('button[type="button"]').attr("disabled", true);
									top.isFresh = true;//回调时刷新
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "其他入库单"), 1);
									window.location.href = webPath+"/pssOtherStockInBill/input";
								}
							} else if(data.flag == "error"){
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url), 0);
						}
					});
				}else{
					LoadingAnimate.stop();
				}
			};
			
			//审核
			function auditOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，仓库必输，数量必输，入库单价
				var tabHeadThs = new Array("goodsName", "storehouseName", "otherInQty", "inUnitCost");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssotherstockinbill0002 input[name='billDate']");
					$billDate.val(billDateVal);
					
					var otherStockInNoVal = $("#top-otherStockInNo").val();
					if(otherStockInNoVal == null || otherStockInNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $otherStockInNo = $("#formpssotherstockinbill0002 input[name='otherStockInNo']");
					$otherStockInNo.val(otherStockInNoVal);
					
					var supplierVal = $("#supplier").val();
					var $supplier = $("#formpssotherstockinbill0002 input[name='supplierId']");
					$supplier.val(supplierVal);
					
					var otherInTypeVal = $("#otherInType").val();
					var $otherInType = $("#formpssotherstockinbill0002 input[name='otherInType']");
					$otherInType.val(otherInTypeVal);
					
					var url = webPath+"/pssOtherStockInBill/auditOrderAjax";
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
									alert(data.data.failed ,2);
								}else{
									$('button[type="button"]').attr("disabled", true);
									top.isFresh = true;//回调时刷新
									window.top.alert("其他入库单审核成功！", 1);
									window.location.href = webPath+"/pssOtherStockInBill/getById?otherStockInId="+data.otherStockInId;
								}
							} else if(data.flag == "error"){
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage(
									"ERROR_REQUEST_URL", url), 0);
						}
					});
				}else{
					LoadingAnimate.stop();
				}
			};
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-other-stock-in-insert">
					<button type="button" class="btn btn-primary" onclick="saveAndAddOrder('#formpssotherstockinbill0002');">保存并新增</button>
					<button type="button" class="btn btn-default" onclick="saveOrder('#formpssotherstockinbill0002');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-other-stock-in-check">
					<button type="button" class="btn btn-default" onclick="auditOrder('#formpssotherstockinbill0002');">审核</button>
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
										<span class="txt fl">供应商：</span> 
										<input class="items-btn" id="supplier" name="supplier"/>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
										<span class="txt">业务类别：</span> 
										<!-- <select class="items-btn menu-btn" name="otherInType" id="otherInType" autocomplete="off"></select> -->
										<input class="items-btn" id="otherInType" name="otherInType" style="width:100px;"/>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-otherStockInNo" name="top-otherStockInNo" value="${dataMap.otherStockInNo} "/>
									</div>
								</div>
							</div>
						</div>
						<!--页面显示区域-->
						<div class="pss-bigform-table">
							<div id="content" class="table_content pss_detail_list">
							</div>
						</div>
						<div class="pss-bigform-form">
							<div class="bootstarpTag">
								<form method="post" enctype="multipart/form-data" id="formpssotherstockinbill0002" theme="simple" name="operform" action=webPath+"/pssOtherStockInBill/saveOrderAjax">
									<dhcc:bootstarpTag property="formpssotherstockinbill0002" mode="query" />
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