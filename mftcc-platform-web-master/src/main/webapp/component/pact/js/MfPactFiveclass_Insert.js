$(function(){
	$("input[name='pactId']").val(pactId);
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			theme:"minimal-dark",
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	var advice = "";
	if(fiveclass == 1){
		advice = "正常";
	}else if(fiveclass == 2){
		advice = "关注";
	}else if(fiveclass == 3){
		advice = "次级";
	}else if(fiveclass == 4){
		advice = "可疑";
	}else if(fiveclass == 5){
		advice = "损失";
	}else{
		advice = "正常";
	}
	$("#adviceinfo").text(advice);
});
//新增操作
function ajaxInsert(formObj){
	var flag = submitJsMethod($(formObj).get(0), '');
	if(flag){
		var url = $(formObj).attr("action")+ pactId + "&fincId="+fincId;
		var dataForm = JSON.stringify($(formObj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data:{ajaxData:dataForm},
			type:"post",
			dataType:"json",
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					window.top.alert(data.msg,3);
					myclose_click();
				}else{
					alert(data.msg,0);
				}
			},
			error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION","新增"),0);
			}
		});
	}
};