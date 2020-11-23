var oldAlert = window.alert;
window.alert = function(msg,type,fun1,fun2){
	$('.cd-popup').remove();
    if(typeof(type)!="undefined"){
    	var view = $(window.top.document.body);
    	view.append('<div class="cd-popup" role="alert" style="background-color: rgba(150,150,150,0.5);"><div class="cd-popup-container cd-alert"><div class="popup-close" onclick="closeDetail();"></div></div></div>');
    	view.find(".cd-popup-container").css("padding",0);
    	var header = $("<div class='header'></div>").appendTo(view.find(".cd-popup-container"));
    	header.append("<span>"+msg+"</span>");
    	view.find('.cd-popup').addClass('is-visible');
    	switch(type){
	    	case 0:
	    		header.append("<i class='i i-x1 err'></i>");
	    		if(typeof(data)=="string"&&data!=""){
	    			header.find("span").css("position","static");
	    			var errorDiv = $("<div class='error'></div>").appendTo(view.find(".cd-popup-container"));
	    			var errorMsg = $("<div class='error-msg'>"+data+"</div>").appendTo(errorDiv);
	    			errorDiv.mCustomScrollbar({
						theme:"minimal-dark"
					});
	    		}
	    	  break;
	    	case 1:
	    		header.append("<i class='i i-duihaocheckmark17 cuss'></i>");
	    		view.find(".popup-close").hide();
	    		setTimeout(function(){
	    			view.find(".popup-close").click();
	    		},1000);
	    	  break;
    	    case 3:
	    		//和case 1一样，但不自动关闭
	    		header.append("<i class='i i-duihaocheckmark17 cuss'></i>");
	    		break;
	    	case 2:
	    		header.append("<i class='i i-tanhao warn'></i>");
	    		view.find(".popup-close").hide();
	    		var warnDiv = $("<div class='warning'></div>").appendTo(view.find(".cd-popup-container"));
    			var submitBtn = $("<input class='submitBtn' type='button' value='确定'/>").appendTo(warnDiv);
    			var cancelBtn = $("<input class='cancelBtn' type='button' value='取消'/>").appendTo(warnDiv);
    			cancelBtn.click(function(){
    				view.find(".popup-close").click();
    				if(typeof(fun2)=="function"){
    					fun2();
        			}
    			});
    			submitBtn.click(function(){
    				view.find(".popup-close").click();
    				if(typeof(fun1)=="function"){
    					fun1();
        			}
    			});
	    	  break;
	    	default:
	    		 oldAlert(msg);
	    	 break;
    	}
    }else{
        oldAlert(msg);
    }
};
//加载
window.loadding = function(){
	  var $body = $("body");
	  if($body.find(".MFTloader").length<1){
		  var loader = $('<div class="MFTloader"></div>');
		  loader.css({
			"position": "fixed",
		    "width": "100%",
		    "height": "100%",
		    "top": "0px",
		    "left": "0px",
		    "background-color": "rgba(90, 90, 90, 0)",
		    "z-index": "9999",
		  });
		  var MFTgif = $('<div></div>');
		  MFTgif.css({
				"margin": "0 auto",
				"min-height": "50px",
			    "min-width": "50px",
			    "left": "38%",
			    "top": "50%",
			    "position": "absolute",
		  		"transform": "translate(-50%, -50%)",
		  		"background": "url(/"+loadingSmallGifPath+") no-repeat scroll",
		  		"backgroundSize": "100%,100%"
			  });
		  loader.append(MFTgif);
		  $body.append(loader);
	  }
};
//加载完成
window.loaded = function(timeToHide){
	var $body = $("body");
	if(timeToHide!==undefined){
		 setTimeout(function(){
			 $body.find(".MFTloader").remove();
		 }, timeToHide);
	}else{
		$body.find(".MFTloader").remove();
	}
};
window.closeDetail = function(){
	$('.cd-popup').removeClass('is-visible');
	$(".ui-layout-pane").removeClass("row");
	var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
	$('.cd-popup').one(transitionEvent, function(){
		$('.cd-popup').remove();
	});
};
/* 探测浏览器种类 */
function whichTransitionEvent(el){
    var t;
    var transitions = {
      'transition':'transitionend',
      'OTransition':'oTransitionEnd',
      'MozTransition':'transitionend',
      'WebkitTransition':'webkitTransitionEnd'
    };
    for(t in transitions){
        if( el.style[t] !== undefined ){
            return transitions[t];
        }
    }
}