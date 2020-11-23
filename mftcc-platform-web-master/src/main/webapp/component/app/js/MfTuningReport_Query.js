;
var MfTuningReport_Query = function(window, $) {


	var _saveOnlyAjax = function(obj) {

		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			alert(top.getMessage("CONFIRM_OPERATION","保存"),2,function(){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				$.ajax({
					url : url,
					data: {ajaxData: dataParam,appId:appId},
					async : false,
					success : function(data) {
						if (data.flag == "success") {
							window.top.alert(data.msg,1);
							myclose_click();
						}else{
							window.top.alert(data.msg, 0);
						}
					},error : function() {
						alert(top.getMessage("ERROR_SERVER"),0);
					}
				});
			});
		}
	};

	var _insertTuningReportAjax = function(obj) {

		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			alert(top.getMessage("CONFIRM_OPERATION","保存"),2,function(){
                var datas = [];
				if(busModel=='12'&&entranceType=='2'){
                    $("#busfee-list").find("tbody tr")
                        .each(
                            function(index) {
                                var entity = {};
                                $thisTr = $(this);
                                entity.id = $thisTr.find("input[name=id]").val();
                                entity.itemNo = $thisTr.find("input[name=itemNo]")
                                    .val();
                                entity.feeType = $thisTr.find(
                                    "select[name=feeType]").val();
                                entity.takeType = $thisTr.find(
                                    "select[name=takeType]").val();
                                entity.rateScale = $thisTr.find(
                                    "input[name=rateScale]").val().replace(
                                    /,/g, "");
                                if(busModel=='12'){
                                    entity.receivableFeeAmt = $thisTr.find(
                                        "input[name=receivableFeeAmt]").val().replace(
                                        /,/g, "");
                                }
                                datas.push(entity);
                            });
				}
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
                        ajaxDataList : JSON.stringify(datas),
						appId : appId,
						channelType : channelType,temporaryStorage:'1'
					},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 1);
							myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			});
		}
	};


	return {
		saveOnlyAjax:_saveOnlyAjax,
		insertTuningReportAjax:_insertTuningReportAjax

	};
}(window, jQuery);
