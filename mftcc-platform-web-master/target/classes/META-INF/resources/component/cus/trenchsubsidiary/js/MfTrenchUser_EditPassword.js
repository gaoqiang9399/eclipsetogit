;
var MfTrenchUser_EditPassword=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	// ajax验证原密码
	var _checkUserPassword = function(obj) {
		var pwdflag=false; 
		$.ajax({
			url :"MfTrenchUserActionAjax_checkUserPasswordAjax.action",
			type : "POST",
			data : {passwordhash : $("input[name='password']").val()},
			success : function(data) {
				if (data.flag == 'success') {
					$('#password').addClass("hide");
					pwdflag= true;
				} else {
					$('#password').removeClass("hide");
					pwdflag= false;
				}
			}
		});
		return pwdflag;
	};
	var _updatePwdAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
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
				async:false,
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}
	};
	//验证用户账号是否唯一
	var _checkCusAccountUnique=function(obj,type){
		var cusAccount = $(obj).val();
		var cusTel = $(obj).val();
		if(type=='1'){//验证登录账号
			if(cusAccount==null||cusAccount==""){
				return ;
			}
		}else if(type=='2'){//验证手机号码
			if(cusTel==null||cusTel==""){
				return ;
			}
		}
		jQuery.ajax({
			url:webPath+"MfTrenchUserActionAjax_checkUserExistAjax.action",
			data:{cusAccount:cusAccount,cusTel:cusTel,checkType:type},
			type:"post",
			success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("EXIST_INFORMATION_EVAL", data.msg),0);
					if(type=='1'){//验证登录账号
						$("input[name=cusAccount]").val("");
					}else if(type=='2'){//验证手机号码
						$("input[name=cusTel]").val("");
					}
				}
			}
		});
	};
	return{
		init:_init,
		checkCusAccountUnique:_checkCusAccountUnique,
		updatePwdAjax:_updatePwdAjax,
		checkUserPassword:_checkUserPassword
	}
}(window,jQuery)