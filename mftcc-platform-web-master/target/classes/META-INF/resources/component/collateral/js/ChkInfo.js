function init(saveType){
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
};

function saveChkInfo(obj,saveType){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		/*var checkFlag = "";
		//证件号码唯一性验证
		var idNum = $("input[name=idNum]").val();
		var idNumTitle = $("input[name=idNum]").attr("title");
		var idNumType = $("select[name=idType]").val();
		var relationId = $("input[name=shareholderId]").val();
		var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusShareholder",idNumType,saveType);
		checkFlag = idNumResult.split("&")[0];
		idNumResult = idNumResult.split("&")[1];
		if(checkFlag == "1"){
			window.top.alert(idNumResult,2,function(){
				ajaxInsertCusForm(obj);
			});
		}else{*/
		ajaxInsertCusForm(obj);
		//}
	}
};

function updateCallBack(){
	top.addFlag = true;
	
	myclose_click();
};