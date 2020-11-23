;
var initRepaymentDoc=function(window,$){
	var _initDoc=function(scNo){
		$.ajax({
			url:webPath+"/mfRepaymentConfig/initRepaymentDocAjax",
			data:{scNo:scNo,fincId:fincId},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					docParm = "relNo="+fincId+"&scNo="+scNo;//查询文档信息的url的参数
					initDocNodes();
				}else{
					alert(top.getMessage("ERROR_DATA_CREDIT","表单文件"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL",""),0);
			}
		});
	//	top.repayId=repayId;
	};
	return{
		initDoc:_initDoc
	}
}(window,jQuery);