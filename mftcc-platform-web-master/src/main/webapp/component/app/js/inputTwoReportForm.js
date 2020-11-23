;
var busApplyFormInfo = function(window, $) {
	var _init = function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("select[name=isCapital]").val('0');
	    //只展示无需产品号
		bindVouTypeByKindNo($("input[name=vouType]"), kindNo);
		_changeCapital();
		//贷款投向选择组件
	     $("select[name=fincUse]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,
			items:fincUse,
			multiple : false//单选
		 });
	
	};
	var _cancle = function (){
		myclose_click();
	};
	//更新
	
	var _checkIfTrue = function(){
		var pactAmt = $("input[name=appAmt]").val();
		var capitalAmt =  $("input[name=capitalAmt]").val();
		pactAmt = pactAmt.replace(/,/g, "");
		capitalAmt = capitalAmt.replace(/,/g, "");
		if(parseFloat(pactAmt)<parseFloat(capitalAmt)){
			$("input[name=capitalAmt]").val(0.00);
			window.top.alert(top.getMessage("NOT_APPLY_VALUE_BIG",{"info":"业务","field":"配资金额" , "value": "申请金额"}), 3);
		}
	};
	
	var _changeCapital = function(){
		var investMethod = $("select[name=isCapital]").val();
		if(investMethod == '0'){
			$("select[name=isCapital]").parent().parent().next().next().hide();
			$("select[name=isCapital]").parent().parent().next().hide();
		}else{
			$("select[name=isCapital]").parent().parent().next().show();
			$("select[name=isCapital]").parent().parent().next().next().show();
		}
	};
	var _updateApplyForm = function(obj){
		var investMethod = $("select[name=isCapital]").val();
		if(investMethod == '1'){
			_checkIfTrue();
		}
		var flag=submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg,3);
						top.flag = true;
						myclose_click();
					}else{
						alert(data.msg,0);
					}
				},error:function(){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		}
	};

	return {
		init : _init,
		cancle :_cancle,
		updateApplyForm : _updateApplyForm,
		changeCapital:_changeCapital,
		checkIfTrue:_checkIfTrue,
	};
}(window, jQuery);
