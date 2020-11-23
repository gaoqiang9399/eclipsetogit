;
var OaBankNoteList = function (window,$){
	var _init = function(){
		_billInsert();
		
	};
	var _billInsert = function(){ 
		$("#billInsert").bind("click", function(event) {
			top.addFlag = false;
			top.window.openBigForm(webPath+'/mfOaBankNote/input', '票据申请',function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	};
	var _getById = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url, '票据申请详情',myclose);
	};
	var _approvalHis=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
    	window.parent.openBigForm(url,"审批历史",function(){});
    };
	return {
		init:_init,
		getById: _getById,
		approvalHis:_approvalHis,
	};
}(window,jQuery);