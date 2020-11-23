//加载完成执行
$(function(){
	$(".row_content").mCustomScrollbar({
		advanced:{
			updateOnBrowserResize:true
		},autoHideScrollbar: true
	});
    var $this;
	selectReportMenu();
	//初始化数据
	$.fn.initData = function(data,type){
		var $this = $(this);
		var length = data.length
		for(var i=0;i<length;i++){
			var $tr = $('<tr></tr>');
			$tr.setHtml1(data[i],type);
			$this.append($tr);
		}
	};


	//组装报表项html
	$.fn.setHtml = function(colData){
		if(typeof(colData) == "undefined"){
			return;
		}
		var $obj = $(this);
		var isNullFlag = false;
		if(colData.codeName=="###"){
			isNullFlag = true;
		}
		var $title = $('<td align="left" class="" style="text-align: left;"><b>流动资产</b></td>');
		if(isNullFlag){
			$title.html("");
		}else{
			$title.html(colData.codeName);
		}
		var $sequences = $('<td align="center" class="" style="text-align: center;">1</td>');
		$sequences.text(colData.cnt);
		var $begin = $('<td align="center" class="" style="text-align: center;"></td>');
		var $beginInput = $('<input class="INPUT_TEXT" type="text" maxlength="15" number="double" onkeyup="toMoney(this)">');
		if(!isNullFlag){
			var beginVal = colData.beginVal;
			if (beginVal == "") {
				beginVal = "0.00";
			}
			if (query != "query") {
				$beginInput.attr("alt", colData.codeName);
				$beginInput.attr("readonly", "readonly");
				$beginInput.addClass("readonly-input");
				$beginInput.val(changeMoney(beginVal));
				$begin.append($beginInput);
			} else {
				$begin.append(changeMoney(beginVal));
			}
		}
		var $end = $('<td align="center" class="" style="text-align: center;"></td>');
		var $endInput = $('<input class="INPUT_TEXT" type="text" maxlength="15" number="double" onkeyup="toMoney(this)">');
		if(!isNullFlag){
			$endInput.attr("name",colData.codeColumn+"_END");
			$endInput.attr("alt",colData.codeName);
			var endVal = colData.endVal;
				if(endVal==""){
					endVal = "0.00";
				}
			$endInput.val(changeMoney(endVal));
			$end.append($endInput);
		}
		$obj.append($title);
		$obj.append($sequences);
		$obj.append($begin);
		$obj.append($end);
	};

    //组装报表项html peng-财务改
    $.fn.setHtml1 = function(colData,reportType){
        if(typeof(colData) == "undefined"){
            return;
        }
        var $obj = $(this);
        for(var i=0;i<colData.length;i++){
        	if(i == 0){
                var $title = $('<td align="left"></td>');
                var titleClass="titleClass10";
                var content = colData[i].split('@');
                if(content[1]=="1"){
                    titleClass = "titleClass21";
                }
                $title.html(content[0]);
                $title.attr("class",titleClass);
                $obj.append($title);
			}else{
                var itemAmtBaseAVal = colData[i];
                if(itemAmtBaseAVal==""){
                    itemAmtBaseAVal = "0.00";
                }
                itemAmtBaseAVal = changeMoney(itemAmtBaseAVal);
                var $htmlStr = $('<td align="center" class="" style="text-align: center;"></td>');
                var $itemAmtBaseA = $('<td align="center" class="" style="text-align: center;"><input readonly name="itemAmtBaseAVal" class="INPUT_TEXT" style="border-width:0 0 1px 0;border-color: #6f6b6b;" type="text" maxlength="15" number="double" value="'+itemAmtBaseAVal+'"></td>');
                $htmlStr.append($itemAmtBaseA);
                $obj.append($htmlStr);
			}
		}
    };

    //初始化财务指标数据
    $.fn.initCusFinRatioData = function(classNoList,cusFinRatioDataListMap,cusFinMain){
        var $this = $(this);
        var classStr = "readonly-input";
        var readonlyStr = 'readonly="readonly"';
        if(finRatioEditFlag==1){
            classStr="";
            readonlyStr="";
		}
        var htmlStr = "";
        $.each(classNoList,function(i,parmDic){
        	var index = i+1;
            var cusFinRatioDataList = cusFinRatioDataListMap[parmDic.optCode];
        	htmlStr = htmlStr +'<tr>'
                +'<td style="font-weight: bold;padding-left:50px;">'+index+'、'+parmDic.optName+'</td>';
				if(index==1){
                    htmlStr = htmlStr  +'<td colspan="2"></td>'
						+'<td align="center" style="font-weight: bold;">';
                    if(cusFinRatioDataList!=undefined){
                       var indexYearMap = cusFinRatioDataList[i].scoreMap;
                       if(indexYearMap!=undefined && indexYearMap!=null){
                           $.each(indexYearMap,function(key,value){
                               console.info("key: " + key + ", Value: " + value );
                               htmlStr = htmlStr +'<div style="width:18%;display: inline-block;margin-right: 5px;">'+key+'年</div>';
                           });
                       }
                        // +'<div style="width:18%;display: inline-block;margin-right: 5px;">2017年</div>'
                        // +'<div style="width:18%;display: inline-block;margin-right: 5px;">2018年</div>';
                    }
                    htmlStr = htmlStr  +'</td>';
                }else{
                    htmlStr = htmlStr  +'<td colspan="3"></td>';
				}
           		htmlStr = htmlStr  +'</tr>';

			if(cusFinRatioDataList!=undefined){
                $.each(cusFinRatioDataList,function(i,cusFinRatioData) {
                	htmlStr = htmlStr +	'<tr style="text-align: center;">'
                    	+'<td style="padding-left:70px; text-align: left;">'+cusFinRatioData.formName+'</td>'
                        +'<td><input style="width:55%;" class="readonly-input text-center"  readonly="readonly" type="text" value="'+cusFinRatioData.originalFormVal+'"></td>'
                        +'<td><input style="width:55%;"  name="'+cusFinRatioData.formNo+'" class="'+classStr+' text-center inputVal"  '+readonlyStr+' type="text" value="'+cusFinRatioData.formVal+'"></td>'
                        +'<td>';
						var indexYearMap = cusFinRatioDataList[i].scoreMap;
						if(indexYearMap!=undefined && indexYearMap!=null) {
							$.each(indexYearMap, function (key, value) {
								htmlStr = htmlStr + '<input style="width:18%;margin-right: 5px;"  class="readonly-input text-center"  readonly="readonly" type="text" value="' + value + '">';
							});
						}else{
                            htmlStr = htmlStr + '<input style="width:55%;margin-right: 5px;"  class="readonly-input text-center"  readonly="readonly" type="text" value="">';
						}
                    htmlStr = htmlStr +'</td></tr>';

                });
            }
        });
        $this.append(htmlStr);
    };


	//初始化数据
	if(dataMap !== undefined && dataMap != null){
		var capList = dataMap.capList;//资产负债表资产类
		var cashList = dataMap.cashList; //现金流量
		var proDataList = dataMap.proDataList; //利润分配表
        var $obj;
		if(capList!==undefined&&capList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinCapData]");
			$obj.find("table[name=zc_table]").find("tbody").initData(capList,'1');
		}
		if(cashList!==undefined&&cashList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinCashData]");
			$obj.find("table tbody").initData(cashList,'3');
		}
		if(proDataList!==undefined&&proDataList.length>0){
			$obj = $(".content_col div[data-ctrl=cusFinProData]");
			$obj.find("table tbody").initData(proDataList,'2');
		}
	}

	//左侧三个菜单切换
	$(".content_col .row_ul ul li").click(function(){
		var $this = $(this);
		selectReportNode($this);
	});
});

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
    var $this;
	if(finCapFlag){//上传了资产负债表，默认选中资产负债表
        $this = $("li[data-ctrl=cusFinCapData]");
        selectReportNode($this);
	}else if(finCashFlag){//没有上传了资产负债表，但是上传了现金流量表，默认选中现金流量表
        $("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
        $this = $("li[data-ctrl=cusFinCashData]");
        selectReportNode($this);
	}else if(finProFlag){//没有上传了资产负债表、现金流量表，但是上传了利润分配表，默认选中利润分配表
        $("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
        $this = $("li[data-ctrl=cusFinProData]");
        selectReportNode($this);
	}
}

//设置计算项 不允许输入
function setInputReadOnly($endInput,reportType,colData){
	var codeColumn = colData.codeColumn;
	var inputType = colData.operationType;
	if(inputType== "2"){
		$endInput.addClass("readonly-input");
	}
}

/**
 * 保存数据
 * @param {Object} obj
 */
function submitData(obj,reportType){
	var $obj = $(obj);
	var cusNo = $("input[name=cusNo]").val();
	var finRptType = $("input[name=finRptType]").val();
	var finRptDate = $("input[name=finRptDate]").val();
	var accRule = $("input[name=accRule]").val();
	var relationCorpName = $("input[name=relationCorpName]").val();
	var relationCorpNo = $("input[name=relationCorpNo]").val();
	var url = $obj.attr("action");
	var dataParmList = [];
	$(obj).find("tbody tr").each(function(i, o) {
		var entity = {};
		entity.codeColumn = $(this).find("input[name=codeColumn]").val();
		entity.beginVal = $(this).find("input[name=beginVal]").val();
		entity.endVal = $(this).find("input[name=endVal]").val();
		dataParmList.push(entity);
	});
	$.ajax({
		type : "post",
		url : url,
		data : {
			ajaxData : JSON.stringify(dataParmList),
			cusNo : cusNo,
			finRptType : finRptType,
			finRptDate : finRptDate,
			accRule : accRule,
			relationCorpName : relationCorpName,
			relationCorpNo : relationCorpNo
		},
		async : false,
		success : function(data) {
			if (data.flag == "success") {
				window.top.alert(top.getMessage("SUCCEED_OPERATION"), 1);
			} else if (data.flag == "error") {
				window.top.alert(data.msg, 1);
			}
		},
		error : function() {
			window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
		}
	});
};

/**
 * 保存数据
 * @param {Object} obj
 */
function submitDataNew(obj,reportType){
    var $obj = $(obj);
    var cusNo = $("input[name=cusNo]").val();
    var finRptType = $("input[name=finRptType]").val();
    var finRptDate = $("input[name=finRptDate]").val();
    var accRule = $("input[name=accRule]").val();
    var relationCorpName = $("input[name=relationCorpName]").val();
    var relationCorpNo = $("input[name=relationCorpNo]").val();
    var url = $obj.attr("action");
    var dataParmList = [];
    if(reportType != "5"){
        $(obj).find("tbody tr").each(function(i, o) {
            var entity = {};
            entity.reportItemId = $(this).find("input[name=codeColumn]").val();
            entity.itemAmtBaseA = $(this).find("input[name=itemAmtBaseAVal]").val().replace(/\,/g, "" );
            entity.itemAmtA = $(this).find("input[name=itemAmtAVal][class]").val().replace(/\,/g, "" );
            entity.itemAmtBaseB = $(this).find("input[name=itemAmtBaseBVal]").val().replace(/\,/g, "" );
            entity.itemAmtB = $(this).find("input[name=itemAmtBVal][class]").val().replace(/\,/g, "" );
            dataParmList.push(entity);
        });
	}else{
        $(obj).find("tbody tr").each(function(i, o) {
            var entity = {};
            entity.reportItemId = $(this).find("input[name=reportItemId]").val();
            entity.showName = $(this).find("input[name=showName]").val();
            entity.amtProperties = $(this).find("input[name=amtProperties]").val();
            entity.itemAmtBaseA = $(this).find("input[name=itemAmtBaseA]").val().replace(/\,/g, "" );
            dataParmList.push(entity);
        });
	}

	if(reportType=="1"){
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
	}

    $.ajax({
        type : "post",
        url : url,
        data : {
            ajaxData : JSON.stringify(dataParmList),
            cusNo : cusNo,
            finRptType : finRptType,
            finRptDate : finRptDate,
            accRule : accRule,
            relationCorpName : relationCorpName,
            relationCorpNo : relationCorpNo,
            reportType:reportType
        },
        async : false,
        success : function(data) {
            if (data.flag == "success") {
                window.top.alert(top.getMessage("SUCCEED_OPERATION"), 1);
            } else if (data.flag == "error") {
                window.top.alert(data.msg, 1);
            }
        },
        error : function() {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};



/**
 * 保存科目余额表数据
 * @param {Object} obj
 */
function submitSubjectData(obj,reportType){
	var $obj = $(obj);
	var cusNo = $("input[name=cusNo]").val();
	var finRptType = $("input[name=finRptType]").val();
	var finRptDate = $("input[name=finRptDate]").val();
	var url = $obj.attr("action");
	var dataParmList = [];
	$(obj).find(".inputValTr").each(function(i, o) {
		var entity = {};
		entity.id = $(this).attr("id");
		entity.subjectCusName = $(this).find("input[name=subjectCusName]").val();
		entity.subjectAmt = parseFloat($(this).find("input[name=subjectAmt]").val().replace(/\,/g, "" ));
		entity.amtProperties = $(this).find("input[name=amtProperties]").val();
		dataParmList.push(entity);
	});
	$.ajax({
		url : url,
		data : {
			ajaxData : JSON.stringify(dataParmList),
			cusNo : cusNo,
			finRptType : finRptType,
			finRptDate : finRptDate
		},
		async : false,
		success : function(data) {
			if (data.flag == "success") {
				window.top.alert(data.msg, 1);
			} else if (data.flag == "error") {
				window.top.alert(data.msg, 0);
			}
		},
		error : function() {
			window.top.alert(top.getMessage("FAILED_OPERATION", " "), 1);
		}
	});
};
// //从数据库中获取该报表元素所有参与计算的元素项
// function endValChange(obj){
// 	var codeColumn = $(obj).attr("alt");
// 	var codeColumn1 = $(obj).attr("alt");
// 	var temp = parseFloat($(obj).val().replace(/,/g,''));
// 	$(obj).val(temp.toFixed(2));
// 	var endVal = temp - parseFloat($(obj).parent().find("input[name=endVal1]").val()) ;
// 	$.ajax({
// 		url:webPath+"/cusFinFormula/checkFormulaAjax?formulaColumn="+codeColumn,
// 		dataType:"json",
// 		type:"POST",
// 		success:function(data){
// 			if(data.flag == "success"){
// 				$.each(data.cusFinFormulaList,function(i,cusFinFormula){
// 					codeColumn = cusFinFormula.codeColumn;
// 					var sign = cusFinFormula.formulaType;//运算符号，1是+，2是-
// 					if($("input[alt="+codeColumn+"]").val()!=undefined&&$("input[alt="+codeColumn+"]").val()!=null){
// 						if(sign == "1"){//
// 							endVal = parseFloat($("input[alt="+codeColumn+"]").val().replace(/,/g,'')) + endVal;
// 						}else{
// 							endVal = parseFloat($("input[alt="+codeColumn+"]").val().replace(/,/g,'')) - endVal;
// 						}
// 					}
// 					$("input[alt="+codeColumn+"]").val(endVal.toFixed(2));
// 				});
//
// 				$(obj).parent().find("input[name=endVal1]").val(temp.toFixed(2));
// //				var wxjdyzchj = parseFloat($("input[alt=ADbi]").val()) + parseFloat($("input[alt=ADbk]").val());
// //				$("input[alt=ADbn]").val(wxjdyzchj.toFixed(2));  //无形及递延资产合计
// //				var zczj = parseFloat($("input[alt=ADas]").val()) + parseFloat($("input[alt=ADax]").val()) + parseFloat($("input[alt=ADbh]").val()) + wxjdyzchj + parseFloat($("input[alt=ADbo]").val()) + parseFloat($("input[alt=ADbp]").val()) + parseFloat($("input[alt=ADbq]").val());
// //				$("input[alt=ADbr]").val(zczj.toFixed(2));  //资产总计
// 				$.each($(".INPUT_TEXT"),function(i,obj1){
// 					var alt=$(obj1).attr("alt");
// 					if(alt!=codeColumn1&&alt!=undefined&&dataMap[alt]!=undefined&&dataMap[alt].length>0){
// 						var endVal1=parseFloat("0.00");
// 						$.each(dataMap[alt],function(i,cusFinFormula1){
// 							var formulaColumn = cusFinFormula1.formulaColumn;
// 							var sign = cusFinFormula1.formulaType;//运算符号，1是+，2是-
// 							if($("input[alt="+formulaColumn+"]").val()!=undefined&&$("input[alt="+formulaColumn+"]").val()!=null){
// 								if(sign == 1){//
// 									endVal1 = parseFloat($("input[alt="+formulaColumn+"]").val().replace(/,/g,'')) + endVal1;
// 								}else if(sign == 2){
// 									endVal1 =endVal1-parseFloat($("input[alt="+formulaColumn+"]").val().replace(/,/g,''))  ;
// 								}
// 							}
// 						});
//
// 						$("input[alt="+alt+"]").val(endVal1.toFixed(2));
// 					}
// 				});
// 			}else{
// 				window.top.alert(data.msg, 0);
// 			}
// 		},
// 		error:function(){
// 			window.top.alert(top.getMessage("ERROR_REQUEST_URL"," "), 0);
// 		}
// 	});
// };

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

//继续上传
function continueUpload(){
	var relationCorpName  = $("input[name=relationCorpName]").val();
	if(relationCorpName!=""){
		window.location.href=webPath+"/cusFinMain/getListPageForPerson?cusNo="+cusNo;
	}else{
		window.location.href=webPath+"/cusFinMain/getListPage1?cusNo="+cusNo;
	}
}


//编辑保存财务指标
function submitFinRatioData(obj){
    var $obj = $(obj);
    var cusNo = $("input[name=cusNo]").val();
    var finRptType = $("input[name=finRptType]").val();
    var finRptDate = $("input[name=finRptDate]").val();
    var url = $obj.attr("action");
    var dataParmList = [];
    $(obj).find(".inputVal").each(function(i, o) {
        var entity = {};
        entity.formNo = $(this).attr("name");
        entity.formVal = $(this).val();
        dataParmList.push(entity);
    });
    $.ajax({
        url : url,
        data : {
            ajaxData : JSON.stringify(dataParmList),
            cusNo : cusNo,
            finRptType : finRptType,
            finRptDate : finRptDate
        },
        async : false,
        success : function(data) {
            if (data.flag == "success") {
                window.top.alert(data.msg, 1);
            } else if (data.flag == "error") {
                window.top.alert(data.msg, 0);
            }
        },
        error : function() {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 1);
        }
    });
}
