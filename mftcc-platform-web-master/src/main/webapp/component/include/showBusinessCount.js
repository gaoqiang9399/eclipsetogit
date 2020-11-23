;
var showBusinessCount=function(window,$1){
	var _init=function(){
		_applyCount();
		_pactCount();
	};
	var _applyCount=function(){
		$.ajax({
			url:webPath+"/mfBusApply/getApplyCountAjax",
			data:{},
			type:'post',
			dataType:'json',
			async:false,
			success:function(data){
				if(data.flag == "success"){
					var applyCount=data.applyCount;
					if(applyCount>0){
						if(applyCount>=100){
							parent.$("#apply_count").text("99");
							parent.$("#apply_count").append("<span class='buss-count-jia' id='count_jia'>+</span>");
							parent.$("#apply_count").attr("style","text-align:left");
						}else{
							parent.$("#apply_count").text(applyCount);
							parent.$("#apply_count").attr("style","width: 22px;");
						}
						parent.$("#apply_count").show();
					}else{
						parent.$("#apply_count").hide();
					}
				}else{
					window.top.alert(data.msg,1);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		}); 
	};
	var _pactCount=function(){
		$.ajax({
			url:webPath+"/mfBusPact/getSignPactCountAjax",
			data:{},
			type:'post',
			dataType:'json',
			async:false,
			success:function(data){
				if(data.flag == "success"){
					var pactCount=data.pactCount;
					if(pactCount>0){
						if(pactCount>=100){
							parent.$("#pact_count").text("99");
							parent.$("#pact_count").append("<span class='buss-count-jia' id='count_jia'>+</span>");
							parent.$("#pact_count").attr("style","text-align:left");
						}else{
							parent.$("#pact_count").text(pactCount);
							parent.$("#pact_count").attr("style","width: 22px;");
						}
						parent.$("#pact_count").show();
					}else{
						parent.$("#pact_count").hide();
					}
				}else{
					window.top.alert(data.msg,1);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	return{
		init:_init,
		applyCount:_applyCount,
		pactCount:_pactCount
	}
}(window,jQuery);