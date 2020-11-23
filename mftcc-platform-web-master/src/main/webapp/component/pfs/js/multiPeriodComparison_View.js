//加载完成执行
$(function(){
	$(".row_content").mCustomScrollbar({
		advanced:{ 
			updateOnBrowserResize:true 
		},autoHideScrollbar: true
	});
	//上传成功后判断
	if(teplateType == "1"){//上传的是资产负债表模板
		$("li[data-ctrl=cusFinCashData]").remove();
		$("li[data-ctrl=cusFinProData]").remove();
	}
    var $this;
	if(teplateType == "2"){//上传的是利润分配表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinProData]");
		selectReportNode($this);
		$("li[data-ctrl=cusFinCapData]").remove();
		$("li[data-ctrl=cusFinCashData]").remove();
	}
	if(teplateType == "3"){//上传的是现金流量表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinCashData]");
		selectReportNode($this);
		$("li[data-ctrl=cusFinCapData]").remove();
		$("li[data-ctrl=cusFinProData]").remove();
	}
	selectReportMenu();
	//初始化数据
	$.fn.initData = function(data,type,cusFinMain){
		var $this = $(this);
		var length = data.length;
		for(var i=0;i<length;i++){
			var $tr = $('<tr></tr>');
			$tr.setHtml1(data[i],type,cusFinMain);
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
	
	//组装报表项html
	$.fn.setHtml1 = function(colData,reportType,cusFinMain){
		if(typeof(colData) == "undefined"){
			return;
		}
		var $obj = $(this);
		var $title = $('<td align="left"></td>');
		var padleft = colData.cnt.length;
		var fontShowStyle = colData.fontShowStyle;
		var titleClass="titleClass10";
		if(padleft == 2 && fontShowStyle == "1"){
			titleClass = "titleClass10";
		}else if(padleft == 4 && fontShowStyle == "1"){
			titleClass = "titleClass20";
		}else if(padleft == 6 && fontShowStyle == "1"){
			titleClass = "titleClass30";
		}else if(padleft == 2 && fontShowStyle == "2"){
			titleClass = "titleClass11";
		}else if(padleft == 4 && fontShowStyle == "2"){
			titleClass = "titleClass21";
		}else if(padleft == 6 && fontShowStyle == "2"){
			titleClass = "titleClass31";
		}else if(fontShowStyle == "3"){
			titleClass = "titleClass20";
		}
		$title.html(colData.codeName);
		$title.attr("class",titleClass);
		//期初项/上期数
		var $begin = $('<td align="center" class="" style="text-align: center;"><input name="codeColumn" type="text" style="display:none;" value="'+colData.codeColumn+'" ></td>');
		var $beginInput = $('<input name="beginVal" class="INPUT_TEXT" type="text" maxlength="15" number="double" >');
		var beginVal = colData.beginVal;
		if(beginVal==""){
			beginVal = "0.00";
		}
		$beginInput.attr("readonly","readonly");
		$beginInput.addClass("readonly-input");
		$beginInput.val(changeMoney(beginVal));
		$begin.append($beginInput);
		var endVal = colData.endVal;
		if(endVal==""){
			endVal = "0.00";
		}
		//期末数/当前数
		var $end = $('<td align="center" class="" style="text-align: center;"><input name="endVal1" type="text" style="display:none;" value="'+endVal+'" ></td>');
		var $endInput = $('<input name="endVal" class="INPUT_TEXT" type="text" maxlength="15" number="double" onkeyup="toMoney(this)" onchange="endValChange(this)" >');
		if(colData.inputType == "2"){
			$endInput.attr("readonly",true);
		}
		//设置计算项 不允许输入
		setInputReadOnly($endInput,reportType,colData);
		if(cusFinMain.finRptSts == "1"){   //当前财务确认后，显示的时候不能修改
			$endInput.addClass("readonly-input");
		}
		$endInput.attr("alt",colData.codeColumn);
		$endInput.val(changeMoney(endVal));
		$end.append($endInput);
		$obj.append($title);
		$obj.append($begin);
		$obj.append($end);
		if(colData.inputType == "3"){//如果是空白型，则不需要显示数据
			$begin.find("*").hide();
			$end.find("*").hide();
		}
	};
	
	//初始化数据
	if(dataMap !== undefined && dataMap != null){
        var capThColList = dataMap.capThColList;//资产负债表head
        var i,j,value;
        if(capThColList!==undefined){
            var cusFinCapData_thead = $("#cusFinCapData_thead");
            var theadCap = "";
            for(i=0;i<capThColList.length;i++){
                theadCap = theadCap+"<th>"+capThColList[i]+"</th>";
            }
            cusFinCapData_thead.html(theadCap);
        }
        //记载资产负债表head
        var capTitleColList = dataMap.capTitleColList;//资产负债表财务指标项名称
        var capColLists = dataMap.capColLists;//资产负债表财务指标项名称
        var trCap;
        if (capTitleColList!==undefined){
        	trCap = "";
            for(i=0;i<capTitleColList.length;i++){

                trCap = trCap+'<tr><td align="center" class="titleClass10">'+capTitleColList[i]+'</td>';
                for(j=0;j<capColLists.length;j++){
                    value = capColLists[j][i];
                    if (capColLists[j][i]==undefined){
                        value = "0.00";
                    }
                    trCap = trCap + '<td align="center" class="" style="text-align: center;">'+value+'</td>';
                }
                trCap = trCap +'</tr>';
            }
			$("#cusFinCapData_tbody").html(trCap);
		}
		//加载现金流量表head
		var cashThColList = dataMap.cashThColList;
        if(cashThColList!==undefined){
            var theadCash = "";
            for(i=0;i<cashThColList.length;i++){
                theadCash = theadCash+"<th>"+cashThColList[i]+"</th>";
            }
            $("#cusFinCashData_thead").html(theadCash);
        }
        //加载现金流量表财务指标项数据
        var cashTitleColList = dataMap.cashTitleColList;//现金流量表财务指标项名称
        var cashColLists = dataMap.cashColLists;//现金流量表财务指标项名称
        if (cashTitleColList!==undefined){
            trCap = "";
            for(i=0;i<cashTitleColList.length;i++){

                trCap = trCap+'<tr><td align="center" class="titleClass10">'+cashTitleColList[i]+'</td>';
                for(j=0;j<cashColLists.length;j++){
                    value = cashColLists[j][i];
                    if (cashColLists[j][i]==undefined){
                        value = "0.00";
                    }
                    trCap = trCap + '<td align="center" class="" style="text-align: center;">'+value+'</td>';
                }
                trCap = trCap +'</tr>';
            }
            $("#cusFinCashData_tbody").html(trCap);
        }

		//记载利润分配表head
        var proThColList = dataMap.proThColList;
        if(proThColList!==undefined){
            var theadPro = "";
            for(i=0;i<proThColList.length;i++){
                theadPro = theadPro+"<th>"+proThColList[i]+"</th>";
            }
            $("#cusFinProData_thead").html(theadPro);
        }

        //加载利润分配表财务指标项数据
        var proTitleColList = dataMap.proTitleColList;//利润分配表财务指标项名称
        var proColLists = dataMap.proColLists;//利润分配表财务指标项数据
        if (proTitleColList!==undefined){
            trCap = "";
            for(i=0;i<proTitleColList.length;i++){

                trCap = trCap+'<tr><td align="center" class="titleClass10">'+proTitleColList[i]+'</td>';
                for(j=0;j<proColLists.length;j++){
                	value = proColLists[j][i];
                	if (proColLists[j][i]==undefined){
                        value = "0.00";
					}
                    trCap = trCap + '<td align="center" class="" style="text-align: center;">'+value+'</td>';
                }
                trCap = trCap +'</tr>';
            }
            $("#cusFinProData_tbody").html(trCap);
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
	if(finCapFlag && !finCashFlag && !finProFlag){
		$this = $("li[data-ctrl=cusFinCapData]");
		selectReportNode($this);
	}
	if(finCapFlag && finCashFlag && !finProFlag){
		$this = $("li[data-ctrl=cusFinCapData]");
		selectReportNode($this);
	}
	if(finCapFlag && finCashFlag && finProFlag){
		$this = $("li[data-ctrl=cusFinCapData]");
		selectReportNode($this);
	}
	if(!finCapFlag && finCashFlag && !finProFlag){//上传的是现金流量表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinCashData]");
		selectReportNode($this);
	}
	if(!finCapFlag && finCashFlag && finProFlag){//上传的是现金流量表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinCashData]");
		selectReportNode($this);
	}
	if(!finCapFlag && !finCashFlag && finProFlag){//上传的是利润分配表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinProData]");
		selectReportNode($this);
	}
	if(finCapFlag && !finCashFlag && finProFlag){//上传的是利润分配表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinProData]");
		selectReportNode($this);
	}
	if(!finCapFlag && finCashFlag && finProFlag){//上传的是利润分配表模板
		$("li[data-ctrl=cusFinCapData]").removeClass("li_selected");
		$this = $("li[data-ctrl=cusFinProData]");
		selectReportNode($this);
	}
}

//设置计算项 不允许输入
function setInputReadOnly($endInput,reportType,colData){
	var codeColumn = colData.codeColumn;
	var inputType = colData.inputType;
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
	/*if(reportType == "1"){
		var zcADbr = Number($obj.find("input[alt=ADbr]").val());
		var zcADdi = Number($obj.find("input[alt=ADdi]").val());
		if(zcADbr != zcADdi){
			window.top.alert(top.getMessage("FIRST_OPERATION","对资产负债表中的资产总计和负债及所有者权益总计的平衡条件的检查"), 0);
			return;
		}
	}*/
	LoadingAnimate.start();
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
				LoadingAnimate.stop();
			if (data.flag == "success") {
				window.top.alert(top.getMessage("SUCCEED_OPERATION"), 0);
			} else if (data.flag == "error") {
				window.top.alert(data.msg, 1);
			}
		},
		error : function() {
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("FAILED_OPERATION", " "), 1);
		}
	});
};
//从数据库中获取该报表元素所有参与计算的元素项
function endValChange(obj){
	var codeColumn = $(obj).attr("alt");
	var codeColumn1 = $(obj).attr("alt");
	var temp = parseFloat($(obj).val().replace(/,/g,''));
	$(obj).val(temp.toFixed(2));
	var endVal = temp - parseFloat($(obj).parent().find("input[name=endVal1]").val()) ;
	$.ajax({
		url:webPath+"/cusFinFormula/checkFormulaAjax?formulaColumn="+codeColumn,
		dataType:"json",
		type:"POST",
		success:function(data){
			if(data.flag == "success"){
				$.each(data.cusFinFormulaList,function(i,cusFinFormula){
					codeColumn = cusFinFormula.codeColumn;
					var sign = cusFinFormula.formulaType;//运算符号，1是+，2是-	
					if($("input[alt="+codeColumn+"]").val()!=undefined&&$("input[alt="+codeColumn+"]").val()!=null){
						if(sign == "1"){//
							endVal = parseFloat($("input[alt="+codeColumn+"]").val().replace(/,/g,'')) + endVal;
						}else{
							endVal = parseFloat($("input[alt="+codeColumn+"]").val().replace(/,/g,'')) - endVal;
						}
					}
					$("input[alt="+codeColumn+"]").val(endVal.toFixed(2));
				});
			
				$(obj).parent().find("input[name=endVal1]").val(temp.toFixed(2));
//				var wxjdyzchj = parseFloat($("input[alt=ADbi]").val()) + parseFloat($("input[alt=ADbk]").val());
//				$("input[alt=ADbn]").val(wxjdyzchj.toFixed(2));  //无形及递延资产合计
//				var zczj = parseFloat($("input[alt=ADas]").val()) + parseFloat($("input[alt=ADax]").val()) + parseFloat($("input[alt=ADbh]").val()) + wxjdyzchj + parseFloat($("input[alt=ADbo]").val()) + parseFloat($("input[alt=ADbp]").val()) + parseFloat($("input[alt=ADbq]").val());
//				$("input[alt=ADbr]").val(zczj.toFixed(2));  //资产总计
				$.each($(".INPUT_TEXT"),function(i,obj1){
					var alt=$(obj1).attr("alt");
					if(alt!=codeColumn1&&alt!=undefined&&dataMap[alt]!=undefined&&dataMap[alt].length>0){
						var endVal1=parseFloat("0.00");
						$.each(dataMap[alt],function(i,cusFinFormula1){
							var formulaColumn = cusFinFormula1.formulaColumn;
							var sign = cusFinFormula1.formulaType;//运算符号，1是+，2是-	
							if($("input[alt="+formulaColumn+"]").val()!=undefined&&$("input[alt="+formulaColumn+"]").val()!=null){
								if(sign == 1){//
									endVal1 = parseFloat($("input[alt="+formulaColumn+"]").val().replace(/,/g,'')) + endVal1;
								}else if(sign == 2){
									endVal1 =endVal1-parseFloat($("input[alt="+formulaColumn+"]").val().replace(/,/g,''))  ;
								}
							}
						});
						
						$("input[alt="+alt+"]").val(endVal1.toFixed(2));
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
	valueArr = inStr.split(".");
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
