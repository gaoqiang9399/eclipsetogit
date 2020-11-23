//table ID 保证唯一
var table_id = "#tablist";
//创建固定表头
function create_thead() {
	if ($(".table-float-head").length > 0) {
		$(".table-float-head").remove();
	}
//	if ($("#mCSB_1_container").position().top < 0) {
		var $this = $(table_id).find("thead");
		var $divHead = $(".tabCont");
		var X = $this.offset().left;
		var Y = $divHead.offset().top + $divHead.outerHeight(true);
		var H = $this.outerHeight(true);
		var $div = $("<div class='table-float-head'></div>");
		var $divcss = {
			"left" : 0,
			"top" : Y,
			"width" : "auto",
			"height" : H
		};
		$div.css($divcss);
		$this.find('th').each(function(i) {
			if (!$(this).is(":hidden")) {
				var tW = $(this).innerWidth();
				var tH = $(this).innerHeight();
				var $th = $("<div class='table-float-th'></div>");
				$th.html($(this).html());
				var $thcss = {
					"width" : tW,
					"height" : tH,
					"line-height" : tH + "px"
				};
				$th.css($thcss);
				$th.appendTo($div);
			}
		});
		$div.appendTo('body');
//	}
}
//显示不同列
function showTable(flag){
	if(flag){
		$(".search-title").find("span").html($(flag).html()).attr("value",$(flag).attr("value"));
	}
	var $flag =$(".search-title").find("span").attr("value").trim();
	var $obj = $(table_id).find("th");
	$obj.each(function(){
		var $index = $(this).index();
		if($flag.indexOf($(this).attr("name").trim())>-1){
			$(table_id).find("tr").each(function(){
				$(this).find("td,th").each(function(i){
					if($(this).index()==$index){
						$(this).show();
					}
				});
			});
		}else{
			$(table_id).find("tr").each(function(){
				$(this).find("td,th").each(function(i){
					if($(this).index()==$index){
						$(this).hide();
					}
				});
			});
		}
	});
	$(table_id).show();
	create_thead();
}
//获取所有表头
function getThName(){
		var thName="";
		var $obj = $(table_id).find("th");
		$obj.each(function(){
			thName+=$(this).attr("name")+",";
		});
		//console.log(thName);
	}