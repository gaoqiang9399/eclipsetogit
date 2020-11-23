var layout = {
	ww: window.innerWidth,
	hh: window.innerHeight,
	time: 500,
	delaytime:0
}
/**
 * url 跳转页面的链接
 * delaytime 为了个别页面切换减少卡顿，可以使用该参数延迟加载，单位：毫秒
 */
window.loadPage = function(url,delaytime) {
	if(delaytime&&typeof(delaytime)=="number"){
		layout.delaytime = delaytime;
	}
	var ww = window.innerWidth;
	var $curr = $(".view-page.curr-page");
	var $next = $(".view-page").not(".curr-page");
	var iframe = $next.find("iframe").get(0);
	iframe.src = url;
	$next.css({left:layout.ww});
	if(iframe.attachEvent) {
		iframe.attachEvent("onload", function() {
			setTimeout(function(){
				$.hideLoading();
				$curr.addClass("suoxiao-page").animate({
					left: -ww
				}, layout.time, function() {
					$(this).css({left: "0px","z-index":1});
					$(this).removeClass("curr-page").removeClass("suoxiao-page");
				});
				$next.animate({
					left: 0
				}, layout.time, function() {
					$(this).css({left: "0px","z-index":2});
					$(this).addClass("curr-page");
					layout.delaytime = 0;
				});
			},layout.delaytime);
			
		});
	} else {
		iframe.onload = function() {
			setTimeout(function(){
				$.hideLoading();
				$curr.addClass("suoxiao-page").animate({
					left: -ww
				}, layout.time, function() {
					$(this).css({left: "0px","z-index":1});
					$(this).removeClass("curr-page").removeClass("suoxiao-page");
				});
				$next.animate({
					left: 0
				}, layout.time, function() {
					$(this).css({left: "0px","z-index":2});
					$(this).addClass("curr-page");
					layout.delaytime = 0;
				});
			},layout.delaytime);
		};
	}

};

$(function() {
	$(".view-page").eq(0).addClass("curr-page");
	$(".view-page").width(layout.ww);
	$(".view-page").height(layout.hh);
	$(".view-page").eq(0).css({
		top: "0px",
		left: "0px",
		"z-index":2
	});
	$(".view-page").eq(1).css({
		top: "0px",
		left: "0px",
		"z-index":1
	});
	$(window).resize(function(){
		layout.ww=window.innerWidth;
		layout.hh=window.innerHeight;
		$(".view-page").width(layout.ww);
		$(".view-page").height(layout.hh);
	});
});