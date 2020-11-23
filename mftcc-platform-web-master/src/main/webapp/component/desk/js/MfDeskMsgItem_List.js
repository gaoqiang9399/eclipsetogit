;
var MfDeskMsgItem_List = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _addItem = function(obj){
		var itemId = $(obj).parents(".info-box-div").attr("id");
		var htmlStr = $(obj).parents(".info-box-div").prop("outerHTML");
		$(obj).parents(".info-box-div").remove();
		$("#attentionDiv").append(htmlStr);
		$("#attentionDiv").find("#"+itemId).removeClass("box-unselect");
		$("#attentionDiv").find("#"+itemId+" .box-hover").html('<div class="box-hover-content"><button class="opt-btn i i-x42" onclick="MfDeskMsgItem_List.deleteItem(this);"></button></div>');
	};
	
	var _deleteItem = function(obj){
		var itemId = $(obj).parents(".info-box-div").attr("id");
		var htmlStr = $(obj).parents(".info-box-div").prop("outerHTML");
		$(obj).parents(".info-box-div").remove();
		$("#unattentionDiv").append(htmlStr);
		$("#unattentionDiv").find("#"+itemId).addClass("box-unselect");
		$("#unattentionDiv").find("#"+itemId+" .box-hover").html('<div class="box-hover-content"><button class="opt-btn i i-gouxuan" onclick="MfDeskMsgItem_List.addItem(this);"></button></div>');
	};
	var _selectConfirm = function(){
		var itemid = "attention|";
		$("#attentionDiv .info-box-div").each(function(index){
			itemid = itemid + $(this).attr("id") + "|";
		});
		itemid = itemid  + "||unattention|";
		$("#unattentionDiv .info-box-div").each(function(index){
			itemid = itemid + $(this).attr("id") + "|";
		});
		top.itemId = itemid;
		top.flag = true;
		myclose_click();
	};
	
	return{ 
		init:_init,
		addItem:_addItem,
		deleteItem:_deleteItem,
		selectConfirm:_selectConfirm
		
	};
	 
}(window,jQuery);