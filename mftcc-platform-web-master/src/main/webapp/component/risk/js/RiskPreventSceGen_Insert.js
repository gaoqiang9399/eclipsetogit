;
var RiskPreventSceGenInsert = function(window,$){
	
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=kindNoDes]").attr("disabled","true");
	};
	//选择客户类型，多选
	var _selectCusType=function(){
		selectCusTypeMutiDialog(_cusTypeMutiDialogCall, $("input[name=cusType]").val());
	};
	//选择过客户类型回调，给客户类型相关字段赋值
	var _cusTypeMutiDialogCall=function(cusType){
		$("input[name=cusType]").val(cusType.cusTypeNo);
		$("input[name=cusTypeDes]").val(cusType.cusTypeDes);
	};
	
	var _selectKind=function(){
		selectKindByBusModelMutiDialog(_kindMutiDialogCall, $("input[name=kindNo]").val(),$("input[name=busModel]").val());
	};
	var _kindMutiDialogCall=function(sysKind){
		$("input[name=kindName]").val(sysKind.kindName);
		$("input[name=kindNo]").val(sysKind.kindNo);
	};
	var _selectVouType=function(){
		selectVouTypeMutiDialog(_vouTypeMutiDialogCall,$("input[name=vouType]").val());
	};
	var _vouTypeMutiDialogCall=function(vouType){
		$("input[name=vouTypeDes]").val(vouType.vouTypeDes);
		$("input[name=vouType]").val(vouType.vouTypeNo);
	};
	var _selectBusModel=function(){
		selectBusModelMutiDialog(_busModelMutiDialogCall,$("input[name=busModel]").val());
	};
	var _busModelMutiDialogCall=function(busModel){
		$("input[name=busModelDes]").val(busModel.busModelDes);
		$("input[name=busModel]").val(busModel.busModel);
		if(busModel.busModel==""){
			$("input[name=kindNoDes]").attr("disabled","true");
		}else{
			$("input[name=kindNoDes]").removeAttr("disabled");
		}
		$("input[name=kindNoDes]").val("");
		$("input[name=kindNo]").val("");
	};
	var _ajaxInsert=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var genName = $("input[name=genName]").val();
			var cusType = $("input[name=cusType]").val();
			var busModel = $("input[name=busModel]").val();
			var vouType = $("input[name=vouType]").val();
			var kindNo = $("input[name=kindNo]").val();
			var cusTypeDes = $("input[name=cusTypeDes]").val();
			var busModelDes = $("input[name=busModelDes]").val();
			var vouTypeDes = $("input[name=vouTypeDes]").val();
			var kindName = $("input[name=kindName]").val();
			var rulesNo = $("input[name=rulesNo]").val();
			var dimeVal = cusType+"|"+busModel+"|"+vouType+"|"+kindNo;
			var dimeValDes = cusTypeDes+"|"+busModelDes+"|"+vouTypeDes+"|"+kindName;
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {"genName" : genName,"dimeVal" : dimeVal,"rulesNo":rulesNo,"dimeValDes":dimeValDes,"pageStr":pageStr},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
					  	var url=webPath+"/riskPrevent/getListPage?pageStr="+pageStr;
					  	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
					    myclose();
					}else if (data.flag == "exist"){
						alert("该维度组合已经存在,无法保存", 0);
						//myclose();
					}else{
						alert(  top.getMessage("FAILED_SAVE"), 0);
					}
				},error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", ""));
				}
			});
		}
	};
	var _ajaxUpdateThis=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var genName = $("input[name=genName]").val();
			var cusType = $("input[name=cusType]").val();
			var busModel = $("input[name=busModel]").val();
			var vouType = $("input[name=vouType]").val();
			var kindNo = $("input[name=kindNo]").val();
			var rulesNo = $("input[name=rulesNo]").val();
			var cusTypeDes = $("input[name=cusTypeDes]").val();
			var busModelDes = $("input[name=busModelDes]").val();
			var vouTypeDes = $("input[name=vouTypeDes]").val();
			var kindName = $("input[name=kindName]").val();
			var dimeVal = cusType+"|"+busModel+"|"+vouType+"|"+kindNo;
			var dimeValDes = cusTypeDes+"|"+busModelDes+"|"+vouTypeDes+"|"+kindName;
			var genNo = $("input[name=genNo]").val();
			$.ajax({
				url : url,
				data : {"riskPreventSceGen.genName" : genName,"riskPreventSceGen.dimeVal" : dimeVal,"riskPreventSceGen.genNo" : genNo,"riskPreventSceGen.rulesNo" : rulesNo,"riskPreventSceGen.dimeValDes" : dimeValDes},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						//window.top.alert(top.getMessage("SUCCEED_SAVE"), 1);
						 top.updateFlag = true;
						myclose_click();
					}else if (data.flag == "exist"){
						alert("该维度组合已经存在,无法保存", 0);
						//myclose();
					}else{
						alert(  top.getMessage("FAILED_SAVE"), 0);
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", ""));
				}
			});
		}
	};
	var _busModelChange=function(){
		var busModel = $("select[name=busModel]").val();
		$.ajax({
			url:webPath+"/mfSysKind/getByBusModelAjax?busModel="+busModel,
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					var optionHtml = "";
					$.each(data.kindList,function(i,kind){
						optionHtml = optionHtml+ "<option value='"+kind.kindNo+"'>"+kind.kindName+"</option>";
					});
					$("select[name=kindNo]").html(optionHtml);
				}else{
					alert("查询产品信息错误",0);
				}
			},error:function(){
				alert("查询产品信息错误",0);
			}
		});
	};
	return{
		init:_init,
		selectCusType:_selectCusType,
		selectKind:_selectKind,
		selectVouType:_selectVouType,
		selectBusModel:_selectBusModel,
		ajaxInsert:_ajaxInsert,
		ajaxUpdateThis:_ajaxUpdateThis,
	};
}(window,jQuery);