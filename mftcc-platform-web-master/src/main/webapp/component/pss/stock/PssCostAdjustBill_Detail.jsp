<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>成本调整单</title>
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
		    var disableGoodsMap = '${dataMap.disableGoodsMap }';
		    if (disableGoodsMap != '') {
		    	Pss.disableGoodsMap = JSON.parse(disableGoodsMap);
		    }
		    var disableStorehouseMap = '${dataMap.disableStorehouseMap }';
		    if (disableStorehouseMap != '') {
		    	Pss.disableStorehouseMap = JSON.parse(disableStorehouseMap);
		    }
		    
			$(function() {
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssCostAdjustDetailBill/getCostAdjustDetailBillListAjax",//列表数据查询的url
			    	tableId:"tablepsscostadjustdetailbill0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence", "goodsName", ["storehouseName"]);
			    		var moneyThs = new Array("adjustDetailAmt");
			    		Pss.addMoneyEvent(moneyThs, ajaxData.pssConfig);
			    		var stringThs = new Array("memo");
			    		Pss.addStringEvent(stringThs);
			    		var quantityThs = new Array("goodsQty");
			    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig);
			    		
			    		Pss.addGoodsEvent("goodsName", "goodsId", PssStockCommon.afterCheckGoods);
						Pss.addStorehouseEvent("storehouseName", "storehouseId");
						
						var tabHeadThs = new Array("goodsName", "storehouseName", "adjustDetailAmt");
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
				
				//初始赋值
				var billDateVal = $("#formpsscostadjustbill0002 input[name='billDate']").val();
				$("#top-billDate").val(billDateVal);

				var costAdjustNoVal = $("#formpsscostadjustbill0002 input[name='costAdjustNo']").val();
				$("#top-costAdjustNo").val(costAdjustNoVal);
				
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
									
			/* function storeChange(obj){
				obj.text($('#storeId option:selected').val());
			}; */
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#formpsscostadjustbill0002').serializeArray());
			};
			
			//保存
			function saveOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，仓库必输，调整金额必输
				var tabHeadThs = new Array("goodsName", "storehouseName", "adjustDetailAmt");
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
					var $billDate = $("#formpsscostadjustbill0002 input[name='billDate']");
					$billDate.val(billDateVal);

					var costAdjustNoVal = $("#top-costAdjustNo").val();
					if(costAdjustNoVal == null || costAdjustNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $costAdjustNo = $("#formpsscostadjustbill0002 input[name='costAdjustNo']");
					$costAdjustNo.val(costAdjustNoVal);

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
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "成本调整单"), 1);
									window.location.href = webPath+"/pssCostAdjustBill/getById?costAdjustId="+data.costAdjustId;
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
			
			//新增
			function newInsert(){
				window.location.href = webPath+"/pssCostAdjustBill/input";
			};
			
			//打印
			function printOrder(obj) {
				alert('建设中，敬请关注...',1);
			};
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-cost-adjust-insert">
					<button type="button" class="btn btn-primary" onclick="newInsert();">新增</button>
					<button type="button" class="btn btn-default" onclick="saveOrder('#formpsscostadjustbill0002');">保存</button>
				</dhcc:pmsTag>
				<!-- <button type="button" class="btn btn-default" onclick="printOrder('#formpsscostadjustbill0002');">打印</button> -->
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
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-costAdjustNo" name="top-costAdjustNo" readonly/>
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
								<form method="post" enctype="multipart/form-data" id="formpsscostadjustbill0002" theme="simple" name="operform" action="${webPath}/pssCostAdjustBill/updateOrderAjax">
									<dhcc:bootstarpTag property="formpsscostadjustbill0002" mode="query" />
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