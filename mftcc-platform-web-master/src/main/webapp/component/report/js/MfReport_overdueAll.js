var overdueAll = function(window,$){
	var _init = function(){
		$('#opno').css("display","none");
		$('#kind_no').css("display","none");
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
		$('#kind_name').click(function(){
			selectKindMutiDialog(getKindList);
		});
	}
	var submit_onclick = function(){
		var opNo = $('#opno').val();
		var date1 = $('#date1').val();
		var date2 = $('#date2').val();
		var date11 = date1.substring(0,4)+date1.substring(5,7)+date1.substring(8,10);
		var date22 = date2.substring(0,4)+date2.substring(5,7)+date2.substring(8,10)
		var kindNoShow = $('#kind_no').val();
		var kindNo = kindNoShow.replace(/@/g ,"','");
		var flagA = '0';
		if(opNo!=null&&opNo!=""){
			flagA = '1';
		}
		var flagB = '0';
		if(kindNo!=null&&kindNo!=""){
			flagB = '1';
		}
		var flagC = '0';
		if(date11!=null&&date11!=""){
			flagC = '1';
		}
		top.createShowDialog("/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=ddbd996a0fd8e20e5c8ea732449552a6&opno="+opNo+"&kindno="+kindNo+"&date1="+date11+"&date2="+date22+"&flagA="+flagA+"&flagB="+flagB+"&flagC="+flagC,"逾期统计表",'85','85');
	};
	var submit_reset = function(){
		$('#opname').val("");
		$('#date1').val("");
		$('#date2').val("");
	};
	function getCusMngNameDialog(userInfo){
			$("input[name=opname]").val(userInfo.opName);
			$("input[name=opno]").val(userInfo.opNo);
		}; 
	function getKindList(kind){
		$("input[name=kind_name]").val(kind.kindName);
		$("input[name=kind_no]").val(kind.kindNo);
	};
	return{
		init:_init
	}
}(window,jQuery);