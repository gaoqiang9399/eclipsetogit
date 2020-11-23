;
var EvalInfo_inputForFlow=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_getPledgeData();
	};
	//选择客户其他押品信息，初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=collateralId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:false,//单选
						items:data.items,
						changeCallback:function(obj,elem){
							_selectPledgeCallback(obj.val());
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
	//选择押品后回调函数
	var _selectPledgeCallback=function(collateralId){
		jQuery.ajax({
			url : webPath+"/evalInfo/getPledgeEvalHtmlAjax",
			type : "POST",
			dataType : "json",
			data:{collateralId:collateralId,appId:appId},
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#evalInfoInsert").html(data.htmlStr);
					_getPledgeData();
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//保存担保评估信息
	var _insertEvalInfoForBuss=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						myclose_click();
						window.top.alert(data.msg, 1);
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	return{
		init:_init,
		insertEvalInfoForBuss:_insertEvalInfoForBuss
	};
}(window,jQuery);