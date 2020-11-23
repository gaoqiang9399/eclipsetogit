;//财务使用更多查询
var MoreSearch = function(window, $) {
	/**
	 * 在此处定义全局变量-各函数内公共使用的。
	 * 函数作用域内的局部变量还是要通过var在函数内部声明。
	 */
	
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		//菜单按钮
		$('.ui-btn-menu .menu-btn').bind('mouseenter.menuEvent',function(e){
			if($(this).hasClass("ui-btn-dis")) {
				return false;
		    }
			$(this).parent().toggleClass('ui-btn-menu-cur');
		    $(this).blur();
		    e.preventDefault();
		});
		//点击其他位置关闭更多查询
		$(document).bind('click.menu',function(e){
			var target  = e.target || e.srcElement;
			$('.ui-btn-menu').each(function(){
				var menu = $(this);
				if($(target).closest(menu).length == 0 && $('.search_con',menu).is(':visible')){
					menu.removeClass('ui-btn-menu-cur');
				}
			});
		});
		//更多条件 收起与展开
		$("#conditions-trigger").on("click",function(a){
			a.preventDefault(),
			$(this).hasClass("conditions-expand")?
			$("#more-conditions").stop().slideUp(200,function(){
				$("#conditions-trigger").removeClass("conditions-expand").html("更多条件<b></b>"),
				$("#filter-reset").css("display","none")
			}):$("#more-conditions").stop().slideDown(200,function(){
				$("#conditions-trigger").addClass("conditions-expand").html("收起更多<b></b>"),
				$("#filter-reset").css("display","inline")
			});
		});
	};
	
	//关闭更多查询
	var _colseMoreBtn = function () {
		if($('.search_con').is(':visible')){
			$('.ui-btn-menu').removeClass('ui-btn-menu-cur');
		}
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		colseMoreBtn : _colseMoreBtn
	};
}(window, jQuery);