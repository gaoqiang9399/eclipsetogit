$.fn.useFlagRcswitcher =  function(options){
	var reloadColl = function(){
		$spanArr.useFlagRcswitcher(options);
	};
	var falseRcSwitcher =  function($toggler,$checkInput){
		var offOn = $toggler.hasClass("on")?true:false;
		if(offOn){
			$toggler.removeClass("on").addClass("off");
			$toggler.css("transform","translateX(-41px)");
			$checkInput.prop('checked',false);
		}else{
			$toggler.removeClass("off").addClass("on");
			$toggler.css("transform","translateX(-9px)");
			$checkInput.prop('checked',true);
		}
	};
	var setUseFlagRcswitcher = function(){
		if(thName!=undefined){
			$spanArr.each(function(){
				var $span = $(this);
				var $a = $span.find("a");
				if($a!==undefined&&$a.length>0){
					var href = $a.attr("href");
					var text = $a.text();
						var thNameThis = 'useFlag';
						var indexCheckBox = '<input name="'+thNameThis+'" type="checkbox" value="1"/>';
						$span.html(indexCheckBox);
						if(text==onVal){
							$span.find("input[type=checkbox]").prop('checked',true);
						}
						$span.find("input[type=checkbox]").rcSwitcher({
							width: width,
							height: height,
							theme: theme,
							blobOffset: 1,
							onText:onText,
							offText:offText
						}).on("change.rcSwitcher",
								function(e,dataObj,changeType){
							var $toggler = dataObj.components.$toggler;
							var $checkInput = dataObj.$input;
							var thNameVal = $checkInput.is(":checked")?onVal:offVal;
							var parmData = {};
							var url = href.split("?")[0];
							var hrefUrl=href.split("=")[0]+"=";
							if(href.split("?")[1]!==undefined){
								$.each(href.split("?")[1].split("&"), function(index,data) {
									var col = data.split("=")[0];
									var val = data.split("=")[1];
									parmData[col]=val;
								});
							}
							parmData[thNameThis]=thNameVal;
							$.ajax({
								type:"post",
								url:url,
								async:false,
								data:{ajaxData:JSON.stringify(parmData)},
								success:function(data){
									if(data.flag=="success"){
										var aHtml = "<a href='"+hrefUrl+data.id+"' id='aaa'>"+data.useFlag+"</a>";
										$span.html(aHtml);
										reloadColl();
										alert(data.msg,1);
										if(callback!==undefined&&typeof(callback)=="function"){
											callback(this);
										}
									}else if(data.flag=="error"){
										falseRcSwitcher($toggler,$checkInput);
										alert(data.msg,0);
									}
								},error:function(){
									alert("操作失败,请检查链接！",0);
									falseRcSwitcher($toggler,$checkInput);
								}
							});
						});
					
				}else{
					//$.myAlert.Alert("未配置字段链接！");
				}
			});
		}else{
			alert("未配置相应字段！",0);
		}
	};

	var $spanArr =  $(this);
	var thName;
	var width;
	var height;
	var theme;
	var onText;
	var offText;
	var onVal ;
	var offVal ;
	var callback ;
	/*if(options.length >0){
		$.each(options,function(i,oneOption){
			thName = oneOption.name;
			width = oneOption.width!=undefined?oneOption.width:50;
			height= oneOption.height!=undefined?oneOption.height:18;
			theme = oneOption.theme!=undefined?oneOption.theme:'lease';
			onText = oneOption.onText!=undefined?oneOption.onText:'是';
			offText = oneOption.offText!=undefined?oneOption.offText:'否';
			onVal = oneOption.onVal!=undefined?oneOption.onVal:'1';
			offVal = oneOption.offVal!=undefined?oneOption.offVal:'0';
			callback = oneOption.callback;
			setCollRcswitcher();
		});
	}else{*/
		thName = options.name;
		width = options.width!=undefined?options.width:50;
		height= options.height!=undefined?options.height:12;
		theme = options.theme!=undefined?options.theme:'lease';
		onText = options.onText!=undefined?options.onText:'是';
		offText = options.offText!=undefined?options.offText:'否';
		onVal = options.onVal!=undefined?options.onVal:'1';
		offVal = options.offVal!=undefined?options.offVal:'0';
		callback = options.callback;
		setUseFlagRcswitcher();
	//}

};
