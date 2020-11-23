$(function(){
	$(".scroll_x").mCustomScrollbar({
		horizontalScroll:true,
		axis:"x",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	$(".scroll_y").mCustomScrollbar({
		axis:"y",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	$(".scroll_xy").mCustomScrollbar({
		axis:"xy",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	$(".scroll_content_table").mCustomScrollbar({
		axis:"y",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	//不适用在视角下的布局模块，只适用于大表单的table滚动条
	$(".scroll_table").mCustomScrollbar({
		axis:"y",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
});
