var AnimEnd = 'animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd oAnimationEnd';
var nav,navButton,overlay,WinSize;
var $cell,Offset;
$(document).ready(function(){
	nav = $('.free-wall-sheji');
	navButton = $('.cell');
	overlay = $('.overlay');
	WinSize = {
			width: document.documentElement.clientWidth,
			height: window.innerHeight-48
	};
	$(".close").bind("click",cellClose);
});
function cellClose(){
	if($cell==null||typeof($cell)=="undefined") return;
	$cell.removeClass('active').addClass('active_reverse');
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
	}, 200);
}
function cellDbclick(event){
	clearTabs();
	$cell = event.data.cell;
	if($cell.attr("cellid")){
		setDataToTabs(blockDatas[$cell.attr("cellid")]);
	}
	
   $cell = $(this);
    if ($cell.hasClass("inactive")) {
        event.preventDefault();
    } else {
        $(navButton).removeClass('inactive_reverse active_reverse');
        $(overlay).removeClass('active active_reverse');
        $cell.children().css("opacity","0");
        $cell.addClass('active');
        var item = $cell[0];
        var overlayOffset = item.getBoundingClientRect();
        Offset = {
    		left:overlayOffset.left,
    		top:overlayOffset.top-48,
    		width:item.offsetWidth,
    		height:item.offsetHeight
        };
        var win = WinSize;
        var dx = win.width/2 - Offset.left - Offset.width/2,
			dy = win.height/2 - Offset.top - Offset.height/2,
			zx = win.width/Offset.width,
			zy = win.height/Offset.height;
        item.style.WebkitTransform = 'translate3d(' + dx + 'px, ' + dy + 'px, 0) scale3d(' + zx + ', ' + zy + ', 1)';
        item.style.transform = 'translate3d(' + dx + 'px, ' + dy + 'px, 0) scale3d(' + zx + ', ' + zy + ', 1)';

        $(overlay).addClass('active');
        var config = $(overlay).find(".cell-config")[0];
        config.style.opacity="0";
        var close = $(overlay).find(".close")[0];
        close.style.opacity="0";
        resize(close,config);
        $("body").addClass('noscroll');
    }
}

function resize(close,config){
	$(close).animate({
		opacity:1
	},1000);
	$(config).animate({
		opacity:1
	},500);
}