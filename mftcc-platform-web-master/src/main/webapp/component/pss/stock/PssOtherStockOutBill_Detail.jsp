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
		<link rel="stylesheet" href='${webPath}/component/pss/stock/css/PssStock_common.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    //var otherOutTypes = '${dataMap.otherOutTypes}';
		    var disableGoodsMap = '${dataMap.disableGoodsMap }';
		    if (disableGoodsMap != '') {
		    	Pss.disableGoodsMap = JSON.parse(disableGoodsMap);
		    }
		    var disableStorehouseMap = '${dataMap.disableStorehouseMap }';
		    if (disableStorehouseMap != '') {
		    	Pss.disableStorehouseMap = JSON.parse(disableStorehouseMap);
		    }
		    
			$(function() {
				//初始赋值
				var billDateVal = $("#formpssotherstockoutbill0002 input[name='billDate']").val();
				$("#top-billDate").val(billDateVal);

				var otherStockOutNoVal = $("#formpssotherstockoutbill0002 input[name='otherStockOutNo']").val();
				$("#top-otherStockOutNo").val(otherStockOutNoVal);
				
				var cusVal = $("#formpssotherstockoutbill0002 input[name='cusNo']").val();
				$("#cus").val(cusVal);
				
				//单据类型放下面赋初值
				var otherOutTypeVal = $("#formpssotherstockoutbill0002 input[name='otherOutType']").val();
				$("#otherOutType").val(otherOutTypeVal);
				
				//客户新组件
				$("input[name=cus]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.cus,
 					changeCallback : function (obj, elem) {
						$("input[name=cusName]").val(obj.data("text"));
					}
				});
				
				//初始化业务类别
				/* if(otherOutTypes){
					var pact = eval(otherOutTypes);
					var opt = $('#otherOutType')[0];
					opt.options.length=0;
					for(var i=0; i<pact.length; i++){
						opt.add(new Option(pact[i].optName, pact[i].optCode));
					}
				} */
				$("#otherOutType").popupSelection({
					searchOn:false,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.pooTypeArray,
					changeCallback : function (obj, elem) {
						
					}
				});
				var otherOutTypePopsDiv = $("#otherOutType").nextAll("div .pops-value").get(0);
				if(otherOutTypePopsDiv != undefined){
					var otherOutTypeLeft = $(otherOutTypePopsDiv).position().left;
					var otherOutTypeSelectDiv = $("#otherOutType").next("div .pops-select").get(0);
					$(otherOutTypePopsDiv).width(80);
					$(otherOutTypeSelectDiv).css({'left':otherOutTypeLeft});
					$(otherOutTypeSelectDiv).width(108); //selectDiv比PopsDiv长28px
					$(otherOutTypeSelectDiv).find("li").width(60);
				}
				
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssOtherStockOutDetailBill/getOtherStockOutDetailBillListAjax",//列表数据查询的url
			    	tableId:"tablepssotherstockoutdetailbill0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence", "goodsName", ["storehouseName"], "unitName");
			    		var stringThs = new Array("memo");
			    		Pss.addStringEvent(stringThs);
			    		var quantityThs = new Array("otherOutQty");
			    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig);
						
			    		Pss.addGoodsEvent("goodsName", "goodsId", PssStockCommon.afterCheckGoods);
			    		
			    		//单位列事件
			    		Pss.addUnitEvent("unitName", "unitId", "goodsId");
			    		
						Pss.addStorehouseEvent("storehouseName", "storehouseId", PssStockCommon.afterSelectStorehouse);
						
						//仓位
						Pss.addFreightSpaceEvent("freightSpaceNo", "freightSpaceId", "storehouseId");
						
						var tabHeadThs = new Array("goodsName", "storehouseName", "otherOutQty");
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
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#formpssotherstockoutbill0002').serializeArray());
			};
			
			//保存
			function saveOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，仓库必输，数量必输
				var tabHeadThs = new Array("goodsName", "storehouseName", "otherOutQty");
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
					var $billDate = $("#formpssotherstockoutbill0002 input[name='billDate']");
					$billDate.val(billDateVal);
					
					var otherStockOutNoVal = $("#top-otherStockOutNo").val();
					if(otherStockOutNoVal == null || otherStockOutNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $otherStockOutNo = $("#formpssotherstockoutbill0002 input[name='otherStockOutNo']");
					$otherStockOutNo.val(otherStockOutNoVal);
					
					var cusVal = $("#cus").val();
					var $cus = $("#formpssotherstockoutbill0002 input[name='cusNo']");
					$cus.val(cusVal);
					
					var otherOutTypeVal = $("#otherOutType").val();
					var $otherOutType = $("#formpssotherstockoutbill0002 input[name='otherOutType']");
					$otherOutType.val(otherOutTypeVal);
					
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
									window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "其他出库单"), 1);
									window.location.href = webPath+"/pssOtherStockOutBill/getById?otherStockOutId="+data.otherStockOutId;
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
				window.location.href = webPath+"/pssOtherStockOutBill/input";
			};
			
			//审核
			function auditOrder(obj) {
				LoadingAnimate.start();
				
				//校验：商品必输，仓库必输，数量必输
				var tabHeadThs = new Array("goodsName", "storehouseName", "otherOutQty");
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
					var $billDate = $("#formpssotherstockoutbill0002 input[name='billDate']");
					$billDate.val(billDateVal);
					
					var otherStockOutNoVal = $("#top-otherStockOutNo").val();
					if(otherStockOutNoVal == null || otherStockOutNoVal == ""){
						LoadingAnimate.stop();
						alert("单据编号不能为空！",1);
						return;
					}
					var $otherStockOutNo = $("#formpssotherstockoutbill0002 input[name='otherStockOutNo']");
					$otherStockOutNo.val(otherStockOutNoVal);
					
					var supplierVal = $("#supplier").val();
					var $supplier = $("#formpssotherstockoutbill0002 input[name='supplierId']");
					$supplier.val(supplierVal);
					
					var otherInTypeVal = $("#otherInType").val();
					var $otherInType = $("#formpssotherstockoutbill0002 input[name='otherInType']");
					$otherInType.val(otherInTypeVal);
					
					var url = webPath+"/pssOtherStockOutBill/auditOrderAjax";
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
									var otherStockOutId = $("#formpssotherstockoutbill0002 input[name='otherStockOutId']").val();
									window.location.href = webPath+"/pssOtherStockOutBill/getById?otherStockOutId="+otherStockOutId;
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
			
			//反审核
			function reAuditOrder(obj) {
				LoadingAnimate.start();
				var otherStockOutNoVal = $("#top-otherStockOutNo").val();
				if(otherStockOutNoVal == null || otherStockOutNoVal == ""){
					LoadingAnimate.stop();
					alert("单据编号不能为空！",1);
					return;
				}
				var $otherStockOutNo = $("#formpssotherstockoutbill0002 input[name='otherStockOutNo']");
				$otherStockOutNo.val(otherStockOutNoVal);
				
				var url = webPath+"/pssOtherStockOutBill/reAuditOrderAjax";
				var dataParm = JSON.stringify($(obj).serializeArray());
				
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParm
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
								window.top.alert("其他入库单反审核成功！", 1);
								var otherStockOutId = $("#formpssotherstockoutbill0002 input[name='otherStockOutId']").val();
								window.location.href = webPath+"/pssOtherStockOutBill/getById?otherStockOutId="+otherStockOutId;
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
			};
			
			var printBill = function(){
				
				window.top.alert("请确认是否打印其他出库单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
					var otherStockOutId = '${pssOtherStockOutBill.otherStockOutId}';
					$.ajax({
						url : webPath+"/pssPrintBill/getPssOtherStockOutBillAjax?otherStockOutId=" + otherStockOutId,
						data : {
							fileName : 'templateFile_pssqtckd.doc'
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							if(data.flag){
								var pssOtherStockOutBillObj = $.parseJSON(data.pssOtherStockOutBill);
								mfPageOffice.openPageOffice(pssOtherStockOutBillObj);
							}else{
								window.top.alert(data.msg, 0);
							}
						}
					});
				});
				
			};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-other-stock-out-insert">
					<button type="button" class="btn btn-primary" onclick="newInsert();">新增</button>
				</dhcc:pmsTag>
				<!-- 复制数据作为新增 --> 
				<!-- <button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">复制</button> -->
				<dhcc:pmsTag pmsId="pss-other-stock-out-print">
					<button type="button" class="btn btn-default" onclick="printBill();">打印</button> 
				</dhcc:pmsTag>
				<c:if test='${dataMap.auditSts eq "0"}'>
					<dhcc:pmsTag pmsId="pss-other-stock-out-insert">
						<button type="button" class="btn btn-default" onclick="saveOrder('#formpssotherstockoutbill0002');">保存</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pss-other-stock-out-check">
						<button type="button" class="btn btn-default" onclick="auditOrder('#formpssotherstockoutbill0002');">审核</button>
					</dhcc:pmsTag>
				</c:if>
				<dhcc:pmsTag pmsId="pss-other-stock-out-check">
					<c:if test='${dataMap.auditSts eq "1"}'>
						<button type="button" class="btn btn-default" onclick="reAuditOrder('#formpssotherstockoutbill0002');">反审核</button>
					</c:if>
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
										<span class="txt fl">客户：</span> 
										<input class="items-btn" id="cus" name="cus"/>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
										<span class="txt">业务类别：</span> 
										<!-- <select class="items-btn menu-btn" name="otherOutType" id="otherOutType" autocomplete="off"></select> -->
										<input class="items-btn" id="otherOutType" name="otherOutType" style="width:100px;"/>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-otherStockOutNo" name="top-otherStockOutNo" readonly value="${dataMap.otherStockOutNo} "/>
									</div>
								</div>
								<c:if test='${dataMap.auditSts eq "1"}'>
									<div id="auditTag" class="i i-warehouse pss-chapter-font">
										<div class="chapter-name-div">已审核</div>
									</div>
								</c:if>
							</div>
						</div>
						<!--页面显示区域-->
						<div class="pss-bigform-table">
							<div id="content" class="table_content pss_detail_list">
							</div>
						</div>
						<div class="pss-bigform-form">
							<div class="bootstarpTag">
								<form method="post" enctype="multipart/form-data" id="formpssotherstockoutbill0002" theme="simple" name="operform" action="${webPath}/pssOtherStockOutBill/updateOrderAjax">
									<dhcc:bootstarpTag property="formpssotherstockoutbill0002" mode="query" />
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