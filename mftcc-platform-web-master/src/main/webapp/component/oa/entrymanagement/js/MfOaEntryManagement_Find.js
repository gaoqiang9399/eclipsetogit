;
var MfOaEntryManagement_Find = function (window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_bindInsertAjax("#base_form");
	};
	var _bindInsertAjax = function(obj){
		$(".base_insertAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
								window.top.alert(data.msg, 1);
								top.htmlStr = data.htmlStr;
								top.baseFlag = true;
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
	// 设置亲属部门
	var _setRelationBrName = function(sysOrg){
		$("input[name=relationBrName]").val(sysOrg.brName);
		$("input[name=relationBrNo]").val(sysOrg.brNo);
	};
	// 设置员工部门
	var _setSysOrgInfo = function(sysOrg){
		$("input[name=brName]").val(sysOrg.brName);
		$("input[name=brNo]").val(sysOrg.brNo);
	};
	// 根据银行卡号，获取银行
	var _getBankByCardNumber = function(obj){
			var identifyNumber = obj.value.trim().replace(/\s/g,"");
			$.ajax({
				url:webPath+"/bankIdentify/getByIdAjax",
				data:{identifyNumber:identifyNumber},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						BASE.removePlaceholder($("input[name=bank]"));
						$("input[name=bank]").val(data.bankName);
					}else{
						$("input[name=bank]").val("");
					}	
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		};
	
	return {
		init:_init,
		setRelationBrName:_setRelationBrName,
		setSysOrgInfo:_setSysOrgInfo,
		getBankByCardNumber:_getBankByCardNumber,
	};
}(window,jQuery);
