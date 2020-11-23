;
var MfAssetsManage_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
	};	
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	// 选择客户名称后回调
	var _callBack = function(cusInfo){
		$("input[name='cusNo']").val(cusInfo.cusNo);
		$("input[name='cusName']").val(cusInfo.cusName);
		jQuery.ajax({
			url :webPath + "/pledgeBaseInfo/getListByCusNoAndAssetPropertyAjax",
			data : {cusNo:cusInfo.cusNo,assetProperty:1},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					var popspledgeNo = $("input[name=popspledgeNo]").val();
					if(typeof(popspledgeNo) != "undefined"){
						$("[name=popspledgeNo]").popupSelection("updateItems",data.pledgeBaseInfoMap);
						return;
					}
					$("input[name=pledgeNo]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//下拉模式
						multiple:false,//多选
						items:data.pledgeBaseInfoMap,
						addBtn:{//添加扩展按钮
							"title":"新增资产",
							"fun":function(hiddenInput,elem){
								//隐藏选择区域
								$(elem).popupSelection("hideSelect", elem);
								_addAsset(function(pledge){
									$("input[name=pledgeNo]").parent(".input-group").find(".pops-value").text(pledge.pledgeName);
									$("input[name=pledgeNo]").val(pledge.pledgeNo);
									$("input[name=pledgeName]").val(pledge.pledgeName);
									$("input[name=popspledgeNo]").popupSelection("addItem",{"id":pledge.pledgeNo,"name":pledge.pledgeName});
								});
							}
						},
						changeCallback : function (obj, elem) {
							$("input[name=pledgeNo]").val(obj.val());
							$("input[name=pledgeName]").val($("input[name=pledgeNo]").parent(".input-group").find(".pops-value").text());
						}
					});
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	//新增押品
	var _addAsset=function(callback){
		var cusNo = $("input[name=cusNo]").val();
		dialog({
			id:"addAssetDialog",
			title:'新增资产',
			url: webPath+'/pledgeBaseInfo/inputInsertByCusNo?cusNo=' + cusNo + '&ifPrivateAssets=1',
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					callback(this.returnValue);
				}
			}
		}).showModal();
	};
	return {
		init : _init,
		ajaxSave : _ajaxSave,
		callBack : _callBack
	};
}(window, jQuery);
