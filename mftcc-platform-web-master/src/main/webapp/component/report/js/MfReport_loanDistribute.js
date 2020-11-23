var loanDistribute = function(window,$){
	var _init = function(){
		$('#opno').css("display","none");
		$('#opname').click(function(){
			selectUserDialog(getCusMngNameDialog);
		});
		$('#query').click(function(){
			submit_onclick();
		});
		$('#reset').click(function(){
			submit_reset();
		});
	}
	var submit_onclick = function(){
		var opNo = $('#opno').val();
		var way = $('#chartType').val();
		var flagA = '0';
		if(opNo!=null&&opNo!=""){
			flagA = '1';
		}
		switch(way){
		case "danbao" :
			top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=ad11f7c634b25864e63ecadf9ec9fff5&opno="+opNo+"&flagA="+flagA,'贷款分布图','85','85');
			break;
		case "loanUse" :
			top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=397656b85ea3ae74b23a23fbe3183b63&opno="+opNo+"&flagA="+flagA,'贷款分布图','85','85');
			break;
		case "busModel" :
			top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=93bef706c99a2376d7ee66851bde915e&opno="+opNo+"&flagA="+flagA,'贷款分布图','85','85');
			break;
		default:
			break;
		}
	}
	var submit_reset = function(){
		$('#opno').val("");
		$('#opname').val("");
		$('#chartType').val("danbao");
	}
	function getCusMngNameDialog(userInfo){
		$("input[name=opname]").val(userInfo.opName);
		$("input[name=opno]").val(userInfo.opNo);
	};
	return{
		init:_init
	}
}(window,jQuery);