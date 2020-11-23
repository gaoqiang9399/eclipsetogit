;
var MfAssetHandleInfoInsert=function(window,$){
	var _init=function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//已处置，展示处置详情
		if(handleFlag=="4"){
			$("#assetHandleTitle").show();
			$("#addAssetHandle").hide();
			$("#detail-form").show();
			$("#follow-form").hide();
		}else if(handleFlag=="3"){//待处置，展示添加资产处置信息按钮
			$("#assetHandleTitle").show();
		}else if(handleFlag=="0"||handleFlag=="1"){//新增或引用时即未提交审批，展示提交审批按钮
			$("#submitApprove").show();
		}
	};
	var _addAssetHandle=function(){
		$.ajax({
			url:webPath+"/mfAssetHandleInfo/getAssetHandleFornHtmlAjax",
			data:{protectId:protectId,assetHandleType:handleType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				if(data.flag=="success"){
					$("#MfAssetHandleInfoForm").html(data.formHtml);
				}
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
		$(".scroll-content").mCustomScrollbar("scrollTo", "#suitFollowDiv");
		$("#follow-form").css("display","block");
		var infoCnt = $("#follow-info").find(".message").length;
		if(infoCnt=="1"){
			$("#follow-info .message").remove();
		}
	};
	//资产处置变化时，切换不同表单
	var _handleTypeChange=function(){
		var handleType=$("[name=handleType]").val();
		$.ajax({
			url:webPath+"/mfAssetHandleInfo/getAssetHandleFornHtmlAjax",
			data:{protectId:protectId,assetHandleType:handleType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				if(data.flag=="success"){
					$("#MfAssetHandleInfoForm").html(data.formHtml);
				}
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//保存资产处置信息
	var _saveAssetHandleAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/mfAssetHandleInfo/insertAjax";
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						$("#assetHandleDetailInfoForm").html(data.formDetailHtml);
						$("#follow-form").hide();
						$("#detail-form").show();
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
	var _cancelAssetHandle=function(){
		$("#follow-form").hide();
	};
	return{
		init:_init,
		saveAssetHandleAjax:_saveAssetHandleAjax,
		handleTypeChange:_handleTypeChange,
		addAssetHandle:_addAssetHandle,
		cancelAssetHandle:_cancelAssetHandle
	};
}(window,jQuery);