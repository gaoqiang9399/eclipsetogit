;
var cusFund = function(window, $) {
	var _init = function(saveType){
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
		if(saveType=="insert"){
			_getIdType();
			$('select[name=cusType]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false, //单选
				changeCallback:_chooseCusType
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
			var cusTypeOption = $("select[name=cusType]").find("option:selected");
			$("select[name=cusType]").empty().append(cusTypeOption);
			$('select[name=cusType]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false, //单选
				changeCallback:_chooseCusType
			});
		}
	};
	
	var _chooseCusType = function (){
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
			changeCallback:_chooseCusType
		});
	};
	
	var _showForm = function (obj,type){
		$("#type").val(type);
		$("#cusInsertForm").empty().html(obj);
		$("#cusType").val(type);
	};
	
	var showcheckinfo = function (obj){
		var cusName = $(obj).val();
		if(cusName!=""){
			$(obj).parent().find(".block-view").removeAttr("disabled"); 
		}else{
			$(obj).parent().find(".block-view").attr("disabled","disabled"); 
		}
	};
	
	var _mobilecheck = function (obj){
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
	};
	
	var _postalCodecheck = function(obj){
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
	};
	
	var _getIdType = function (){
		_idTypeChange();
	};
	
	var _idTypeChange = function(){
		
	};
	
	var _userNameCallback = function (data){
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
//		makeOptionsJQ(options, data.idType);
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
	};

	var _cusInfoSave = function (obj,saveType){
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
				
				//社会信用代码 证件号码唯一性验证
				var unVal = $("[name=idNum]").val();
				if(cusType.indexOf("2") != 0){  //企业客户
					columnTitle = "社会信用代码";
				} else {
					columnTitle = $("select[name=idType]").find("option:selected").text();
				}
				unValCheckResult = checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","01",saveType,"");
				checkFlag = unValCheckResult.split("&")[0];//idNumResult.split("&")[0];
				if(checkFlag == "1"){
					unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
					window.top.alert(unValMsg.idNumResultMsg,0);
					return false;
				}
				//手机号码唯一性验证
				var $tel =  $("input[name=cusTel]");
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
						/*window.top.alert(telResultMsg+" 是否继续保存?",2,function(){
							saveForBus(obj,saveType);
						});*/
						unValMsg.telResultMsg = unValCheckResult.split("&")[1];
						window.top.alert(unValMsg.telResultMsg,0);
						return false;
					}else{
						_saveForBus(obj,saveType);
					}
				}else{
					_saveForBus(obj,saveType);
				}
				
			}
			
		}
	};
	
	var _saveForBus = function (obj,saveType){
		var url = $(obj).attr("action");
		if(saveType == "update"){
			url = url + "?cusNo="+cusNo;
		}
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data:{ajaxData:dataParam, cusType : $("select[name=cusType]").find("option:selected").val()},
			type:'post',
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					if(saveType=="insert"){
					  myclose();
					}else if(saveType=="update"){
						if(data.cusBaseType=="2"){
							top.cusTel = data.mfCusPersBaseInfo.cusTel;
							top.commAddress = data.mfCusPersBaseInfo.commAddress;
						}else{
							top.postalCode = data.postalCode;
							top.commAddress = data.mfCusCustomer.commAddress;
							top.contactsName = data.mfCusCustomer.contactsName;
							top.contactsTel = data.mfCusCustomer.contactsTel;
						}
						top.updateFlag = true;
						top.cusType = data.cusType;
						top.cusName = data.mfCusCustomer.cusName;				
						top.idNum = data.mfCusCustomer.idNum;	
						top.htmlStr = data.htmlStr;
						top.htmlStrFlag = data.htmlStrFlag;
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
	};
	
	var _cancelInsert = function (){
		window.location.href=webPath+"/mfBusApply/getListPage";
	};
	
	var _selectAreaCallBack = function(areaInfo){
		$("input[name=commAddress]").val(areaInfo.disName);
	};
	
	var controlMax = function (){
		var registeredCapitalVal = $("input[name='registeredCapital']").val();
		if(registeredCapitalVal>="0"){
			
		}else{
			$("input[name='registeredCapital']").val("");
		}
	};
	return {
		init : _init,
		chooseCusType : _chooseCusType,
		showForm : _showForm,
		showcheckinfo :showcheckinfo,
		mobilecheck : _mobilecheck,
		postalCodecheck : _postalCodecheck,
		getIdType : _getIdType,
		idTypeChange : _idTypeChange,
		userNameCallback : _userNameCallback,
		cusInfoSave : _cusInfoSave,
		saveForBus :_saveForBus,
		cancelInsert  :_cancelInsert,
		selectAreaCallBack : _selectAreaCallBack,
		checkinfo:_checkinfo,
		checkinfo1 :_checkinfo1,
		controlMax :controlMax,
		
		
	};
}(window, jQuery);
