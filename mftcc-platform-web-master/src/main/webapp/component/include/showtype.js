/**
 * 按钮类型初始化方法
 * @param {Object} obj(this对象)
 * @param {Object} parm 参数名
 * @param {Object} array 参数-显示名称
 * @param {Object} url 连接
 */
var choiceTypeInit = function(obj){
	var column = obj.column,
	array = obj.array,
	inputUrl = obj.inputUrl,
	insertUrl = obj.insertUrl;
	$(".choiceType").hover(function(){
		choiceType($(this),column,array,inputUrl,insertUrl);
	},function(){});
};
var choiceType = function(obj,column,array,url,insertUrl){
	var $obj = $(obj);
	var top = $obj.position().top+$obj.height();
	var right =$("body").width()-($obj.position().left+$obj.width()+30);
	$(".showType").remove();
	var $show = $('<div class="showType" style=" border: 2px solid #eef2f5;'+
				  ' border-radius: 2px; color: #a7aebb;font-size: 12px;'+
				  'position: absolute; background-color: white;">'+
				  '<div class="show"><ul style=" margin: 0;padding:0;max-height:160px;overflow:auto;min-width:100px">'+
				  '</ul></div></div>');
	$show.css({"top":top+"px","right":right+"px"});
	$.each(array,function(index,val){
		var vals = val.split("-");
		var newUrl = initUrl(url,{"key":column,"val":vals[0]});
		var typeHtml = '<li onclick="ajaxInitForm(this,\''+newUrl+'\',\''+insertUrl+'\')" style="padding: 1px 10px;">'+vals[1]+'</li>';
		$show.find("ul").append(typeHtml);
	});
	$show.find("ul li").mouseover(function(){
		$(this).addClass("hovers");
		$(this).siblings().removeClass("hovers");
	});
	$(".choiceType").trigger('mouseout');
	$("body").append($show);
	$(".showType").hover(function(){
		$(this).show();
	},function(){
		$(this).hide();
	});
};
var initUrl = function(url,parm){
	if(url.indexOf("?")!=-1){
		url+="&";
	}else{
		url+="?";
	}
	url+=parm.key+"="+parm.val;
	return url;
};
function ajaxInitForm(obj,url,insertUrl){
	var $obj= $(obj);
	$.ajax({
		type:"get",
		url:url,
		async:false,
		cache:false, 
		success:function(data){
			if(data.flag == "success"){
				ajaxInput($obj.parents("body").find(".input_btn"),insertUrl);
				$obj.parents(".showType").remove();
				$(".content_table").find(".content_form").find("tbody").html($(data.formHtml).find("tbody").html());
				$(".content_table").find(".content_form").find("table").attr("title",$(data.formHtml).filter("table").attr("title"));
				var $formHidden = $(data.formHtml).filter("input[type=hidden]");
				$(".content_table").find(".content_form").find("form").find("table").parent().find("input[type='hidden']").remove();
			  	$formHidden.each(function(){
					$(".content_table").find(".content_form").find("form").find("table").parent().append($(this).prop('outerHTML'));
				});
				$(".content_table").find(".content_form").show();
//				mCSformLineCallback.elem = $obj;
				initIfreamHeight(mCSformLineCallback);
				initIfreamHeight();
			}else if(data.flag == "error"){
				//$.myAlert.Alert("初始化失败！");
				alert("初始化失败！",0);
			}
		},error:function(){
			//$.myAlert.Alert("初始化失败！");
			alert("初始化失败！",0);
			}
	});
}
var mCSformLineCallback = function(){
	var divName = $(this).parents(".formDiv").attr("name");
	parent.scrollSelectorTop(divName);
//	parent.mcFormSelector.mCustomScrollbar("scrollTo",'div[name="' + divName + '"]', {
//		callback: true
//	});
//	parent.document.body.mCustomScrollbar("update");
};