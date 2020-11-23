;
var OaDebtList = function(window, $) {
	var _init = function () {	
		 _debtInsert();
		 _closeCallBack();
		 _returnInsert();
	};
	
	var _debtInsert = function () {
		$("#debtInsert").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaDebt/input','新增借款',_closeCallBack);
		});
		
	}; 
	var _returnInsert = function () {
		$("#returnInsert").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaDebtReturnHis/input','还款申请',_closeCallBack);
			
		});
	}; 
	
	var _closeCallBack = function (){
    	myclose();
   };
   
   var _ajaxGetById = function (obj ,url){
	   top.addFlag = false;
	   top.openBigForm(url,"借款申请信息", function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
   };
   var _ajaxGetById01 = function (obj ,url){
	   top.addFlag = false;
	   top.openBigForm(url,"借款申请信息", function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
   };
   var _ajaxGetByIdReturn = function (obj ,url){
	   top.addFlag = false;
	   top.openBigForm(url,"还款信息核对", function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
	 };
   
	 var _ajaxTrDeleteOaList = function (obj,url, func_success){
        		window.top.alert("是否确认删除!",2,function(){
        			jQuery.ajax({
        				url:url,
        				type:"POST",
        				dataType:"json",
        				beforeSend:function(){  
        				},success:function(data){
        					if(data.flag == "success"){
        						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
        						//$(obj).parents("tr").remove();
        						//func_success(obj);
        						//updateMyCustomScrollbar.delTrData();
        						window.location.reload();
        					}else if(data.flag == "error"){
        						alertFlag.Alert(data.msg);
        					}
        				},error:function(data){
        					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
        				}
        			});
        			$(obj).parents(".content_form").hide();
        		});

        };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById:_ajaxGetById,
		ajaxGetById01:_ajaxGetById01,
		ajaxGetByIdReturn:_ajaxGetByIdReturn,
		ajaxTrDeleteOaList:_ajaxTrDeleteOaList
		

	};
}(window, jQuery);