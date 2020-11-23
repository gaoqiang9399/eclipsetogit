;
var MfMoveableCompensation=function(window,$){
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
		$("select[name=compensateType]").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : true//多选
		});
	};
	//保存方法
	var _ajaxCompensationSave = function(formObj){
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
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
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
	return{
		init:_init,
		ajaxCompensationSave:_ajaxCompensationSave,
		getPledgeData:_getPledgeData
	};
}(window,jQuery);