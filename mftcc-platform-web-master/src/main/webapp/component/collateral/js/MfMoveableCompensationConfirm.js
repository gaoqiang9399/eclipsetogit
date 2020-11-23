;
var MfMoveableCompensationConfirm=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("input[name=marginAmount]").parent().parent().parent().hide();
		$("input[name=goodsAmount]").parent().parent().parent().hide();
		$("select[name=compensateType]").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : true,//多选
			changeCallback:_test
		});
	};
	var _test=function(){
		var compensateType= $("input[name=compensateType]").val();
		$("input[name=marginAmount]").parent().parent().parent().hide();
		$("input[name=goodsAmount]").parent().parent().parent().hide();
		$("#goodsDetailListdiv").hide();
		if(compensateType!=""){
			if(compensateType.indexOf("2")!=-1){
				$("input[name=marginAmount]").parent().parent().parent().show();
				if(compensateType.indexOf("1")==-1){
					$("input[name=goodsAmount]").val('0.00');
					$("#goodsDetailList").html("");
					_deleteConfirm();
				}
				
			}
			if(compensateType.indexOf("1")!=-1){
				$("input[name=goodsAmount]").parent().parent().parent().show();
				$("#goodsDetailListdiv").show();
				if(compensateType.indexOf("2")==-1){
					$("input[name=marginAmount]").val('');
				}
			}
			
		}else{
			$("input[name=compensateTotalValue]").val('0.00');
			$("input[name=goodsAmount]").val('0.00');
			$("input[name=marginAmount]").val('0.00');
			$("#goodsDetailList").html("");
			_deleteConfirm();
		}
		_getCompensateTotalValue();
		
	}
	var _deleteConfirm =function (){
		var pledgeNo=$("input[name=pledgeNo]").val();
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBillHis/deleteConfirmAjax?pledgeNo="+pledgeNo+"&modifyId=confirm",
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
				}
			},
			error : function(data) {
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	
	}
	//保存方法
	var _ajaxCompensationConfirmSave = function(formObj){
		var compensatePrice = $("input[name=compensatePrice]").val();
		var compensateTotalValue = $("input[name=compensateTotalValue]").val();
		if(compensatePrice>compensateTotalValue){
			window.top.alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":"补偿总价值","timeTwo":"需补偿价值"}), 0);
			return;
		}
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId,
					busPleId:busPleId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	//移库申请新增
	var _modifyApplyInput=function(){
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/input?busPleId='+busCollateralId+"&appId="+appId,"移库",function(){
		});
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=pledgeNo]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:false,//单选
						items:data.items,
						changeCallback:function(obj,elem){
							$("input[name=pledgeName]").val(obj.data('text'));
							var pledgeNo=$("input[name=pledgeNo]").val();
							_chosePledge(pledgeNo);
						}
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _chosePledge=function(pledgeNo){
		$("input[name=pledgeShowNo]").val('');
		$("input[name=pledgeWorth]").val('');
		$("input[name=regulatoryPrice]").val('');
		$("input[name=compensatePrice]").val('');
		$("input[name=unitPrice]").val('100');
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfo/getPledgeBaseInfoAjax?pledgeNo="+pledgeNo,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					if(data.pledgeBaseInfo!=""){
						var pledgeBaseInfo= data.pledgeBaseInfo;
						$("input[name=pledgeShowNo]").val(pledgeBaseInfo.pledgeShowNo);
						$("input[name=pledgeWorth]").val(pledgeBaseInfo.extNum01);
						$("input[name=regulatoryPrice]").val(pledgeBaseInfo.extNum06);
						$("input[name=compensatePrice]").val(pledgeBaseInfo.extNum07);
						$("input[name=unitPrice]").val('100');
					}
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	var _pledgeBillInput =function (){
		var pledgeNo=$("input[name=pledgeNo]").val();
		if(pledgeNo!=""){
			dialog({
				id:"pledgeBillDialog",
	    		title:'货物详情',
	    		url: webPath+'/mfMoveableCompensationConfirm/billInfoinput?pledgeNo='+pledgeNo+"&appId="+appId,
	    		width:1000,
	    		height:500,
	    		backdropOpacity:0,
	    		onshow:function(){
	    			this.returnValue = null;
	    		},onclose:function(){
	    			if(this.returnValue){
	    				//返回对象的属性:实体类MfCusCustomer中的所有属性
						var data = this.returnValue;
						if(data != undefined){
							$("#goodsDetailList").html(data.htmlStr);
							$("input[name=goodsAmount]").val(data.goodsValues);
							_getCompensateTotalValue();
						}
	    			}
	    		}
	    	}).showModal();
		}else{
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","押品名称"), 0);
		}
		
	}
	var _savePledgeBill=function saveParmDic(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {
						parent.dialog.get('pledgeBillDialog').close(data);
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				},
				complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	}
	var _fileExport =function (){
		var pledgeNo=$("input[name=pledgeNo]").val();
		if(pledgeNo!=""){
			dialog({
				id:"pledgeBillDialog",
	    		title:'货物详情',
	    		url: webPath+'/mfMoveableCompensationConfirm/export?pledgeNo='+pledgeNo+"&appId="+appId,
	    		width:850,
	    		height:500,
	    		backdropOpacity:0,
	    		onshow:function(){
	    			this.returnValue = null;
	    		},onclose:function(){
	    			if(this.returnValue){
	    				//返回对象的属性:实体类MfCusCustomer中的所有属性
						var data = this.returnValue;
						if(data != undefined){
							if(data.flag="success"){
								$("#goodsDetailList").html(data.htmlStr);
								$("input[name=goodsAmount]").val(data.goodsValues);
								_getCompensateTotalValue();
							}else{
								window.top.alert(data.msg, 0);
							}
							
						}
	    			}
	    		}
	    	}).showModal();
		}else{
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","押品名称"), 0);
		}
		
	}
	var _getCompensateTotalValue=function (){
		var goodsAmount =	$("input[name=goodsAmount]").val();
		var marginAmount =	$("input[name=marginAmount]").val();
		var compensateTotalValue=0;
		if(goodsAmount==''){
			goodsAmount=0;
		}else{
			compensateTotalValue=Number(compensateTotalValue)+Number(goodsAmount.replace(',',''));
		}
		if(marginAmount==''){
			marginAmount=0;
		}else{
			compensateTotalValue=Number(compensateTotalValue)+Number(marginAmount.replace(',',''));
		}
		$("input[name=compensateTotalValue]").val(compensateTotalValue);
	}
	return{
		init:_init,
		ajaxCompensationConfirmSave:_ajaxCompensationConfirmSave,
		getPledgeData:_getPledgeData,
		pledgeBillInput:_pledgeBillInput,
		savePledgeBill:_savePledgeBill,
		fileExport:_fileExport,
		getCompensateTotalValue:_getCompensateTotalValue
	};
}(window,jQuery);