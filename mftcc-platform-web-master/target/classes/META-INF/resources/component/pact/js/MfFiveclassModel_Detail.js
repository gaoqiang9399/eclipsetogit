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
});
//选择客户类型
function selectCusType(){
	selectCusTypeMutiDialog(callBackCusTypeMutiDialog, $("input[name=cusType]").val());
};
//选择客户类型回调函数
function callBackCusTypeMutiDialog(cusType){
	$("input[name=cusType]").val("");
	$("input[name=cusTypeNo]").val("");
	$("input[name=cusType]").val(cusType.cusTypeDes);
	$("input[name=cusTypeNo]").val(cusType.cusTypeNo);
};

//关闭
function myback(){
	window.location.href = webPath+"/mfFiveclassModel/getListPage";
};

//选择担保类型
function selectVouType(){
	selectVouTypeMutiDialog(callBackVouTypeMutiDialog, $("input[name=vouType]").val());
};

//选择担保类型回调函数
function callBackVouTypeMutiDialog(vouType){
	$("input[name=vouType]").val("");
	$("input[name=vouTypeNo]").val("");
	$("input[name=vouType]").val(vouType.vouTypeDes);
	$("input[name=vouTypeNo]").val(vouType.vouTypeNo);
};

function selectVouTypeMutiDialog(callback,vouType){
	dialog({
		id:"vouTypeMutiDialog",
		title:'选择担保类型',
		url: webPath+'/mfFiveclassModel/getVouTypeForMutiSel?optCode='+vouType,
		width:600,
		height:400,
		backdropOpacity:0,
		onshow:function(){
			this.returnValue = null;
		},onclose:function(){
			if(this.returnValue){
				//返回对象的属性:cusTypeNo,cusTypeDes;如果多个，使用@分隔
				if(typeof(callback)== "function"){
					callback(this.returnValue);
				}else{
				}
			}
		}
	}).showModal();
};

//选择还款方式
function selectRepayType(){
	selectRepayTypeMutiDialog(callBackRepayTypeMutiDialog, $("input[name=repayType]").val());
};

//选择还款方式回调函数
function callBackRepayTypeMutiDialog(repayType){
	$("input[name=repayType]").val("");
	$("input[name=repayTypeNo]").val("");
	$("input[name=repayType]").val(repayType.repayTypeDes);
	$("input[name=repayTypeNo]").val(repayType.repayTypeNo);
};

function selectRepayTypeMutiDialog(callback,repayType){
	dialog({
		id:"repayTypeMutiDialog",
		title:'选择还款方式',
		url: webPath+'/mfFiveclassModel/getRepayTypeForMutiSel?optCode='+repayType,
		width:600,
		height:400,
		backdropOpacity:0,
		onshow:function(){
			this.returnValue = null;
		},onclose:function(){
			if(this.returnValue){
				//返回对象的属性:cusTypeNo,cusTypeDes;如果多个，使用@分隔
				if(typeof(callback)== "function"){
					
					callback(this.returnValue);
				}else{
				}
			}
		}
	}).showModal();
};

//选择业务流程回调函数
function getProcessNo(fieldName){
	return $("input[name='"+fieldName+"']").val();
}
function setProcessNo(key,fieldName){
	$("input[name='"+fieldName+"']").val(key);
}

//更新操作
function ajaxUpdate(formObj){
	var approveFlag = $("select[name='approveFlag'] option:selected").val();
	var processName = $("input[name='processName']").val();
	if(approveFlag == "1" && processName == ""){
		window.top.alert(top.getMessage("NOT_FORM_EMPTY", "启用审批，审批流程"),0);
		return false;
	}
	var flag = submitJsMethod($(formObj).get(0), '');
	if(flag){
		var url = $(formObj).attr("action");
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
					window.top.alert(data.msg,1);
					window.location.href = webPath+"/mfFiveclassModel/getListPage";
				}else{
					alert(data.msg,0);
				}
			},
			error:function(data){
				LoadingAnimate.stop();
				alert("更新操作发生异常",0);
			}
		});
	}
};