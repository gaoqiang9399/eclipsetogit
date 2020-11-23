;
var MfCusCustomer = function (window, $) {
    // 新增客户短信验证码校验-发送短信验证码
    var _sendVerificationCode = function (thiz) {
        var cusTel = $("input[name='cusTel']").val();
        if (!cusTel) {
            alert(top.getMessage("NOT_FORM_EMPTY", "手机号码"), 3);
            return false;
        }
        $.ajax({
            url: webPath + '/mfCusCustomer/sendVerificationCode',
            data: {cusTel: cusTel},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    var secs = 30;
                    $(thiz).attr("disabled", true);
                    $(thiz).find("#secs").text('(' + secs + ')');
                    var timer_ = window.setInterval(function (a, b, c, d) {// 循环执行，每隔1秒钟执行一次
                        secs = secs - 1;
                        $(thiz).find("#secs").text('(' + secs + ')');
                        if (secs == 0) {
                            $(thiz).attr("disabled", false);
                            $(thiz).find("#secs").text('');
                            clearInterval(timer_);
                        }
                    }, 1000);

                    if($("[name='verificationCode']").length == 0) {
                        var $verificationCode = $(' <input type="text" title="验证码" name="verificationCode" size="7">');
                        $(thiz).after($verificationCode);
                        $(thiz).after('&nbsp;');
                    }

                    alert(top.getMessage("SUCCEED_MSG_SEND"), 1);
                } else {
                    alert(top.getMessage("FAILED_MSG_SEND"), 3);
				}
            }, error: function () {
                alert(top.getMessage("FAILED_MSG_SEND"), 3);
            }
        });
    };

    return {
        sendVerificationCode: _sendVerificationCode
    }
}(window, jQuery);

function init(saveType){
	myCustomScrollbarForForm({
		obj:".scroll-content"
	});
	dealWithCusType("1,2");//只加普通客户的类型
	if(saveType=="insert"){
		getIdType();
		$('select[name=cusType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:chooseCusType
		});

	}else if(saveType=="update"){
		var options = $("select[name=idType]").find("option");
		var isThreeToOne = $("select[name=isThreeToOne]").val();
		var idTypeVal = $("select[name=idType]").val();
		if (isThreeToOne == "0") {
			makeOptionsJQ(options, 'A,C');
			$("select[name=idType]").val(idTypeVal);
		} else if (isThreeToOne == "1") {
			makeOptionsJQ(options, 'B');
			$("select[name=idType]").val(idTypeVal);
		}
		//更新客户登记信息时，不允许更改客户类型，因为这样会导致两个不同的客户类型之间的表单个数不一致，表单的内容也不一致
//		var cusTypeOption = $("select[name=cusType]").find("option:selected");
//		$("select[name=cusType]").empty().append(cusTypeOption);
		var appNum = ajaxData.appNum;
//		if(appNum==0){
			$('input[name=cusType]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false, //单选
				items:ajaxData.cusType,
				changeCallback:cusTypeChange
			});

		if($('[name=cusSubType]').attr("type")!="hidden"){//获取明显类别的属性如果不为hidden显示
			$('input[name=cusSubType]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false, //单选
				items:ajaxData.cusSubType
			});
			}
//		}
	}
}
function chooseCusType(){
	var val = $("input[name=cusType]").val();
	var tmpUrl=webPath+"/mfCusCustomer/input?cusType="+val + "&from=" + from;
	if(typeof insertCusFlag !="undefined" && insertCusFlag=="coborrInsert"){  //新增共借人
		tmpUrl=webPath+"/mfCusCustomer/inputCoborr?cusType="+val + "&from=" + from;
	}
	document.location.href=tmpUrl;
		/*
		var val = $("input[name=cusType]").val().substring(0,1);	//表单客户类型
		var type = $("input[name=cusType]").val();
		var cusType = $("#type").val();//客户类型隐藏域
		if(val=="2"&&cusType=="1"){//企业客户变成个人客户
			$("#type").val("2");
			$("#cusInsertForm").empty().html(htmlStrPerson);
		}else if(val!="2"&&cusType=="2"){//个人客户变成企业客户
			$("#type").val("1");
			$("#cusInsertForm").empty().html(htmlStrPersonNew);
			formRang();
		}
		$("select[name=cusType]").val(type);
		$('select[name=cusType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:chooseCusType
		});
		*/
}


function showForm(obj,type){
	$("#type").val(type);
	$("#cusInsertForm").empty().html(obj);
	$("#cusType").val(type);

}
function showcheckinfo(obj){
	var cusName = $(obj).val();
	if(cusName!=""){
		$(obj).parent().find(".block-view").removeAttr("disabled");
	}else{
		$(obj).parent().find(".block-view").attr("disabled","disabled");
	}

}

function selectCusDialogCus() {
	var cusType = $('input[name=cusType]').val();
    selectCusDialog(showcheckinfo,cusType,null,"5");
}

function showIdentityCheck(obj){
	var cusName = $(obj).val();
	var idNum = $("input[name=idNum]").val();
	if(cusName!="" && idNum !=""){
		$(obj).parent().find(".block-view").removeAttr("disabled");
	}else{
		$(obj).parent().find(".block-view").attr("disabled","disabled");
	}

}
var mobilemsg = '';
function mobilecheck(obj){
	//var phoneNum = obj.val();
	var phoneNum = $(obj).val();
	if(phoneNum!=""){
		mobilemsg = isMobile(obj);
		 if(mobilemsg==""){
			 $(obj).removeClass("Required");
			 $(obj).parent().find(".error").remove();
		 }else{
			 func_uior_addTips(obj,mobilemsg);
		 }
	}
	return mobilemsg;
}
var postalCodemsg = '';
function postalCodecheck(obj){
	var postalCode = $("input[name=postalCode]").val();
	if(postalCode!=""){
		postalCodemsg = isZipcode(obj);
		if(postalCodemsg==""){
			 $(obj).removeClass("Required");
			 $(obj).parent().find(".error").remove();
		 }else{
			 func_uior_addTips(obj,postalCodemsg);
		 }
	}
	return postalCodemsg;
}
//三证合一和证件号码联动
function getIdType(){
//	var options = $("[name=idType]").find("option");
//	var isThreeToOne = $("[name=isThreeToOne]").val();
//	if (isThreeToOne == "0") {
//		makeOptionsJQ(options, 'A,C');
//		$("[name=idType]").val("A");
//	} else if (isThreeToOne == "1") {
//		makeOptionsJQ(options, 'B');
//		$("[name=idType]").val("B");
//	}
//	idTypeChange();
}
//证件类型变化是修改证件号码验证规则
function idTypeChange(obj){
/*	var idType = $("[name=idType]").val();
	if(idType=="A"){
		$("input[name=idNum]").attr("alt","organ");
	}else if(idType=="B"){
		$("input[name=idNum]").attr("alt","credit");
	}else if(idType=="C"){
		$("input[name=idNum]").attr("alt","licence");
	}
	$("input[name=idNum]").val("");*/

	//证件号码格式验证
	var idType = $(obj).val();
	var $idNum = $(obj).parents("table").find("input[name=idNum]")[0];
	if(idType=="0"){//身份证样式格式
		//如果是身份证，添加校验
		changeValidateType($idNum, "idnum");
	}else{
		changeValidateType($idNum, "");
	}
	$(obj).parents("table").find("input[name=idNum]").val("");
	$(obj).parents("table").find("input[name=idNum]").focus();
};

//客户名称字段 下拉选择回调处理
function userNameCallback(data){
	$("input[name=cusNo]").val(data.cusNo);
	$("input[name=cusName]").val(data.cusName);
	$("input[name=idNum]").val(data.idNum);
	$("input[name=contactsTel]").val(data.contactsTel);
	$("input[name=contactsName]").val(data.contactsName);
	$("input[name=commAddress]").val(data.commAddress);
	//$("select[name=isThreeToOne]").val(cusInfo.isThreeToOne);
	makeOptionsJQ($("select[name=isThreeToOne]").find("option"), data.isThreeToOne);
	makeOptionsJQ($("select[name=cusType]").find("option"), data.cusType);
	//$("select[name=idType]").val(cusInfo.idType);
	makeOptionsJQ($("select[name=idType]").find("option"), data.idType);
//	makeOptionsJQ(options, data.idType);
	//$("select[name=cusType]").val(cusInfo.cusType);
	var idType = $("select[name=idType]").val();
	if(idType=="A"){
		$("input[name=idNum]").attr("alt","organ");
	}else if(idType=="B"){
		$("input[name=idNum]").attr("alt","credit");
	}else if(idType=="C"){
		$("input[name=idNum]").attr("alt","licence");
	}
	$("input[type=text]").attr("readonly",true);
}

//表单保存,新增或编辑保存
window.cusInfoSave = function(obj,saveType){
	var relationId = "";
    var cusNo;
	if(saveType == "update"){
		cusNo = $("input[name=cusNo]").val();
		relationId = cusNo;
	}
	if (mobilemsg != "" || postalCodemsg != "") {
		return false;
	}
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		cusNo = $("input[name=cusNo]").val();
		if(cusNo!="" && saveType == "insert"){//选择已有客户的
			//选择客户的情况下，直接跳转至业务申请页面
			var url=webPath+"/mfBusApply/input?query='0'&kindNo="+kindNo+"&cusNo="+cusNo;
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
			myclose();
		}else{//新增客户
			var unValMsg = {};
			var checkFlag = "",columnTitle = "";
			var unValCheckResult = null;
			var cusType = $("[name=cusType]").val();

			var idCardValidateFlag = false;//身份证验证唯一性标志
			//社会信用代码 证件号码唯一性验证
			var unVal = $("[name=idNum]").val();
			if(cusType.indexOf("2") != 0){  //企业客户
				columnTitle = "社会信用代码";
			} else {//个人客户
				columnTitle = $("select[name=idType]").find("option:selected").text();
				if($("select[name=idType]").val()=='0'){//身份证
					idCardValidateFlag = true;
				}
			}
			if(idCardValidateFlag){//个人身份证,只需要在customer表中验证唯一性，不需要在关联表中验证唯一性
				unValCheckResult = doCheckUniqueIdNum(unVal);
				checkFlag = unValCheckResult.split("&")[0];
				if(checkFlag == "1"){
					unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
					window.top.alert(unValMsg.idNumResultMsg,0);
					return false;
				}
			}else{
				unValCheckResult = checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","01",saveType,"");
				checkFlag = unValCheckResult.split("&")[0];//idNumResult.split("&")[0];
				if(checkFlag == "1"){
					unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
					window.top.alert(unValMsg.idNumResultMsg,0);
					return false;
				}
			}
			//手机号码唯一性验证
			var $tel =  $("input[name=cusTel]")
			if(cusType.indexOf("2") != 0){  //企业客户
				$tel = $("input[name=contactsTel]");
			}
			unVal = $tel.val();
			if(unVal !=null && unVal!=""){
				columnTitle= $tel.attr("title");
				unValCheckResult = checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","20",saveType,"");
				checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
				telResultMsg = unValCheckResult.split("&")[1];
				if(checkFlag == "1"){
                        window.top.alert(telResultMsg+" 是否继续保存?",2,function(){
                            saveForBus(obj,saveType);
                        });

				}else{
					saveForBus(obj,saveType);
				}
			}else{
				saveForBus(obj,saveType);
			}

		}

	}
};


//新增保存
function saveForBus(obj,saveType){
	var url = $(obj).attr("action");
	if(saveType == "update"){
		url = url + "?cusNo="+cusNo;
	}
	var dataParam = JSON.stringify($(obj).serializeArray());
	LoadingAnimate.start();
	$.ajax({
		url:url,
		data:{ajaxData:dataParam, cusType : $("select[name=cusType]").find("option:selected").val()},
		success:function(data){
			LoadingAnimate.stop();

			if(data.flag == "success"){
				if(saveType=="insert"){
				  var cusNo = data.cusNo;
				  var cusType=data.cysType;
				  if(typeof(insertCusFlag)!="undefined" && insertCusFlag=="coborrInsert"){//新增共同借款人
					  myclose_click();
				  }else{
					  var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId=&cusType="+cusType;
					  $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
					  myclose();
				  }
				}else if(saveType=="update"){
					if(data.cusBaseType=="2"){
						top.cusTel = data.mfCusPersBaseInfo.cusTel;
						top.commAddress = data.mfCusPersBaseInfo.commAddress;
						top.postalCode = data.mfCusCustomer.postalCode;

					}else{
						top.postalCode = data.postalCode;
						top.commAddress = data.mfCusCustomer.commAddress;
						top.contactsTel = data.mfCusCustomer.contactsTel;
					}
					top.updateFlag = true;
					top.contactsName = data.mfCusCustomer.contactsName;
					top.cusType = data.cusType;
					top.cusName = data.mfCusCustomer.cusName;
					top.idNum = data.mfCusCustomer.idNum;
					top.htmlStr = data.htmlStr;
					top.htmlStrFlag = data.htmlStrFlag;
					if(data.cusSubTypeName!=""&&data.cusSubTypeName!=null){
					top.cusSubTypeName = data.cusSubTypeName;
					}
					top.cusBaseType = data.cusBaseType;
					window.top.alert(data.msg,1);
					myclose_click();
				}
			}else{
				window.top.alert(data.msg,0);
			}
		}
	});
}


function cancelInsert(){
 //	window.location.herf = webPath+"/mfBusApply/getListPage";
	if(from == "MfCusCustomer"){
		window.location.href=webPath+"/mfCusCustomer/getEntListPage";
	}else if(from =="coopAgency"){
        myclose_click();
    }else{
		window.location.href=webPath+"/mfBusApply/getListPage";
	}

}
function selectAreaCallBack(areaInfo){
    $("input[name=careaProvice]").val(areaInfo.disNo);
    $("input[name=careaCounty]").val(areaInfo.disNo);

    $("input[name=careaCity]").val(areaInfo.disName);
    $("input[name=regHomeAddre]").val(areaInfo.disName);
    $("input[name=commAddress]").val(areaInfo.disName);
    $("input[name=postalCode]").val(areaInfo.postalCode);
};

function addCoreCompany(){
	// 判断鼠标是否被拖动过，如果被拖动过不执行跳转
	top.flag=true;
	top.toCoreCompanyFlag = true;
	myclose_click();
};


function checkinfo(event){
	var cusNameVal = $("input[name=cusName]").val();
	var cusNo = $("input[name=cusNo]").val();
	var formId =$("input[name=formId]").val();
    var cusType =$("input[name=cusType]").val();

    if(cusNameVal){
		cusNameVal = encodeURIComponent(encodeURIComponent(cusNameVal));
			LoadingAnimate.start();
			$.ajax({
				type:'get',
				dataType : 'json',
				url:webPath+"/mfCusCorpBaseInfo/checkAjax?cusName="+cusNameVal+"&cusNo="+cusNo+"&nodeNo=netWork",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag== "success"){
						$("input[name=commAddress]").val(data.commAddress);//地址
						$("input[name=idNum]").val(data.socialCreditCode);//社会信用代码
						$("input[name=legalRepresName]").val(data.legalRepresName);//法人姓名
					 	$("input[name=ext2]").val(data.cusNo);//客户号2
					 	$("input[name=registeredCapital]").val(CalcUtil.formatMoney(data.registeredCapital));//注册资本(元)
					 	$("input[name=beginDate]").val(data.beginDate);//营业开始日期
					 	$("input[name=endDate]").val(data.endDate);//营业结束日期
						if(undefined != data.postalCode && null != data.postalCode){
                            $("input[name=postalCode]").val(data.postalCode);//邮政编码
						}else{
                            $("input[name=postalCode]").val("");//邮政编码
						}

					 	alert(data.msg,1);
					}else{
						alert(data.msg,0);
					}
				}
			});
		//})
	}else{
		window.top.alert("请先输入企业名称",0);
	}

};
//个人身份核查
function identityCheck(event){
	var cusNameVal = $("input[name=cusName]").val();
	var cusNo = $("input[name=cusNo]").val();
	var idNum = $("input[name=idNum]").val();
	if(cusNameVal){
		window.top.alert(top.getMessage("CONFIRM_QUERY_COST",{"cost":idCheck}),2,function(){
			LoadingAnimate.start();
			$.ajax({
				type:'get',
				dataType : 'json',
				url:webPath+"/mfCusPersBaseInfo/identityCheck?cusName="+cusNameVal+"&idNum="+idNum+"&thirdFlag=identityCheck",
				success:function(data){
					LoadingAnimate.stop();
					if(data.msgFlag== "S"){
					 	alert(data.msg,3);
					 	$("input[name=recordNo]").val(data.mfThirdServiceRecord.id)
					}else if(data.msgFlag== "N" || data.msgFlag== "D"){
						alert(data.msg,3);
						$("input[name=recordNo]").val(data.mfThirdServiceRecord.id)
					}
					else{
						alert(data.msg,0);
						$("input[name=recordNo]").val(data.mfThirdServiceRecord.id)
					}
				}
			});
		})
	}else{
		window.top.alert("请先输入个人姓名",0);
	}

};
//注册资本不能输入负数
function controlMax(){
	var registeredCapitalVal = $("input[name='registeredCapital']").val();
	if(registeredCapitalVal>="0"){

	}else{
		$("input[name='registeredCapital']").val("");
	}

}
function getCusMngNameDialog(userInfo){
	$("input[name=cusMngName]").val(userInfo.opName);
	$("input[name=cusMngNo]").val(userInfo.opNo);
};


//处理客户类型问题,以后逗号分隔，可以传入多个客户类型
function dealWithCusType(baseTypes){
	$.ajax({
		url:webPath + '/mfBusTrench/getCusTypeNotShowAjax',
		data:'baseTypes='+baseTypes,
		dataType:'json',
		async:false,
		type:'POST',
		success:function(data){
			var notShowCusTypes=data.cusTypeList;
			if(notShowCusTypes!=null){
				for(var i=0;i<notShowCusTypes.length;i++){
					var typeNo=notShowCusTypes[i].typeNo;
					$("[name=cusType]").find("option[value="+typeNo+"]").remove();
				}
			}
		}
	});
}
function cusTypeChange(){
	var cusType = $("input[name=cusType]").val();
	var cusNo = $("input[name=cusNo]").val();
	$.ajax({
		url:webPath+'/mfCusCustomer/getCusSubTypeByCusTypeAjax',
		data:'cusType='+cusType+'&cusNo='+cusNo,
		dataType:'json',
		async:false,
		type:'POST',
		success:function(data){
			if(data.flag=='success'){
				var cusSubType = data.cusSubType;
				$("input[name=popscusSubType]").popupSelection("updateItems",cusSubType);
			}
		}
	});
};
//客户名称验证，系统中有重复的给出提醒
function checkRepeatCusName(obj){
	var cusName = $(obj).val();
	var columnTitle=  $(obj).attr("title");
	var relationId = $("input[name=cusNo]").val();
	var unValCheckResult = checkUniqueValCusName(cusName,"02");
	var checkFlag = unValCheckResult.split("&")[0];
	if(checkFlag == "1"){
		window.top.alert(unValCheckResult.split("&")[1],2,function(){

		},function(){
			$(obj).val("");
		});
		return false;
	}
}

//根据身份证获取年龄性别信息 
function getBirthdayAndSex(){
	var idType = $("[name='idType']").val();
	if("0"==idType){
		StringUtil.setBirthyAndSexByID($("input[name='idNum']"), 'sex', 'brithday','age');
	}
}
//更换信息来源的时候清空推荐信息
 function  emptyRecommender(){
	$("input[name=recommenderName]").val("");
	$("input[name=recommenderPhone]").val("");
	$("input[name=recommenderEmployer]").val("");
}



//选择常住地址回调
function selectCommAddressCallBack(areaInfo){
    $("input[name=commAddress]").val(areaInfo.disName);
};