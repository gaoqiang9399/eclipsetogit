var $cell,$cellOpen,index,Offset,AnimEnd,nav,navButton,overlay,WinSize;
$(document).ready(function(){
	AnimEnd = 'animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd oAnimationEnd';
	nav = $('.free-wall');
	navButton = $('.cell');
	overlay = $('.overlay');
	WinSize = {
		width: document.documentElement.clientWidth-16,
		height: window.innerHeight-68
	};
});
function more(e){
	   var type = e.data.type;
	   index = $(this).index();
	   $cell = $(this).parents(".cell");
	   $cellOpen = $cell.clone(true);
	    if ($cell.hasClass("inactive")) {
	        return false;
	    } else {
	        $(navButton).removeClass('inactive_reverse active_reverse');
	        $(overlay).removeClass('active active_reverse');
	        $cell.children().css("opacity","0");
	        $cell.addClass('active');
	        var item = $cell[0];
	        var overlayOffset = item.getBoundingClientRect();
	        Offset = {
	    		left:overlayOffset.left,
	    		top:overlayOffset.top,
	    		width:item.offsetWidth,
	    		height:item.offsetHeight
	        };
	        var win = WinSize;
	        var dx = win.width/2 - overlayOffset.left - item.offsetWidth/2+8,
				dy = win.height/2 - overlayOffset.top - item.offsetHeight/2+48,
				zx = win.width/item.offsetWidth,
				zy = win.height/item.offsetHeight;
	        item.style.WebkitTransform = 'translate3d(' + dx + 'px, ' + dy + 'px, 0) scale3d(' + zx + ', ' + zy + ', 1)';
	        item.style.transform = 'translate3d(' + dx + 'px, ' + dy + 'px, 0) scale3d(' + zx + ', ' + zy + ', 1)';
	        $(overlay).addClass('active');
	        $("body").addClass('noscroll');
	        resize($cell,type);
	    }
	}
	function resize($cell,type){
		$cellOpen.css({
			width:"100%",
			height:"100%",
			top:"0px",
			left:"0px"
		});
		$cellOpen.find(".more").remove();
		var close = $('<button class="close"><span class="i i-x2"></span></button>').appendTo($cellOpen.find(".handle"));
		
		close.bind("click",function(){
			closePopup();
		});
		
		$(".free-wall-open").html("");
		$(".free-wall-open").append($cellOpen);
		
		if(type=="all"){
			$cellOpen.find(".list").html("");
			initUl($cellOpen,wsData[$cellOpen.attr("taskNo")],0);
			$cellOpen.find(".list").mCustomScrollbar({
				theme:"minimal-dark"
			});
		}else{
			$cellOpen.find(".list").remove();
			liClick($cellOpen,wsData[$cellOpen.attr("taskNo")][index]);
		}
	}
function closePopup(){
	if(isList){
		$cellOpen.find(".info").html("<ul class='list'></ul>");
		initUl($cellOpen,wsData[$cellOpen.attr("taskNo")],0);
		isList = false;
		$cellOpen.find("h2").show();
	}else{
		$(".close").animate({
			opacity:0
		},500);
		overlay.addClass('active_reverse');
	    var item = $cell[0];
	    var win = WinSize,
			dx = Offset.left + Offset.width/2 - win.width/2,
			dy = Offset.top + Offset.height/2 - win.height/2,
			zx = Offset.width/win.width,
			zy = Offset.height/win.height;
	    item.style.WebkitTransform = 'translate3d(0, 0, 0) scale3d(1, 1, 1)';
	    item.style.transform = 'translate3d(0, 0, 0) scale3d(1, 1, 1)';
	    overlay.css("visibility","hidden");
	    setTimeout(function(){
			$cell.children().animate({
				opacity:1
			});
			$cellOpen.find("h2").show();
		}, 200);
	    setTimeout(function(){
			$('.active', nav).removeClass('active').addClass('active_reverse');
		}, 500);
	}
}