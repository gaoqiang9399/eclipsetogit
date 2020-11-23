;
var MfKindFormConfig=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		var htmlStr = getPrdctFormConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
	};
	//产品表单配置
	var getPrdctFormConfigHtml = function(data){
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content padding_left_20">';
		
		htmlStr=htmlStr+'</div></div></div>';
		return htmlStr;
	};
	return{
		init:_init,
	};
}(window, jQuery);