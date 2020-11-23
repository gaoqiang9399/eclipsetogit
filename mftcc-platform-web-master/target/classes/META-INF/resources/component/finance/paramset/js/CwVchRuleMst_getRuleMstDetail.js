/**
 * 使用闭包
 */
var CwVchRuleMstJs = function(window, $) {
	
	var _init = function () {
		detailInitfun();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);


/*初始化方法*/

function detailInitfun(){
    var i;
	for (i = 0; i < wordbean.length; i++) {
		if(bean.pzProofNo==wordbean[i].pzProofNo){
			$("#pzType").append("<option selected=\"selected\" value=\""+wordbean[i].pzProofNo+"\">"+wordbean[i].pzPrefix+"</option>");
		}else{
			$("#pzType").append("<option value=\""+wordbean[i].pzProofNo+"\">"+wordbean[i].pzPrefix+"</option>");
		}
	}
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	//$("#ywtype").val(bean.txType);
	//给业务类赋值
	for(i = 0;i<listmap.length;i++){
		if(bean.txType==listmap[i].key){
			$("#txType").append("<option selected=\"selected\" value=\""+listmap[i].key+"\">"+listmap[i].value+"</option>");
		}
		/* else{
			$("#txType").append("<option value=\""+listmap[i].key+"\">"+listmap[i].value+"</option>");
		} */
	}
	//给取值 赋值
	for ( var j = 0; j < paymap.length; j++) { 
			$("#payItems").append("<option value=\""+paymap[j].key+"\">"+paymap[j].value+"</option>");
	} 
	
	//适用交易
	//$("#adaptProduct").prop('outerHTML');
	getAdaptDataByType();
	/*for ( var j = 0; j < adaptlistmap.length; j++) { 
			var adaptstr = "";
			adaptstr += "<span style=\"white-space: nowrap;float:left;\"> <input type=\"checkbox\"  class=\"inputstyle1 adaptProduct\" value=\""+adaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+adaptlistmap[j].key+"\"><label  for=\"adapt"+adaptlistmap[j].key+"\">"+adaptlistmap[j].value+"</label></span>";
			$("#adaptProduct").append(adaptstr);
	} */
	
	var unCheckedBoxs = $("input[name='productno']").not("input:checked");
	var prdtno = bean.prdtNo;
	var arr = prdtno.split('@');
	//添加选中的适用交易
	unCheckedBoxs.each(function(){
		 var protype = $(this).val();
		 for(var i=0;i<arr.length;i++){
			 var temtype = arr[i];
			 if(protype==temtype){
				 $(this).attr({checked:true});
			 }
			}
	 });
	//
	$("#ywName").append("<option value=\""+bean.txCode+"\">"+bean.txName+"</option>");
	$("#traceNo").val(traceNo);
	
	giveTabVal();
  	
			clickCashFlow();
			operatEvent();//增加一行，删除一行的方法
			//全部选中事件
			$("#adapt0").click(function(){
				var flage =$(this).is(":checked");//选中为true，否则为false
			    if(flage){
			    	$("input[name='productno']").each(function() {
						$(this).prop("checked", true); 
					}); 
			    }else{
			    	$("input[name='productno']").each(function() { 
						$(this).prop("checked", false); 
					}); 
			    }
			});
			
		// 给类似于放大镜的添加点击事件createShowDialog,openComItemDialog
		 $("#tab").delegate(".comitem_select", "click", function() {
		 	var that = $(this);
		 	openComItemDialog('2', function(data) {
		 		if (data) {
		 			// $('.comitem_select').prev('input').val(data.id);
		 			that.prev('input').val(data.id+"/"+data.name);
		 			// $('#accNo').val(data.id);
		 		}
		 	});
		 });
		//给价税分离添加change事件
		$("#tab").delegate(".priceTaxFlag", "change", function() {
			var that = $(this);
			//var priceTaxFlag = $(".priceTaxFlag option:selected").val();
			var priceTaxFlag = $(this).val();
			var trObj = $(this).parents('tr');	
			var idval = trObj.attr("id");
			var son1 = $("#"+idval+"_son1").length;
			
			if(priceTaxFlag==1){
				//查看是否有子元素，如果有子元素直接显示
				if(son1>0){
					$("#"+idval+"_son1").show();
				}else{
					addRowForPriceTax(that);
				}
			}else if(priceTaxFlag==2){
				//查看是否有子元素，如果有子元素，直接隐藏即可，
				if(son1>0){
					//隐藏子元素
					$("#"+idval+"_son1").hide();
				}else{
					subRowForPriceTax(that);
				}
			}
		});
		 $("#txType").change(function() {
				$("#ywName").html("");
				getYewuDataList();

		});
		//给取值加change事件
		$("#tab").delegate(".payItems", "change", function() {
		
			    var stval = $(this).val();
				var tdObj = $(this).parent('td');
				var nexttd = tdObj.next();
				var nntd = nexttd.next();
				if(stval=='cashBankAmt'){
					nexttd.hide();
					nntd.show();
				}else{
					nexttd.show();
					nntd.hide();
				}
		});
}

$(".adaptProduct").click(function(){
	var unCheckedBoxs = $("input[name='productno']").not("input:checked");
   var prolen = unCheckedBoxs.length;
   if(prolen!=0){
   	$("#adapt0").prop("checked", false); 
   } 
   var unCheckedBox2 = $("input[name='productno']").not("input:checked");
   var prolen2 = unCheckedBox2.length;
   if(prolen2==1){
   	var flage =$("#adapt0").is(":checked");//选中为true，否则为false
   	if(!flage){
   		$("#adapt0").prop("checked", true); 
   	}
   }

}); 
//修改适应交易
function getAdaptDataByType(){
	var txtypeVal = $("#txType option:selected").val();
	$("#adaptProduct").html("");
    var j;
    var adaptstr;
	if(txtypeVal=='01'){
		//适用交易
		for (j = 0; j < bdaptlistmap.length; j++) {
			adaptstr = "";
			adaptstr += "<span style=\"white-space: nowrap;float:left;\"> <input type=\"checkbox\"  class=\"inputstyle1 adaptProduct\" value=\""+bdaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+bdaptlistmap[j].key+"\"><label  for=\"adapt"+bdaptlistmap[j].key+"\">"+bdaptlistmap[j].value+"</label></span>";
			$("#adaptProduct").append(adaptstr);
	}
	}else{
		//适用交易
		for (j = 0; j < adaptlistmap.length; j++) {
			adaptstr = "";
			adaptstr += "<span style=\"white-space: nowrap;float:left;\"> <input type=\"checkbox\"  class=\"inputstyle1 adaptProduct\" value=\""+adaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+adaptlistmap[j].key+"\"><label  for=\"adapt"+adaptlistmap[j].key+"\">"+adaptlistmap[j].value+"</label></span>";
			$("#adaptProduct").append(adaptstr);
	}
	}
	//getYewuDataList();
	//$("#tab").html("");
}

/**
 * 在页面中添加值
 */
function giveTabVal() {
	var temstr = '';
	var datalen = rulelistbean.length;
	if (datalen == 0) {
	} else {
		$("#tab").html('');
	}
	
	for ( var i = 0; i < rulelistbean.length; i++) {
		var paystr = '';
		var stystr ='';
		var stystr2 ='';
		//alert(rulelistbean[i].payItems);
		if(rulelistbean[i].payItems=='cashBankAmt'){
			stystr ="style=\"display:none\"";
			stystr2 ="";
			
		}else{
			stystr ="";
			stystr2 ="style=\"display:none\"";
		}
		for ( var j = 0; j < paymap.length; j++) {
			if (paymap[j].key == rulelistbean[i].payItems) {
				paystr += "<option selected=\"selected\" value=\""
						+ paymap[j].key + "\">" + paymap[j].value + "</option>";
			} else {
				paystr += "<option value=\"" + paymap[j].key + "\">"
						+ paymap[j].value + "</option>";
			}

		}
		var comstr = "";
		if (rulelistbean[i].cashType && rulelistbean[i].cashTypeName) {
			comstr = rulelistbean[i].cashType + "/"
					+ rulelistbean[i].cashTypeName;
		}
		var ruleaccNo = "";
		if (rulelistbean[i].accNo) {
			ruleaccNo = rulelistbean[i].accNo;
		}
		var ruledcind = "";
		if (rulelistbean[i].dcInd == 1) {
			ruledcind = "<option value=\"2\">贷</option>";
		} else {
			ruledcind = "<option value=\"1\">借</option>";
		}
		
		var priceTaxFlagStr = "";
		
		if (rulelistbean[i].priceTaxFlag) {
			if(rulelistbean[i].priceTaxFlag==1){
				priceTaxFlagStr +="<select class=\"inputstyle3 priceTaxFlag\">";
				priceTaxFlagStr +="<option value=\"1\">计税</option>";
				priceTaxFlagStr +="<option value=\"2\">不计税</option>";
				priceTaxFlagStr +="</select>";
			}else{
				priceTaxFlagStr +="<select class=\"inputstyle3 priceTaxFlag\">";
				priceTaxFlagStr +="<option value=\"2\">不计税</option>";
				priceTaxFlagStr +="<option value=\"1\">计税</option>";
				priceTaxFlagStr +="</select>";
			}
		}
		var trid = "";
		if(rulelistbean[i].trId){
			trid = rulelistbean[i].trId;
		}else{
			//为了保证trid的唯一性，所以这里需要创建一个唯一的id
			trid = i;
		}
		temstr +=" <tr id=\""+trid+"\"> ";
		temstr += "<td  align=\"center\" class=\"zhaiyao\"><input type=\"text\" class=\"inputstyle2 edit_subject\" name=\"zhaiyao"
				+ i + "\" value=\"" + rulelistbean[i].txRemark + "\"/></td>";
		temstr += "<td  align=\"center\" class=\"dcind\" ><select class=\"inputstyle3 dcind\"><option selected=\"selected\" value=\""
				+ rulelistbean[i].dcInd
				+ "\">"
				+ rulelistbean[i].dcIndName
				+ "</option>" + ruledcind + "</select></td>";
		//onclick=\"autoComPleter(this, '2',changeFont)\" 
		temstr += "<td  align=\"center\" class=\"accNo\"><input class=\"form-control form-warp accNo\" style=\"width: 92%;\"  type=\"text\" id=\"accNo\" name=\"accNo\" value=\""
				+ ruleaccNo + "\"  onclick=\"autoComPleter(this, '0')\"  autocomplete=\"off\"  />";
		temstr += "<span class=\"glyphicon glyphicon-search search-addon comitem_select \"></span></td>";
		temstr += "<td  align=\"center\" class=\"payItems\" ><select class=\"inputstyle2 payItems\"> "
				+ paystr + " </select></td>";
		/*var payitstr = "<td  align=\"center\" class=\"payItems\" ><select class=\"inputstyle2 payItems\"> "
			+ paystr + " </select></td>";*/
		temstr += "<td  align=\"center\"  class=\"cashflow\" "+stystr+"><font class=\"click_search\">";
		temstr += "<input type=\"text\" class=\"form-control form-warp cashflow \" style=\"width: 92%;\" name=\"cashFlow\" id=\"cashFlow\" value=\""
				+ comstr + "\">";
		temstr += "<span class=\"glyphicon glyphicon-search search-addon\">";
		temstr += "</span></font>";
		temstr += "</td>";

		temstr += "<td  align=\"center\" id=\"showflow2\" "+stystr2+">";
		temstr += "<font class=\"click_search2\" id=\"flowsearch2\"> ";
		temstr += "<input type=\"text\" disabled=\"disabled\" class=\"form-control form-warp  cashflow\" style=\"width: 91%;\" name=\"cashFlow\" id=\"cashFlow\" value=\"\">";
		temstr += "<span class=\"glyphicon glyphicon-search search-addon click_search2\"></span> ";
		temstr += " </font> ";
		temstr += "</td> ";
		if(priceTaxType==1){
			temstr +="<td id=\"priceTaxFlag1\" class=\"priceTaxFlag1\">";
			temstr += priceTaxFlagStr;
			temstr +="</td>";
		}
		
		//var stystr =" style=\"display:none\"";
		temstr += "<td align=\"center\" style=\"display:none\"><input type=\"hidden\" name=\"uuid\" value=\""
				+ rulelistbean[i].uuid + "\"></td>";
		temstr += "<td><p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a><a class='i i-lajitong  ui-icon ui-icon-trash' title='删除'></a></p></td>";
		temstr += "</tr>";

	}

	$("#tab").append(temstr);
}

function changeFont(selected) {
	// $('#accNo').val(selected.accNo);
	var that = $(this);
	that.prev('input').val(selected.accNo);
}


// 点击现金流量
function clickCashFlow() {
	// 现金流量的弹框
	$(".click_search").each(function() {
		$(this).bind("click", function() {
			getTreeCashType(this)
		});
		/*
		 * $(".cancel").bind("click", function(event){//关闭弹框
		 * $(parent.document).find("#showDialog .close").click(); });
		 */
	});
}
// 获取现金流量项
function getTreeCashType(dom) {
	openCashItemTreeDialog("002", function(data) {
		if (data == '') {
			return false;
		}

		$(dom).find("input").val(data.callName);

	});

}
// 修改保存
function updateCwPz() {
	var dataMap = {};
	var detils = [];
	$("#tab tr").each(function(i) {
		if($(this).is(":hidden")){
			var trObj = $(this);
			var uuid = trObj.find('input[name=uuid]').val();
			if (uuid != '') {
				deleteRuleMstDetailById(uuid);
			}
			
		}else{
			var trId = $(this).attr("id");
			var summStr = $(this).children(".zhaiyao").children(".edit_subject").val();
			var dcind = $(this).children(".dcind").children(".dcind").val();
			var accNo = $(this).children(".accNo").children(".accNo").val();
			var payItems = $(this).children(".payItems").children(".payItems").val();
			var cashflow = $(this).children(".cashflow").children(".click_search").children(".cashflow").val();
			var priceTaxFlag = $(this).find("select.priceTaxFlag").val();
			
			
			// alert("lzs=="+accNo);
			var detil = {};
			detil.summStr = summStr;
			detil.dcind = dcind;
			detil.accNo = accNo;
			detil.payItems = payItems;
			detil.cashflow = cashflow;
			detil.trId = trId;
			detil.priceTaxFlag = priceTaxFlag;
			detils.push(detil);
		}

	});
	var txType = $("#txType option:selected").val();
	var ywName = $("#ywName option:selected").val();
	var pzType = $("#pzType option:selected").val();
	var traceNo = $("#traceNo").val();
	var id_array = new Array();
	$('input[name="productno"]:checked').each(function() {
		id_array.push($(this).val());// 向数组中添加元素
	});
	var productno = id_array.join('@');// 将数组元素连接起来以构建一个字符串

	dataMap.detils = detils;
	dataMap.txType = txType;
	dataMap.ywName = ywName;
	dataMap.pzType = pzType;
	dataMap.traceNo = traceNo;
	dataMap.productno = productno;
	// dataMap.cashFlow=cashFlow;
	// alert("lzs=="+dataMap);addRuleDataAjax
	$.ajax({
		url : webPath+'/cwVchRuleMst/updateRuleDataAjax',
		type : 'post',
		data : {
			ajaxData : JSON.stringify(dataMap)
		},
		dataType : 'json',
		error : function() {
			//alert('error', 0);
			alert(top.getMessage("FAILED_OPERATION"," "), 0);
		},
		success : function(data) {
			LoadingAnimate.stop();
			if (data.flag == "success") {
				$(parent.document).find("#showDialog .close").click();
			} else {
				alert(data.msg, 0)
			}

		}
	});
}

// 业务名称变动
function getYewuDataList() {
	var txtypeVal = $("#txType option:selected").val();
	// alert(txtypeVal+"===="+tembean.length);
	for ( var i = 0; i < tembean.length; i++) {
		if (tembean[i].txType == txtypeVal) {
			$("#ywName").append(
					"<option value=\"" + tembean[i].plateNo + "\">"
							+ tembean[i].txName + "</option>");
			// $("#ywName").append("<option
			// value=\""+tembean[i].txCode+"\">"+tembean[i].txName+"</option>");
		}
	}
}

//获取id加1操作
function getTrIdMethod(trlength){
	var len = trlength;
	var  trIdCount =$("#"+len).length;
	if(trIdCount>0){
		len++;
		len = getTrIdMethod(len);
	}
	
	return len;
	
}

/**
 * 添加一行，删除一行
 */

function operatEvent() {
	//添加一行
	$('.ui-icon-plus').unbind().on('click', function() {
		var trObj = $(this).parents('tr');
		var trlengthn = $("#tab").find("tr").length;//获取的tab下的有多少个tr
		//为了保证trid的唯一性，所以这里需要创建一个唯一的id
		var trlength = getTrIdMethod(trlengthn);
		var idval = trObj.attr("id");
		var sflag =isNaN(idval);
		var trObjSon = trObj ; 
		if(sflag){
			var arr =idval.split("_");
			if(arr){
				trObj= $("#"+arr[0]);
			}
		}else{
			//idval++;
			//查看是否有子元素，根据tr的id判断
			var son1 = $("#"+idval+"_son1").length;
			if(son1>0){
				trObjSon = $("#"+idval+"_son1");
			}
		}
		var nextTr = $(trObj.prop('outerHTML'));
		nextTr.find('td:eq(0)').children('input').val("");
		nextTr.find('td:eq(2)').children('input').val("");
		nextTr.find('td:eq(4)').children('font').children('input').val("");
		//nextTr.find('td:eq(6)').children('input').val("");
		nextTr.find('td:eq(6)').children('input').val("");
		nextTr.find('td:eq(7)').children('input').val("");
		nextTr.attr("id",trlength);//给tr赋值id
		var casval = nextTr.find('td:eq(3)').children('select').val();
		if(casval=='cashBankAmt'){
			nextTr.find('td:eq(5)').show();
			nextTr.find('td:eq(4)').hide();
		}else{
			nextTr.find('td:eq(4)').show();
			nextTr.find('td:eq(5)').hide();
		}
		
		trObjSon.after(nextTr);
		operatEvent();
	});
	//删除一行
	$('.ui-icon-trash').unbind().on('click', function() {
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		var uuid = trObj.find('input[name=uuid]').val();
		var idval = trObj.attr("id");
		var sflag =isNaN(idval);
		if(sflag){
			//获取其父tr
			var arr =idval.split("_");
			if(arr){
				var futr= $("#"+arr[0]);
				futr.find('td:eq(6)').children('select').find("option[value='2']").attr("selected",true); 
			}
		}else{
			//获取sontr
			var son1 = $("#"+idval+"_son1").length;
			if(son1>0){
				alert(top.getMessage("ERROR_SCENCE_EVAL"),0);
				return;
			}
		}
		var len = $('#tab tr').length;
		if (len == 1) {
		} else {
			if (uuid != '') {
				deleteRuleMstDetail(uuid);
			}
			trObj.remove();
		}
	});

	// 给类似于放大镜的添加点击事件,现金流量
	$("#tab").delegate(".click_search", "click", function() {
		$(this).bind("click", function() {
			getTreeCashType(this);
		});
	});
}

//点击计税时添加一行
function addRowForPriceTax(obj){
	var trObj = $(obj).parents('tr');
	
	var idval = trObj.attr("id");
	var sflag =isNaN(idval);
	var s1='';
	if(sflag){
	}else{
		s1 = idval+"_"+"son1";
	}
	var nextTr = $(trObj.prop('outerHTML'));
	
	nextTr.attr("id",s1);//给tr赋值id
	
	nextTr.find('td:eq(0)').children('input').val("");
	nextTr.find('td:eq(2)').children('input').val("");
	nextTr.find('td:eq(4)').children('font').children('input').val("");
	nextTr.find('td:eq(4)').show();
	nextTr.find('td:eq(5)').hide();
	//税金不用考虑现金银行
	var cvalue = nextTr.find('td:eq(3)').children('select');
	cvalue.html('');
	var str = "<option value=\"priceTaxAmt\">税金</option>";
	cvalue.append(str);
	var priceTax = nextTr.find('td:eq(6)').empty();//
	//var priceTax = nextTr.find('td:eq(7)').empty();//
	//remove() - 删除被选元素（及其子元素 	empty() - 从被选元素中删除子元素
	trObj.after(nextTr);
	operatEvent();
}
//删除一行
function subRowForPriceTax(obj){
	var trObj = $(obj).parents('tr');	
	
	var uuid = trObj.find('input[name=uuid]').val();
	
	var idval = trObj.attr("id");
	var sflag =isNaN(idval);
	var s1='';
	if(sflag){
	}else{
		s1 = idval+"_"+"son1";
	}
	/*if (uuid != '') {
		deleteRuleMstDetail(uuid);
	}*/
	
	$("#"+s1).remove();
	operatEvent();
}




function deleteRuleMstDetail(uuid) {
	jQuery.ajax({
		url : webPath+'/cwVchRuleDetail/deletebyUuid',
		data : {
			uuid : uuid
		},
		type : "POST",
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			if (data.flag == "success") {
				alert(top.getMessage("SUCCEED_DELETE"), 1);
				//updateTableData();// 重新加载列表数据
			} else if (data.flag == "error") {
				alert(data.msg, 0);
			}
		},
		error : function(data) {
			alert(top.getMessage("FAILED_OPERATION", " "), 0);
		}
	});
}
//删除没有弹框
function deleteRuleMstDetailById(uuid){
	jQuery.ajax({
		url : webPath+'/cwVchRuleDetail/deletebyUuid',
		data : {
			uuid : uuid
		},
		type : "POST",
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			/*if (data.flag == "success") {
				alert(top.getMessage("SUCCEED_DELETE"), 1);
			} else if (data.flag == "error") {
				alert(data.msg, 0);
			}*/
		},
		error : function(data) {
			alert(top.getMessage("FAILED_OPERATION", " "), 0);
		}
	});
}

function mycloseDef() {
	$(parent.document).find("#showDialog .close").click();
}