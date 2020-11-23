;
var MfOaCounttransInsert = function(window, $) {
	var _myInsertAjax = function(dom){
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			//进行数据的空值校验
			var openBandNameValue = $("input[name='openBandName']").val();
			var openAccountNameValue = $("input[name='openAccountName']").val();
			if (openBandNameValue==""){
				window.top.alert(top.getMessage("NOT_FORM_EMPTY","开户行名称"), 0);
				return;
			}
			if(openAccountNameValue==""){
				window.top.alert(top.getMessage("NOT_FORM_EMPTY","开户单位名称"), 0);
				return;
			}
			//获取表单数据
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			//发送ajax请求
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						top.addFlag=true;
						window.top.alert(data.msg, 3);
						/*
						//触发搜索按钮,点击数据
						$("#filter_btn_search").click();
						//重新加载页面数据
						 */
						$("#filter_btn_search").click();
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	var _updateAjax = function(dom){
		window.top.alert("您确定要进行开户操作吗?",2,function(){
			var flag = submitJsMethod($(dom).get(0), '');
			if(flag){
				//获取表单数据
				var url = $(dom).attr("action");
				var dataParam = JSON.stringify($(dom).serializeArray());
				LoadingAnimate.start();
				//发送ajax请求
				$.ajax({
					url:url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop(); 
						if (data.flag == "success") {
							top.addFlag=true;
							window.top.alert(data.msg, 3);
							/*
							//触发搜索按钮,点击数据
							$("#filter_btn_search").click();
							//重新加载页面数据
							 */
							$("#filter_btn_search").click();
							myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop(); 
						alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			}
		});
	}
	
		
		  
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		myInsertAjax:_myInsertAjax,
		updateAjax:_updateAjax
	};	
}(window, jQuery);
	
	
	















