//编辑操作
function updateFiveClassModel (obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
};

//新增模型配置
function newModel (obj){
	var url = webPath+"/mfFiveclassModel/input";
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
};

//删除操作
function deleteFiveclassModel (obj,url){
	var modelId = url.split("?")[1].split("=")[1];
	var actionUrl = url.split("?")[0];
	$.ajax({
		url:actionUrl,
		type:"post",
		data:{
			modelId:modelId
		},
		success:function(data){
			if(data.flag == "success"){
				window.top.alert(data.msg, 1);
				window.location.href = webPath+"/mfFiveclassModel/getListPage";
			}else{
				window.top.alert(data.msg, 1);
				//ajaxTrDelete(obj,url);
			}
		},
		error:function(){
			alert("删除表单请求异常");
		}
	});
};