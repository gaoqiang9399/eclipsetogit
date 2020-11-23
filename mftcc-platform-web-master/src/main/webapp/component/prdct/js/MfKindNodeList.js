;
var MfKindNodeList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		var htmlStr=_setHtml(itemList);
		$("#itemsDiv").html(htmlStr);
		
		
		
		$("#itemsDiv .option-div").bind("click",function(){
			if($(this).hasClass("selected")){
				$(this).removeClass("selected");
			}else{
				$(this).addClass("selected");
			}
		});		
	};
	var _setHtml= function(itemList){
		var subHtml="";
		subHtml=subHtml+'<div class="row clearfix margin_top_20" style="padding: 10px 30px;">'
		   +'<div class="col-xs-12 column">';
		   $.each(itemList, function(i, itemObj) {
			  var selected = "";
			   if(itemObj.useFlag=="1"){
				   selected="selected";
			   }
			   subHtml=subHtml+'<div class="option-div '+selected+'" data-itemid="'+itemObj.id+'"><span>'+itemObj.name+'</span><i class="i i-sanjiaoduihao"></i></div>';
		   });
		   subHtml=subHtml+'</div></div>';
		   return subHtml;
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
	}
	
	return{ 
		init:_init,
		selectConfirm:_selectConfirm
	};
	 
}(window,jQuery);