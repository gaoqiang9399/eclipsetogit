;
var MfCusBorrowerInfo_Insert = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        $("[name='borrowMariage']").val("3");
		
	};

    //共借人填写省份证号 然后自动带出出生日期和年龄
    var _getAgeAndBrithday=function(obj){
        var idType = $("select[name=borrowIdType]").val();
        var id = $("input[name='borrowIdNum']").val();
        if(idType == "0" && 18 == id.length ){
            //获取出生日期
            var birthdayValue = id.substring(6,10)+'-'+id.substring(10,12)+'-'+id.substring(12,14);
            $("input[name='borrowBirth']").val(birthdayValue);
            //获取年龄
            var myDate = new Date();
            var month = myDate.getMonth() + 1;
            var day = myDate.getDate();
            var ageValue = myDate.getFullYear() - id.substring(6, 10) - 1;
            if (id.substring(10, 12) < month || id.substring(10, 12) == month && id.substring(12, 14) <= day) {
                ageValue++;
            }
            $("input[name='borrowAge']").val(ageValue);
            //获取性别
            var sexValue;
            if (parseInt(id.charAt(16) / 2) * 2 != id.charAt(16))
                sexValue = '0';
            else{
                sexValue = '1';
			}
            $("[name='borrowSex']").val(sexValue);
        }
    }

	var _selectBorrower = function(){
		 $("input[name=borrowName]").parent().find('div').remove();
		 var obj= $('input[name=borrowName]');
		 var hide = "borrowIdNum";
		 var formId = $(obj).parents('form').find('input[name=formId]').val();
		 var ajaxUrl =webPath+"/mfUserPermission/getPerDataSourceAjax?formId="+formId+"&element="+hide+"&cusNo="+cusNo+"&cusBaseType="+cusBaseType;//请求数据URL;
		 $(obj).popupList({
			 searchOn: true, //启用搜索
			 multiple: false, //false单选，true多选，默认多选
			 ajaxUrl:ajaxUrl,
			 valueElem:"input[name="+hide+"]",//真实值选择器
			 title: "选择共同借款人",//标题
			 labelShow:false,
			 changeCallback:function(elem){//回调方法
				 BASE.removePlaceholder($("input[name="+hide+"]"));
				 var sltVal = elem.data("selectData");
				 $("input[name='borrowName']").val(sltVal.cusName);
				 $("input[name='borrowTel']").val(sltVal.contactsTel);
                 $("input[name='borrowCommAddress']").val(sltVal.commAddress);
                 if(sltVal.ext1 != ""){
					 $("[name='relation']").val(sltVal.ext1);
				 }
                 if(sltVal.idType == '0'){
					 $("input[name='borrowIdNum']").val(sltVal.idNum);
					 if(sltVal.idNum != ''){
						var idNode = document.getElementsByName("borrowIdNum")
						StringUtil.setBirthyAndSexByID(idNode, 'borrowSex', 'borrowBirth','borrowAge');							
					 }
				 }
			 },
			 tablehead:{//列表显示列配置
				 "cusName":"客户名称",
				 "idNum":"证件号码"
			 },
			 returnData:{//返回值配置
				 disName:"cusName",//显示值
				 value:"idNum"//真实值
			 }
		 });
		 $('input[name=borrowName]').next().click();
		 $('input[name=borrowName]').siblings(".pops-value").remove();
		 $("input[name=borrowName]").css('display', '');
	};
	var _updateCallBack = function(){
		top.addFlag = true;
		myclose_click();
	};
	
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
    //保存继续新增
    var  _ajaxSaveAndAdd = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusBorrowerInfo/input?cusNo="+cusNo;
        if(flag){
            ajaxInserAndAddCusForm(obj,inputUrl);
        }
    };
    
    var _updateMarital = function (obj) {
		var relation = $("[name='relation']").val();
		//alert(relation);
		if(relation=="1"){
            $("[name='borrowMariage']").val("3");
        }
    }
    //证件类型变化是修改证件号码验证规则
    var _idTypeChange =  function(obj){
        //证件号码格式验证
       /* var idType = $("select[name=borrowIdType]").val();*/
      /*  var $idNum = $("input[name='borrowIdNum']").val();*/
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=borrowIdNum]")[0];
        if(idType=="0"){//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        }else{
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=borrowIdNum]").val("");
        $(obj).parents("table").find("input[name=borrowIdNum]").focus();
    };
	return {
		init : _init,
		updateCallBack:_updateCallBack,
		ajaxSave:_ajaxSave,
		selectBorrower:_selectBorrower,
        getAgeAndBrithday:_getAgeAndBrithday,
        ajaxSaveAndAdd:_ajaxSaveAndAdd,
        updateMarital:_updateMarital,
        idTypeChange:_idTypeChange
	};
}(window, jQuery);



