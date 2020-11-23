;
var inputBusCommonForm = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//初始化formid
		var tmpCnt=0;
		$("form").each(function(){
			var tmpId=$(this).attr("id");
			tmpId=tmpId+"_"+tmpCnt;
			$(this).attr("id",tmpId);
			tmpCnt++;
		});
		
		//初始化一些选择组件
		//默认为空
		 $("input[name=vouType]").empty();
		//共同借款人选择组件
		 $('input[name=channelSource]').popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl:webPath+"/mfBusTrench/getChannelAjax",//请求数据URL
				valueElem:"input[name=channelSourceNo]",//真实值选择器
				title: "选择渠道来源",//标题
				labelShow:false,
				tablehead:{//列表显示列配置
					"trenchUid":"渠道编号",
					"trenchName":"渠道名称"
				},
				returnData:{//返回值配置
					disName:"trenchName",//显示值
					value:"trenchUid"//真实值
				}
			});
		//产品选择组件
		$("select[name=kindNo]").val(kindNo);
	    $("select[name=kindNo]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//弹窗模式
				multiple:false,
				changeCallback:changeKindNo//单选
		});
	    $("select[name=cusType]").val(cusType);
	    $('select[name=cusType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:chooseCusType
		});
	    
	    $('select[name=cusSubType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			items:cusSubType
		});
	   
	};
	
	var _insertCommonAjax = function(){
		var flag ;
		var json_data = ""; 
		$.each($("form[id^='commonBusAdd_']"),function(){
		      var dataObj = $(this).serializeArray();
		      var formType = {"name":"formType","value":$(this).attr('formType')};
		      dataObj.push(formType);
			  var dataParam = JSON.stringify(dataObj); 
			  flag=submitJsMethod($(this).get(0), '');
			  if(!flag){
				  return;
			  }
			  json_data = json_data + dataParam +"@";
		});
		if(flag){
			LoadingAnimate.start();
			$.ajax({
				url:webPath+"/mfLoanApply/insertCommonAjax",
				data:{ajaxData:json_data,kindNo:kindNo},
				type:'post',
				dataType:'json',
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,3);
						var url = data.url;
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						top.flag = true;
					}else{
						alert(top.getMessage("FAILED_SAVE"),0);
					}
				},error:function(){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		}
	};
	var _cancleBack = function(){
		window.location.href=webPath+"/mfBusApply/getListPage";
	};
	
	
	var _showcheckinfo = function(obj){
		var cusName = $(obj).val();
		if(cusName!=""){
			$(obj).parent().find(".block-view").removeAttr("disabled"); 
		}else{
			$(obj).parent().find(".block-view").attr("disabled","disabled"); 
		}
		
	};
	var _checkTerm = function (obj){
		minTerm = $("input[name='minTerm']").val();
		maxTerm = $("input[name='maxTerm']").val();
		var termType = $("input[name='termType']").val();
		var appTerm = $("input[name=term]").val();
		appTerm = appTerm.replace(/,/g, "");
		var title = $("input[name=term]").attr("title").split("(")[0];
		var appTermType = $("[name=termType]").val();
		appTermType = appTermType.replace(/,/g, "");
		var appMinTerm;
		var appMaxTerm;
		//申请期限
		if(minTerm!=null && maxTerm!=null && minTerm!="" && maxTerm!=""&&termType!=null&&termType!=""){				
			minTermNum = new Number(minTerm);
			maxTermNum = new Number(maxTerm);
			var unit = appTermType=="1"?"个月":"天";
			if(appTermType=="1"){//表单填写申请期限为月
				if(termType=="2"){//产品申请期限为日
					minTermNum = (minTerm/30).toFixed();
					maxTermNum = (maxTerm/30).toFixed();
				}
			}
			if(appTermType=="2"){//表单填写申请期限为日
				if(termType=="1"){//产品申请期限为月
					minTermNum = (minTerm*30).toFixed();
					maxTermNum = (maxTerm*30).toFixed();
				}
			}
			appMinTerm = minTermNum + unit;
			appMaxTerm = maxTermNum + unit;
			$("input[name=term]").attr("placeholder",appMinTerm+"-"+appMaxTerm);
			if(parseFloat(appTerm)<parseFloat(minTermNum)||parseFloat(appTerm)>parseFloat(maxTermNum)){
				$("input[name=term]").val("");
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":title,"value1":appMinTerm,"value2":appMaxTerm}),0);
			}
		}
	};
	var _checkByKindInfo = function(obj){
		var name = $(obj).attr("name");
		var title = $(obj).attr("title").split("(")[0];
		//申请金额
		if(name=="appAmt"){
			maxAmt = $("input[name='maxAmt']").val().replace(/,/g, "");
			minAmt = $("input[name='minAmt']").val().replace(/,/g, "");
			
			if(maxAmt!=null && minAmt!=null && maxAmt!="" && minAmt!=""){
				maxAmtNum = new Number(maxAmt);
				minAmtNum = new Number(minAmt); 			
				var appAmt = $(obj).val();
				appAmt = appAmt.replace(/,/g, "");
				if(parseFloat(appAmt)<parseFloat(minAmtNum)||parseFloat(appAmt)>parseFloat(maxAmtNum)){//判断申请金额是否在产品设置范围内
					$(obj).val(null);
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":title,"value1":_fmoney(minAmtNum,2),"value2":_fmoney(maxAmtNum,2)}),0);
				}
			}			
		}
	};
	
	var _checkRate = function (obj){
		var name = $(obj).attr("name");
		var val = $("input[name='"+name+"']").val();
		if(val<0){
			alert("利率必须大于0！",3);
		}
	};
	
	
	//格式化金额
	var _fmoney = function (s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	};
	var _mobilecheck = function (obj){	
		//var phoneNum = obj.val();
		var phoneNum = $(obj).val();
		if(phoneNum!=""){		
			mobilemsg = isMobile(obj);
			 if(mobilemsg==""){
				 $(obj).removeClass("Required");
				 $(obj).parent().find(".error").remove();
			 }else{
				 func_uior_addTips(obj,mobilemsg);
				 $("input[name=mobile]").val("");
			 }		
		}
		return mobilemsg;
	};
	var _controlMax = function(){
		var registeredCapitalVal = $("input[name='registeredCapital']").val();
		if(registeredCapitalVal>="0"){
			
		}else{
			$("input[name='registeredCapital']").val("");
		}
		
	};
	
	var _postalCodecheck = function (obj){
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
	
	var _getForm = function getForm(kind,formId){
		$.ajax({
			url:webPath+"/mfLoanApply/getBusForm",
			data:{kindNo:kindNo},
			type:'post',
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag=="success"){
					$("#"+formId).empty().html(data.htmlStr);
					$("select[name=kindNo]").val(kindNo);
				    $("select[name=kindNo]").popupSelection({
							searchOn:true,//启用搜索
							inline:true,//弹窗模式
							multiple:false,
							changeCallback:changeKindNo//单选
					});
				}else{
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			},error:function(){
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_SERVER"),0);
			}
		});
			
	}
	return {
		init : _init,
		insertCommonAjax :_insertCommonAjax,
		cancleBack :_cancleBack,
		checkByKindInfo :_checkByKindInfo,
		checkRate :_checkRate,
		mobilecheck:_mobilecheck,
		checkTerm:_checkTerm,
		fmoney:_fmoney,
		showcheckinfo:_showcheckinfo,
		controlMax:_controlMax,
		postalCodecheck:_postalCodecheck,
		getForm:_getForm,
	};
}(window, jQuery);
