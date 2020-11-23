<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>库存预警</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/stock/js/PssStock_common.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
				ajaxData =JSON.parse(ajaxData);
			var pssConfig={
				numDecimalDigit:0,
			};
		    var dataMap = '${dataMap}';
		    var goodsId='${dataMap.goodsId}';
			$(function() {
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pssStorageWarning/getListPageAjax?goodsId="+goodsId,//列表数据查询的url
			    	tableId:"tablestorewarn0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:5,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options, data){
			    		Pss.addRowOperateEvent("sequence","aaa",["storehouseName"]);
			    		//var moneyThs = new Array("unitPrice", "taxUnitPrice", "discountAmount", "amount", "taxAmount", "totalPriceWithTax");
			    		//addMoneyEvent(moneyThs, 2);
			    		//var doubleThs = new Array("discountRate", "taxRate");
			    		//addDoubleEvent(doubleThs, 6);
			    		//var stringThs = new Array("goodsId", "unitId", "storehouseId", "memo");
			    		//var stringThs = new Array("memo");//"outStorehouseId"
			    		//addStringEvent(stringThs);
			    		//var stringThs = new Array("storehouseId");//"outStorehouseId"
			    		Pss.addStorehouseEvent("storehouseName", "storehouseId");
			    		var maxAmt = new Array("maxAmt");
			    		Pss.addQuantityEvent(maxAmt, pssConfig);
			    		var minAmt = new Array("minAmt");
			    		Pss.addQuantityEvent(minAmt, pssConfig);
			    		
			    		//PssStockCommon.addSelectGoodsEvent("goodsId");
			    		
			    		//PssStockCommon.addSelectStoresEvent(new Array("storehouseId"));
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    $("#goodsId").val(goodsId);
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
			function saveOrder() {

					//隐藏域中值暂时无法获取到
					var jsonArr = new Array();
					$(".table_content table tbody tr").each(function(trIndex, tr) {
						var jsonObj = new Object();
						$(".table_content table thead th").each(function(thIndex, th) {
							var $td = $(tr).children("td:eq(" + thIndex + ")");
							var tdVal = $td.text();
							if(/^(-)?((\d){1,3})(([,](\d){3})+)([.](\d)+)?$/.test(tdVal)){
								tdVal = tdVal.replace(/,/g,"");
							}
							//获取隐藏域中值
							if($td.find("input").length != 0){
								tdVal = $td.find("input").val();
							}
							jsonObj[$(th).attr("name")] = tdVal;
						});
						jsonArr.push(jsonObj);
					});

					LoadingAnimate.start();
					jQuery.ajax({
						url : webPath+"/pssStorageWarning/saveWarnAjax?goodsId="+goodsId,
						data : {
							"jsonArr" : JSON.stringify(jsonArr)
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "库存预警"), 1);
								window.location.href = webPath+"/pssStorageWarning/getListPage?goodsId="+goodsId;
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
						<button type="button" class="btn btn-primary" onclick="saveOrder();">保存</button>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
			<div id="content" class="table_content" style="height:auto; width:auto;">
			</div>
			<input type="hidden" id="goodsId">
	</body>
</html>