//加载动画  czk 20160930
;
var LoadingAnimate = function(projectPath, loadingGifPath){
	var _begin = function(){
		if($("body").find("#loadingAnimateModal").length>0){
			return false;
		}
		if(top.$("body").find("#loadingAnimateModal").length>0){
			return false;
		}
		//修改为全路径
		var path = projectPath+"/"+loadingGifPath || (projectPath+"/themes/factor/images/loadingLogo.gif");
		var loadingModal = '<div class="modal fade in" id="loadingAnimateModal" tabindex="-1" role="dialog"  aria-hidden="true" style="display: block;">'
		+ '<img src="'+path+'" style="position: absolute;left:45%;top:40%;">'
		+'</div>';
		$("body").append(loadingModal);
		$("#loadingAnimateModal").modal({
	        backdrop:false,
	        show:true,
	        keyboard: false
	    });
		
	};
	var _end =  function(){
		$('#loadingAnimateModal').remove();
	};
	return {
		start : _begin,
		stop : _end
	};
} (webPath, loadingGifPath);
