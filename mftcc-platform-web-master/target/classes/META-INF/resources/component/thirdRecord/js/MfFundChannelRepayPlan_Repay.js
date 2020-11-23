;
var MfFundChannelRepayPlan_Repay = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{ 
				updateOnBrowserResize:true 
			},
			autoHideScrollbar: true
		});
	};
	
	//计算实还总额:实还本金+实还利息
	var _countRealRepayPrcpIntstSum = function(){
		var realRepayPrcp = $("input[name=realRepayPrcp]").val().replace(/,/g,'');//实还本金
		var realRepayIntst = $("input[name=realRepayIntst]").val().replace(/,/g,'');//实还利息
		var realRepayPrcpIntstSum = CalcUtil.add(realRepayPrcp,realRepayIntst);
		$("input[name=realRepayPrcpIntstSum]").val(CalcUtil.formatMoney(realRepayPrcpIntstSum,2));
	};
	
	//保存还款
	var _saveMfFundChannelRepay = function (obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						 /*top.flag=true;
						 top.gpsUpdateFlag=true;//表示是否是GPS节点
						 top.gpsDetailInfo = data.gpsDetailInfo;*/
						myclose_click();
					}
					if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					 LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	return{
		init:_init,
		countRealRepayPrcpIntstSum:_countRealRepayPrcpIntstSum,
		saveMfFundChannelRepay:_saveMfFundChannelRepay
	};
}(window,jQuery);