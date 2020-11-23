
function init(saveType){
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			updateOnContentResize : true
		}
	});
	if(saveType=="insert"){
		getIdType();
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
		var cusTypeOption = $("select[name=cusType]").find("option:selected");
		$("select[name=cusType]").empty().append(cusTypeOption);;
	}
	//取消手机号码的格式验证
	/*$("input[name=contactsTel]").attr("alt","mobile");*/
  
   
		
	var optionStr = '';
	$.each(kindList,function(i,obj){
		optionStr = optionStr + "<option value='"+obj.kindNo+"'>"+obj.kindName+"</option>";
	});
	$("select[name=kindNo]").html(optionStr);
	$("input[name='kindName']").val($("select[name=kindNo]").find("option:selected").text());
	
}

function showcheckinfo(obj){
	var cusName = $(obj).val();
	if(cusName!=""){
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
	idTypeChange();
}
//证件类型变化是修改证件号码验证规则
function idTypeChange(){
/*	var idType = $("[name=idType]").val();
	if(idType=="A"){
		$("input[name=idNum]").attr("alt","organ");
	}else if(idType=="B"){
		$("input[name=idNum]").attr("alt","credit");
	}else if(idType=="C"){
		$("input[name=idNum]").attr("alt","licence");
	}
	$("input[name=idNum]").val("");*/
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
		if(mobilemsg!=""||postalCodemsg!=""){
			return false;
		}	
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		cusNo = $("input[name=cusNo]").val();
		var kindNo = $("select[name=kindNo]").val();
		if(cusNo!="" && saveType == "insert"){//选择已有客户的
			//选择客户的情况下，直接跳转至业务申请页面
			var url=webPath+"/mfBusApply/input?query='0'&kindNo="+kindNo+"&cusNo="+cusNo;
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
			myclose();
		}else{//新增客户
			var checkFlag = "";
			//证件号码唯一性验证
			var idNum = $("[name=idNum]").val();
			var idNumTitle = "社会信用代码";
			var idNumType = 'B';
			var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusCustomer","01",saveType,"");
			checkFlag = idNumResult.split("&")[0];
			idNumResult = idNumResult.split("&")[1];
			if(checkFlag == "1"){
				window.top.alert(idNumResult+"无法保存请检查信息的正确性",2
					//手机号码唯一性验证
					/*var unVal = $("input[name=contactsTel]").val();
					var column= $("input[name=contactsTel]").attr("title");
					var result = checkUniqueVal(unVal,column,relationId,"MfCusCustomer","01",saveType);
					checkFlag = result.split("&")[0];
					result = result.split("&")[1];
					if(checkFlag == "1"){
						window.top.alert(result,2,function(){
							saveForBus(obj,saveType);
						});
					}else{
						saveForBus(obj,saveType);
					}*/
				);
				return false;
			}else{
				//手机号码唯一性验证
				var unVal = $("input[name=contactsTel]").val();
				var column= $("input[name=contactsTel]").attr("title");
				var result = checkUniqueVal(unVal,column,relationId,"MfCusCustomer","20",saveType,"");
				checkFlag = result.split("&")[0];
				result = result.split("&")[1];
				if(checkFlag == "1"){
					window.top.alert(result+"是否继续保存",2,function(){
						saveForBus(obj,saveType);
					});
				}else{
					saveForBus(obj,saveType);
				}
			}
		}
		
	}
}
function  saveEval(obj){
	var url = $(obj).attr("action");
	var dataParam = JSON.stringify($(obj).serializeArray()); 
	LoadingAnimate.start();
	$.ajax({
		url:url,
		data:{ajaxData:dataParam},
		type:'post',
		dataType:'json',
		success:function(data){
			myclose();
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	}); 
}

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
		data:{ajaxData:dataParam},
		type:'post',
		dataType:'json',
		success:function(data){
			LoadingAnimate.stop();
			if(data.flag == "success"){
				if(saveType=="insert"){
				  var cusNo = data.cusNo;
				  var cusType=data.cysType;
				  var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId=&cusType="+cusType;
				  $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
				  myclose();
				}else if(saveType=="update"){
					top.updateFlag = true;
					top.cusName = data.mfCusCustomer.cusName;
					top.contactsName = data.mfCusCustomer.contactsName;
					top.contactsTel = data.mfCusCustomer.contactsTel;
					top.idNum = data.mfCusCustomer.idNum;
					top.postalCode = data.postalCode;
					top.htmlStr = data.htmlStr;
					top.htmlStrFlag = data.htmlStrFlag;
					top.commAddress = data.mfCusCustomer.commAddress;
					window.top.alert(data.msg,1);
					myclose_click();
				}
			}else{
				LoadingAnimate.stop();
				window.top.alert(data.msg,1);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	}); 
}


function cancelInsert(){
 //	window.location.herf = webPath+"/mfBusApply/getListPage";
 	window.location.href=webPath+"/mfBusApply/getListPage";
}
function selectAreaCallBack(areaInfo){
	$("input[name=commAddress]").val(areaInfo.disName);
};

function checkinfo(event){
	var cusNameVal = $("input[name=cusName]").val();
	cusNameVal = encodeURIComponent(encodeURIComponent(cusNameVal));
	if(cusNameVal.length>0){
		window.top.alert(top.getMessage("CONFIRM_VERIFICATION_COST",{"cost":"1.5"}),2,function(){	
			LoadingAnimate.start();
			$.ajax({
				type:'get',
				dataType : 'json',
				url:webPath+"/mfCusCorpBaseInfo/checkAjax?cusName="+cusNameVal,
				success:function(data){	
					LoadingAnimate.stop();
					$("input[name=legalRepresName]").val(data.fName);
				 	$("input[name=ext2]").val(data.cusNo);
				 	$("input[name=registeredCapital]").val(data.registerCap);
				 	var opFrom = data.opFrom.substring(0,4)+"-"+data.opFrom.substring(4,6)+"-"+data.opFrom.substring(6,8);
				 	var opTo = data.opTo.substring(0,4)+"-"+data.opTo.substring(4,6)+"-"+data.opTo.substring(6,8);
				 	$("input[name=beginDate]").val(opFrom);
				 	$("input[name=endDate]").val(opTo); 
				 	$("input[name=idNum]").val(data.regNo); 
				},error:function(){
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		})
	}else{
		window.top.alert("请先输入企业名称",0);
	}
		
};
function checkinfo1(){
	LoadingAnimate.start();
	$.ajax({
		type:'get',
		url:webPath+"/mfCusCorpBaseInfo/checkAjax?cusNo="+cusNo,
		success:function(data){	
			LoadingAnimate.stop();
			$("input[name=legalRepresName]").val(data.fName);
		 	$("input[name=registeredCapital]").val(data.registerCap);
		 	var opFrom = data.opFrom.substring(0,4)+"-"+data.opFrom.substring(4,6)+"-"+data.opFrom.substring(6,8);
		 	var opTo = data.opTo.substring(0,4)+"-"+data.opTo.substring(4,6)+"-"+data.opTo.substring(6,8);
		 	$("input[name=beginDate]").val(opFrom);
		 	$("input[name=endDate]").val(opTo); 
		 	$("input[name=idNum]").val(data.regNo);
			$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
		},error:function(){
			LoadingAnimate.stop();
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
}