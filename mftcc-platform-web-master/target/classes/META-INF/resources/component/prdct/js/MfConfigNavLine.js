;
var MfConfigNavLine=function(window, $){
	//产品配置纵向导航线
	var _createKindConfigNavLine = function(configTypeList){

		var ddArr = [];
		var len = configTypeList;
		for(var i = 0;i<len.length;i++){
			var ddHtml;
			var parm = len[i];
			ddHtml = '<dd class="time-line-dd time-line-point" data-dit="' + parm.optCode + '">'
						+ '<span class="time-line-dot"><em></em></span>'
						+ '<a data-dit="' + parm.optCode
						+ '" class="time-line-a">'+ parm.optName
						+ '</a>' + '</dd>';
			ddArr.push(ddHtml);
		}
		
		$(".time-line-line").css({"height":(len.length-1)*50});
		$(".time-line-line").css("top","79px");
		
		$('.time-line-dl').html(ddArr.join(''));
		$('.time-line-bg').delegate('.time-line-point', 'click', function(evt) {
			$('.time-line-bg').find('.time-line-point-select').removeClass('time-line-point-select');
			$(this).addClass('time-line-point-select');
			$('.time-line-bg').find("*").removeClass('line-a-on').removeClass('line-dot-on i i-duihao1');
			$(this).find('a').addClass('line-a-on');
			$(this).find('span').addClass('line-dot-on i i-duihao1');
			//产品个性化配置的类型基础、核算、费用、影像、模板、流程、表单、风控模型等
			var configType = $(this).data('dit');
			MfSysKindConfig.getKindConfigByType(configType);
		});
		//默认选中第一个节点
		$("dd").eq(0).click();//默认触发获取第一项配置信息
		$("dd").eq(0).addClass('time-line-point-select');
		$("dd").eq(0).find('a').addClass('line-a-on');
		$("dd").eq(0).find('span').addClass('line-dot-on i i-duihao1');
	};

	//公共配置纵向导航线
	var _createPubConfigNavLine = function(configTypeList){
		var ddArr = [];
		var len = configTypeList;
		for(var i = 0;i<len.length;i++){
			var ddHtml;
			var parm = len[i];
			ddHtml = '<dd class="time-line-dd time-line-point" data-dit="' + parm.optCode + '">'
						+ '<span class="time-line-dot"><em></em></span>'
						+ '<a data-dit="' + parm.optCode
						+ '" class="time-line-a">'+ parm.optName
						+ '</a>' + '</dd>';
			ddArr.push(ddHtml);
		}

		$(".time-line-line").css({"height":(len.length-1)*50});
		$(".time-line-line").css("top","79px");

		$('.time-line-dl').html(ddArr.join(''));
		$('.time-line-bg').delegate('.time-line-point', 'click', function(evt) {
			$('.time-line-bg').find('.time-line-point-select').removeClass('time-line-point-select');
			$(this).addClass('time-line-point-select');
			$('.time-line-bg').find("*").removeClass('line-a-on').removeClass('line-dot-on i i-duihao1');
			$(this).find('a').addClass('line-a-on');
			$(this).find('span').addClass('line-dot-on i i-duihao1');
			//产品个性化配置的类型基础、核算、费用、影像、模板、流程、表单、风控模型等
			var configType = $(this).data('dit');
			MfKindPubConfig.getPubConfigByType(configType);
		});
		//默认选中第一个节点
		$("dd").eq(0).click();//默认触发获取第一项配置信息
		$("dd").eq(0).addClass('time-line-point-select');
		$("dd").eq(0).find('a').addClass('line-a-on');
		$("dd").eq(0).find('span').addClass('line-dot-on i i-duihao1');
	};

	return{
		createKindConfigNavLine:_createKindConfigNavLine,
		createPubConfigNavLine:_createPubConfigNavLine,
		
	};
}(window, jQuery);