;
var CheckOperable = function(window,$){
	//currOpNo当前登录的操作员编号，dataOpNo当前操作数据的客户经理编号
	var _checkOperable=function(currOpNo,dataOpNo){
		var checkData="";
		$.ajax({
			url:webPath + "/sysUser/checkOperableAjax",
			data:{opNo:currOpNo, dataOpNo:dataOpNo},
			type:'post',
			dataType:'json',
			async: false,
			success:function(data){
				if(data.flag == "success"){
					checkData=data;
				}else if(data.flag == "error"){
					window.top.alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		}); 
		return checkData;
	};
	return{
		checkOperable:_checkOperable,
	};
}(window,jQuery);