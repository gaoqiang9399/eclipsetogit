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
	//���������ӽ��µĲ���ģ�飬ֻ�����ڴ����table������
	$(".scroll_table").mCustomScrollbar({
		axis:"y",
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
});
