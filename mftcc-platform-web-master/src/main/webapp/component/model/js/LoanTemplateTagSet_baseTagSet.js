var TagItemList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		var index = $("li[class=current]").attr("value");
		$("tr[id="+index+"]").show();
		var  itemsHtml = "";
		$.each(groupFlag,function(i,mapObj){
			itemsHtml += _setHtml(mapObj.optCode,itemList);
		});
		$("#itemsDiv").html(itemsHtml);
		$("#itemsDiv .option-div").bind("click",function(){
			if($(this).hasClass("selected")){
				$(this).removeClass("selected");
			}else{
				$(this).addClass("selected");
			}
		});	
	};
	var _setHtml= function(type,itemList){
		var subHtml="";
		var allCheck = "<input type='button' value='全选' onclick=\"TagItemList.allInCheck('type"+type+"')\"><input type='button' style='margin-left:5px' value='反选' onclick=\"TagItemList.backCheck('type"+type+"')\">";
		subHtml=subHtml+allCheck+'<div id="type'+type+'" class="row clearfix margin_top_20" style="padding: 10px 30px;border-bottom: 1px solid #d2d2d2;>'
		+'<div class="col-xs-12 column">';
		var subHtmlTmp = "";
		$.each(itemList,function(i,mapObj){
			if(mapObj.groupFlag == type){
				var selected = "";
				if(mapObj.isChecked=="1"){
					selected="selected";
				}
				subHtmlTmp=subHtmlTmp+'<div title="'+mapObj.remark+'" class="col-xs-3" style="text-align:center">';
				subHtmlTmp=subHtmlTmp+'<div class="option-div '+selected+'"><span id="'+mapObj.keyNo+'" name="'+mapObj.tagKeyName+'">'+mapObj.remark+'</span><i class="i i-sanjiaoduihao"></i></div>';
				subHtmlTmp=subHtmlTmp+'</div>';
			}
		});
		if(subHtmlTmp != ""){
			subHtml=subHtml+subHtmlTmp+'</div></div>';
		}else{
			subHtml = "";
		}
	   return subHtml;
	};
	var _showTags = function(obj){
		var value = $(obj).attr("value");
		$("tr").hide();
		$("tr[id="+value+"]").show();
		$("li[class=current]").removeClass("current");
		$(obj).attr("class","current");
	};
	//更新书签
	var _updateTagSet = function(){
		var templatePath = "/factor/component/model/docmodel/";
		var tagkeyno = "";
		var tagkeyname="";
		$(".selected > span").each(function(){
			tagkeyno = tagkeyno + $(this).attr('id')+",";
			tagkeyname = tagkeyname +$(this).attr('name')+",";
		});
		if(tagkeyno.length>0){
			tagkeyno = tagkeyno.substring(0,tagkeyno.length-1);
		}
		if(tagkeyname.length>0){
			tagkeyname = tagkeyname.substring(0,tagkeyname.length-1);
		}
		//modelCnName = encodeURI(encodeURI(modelCnName));
		tagkeyname = encodeURI(encodeURI(tagkeyname));
		jQuery.ajax({
			url:webPath+"/loanTemplateTagSet/updateTagSetAjax",
			data:{templateNo:templateNo,tagkeyno:tagkeyno,tagkeyname:tagkeyname,templatePath:templatePath},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
					 myclose_click();
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	var _selectAll = function(type){
		$("#"+type+" .option-div").each(function(){
			$(this).addClass("selected");
		});
	};
	var _backAll = function(type){
		$("#"+type+" .option-div").each(function(){
			if($(this).hasClass("selected")){
				$(this).removeClass("selected");
			}else{
				$(this).addClass("selected");
			}
		});
	};
	return{
		init:_init,
		showTags :_showTags,
		updateTagSet:_updateTagSet,
		allInCheck : _selectAll,
		backCheck : _backAll
	};
}(window,jQuery);
