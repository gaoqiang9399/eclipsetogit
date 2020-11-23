var MfCusCreditApply_transferInput = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		_setKindPopupSelection("kindNo");
	};
	
	//产品使用选择组件
	var _setKindPopupSelection = function(name) {
		var kinds = new Array();
		for (var i = 0; i < mfSysKinds.length; i++) {
			kinds.push({"id" : mfSysKinds[i].kindNo, "name" : mfSysKinds[i].kindName});
		}
		$form.find("[name=" + name + "]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			itemsCount : 8,
			multiple : false,//单选
			items: kinds,
			changeCallback : function(obj,elem) {
			var  id =  obj.data("values").attr("name");
			if(id.indexOf("kindNo_") != -1){
				$("input[id="+id+"]").val("");
			  
			}else{
				 $("input[name=creditAmt]").val("");
			 }
			}
		});
	}
	
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			//规则验证
			var ruleCusNo = $("input[name=cusNo]").val();
			var ruleKindNo = $(formObj).find("[name=kindNo]").val();
			var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'kindNo':ruleKindNo};
			if(!rulesCall.validateRules(ruleParmsData,ruleCusNo)){
				return false;
			}
            var i;
			var url = $(formObj).attr("action");
			var dataForm;
			var kindNo = null;
			var kindName= null;
			var kindNos = [];
			var kindNames = [];
			var creditAmts = [];
			var amountLands = [];
			var productAmtSum = Number($("input[name=creditAmt]").val().replace(/,/g,""));
			var creditAmtSum = Number($("input[name=creditSum]").val().replace(/,/g,""));
			var dataObject = {};
			var timeOne="";
			var creditType=$(formObj).find("[name=creditType]").val();
			if(cusType == "101"){
				timeOne=$(formObj).find("[name=companyName]").attr("title");
				kindName = $("input[name=companyName]").val();
				var creditAmt = $("input[name=creditAmt").val().replace(/,/g,"");
				if(kindName!=""&&kindName!=null){
					kindNames.push(kindName);
					creditAmts.push(creditAmt);
					amountLands.push($("input[name=amountLand]").val());
				}
				dataForm = JSON.stringify($(formObj).serializeArray());
				if(index != 0){
					for(i = 1;i<=index;i++){
						if($("input[name=companyName_"+i+"]").length>0){   //对象存在
							kindName =  $("input[name=companyName_"+i+"]").val();
							if(kindName==""||kindName==null){
								continue;
							}
							kindNames.push($("input[name=companyName_"+i+"]").val());
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							amountLands.push($("input[name=amountLand_"+i+"]").val());
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}
				dataObject = { ajaxData : dataForm,
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts),
								amountLands: JSON.stringify(amountLands)
			         };
			}else{
				timeOne=$(formObj).find("[name=popskindNo]").attr("title");
				kindNo = $(formObj).find("[name=kindNo]").val();
				kindName= $(formObj).find("[name=kindNo]").parents("tr").find(".pops-value").html();
				$("input[name=kindName]").val(kindName);
				if(kindNo!=""&&kindNo!=null){
					kindNos.push(kindNo);
					kindNames.push(kindName);
					creditAmts.push($("input[name=creditAmt").val().replace(/,/g,""));
					amountLands.push($("input[name=amountLand]").val());
				}
				dataForm = JSON.stringify($(formObj).serializeArray());
				if(index != 0){
					for(i = 1;i<=index;i++){
						if($(formObj).find("[name=kindNo_"+i+"]").length>0){   //对象存在
							kindNo = $(formObj).find("[name=kindNo_"+i+"]").val();
							if(kindNo==""||kindNo==null){
								continue;
							}
							kindName=$(formObj).find("[name=kindNo_"+i+"]").parents("tr").find(".pops-value").html();
							kindNos.push(kindNo);
							kindNames.push(kindName);
							amountLands.push($("input[name=amountLand_"+i+"]").val());
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}
				dataObject = { ajaxData : dataForm,
								kindNos: JSON.stringify(kindNos),
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts),
								amountLands: JSON.stringify(amountLands),
								creditModel:creditModel
					         };
			}
			if(creditAmtSum < productAmtSum){
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"额度总和","timeTwo":$("input[name=creditSum]").attr("title")}), 0);
				return;
			}
			if(kindNos != null && kindNos !='' && kindNos.length>0){
				var tmpKindNo = [];
				for(i=0;i<kindNos.length;i++){
					if(tmpKindNo.indexOf(kindNos[i]) == -1){
						tmpKindNo.push(kindNos[i]);
					}else{
						//window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"产品授信额度","content2":"重复"}), 0);
						window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"","content2":"单一产品在同一期限内存在多笔授信"}), 0);
						return false;
					}
				}
			}
			
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : dataObject,
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = data.wkfAppId;
						top.creditType=creditType;
						top.creditAppId=data.creditAppId;
						window.location.href=webPath+"/mfCusCreditApply/getCusCreditView?&cusNo="+cusNo+"&creditAppId="+data.creditAppId+"&busEntrance=credit";
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	
	//关闭
	var _close = function(){
		myclose();
	};



	   
	return{
		init:_init,
		close:_close,
		ajaxInsert:_ajaxInsert
	};
}(window,jQuery);