var repayAll = function(window,$){
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
		$('#date1').click(function(){
			fPopUpCalendarDlg();
		});
		$('#date2').click(function(){
			fPopUpCalendarDlg();
		});
	}
	var submit_onclick = function(){
		var opNo = $('#opno').val();
		var date1 = $('#date1').val();
		var date2 = $('#date2').val();
		var date11 = date1.substring(0,4)+date1.substring(5,7)+date1.substring(8,10);
		var date22 = date2.substring(0,4)+date2.substring(5,7)+date2.substring(8,10);
		var flagA = '0';
		if(opNo!=null&&opNo!=""){
			flagA = '1';
		}
		var flagB = '0';
		if(date11!=null&&date11!=""){
			flagB = '1';
		}
		top.createShowDialog("/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=9e8526aa820e46a918f9528f2cd5585e&opno="+opNo+"&date1="+date11+"&date2="+date22+"&flagA="+flagA+"&flagB="+flagB,"还款情况表",'85','85');
	}
	var submit_reset = function(){
		$('#opname').val("");
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