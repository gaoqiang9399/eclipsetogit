var MfOaBadgeMst = function(window, $) {
	
	var _ajaxGetById = function (obj,msg,url){
		top.addFlag = false;
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url,msg, function() {
			updateTableData();//重新加载列表数据
		});
	};
	
	var _showAppHis = function showAppHis(obj, url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		_ajaxGetById(obj, '审批历史', url);
	}
	
	return {
		ajaxGetById : _ajaxGetById,
		showAppHis : _showAppHis
	};
}(window, jQuery);
