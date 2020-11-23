;
var QueryItemOaList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		for(var key in mapObj) {
		    var subHtml="";
			if(key=="attention"){
				subHtml = _setHtml(key,mapObj[key]);
				$("#attentionDiv").append(subHtml);
			}else{
				subHtml = _setHtml(key,mapObj[key]);
				$("#unattentionDiv").append(subHtml);
			}
        } 
	};
	
	var _addItem = function(obj){
		var itemId = $(obj).parents(".info-box-div").attr("id");
		var htmlStr = $(obj).parents(".info-box-div").prop("outerHTML");
		$(obj).parents(".info-box-div").remove();
		$("#attentionDiv").append(htmlStr);
		$("#attentionDiv").find("#"+itemId).removeClass("box-unselect");
		$("#attentionDiv").find("#"+itemId+" .box-hover").html('<div class="box-hover-content"><button class="opt-btn i i-x42" onclick="QueryItemOaList.deleteItem(this);"></button></div>');
	};
	
	var _deleteItem = function(obj){
		var itemId = $(obj).parents(".info-box-div").attr("id");
		var htmlStr = $(obj).parents(".info-box-div").prop("outerHTML");
		$(obj).parents(".info-box-div").remove();
		$("#unattentionDiv").append(htmlStr);
		$("#unattentionDiv").find("#"+itemId).addClass("box-unselect");
		$("#unattentionDiv").find("#"+itemId+" .box-hover").html('<div class="box-hover-content"><button class="opt-btn i i-gouxuan" onclick="QueryItemOaList.addItem(this);"></button></div>');
	};
	var _setHtml= function(key,subList){
		var btnIncon="";
		var func = "";
		var selectStr="";
		if(key=="attention"){
			btnIncon="x42";
			func = "QueryItemOaList.deleteItem(this);"
		}else{
			btnIncon="gouxuan";
			func = "QueryItemOaList.addItem(this);"
			selectStr="box-unselect";
		}
		var subHtml="";
		$.each(subList,function(i,itemObj){
			subHtml = subHtml+'<div class="col-xs-3 info-box-div '+selectStr+'" id="'+itemObj.itemId+'">'
				+'<div class="info-box margin_bottom_25">'
				+'<i class="info-box-icon i i-'+itemObj.itemIcon+'"></i>'
				+'<div class="info-box-content margin_left_60">'
				+'<span class="info-box-text">'+itemObj.itemName+'</span> '
				+'</div>'
				+'</div>'
				+'<div class="box-hover">'
				+'<div class="box-hover-content">'
				+'<button class="opt-btn i i-'+btnIncon+'" onclick="'+func+'"></button>'
				+'</div>'
				+'</div>'
				+'</div>';
		});
		 return subHtml;
	};
	
	var _selectConfirm = function(){
		var itemid = "";
		$("#attentionDiv .info-box-div").each(function(index){
			itemid =itemid+$(this).attr("id")+"|";
		});
		top.itemId = itemid;
		top.flag=true;
		myclose_click();
	};
	
	return{ 
		init:_init,
		addItem:_addItem,
		deleteItem:_deleteItem,
		selectConfirm:_selectConfirm
		
	};
	 
}(window,jQuery);