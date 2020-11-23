;
var MfBusCompensatory = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});

	};
	 //打开发起代偿页面
	 var _getCompensatoryType = function(){
		 $.ajax({
				url : webPath+'/mfBusCompensatoryApply/getCompensatoryType',
				data : {
					"fincId" : fincId
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {
						if(data.appSts=="3"){//代偿审批完成,隐藏代称按钮,展示代偿确认按钮
							$("#compensatory").attr("style","display:none;");
							$("#compensatoryConfirm").attr("style","display:show();");
						}else if(data.appSts=="1" || data.appSts=="2"){
							$("#compensatory").attr("style","display:show();");
							$("#compensatoryConfirm").attr("style","display:none;");
							$("#compensatory").text("代偿中");
							$("#compensatory").attr('disabled',true);
							$("#compensatory").attr("class", "btn btn-opt-dont");
						}else if(data.appSts=="7"){//已经代偿申请过一次,不让其再进行代偿申请
                            $("#compensatory").attr('disabled',true);
                            $("#compensatory").attr("class", "btn btn-opt-dont");
						}else{
							$("#compensatoryConfirm").attr("style","display:none;");
							$("#compensatory").attr("style","display:show();");
						}
						compensatoryId = data.compensatoryId;
					} 
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
	 }

	//代偿历史列表信息
	//正常初始化
	var _pubMfBusCompensatoryList = function (){
		$.ajax({
			url:webPath+"/mfBusCompensatoryConfirm/getCompensatoryHistoryListAjax?fincId="+fincId+"&tableId="+compensatoryTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				if(html==''){
				}else{
					$('#mfCompensatoryHistoryList-block').removeClass('hidden');
					$("#mfCompensatoryHistoryList").empty().html(html);
				}
			}
		});
	};
	 var _getCompensatoryApply = function(){
		top.flag=false;
		top.window.openBigForm(webPath+"/mfBusCompensatoryApply/input?fincId="+fincId,'代偿申请',function(){
			_getCompensatoryType();
		});
	 }
	 
	 var _getCompensatoryConfirm = function(){
		top.flag=false;
		top.window.openBigForm(webPath+"/mfBusCompensatoryConfirm/input?fincId="+fincId+"&compensatoryId="+compensatoryId,'代偿确认',function(){
            _getCompensatoryConfirmBack();
		});
	 }

    var _getCompensatoryConfirmBack = function () {
        _getCompensatoryType();
        _mfBusCompensatoryConfirmInit();
    }

    //代偿
    var _mfBusCompensatoryConfirmInit = function () {
        $.ajax({
            url: webPath + '/mfBusCompensatoryConfirm/getByFincId',
            data: {"fincId": fincId },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    //点亮代偿图标
                    $("#compensatory_info").addClass("btn-dodgerblue");
                    //添加代偿点击事件
                    $("#compensatory_info").click(function () {
                        MfBusCompensatory.getCompensatoryInfo();
                    })
                }
            }
        })
    };
	 var _getCompensatoryInfo = function(){
			top.flag=false;
			top.window.openBigForm(webPath+"/mfBusCompensatoryConfirm/getDetail?fincId="+fincId+"&compensatoryId="+compensatoryId,'代偿信息',function(){

			});
		 }
	return{
		init:_init,
		getCompensatoryType:_getCompensatoryType,
		getCompensatoryApply:_getCompensatoryApply,
		getCompensatoryConfirm:_getCompensatoryConfirm,
		getCompensatoryInfo:_getCompensatoryInfo,
		pubMfBusCompensatoryList:_pubMfBusCompensatoryList,
        mfBusCompensatoryConfirmInit : _mfBusCompensatoryConfirmInit
	};
}(window,jQuery);