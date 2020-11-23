var AI_options = {
		disNo:{},
		disName:{},
		flag:{}//czk 2016-11-04 factor的自定义需求标识
};
/*
 * 行政区划
 * selectArea(this,'name');
 */
var selectArea = function(obj,disName,flag){
	
	AI_options.flag=flag;
	
	$(obj).removeAttr("onblur");
	if($('.nmd-zone-html').length>0){
		$('.nmd-zone-html').find('iframe').remove();
		$('.nmd-zone-html').remove();
		return false;
	}
	var $disNo = AI_options.disNo = $(obj);
	AI_options.disName = $disNo.parents("form").find("[name="+disName+"]");
	AI_options.selectType = "Area";
	var $elem = $(AIHtml);
	$elem.find('.nmd-zone-title').html("行政区划");
	$elem.find('.fa').bind("click",function(){
		AI_options.disNo.attr("onblur","func_uior_valTypeImm(this);");
		$('.nmd-zone-html').slideUp(function(){
			$('.nmd-zone-html').find('iframe').remove();
			$('.nmd-zone-html').remove();
		});
	});
	$elem.find("iframe").attr("src",webPath+'/nmdArea/getLv1Page');
	$elem.hide();
	$elem.appendTo('body');
	$elem.slideDown();
};
/*
 * 行业分类
 */
var selectIndustry = function(obj,disName){
	$(obj).removeAttr("onblur");
	if($('.nmd-zone-html').length>0){
		$('.nmd-zone-html').find('iframe').remove();
		$('.nmd-zone-html').remove();
		return false;
	}
	var $disNo = AI_options.disNo = $(obj);
	AI_options.disName = $disNo.parents("form").find("[name="+disName+"]");
	AI_options.selectType = "Industry";
	var $elem = $(AIHtml);
	$elem.find('.nmd-zone-title').html("行业分类");
	$elem.find('.fa').bind("click",function(){
		AI_options.disNo.attr("onblur","func_uior_valTypeImm(this);");
		$('.nmd-zone-html').slideUp(function(){
			$('.nmd-zone-html').find('iframe').remove();
			$('.nmd-zone-html').remove();
		});
	});
	$elem.find("iframe").attr("src",webPath+'/nmdArea/getListAll');
	$elem.hide();
	$elem.appendTo('body');
	$elem.slideDown();
};
var AIHtml = '<div class="nmd-zone-html">'
	+'<div class="nmd-zone-head">'
	+'<span class="nmd-zone-title"></span><i class="fa fa-close"></i>'
	+'</div>'
	+'<div class="nmd-zone-body">'
	+'<iframe id="nmd-zone-iframe" src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe>'
	+'</div>'
	+'</div>';
function returnAIVal(disNo,disNames){
	var disName = "";
	for(var i = 0;i<disNames.length;i++){
		if(i==0){
			disName+=disNames[i].name;
		}else{
			disName+=">>"+disNames[i].name;
		}
	}
	/*2016-11-04 czk lease项目是通过点击行政区划编号触发此函数，factor是将编号隐藏，通过点击行政区划名称触发此函数
	*/
	if(AI_options.flag == "factor-area"){
		AI_options.disNo.val(disName);
		AI_options.disName.val(disNo);
	}else{
		AI_options.disNo.val(disNo);
		AI_options.disName.val(disName);
	}
	
	$('.nmd-zone-html').slideUp(function(){
		$('.nmd-zone-html').find('iframe').remove();
		$('.nmd-zone-html').remove();
	});
	AI_options.disNo.attr("onblur","func_uior_valTypeImm(this);");
}
