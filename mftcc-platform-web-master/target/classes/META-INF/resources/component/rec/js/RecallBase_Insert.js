;
var ReCallBaseInsert = function(window, $) {
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	}
	var _meilaiPhone = function () {
        window.parent.meiLaiPhone.showPhoneDialog(cusName,cusTel,getNowFormatDate(),"1");
    }
	//催收办理
	var _recallRegist = function (obj) {
		$(".btn-rec").removeClass("active");
		$(obj).addClass("active");
		$("#registDiv").removeClass("hide");
		$("#registDiv").addClass("show");
		$("#registBtn").removeClass("hide");
		$("#registBtn").addClass("show");
		$("#assignDiv").removeClass("show");
		$("#assignDiv").addClass("hide");
		$("#assignBtn").removeClass("show");
		$("#assignBtn").addClass("hide");
	};
	//催收指派
	var _recallAssign = function (obj) {
		$(".btn-rec").removeClass("active");
		$(obj).addClass("active");
		$("#assignDiv").removeClass("hide");
		$("#assignDiv").addClass("show");
		$("#assignBtn").removeClass("hide");
		$("#assignBtn").addClass("show");
		
		$("#registDiv").removeClass("show");
		$("#registDiv").addClass("hide");
		$("#registBtn").removeClass("show");
		$("#registBtn").addClass("hide");
	};
	//保存
	var _doSubmit = function (obj,formType){
        var dataParam = JSON.stringify($(obj).serializeArray());
		var ajaxUrl = $(obj).attr("action");
		jQuery.ajax({
			url : ajaxUrl,
			data : {ajaxData : dataParam},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},success : function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					top.formType = formType;
					top.flag=true;
					myclose_click();
				}else{
					window.top.alert(data.msg,0);
				}
			},
			error : function(data) {
				if(data.flag == "error"){
					window.top.alert("操作失败！",0);
				}
			},complete: function(){
				LoadingAnimate.stop();
			}
		});

	};
	//返回至催收详情页面
	var _returnChoose = function(){
		window.location.href=webPath+'/recallBase/getRecallInfo?pactId='+pactId+'&cusNo='+cusNo;
	}
	
	return {
		init:_init,
		recallRegist:_recallRegist,
		recallAssign:_recallAssign,
		doSubmit:_doSubmit,
		returnChoose:_returnChoose,
        meilaiPhone:_meilaiPhone
	};
}(window, jQuery);
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hour + seperator2 + minute
        + seperator2 + second;
    return currentdate;
}