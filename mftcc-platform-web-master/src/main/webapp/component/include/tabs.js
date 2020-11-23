$(function(){
	var $ul = $("#tablist");
	var $mainDiv = $("#iframeMain");
	$.each(tabs, function(index, tab){
		var $urlLi = $('<li style="width: 110px;" class="pages-btn"></li>');
		$urlLi.append('<a href="' + tab.url + '" target="mainIf" >' + tab.title + '</a>');
		$ul.append($urlLi);
		if(index == 0) {
			$mainDiv.append('<iframe name="mainIf" src="'+tab.url+'" style=";height:calc(100% - 40px);width:100%; "></iframe>');
		}
	});
	$('#tablist').lavaLamp();
	$('#tablist').delegate('.pages-btn','click',function() {
		if(!$(this).hasClass("current-cat")){
			$(this).addClass("current-cat").siblings('.pages-btn').removeClass("current-cat");
		}
	});
});