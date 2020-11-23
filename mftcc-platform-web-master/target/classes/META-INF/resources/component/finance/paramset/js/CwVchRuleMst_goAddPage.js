;
/*使用闭包*/
var CwVchRuleMstAddJs = function(window, $) {
	
	var _init = function () {
		doneInitmethod();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);

function doneInitmethod(){
    var i;
	for(i = 0;i<listmap.length;i++){
		$("#txType").append("<option value=\""+listmap[i].key+"\">"+listmap[i].value+"</option>");
	}
	
	//给取值 赋值
	for ( var j = 0; j < paymap.length; j++) { 
			$("#payItems").append("<option value=\""+paymap[j].key+"\">"+paymap[j].value+"</option>");
	} 
	/*//适用交易
	for ( var j = 0; j < adaptlistmap.length; j++) { 
			$("#adaptProduct").append("<span style=\"white-space: nowrap;float:left;\"><input type=\"checkbox\" class=\"inputstyle1 adaptProduct\" value=\""+adaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+adaptlistmap[j].key+"\"><label  for=\"adapt"+adaptlistmap[j].key+"\">"+adaptlistmap[j].value+"</label></span>");
	} */
	//要根据业务类型获取适应交易数据
	getAdaptDataByType();
	//获取交易类型数据
	getYewuDataList();
	changeYewu();
	
	clickCashFlow(); //现金流量的弹框
	operatEvent();//增加一行，删除一行
	//自定义滚动条
	/*$(".page-voucher").mCustomScrollbar({
		advanced:{
			theme:"minimal-dark",
			updateOnContentResize:true
		}
	});*/
//	$(".scroll-content").mCustomScrollbar({
//		advanced : {
//			theme : "minimal-dark",
//			updateOnContentResize : true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
/*	$("#adapt0").click(function(){
		alert(1);
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
	});*/
	$("#adapt0").bind('click',function(){
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
	for (i = 0; i < wordbean.length; i++) {
		$("#pzType").append(
				"<option value=\"" + wordbean[i].pzProofNo + "\">"
						+ wordbean[i].pzPrefix + "</option>");
	}
	
	$("#txType").change(function() {
		$("#ywName").html("");
		
		getAdaptDataByType();//2017年10月14日  add
		getYewuDataList();
		//改变列表的值
		changeYewu();
		//现金流量
		clickCashFlow();
	});
	//当业务中的值改变时
	$("#ywName").change(function() {
		changeYewu();
		changeProduct();//修改产品
		/*$("#ywName").append("<option value=\"0000\" selected=\"selected\">请选择</option>");*/
	});
	//修改产品
	function changeProduct(){
		
		var jiaoyiName = $("#ywName option:selected").val();
		var txtypeVal = $("#txType option:selected").val();//获取的业务类型
		var jiaoyiJson ={"txCode":jiaoyiName,"txType":txtypeVal} ;
		$.ajax({
			url : webPath+'/cwVchRuleMst/dochangeProductAjax',
			type : 'post',
			data : jiaoyiJson,
			dataType : 'json',
			error : function() {
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			},
			success : function(data) {
				if (data.flag == "success") {
					///alert("获取数据成功", 1);
					var alistmap = data.alistmap;
					var adaptlistmap =alistmap;
					//在模版中添加值
					$("#adaptProduct").html('');
					for ( var j = 0; j < adaptlistmap.length; j++) { 
						$("#adaptProduct").append("<span style=\"white-space: nowrap;float:left;\"><input type=\"checkbox\" class=\"inputstyle1 adaptProduct\" value=\""+adaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+adaptlistmap[j].key+"\"><label  for=\"adapt"+adaptlistmap[j].key+"\">"+adaptlistmap[j].value+"</label></span>");
					} 
					tritAllMethod();//给全部添加事件
				} else {
					alert(data.msg, 0)
				}

			}
		});
		
		
		
	}

	//给类似于放大镜的添加点击事件
	$("#tab").delegate(".comitem_select", "click", function() {
		var that = $(this);
		openComItemDialog('2', function(data) {
			if (data) {
				//that.prev('input').val(data.id);
				that.prev('input').val(data.id+"/"+data.name);
			}
		});
	});
	//给价税分离添加change事件
	$("#tab").delegate(".priceTaxFlag", "change", function() {
		var that = $(this);
		//var priceTaxFlag = $(".priceTaxFlag option:selected").val();
		var priceTaxFlag = $(this).val();
		if(priceTaxFlag==1){
			addRowForPriceTax(that);
		}else if(priceTaxFlag==2){
			subRowForPriceTax(that);
		}
	});
	//给类似于放大镜的添加点击事件,现金流量
	$("#tab").delegate(".click_search", "click", function() {
		$(this).bind("click", function() {
			getTreeCashType(this);
		});
	});
	//给取值加change事件
	$("#tab").delegate("#payItems", "change", function() {
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
//tritAllMethod  添加事件
function tritAllMethod(){
	$("#adaptProduct").delegate('#adapt0',"click", function() {
		$(this).bind("click", function() {
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
		
	});
	$("#adaptProduct").delegate('.adaptProduct',"click", function() {
		$(this).bind("click", function() {
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
	});
	
	//点击全部两下，让其有触发事件
	$(".adaptProduct").click();
	//$(".adaptProduct").click();

	$("#adapt0").click();
	//$("#adapt0").click();
	
}

//获取列表内容信息
function changeYewu() {
	var ywNameVal = $("#ywName option:selected").val();
	var temstr = '';
	if(ywNameVal=='0000'){
		return;
	}
	var txCodeJson ={"txCode":ywNameVal} ;
	//根据交易类型选择所需要的模版
	$.ajax({
		url : webPath+'/cwVchRuleMst/getRulePlateAjax',
		type : 'post',
		data : txCodeJson,
		dataType : 'json',
		error : function() {
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		},
		success : function(data) {
			if (data.flag == "success") {
				///alert("获取数据成功", 1);
				//在模版中添加值
				changePlage(data);
				
			} else {
				alert(data.msg, 0)
			}

		}
	});
	
	
}
//修改模版列表
function changePlage(data){
	$("#tab").html("");
	var jieCount = data.jieCount;
	var daiCount = data.daiCount;
    var i;
	//循环借方科目
	for(i=0;i<jieCount;i++){
		setRulesValue(data,1,i);
	}
	//循环贷方科目
	for(i=0;i<daiCount;i++){
		setRulesValue(data,2,i);
	}
	
}
//给列表内容语句添加值
function setRulesValue(data,num,i){
	//1借，2贷
	var trlength = $("#tab").find("tr").length;//获取的tab下的有多少个tr
	
	var platestr = "<tr id="+trlength+"> ";
	platestr +="<td align=\"center\" class=\"zhaiyao\"><input type=\"text\" class=\"inputstyle2 edit_subject\" name=\"zhaiyao\" value=\"\"></td>";
	platestr +="<td align=\"center\" class=\"dcind\"><select class=\"inputstyle3 dcind\">";
	//var valArray[] = new Array();
	if(num==1){
		//valArray = data.jieArray;
		platestr +="<option value=\"1\" selected = \"selected\">借</option> <option value=\"2\">贷</option>";
	}else{
		//valArray = data.daiArray;
		platestr +="<option value=\"1\" >借</option><option value=\"2\" selected = \"selected\">贷</option> ";
	}
	platestr +="</select></td>";
	platestr +="<td align=\"center\" class=\"accNo\"> ";
	var desc= num==1?data.jieDescArray[i]:data.daiDescArray[i];
	platestr +="<input class=\"form-control form-warp accNo\" placeholder=\""+desc+"\"  style=\"width: 91%;\" type=\"text\" id=\"accNo\" name=\"accNo\" onclick=\"autoComPleter(this, '0')\"  autocomplete=\"off\">";
	platestr +="<span class=\"glyphicon glyphicon-search search-addon comitem_select\"></span>";
	platestr +="</td>";
	//取值，做修改
	platestr +="<td align=\"center\" class=\"payItems\">";
	platestr +="<select class=\"inputstyle2 payItems\" id=\"payItems\">";
	var key= num==1?data.jieArray[i]:data.daiArray[i];
	for ( var j = 0; j < paymap.length; j++) { 
		//var key = data.jieArray;
		if(key==paymap[j].key){
			platestr+="<option value=\""+paymap[j].key+"\" selected = \"selected\">"+paymap[j].value+"</option>";
		}else{
			platestr+="<option value=\""+paymap[j].key+"\">"+paymap[j].value+"</option>";
		}
		
	} 
	platestr +="</select></td>";
	//
	var style1="";
	var style2="style=\"display:none\"";
	if(key=='cashBankAmt'){
		style1="style=\"display:none\"";
		style2="";
	}
	platestr +="<td align=\"center\" class=\"cashflow\" id=\"showflow1\" "+style1+">";
	platestr +=" <font class=\"click_search\" id=\"flowsearch\">";
	platestr +="<input type=\"text\" class=\"form-control form-warp  cashflow\" style=\"width: 91%;\" name=\"cashFlow\" id=\"cashFlow\" value=\"\">";
	platestr +="<span class=\"glyphicon glyphicon-search search-addon click_search2\"></span> ";
	platestr +=" </font></td>";
	platestr +="<td align=\"center\" id=\"showflow2\" "+style2+">"
	platestr +="<font class=\"click_search2\" id=\"flowsearch2\">  ";
	platestr +="<input type=\"text\" disabled=\"disabled\" class=\"form-control form-warp  cashflow\" style=\"width: 91%;\" name=\"cashFlow\" id=\"cashFlow\" value=\"\">";
	platestr +=" </font> </td> ";
	if (voucherBody.length > 0){
        //取值，做修改
        platestr +="<td align=\"center\" class=\"voucherBody\">";
        platestr +="<select class=\"inputstyle2\" id=\"voucherBody\">";
        for (var k in voucherBody){
            platestr+="<option value=\""+voucherBody[k].optCode+"\">"+voucherBody[k].optName+"</option>";
        }
        platestr +="</select></td>";
    }
	if(priceTaxType==1){
		platestr +="<td id=\"priceTaxFlag1\" class=\"priceTaxFlag1\">";
		platestr +="<select class=\"inputstyle3 priceTaxFlag\">";
		platestr +="<option value=\"2\">不计税</option>";
		platestr +="<option value=\"1\">计税</option>";
		platestr +="</select>";
		platestr +="</td>";
	}
	platestr +="<td><p class=\"operating\" data-id=\"3\"><a class=\"i i-jia1 ui-icon ui-icon-plus\" title=\"新增\"></a>";
	platestr +="<a class=\"i i-lajitong ui-icon ui-icon-trash\" title=\"删除\"></a></p></td>";
	platestr +="</tr>";
	
	
	$("#tab").append(platestr); 
	operatEvent();
}

//根据业务类型获取适应交易数据 2017年10月14日 lzs
function getAdaptDataByType(){
	var txtypeVal = $("#txType option:selected").val();
	$("#adaptProduct").html("");
    var j;
	if(txtypeVal=='01'){
		//适用交易
		for (j = 0; j < bdaptlistmap.length; j++) {
				$("#adaptProduct").append("<span style=\"white-space: nowrap;float:left;\"><input type=\"checkbox\" class=\"inputstyle1 adaptProduct\" value=\""+bdaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+bdaptlistmap[j].key+"\"><label  for=\"adapt"+bdaptlistmap[j].key+"\">"+bdaptlistmap[j].value+"</label></span>");
		} 
	}else{
		//适用交易
		for (j = 0; j < adaptlistmap.length; j++) {
				$("#adaptProduct").append("<span style=\"white-space: nowrap;float:left;\"><input type=\"checkbox\" class=\"inputstyle1 adaptProduct\" value=\""+adaptlistmap[j].key+"\" name=\"productno\" id=\"adapt"+adaptlistmap[j].key+"\"><label  for=\"adapt"+adaptlistmap[j].key+"\">"+adaptlistmap[j].value+"</label></span>");
		} 
	}
	tritAllMethod();//给全部添加事件
	//getYewuDataList();
	$("#tab").html("");
}

//业务名称变动
function getYewuDataList() {
	var txtypeVal = $("#txType option:selected").val();
	//alert(txtypeVal+"===="+tembean.length);tembean.length
	for ( var i = 0; i < tembean.length; i++) {
		//
		if (tembean[i].txType == txtypeVal) {
			$("#ywName").append(
					"<option value=\"" + tembean[i].plateNo + "\">"
							+ tembean[i].txName + "</option>");
		}
	}
	$("#ywName").append("<option value=\"0000\" selected=\"selected\">请选择</option>");
}

function changeFont(selected) {
	var that = $(this);
	var sval = selected.accNo;
	that.prev('input').val(sval);
}
//保存数据
function addCwPz() {
	var dataMap = {};
	var detils = [];
	$("#tab tr").each(function(i) {
		
			var trId = $(this).attr("id");
	
			var summStr = $(this).children(".zhaiyao").children(".edit_subject").val();
			var dcind = $(this).children(".dcind").children(".dcind").val();
			var accNo = $(this).children(".accNo").children(".accNo").val();

			var payItems = $(this).children(".payItems").children(".payItems").val();
			
			//var txdesc = $(this).children(".txdesc").val();
			//var xianjinflow = $(this).children(".xianjinflow").children(".xianjinflow").val();
			var cashflow = $(this).children(".cashflow").children(".click_search").children(".cashflow").val();
			var voucherBody = $(this).find("#voucherBody").val();
			var priceTaxFlag = $(this).children(".priceTaxFlag1").children(".priceTaxFlag").val();
			//alert(priceTaxFlag);
			var detil = {};
			detil.summStr = summStr;
			detil.dcind = dcind;
			detil.accNo = accNo;
			//detil.txdesc=txdesc;
			detil.payItems = payItems;
			detil.cashflow = cashflow;
			detil.trId = trId;
			detil.priceTaxFlag = priceTaxFlag;
			detil.voucherBody = voucherBody;
			detils.push(detil);
	});
	
	var txType = $("#txType option:selected").val();
	var ywName = $("#ywName option:selected").val();
	var pzType = $("#pzType option:selected").val();
	
	if(ywName=='0000'){
		alert(top.getMessage("FIRST_OPERATION","选择交易类型"), 1)
		//alert(top.getMessage("NOT_FORM_EMPTY","交易类型"), 1)
		return;
	}
	
	var id_array = new Array();
	$('input[name="productno"]:checked').each(function() {
		id_array.push($(this).val());//向数组中添加元素  
	});
	var productno = id_array.join('@');//将数组元素连接起来以构建一个字符串  

	dataMap.detils = detils;
	dataMap.txType = txType;
	dataMap.ywName = ywName;
	dataMap.pzType = pzType;
	dataMap.productno = productno;

	//dataMap.cashFlow=cashFlow;
	//alert("lzs=="+dataMap);addRuleDataAjax
	$.ajax({
		url : webPath+'/cwVchRuleMst/addRuleDataAjax',
		type : 'post',
		data : {
			ajaxData : JSON.stringify(dataMap)
		},
		dataType : 'json',
		error : function() {
			//alert('error', 0);
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		},
		success : function(data) {
			//LoadingAnimate.stop();
			if (data.flag == "success") {
//				$('.myclose').click();
				//$(parent.document).find("#showDialog .close").click();
				alert(data.msg, 1)
			} else {
				alert(data.msg, 0)
			}

		}
	});

}
//点击现金流量
function clickCashFlow() {
	//现金流量的弹框
	/*$(".click_search").each(function() {
		$(this).bind("click", function() {
			getTreeCashType(this);
		});
	});*/
	$(".cancel").bind("click", function(event) {//取消只移除弹框
		$(parent.document).find("#showDialog .close").click();
	});
}

//获取现金流量项
function getTreeCashType(dom) {
	openCashItemTreeDialog("002", function(data) {
		if (data == '') {
			return false;
		}
		$(dom).find("input").val(data.callName);

	});

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
添加一行，删除一行
 */
function operatEvent() {
	//添加一行
	$('.ui-icon-plus').unbind().on('click', function() {
		var trObj = $(this).parents('tr');
		//trObj.attr('id', 1);
		var trlengthn = $("#tab").find("tr").length;//获取的tab下的有多少个tr
		//为了保证trid的唯一性，所以这里需要创建一个唯一的id
		var trlength = getTrIdMethod(trlengthn);
		var idval = trObj.attr("id");
		var sflag =isNaN(idval);
		var trObjSon = trObj ; 
		if(sflag){
//			alert(1);
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
			trObj.remove();
		}

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
	
/*	var casval = nextTr.find('td:eq(3)').children('select').val();
	if(casval=='cashBankAmt'){
		nextTr.find('td:eq(5)').show();
		nextTr.find('td:eq(4)').hide();
	}else{
		nextTr.find('td:eq(4)').show();
		nextTr.find('td:eq(5)').hide();
	}*/
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
	var idval = trObj.attr("id");
	var sflag =isNaN(idval);
	var s1='';
	if(sflag){
	}else{
		s1 = idval+"_"+"son1";
	}
	
	$("#"+s1).remove();
	///var nextTr = $(trObj.prop('outerHTML'));
	//nextTr.attr("id",s1);//给tr赋值id
	
	//remove() - 删除被选元素（及其子元素 	empty() - 从被选元素中删除子元素
	//trObj.remove();
	
	operatEvent();
}

