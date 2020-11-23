;
var OaArchivesFamily = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindInsertAjax("#family_form");
	};
	var _bindInsertAjax = function(obj) {
		$(".family_insertAjax").bind("click", function(event) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 3);
							top.familyFlag = true;
							top.familyTableHtml = data.familyTableHtml;
							myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();

					}
				});
			}
		});
	};
	// 根据出生年月带出年龄
	var _getAge = function() {
		var returnAge;  
	    var strBirthdayArr=$("input[name='birthday']").val().split("-");  
	    var birthYear = strBirthdayArr[0];  
	    var birthMonth = strBirthdayArr[1];  
	    var birthDay = strBirthdayArr[2];  
	    d = new Date();  
	    var nowYear = d.getFullYear();  
	    var nowMonth = d.getMonth() + 1;  
	    var nowDay = d.getDate();  
	    if(nowYear == birthYear){  
	        returnAge = 0;//同年 则为0岁  
	    }  
	    else{  
	        var ageDiff = nowYear - birthYear ; //年之差  
	        if(ageDiff > 0){  
	            if(nowMonth == birthMonth) {  
	                var dayDiff = nowDay - birthDay;//日之差  
	                if(dayDiff < 0)  
	                {  
	                    returnAge = ageDiff - 1;  
	                }  
	                else  
	                {  
	                    returnAge = ageDiff ;  
	                }  
	            }  
	            else  
	            {  
	                var monthDiff = nowMonth - birthMonth;//月之差  
	                if(monthDiff < 0)  
	                {  
	                    returnAge = ageDiff - 1;  
	                }  
	                else  
	                {  
	                    returnAge = ageDiff ;  
	                }  
	            }  
	        }  
	        else  
	        {  
	            returnAge = "";//返回-1 表示出生日期输入错误 晚于今天  
	        }  
	    }  
		$("input[name='age']").val(returnAge);
	};
	return {
		init : _init,
		getAge : _getAge
	};
}(window, jQuery);