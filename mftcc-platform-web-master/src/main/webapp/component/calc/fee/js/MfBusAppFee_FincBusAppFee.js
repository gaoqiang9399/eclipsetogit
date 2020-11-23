;
var MfBusAppFee_FincBusAppFee = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_getFincBusFeePlanAjax();
	};
	//生成费用计划
	var _ajaxInsertThis = function(){
		var datas = [];
		$.each($("#fincBusAppFeeList").find("tbody tr"),function(index) {
			var entity = {};
			$thisTr = $(this);
			entity.id = $thisTr.find("input[name=id]").val();
			entity.standard = $thisTr.find("input[name=standard]").val();
			entity.itemNo = $thisTr.find("input[name=itemNo]").val();
			entity.feeType = $thisTr.find("select[name=feeType]").val();
			entity.takeType = $thisTr.find("select[name=takeType]").val();
			entity.rateScale = $thisTr.find("input[name=rateScale]").val().replace(/,/g, "");
			entity.standard = $thisTr.find("input[name=standard]").val();
			entity.itemType = $thisTr.find("input[name=itemType]").val();
			entity.feeId = $thisTr.find("input[name=feeId]").val();
			entity.optPower = $thisTr.find("input[name=optPower]").val();
			entity.feeCollectTime = $thisTr.find("input[name=feeCollectTime]").val();
			datas.push(entity);
		});
		$.ajax({
			url:webPath+"/mfBusAppFee/doFincFeePlanAjax",
			data:{
				appId:appId,
				fincId:fincId,
				ajaxDataList : JSON.stringify(datas)
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					_getFincBusFeePlanAjax();
				}else{
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};
	
	var _initFincBusFeeCollect = function(){
		$("#fincBusAppFeeList").find("tbody tr").each(function() {
			var trObj = $(this);
			_setFeeMain(trObj);
		});
		$('#fincBusAppFeeList').on('change', 'select[name=feeMainNo]', function(){
			var trObj = $(this).parents('tr');
			var feeMainNoObj = trObj.find('select[name=feeMainNo]');
			var feeMainNo = feeMainNoObj.val();
			//alert(feeMainNoObj.options[feeMainNo].text);
			//trObj.find('input[name=feeMainName]').val(feeMainNoObj.text());
			_setfeeAccount(trObj,feeMainNoObj.val());
		});
		
		$('#fincBusAppFeeList').on('blur', 'input[name=collectFeeAmt]', function(){
			var trObj = $(this).parents('tr');
			var feeCollAmt = $(this).val();
			var deductFeeAmt = trObj.find("input[name=deductFeeAmt]").val();
			var amtBal = trObj.find(".ext3").html();
			if(''!=feeCollAmt&&''!=deductFeeAmt){
				feeCollAmt = Number(feeCollAmt.replace(/,/g,""));
				deductFeeAmt = Number(deductFeeAmt.replace(/,/g,""));
				amtBal = Number(amtBal.replace(/,/g,""));
				if(deductFeeAmt<0){
					deductFeeAmt = 0;
				}
				var feeAmtSum = feeCollAmt+deductFeeAmt;
				if(parseFloat(amtBal)<parseFloat(feeAmtSum)){
					trObj.find("input[name=collectFeeAmt]").val(amtBal);
					alert("收费金额和减免金额合计值不能大于费用余额");
				}else{
					
				}
			}
		});
		$('#fincBusAppFeeList').on('blur', 'input[name=deductFeeAmt]', function(){
			var trObj = $(this).parents('tr');
			var feeCollAmt = trObj.find("input[name=collectFeeAmt]").val();
			var deductFeeAmt = $(this).val();
			var amtBal = trObj.find(".ext3").html();
			if(''!=feeCollAmt&&''!=deductFeeAmt){
				feeCollAmt = Number(feeCollAmt.replace(/,/g,""));
				deductFeeAmt = Number(deductFeeAmt.replace(/,/g,""));
				amtBal = Number(amtBal.replace(/,/g,""));
				trObj.find("input[name=deductFeeAmt]").val(deductFeeAmt);
				var feeAmtSum = feeCollAmt+deductFeeAmt;
				if(parseFloat(amtBal)<parseFloat(feeAmtSum)){
					trObj.find("input[name=deductFeeAmt]").val(0);
					alert("收费金额和减免金额合计值不能大于费用余额");
				}
			}
		});
	};
	
	
	var _setFeeMain =  function(trObj){
		$.ajax({
	 		type:"post",
	 		url:webPath+"/mfCusAssureCompany/getAssureData",
	 		data:{},
	 		async: false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
			},success:function(data){
				if(data.flag=="success"){
					var mfCusAssureCompanyList = data.mfCusAssureCompanyList;
					$.each(mfCusAssureCompanyList,function(i,obj){
						trObj.find("select[name=feeMainNo]").append("<option value='"+obj.assureCompanyId+"'>"+obj.assureCompanyName+"</option>");
					});
				}
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
	 		}
	 	});
	};
	
	var _setfeeAccount = function (trObj,feeMainNo){
		$.ajax({
	 		type:"post",
	 		url:webPath+"/mfCusBankAccManage/getBankAccData",
	 		data:{cusNo:feeMainNo},//,useType:"7"
	 		async: false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
			},success:function(data){
				if(data.flag=="success"){
					var mfCusBankAccManageList = data.mfCusBankAccManageList;
					trObj.find("select[name=feeAccountId]").empty();
					trObj.find("select[name=feeAccountId]").append("<option value=''></option>");
					$.each(mfCusBankAccManageList,function(i,obj){
						trObj.find("select[name=feeAccountId]").append("<option value='"+obj.id+"'>"+obj.accountNo+"</option>");
					});
				}
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
	 		}
	 	});
	};
	
	//生成费用计划
	var _ajaxInsertFincBusFeeCollect = function(){
		var datas = [];
		$.each($("#fincBusAppFeeList").find("tbody tr"),function(index) {
			var entity = {};
			$thisTr = $(this);
			var feeCollAmt = $thisTr.find("input[name=collectFeeAmt]").val();
			var deductFeeAmt = $thisTr.find("input[name=deductFeeAmt]").val();
			var amtBal = $thisTr.find(".ext3").html();
			feeCollAmt = Number(feeCollAmt.replace(/,/g,""));
			deductFeeAmt = Number(deductFeeAmt.replace(/,/g,""));
			amtBal = Number(amtBal.replace(/,/g,""));
			var feeAmtSum = feeCollAmt+deductFeeAmt;
			if(parseFloat(amtBal)<parseFloat(feeAmtSum)){
				alert("收费金额和减免金额合计值不能大于费用余额");
				return false;
			}
			
			entity.id = $thisTr.find("input[name=id]").val();
			entity.itemNo = $thisTr.find("input[name=itemNo]").val();
			entity.collectFeeAmt = $thisTr.find("input[name=collectFeeAmt]").val().replace(/,/g, "");//收取费用金额
			entity.deductFeeAmt = $thisTr.find("input[name=deductFeeAmt]").val().replace(/,/g, "");//减免金额
			entity.repayDate = $thisTr.find("input[name=repayDate]").val().replace(/-/g, "");//收费日期
			entity.receivableFeeAmt = $thisTr.find("input[name=receivableFeeAmt]").val();//应收费用金额
			entity.feeMainNo = $thisTr.find("select[name=feeMainNo]").val();
			entity.feeMainName = $thisTr.find("input[name=feeMainName]").val();
			entity.feeAccountId = $thisTr.find("select[name=feeAccountId]").val();
			entity.feeVoucherNo = $thisTr.find("input[name=feeVoucherNo]").val();
			entity.planId = $thisTr.find("input[name=planId]").val();
			datas.push(entity);
		});
		$.ajax({
			url:webPath+"/mfBusAppFee/doFincBusFeeCollectAjax",
			data:{
				appId:appId,
				fincId:fincId,
				ajaxDataList : JSON.stringify(datas)
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					myclose_click();
				}else{
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};
	
	//生成费用计划
	var _getFincBusFeePlanAjax = function(){
		$.ajax({
			url:webPath+"/mfBusFeePlan/getMfBusFeePlanListAjax",
			data:{
				fincId:fincId,
				tableId:"tablebusFeePlanList"
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("#feePlanList").html(data.htmlStr);
					if(!data.outFlag){
						$("#fincFeePlanTrial_button").attr("disabled", true);// 拒绝业务
						$("#fincFeePlanTrial_button").css("background", "rgb(122,122,122)");// 拒绝业务
					}
					/*
*/				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};
	var _perfectBusFeePlan = function(obj,url){
		top.saveFlag=false;
		top.mfBusFeePlan="";
		top.feeAccountNo = "";
		top.openBigForm(url, "完善费用信息", function(){
			if(top.saveFlag){
				$(obj).parents("tr").find(".feeMainName").html(top.mfBusFeePlan.feeMainName);
				$(obj).parents("tr").find(".feeAccountNo").html(top.feeAccountNo);
				$(obj).parents("tr").find(".feeVoucherNo").html(top.mfBusFeePlan.feeVoucherNo);
			}
		});
	};
	
	return{
		init:_init,
		ajaxInsertThis:_ajaxInsertThis,
		initFincBusFeeCollect:_initFincBusFeeCollect,
		ajaxInsertFincBusFeeCollect:_ajaxInsertFincBusFeeCollect,
		perfectBusFeePlan:_perfectBusFeePlan
	};
}(window,jQuery);