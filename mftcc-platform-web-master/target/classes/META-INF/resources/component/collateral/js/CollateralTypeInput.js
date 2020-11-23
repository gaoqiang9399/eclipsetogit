function ajaxUpdateThis(objArgs){
	var flag = submitJsMethod($(objArgs).get(0), '');
	if(flag){
		var url = $(objArgs).attr("action");
		var dataParam = JSON.stringify($(objArgs).serializeArray());
		var obj = eval ("(" + dataParam + ")");
		if(obj[3].value == obj[4].value){
			window.top.alert("详情表单编号不能与新增表单编号重复",0);
			return;
		}
		LoadingAnimate.start();
		jQuery.ajax({
			url:url+"?formId="+formId,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					top.flag="1";
					top.classFirstNo=data.classFirstNo;
					top.classFirstName=data.classFirstName;
					top.classSecondName=data.classSecondName;
					top.classId=data.classId;
					top.vouType=data.vouType;
					top.classModel=data.classModel;
					top.useFlag=data.useFlag;
					window.top.alert(data.msg, 1);
					myclose_click();
				}else if(data.flag == "error"){
					window.top.alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				window.top.alert(data.msg,0);
			}
		});

	}

};

function selectVouTypeDialog(){
	selectVouTypeMutiForPledgeDialog(vouTypeMutiDialogCall, $("input[name=vouType]").val());
};

function vouTypeMutiDialogCall(vouType){
	$("input[name=vouTypeDes]").val(vouType.vouTypeDes);
	$("input[name=vouType]").val(vouType.vouTypeNo);
};

function selectClassModelDialog(){
	selectClassModelMutiForPledgeDialog(classModelMutiDialogCall, $("input[name=classModel]").val());
};

function classModelMutiDialogCall(classModel){
	$("input[name=classModelDes]").val(classModel.classModelDes);
	$("input[name=classModel]").val(classModel.classModelNo);
};