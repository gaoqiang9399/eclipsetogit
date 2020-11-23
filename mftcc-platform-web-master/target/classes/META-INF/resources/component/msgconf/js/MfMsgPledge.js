var MfMsgPledge=function(window, $){
	function _ajaxUpdateThis(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						/*top.flag="1";
						top.classFirstNo=data.classFirstNo;
						top.classFirstName=data.classFirstName;
						top.classSecondName=data.classSecondName;
						top.classId=data.classId;
						top.vouType=data.vouType;
						top.classModel=data.classModel;
						top.useFlag=data.useFlag;*/
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
	
	function _addMfMsgPledgeModel(){
		var varUsage = $("input[name=varUsage]").val();
		varUsage = varUsage.replace(/\|/g,",");
		//var modelContent = $("textarea[name=modelContent]").val();
		var id = $("input[name=id]").val();
		/*var obj = varUsage.split("|");
		var temParm = "";
		if(obj != undefined && obj != null && obj != ""){
			$.each(obj, function(index,data) {
				if(data != undefined && data != null && data != ""){
					temParm = temParm + ",'"+data+"'";
				}
			});
		}else{
			varUsage = '(\'01\',\'08\')';
		}
		if(temParm != ""){
			temParm = temParm.substring(1,temParm.length);
			varUsage = "(" + temParm + ")";
		}*/
		
		dialog({
			id:"MfMsgPledgeModelDialog",
			title:'押品预警信息模版配置',
			url: webPath + '/mfMsgPledge/inputModel?varUsage='+varUsage+'&id='+id,
			width:1200,
			height:350,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					addMfMsgPledgeModelBack(this.returnValue);
				}
			}
		}).showModal();
	}

	function addMfMsgPledgeModelBack(data){
		$("textarea[name=modelContent]").val(data);
	}
	
	var _initModel = function(){
		var htmlStr = "";
		var divStr = '<div style="margin-top: 20px; margin-bottom: 10px; text-align: left;">';
		var divHtmlStr = "";
		var titleName = "";
		var i = 1;
		$.each(vuListMap,function(keyMap,vuMap){
			if(i != 1){
				divStr = '<div style="margin-top: 0px; margin-bottom: 10px; text-align: left;">';
			}
			divHtmlStr = modelSelectHtml(vuMap);
			/*for(var key in keyMap){
				titleName = keyMap[key];
			}*/
			titleName = keyMap.split("_")[1];
			htmlStr = htmlStr + divStr 
				+ '<span class=\'input-class1\'>' + titleName + '：</span>'
				+ divHtmlStr + '</div>';
			i++;
		});
		
		$('#mfMsgPledgeForm').prepend(htmlStr);
	};
	
	var modelSelectHtml = function(mmvlist){
		var htmlStr = "";
		$.each(mmvlist,function(i,mmv){
			htmlStr = htmlStr + '<input type="button" onclick=\'insertText(this)\' class=\'input-class\' name="'
				+ mmv.varCol + '" value=\'' + mmv.varName + '\'> ';
		});
		return htmlStr;
	};
	
	var _changeFunType = function(){
		var val = $("input[name=funType]").val();
		var tmpUrl=webPath + "/mfMsgPledge/input?funType="+val;
		document.location.href=tmpUrl;
	};
	
	var _changeWaveType = function(optType,obj){
		var waveType = $(obj).val();
		if(waveType == undefined || waveType == null || waveType == ""){
			waveType = $("select[name=waveType]").val();
		}
		if(waveType == "1"){
			$($("input[name=waveThresholdValue]").parents("tr").get(0)).show();
			if(optType != "update"){
				$("input[name=waveThresholdValue]").val("");
			}
			$("input[name=waveThresholdValue]").attr("mustinput","1");
			$($("input[name=waveThresholdRate]").parents("tr").get(0)).hide();				
			$("input[name=waveThresholdRate]").val("");
			$("input[name=waveThresholdRate]").attr("mustinput","");
		}else if(waveType == "2"){
			$($("input[name=waveThresholdValue]").parents("tr").get(0)).hide();
			$("input[name=waveThresholdValue]").val("");
			$("input[name=waveThresholdValue]").attr("mustinput","");
			$($("input[name=waveThresholdRate]").parents("tr").get(0)).show();		
			if(optType != "update"){
				$("input[name=waveThresholdRate]").val("");
			}
			$("input[name=waveThresholdRate]").attr("mustinput","1");
		}
	};
	
	return {
		ajaxUpdateThis:_ajaxUpdateThis,
		addMfMsgPledgeModel:_addMfMsgPledgeModel,
		initModel:_initModel,
		changeFunType:_changeFunType,
		changeWaveType:_changeWaveType
	};
}(window, jQuery);