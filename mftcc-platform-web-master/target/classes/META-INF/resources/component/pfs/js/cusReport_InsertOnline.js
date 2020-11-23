//加载完成执行
$(function(){
	$(".row_content").mCustomScrollbar({
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	selectReportMenu();
	//初始化数据
	$.fn.initData = function(data,type,cusFinMain){
		var $this = $(this);
		var length = data.length
		for(var i=0;i<length;i++){
			var $tr = $('<tr></tr>');
			$tr.setHtml(data[i],type,cusFinMain);
			$this.append($tr);
		}
	};

    //组装报表项html peng-财务改
    $.fn.setHtml = function(colData,typeId,cusFinMain){
        if(typeof(colData) == "undefined"){
            return;
        }
        var $obj = $(this);
        var $title = $('<td align="left"></td>');
        var titleClass="titleClass10";
        var showStyle=colData.showStyle;
        if(showStyle=="1"){
            titleClass = "titleClass21";
        }
        $title.html(colData.showName);
        $title.attr("class",titleClass);

        var $showOrder = $('<td align="left"></td>');
        $showOrder.html(colData.showOrder);
        $showOrder.attr("class","titleClass10");

        //期初项/上期数
        var itemAmtAVal = colData.itemAmtA;
        if(itemAmtAVal==""){
            itemAmtAVal = "0.00";
        }
        var $itemAmtA = $('<td align="center" class="" style="text-align: center;"><input name="codeColumn" type="hidden" style="display:none;" value="'+colData.reportItemId+'" ><input name="itemAmtAVal" type="text" style="display:none;" value="'+itemAmtAVal+'" ></td>');
        var $itemAmtAInput = $('<input name="itemAmtAVal" class="INPUT_TEXT" type="text" maxlength="15" number="double" onkeyup="toMoney(this)" onchange="valChange(this,\'1\')" >');
        if(colData.operationType == "2"){
            $itemAmtAInput.attr("readonly",true);
        }
        //设置计算项 不允许输入
        setInputReadOnly($itemAmtAInput,reportType,colData);
        if(cusFinMain.reportSts == "2"){  //当前财务确认后，显示的时候不能修改
            $itemAmtAInput.addClass("readonly-input");
        }
        $itemAmtAInput.attr("alt",colData.reportItemId+"_1");
        $itemAmtAInput.val(changeMoney(itemAmtAVal));
        $itemAmtA.append($itemAmtAInput);

        //本期项/本期数
        var itemAmtBVal = colData.itemAmtB;
        if(itemAmtBVal==""){
            itemAmtBVal = "0.00";
        }

        var $itemAmtB = $('<td align="center" class="" style="text-align: center;"><input name="itemAmtBVal" type="text" style="display:none;" value="'+itemAmtBVal+'" ></td>');
        var $itemAmtBInput = $('<input name="itemAmtBVal" class="INPUT_TEXT" type="text" maxlength="15" number="double" onkeyup="toMoney(this)" onchange="valChange(this,\'2\')" >');
        if(colData.operationType == "2"){
            $itemAmtBInput.attr("readonly",true);
        }
        //设置计算项 不允许输入
        setInputReadOnly($itemAmtBInput,reportType,colData);
        if(cusFinMain.reportSts == "2"){  //当前财务确认后，显示的时候不能修改
            $itemAmtBInput.addClass("readonly-input");
        }
        $itemAmtBInput.attr("alt",colData.reportItemId+"_2");
        $itemAmtBInput.val(changeMoney(itemAmtBVal));
        $itemAmtB.append($itemAmtBInput);

        $obj.append($title);
        $obj.append($showOrder);
        $obj.append($itemAmtA);
        $obj.append($itemAmtB);
        if(colData.operationType == "0"){//如果是空白型，则不需要显示数据
            $itemAmtA.find("*").hide();
            $itemAmtB.find("*").hide();
        }
    };

    $.fn.setHtmlOfSubjectBal = function(){
        addTr();
    }


	//初始化数据
	if(dataMap !== undefined && dataMap != null){
		var capList = dataMap.assetsList;//资产负债表资产类
		var capList1 = dataMap.assetsList1;//资产负债表负债类
		var cashList = dataMap.cashList; //现金流量
		var proDataList = dataMap.profList; //利润分配表
		var mfCusReportAcount= dataMap.mfCusReportAcount;   //财务报表主表
        var subjectBal = dataMap.subjectBal;   //科目余额表
        var $obj;
		if(capList!==undefined&&capList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinCapData]");
			$obj.find("table[name=zc_table]").find("tbody").initData(capList,'001',mfCusReportAcount);
		}
		if(capList1!==undefined&&capList1.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinCapData]");
			$obj.find("table[name=fz_table]").find("tbody").initData(capList1,'001',mfCusReportAcount);
		}
		if(cashList!==undefined&&cashList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinCashData]");
			$obj.find("table tbody").initData(cashList,'002',mfCusReportAcount);
		}
		if(proDataList!==undefined&&proDataList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinProData]");
			$obj.find("table tbody").initData(proDataList,'003',mfCusReportAcount);
		}
        if(subjectBal!==undefined&&subjectBal==true){
            $obj = $(".content_col div[data-ctrl=cusFinSubjectData]");
            $obj.find("table tbody").setHtmlOfSubjectBal(proDataList,'003',mfCusReportAcount);
        }

	}
	
	//左侧三个菜单切换
	$(".content_col .row_ul ul li").click(function(){
		var $this = $(this);
		selectReportNode($this);
	});
});

function delTr(obj){
    $("#"+obj).remove();  //删除tr
}

function addTr(){
    var $data = $(".content_col div[data-ctrl=cusFinSubjectData]");
    var mydate = new Date();
    var mytime=mydate.getTime(); //获取当前时间
    var $obj =$data.find("table tbody");
    var $tr = $('<tr id="'+mytime+'"></tr>');
    var $subjectNo = $('<td align="center" class="" style="text-align: center;"></td>');
    var $subjectNoInput = $('<input name="subjectNo" class="INPUT_TEXT" type="text" maxlength="30" >');
    $subjectNo.append($subjectNoInput);
    $tr.append($subjectNo);
    var $subjectName = $('<td align="center" class="" style="text-align: center;"></td>');
    var $subjectNameInput = $('<input name="subjectName" class="INPUT_TEXT" type="text" maxlength="40"  >');
    $subjectName.append($subjectNameInput);
    $tr.append($subjectName);
    var $amtProperties = $('<td align="center" class="" style="text-align: center;"></td>');
    var $amtPropertiesInput = $('<input name="amtProperties" class="INPUT_TEXT" type="text" maxlength="125"  >');
    $amtProperties.append($amtPropertiesInput);
    $tr.append($amtProperties);
    var $subjectAmt = $('<td align="center" class="" style="text-align: center;"></td>');
    var $subjectAmtInput = $('<input name="subjectAmt" class="INPUT_TEXT" type="text" maxlength="14" number="double" onkeyup="toMoney(this)" onchange="valChange(this,\'1\')" >');
    $subjectAmt.append($subjectAmtInput);
    $tr.append($subjectAmt);
    var $opr = $('<td align="center" class="" style="text-align: center;"><input value="删除" type="button" onclick="delTr('+mytime+')" class="btn btn-link formAdd-btn" ></td>');
    $tr.append($opr);
    $obj.append($tr);
}

//切换左侧报表节点
function selectReportNode(selObj){
	selObj.parent().find(".li_selected").removeClass("li_selected");
	selObj.addClass("li_selected");
	var data_ctrl= selObj.attr("data-ctrl");
	selObj.parents(".content_data").find(".row_selected").removeClass("row_selected");
	var $thisRowContent = selObj.parents(".content_data").find(".content_col div[data-ctrl="+data_ctrl+"]").find(".row_content");
	$thisRowContent.addClass("row_selected");
	selObj.parents(".content_data").find(".content_col div").removeClass("data_ctrl_selected");
	selObj.parents(".content_data").find(".content_col div[data-ctrl="+data_ctrl+"]").addClass("data_ctrl_selected");
	var $thisFinBtn = selObj.parents(".content_data").find(".btn-fin div[data-ctrl="+data_ctrl+"]").find(".btn_content");
	$thisFinBtn.addClass("row_selected");
}

//左侧菜单显示的排列组合
function selectReportMenu(){
    var $this = $("li[data-ctrl=cusFinCapData]");
    if ('002'==reportTypeId){
        $("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
        $this = $("li[data-ctrl=cusFinCashData]");
    } else if ('003'==reportTypeId){
        $("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
        $this = $("li[data-ctrl=cusFinProData]");
    } else if ('004'==reportTypeId){
        $("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
        $this = $("li[data-ctrl=cusFinSubjectData]");
    }
    selectReportNode($this);
}

//设置计算项 不允许输入
function setInputReadOnly($endInput,reportType,colData){
	var codeColumn = colData.codeColumn;
	var inputType = colData.operationType;
	if(inputType== "2"){
		$endInput.addClass("readonly-input");
	}
}

/*********
 * 处理各级科目信息
 * @param subjectNo
 * @param map
 * @returns {*}
 */
function getSubjectNoLev(subjectNo,map) {
    subjectNo=subjectNo.replace(/\-/g,"");
    subjectNo=subjectNo.replace(/\./g,"");
    subjectNo=subjectNo.replace(/\_/g,"");
    subjectNo=subjectNo.replace(/\//g,"");
   //计算科目级别
    var subjectNoLen = subjectNo.length;
    if(subjectNoLen%2!=0){
        map["error"]="科目编码格式错误";
        return map;
    }
    var subLev = (subjectNoLen-4)/2+1;
    var firstNo;
    if(subLev=="1"){
        map[subjectNo] = 1;
    }else  if(subLev=="2"){
        firstNo=subjectNo.substr(0,4);
        map[subjectNo] = 1;
        map[firstNo]=map[firstNo]== null ? 0 : parseInt(map[firstNo]);
    }else  if(subLev=="3"){
        firstNo=subjectNo.substr(0,4);
        var secondNo=subjectNo.substr(0,6);
        map[subjectNo] = 1;
        map[firstNo]=map[firstNo]== null ? 0 : parseInt(map[firstNo]);
        map[secondNo]=map[secondNo]== null ? 0 : parseInt(map[secondNo]);
    }
    return map;
}




/**
 * 保存数据
 * @param {Object} obj
 */
function submitDataNew(obj,typeId){
    var $obj = $(obj);
    var cusNo = $("input[name=cusNo]").val();
    var reportType=$('#reportType').val();
    var weeks = $('#weeks').val();
    var ifAud=$('#ifAud').val();
    var audOrg=$('#audOrg').val();
    var audDate=$('#audDate').val();
    var audIdea=$('#audIdea').val();
    if (weeks == ''){
        window.top.alert(top.getMessage("NOT_FORM_EMPTY","报表时间"), 0);
        return;
    }else if (reportType == '1' && weeks.length != 6){
        window.top.alert(top.getMessage("ONLY_FORM_LENGTH",{"field":"月报报表时间" , "length": "6"}), 0);
        return;
    }else if (reportType == '3' && weeks.length != 4){
        window.top.alert(top.getMessage("ONLY_FORM_LENGTH",{"field":"年报报表时间" , "length": "4"}), 0);
        return;
    }
    if (ifAud == '1'){
        if (audOrg == ''){
            window.top.alert(top.getMessage("NOT_FORM_EMPTY","审计单位"), 0);
            return;
        }
        if (audDate == ''){
            window.top.alert(top.getMessage("NOT_FORM_EMPTY","审计日期"), 0);
            return;
        }else if (audDate.length != 8){
            window.top.alert(top.getMessage("ONLY_FORM_LENGTH",{"field":"审计日期" , "length": "8"}), 0);
            return;
        }
        if (audIdea == ''){
            window.top.alert(top.getMessage("NOT_FORM_EMPTY","审计意见"), 0);
            return;
        }
    }
    var reportAcount = {};
    reportAcount.cusNo=cusNo;
    reportAcount.reportType= reportType;
    reportAcount.weeks=weeks;
    reportAcount.ifAud=ifAud;
    reportAcount.audOrg=audOrg;
    reportAcount.audDate=audDate;
    reportAcount.audIdea=audIdea;

    var url = $obj.attr("action");
    var dataParmList = [];
    if(typeId != "004"){
        $(obj).find("tbody tr").each(function(i, o) {
            var entity = {};
            entity.reportItemId = $(this).find("input[name=codeColumn]").val();
            entity.itemAmtA = $(this).find("input[name=itemAmtAVal][class]").val().replace(/\,/g, "" );
            entity.itemAmtB = $(this).find("input[name=itemAmtBVal][class]").val().replace(/\,/g, "" );
            dataParmList.push(entity);
        });
	}else{
        var msg="";
        var map=new Object();
        $(obj).find("tbody tr").each(function(i, o) {
            var entity = {};
            entity.subjectNo = $(this).find("input[name=subjectNo]").val();
            if( entity.subjectNo=="") {
                msg="科目编号不能为空!";
                return;
            }else{
                // debugger
                getSubjectNoLev(entity.subjectNo,map);
                if(map["error"]!=undefined){
                    msg+=map["error"];
                }
            }
            entity.subjectName = $(this).find("input[name=subjectName]").val();
            if( entity.subjectName=="") {
                msg="科目名称不能为空!";
                return;
            }
            entity.amtProperties = $(this).find("input[name=amtProperties]").val();
            if( entity.amtProperties=="") {
                msg="款项性质不能为空!";
                return;
            }
            entity.subjectAmt = $(this).find("input[name=subjectAmt]").val().replace(/\,/g, "" );
            if( entity.subjectAmt=="") {
                msg="金额不能为空!";
                return;
            }
            entity.cusNo=cusNo;
            entity.finRptType=$('#reportType').val();
            entity.finRptDate= $('#weeks').val();
            dataParmList.push(entity);
        });


        for(var key in map){
            if(map[key]==0){
                alert("请录入["+key+"]科目的数据",0);
                return;
            }
        }


        if(dataParmList.length==0){
            msg="请录入科目余额信息!";
        }

        if(msg!=""){
            alert(msg,0);
            return;
        }
	}

    var reason = (reportType=='1' ? '该期' : "该年");
    if(typeId == "001"){
        reason += "资产负债表已存在";
        var q10026 = $obj.find("input[alt=10025_1]").val();
        var q10056 = $obj.find("input[alt=10053_1]").val();
        if(CalcUtil.compare(q10026, q10056) != 0){
            window.top.alert(top.getMessage("FIRST_OPERATION","对资产负债表中期末的资产总计和负债及所有者权益总计的平衡条件的检查"), 0);
            return;
        }
        var n10026 = $obj.find("input[alt=10025_2]").val();
        var n10056 = $obj.find("input[alt=10053_2]").val();
        if(CalcUtil.compare(n10026, n10056) != 0){
            window.top.alert(top.getMessage("FIRST_OPERATION","对资产负债表中年初的资产总计和负债及所有者权益总计的平衡条件的检查"), 0);
            return;
        }
    }else if (typeId == "002") {
        reason += "现金流量表已存在";
    }else if (typeId == "003") {
        reason += "利润表已存在";
    }
    $.ajax({
        type : "post",
        url : webPath + "/mfCusReportData/isExistReportAjax",
        data : {
            cusNo : cusNo,
            reportTypeId:typeId,
            reportAcount : JSON.stringify(reportAcount)
        },
        async : false,
        success : function(data) {
            if (data.flag == "success") {
                if (data.isExist == "true"){
                    window.top.alert(top.getMessage("CONFIRM_OPERATION_REASON", {"reason":reason , "operation": "覆盖"}), 2, function () {
                        insertReportData(url, dataParmList, cusNo, typeId, reportAcount);
                    });
                }else {
                    insertReportData(url, dataParmList, cusNo, typeId, reportAcount);
                }
            } else if (data.flag == "error") {
                window.top.alert(data.msg, 1);
            }
        },
        error : function() {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};

function insertReportData(url, dataParmList, cusNo, typeId, reportAcount) {
    $.ajax({
        type : "post",
        url : url,
        data : {
            ajaxData : JSON.stringify(dataParmList),
            cusNo : cusNo,
            reportTypeId:typeId,
            reportAcount : JSON.stringify(reportAcount)
        },
        async : false,
        success : function(data) {
            if (data.flag == "success") {
                window.top.alert(top.getMessage("SUCCEED_OPERATION"), 1);
                var $this = $("li[data-ctrl=cusFinCapData]");
                if (null==data.assetsId || ''==data.assetsId){
                     $this = $("li[data-ctrl=cusFinCapData]");
                } else if (null==data.cashId || ''==data.cashId){
                     $this = $("li[data-ctrl=cusFinCashData]");
                } else if (null==data.incomId || ''==data.incomId){
                     $this = $("li[data-ctrl=cusFinProData]");
                }
                selectReportNode($this);
            } else if (data.flag == "error") {
                window.top.alert(data.msg, 0);
            }
        },
        error : function() {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}

//从数据库中获取该报表元素所有参与计算的元素项  type 1-上期 2-本期
function valChange(obj,type){
    var codeColumn = $(obj).attr("alt");
    var codeColumn1 = $(obj).attr("alt");
    var temp = parseFloat($(obj).val().replace(/,/g,''));
    $(obj).val(temp.toFixed(2));
    var typeStr="";
    if(type==1){
        typeStr="itemAmtAVal";
    }else{
        typeStr="itemAmtBVal";
    }
    var changeVal = temp - parseFloat($(obj).parent().find("input[name="+typeStr+"]").val()) ; //变化金额
    var formulaColumn=codeColumn.split("_")[0];
    $.ajax({
        url:webPath+"/cusFinFormula/checkFormulaAjaxNew?formulaColumn="+formulaColumn,
        dataType:"json",
        type:"POST",
        success:function(data){
            if(data.flag == "success"){
                $.each(data.cusFinFormulaList,function(i,cusFinFormula){
                    var endVal="";
                    codeColumn = cusFinFormula.reportItemId;
                    var sign = cusFinFormula.calcSign;//运算符号，1是+，2是-
                    if($("input[alt="+codeColumn+"_"+type+"]").val()!=undefined&&$("input[alt="+codeColumn+"_"+type+"]").val()!=null){
                        if(sign == "1"){//
                            var test=$("input[alt="+codeColumn+"_"+type+"]").val();
                            endVal = parseFloat($("input[alt="+codeColumn+"_"+type+"]").val().replace(/,/g,'')) + changeVal;
                        }else{
                            //var test=$("input[alt="+codeColumn+"_"+type+"]").val();
                            endVal = parseFloat($("input[alt="+codeColumn+"_"+type+"]").val().replace(/,/g,'')) - changeVal;
                        }
                    }
                    $("input[alt="+codeColumn+"_"+type+"]").val(endVal.toFixed(2));
                });

                $(obj).parent().find("input[name="+typeStr+"]").val(temp.toFixed(2));
//				var wxjdyzchj = parseFloat($("input[alt=ADbi]").val()) + parseFloat($("input[alt=ADbk]").val());
//				$("input[alt=ADbn]").val(wxjdyzchj.toFixed(2));  //无形及递延资产合计
//				var zczj = parseFloat($("input[alt=ADas]").val()) + parseFloat($("input[alt=ADax]").val()) + parseFloat($("input[alt=ADbh]").val()) + wxjdyzchj + parseFloat($("input[alt=ADbo]").val()) + parseFloat($("input[alt=ADbp]").val()) + parseFloat($("input[alt=ADbq]").val());
//				$("input[alt=ADbr]").val(zczj.toFixed(2));  //资产总计
                $.each($(".INPUT_TEXT"),function(i,obj1){
                    var alt=$(obj1).attr("alt");
                    if(alt!=codeColumn1&&alt!=undefined) {
                        var reportItemId=alt.split("_")[0];//获取实际指标号
                        var flag=alt.split("_")[1];//获取列
                        if (dataMap[reportItemId] != undefined && dataMap[reportItemId].length > 0&&flag==type) {
                            var endVal1 = parseFloat("0.00");
                            $.each(dataMap[reportItemId], function (i, cusFinFormula1) {
                                var formulaColumn = cusFinFormula1.calcItem;
                                var sign = cusFinFormula1.calcSign;//运算符号，1是+，2是-
                                if ($("input[alt=" + formulaColumn +"_"+type+"]").val() != undefined && $("input[alt=" + formulaColumn +"_"+type+"]").val() != null) {
                                    if (sign == 1) {//
                                        endVal1 = parseFloat($("input[alt=" + formulaColumn +"_"+type+"]").val().replace(/,/g, '')) + endVal1;
                                    } else if (sign == 2) {
                                        endVal1 = endVal1 - parseFloat($("input[alt=" + formulaColumn +"_"+type+"]").val().replace(/,/g, ''));
                                    }
                                }
                            });

                            $("input[alt=" + alt + "]").val(endVal1.toFixed(2));
                        }
                    }
                });
            }else{
                window.top.alert(data.msg, 0);
            }
        },
        error:function(){
            window.top.alert(top.getMessage("ERROR_REQUEST_URL"," "), 0);
        }
    });
};

/** 目前只使用固定报表项计算，后期使用报表公式 * */
function reportCalcAmt(obj,type){
    var typeId = $(obj).attr("type_id");
    $(obj).val(CalcUtil.formatMoney($(obj).val(), 2));
    var sumList = [];
    var i;
    if ('001'==typeId){
        //资产
        var item10011 = ['10001', '10002', '10003', '10004', '10005', '10006', '10007', '10008', '10009', '10010'];
        var item10025 = ['10012', '10013', '10014', '10015', '10016', '10017', '10018', '10019', '10020', '10021', '10022', '10023', '10024'];
        for (i in item10011) {
            sumList.push($("input[alt="+item10011[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10011 = CalcUtil.sum(sumList);

        sumList = [];
        for (i in item10025) {
            sumList.push($("input[alt="+item10025[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10025 = CalcUtil.sum(sumList);
        var amt10026 = CalcUtil.add(amt10011, amt10025);
        $("input[alt=10011_"+type+"]").val(CalcUtil.formatMoney(amt10011, 2));
        $("input[alt=10025_"+type+"]").val(CalcUtil.formatMoney(amt10025, 2));
        $("input[alt=10026_"+type+"]").val(CalcUtil.formatMoney(amt10026, 2));
        //负债
        var item10038 = ['10028', '10029', '10030', '10031', '10032', '10033', '10034', '10035', '10036', '10037'];
        var item10046 = ['10039', '10040', '10041', '10042', '10043', '10044', '10045'];
        sumList = [];
        for (i in item10038) {
            sumList.push($("input[alt="+item10038[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10038 = CalcUtil.sum(sumList);
        sumList = [];
        for (i in item10046) {
            sumList.push($("input[alt="+item10046[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10046 = CalcUtil.sum(sumList);
        var amt10047 = CalcUtil.add(amt10038, amt10046);
        $("input[alt=10038_"+type+"]").val(CalcUtil.formatMoney(amt10038, 2));
        $("input[alt=10046_"+type+"]").val(CalcUtil.formatMoney(amt10046, 2));
        $("input[alt=10047_"+type+"]").val(CalcUtil.formatMoney(amt10047, 2));
        //权益
        var item10055 = ['10049', '10050', '10051', '10052', '10053', '10054'];
        sumList = [];
        for (i in item10055) {
            sumList.push($("input[alt="+item10055[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10055 = CalcUtil.sum(sumList);
        var amt10056 = CalcUtil.add(amt10047, amt10055);
        $("input[alt=10055_"+type+"]").val(CalcUtil.formatMoney(amt10055, 2));
        $("input[alt=10056_"+type+"]").val(CalcUtil.formatMoney(amt10056, 2));
    }else if('002'==typeId){
        //经营活动
        var item10104 = ['10101', '10102', '10103'];
        var item10109 = ['10105', '10106', '10107', '10108'];
        sumList = [];
        for (i in item10104) {
            sumList.push($("input[alt="+item10104[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var r10104 = CalcUtil.sum(sumList);
        sumList = [];
        for (i in item10109) {
            sumList.push($("input[alt="+item10109[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var c10109 = CalcUtil.sum(sumList);
        var j10110 = CalcUtil.subtract(r10104, c10109);
        $("input[alt=10104_"+type+"]").val(CalcUtil.formatMoney(r10104, 2));
        $("input[alt=10109_"+type+"]").val(CalcUtil.formatMoney(c10109, 2));
        $("input[alt=10110_"+type+"]").val(CalcUtil.formatMoney(j10110, 2));
        //投资活动
        var item10117 = ['10112', '10113', '10114', '10115', '10116'];
        var item10122 = ['10118', '10119', '10120', '10121'];
        sumList = [];
        for (i in item10117) {
            sumList.push($("input[alt="+item10117[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var r10117 = CalcUtil.sum(sumList);
        sumList = [];
        for (i in item10122) {
            sumList.push($("input[alt="+item10122[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var c10122 = CalcUtil.sum(sumList);
        var j10123 = CalcUtil.subtract(r10117, c10122);
        $("input[alt=10117_"+type+"]").val(CalcUtil.formatMoney(r10117, 2));
        $("input[alt=10122_"+type+"]").val(CalcUtil.formatMoney(c10122, 2));
        $("input[alt=10123_"+type+"]").val(CalcUtil.formatMoney(j10123, 2));
        //筹资活动
        var item10128 = ['10125', '10126', '10127'];
        var item10132 = ['10129', '10130', '10131'];
        sumList = [];
        for (i in item10128) {
            sumList.push($("input[alt="+item10128[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var r10128 = CalcUtil.sum(sumList);
        sumList = [];
        for (i in item10132) {
            sumList.push($("input[alt="+item10132[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var c10132 = CalcUtil.sum(sumList);
        var j10133 = CalcUtil.subtract(r10128, c10132);
        $("input[alt=10128_"+type+"]").val(CalcUtil.formatMoney(r10128, 2));
        $("input[alt=10132_"+type+"]").val(CalcUtil.formatMoney(c10132, 2));
        $("input[alt=10133_"+type+"]").val(CalcUtil.formatMoney(j10133, 2));
        //期末余额
        var item10137 = ['10135', '10136'];
        sumList = [];
        for (i in item10137) {
            sumList.push($("input[alt="+item10137[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var amt10137 = CalcUtil.sum(sumList);
        $("input[alt=10137_"+type+"]").val(CalcUtil.formatMoney(amt10137, 2));
    }else if('003'==typeId){
        //营业利润
        var ritem10213 = ['10200', '10209', '10210', '10211', '10212'];
        var citem10213 = ['10201', '10202', '10203', '10204', '10205', '10206', '10207', '10208'];
        sumList = [];
        for (i in ritem10213) {
            sumList.push($("input[alt="+ritem10213[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var r10213 = CalcUtil.sum(sumList);
        sumList = [];
        for (i in citem10213) {
            sumList.push($("input[alt="+citem10213[i]+"_"+type+"]").val().replace(/,/g,''));
        }
        var c10213 = CalcUtil.sum(sumList);
        var amt10213 = CalcUtil.subtract(r10213, c10213);
        $("input[alt=10213_"+type+"]").val(CalcUtil.formatMoney(amt10213, 2));
        var amt10216 = CalcUtil.add(amt10213, $("input[alt=10214_"+type+"]").val().replace(/,/g,''));
        amt10216 = CalcUtil.subtract(amt10216, $("input[alt=10215_"+type+"]").val().replace(/,/g,''));
        $("input[alt=10216_"+type+"]").val(CalcUtil.formatMoney(amt10216, 2));
        var amt10218 = CalcUtil.subtract(amt10216, $("input[alt=10217_"+type+"]").val().replace(/,/g,''));
        $("input[alt=10218_"+type+"]").val(CalcUtil.formatMoney(amt10218, 2));
    }
}

//数字格式化
function changeMoney(inStr) {
	var returnStr = inStr;
	var i, charValue, outStr, id = 0,fs=0;//fs 表示负数
	for (i = 0; i < inStr.length; i++) {
		charValue = inStr.charAt(i);
		if (isNaN(parseInt(charValue, 10)) && (charValue != ".")
				&& (charValue != ",") && (charValue != "-")) {
			if(fs==1)
				returnStr = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
			else
				returnStr = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
			return;
		}
		if (i>0 && charValue == "-") {
			if(fs==1)
				returnStr = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
			else
				returnStr = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
			return;
		}
		if (i==0 && charValue == "-") {
			fs = 1;
			inStr = inStr.substr(1, inStr.length);
			i=-1;
		}
		if (charValue != "0") {
			id = 1;
		}
		if (id == 0 && i == 1 && charValue == "0") {
			inStr = inStr.substr(1, inStr.length - 1);
			i = 0;
		}
	}
	var valueArr;
	valueArr = (inStr+"").split(".");
	if (valueArr.length > 2) {
		return;
	}
	var dotStr, dotValue;
	if (valueArr.length == 2) {
		dotValue = valueArr[1];
		if (dotValue.length == 0) {
			dotStr = "";
		} else {
			if (dotValue.length == 1)
				dotStr = dotValue+ "";
			else
				dotStr = dotValue.substring(0, dotValue.length);
		}
	}	
	var intArr;
	intArr = valueArr[0].split(",");
	var intValue = "";
	for (i = 0; i < intArr.length; i++) {
		intValue += intArr[i];
	}
	var intStr = "";
	if (intValue.length > 1 && intValue.charAt(0) == "0" && intValue.charAt(1) != ".") {
		intValue = intValue.substr(1, intValue.length - 1);
	}
	while (intValue.length > 3) {
		intStr = "," + intValue.substring(intValue.length - 3, intValue.length) + intStr;
		intValue = intValue.substring(0, intValue.length - 3);
	}
	intStr = intValue + intStr;
	if (dotStr == null)
		outStr = intStr;
	else
		outStr = intStr + "." + dotStr;
	if(fs==1){
		outStr = "-" + outStr;
	}
	returnStr = outStr;
	return returnStr;
}
