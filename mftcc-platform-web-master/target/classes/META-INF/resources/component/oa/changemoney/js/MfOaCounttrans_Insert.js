;
var MfOaCounttransInsert = function(window, $) {
	
	var _init = function () {
		top.LoadingAnimate.start();
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaCounttrans/findByPageAjax",//列表数据查询的url
	    	tableId:"tablechangemoney0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	        ownHeight : true,
		    callback:function(){
		    	top.LoadingAnimate.stop();
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};
	
	
	
	
	var _myInsertAjax = function(dom){
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
	}
	
	
	var _myInsertDiscussAjax = function(dom){
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
						myclose_task();
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
	
	
	//处理转出账号表单相关数据
	var _getBankByCardNumber01 = function(obj){
		var identifyNumber = obj.value.trim().replace(/\s/g,"");
		$.ajax({
			url:webPath+"/bankIdentify/getByIdAjax",
			data:{identifyNumber:identifyNumber},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name=transferOutBandName]").val(data.bankName);
				}else{
					$("input[name=transferOutBandName]").val("");
				}	
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		};
		
		//处理转入账号相关数据
		var _getBankByCardNumber02 = function(obj){
			var identifyNumber = obj.value.trim().replace(/\s/g,"");
			$.ajax({
				url:webPath+"/bankIdentify/getByIdAjax",
				data:{identifyNumber:identifyNumber},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						$("input[name=transferInBandBignumber]").val(data.bankId);
						$("input[name=transferInBandName]").val(data.bankName);
					}else{
						$("input[name=transferInBandName]").val("");
						$("input[name=transferInBandBignumber]").val("");
					}	
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
			};
		
	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myInsertAjax : _myInsertAjax,
		myInsertDiscussAjax : _myInsertDiscussAjax,
		getBankByCardNumber01 : _getBankByCardNumber01,
		getBankByCardNumber02 : _getBankByCardNumber02
	};
}(window, jQuery);