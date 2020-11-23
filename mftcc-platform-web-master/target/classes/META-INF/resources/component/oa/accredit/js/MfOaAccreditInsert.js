;
var MfOaAccreditInsert = function(window, $) {
	var _init = function () {
		alert();
		
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});


		$("input[name=agentName]").bind("click", function(event) {
			selectUserCustomTitleDialog("选择受托人",_getRecDialog);
		});
		$(".i-fangdajing").bind("click", function(event) {
			selectUserCustomTitleDialog("选择受托人",_getRecDialog);
		});
		$('input[name=accreditFunc]').popupSelection({
			searchOn: true, //启用搜索
			inline: false, //下拉模式
			multiple: true, //单选
			items:funcsData
//			changeCallback:function (obj, elem) {
//				$("input[name=brName]").val(obj.data("text"));
//			}
			});
		};
	var _getRecDialog = function(userInfo){
		var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
		$("input[name=agentNo]").val(users);
		$("input[name=agentName]").val(userInfo.brName);		
	};
	var _checkAccredit=function(dom){
		var formData=JSON.stringify($(dom).serializeArray());
		if(checkTime($("input[name=accreditStart]").val())){//当前时间大于等于选择时间
			top.alert("您选择的托管开始时间已过期，是否使用当前系统时间使此托管立即生效？",2,function(){//确定
				$.ajax({
					url:webPath+'/mfOaAccredit/checkAccreditAjax',
					data : {
						ajaxData : formData
					},
					dataType:'json',
					type:'POST',
					success:function(data){
						if(data.flag=='success'){
							if(data.result.funcFlag=='1'){//托管的功能有冲突
								top.alert(data.result.message,3);
							}else{
								if(data.result.agentFlag=="1"){
									top.alert(data.result.message,3);
								}else{
									ajaxInsertCusForm(dom);
								}
							}
						}else{
							window.top.alert(data.msg);
						}
					}
				})
			});
		}else{//正常，会走定时任务
			$.ajax({
				url:webPath+'/mfOaAccredit/checkAccreditAjax',
				data : {
					ajaxData : formData
				},
				dataType:'json',
				type:'POST',
				success:function(data){
					if(data.flag=='success'){
						if(data.result.funcFlag=='1'){//托管的功能有冲突
							top.alert(data.result.message,3);
						}else{
							if(data.result.agentFlag=="1"){
								top.alert(data.result.message,3);
							}else{
								ajaxInsertCusForm(dom);
							}
						}
					}else{
						window.top.alert(data.msg);
					}
				}
			})
		}
	}
	var _timerCallBack=function(){
//		alert(0)
	}
    var _workGive =function(){
        selectUserCustomTitleDialog("选择受托人",_getRecDialog);
    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		checkAccredit:_checkAccredit,
		timerCallBack:_timerCallBack,
		workGive:_workGive

	};
}(window, jQuery);

