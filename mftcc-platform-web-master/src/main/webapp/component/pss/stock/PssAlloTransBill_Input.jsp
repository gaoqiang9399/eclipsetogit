<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>调拨单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssAlloTransBill.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    //var storeIds = '${dataMap.storeIds}';
		    
			$(function() {
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssAlloTransDetailBill/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepssallotransdetailbill0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence", "goodsName", ["inStorehouseName", "outStorehouseName"], "unitName");
			    		var stringThs = new Array("memo");//"outStorehouseId"
			    		Pss.addStringEvent(stringThs);
			    		var quantityThs = new Array("goodsQty");
			    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig);
			    		
			    		Pss.addGoodsEvent("goodsName", "goodsId", PssAlloTransBill.afterSelectGoods);
			    		Pss.addStorehouseEvent("inStorehouseName", "inStorehouseId", PssAlloTransBill.afterSelectInStorehouse);
						Pss.addStorehouseEvent("outStorehouseName", "outStorehouseId", PssAlloTransBill.afterSelectOutStorehouse);
						
						var tabHeadThs = new Array("goodsName", "goodsQty", "outStorehouseName", "inStorehouseName");
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
			
			//选择完商品调用
			function afterCheckGoods($tr, trIndex, goods) {
				//仓库
				var storehouseNameThIndex = $("#tablist>thead th[name=outStorehouseName]").index();
				var $storehouseNameTd = $tr.children("td:eq(" + storehouseNameThIndex + ")");
				var storehouseIdThIndex = $("#tablist>thead th[name=outStorehouseId]").index();
				var $storehouseIdTd = $tr.children("td:eq(" + storehouseIdThIndex + ")");
				$storehouseIdTd.children("input[name=outStorehouseId]").val(goods.storehouseId);
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
						$storehouseIdTd.children("input[name=outStorehouseId]").val(obj.val());
						
						Pss.popsSelectSelected($storehouseNameTd);
					}
				});
				Pss.addPopsSelectClick($storehouseNameTd);
				
				$.ajax({
					url:webPath+"/pssUnitGoodsRel/getAllByGoodsIdAjax",
					data:{"goodsId":goods.id},
					type:'post',
					dataType:'json',
					success:function(data){
						if (data.success) {
							var pssUnitGoodsRelMap = data.pssUnitGoodsRelMap;
							Object.keys(pssUnitGoodsRelMap).length
							if(pssUnitGoodsRelMap !== null && pssUnitGoodsRelMap !== undefined && Object.keys(pssUnitGoodsRelMap).length > 0){
								//商品已配置单位
								var unitIdThIndex = $("#tablist>thead th[name=unitId]").index();
								var unitIdTd = $tr.children("td:eq(" + unitIdThIndex + ")");
								
								var unitIdInput = $("<input style='display:none;' id='unitIdInput" + trIndex + "'></input>");
								unitIdTd.children().remove();
								unitIdTd.append(unitIdInput);
								var unitIdJsonArray;
								for(var unitIdKey in pssUnitGoodsRelMap){
									unitIdJsonArray.push(pssUnitGoodsRelMap[unitIdKey]);
								}
								unitIdTd.children("input").popupSelection({
									searchOn:false, //启用搜索
									inline:true, //下拉模式
									multiple:false, //多选
									items:unitIdJsonArray,
									changeCallback : function (obj, elem) {
										Pss.popsSelectSelected(unitIdTd);
									}
								});
								Pss.addPopsSelectClick(unitIdTd);
							}
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
				
			};
			
			/* function storeChange(obj){
				obj.text($('#storeId option:selected').val());
			}; */
			
			//保存
			function saveOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，数量必输，出库必输，入库必输
				var tabHeadThs = new Array("goodsName", "goodsQty", "outStorehouseName", "inStorehouseName");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				var storeFlag = validateInOutStore("outStorehouseId", "inStorehouseId");
				if (flag && storeFlag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssallotransbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var alloTransNoVal = $("#top-alloTransNo").val();
					if(alloTransNoVal == null || alloTransNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $alloTransNo = $("#formpssallotransbill0002 input[name='alloTransNo']");
					$alloTransNo.val(alloTransNoVal);

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
									top.isFresh = true;//回调是刷新
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "调拨单"), 1);
									window.location.href = webPath+"/pssAlloTransBill/getById?alloTransId="+data.alloTransId;
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
				
				//校验：商品必输，数量必输，出库必输，入库必输
				var tabHeadThs = new Array("goodsName", "goodsQty", "outStorehouseName", "inStorehouseName");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				var storeFlag = validateInOutStore("outStorehouseId", "inStorehouseId");
				if (flag && storeFlag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssallotransbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var alloTransNoVal = $("#top-alloTransNo").val();
					if(alloTransNoVal == null || alloTransNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $alloTransNo = $("#formpssallotransbill0002 input[name='alloTransNo']");
					$alloTransNo.val(alloTransNoVal);

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
									top.isFresh = true;//回调是刷新
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "调拨单"), 1);
									window.location.href = webPath+"/pssAlloTransBill/input";
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
					LoadingAnimate.start();
				}
			};
			
			//审核
			function auditOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，数量必输，出库必输，入库必输
				var tabHeadThs = new Array("goodsName", "goodsQty", "outStorehouseName", "inStorehouseName");
				if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
					LoadingAnimate.stop();
					return;
				}
				
				var flag = submitJsMethod($(obj).get(0), '');
				var storeFlag = validateInOutStore("outStorehouseId", "inStorehouseId");
				if (flag && storeFlag) {
					var billDateVal = $("#top-billDate").val();
					if(billDateVal == null || billDateVal == ""){
						LoadingAnimate.stop();
						alert("单据日期不能为空！",1);
						return;
					}
					var $billDate = $("#formpssallotransbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var alloTransNoVal = $("#top-alloTransNo").val();
					if(alloTransNoVal == null || alloTransNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $alloTransNo = $("#formpssallotransbill0002 input[name='alloTransNo']");
					$alloTransNo.val(alloTransNoVal);

					var url = webPath+"/pssAlloTransBill/auditOrderAjax";
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
									top.isFresh = true;//回调是刷新
									alert(top.getMessage("SUCCEED_OPERATION") ,1);
									window.location.href = webPath+"/pssAlloTransBill/getById?alloTransId="+data.alloTransId;
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
			
			function validateInOutStore(inStore, outStore){
				var reVal = true;
				var inIndex = $("#tablist>thead th[name='" + inStore + "']").index();
				var outIndex = $("#tablist>thead th[name='" + outStore + "']").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr){
					var inVal = $(tr).children("td:eq(" + inIndex + ")").find("input").val();
					var outVal = $(tr).children("td:eq(" + outIndex + ")").find("input").val();
					if((null != inVal && "" != inVal) && (null != outVal && "" != outVal)){
						if(inVal == outVal){
							alert("调出仓库与调入仓库不能相同！", 1);
							reVal = false;
							return;
						}
					}
				});
				return reVal;
			};
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-allo-trans-insert">
					<button type="button" class="btn btn-primary" onclick="saveAndAddOrder('#formpssallotransbill0002');">保存并新增</button>
					<button type="button" class="btn btn-default" onclick="saveOrder('#formpssallotransbill0002');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-allo-trans-check">
					<button type="button" class="btn btn-default" onclick="auditOrder('#formpssallotransbill0002');">审核</button>
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
										<span class="txt"><label class="pssTabRed">*</label>单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
									</div>
									<div class="right">
										<span class="txt"><label class="pssTabRed">*</label>单据编号：</span>
										<input class="items-btn" type="text" id="top-alloTransNo" name="top-alloTransNo" value="${dataMap.alloTransNo} "/>
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
								<form method="post" enctype="multipart/form-data" id="formpssallotransbill0002" theme="simple" name="operform" action="${webPath}/pssAlloTransBill/saveOrderAjax">
									<dhcc:bootstarpTag property="formpssallotransbill0002" mode="query" />
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