;
var mfRequestPayoutDetailInsert=function(window,$){
	var _init=function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		/*//合同组件
		$("input[name=pactId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxOverduePactData.overduePact,
			changeCallback : function (obj, elem) {
				_appNameChange(obj);
			}
		});
		//客户新组件
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
		});
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-select").remove();*/
	};
	//保存请款明细信息
	var _saveRequestPayoutDetailAjax=function(obj){
		var appFlag = submitJsMethod($(obj).get(0), '');
		if (appFlag) {
            var url = $(obj).attr("action");
            var appDataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
                    ajaxData : appDataParam,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
                        top.fundsFlag=true;
						top.detailTableHtml = data.detailTableHtml;
                        // 刷新总金额
                        top.payoutTotalAmount = data.payoutTotalAmount;
                        myclose_click();
					}else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	return{
		init:_init,
        saveRequestPayoutDetailAjax:_saveRequestPayoutDetailAjax,
	};
}(window,jQuery);