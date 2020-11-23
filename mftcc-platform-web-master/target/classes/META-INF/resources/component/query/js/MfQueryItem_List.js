;
var QueryItemList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		
		$("#itemsDiv .option-div").bind("click",function(){
			if($(this).hasClass("selected")){
				$(this).removeClass("selected");
			}else{
				$(this).addClass("selected");
			}
		});		
	};
	//选择后确认
	var _selectConfirm = function(){
		var itemid = "";
		$(".option-div.selected").each(function(index){
			itemid =itemid+$(this).data("itemid")+"|";
		});
		top.itemId = itemid;
		top.flag=true;
		myclose_click();
	};
	
	return{ 
		init:_init,
		selectConfirm:_selectConfirm
	};
	 
}(window,jQuery);