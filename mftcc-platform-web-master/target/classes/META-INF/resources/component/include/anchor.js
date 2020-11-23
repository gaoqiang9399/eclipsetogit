/**
 * 页面显隐控制方法 obj为输入域对象
 */
var handleAnchorFun = function(obj){
	var anchorData = $(obj).attr("anchor-data");
	var value = $(obj).val();
	var $form = $(obj).parents("form");
	if(anchorData!==undefined&&anchorData!=null&&anchorData!=""){
		var anchor = eval('({'+anchorData+'})');//触发的标记点
		$.each(anchor.anchors,function(index,anchor){
            var anchorsValue = $form.find("input[name='anchors_value']").val();
			anchorsValue =  eval('('+anchorsValue+')');//锚点控制数据
			$.each(anchorsValue,function(anchorIndex,values){//锚点控制数组
				$.each(values,function(anchorKey,vals){//锚点控制对象
					var anchorFlag = true;
					$.each(vals,function(i,cols){//控制锚点值数组
						$.each(vals,function(i,colObj){
							$.each(colObj,function(key,ctrlVals){
								var keyValue = $("[name='"+key+"']", $form).val();
								if($.inArray(keyValue, ctrlVals.split("|"))==-1){
									anchorFlag = false;
								}
							});
						});
					});
					if(anchorFlag){
						$(obj).parents("table").find("tr."+anchorKey+"-anchor," +
								"tr."+anchorKey+"-anchor td input," +
								"tr."+anchorKey+"-anchor td select," +
								"tr."+anchorKey+"-anchor td textarea").removeAttr("disabled");

						//修改在切换锚点的时候  清空大写金额问题

						$(obj).parents("table").find("tr."+anchorKey+"-anchor," +
                            "tr."+anchorKey+"-anchor td span").find("span.bigAmt").html("");

                        $(obj).parents("table").find("tr."+anchorKey+"-anchor," +
                            "tr."+anchorKey+"-anchor td span").find("span.bigAmt").removeAttr("style");


                        $(obj).parents("table").find("tr."+anchorKey+"-anchor").show(200);
					}else{
						$(obj).parents("table").find("tr."+anchorKey+"-anchor," +
								"tr."+anchorKey+"-anchor td input," +
								"tr."+anchorKey+"-anchor td select," +
								"tr."+anchorKey+"-anchor td textarea").attr("disabled","disabled");
						$(obj).parents("table").find("tr."+anchorKey+"-anchor").hide(200);
					}
				});
			});
		});
	}
};
$(function(){
	//初始化加载
	$(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
	$(".anchor-ctrl select").each(function(){handleAnchorFun(this);});
});
(function($){
	/**
	 * 手动初始化 ctrlParam .class或#id 或者其他jquery 选择器
	 * 手动初始化 出发锚点标签 不要加 anchor-ctrl  样式
	 * 用法 $(".class").initAchor(function(){处理内容});
	 */
	$.fn.initAchor = function(callback){
		var $this = $(this);
		$(this).change(function(){
			handleAnchorFun($this);
			if(callback){
				callback.call(this);
			}
		});
	};
})(jQuery);