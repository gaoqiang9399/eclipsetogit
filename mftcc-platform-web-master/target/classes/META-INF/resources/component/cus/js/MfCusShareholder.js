	var options;
	var selectHtml;
	function init(saveType){
		selectHtml=$("[name=idType]").parent().html();
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});

		$("input[name=shareholderType][value=2]").attr("checked",true);
		options = $("select[name=idType]").find("option");
		makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9', '0');
		getIdType();
		viorIdType();//初始验证

		
	}

	function getIdType() {
		var cusType = $("input:radio[name=shareholderType]:checked").val(); 
		$("select[name=popsidType]").parent(".input-group").find(".pops-select").remove();
		$("select[name=popsidType]").parent(".input-group").find(".pops-value").remove();
		$("input[name=idType]").remove();	
		$("[name=popsidType]").attr("name","idType");
		$("[name=idType]").css("display","block");
		$("[name=idType]").data("options",options);	
		if (cusType == "1") {
			makeOptionsJQ(options, 'A,B,C');
			$("[name=idType]").val("B");
			$("[name=sex]").parents("tr").hide();
			$("[name=sex]").val("");
			$("[name=education]").parents("tr").hide();
			$("[name=education]").val("");
			$("[name=brithday]").parents("tr").hide();
			$("[name=brithday]").val("");
			$("[name=liveAddress]").parents("tr").hide();
			$("[name=liveAddress]").val("");
		} else if (cusType == "2") {
			makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9');
			$("[name=idType]").val("0");
			$("[name=sex]").parents("tr").show();
			$("[name=education]").parents("tr").show();
			$("[name=brithday]").parents("tr").show();
			$("[name=liveAddress]").parents("tr").show();
		}

        $("select[name=idType]").siblings(".input-group-addon").remove();
		$("select[name=idType]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//单选
			changeCallback : function () {//引用公共选择组件后证件校验失效的解决。
				$("[name=popsidType]").trigger("change");
			}
		});
		$("[name=popsidType]").trigger("change");//类型变化，change一下
	}
	//证件类型变化时修改证件号码验证规则（个人表单可共用：表单配置：onchange事件：idTypeChange(this)）
	function idTypeChange(obj){
		//证件号码格式验证
		var idType = $(obj).val();
		var $idNum = $("input[name=idNum]")[0];
		if(idType=="0"){//身份证样式格式
			//如果是身份证，添加校验
			changeValidateType($idNum, "idnum");
		}else if(idType=="7"){
			changeValidateType($idNum, "idnum");
		}else if(idType=="A"){
			changeValidateType($idNum, "organ");
		}else if(idType=="B"){
			changeValidateType($idNum, "credit");
		}else if(idType=="C"){
			changeValidateType($idNum, "licence");
		}else{
			changeValidateType($idNum, "");
		}
		$("input[name=idNum]").val("");
		$("input[name=idNum]").focus();
	}
	function viorIdType(){//初始化页面时根据默认值给证件号加校验(个人表单可共用)
		var idType = $("[name=idType]").val();
		var $idNum =$("input[name=idNum]")[0];
		if(idType=="0"){//身份证样式格式
			//如果是身份证，添加校验
			changeValidateType($idNum, "idnum");
		}else if(idType=="A"){//组织机构
			changeValidateType($idNum, "organ");
		}else if(idType=="B"){//社会信用
			changeValidateType($idNum, "credit");
		}else if(idType=="C"){//营业执照
			changeValidateType($idNum, "licence");
		}else{
			changeValidateType($idNum, "");
		}
	}
//	function getIdTypeTmp() {
//			var idType = $("select[name=idType]").val();
//			if (idType == "A") {
//				$("input[name=idNum]").attr("alt", "organ");
//			} else if (idType == "B") {
//				$("input[name=idNum]").attr("alt", "credit");
//			} else if (idType == "C") {
//				$("input[name=idNum]").attr("alt", "licence");
//			} else if (idType == "0") {
//				$("input[name=idNum]").attr("alt", "idnum");
//			} else {
//				$("input[name=idNum]").attr("alt", "tmp");
//			}
//			$("input[name=idNum]").val("");
//		}

	function saveCusShareholder(obj,saveType){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			if(!checkNumRange()){
				window.top.alert("股权比例必须是0到100以内的数",0);
				return;
			}
			var checkFlag = "",idNumResult = null;
			//证件号码唯一性验证
			var idNum = $("input[name=idNum]").val();
			var idNumTitle = $("input[name=idNum]").attr("title");
			var idNumType = $("input[name=idType]").val();
			var relationId = $("input[name=shareholderId]").val();
			/*if(idNum != ""){
				idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusShareholder","01","");
				checkFlag = idNumResult.split("&")[0];
				idNumResult = idNumResult.split("&")[1];
			}else{
				checkFlag = "0";
			}
			if(checkFlag == "1"){
				window.top.alert(idNumResult,2,function(){
					ajaxInsertCusForm(obj);
				});
			}else{
				ajaxInsertCusForm(obj);
			}*/
            ajaxInsertCusForm(obj);
		}
	}
	
	//检查某个数字在0-100范围之内
	function checkNumRange(){
		var pushCapitalScale = $("input[name=pushCapitalScale]").val();
		pushCapitalScale = Number(pushCapitalScale);
		if(pushCapitalScale >= 0 && pushCapitalScale <= 100){
			return true;
		}
		return false;
	}
	
	function updateCallBack(){
		top.addFlag = true;
		
		myclose_click();
	};
	//选择法人信息
	function selectLegalPerson(){
		var cusNo = $("input[name=cusNo]").val();
		if ($("input[name=cusNo]").data("popupListInited") != true) {
			$("input[name=shareholderName]").popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl : webPath+"/mfCusCorpBaseInfo/getLegalPersonAjax?cusNo=" + cusNo,// 请求数据URL
				valueElem:"input[name=idNum]",//真实值选择器
				title: "法人信息",//标题
				elemEdit: true,
				changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name=shareholderName]"));
					var sltVal = elem.data("selectData");
					$("select[name=popsidType]").popupSelection("selectedById", sltVal.legalIdType);
					$("input[name='idNum']").val(sltVal.legalIdNum);
					$("input[name='shareholderName']").val(sltVal.legalRepresName);
				},
				tablehead:{//列表显示列配置
					"legalRepresName":"法人名称",
					"legalIdNum":"法人证件号码"
				},
				returnData:{//返回值配置
					disName:"legalRepresName",//显示值
					value:"legalIdNum"//真实值
				}
			});
		}
		$("input[name=cusNo]").data("popupListInited", true);
		$('input[name=shareholderName]').next().click();
	};
	function checkPushCapitalScale(dom){
		var val = $(dom).val();
		var cusNo = $("input[name=cusNo]").val();
		var param = {
				number:val,
				cusNo:cusNo
			}
		$.ajax({
			url:webPath+"/mfCusShareholder/checkPushCapitalScaleAjax",
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				if(data.flag=='error'){
					window.top.alert(data.msg,1);
				}
			}
		})
		
	};
	
	function setBirthyAndSexByIDBefore(obj){
		if('0' == $("[name=idType]").val()){
			StringUtil.setBirthyAndSexByID(obj,'sex','brithday');
		}
	};
	
	
	//如果证件类型为身份证则自动带出性别与出生日期
	function   func_using_IDcard_to_set_sex_birthday(obj){	
		var idType = $("select[name=popsidType]").val();				
		var idNode = $(obj).val();				
		if(idType == "0"){				
			$("select[name=sex]").val("");					
			StringUtil.setBirthyAndSexByID(obj, 'sex', 'brithday','age');							
		}
	};

    function calculateAmount() {//根据注册资本自动计算出资比例
        var pushCapitalAmt = $("input[name=pushCapitalAmt]").val();
        pushCapitalAmt = pushCapitalAmt.replace(/,/g, '');
        //正则判断是否是数字
        var patrn = /^(-)?\d+(\.\d+)?$/;
        if (patrn.exec(pushCapitalAmt) == null || pushCapitalAmt == "") {
            return ;
        } 
        //判断结束
        $.ajax({
            url: webPath + "/mfCusShareholder/calculateAmountAjax",
            type: "POST",
            data: {pushCapitalAmt: pushCapitalAmt, registeredCapital: registeredCapital},
            dataType: "json",
            success: function (data) {
                $("input[name=pushCapitalScale]").val(data.pushCapitalScale);
            }, error: function () {
                alert(data.msg, 0);
            }
        })
    };
	
	
	
	
	
	
	
