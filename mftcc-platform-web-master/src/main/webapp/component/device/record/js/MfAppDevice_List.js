;
var MfAppDevice_List = function(window,$){
	 //添加设备信息
	 var _applyInsert = function(url){
		 if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
	 	location.href = url;
	 };
	 var _ajaxDelete = function(obj,url){
		 if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
		 LoadingAnimate.start();
		 $.ajax({
				url:url,
				data:{},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg, 1);
						updateTableData();//重新加载列表数据
					}else{
						window.top.alert(data.msg, 0);
					}
				},error:function(data){
					 LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	 };
	 /**
	 * 在return方法中声明公开接口。
	 */
	return {
		applyInsert:_applyInsert,
		ajaxDelete:_ajaxDelete
	};
}(window, jQuery);