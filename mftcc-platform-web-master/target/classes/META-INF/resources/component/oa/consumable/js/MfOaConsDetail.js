;
var OaConsDetail = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$(".content_table").find("table").show();
	};
   var _consumableInsert = function() {
		$("#consInsert").bind("click", function(event) {
			top.addFlag = false;
			top.openBigForm(webPath+"/mfOaCons/input", "新增入库", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	}; 
	var _ajaxGetById = function (obj ,url){
	    top.addFlag = false;
		top.openBigForm(url,"低值易耗品操作详情", function() {
		myclose();
		if (top.addFlag) {
			window.location.reload();
		}
	});
  };
   
   var _ajaxFindConsumableLooking = function (obj ,url){
        window.parent.showDialog(url,"查阅情况","40","60");
        };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById : _ajaxGetById
	};
}(window, jQuery);