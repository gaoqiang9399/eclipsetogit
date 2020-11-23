;
var MfBusRiskAudit_Detail = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        $('.hidden-content').addClass('hidden');
	};


    var _getDetailPage = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        // top.LoadingAnimate.start();
        // window.location.href=url;
        top.window.openBigForm(url,"详情",function(){
            // updateTableData();//重新加在列表数据
        });
    };



	return {
		init : _init,
        getDetailPage:_getDetailPage
	};
}(window, jQuery);
