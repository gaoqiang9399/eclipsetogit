$(function(){
	$(".scroll-content").mCustomScrollbar({
		advanced:{
			updateOnContentResize:true
		}
	});
	$.each(cusTableList,function(i,cusTable){
		var itemId = cusTable.itemId;
		var flag = cusTable.attentionFlag;
		if(flag == "1"){//选中
			$("#"+itemId).css("display","block");
			$("#"+itemId+"_no").css("display","none");
		}else{//未选中		
			$("#"+itemId).css("display","none");
			$("#"+itemId+"_no").css("display","block");
			$("#"+itemId+"_no").children().children().children().css("color","#706f6f");
		}
	});
	
	
	
});
function gouxuan(obj,id,item_id,funcType){
	LoadingAnimate.start();
	$("#"+item_id).children().children().children().css("color","");
	$("#"+id).children().children().children().css("color","");
	$("#"+id).css("display","none");
	$("#"+item_id).css("display","block");
	$.ajax({
		url : webPath+"/mfQueryItem/updateReportAjax",
		data : {
			ajaxData : item_id,
			attentionFlag : "0",
			funcType :funcType,			
		},
		type : 'post',
		dataType : 'json',
		beforeSend:function(){  
		},success:function(data){
			LoadingAnimate.stop();
			if(data.flag == "success"){
				top.flag = true;
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		},error:function(data){
			LoadingAnimate.stop(); 
			alert("操作失败！",0);
		}
	});
	
};
function deletegouxuan(obj,item_id,id,funcType){
	LoadingAnimate.start();
	$("#"+item_id).children().children().children().css("color","");
	$("#"+item_id).css("display","none");
	$("#"+id).css("display","block");
	$("#"+id).children().children().children().css("color","#706f6f");
	$.ajax({
		url : webPath+"/mfQueryItem/updateReportAjax",
		data : {
			ajaxData : item_id,
			attentionFlag : "1",
			funcType :funcType,
		},
		type : 'post',
		dataType : 'json',
		beforeSend:function(){  
		},success:function(data){
			LoadingAnimate.stop();
			if(data.flag == "success"){
				top.flag = true;
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		},error:function(data){
			LoadingAnimate.stop(); 
			alert("操作失败！",0);
		}
	});
}
//确认设置
function setConfirm(){
	top.flag=true;
	myclose_click();
}


