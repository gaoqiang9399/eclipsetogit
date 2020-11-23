
var MfBusFincApp_examineTaskInsert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("select[name=pasMinNo]").change(function () {
            //清空表单内容
            clearFormVal(addFormId,this.value);
        });
	};
	//选择客户
	var _selectCusDialog=function(){
		selectCusDialog(_selectCusBack,"","","8");
	};
    //选择合同
    var _selectPactDialog=function(){
        selectPactNoByCusno(_selectPactBack);
    };
    //选择借据
    var _selectFincDialog=function(){
        selectFincIdByCusno(_selectFincBack);
    };
    //选择指派检查人
    var _selectUserDialog=function(){
        selectUserDialog(_selectUserBack);
    };

    //选择合同之后的回调方法
    var _selectPactBack = function (mfBusPact) {
        $("input[name=pactNo]").val(mfBusPact.pactNo);
        $("input[name=pactId]").val(mfBusPact.pactId);
        $("input[name=kindName]").val(mfBusPact.kindName);
        $("input[name=pactAmt]").val(mfBusPact.pactAmt);
        $("input[name=pactBal]").val(mfBusPact.usableFincAmt);
        $("input[name=pactBeginDate]").val(getDateShow(mfBusPact.beginDate));
        $("input[name=pactEndDate]").val(getDateShow(mfBusPact.endDate));
        var pasMinNo = $("select[name=pasMinNo]").val();
        if("303"==pasMinNo&&"ecamHisInsertForm"==addFormId){//贷后检查主体为合同时
            setTemplateOptions("",mfBusPact.pactId);
        }

    }
    //选择借据之后的回调方法
	var _selectFincBack=function(mfBusFincApp){
		$("input[name=fincId]").val(mfBusFincApp.fincId);
		$("input[name=fincShowId]").val(mfBusFincApp.fincShowId);
		$("input[name=pactId]").val(mfBusFincApp.pactId);
		$("input[name=overdueSts]").val(mfBusFincApp.overdueSts=='0'?'否':'是');
		$("input[name=putoutAmt]").val(mfBusFincApp.putoutAmt);
		$("input[name=loanBal]").val(mfBusFincApp.loanBal);
		$("input[name=intstBeginDate]").val(getDateShow(mfBusFincApp.intstBeginDate));
		$("input[name=intstEndDate]").val(getDateShow(mfBusFincApp.intstEndDate));
        var pasMinNo = $("select[name=pasMinNo]").val();
        if("304"==pasMinNo&&"ecamHisInsertForm"==addFormId){//贷后检查主体为借据时
            setTemplateOptions("",mfBusFincApp.pactId);
        }
	};
	//选择客户回调
	var _selectCusBack=function(cus){
		cusNo=cus.cusNo;

        //清空表单内容
        clearFormVal(addFormId,$("select[name=pasMinNo]").val());

		//判断选择的客户是否业务已经走到贷后环节
        var result = _checkCusInfo(cusNo);
        if(result.flag == "success"){
            $("input[name=cusNo]").val(cusNo);
            $("input[name=cusName]").val(cus.cusName);
            $("input[name=idNum]").val(cus.idNum);
            $("input[name=cusTelNew]").val(cus.contactsTel);
            //根据客户号获取客户相关的信息，并回显到页面上
            _getCusInfoByCusNo(cusNo);
        }else{
            DIALOG.error(result.msg,function () {
                $("input[name=cusNo]").val("");
                $("input[name=cusName]").val("");
            });
        }
	};
	//选择指派检查人回调方法
    var _selectUserBack = function (user) {
        $("input[name=userNo]").val(user.opNo);
        $("input[name=userName]").val(user.opName);
    }
	//根据选择的贷后检查主体和客户来展示相对应的表单内容
	var _getCusInfoByCusNo=function(){
        var parmData = {'cusNo': cusNo};
        $.ajax({
            url : webPath+"/mfBusFincApp/getExamineTaskFormAjax",
            type : "post",
            data : {ajaxData: JSON.stringify(parmData),tableId:"tablemfBusPactExamineList"},
            dataType : "json",
            success : function(data) {
                if (data.flag == "success") {
                    //根据客户是否代偿展示分级选项
                    if( data.sideLevelList !== undefined && data.sideLevelList.length !== 0){
                        var optionHtml = "<option value='' selected=''></option>";
                        var sideLevelList = data.sideLevelList;
                        $.each(data.sideLevelList, function (i, template) {
                            optionHtml = optionHtml + "<option value='" + template.optCode + "'>" + template.optName + "</option>";
                        });
                        $("select[name=riskLevel]").html(optionHtml);
                    }
                    // if(data.sideLevelFlag !== undefined && data.sideLevelFlag){
                    //     $("select[name=riskLevel]").val("4");
                    // }else{
                    //     $("select[name=riskLevel]").val("1");
                    // }
                    // $("input[name=amtSum]").val(data.amtSum);
                    // $("input[name=amtRest]").val(data.amtRest);
                    // $("input[name=isOverdue]").val(data.isOverdue);
                    var pasMinNo = $("select[name=pasMinNo]").val();
                    if("ecamHisInsertForm"==addFormId){
                        $("input[name=cusNoSupplier]").val(cusNo);
                    }
                    if("305"==pasMinNo&&"ecamHisInsertForm"==addFormId){//贷后检查主体为客户时
                    }
                    $("#mfBusPactExamineList").html(data.tableData);
                }
            },
            error : function() {
            }
        });
	}

	var _checkCusInfo = function (cusNo) {
        var parmData = {'cusNo': cusNo};
        var result;
        $.ajax({
            url : webPath+"/mfBusPact/checkCusInfoByAjax",
            type : "post",
            async:false,
            data : {ajaxData: JSON.stringify(parmData)},
            dataType : "json",
            success : function(data) {
                result = data;
            },
            error : function() {
            }
        });
        return result;
    }

	return{
		init:_init,
		selectCusDialog:_selectCusDialog,
        selectPactDialog:_selectPactDialog,
        selectFincDialog:_selectFincDialog,
        selectUserDialog:_selectUserDialog
	}
}(window,jQuery);

function selectPactNoByCusno(callback) {
    dialog({
        id: 'pactDialog',
        title: "业务选择",
        url: webPath + "/mfBusFincApp/getPactNoByCus?cusNo=" + cusNo,
        width: 900,
        height: 400,
        backdropOpacity: 0,
        onshow: function () {
            this.returnValue = null;
        }, onclose: function () {
            if (this.returnValue) {
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if (typeof(callback) == "function") {
                    callback(this.returnValue);
                } else {
                    getCusInfoArtDialog(this.returnValue);
                }
            }
        }
    }).showModal();
}

function selectFincIdByCusno(callback) {
    dialog({
        id: 'fincDialog',
        title: "业务选择",
        url: webPath + "/mfBusFincApp/getFincIdByCus?cusNo=" + cusNo,
        width: 900,
        height: 400,
        backdropOpacity: 0,
        onshow: function () {
            this.returnValue = null;
        }, onclose: function () {
            if (this.returnValue) {
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if (typeof(callback) == "function") {
                    callback(this.returnValue);
                } else {
                    getCusInfoArtDialog(this.returnValue);
                }
            }
        }
    }).showModal();
}

//将日期改为带中画线的格式
var getDateShow = function(date){
    var result = "";
    if(date!=null&&date!=''&&date!=undefined){
        result = result =date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
    }
    return result;
}
//新增贷后检查任务提交
function insertForExamineTaskForm(obj,type) {
    var url = $(obj).attr("action");
    var flag = submitJsMethod($(obj).get(0), '');
    if (flag) {
        var paramArray =  $(obj).serializeArray();
        paramArray.push({name:"actionType",value:type});
        var dataParam = JSON.stringify(paramArray);
        $.ajax({
            url : url,
            data : {ajaxData : dataParam},
            success : function(data) {
                if (data.flag == "success") {
                    // 关闭当前弹层。
                    myclose_click();
                } else {
                    DIALOG.error(top.getMessage("FAILED_SAVE_CONTENT",{content : "", reason: data.msg}));
                }
            },error : function() {
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });
    } else {

    }
}

//关闭新增弹出框，并支持回调
function myclose_click(){
    if("ecamHisInsertForm"==addFormId){
        /*var url = webPath+'/MfExamineDetailController/getExamineRecordListPage';
        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
        $(top.window.document).find("#bigFormShowiframe").remove();
        $(".bigFormShowiframe .i-x5",parent.document).last().click();*/
        $(".dhccModalDialog .i-x5",parent.document).last().click();
    }else{
        var url = webPath+'/mfBusFincApp/getNewExamineStateListPage';
        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
        $(top.window.document).find("#showDialog").remove();
        $(".showDialog .i-x5",parent.document).last().click();

    }
}

//清空表单内容
function clearFormVal(id,value) {
    $("#"+id)[0].reset();
    if("ecamHisInsertForm"==id){
        //$("select[name=templateId]").html("");
    }
    $("select[name=pasMinNo]").val(value);
}

//给检查模型赋值
function setTemplateOptions(cusNo,pactId){
    var dataParam = {"cusNo":cusNo,"pactId":pactId};
    jQuery.ajax({
        url:webPath+"/mfExamineHis/getConfigMatchedListAjax",
        data:{ajaxData:JSON.stringify(dataParam)},
        type:"POST",
        dataType:"json",
        beforeSend:function(){
        },success:function(data){
            if(data.flag == "success"){
                var optionHtml = "";
                templateList = data.templateList;
                $.each(data.templateList, function(i, template) {
                    optionHtml = optionHtml + "<option value='" + template.optionValue
                        + "'>" + template.optionLabel + "</option>";
                });
                $("select[name=templateId]").html(optionHtml);
            }
        },error:function(data){
            window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
        }
    });
}

