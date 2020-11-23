var repayZhuTu = function(window,$){
	var date = new Date();
	var year = date.getFullYear().toString();
	var month = (date.getMonth()+1).toString();
	if(month>0&&month<10){
		month = '0'+month;
	}
	var nowMonth = (year+month).toString();
	var beginMonth = (year+'01').toString();
	var _init = function(){
		 
		$('#date1').val(beginMonth);
		$('#date2').val(nowMonth);
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
		$('#date1').click(function(){
			laydatemonth({max:'nowMonth'});
		});
		$('#date2').click(function(){
			laydatemonth(this);
		});
	}
	var submit_onclick = function(){
		var opNo = $('#opno').val();
		var date1 = $('#date1').val();
		var date2 = $('#date2').val();
		var flagA = '0';
		if(opNo!=null&&opNo!=""){
			flagA = '1';
		}
		top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=35cd387a37309ec027670e4b2240d2fa&opno="+opNo+"&flagA="+flagA+"&date1="+date1+"&date2="+date2,"还款柱状图",'85','85');
	}
	var submit_reset = function(){
		$('#date1').val("");
		$('#date2').val("");
	}
	function getCusMngNameDialog(userInfo){
		$("input[name=opname]").val(userInfo.opName);
		$("input[name=opno]").val(userInfo.opNo);
	}; 
	return{
		init:_init
	}
}(window,jQuery);