var MfBusGpsReg_CarInsert = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//选择客户押品回调设置押品相关字段。
	var _setCollateralData=function(data){
		var pledgeNo = data.pledgeNo;
		//1 押品关联业务
		jQuery.ajax({
			url :webPath+"/pledgeBaseInfo/getPledgeBaseInfoAjax",
			data : {pledgeNo:pledgeNo},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				$("#MfBusGpsRegForm input[name=gpsBrand]").val(data.pledgeBaseInfo.pleMediaBrand);
				$("#MfBusGpsRegForm input[name=gpsType]").val(data.pledgeBaseInfo.pleMediaModel);
				$("#MfBusGpsRegForm input[name=pledgeName]").val(data.pledgeBaseInfo.pledgeName);
				$("#MfBusGpsRegForm input[name=pledgeShowNo]").val(data.pledgeBaseInfo.pledgeShowNo);
				$("#MfBusGpsRegForm input[name=ext1]").val(data.pledgeBaseInfo.pledgeNo);
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//GPS信息登记
	var _saveMfBusGpsRegInfo = function (obj){
	var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url: url,
                data: {ajaxData: dataParam},
                type: "POST",
                dataType: "json",
                success: function (data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        top.flag = true;
                        top.addFlag = true;
                        top.gpsListInfo = data.gpsListInfo;
                        top.gpsDetailInfo = data.htmlStr;
                        top.htmlStrFlag = true;
                        top.htmlString = data.htmlStr;
                        top.tableName = "mf_bus_gps_reg";
                    }
                    myclose_click();
                    if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }
            });
        }
	};
	
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		selectCollateralDataDialog(_setCollateralData,"",appId,pledgeMethod);
	};
	return {
		init : _init,
		setCollateralData : _setCollateralData,
		saveMfBusGpsRegInfo : _saveMfBusGpsRegInfo,
		selectCollateralData : _selectCollateralData
	};
}(window, jQuery);