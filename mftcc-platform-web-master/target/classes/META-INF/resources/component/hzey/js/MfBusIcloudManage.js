;
var MfbusIcloudManage = function(){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".list-item",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	var _ajaxIcloudSave =function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
					/*	window.top.alert(data.msg, 3);*/
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	//弹出框
	var _getById = function (obj ,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,null,getList);
	};
	var _toUpdatePage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,null,getList);
	}
	/**
	 * 解除绑定
	 * @param {Object} obj
	 * @param {Object} url
	 */
	var _unBindAjax = function (obj,url) {
	    alert("是否确定解除绑定？",2,function(){
	        $.ajax({
	            type: "get",
	            url: url,
	            success: function(data) {
	                    //$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
	            },
	            error: function() {
	                alert(top.getMessage("FAILED_DELETE"),0);
	            }
	        });
	    });
	}
	var _updateCifInfoAjax = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		$("#ajaxurl").val(url);
		selectCusDialog(getCifInfo,null,null,1);
	} 
	
	var _getIcloudPwdAjax = function(){
		  $.ajax({
	            type: "get",
	            url: webPath+"/mfBusIcloudManage/getIcloudPwdAjax",
	            success: function(data) {
	                    $("input[name='icloudPwd']").val(data.pwd);
	            },
	            error: function() {
	                alert(top.getMessage("FAILED_DELETE"),0);
	            }
	        });
	}

	return {
		init:_init,
		ajaxIcloudSave:_ajaxIcloudSave,
		getById:_getById,
		toUpdatePage:_toUpdatePage,
		unBindAjax:_unBindAjax,
		updateCifInfoAjax:_updateCifInfoAjax,
		getIcloudPwdAjax:_getIcloudPwdAjax
	};
}(window,jQuery);
