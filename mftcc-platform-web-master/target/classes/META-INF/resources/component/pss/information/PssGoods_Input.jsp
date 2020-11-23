<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript">
		var ajaxData = JSON.parse('${ajaxData}');
		var aloneFlag = true;
		var dataDocParm={
				relNo:ajaxData.goodsId,
				docType:'goodsPic',
				docTypeName:"",
				docSplitName:"商品图片",
				query:''
			};
		$(function(){
				myCustomScrollbarForForm({
				obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				}); 
				$("input[name=batchQgp]").parent().parent().parent().hide();
				$("input[name=batchBeforQgp]").parent().parent().parent().hide();
				$("input[name=goodsSn]").parent().parent().parent().hide();
				$("input[name=unitId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//弹窗模式
						multiple:false,//单选
						labelShow : false,//已选项显示
						items:ajaxData.goodsUnitArray,
						addBtn:{//添加扩展按钮
						"title":"新增",
						"fun":function(d,elem){
							top.goodsUnitArray = ajaxData.goodsUnitArray;
							top.window.createShowDialog(webPath+'/pssUnit/getInputPage?unitType=1','新增单位', '500px', '600px',function(){
								if(top.unitId!=""&&top.unitName!=""&&top.unitId!=undefined&&top.unitName!=undefined){
									var newNode = {"id":top.unitId,"name":top.unitName};
									goodsUnitArray = goodsUnitArray.concat(newNode);
									top.goodsUnitArray = goodsUnitArray;
									$(elem).popupSelection("addItem",newNode);
									//新增后选择该数据
									$(elem).popupSelection("selectedItem",newNode);
									top.roleNo="";
									top.roleName="";
								}
								top.roleArray="";
							});
						}
						}	
					});	
				$("input[name=goodsType]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//弹窗模式
						multiple:false,//单选
						labelShow : false,//已选项显示
						items:ajaxData.goodsTypeArray,
						addBtn:{//添加扩展按钮
						"title":"新增",
						"fun":function(hiddenInput, elem){
								$(elem).popupSelection("hideSelect", elem);
								BASE.openDialogForSelect('新增商品类型','PSS_GOODS_TYPE', elem);
							}
						}	
					});	
				$("input[name=storehouseId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//弹窗模式
						multiple:false,//单选
						labelShow : false,//已选项显示
						items:ajaxData.houseJsonArray,
						addBtn:{//添加扩展按钮
						"title":"新增",
						"fun":function(d,elem){
							top.houseJsonArray = ajaxData.houseJsonArray;
							top.popFlag="1";
							top.window.createShowDialog(webPath+'/pssStorehouse/getInputPage','新增仓库', '500px', '600px',function(){
								if(top.storehouseId!=""&&top.storehouseName!=""&&top.storehouseId!=undefined&&top.storehouseName!=undefined){
									var newNode = {"id":top.storehouseId,"name":top.storehouseName};
									houseJsonArray = houseJsonArray.concat(newNode);
									top.houseJsonArray = houseJsonArray;
									$(elem).popupSelection("addItem",newNode);
									//新增后选择该数据
									$(elem).popupSelection("selectedItem",newNode);
									top.roleNo="";
									top.roleName="";
								}
								top.roleArray="";
							});
						}
					}				
					});	
		});
		function batchWarning(){
			if($("input[name=batchWarningFlag]").get(0).checked){
				$("input[name=batchQgp]").parent().parent().parent().show();
				$("input[name=batchBeforQgp]").parent().parent().parent().show();
			}else{
				$("input[name=batchQgp]").parent().parent().parent().hide();
				$("input[name=batchBeforQgp]").parent().parent().parent().hide();
			}
		}
		function switchUnit(){
			if($("input[name=mutiUnitFlag]").is(':checked')){
				$("input[name=wholesalePrice]").parent().parent().parent().hide();
				$("input[name=retailPrice]").parent().parent().parent().hide();
				$("input[name=discountRate1").parent().parent().parent().hide();
				$("input[name=discountRate2]").parent().parent().parent().hide();
				$("input[name=vipPrice]").parent().parent().parent().hide();
				$("input[name=estimatedPurchasePrice]").parent().parent().parent().hide();
				$("input[name=unitId]").parent().parent().prev().children(".control-label ").attr("title","多单位名称");
				$("input[name=unitId]").parent().parent().prev().children(".control-label ").text("多单位名称");
				addPop("2");
				$("input[name=unitId]").val("");
				$("input[name=unitId]").prev().text("");
			}else{
				$("input[name=wholesalePrice]").parent().parent().parent().show();
				$("input[name=retailPrice]").parent().parent().parent().show();
				$("input[name=discountRate1").parent().parent().parent().show();
				$("input[name=discountRate2]").parent().parent().parent().show();
				$("input[name=vipPrice]").parent().parent().parent().show();
				$("input[name=estimatedPurchasePrice]").parent().parent().parent().show();
				$("input[name=unitId]").parent().parent().prev().children(".control-label ").attr("title","计量单位");
				$("input[name=unitId]").parent().parent().prev().children(".control-label ").text("计量单位");
				addPop("1");
				$("input[name=unitId]").val("");
				$("input[name=unitId]").prev().text("");
			}
		}
		function changeSn(){
			if($("input[name=snFlag]").get(0).checked){
				$("input[name=goodsSn]").parent().parent().parent().show();
			}else{
				$("input[name=goodsSn]").parent().parent().parent().hide();
			}
		}
		function changeWarn(){
			if($("input[name=stockWarningFlag]").get(0).checked){
				$("input[name=sepStockFlag]").parent().parent().parent().show();
				$("input[name=maxStock]").parent().parent().parent().show();
				$("input[name=minStock]").parent().parent().parent().show();
			}else{
				$("input[name=sepStockFlag]").parent().parent().parent().hide();
				$("input[name=maxStock]").parent().parent().parent().hide();
				$("input[name=minStock]").parent().parent().parent().hide();
			}
		}
		function changeSepFlag(){
			if($("input[name=sepStockFlag]").get(0).checked){
				$("input[name=maxStock]").parent().parent().parent().hide();
				$("input[name=minStock]").parent().parent().parent().hide();
				$("#sepStockTb").show();
			}else{
				$("input[name=maxStock]").parent().parent().parent().show();
				$("input[name=minStock]").parent().parent().parent().show();
				$("#sepStockTb").hide();
			}
		}
		function addPop(type){
			var result; 
				if(type=="1"){
					result=ajaxData.goodsUnitArray;
				}else if(type=="2"){
					result=ajaxData.goodsMutiUnitArray;
				}
			$("input[name=popsunitId]").nextAll().remove();
				$("input[name=popsunitId]").attr("name","unitId");
				$("input[name=unitId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//弹窗模式
						multiple:false,//单选
						labelShow : false,//已选项显示
						items:result,
						changeCallback : function (obj, elem) {
							if(type=="2"){
							var relId = $("input[name=unitId]").val();
			    			myCustomScrollbar({
					    	obj : "#content",//页面内容绑定的id
					    	url : webPath+"/pssUnit/getMutiByRelIdAjax",
					    	tableId : "tablepssugr01",//列表数据查询的table编号
					    	tableType : "thirdTableTag",//table所需解析标签的种类
					    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					    	data : {"relId" : relId},
					    	/* ownHeight : true, */
					    	callback : function(options, data) {
					    		var moneyThs = new Array("retailPrice", "wholesalePrice", "vipPrice", "estimatedPurchasePrice");
	    						Pss.addMoneyEvent(moneyThs, ajaxData.pssConfig,calculateUnitPrice);
					    		//数字列事件
						    	var doubleThs = new Array("discountRate1", "discountRate2");
						    	Pss.addDoubleEvent(doubleThs, ajaxData.pssConfig,copyRate);
						    	$('.footer_loader').remove();
	   	 						$('.table-float-head').remove();
	   	 					/* 	$('#content').height(260); */
					    	}
					   		 });
							$('.pss_detail_list').css('height', 'auto');
							$('#mCSB_2').css('height', 'auto'); 
							}
						},
						addBtn:{//添加扩展按钮
						"title":"新增",
						"fun":function(d,elem){
							top.goodsUnitArray = ajaxData.goodsUnitArray;
							top.window.createShowDialog(webPath+'/pssUnit/getInputPage?unitType='+type,'新增多单位', '500px', '600px',function(){
								if(top.unitId!=""&&top.unitName!=""&&top.unitId!=undefined&&top.unitName!=undefined){
									var newNode = {"id":top.unitId,"name":top.unitName};
									houseJsonArray = houseJsonArray.concat(newNode);
									top.houseJsonArray = houseJsonArray;
									$(elem).popupSelection("addItem",newNode);
									//新增后选择该数据
									$(elem).popupSelection("selectedItem",newNode);
									top.roleNo="";
									top.roleName="";
								}
								top.roleArray="";
							});
						}
					   
					}	
				});	
		}
		function insertGoods(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				var units = JSON.stringify(getTableData());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						unitData: units
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							alert(top.getMessage("SUCCEED_OPERATION"),1);
							top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
								top.updateFlag = true;
								top.tableName = "ins_info";
							}
							myclose_click();
							//window.close();
							//myclose_showDialogClick();
							//if (callback && typeof (callback) == "function") {
							//	callback.call(this, data);
							//}
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		};
		function addUnitGoodsRel(){
			
		}
		function getTableData(){
		var pssUgrList = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr){
			var ugr = new Object();
			var flag = '0';
			$("#tablist>thead th").each(function(thIndex, th) {
				
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var colName = $(th).attr("name");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					ugr[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					ugr[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					ugr[$(th).attr("name")] = tdText;
				}
			});
				pssUgrList.push(ugr);
		});
		
		return pssUgrList;
	};
	function calculateUnitPrice(obj, th, val, pssConfig){
		//var idx = obj.parent().children("tr").index(obj);
		var idx=obj.index();
		if(idx==0){
		var thIndex = $("#tablist>thead th[name='" + th + "']").index();
		$("#tablist>tbody tr").each(function(trIndex, tr){
			if(trIndex==0){
				return true;
			}else{
				var relNum = $(tr).find("input[name=relNum]").val();
				$(tr).children("td:eq(" + thIndex + ")").text(parseFloat(val*relNum).toFixed(pssConfig.amtDecimalDigit));
			}
		});
		}
	}
	function copyRate(obj, th, val, pssConfig){
		var idx=obj.index();
		if(idx==0){
		var thIndex = $("#tablist>thead th[name='" + th + "']").index();
		$("#tablist>tbody tr").each(function(trIndex, tr){
			if(trIndex==0){
				return true;
			}else{
				$(tr).children("td:eq(" + thIndex + ")").text(parseFloat(val).toFixed(pssConfig.amtDecimalDigit));
			}
		});
		}
	}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">	
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="pssGoods" theme="simple" name="operform" action="${webPath}/pssGoods/insertAjax">
								<dhcc:bootstarpTag property="formpssgoods0002" mode="query" />
							</form>
						</div>
						<div id="content" class="table_content pss_detail_list" style="padding-left:inherit;padding-right:inherit;">
						</div>
						<br>
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
							</div>
						</div>	
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:pmsTag pmsId="pss-goods-insert">
					<dhcc:thirdButton value="保存" action="保存" onclick="insertGoods('#pssGoods')"></dhcc:thirdButton>
				</dhcc:pmsTag>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
			</div>
		</div> 
	</body>
</html>