var MfCusFamilyInfo = function(window,$){
	var _init=function(cusNo){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//客户名称新版选择组件
		$('input[name=relNameHidden]').popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfCusCustomer/findByPageForSelectAjax?removeCusId="+cusNo+"&cusBaseType=2",//请求数据URL
			handle:BASE.getIconInTd($("input[name=relName]")),//其他触发控件
			valueElem:"input[name=anotherCusNo]",//真实值选择器
			title: "选择个人客户",//标题
			changeCallback:function(elem){//回调方法
				$('input[name=isAnotherCus]').val("1");
				$('input[name=relName]').val($('input[name=relNameHidden]').val());
				$('input[name=relName]').parent().find(".error").remove();
				$('.hidden-content').find('div[class=pops-value]').hide();
				_getCusPersonBaseInfo(elem.data("values").val());
			},
			tablehead:{//列表显示列配置
				"cusNo":{"disName":"客户编号","align":"center"},
				"cusName":{"disName":"客户名称","align":"center"}
			},
			returnData:{//返回值配置
				disName:"cusName",//显示值
				value:"cusNo"//真实值
			}
		});
	};

    //根据身份证获取年龄性别信息
    var _getBirthdayAndSex = function(){
        var idType = $("[name='popsidType']").val();
        if (!idType) {
            idType = $("[name='idType']").val();
        }
        if("0"==idType){
            StringUtil.setBirthyAndSexByID($("input[name='idNum']"), 'sex', 'brithday','age');
        }
    }
    //根据证件号码获取客户表客户
	var _getCusNameByIdNum = function () {
    	var idNum = $("input[name='idNum']").val();
        $.ajax({
            url : webPath+"/mfCusFamilyInfo/getByIdNum?idNum="+idNum,
            dataType : 'json',
            async:false,
            success : function(data) {
                if(data.flag=="success"){
                	$("input[name='relName']").val(data.customer.cusName);
                    $("input[name='relTel']").val(data.customer.cusTel);
                    $("input[name='postalAddress']").val(data.customer.commAddress);
                    $("input[name='workUnit']").val(data.personJob);
                    _getBirthdayAndSex();
                    if(data.customer.sex!=null && data.customer.sex!=""){
                        $("select[name=sex]").val(data.customer.sex);
                    }
                }
            },
            error : function() {
            }
        });
    }
	var  _getCusPersonBaseInfo = function(cusNo){
		$.ajax({
			url : webPath+"/mfCusPersBaseInfo/getByIdAjax?cusNo="+cusNo,
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(data) {
				if(data.flag=="success"){
					var mfCusPersBaseInfo=data.mfCusPersBaseInfo;
					$("input[name=idNum]").val(mfCusPersBaseInfo.idNum);
					$("input[name=relTel]").val(mfCusPersBaseInfo.cusTel);
                    $("input[name='postalAddress']").val(mfCusPersBaseInfo.commAddress);
                    $("input[name='workUnit']").val(data.personJob);
                    _getBirthdayAndSex();
					if(mfCusPersBaseInfo.sex!=null && mfCusPersBaseInfo.sex!=""){
						$("select[name=sex]").val(mfCusPersBaseInfo.sex);
					}
				}
			},
			error : function() {
			}
		});
	}
	return{
		init:_init,
        getBirthdayAndSex:_getBirthdayAndSex,
        getCusNameByIdNum:_getCusNameByIdNum
	};
	
}(window, jQuery);

//证件号码格式验证
function idTypeChange(obj){
	var idType = $(obj).parent(".input-group").find("input[name=idType]").val();
	var $idNum = $(obj).parents("table").find("input[name=idNum]")[0];
	if(idType=="0"){//身份证样式格式
		//如果是身份证，添加校验
		changeValidateType($idNum, "idnum");
	}else if(idType == "3"){//军官证样式格式
		//如果是军官证，添加校验
		changeValidateType($idNum, "officersNo");
	}else{
		changeValidateType($idNum, "");
	}
	$(obj).parents("table").find("input[name=idNum]").val("");
	$(obj).parents("table").find("input[name=idNum]").focus();
}

function saveCusFamilyInfoInsert(obj,saveType){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var checkFlag = "";
		//证件号码唯一性验证
		var idNum = $("input[name=idNum]").val();
		var idNumTitle = $("input[name=idNum]").attr("title");
		var idNumType = $("input[name=idType]").val();
		var relationId = $("input[name=id]").val();
		var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusFamilyInfo","01",saveType,"");
		checkFlag = idNumResult.split("&")[0];
		idNumResult = idNumResult.split("&")[1];
        //根据身份证获取年龄性别信息
        getBirthdayAndSex();
		if(checkFlag == "1"){
			/*window.top.alert(idNumResult+",继续保存？",2,function(){*/
				ajaxInsertCusForm(obj);
			//});
		}else{
			ajaxInsertCusForm(obj);
		}
	}
}

//根据身份证获取年龄性别信息
function getBirthdayAndSex(){
	var idTypes = $("[name='idType']").val();
    if("0"==idTypes){
        StringUtil.setBirthyAndSexByID($("input[name='idNum']"), 'sex', 'brithday','age');
    }
}

function saveCusFamilyInfoInsertAndAdd(obj,saveType){

    var flag = submitJsMethod($(obj).get(0), '');
    var cusNo = $("input[name='cusNo']").val();
    var inputUrl = webPath+"/mfCusFamilyInfo/input?cusNo="+cusNo;
    if(flag){
        var checkFlag = "";
        //证件号码唯一性验证
        var idNum = $("input[name=idNum]").val();
        var idNumTitle = $("input[name=idNum]").attr("title");
        var idNumType = $("input[name=idType]").val();
        var relationId = $("input[name=id]").val();
        var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusFamilyInfo","01",saveType,"");
        checkFlag = idNumResult.split("&")[0];
        idNumResult = idNumResult.split("&")[1];
        if(checkFlag == "1"){
            window.top.alert(idNumResult+",继续保存？",2,function(){
                ajaxInserAndAddCusForm(obj,inputUrl);
            });
        }else{
            ajaxInserAndAddCusForm(obj,inputUrl);
        }
    }
}
function updateCallBack(){
	top.addFlag = true;
	myclose_click();
};

function func_using_IDcard_to_set_sex(obj){
	StringUtil.setBirthyAndSexByID(obj,"sex",null,"ext1");
	var sex = $("input[name=sex]").val();
	$("select[name=popssex]").popupSelection("selectedById", sex);
		 
}