;
var CollateralInsert = function(window, $){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//var option = $("select[name=classNo]").find("option");
		//makeOptionsJQ(option, classNo, pledgeMethod);
		//添加押品放大镜
		$("input[name=pledgeName]").after('<span class="input-group-addon">'+
				'<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
		$("input[name=cusName]").attr("readOnly","readOnly").removeAttr("onclick");
		$("input[name=certificateName]").attr("readOnly","readOnly").removeAttr("onclick");
	};
	var _insertCollateralBase=function(obj,collateralType){
		var flag = submitJsMethod($(obj).get(0), '');
		if(typeof(collateralType)=='undefined'){
			//var collateralType = 'pledge';
            collateralType = 'pledge';
		}
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId,
					entrFlag:entrFlag,
					isQuote:isQuote,
					collateralType:collateralType
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				asyns:false,
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(top.collaFlag!=undefined){
							top.collaFlag=true;
						}
						if(top.addCollateral!=undefined){
							top.addCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId=data.classId;
							top.vouType=data.pledgeMethod;
						}
						if(top.addCreditCollaFlag!=undefined){
							top.addCreditCollaFlag=true;
							top.creditAppId=appId;
						}
						window.top.alert(data.msg,3);
						//刷新回显
//					    var pleInfoThis = new Object();
//					    pleInfoThis.pledgeName = data.pledgeName;
//					    pleInfoThis.envalueAmt = data.envalueAmt;
//					    pleInfoThis.receiptsAmount = data.receiptsAmount;
//						top.pleInfo = pleInfoThis;
//						top.pleFlag = true;
						
						var url = webPath+'/mfBusApply/getSummary?appId='+data.appId+'&busEntrance=1';
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						
						
						top.flag = true;
					    myclose_click();
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
	var _replaceCollateral=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					collateralId_old:collateralId_old,
					appId:appId,
					entrFlag:entrFlag,
					isQuote:isQuote
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(top.repCollateral!=undefined){
							top.repCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId =data.classId;
						}
						alert(top.getMessage("SUCCEED_REPLACE"), 1);
						myclose_click();
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
	//选择押品类型
	var _selectCollateralTypeData=function(_setCollateralType){
		var vouType = $("input[name=vouType]").val();
		if(vouType==undefined){//如果取不到，就查询全部押品类别
			vouType = "";
		}
		if(vouType=="5"){
			vouType ="4";
		}
		selectCollateralTypeDataDialog(_setCollateralType, vouType);
	};
	//选择押品类型回调设置表单中押品类型相关字段
	var _setCollateralType=function(data){
		if(entrance=="credit"){//如果是授信登记押品，担保方式隐藏
			$("input[name=pledgeMethod]").val(data.vouType);
		}
		$("input[name=classId]").val(data.classId);
		$("input[name=collateralTypeName]").val(data.classSecondName);
	};
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		var pledgeMethod = $("input[name=pledgeMethod]").val();
		selectCollateralDataDialog(_setCollateralData,cusNo,appId,pledgeMethod);
	};
	//选择客户押品回调设置押品相关字段。
	var _setCollateralData=function(data){
		var pledgeNo = data.pledgeNo;
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/getAddPledgeBaseHtmlAjax",
			data : {pledgeNo:pledgeNo},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#collateralBaseInsert").find("table").remove();
					$("#collateralBaseInsert").find(".hidden-content").remove();
					$("#collateralBaseInsert").append(data.htmlStr);
					isQuote="1";
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//发票面额变化
	var _invoiceAmtChange=function(){
		//发票面额
		var extNum02=$("input[name=extNum02]").val();
		//应收账款净值
		$("input[name=extNum01]").val(extNum02);
	};
	//应收账款净值变化
	var _receAmtChange=function(){
		//发票面额
		var extNum02=$("input[name=extNum02]").val();
		//应收账款净值
		var extNum01=$("input[name=extNum01]").val();
		extNum01= extNum01.replace(/,/g,"");
		extNum02= extNum02.replace(/,/g,"");
		if(parseFloat(extNum02)<parseFloat(extNum01)){
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=extNum01]").attr("title"),"timeTwo":$("input[name=extNum02]").attr("title")},3));
			$("input[name=extNum01]").val(extNum02);
		}
	};
	return {
		init:_init,
		insertCollateralBase:_insertCollateralBase,
		replaceCollateral:_replaceCollateral,
		selectCollateralTypeData:_selectCollateralTypeData,
		setCollateralType:_setCollateralType,
		selectCollateralData:_selectCollateralData,
		setCollateralData:_setCollateralData,
		invoiceAmtChange:_invoiceAmtChange,
		receAmtChange:_receAmtChange,
	};
}(window, jQuery);