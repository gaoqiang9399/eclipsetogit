;
var MfTuningReport_Detail = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		if($("[name=vouType]").is(':visible')){
			bindVouTypeByKindNo($("#operform").find('[name=vouType]'), kindNo);
		}
		if(busModel=='12'){
            _tuningListInit('1');
            _tuningListInit('2');
            _tuningListInit('3');
            _tuningListInit('4');
            _tuningListInit('5');
		}
	};

	var _closeMyOnly = function (){
		$(top.window.document).find(".dhccModalDialog").eq(1).remove();
	};
	var _insertTuning = function(obj){
		var url = $(obj).attr("action");
		var flag= submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
					$.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:'post',
						dataType:'json',
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag=="success"){
								alert(data.msg,3);
								var url = $(top.window.document).find('.dhccModalDialog').eq(0).find('iframe').attr('src');
								$(top.window.document).find(".dhccModalDialog").eq(0).find("iframe").attr('src',url);
								 top.flag = true;
								 _closeMyOnly();
							}else{
								alert(top.getMessage("FAILED_SAVE"),0);
							}
						},error:function(){
							LoadingAnimate.stop();
							alert(top.getMessage("FAILED_SAVE"),0);
						}
					});
			}
	};
    //尽调下列表初始化
    var _tuningListInit = function (parmType) {
        var tableId = "tablecredittuningList"+parmType;
        $.ajax({
            url:webPath+"/mfCusCreditTuningDetail/findByPageAjax",
            type:'post',
            data : {tableId:tableId,delType:parmType,creditAppId:appId},
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $("#tuningList"+parmType).empty().html(data.tableHtml);
                }else if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
	return {
		init : _init,
		insertTuning:_insertTuning,
		closeMyOnly:_closeMyOnly
	};
}(window, jQuery);
