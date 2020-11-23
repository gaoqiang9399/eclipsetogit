;
var MfCusCustomer_InputUpdate=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
		//dealWithCusType("0");//只加普通客户的类型
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
		/*var appNum = ajaxData.appNum;
		$('[name=cusType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			items:ajaxData.cusType
		});
		$('[name=cusSubType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			items:ajaxData.cusSubType
		});*/
		$("[name=cusType]").parents("td").find(".pops-select").remove();
		$("[name=cusSubType]").parents("td").find(".pops-select").remove();
		$("[name=recommenderName]").removeAttr("onclick");
		$("[name=cusMngName]").removeAttr("onclick");
		$("[name=beginDate]").removeAttr("onclick");
		$("[name=endDate]").removeAttr("onclick");
		$("[name=idNum]").removeAttr("onblur");
		$(".input-group-addon").find("i").removeAttr("onclick");
	};
	//根据客户证件号码带出客户信息
	var _getCusInfoByIdNum=function(){
		var idNum=$("[name=idNum]").val();
		var formId=$("[name=formId]").val();
		if(idNum==""){
			return;
		}
		$.ajax({
			url:webPath+'/mfCusCustomer/getCusInfoByIdNumAjax',
			data:{idNum:idNum,formId:formId},
			dataType:'json',
			async:false,
			type:'POST',
			success:function(data){
				if(data.flag="success"){
					//客户已存在，带出客户信息
					if(data.existFlag=="1"){
						$("input[name=idNum]").val("");
						var cusInfo=data.cusInfo;
						var cusBaseType=cusInfo.cusBaseType;
						var msg=cusInfo.opName+"(客户经理手机号:"+data.cusMngPhone+")"+"登记的"+cusInfo.cusName+"的证件号码"
						alert(top.getMessage("CONFIRM_UPDATE_CUSTOMER",msg),2,function(){
							var tmpUrl=webPath+"/mfCusCustomer/inputUpdate?idNum="+idNum;
							document.location.href=tmpUrl;
						});
					}
				}else if(data.flag="error"){
					window.top.alert(data.msg,0);
				}
			}
		});
	};
	//切换信息来源时，清空推荐者
	var _cleanRecommenderName = function () {
		$("input[name=recommenderName]").val("");
    }

	//输入证件号码带出客户信息后，完善客户信息
	var _perfectCusInfo=function(obj){
		var relationId = "";
		var saveType="update";
		var cusNo = $("input[name=cusNo]").val();
		relationId = cusNo;
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var checkData=CheckOperable.checkOperable("",cusMngNo);
			if(checkData.checkFlag=="0"){
				window.top.alert(checkData.msg,0);
				return;
			}
			cusNo = $("input[name=cusNo]").val();
			var unValMsg = {};
			var checkFlag = "",columnTitle = "";
			var unValCheckResult = null;
			var cusType = $("[name=cusType]").val();
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
					unValMsg.telResultMsg = unValCheckResult.split("&")[1];
					window.top.alert(unValMsg.telResultMsg,0);
					return false;
				}else{
					_saveCusInfoForPerfect(obj,cusNo);
				}
			}else{
				_saveCusInfoForPerfect(obj,cusNo);
			}
		}
	};
	//完善资料保存
	var _saveCusInfoForPerfect=function(obj,cusNo){
		var url=webPath+"/mfCusCustomer/updateForBusAjax";
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data:{ajaxData:dataParam,cusNo:cusNo},
			type:'post',
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					var cusNo = data.cusNo;
					var cusType=data.cysType;
					var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId=&cusType="+cusType;
					$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
				}else{
					LoadingAnimate.stop();
					window.top.alert(data.msg,1);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		}); 
	};
	//跳转客户详情页面
	var _toCusDetailInfo=function(){
		var checkData=CheckOperable.checkOperable("",cusMngNo);
		if(checkData.checkFlag=="0"){
			window.top.alert(checkData.msg,0);
			return;
		}
		var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId=&cusType="+cusType;
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
        myclose_click();
	};
	//点击取消，返回列表页面
	var _cancelInsert=function(){
		window.location.href=webPath+"/mfCusCustomer/getEntListPage?entranceNo=8";
        myclose_click();
	};
	return{
		init:_init,
		getCusInfoByIdNum:_getCusInfoByIdNum,
		perfectCusInfo:_perfectCusInfo,
		saveCusInfoForPerfect:_saveCusInfoForPerfect,
		cancelInsert:_cancelInsert,
		toCusDetailInfo:_toCusDetailInfo,
		cleanRecommenderName:_cleanRecommenderName
	}
}(window,jQuery);