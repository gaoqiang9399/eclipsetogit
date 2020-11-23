$(function(){
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

function getHis() {
    // window.location.href = webPath+"/mfPactFiveclass/getListHisPage?fincId="+fincId+"&fiveclassId="+fiveclassId;
    window.location.href = webPath + "/mfPactFiveclass/fiveclassView?fincId=" + fincId;
}

//更新操作
function ajaxUpdate(formObj){
	var flag = submitJsMethod($(formObj).get(0), '');
	if(flag){
		var url = $(formObj).attr("action")+ "?&fincId="+fincId;
		var dataForm = JSON.stringify($(formObj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data:{ajaxData:dataForm,"pactId":pactId},
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
				loadingAnimate.stop();
				alert("更新操作发生异常",0);
			}
		});
	}
};

function selectClass(){
	var select = $("select[name='fiveclass']").val();
	if(allowManualFlag == 0){
		if(fiveclass > select){
			$("select[name='fiveclass']").val(fiveclass);
			alert("请勿向上调整五级分类等级",0);
		}
	}
};
//不显示比当前等级低的五级分类
//$(function(){ 
//	if(allowManualFlag == 0){
//		for ( var i = 1; i < fiveclass; i++) {
//			var item = "select[name='fiveclass'] option[value='" + i + "']";
//			$(item).remove();
//		}
//	}
//});

