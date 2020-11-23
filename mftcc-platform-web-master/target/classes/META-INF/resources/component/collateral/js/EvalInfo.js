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

function saveEvalInfo(obj,saveType){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		ajaxInsertCusForm(obj);
	}
};

function updateCallBack(){
	top.addFlag = true;

	myclose_click();
};