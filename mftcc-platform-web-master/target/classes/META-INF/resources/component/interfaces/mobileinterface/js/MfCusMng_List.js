var mfAppCusMng = {	
		//添加客户经理
		toAddCusMng: function(){

			top.openBigForm(webPath+"/sysUser/getListForAppPage?opNoType=1","客户经理列表",function(){
				window.updateTableData();
			}); 
		},
		
};

//删除客户经理
function deleteCusMng(obj,url){
	$.ajax({
		url : url,
		type : "get",
		dataType : "json",
		success : function(data) {
			if (data.flag == "success") {
				LoadingAnimate.stop();
				alert(data.msg,3,function(){
					window.updateTableData();
				});
			} else {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION",data.msg),0);
			}
		},
		error : function() {
			LoadingAnimate.stop();
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});

};