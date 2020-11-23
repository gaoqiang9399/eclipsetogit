function Page(pageNum) {
	try {
		document.page.currentPage.value = pageNum;
		document.page.submit();
		return true;
	} catch (e) {
		alert(e);
	}
}

function openurl(theURL, winName, features) {
	window.open(theURL, winName, features);
}

function switch_updown() {
	if (mspan.title == "关闭上栏") {
		mspan.title = "打开上栏";
		document.all("mtop").style.display = "none";
	} else {
		mspan.title = "关闭上栏";
		document.all("mtop").style.display = "";
	}
}

function switch_leftRight() {
	if (mspan.title == "关闭客户列表") {
		mspan.title = "打开客户列表";
		document.all("mleft").style.display = "none";
	} else {
		mspan.title = "关闭客户列表";
		document.all("mleft").style.display = "";
	}
}

function replaceAll(str, str1, str2) {
	while (str.indexOf(str1) >= 0)
		str = str.replace(str1, str2);

	return str;
}

function getParam(str, leftFlag, rightFlag) {
	var param = "";
	while (str.indexOf(rightFlag) < str.indexOf(leftFlag)) {
		if (str.indexOf(rightFlag) < 0)
			break;
		str = str.substring(str.indexOf(rightFlag) + 1);
	}

	if (str.indexOf(leftFlag) >= 0 && str.indexOf(rightFlag) >= 0) {
		var pos1 = str.indexOf(leftFlag);
		var pos2 = str.indexOf(rightFlag);
		param = str.substring(pos1 + 1, pos2);
	}
	return param;
}

function samepara() {
	kind = document.all["param"].value;
	if (kind == "1") {
		para = document.all["param1"].value;
		document.all["param2"].value = para;
		document.all["param3"].value = para;
		document.all["param4"].value = para;
		document.all["param5"].value = para;
		document.all["param6"].value = para;
		document.all["param7"].value = para;
		document.all["param8"].value = para;
		document.all["param9"].value = para;
		document.all["param10"].value = para;
		document.all["param11"].value = para;
		document.all["param12"].value = para;
	}

}

function setElementValue(src, value) {
	document.all[src].value = value;
}

function setQuery(kind) {
	document.all["query"].value = kind;
}

function displaylayer() {
	laywait.style.visibility = "visible";

}

function popMessage(msg) {
	if (msg != null && msg != "") {
		alert(msg);
	}

}

function stat() {
	var a = pageYOffset + window.innerHeight - document.bar.document.height
			- 15;
	document.bar.top = pageYOffset;
	setTimeout('stat()', 2);
}
function iefd(offsetxpos, offsetypos) {
	bar.style.top = document.body.scrollTop + document.body.offsetHeight
			- offsetypos;
	bar.style.left = document.body.scrollLeft + document.body.offsetWidth
			- offsetxpos;
	setTimeout("iefd(" + offsetxpos + "," + offsetypos + ")", 2);
}//<!-- 浮动条 --->
function fix(offsetxpos,offsetypos){
  nome=navigator.appName;
  if(nome=='Netscape'){
    stat();
  }else{
    iefd(offsetxpos,offsetypos);
  }
}

//<!-- 清空表单内容 --->
function clearAll(theForm){
	len = theForm.elements.length;
	for(i=0;i<len;i++){
		//alert(theForm.elements[i].type);
		if(theForm.elements[i].type == 'checkbox'){
				theForm.elements[i].checked = false;
		}
		if(theForm.elements[i].type == 'radio'){
			theForm.elements[i].checked = false;
		}
		if(theForm.elements[i].type == 'select-one'){
			theForm.elements[i].value = theForm.elements[i].options[0].value ;
		}
		if(theForm.elements[i].type == 'text')
		  theForm.elements[i].value = "";
		  
		if(theForm.elements[i].type == 'textarea')
		  theForm.elements[i].value = "";
	}
} 

//<!-- 清空复选框 --->
function clearAllCheckbox(theForm){
	len = theForm.elements.length;
	for(i=0;i<len;i++){
		//alert(theForm.elements[i].type);
		if(theForm.elements[i].type == 'checkbox'){
				theForm.elements[i].checked = false;
		}
	}
} 
//<!-- 选中所有复选框 --->
function selAllCheckbox(theForm){
	len = theForm.elements.length;
	for(i=0;i<len;i++){
		//alert(theForm.elements[i].type);
		if(theForm.elements[i].type == 'checkbox'){
				theForm.elements[i].checked = true;
		}
	}
} 

//<!-- 计算选中所有复选框个数 --->
function countCheckbox(theForm){
	sum = 0;
	len = theForm.elements.length;
	for(i=0;i<len;i++){
		//alert(theForm.elements[i].type);
		if(theForm.elements[i].type == 'checkbox' && theForm.elements[i].checked == true){
				sum ++ ;
		}
	}
	return sum;
} 

//<!-- 得到选择域的文本 -->
	function getSelectText(src){
		return src.options[src.selectedIndex].text;
	}

//<!-- 确认执行操作 -->
function lkconfirm(lk){
	alert("请确定是否要执行此操作，按“取消”表示不进行此操作！",2,function(){
		location.href = lk;
	});
}

//<!-- 删除确认执行操作 -->
function delconfirm(obj,url){
	alert("请确定是否要执行此操作，按“取消”表示不进行此操作！",2,function(){
		$.ajax({
			type:"post",
			url:url,
			async:false,
			success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_DELETE"),1);
					$(obj).parents("tr").remove();
				}else if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	});
}
//<!-- 详情页面中的查看按钮操作 -->
function fromInfoView(fieldName,fieldValue,lk){
	if(fieldValue=='' ||fieldValue==null ){
		alert(fieldName+"为空，请赋值后才能查看!",0);
		return;
	}
	window.open(lk,"window","status:no;help:no;border:thin;statusbar:no,left=100,top=30,resizable=yes,width=1200,height=600");
 
}
//<!-- TABLE中按钮执行JS操作 -->
function buttonforward(lk){
  	location.href = lk;
  	//location.href.click();
}
//<!--买断买入提交申请-->
function func_submitconfirm(lk) {
	var strs=lk.split("&");
	var str=strs[1];
	var temps=str.split("=");
	var status=temps[1];

	if (confirm('提交后就不能修改,确认提交！')) {
		if (status == "0" ||status == "2") {
			location.href = lk;
			//location.href.click();
		} else {
			alert('该申请已提交！');
			return;
		}
	}
}

//<!-- 链接url -->
function linkUrl(lk,win){
  	location.href = lk;
  	location.href.target = '_blank';
  	//location.href.click();
}


//the page could invoke history.goback(-1) to goback last page while isGoBack is not null
//but please specify the var isGoBack's value in your page, eg: isGoBack = true;
var isGoBack; 

function historyForward() {
	if(  isGoBack == "undefined" || isGoBack == null ) {
		window.history.forward(50);
	}
}

historyForward();

//<!-- 翻页JS -->
 function doEadisPage(url,v){
	
	  url = trim(url);
	  var formself;
	  var formfirst;
	  var forms = document.getElementsByTagName("form");
	  for(var i=0;i<forms.length;i++){
			var form = forms[i];
			if(form.action==url&& (form.name=='null'||form.name=='formBean')||form.name=='tableDataBean'){
					formself=form;
			}
			if(form.name!='null' && form.name!='formBean' && form.name!='tableDataBean'){
				if(form.action.indexOf("/")>=0){
	                var tempUrl =  form.action.substring(form.action.lastIndexOf("/")+1,form.action.length);
	                tempUrl = trim(tempUrl);
	                if(tempUrl==url){
	                	formfirst = form;
	                }
	            }else{
	            	 tempUrl = trim(form.action);
	                if(tempUrl==url){
	                	formfirst = form;
	                }
	            }
			}
	  }
	  var eles,esubmit;
      if(formfirst){
    	    eles=formfirst.elements;
		 	//var esubmit;
            for(i=0;i<eles.length;i++){
               e=eles[i];
               if(e.name=='eadis_page'){
            	   e.value=v;
               }
               if(e.name=='formPage'){
            	   e.value=v;
               }
                if(e.name=='submitgo'){
            	   esubmit=e;
               }
            }
            esubmit.click();
      }else {
			  eles = formself.elements;
			  //var esubmit;
			  for (i = 0; i < eles.length; i++) {
				  e = eles[i];
				  if (e.name == 'formPage') {
					  e.value = v;
				  }
				  if (e.name == 'eadis_page') {
					  e.value = v;
				  }
				  if (e.name == 'submitgo') {
					  esubmit = e;
				  }
			  }
			  esubmit.click();
      }
 }
 	function trim(oValue){
		oValue = oValue.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		return oValue;
	}
 
	function makeOptionsJQ(obj, allowOptionStr,defVal) {
		var p =obj.parent();
		if(typeof(p.data("options"))=="undefined"){
			p.data("options",obj);
		}
		p.empty();
		$.each(p.data("options"), function(i,list) {
			if(allowOptionStr != '' && $(list).val() != ''&&allowOptionStr!==undefined&&allowOptionStr!=null){
				$.each(allowOptionStr.split(","),function(index,val){
					if($(list).val()==val){
						p.append($(list));
						return false;
					}
				});
			}
		});
		if(typeof(defVal)!="undefined"){
			p.val(defVal);
		}
	}
 
	function makeOptions(objName, allowOptionStr) {
		var obj = document.getElementsByName(objName)[0];
		if(obj){
			var options=obj.options;
			if(options){
			for ( var i = options.length - 1; i >= 0; i--) {
				//根据返回值移除，如果返回空则不做任何处理
				if (allowOptionStr != '' && options[i].value != ''
						&& !optionsIndex(allowOptionStr,options[i].value)) {
					options.remove(i);
				}
			}
			}
		}
	}
	
	function optionsIndex(allowOptionStr,str){
		var flag = false;
		var options =  allowOptionStr.split(",");
		for(var i=0;i<options.length;i++){
			if(options[i]==str){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	function getCursorPsn(inObject){
		var position = 0; 
		if(inObject==null){
			return position;
		}
		var domObj = inObject;
		if (document.selection) {	//for IE
			domObj.focus();
			var sel = document.selection.createRange();
			sel.moveStart('character', -domObj.value.length);
	
			position = sel.text.length;
		} else if (domObj.selectionStart || domObj.selectionStart == '0') {
			position = domObj.selectionStart;
		}
		return position;
	}
	
	function getOstr(inObject,position){
		var sumOstr = 0;
		if(inObject==null){
			return sumOstr;
		}
		var inStr = inObject.value;
		if(inStr.length > 0){
			var lStr = inStr.substring(0,position);
			for(var i=0; i<lStr.length; i++){
				var v = lStr.charAt(i);
				if(isNaN(v)){
					sumOstr++;
				}
			}
		}
		return sumOstr;
	}
	//格式化金额
	function fmoney(s, n) {
 		if(s!="" && s!=null){
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
            t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }
            return t.split("").reverse().join("") + "." + r;
        }else{
 			if(s==0){
 				return "0.00";
			}else {
                return "";
            }
		}
	};
	
	
	//<!-- 千分位 --> //wusheng
	function toMoney(inObject) {
		if(inObject==null){
			return;
		}
		//获取光标位置
		var position = getCursorPsn(inObject);
		//获取光标前非数字个数
		var sumOstr = getOstr(inObject,position);
		
		var inStr = inObject.value;

		var i, charValue, outStr, id = 0,fs=0;
		//var fsInStr ="";

		for (i = 0; i < inStr.length; i++) {
			charValue = inStr.charAt(i);
			//alert("charValue:"+charValue+":"+i+":"+inStr.length);
			if (isNaN(parseInt(charValue, 10)) && (charValue != ".")
					&& (charValue != ",") && (charValue != "-")) {
				if(fs==1)
					inObject.value = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				else
					inObject.value = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				return;
			}
			if (i>0 && charValue == "-") {
				if(fs==1)
					inObject.value = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				else
					inObject.value = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
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
		//最大长度和保留小数位数处理
		var dbmaxlength = inObject.getAttribute('dbmaxlength');
		var dotSize = inObject.getAttribute('size');
		var maxlength = inObject.getAttribute('maxlength');
		if(!dbmaxlength){
			dbmaxlength = maxlength;
			inObject.setAttribute('dbmaxlength',dbmaxlength);
			if(dotSize&&dotSize.indexOf(".")>-1){
				var dotSizeArr = dotSize.split(".");
				var tempSize = Number(dbmaxlength)-dotSizeArr[1].length;
				inObject.setAttribute('maxlength',Number(dbmaxlength)+parseInt(tempSize/3)+1);
				inObject.setAttribute('dotlength',dotSizeArr[1].length);
			}else{
				inObject.setAttribute('maxlength',Number(dbmaxlength)+1);
			}
		}
		
		valueArr = inStr.split(".");

		if (valueArr.length > 2) {
			alert(inStr + " 非法金额!");
			inObject.focus();
			inObject.select();
			return;
		}

		var dotStr, dotValue;
		var datatype = inObject.getAttribute('datatype');
		var dotlength = inObject.getAttribute('dotlength');
		if (valueArr.length == 2) {
			dotValue = valueArr[1];
			if (dotValue.length == 0) {
				dotStr = "";
			} else {
				if (dotValue.length == 1){
					dotStr = dotValue+ "";
				}else{
					if(dotlength>0&&typeof(dotlength)!="undefined"){
						dotStr = dotValue.substring(0, dotlength);
					}else if(datatype&&typeof(datatype)!="undefined"){//根据数据类型判断小数点保留后几位
						if(datatype=="3"||datatype=="12"){
							dotStr = dotValue.substring(0, 2);
						}else if(datatype=="8"){
							dotStr = dotValue.substring(0, 4);
						}else if(datatype=="9"){
							dotStr = dotValue.substring(0, 6);
						}
						dotlength = 0;
					}else{
						dotStr = dotValue.substring(0, dotValue.length);
						dotlength = 0
					}
				}
			}
		}	
		
		var intArr;

		intArr = valueArr[0].split(",");

		var intValue = "";

		for (i = 0; i < intArr.length; i++) {
			intValue += intArr[i];
		}
		var intStr = "";

		if (intValue.length > 1 && intValue.charAt(0) == "0"
				&& intValue.charAt(1) != ".") {
			intValue = intValue.substr(1, intValue.length - 1);
		}
		if(intValue.length>(Number(dbmaxlength)-Number(dotlength))){
			if(dotlength>0&&typeof(dotlength)!="undefined"){
				intValue = intValue.substring(0, Number(dbmaxlength) - Number(dotlength));
			}else if(datatype&&typeof(datatype)!="undefined"){//根据数据类型判断小数点前保留几位
				if(datatype=="3"){
					intValue = intValue.substring(0, Number(dbmaxlength) - 2);
				}else if(datatype=="8"){
					intValue = intValue.substring(0, Number(dbmaxlength) - 4);
				}else if(datatype=="9"){
					intValue = intValue.substring(0, Number(dbmaxlength) - 6);
				}
			}else{
				intValue = dotValue.substring(0, maxlength);
			}
		}
		while (intValue.length > 3) {
			intStr = ","
					+ intValue.substring(intValue.length - 3, intValue.length)
					+ intStr;
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

		inObject.value = outStr;
		var $bigAmt=$(inObject).siblings(".bigAmt");
		if($bigAmt.length>0){
			var bigAmtShow=bigAmt(outStr);
			if(bigAmtShow){
				$bigAmt.css({display: 'table-cell'});
				$bigAmt.html('（'+bigAmtShow+'）');
			}else{
				$bigAmt.html('');
				$bigAmt.css({display: 'none'});
			}
		}
		//var esumOstr = getOstr(inObject,position);
		
		//设置光标位置
		//position = position + (esumOstr - sumOstr);
		//setgetCursorPsn(inObject,position);
		
		return;
	}
	
	function setgetCursorPsn(domObj,position){
		if (document.selection) {	//for IE
			domObj.focus();
			var sel = document.selection.createRange();
			var r = domObj.createTextRange();   
			r.collapse(true);  
			r.moveStart('character', position);
			r.select();   
			
		} else  {
			domObj.selectionStart = position;
		}
	}
	
	
	function toDouble(inObject) {
		var inStr = inObject.value;
		var i, charValue, outStr, id = 0,fs=0;
		for (i = 0; i < inStr.length; i++) {
			charValue = inStr.charAt(i);
			if (isNaN(parseInt(charValue, 10)) && (charValue != ".")
					&& (charValue != ",") && (charValue != "-")) {
				if(fs==1)
					inObject.value = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				else
					inObject.value = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				return;
			}
			if (i>0 && charValue == "-") {
				if(fs==1)
					inObject.value = "-"+inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
				else
					inObject.value = inStr.substring(0, i)+inStr.substring(i+1, inStr.length);
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
			alert(inStr + " 非法数值!");
			inObject.focus();
			inObject.select();
			return;
		}
		var dbmaxlength = inObject.getAttribute('dbmaxlength');//最大长度加1处理，小数点占一位
		var maxlength = inObject.getAttribute('maxlength');
		var dotSize = inObject.getAttribute('size');
		if(!dbmaxlength){
			dbmaxlength = maxlength;
			inObject.setAttribute('dbmaxlength',dbmaxlength);
			if(dotSize&&dotSize.indexOf(".")>-1){
				var dotSizeArr = dotSize.split(".");
				inObject.setAttribute('dotlength',dotSizeArr[1].length);
			}
			inObject.setAttribute('maxlength',Number(dbmaxlength)+1);
		}
		var dotStr, dotValue;
		var datatype = inObject.getAttribute('datatype');
		var dotlength = inObject.getAttribute('dotlength');
		if (valueArr.length == 2) {
			dotValue = valueArr[1];
			if (dotValue.length == 0) {
				dotStr = "";
			} else {
				if (dotValue.length == 1){
					dotStr = dotValue+ "";
				}
				else{
					if(dotlength>0&&typeof(dotlength)!="undefined"){
						dotStr = dotValue.substring(0, dotlength);
					}else if(datatype&&typeof(datatype)!="undefined"){//根据数据类型判断小数点保留后几位
						if(datatype=="3"){
							dotStr = dotValue.substring(0, 2);
						}else if(datatype=="8"){
							dotStr = dotValue.substring(0, 4);
						}else if(datatype=="9"){
							dotStr = dotValue.substring(0, 6);
						}
					}else{
						dotStr = dotValue.substring(0, dotValue.length);
					}
				}
			}
		}	
		var intArr;
		intArr = valueArr[0].split(",");
		var intValue = "";
		for (i = 0; i < intArr.length; i++) {
			intValue += intArr[i];
		}
		var intStr = "";
		if (intValue.length > 1 && intValue.charAt(0) == "0"
			&& intValue.charAt(1) != ".") {
			intValue = intValue.substr(1, intValue.length - 1);
		}
		
		if(dotlength>0&&typeof(dotlength)!="undefined"){
			intValue = intValue.substring(0, Number(dbmaxlength) - Number(dotlength));
		}else if(datatype&&typeof(datatype)!="undefined"){//根据数据类型判断小数点前保留几位
			if(datatype=="3"){
				intValue = intValue.substring(0, Number(dbmaxlength) - 2);
			}else if(datatype=="8"){
				intValue = intValue.substring(0, Number(dbmaxlength) - 4);
			}else if(datatype=="9"){
				intValue = intValue.substring(0, Number(dbmaxlength) - 6);
			}
		}else{
			intValue = dotValue.substring(0, maxlength);
		}
		
		
		
		
//		while (intValue.length > 3) {
//			intStr = intValue.substring(intValue.length - 3, intValue.length) + intStr;
//			intValue = intValue.substring(0, intValue.length - 3);
//		}
		intStr = intValue + intStr;
		if (dotStr == null)
			outStr = intStr;
		else
			outStr = intStr + "." + dotStr;
		if(fs==1){
			outStr = "-" + outStr;
		}
		inObject.value = outStr;
		return;
	}

	function lastMoney(inObject) {
		var inStr = inObject.value;
		
		var i, charValue, id = 0,fs=0;
		for (i = 0; i < inStr.length; i++) {
			charValue = inStr.charAt(i);
			// alert("charValue:"+charValue+":"+i+":"+inStr.length);
			if (isNaN(parseInt(charValue, 10)) && (charValue != ".")
					&& (charValue != ",") && (charValue != "-")) {
				if(fs==1)
					inStr = "-"+inStr.substring(0, i);
				else
					inStr = inStr.substring(0, i);
			}
			if (i>0 && charValue == "-") {
				if(fs==1)
					inStr = "-"+inStr.substring(0, i);
				else
					inStr = inStr.substring(0, i);
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
		
		var valueArr = inStr.split(".");

		if (valueArr.length > 2) {
			alert(inStr + " 非法金额!");
			inObject.focus();
			inObject.select();
			return;
		}

		var dotStr="", dotValue;

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

		if (intValue.length > 1 && intValue.charAt(0) == "0"
				&& intValue.charAt(1) != ".") {
			intValue = intValue.substr(1, intValue.length - 1);
		}

		while (intValue.length > 3) {
			intStr = ","
					+ intValue.substring(intValue.length - 3, intValue.length)
					+ intStr;
			intValue = intValue.substring(0, intValue.length - 3);
		}
		intStr = intValue + intStr;

		if (dotStr == "")
			inStr = intStr;
		else
			inStr = intStr + "." + dotStr;
		
		if(fs==1){
			inStr = "-" + inStr;
		}
		var outStr;

		valueArr = inStr.split(".");

		dotStr="";

		if (valueArr.length == 2) {
			dotValue = valueArr[1];
			if (dotValue.length == 0) {
				dotStr = "00";
			} else {
				if (dotValue.length == 1)
					dotStr = dotValue + "0";
				else{
					var is0 = 0;
					for (i = dotValue.length-1; i >= 2; i--) {
						charValue = dotValue.charAt(i);
						if(charValue!="0" || is0!=0){
							if(charValue!=",")
								dotStr = charValue + dotStr;
							is0 = 1;
						}
					}
					dotStr = dotValue.substring(0, 2) + dotStr;
					if(dotStr.length > 6)
						dotStr = dotStr.substring(0, 6);
					else
						dotStr = dotStr.substring(0, dotStr.length);
					//dotStr = dotValue.substring(0, 2);
				}
			}
		} else {
			dotStr = "00";
		}

		intStr = valueArr[0];

		if (intStr == "" || intStr == null)
			intStr = "0";

		outStr = intStr + "." + dotStr;

		if (outStr == "" || outStr == null || outStr == ".00"
				|| outStr == "0.00" || outStr == ".")
			inObject.value = "";
		else
			inObject.value = outStr;

		return;
	}
	function enterKey()
	{
		event=arguments.callee.caller.arguments[0] || window.event;
		if (event.keyCode==13 && event.srcElement.type.toLowerCase()!="textarea") 
	   {
	       event.keyCode=9;
	   }
	   return;
	}

	function enterKeyEmpty()
	{
	}

/**
 * 锁屏直到页面刷新（div在tld.jsp中定义）
 */
function screenLock() {
	$("#screenLockDiv").css({"width" : document.body.scrollWidth,
		"height" : document.body.scrollHeight,
		"opacity" : "0.5"
	}).fadeIn('normal');
	document.getElementsByTagName('body')[0].style.overflow = 'hidden'; // 隐藏滚动条
}

/**
 * 解除锁屏
 */
function screenUnlock() {
	$("#screenLockDiv").fadeOut('normal');
	document.getElementsByTagName('body')[0].style.overflow = 'auto'; // 显示滚动条
}


function submitJsMethod(form,otherExtend){
	//screenLock();
	if(func_uior_calAll(form)){
		try{
			if(otherExtend=="" ||otherExtend=='null' ||otherExtend=='undefined' ||otherExtend==null){
				return true;
			}
			var flag =true;
			var otherExtendGroup = otherExtend.split(";");
             for(var k=0;k<otherExtendGroup.length;k++ ){
            		if (otherExtendGroup[k] != null && otherExtendGroup[k] != undefined && otherExtendGroup[k] != '') {
            			 flag = eval(otherExtendGroup[k]);
					if(!flag){
			   		  screenUnlock();
			   		  return false;
				    }
             }
            }
			return flag;
		}catch(e){
			return true;
		}
	}else {
		//screenUnlock();
		return false;
	};
}
function firstEadisPageFlag(){
	document.all('eadis_page').value='1';
	return true;
}
/**
 * 控制页面中 数据类型要素获取焦点时，是全部选中效果。再次点击文本域，可在定位文本域具体的位置。
 */
var numericaltimes = 0;
function selectInput(obj){
	if(++numericaltimes==1){
		obj.select();
	}
}

function resetTimes(){
	numericaltimes = 0;
}
/**
 * 该JS未使用
 */
function out(obj){
		//obj.style.color = "black";
}
/**
 * 该JS未使用
 */
function over(obj){
		//obj.style.color = "#06F";
}

/**
 * 显示textarea剩余可输入汉字数的Div（onfoucs事件，div在/include/tld.jsp中定义）
 * @param textarea 多行文本域对象
 */
function showCharsInfo(textarea) {
	if (textarea.maxlength && !document.getElementById(textarea.name).readOnly) {
		document.getElementById(textarea.name + "-charsdiv").style.display = "inline";
		textareaInputCount(textarea);
	}
}
/**
 * 隐藏textarea剩余可输入汉字数的Div（onblur事件，div在/include/tld.jsp中定义）
 * @param textarea 多行文本域对象
 */
function hideCharsInfo(textarea) {
	if (textarea.maxlength && !document.getElementById(textarea.name).readOnly) {
		document.getElementById(textarea.name + "-charsdiv").style.display = "none";
	}
}
/**
 * 计算并限制textarea可输入字节数（onkeyup事件，div在/include/tld.jsp中定义，一个汉字等于两个字节）
 * @param textarea 多行文本域对象
 */
function textareaInputCount(textarea) {
	var strValue = textarea.value; // 当前文本域值
	var strMaxlength = textarea.maxlength; // 可输入最大字节数
    var byteCount = 0; // 已经输入的字节数
	var remainlength = strMaxlength; // 剩余字节数
    for (var i = 0; i < strValue.length; i++) {
        var c = strValue.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            byteCount++; //单字节加1
        } else {
            byteCount += 2;
        }
		remainlength = strMaxlength - byteCount;
		if (byteCount > strMaxlength) {
			textarea.value = strValue.substring(0, i);
			byteCount = strMaxlength;
			remainlength = 0;
			break;
		}
	}
    if (document.getElementById(textarea.name + "-charsdiv")) {
    	document.getElementById(textarea.name + "-charsdiv").innerHTML = parseInt(remainlength/2);
    }
}

/**
 * 担保方式选择组件
 * @param input 绑定对象
 * @param kindNo 产品编号
 */
function bindVouTypeByKindNo(input, kindNo){
	var $obj = $(input);
	
	if($obj.length > 0){
		$obj.popupSelection({
	    	ajaxUrl:webPath+"/mfSysKind/getVouTypeSelectByNoAjax?kindNo="+kindNo,
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false//单选
		});
		//绑定完成后执行
		setTimeout(function(){
		    if(typeof($obj.attr('readonly'))!='undefined'){
		    	$obj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
		    }
		}, 10);
	}
}

//获取该产品的其他担保方式
function bindVouTypeByKindNoOther(input, kindNo){
    var $obj = $(input);
    var  vouType  =  $("select[name=vouType]").val();
    if($obj.length > 0){
        $obj.popupSelection({
            ajaxUrl:webPath+"/mfSysKind/getVouTypeOtherSelectByNoAjax?kindNo="+kindNo+"&vouTypeOwner="+vouType,
            searchOn:false,//启用搜索
            inline:true,//下拉模式
            multiple:true//单选
        });
        //绑定完成后执行
        setTimeout(function(){
            if(typeof($obj.attr('readonly'))!='undefined'){
                $obj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }, 10);
    }
}

function checkDouble (obj){   
	var val=obj.value;  
	if(!/^\d+(\.\d{0,2})?$/.test(val)){   
		obj.value = val.substr(0,val.length-1);
	}
}
/**
 * 
 * @param obj 当前input框  this
 * @param type 类型  
 * 1：所有操作员,
 * 2：共同借款人,
 * 3：渠道商,
 * 4：保证人,
 * 5：核心企业,
 * 6:企业和个人客户（逾期客户）
 * 9：担保公司,
 * 12：渠道操作员,
 * 18：追偿期数,
 * 19：在贷信息,
 * 20：合作银行,
 * A：集团客户,
 * B：客户信息（按客户类型）
 * @param hide 存放真实编号的隐藏域name
 * @param parmTitle 标题
 * @param parmMultipleFlag false单选，true多选，默认多选
 * @param cusBaseType 客户大类 1-企业客户 2-个人客户
 * @param cusType
 * @param ifFilterFlag 是否过滤当前操作员1-是 0-否
 * @param formId_ 详情页面单字段编辑是悬浮框，取不到formId，所以用参数方式处理
 * @param ifAdd 共借人新增按钮是否隐藏
 * @param app_relNo 关联编号
 */
function bindDataSource(obj, type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_,ifAdd,app_relNo,busModel){
	//表单编号
	var formId = $(obj).parents('form').find('input[name=formId]').val();
	if (formId_) {// 当formId_有值时，以formId_为准
		formId = formId_;
	}
	//绑定事件的input框
	var element = $(obj).attr('name');
    var multipleFlag;
    var ajaxUrl;
    var title;
	if(type == '1'){//选择所有操作员
		if (!ifFilterFlag) {
			ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
		}
		multipleFlag = false;//多选标志 true多选,false 单选
		 if(parmMultipleFlag != undefined){
			 multipleFlag = parmMultipleFlag;
		 }
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: multipleFlag, //false单选，true多选，默认多选
			ajaxUrl : webPath+"/mfUserPermission/getOpDataSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag + "&opNoType=1"+"&relNo="+app_relNo,// 请求数据URL
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: "选择人员",//标题
            searchplaceholder: "人员名称/人员编号/部门",//明确支持的过滤条件
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name="+element+"]"));
                var sltVal = elem.data("selectData");
                if(typeof(callback)== "function"){
                    callback(sltVal);
                }
			},
			tablehead:{//列表显示列配置
				"opName":"人员名称",
				"opNo":"人员编号",
                "brName":"部门",
                "roleName":"角色"
			},
			returnData:{//返回值配置
				disName:"opName",//显示值
				value:"opNo"//真实值
			}
		});
	}else  if(type == '12'){//选择渠道操作员
			if (!ifFilterFlag) {
				ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
			}
			multipleFlag = false;//多选标志 true多选,false 单选
			 if(parmMultipleFlag != undefined){
				 multipleFlag = parmMultipleFlag;
			 }
			$(obj).popupList({
				searchOn: true, //启用搜索
				multiple: multipleFlag, //false单选，true多选，默认多选
				ajaxUrl : webPath+"/mfUserPermission/getOpDataSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag + "&opNoType=2",// 请求数据URL
				valueElem:"input[name="+hide+"]",//真实值选择器
				title: "选择人员",//标题
				changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name="+element+"]"));
					var sltVal = elem.data("selectData");
					// 根据操作员编号查渠道
					$.ajax({
						url : webPath + "/sysUser/getByIdAjax",
						data : {
							opNo:sltVal.opNo
							},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if (data.flag == "success") {
								$("input[name='channelSource']").val(data.brName);
								$("input[name='channelSourceNo']").val(data.brNo);
							} else {
								alert(data.msg, 0);
							}
						},
						error : function() {
							alert("查询失败",0);
						}
					});
				},
				tablehead:{//列表显示列配置
					"opName":"人员名称",
					"opNo":"人员编号"
				},
				returnData:{//返回值配置
					disName:"opName",//显示值
					value:"opNo"//真实值
				}
			});
		}
	else if(type == '3'){// 渠道商
		var showName = "渠道商";
		 if(parmTitle != undefined){
			 showName = parmTitle;
		 }
		 
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfUserPermission/getChannelSourceAjax?formId="+formId+"&element="+element,//请求数据URL
//			valueElem:"input[name='channelSourceNo']",//真实值选择器
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: "选择" + showName,//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name="+element+"]"));
			},
			tablehead:{//列表显示列配置
				"trenchUid":showName + "编号",
				"trenchName":showName
			},
			returnData:{//返回值配置
				disName:"trenchName",//显示值
				value:"trenchUid"//真实值
			}
		});
	}else if(type == '4'){// 保证人
        if(typeof(busModel) !='undefined'&& busModel==12){
            if(typeof(cusNo) == "undefined"){
                var  cusNo = $("input[name=cusNo]").val();
            }
            ajaxUrl =webPath+"/mfUserPermission/getAssureDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo + "&cusType=" + cusType+"&cusBaseType="+cusBaseType + "&appId=" + appId;//请求数据URL;
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: parmMultipleFlag, //false单选，true多选，默认多选
                ajaxUrl:  ajaxUrl,
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: parmTitle,//标题
                labelShow:false,
                elemEdit:true,
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                    var sltVal = elem.data("selectData");
                    if(typeof(callback)== "function"){
                        callback(sltVal);
                    }
                    var idNum = $("input[name="+hide+"]").val();
                    //查询是否重名
                    $.ajax({
                        url : webPath+"/mfCusCustomer/getCusByIdAjax",
                        type : "post",
                        async: false,
                        data : {idNum:idNum},
                        dataType : "json",
                        success : function(data) {
                            if (data.flag == "success") {
                                if (data.listCus == "1") {
                                    dialog({
                                        id: 'cusMoreDialog',
                                        title: "重名客户",
                                        url: webPath + "/mfCusCustomer/getCusListByName?sign=sure&cusNo="+data.cusInfo.cusNo,
                                        width: 500,
                                        height: 300,
                                        backdropOpacity: 0,
                                        onshow: function () {
                                            this.returnValue = null;
                                        },
                                        onclose: function () {
                                            if (this.returnValue) {
                                                //返回对象的属性:实体类MfCusCustomer中的所有属性
                                                if (typeof(callback) == "function") {
                                                    callback(this.returnValue);
                                                } else {
                                                    getCusInfoArtDialog(this.returnValue);
                                                }
                                            }
                                            var assureNo = $("input[name=assureNo]").val();
                                            if(assureNo ==null || assureNo==""){//取消
                                                $("input[name='assureName']").parent().find(".pops-value").click();
                                            }
                                        }
                                    }).showModal();
                                }
                            }else{

                            }
                        },
                        error : function() {
                            window.top.alert("请重新选择客户！",1);
                        }
                    });
                },
                tablehead:{//列表显示列配置
                    "cusName":{"disName":"客户名称","align":"center"},
                    "idNum":{"disName":"证件号码","align":"center"},
                    "ext7":{"disName":"长期担保有效期","align":"center"}
                },
                returnData:{//返回值配置
                    disName:"cusName",//显示值
                    value:"idNum"//真实值
                }
            });
		}else{
            if(typeof(cusNo) == "undefined"){
                var  cusNo = $("input[name=cusNo]").val();
            }
            ajaxUrl =webPath+"/mfUserPermission/getAssureDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo + "&cusType=" + cusType+"&cusBaseType="+cusBaseType + "&appId=" + appId;//请求数据URL;
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: parmMultipleFlag, //false单选，true多选，默认多选
                ajaxUrl:  ajaxUrl,
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: parmTitle,//标题
                labelShow:false,
                elemEdit:true,
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                    var sltVal = elem.data("selectData");
                    if(typeof(callback)== "function"){
                        callback(sltVal);
                    }
                    var idNum = $("input[name="+hide+"]").val();
                    //查询是否重名
                    $.ajax({
                        url : webPath+"/mfCusCustomer/getCusByIdAjax",
                        type : "post",
                        async: false,
                        data : {idNum:idNum},
                        dataType : "json",
                        success : function(data) {
                            if (data.flag == "success") {
                                if (data.listCus == "1") {
                                    dialog({
                                        id: 'cusMoreDialog',
                                        title: "重名客户",
                                        url: webPath + "/mfCusCustomer/getCusListByName?sign=sure&cusNo="+data.cusInfo.cusNo,
                                        width: 500,
                                        height: 300,
                                        backdropOpacity: 0,
                                        onshow: function () {
                                            this.returnValue = null;
                                        },
                                        onclose: function () {
                                            if (this.returnValue) {
                                                //返回对象的属性:实体类MfCusCustomer中的所有属性
                                                if (typeof(callback) == "function") {
                                                    callback(this.returnValue);
                                                } else {
                                                    getCusInfoArtDialog(this.returnValue);
                                                }
                                            }
                                            var assureNo = $("input[name=assureNo]").val();
                                            if(assureNo ==null || assureNo==""){//取消
                                                $("input[name='assureName']").parent().find(".pops-value").click();
                                            }
                                        }
                                    }).showModal();
                                }
                            }else{

                            }
                        },
                        error : function() {
                            window.top.alert("请重新选择客户！",1);
                        }
                    });
                },
                tablehead:{//列表显示列配置
                    "cusName":{"disName":"客户名称","align":"center"},
                    "idNum":{"disName":"证件号码","align":"center"}
                },
                returnData:{//返回值配置
                    disName:"cusName",//显示值
                    value:"idNum"//真实值
                }
            });
		}

	} else if(type == '7') {//联保体
        if (typeof(cusNo) == "undefined") {
            var cusNo_1 = $("input[name=cusNo]").val();
        }
        ajaxUrl = webPath + "/mfBusAlliance/getOtherCus?cusNo=" + cusNo_1;//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: parmMultipleFlag, //false单选，true多选，默认多选
            ajaxUrl: ajaxUrl,
            valueElem: "input[name=" + hide + "]",//真实值选择器
            title: parmTitle,//标题
            labelShow: false,
            elemEdit: true,
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=" + element + "]"));
                var sltVal = elem.data("selectData");
                if (typeof(callback) == "function") {
                    callback(sltVal);
                }
            },
            tablehead: {//列表显示列配置
         		"cusName": "客户名称",
                "ext1": "证件号码"
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "ext1"//真实值
            }
        });
    }else if (type == '9') {// 担保公司
		ajaxUrl = webPath+"/mfCusAssureCompany/getAssureCompanyAjax?cusType=" + cusType + "&cusBaseType=" + cusBaseType + "&appId=" + appId;// 请求数据URL;
		$(obj).popupList({
			searchOn : true, // 启用搜索
			multiple : parmMultipleFlag, // false单选，true多选，默认多选
			ajaxUrl : ajaxUrl,
			valueElem : "input[name=" + hide + "]",// 真实值选择器
			title : parmTitle,// 标题
			labelShow : false,
			changeCallback : function(elem) {// 回调方法
				BASE.removePlaceholder($("input[name=" + element + "]"));
				var sltVal = elem.data("selectData");		
				$("select[name='idType']").val(sltVal.idType);
				$("input[name='idNum']").val(sltVal.idNum);
			},
			tablehead : {// 列表显示列配置
			/*	"cusName" : "担保公司名称",
				"cusNo" : "担保公司编号",
                "idNum" : "社会信用代码"*/
                "cusName":{"disName":"担保公司名称","align":"center"},
                "idNum":{"disName":"社会信用代码","align":"center"}
			},
			returnData : {// 返回值配置
				disName : "cusName",// 显示值
				value : "cusNo"// 真实值
              /*  value : "idNum"// 真实值*/
			}
		});
	} else if (type == 'A') {// 集团客户
		ajaxUrl = webPath+"/mfCusGroup/getCusGroupAjax";// 请求数据URL;
		$(obj).popupList({
			searchOn : true, // 启用搜索
			multiple : parmMultipleFlag, // false单选，true多选，默认多选
			ajaxUrl : ajaxUrl,
			valueElem : "input[name=" + hide + "]",// 真实值选择器
			title : parmTitle,// 标题
			labelShow : false,
			changeCallback : function(elem) {// 回调方法
				BASE.removePlaceholder($("input[name=" + element + "]"));
				var sltVal = elem.data("selectData");
				$("input[name='groupNo']").val(sltVal.idNum);
				
			},
			tablehead : {// 列表显示列配置
				"groupName" : "集团名称",
				"idNum" : "集团代码"
			},
			returnData : {// 返回值配置
				disName : "groupName",// 显示值
				value : "idNum"// 真实值
			}
		});
	}else if(type == '5'){// 核心企业
		 title = '选择核心企业';
		 if(parmTitle != undefined){
			 title = parmTitle;
		 }
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfUserPermission/getCoreCompanySourceAjax?formId="+formId+"&element="+element,//请求数据URL
//			valueElem:"input[name='channelSourceNo']",//真实值选择器
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: title,//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name="+element+"]"));
			},
			tablehead:{//列表显示列配置
				"coreCompanyName":"核心企业",
				"coreCompanyUid":"核心企业编号"
			},
			returnData:{//返回值配置
				disName:"coreCompanyName",//显示值
				value:"coreCompanyUid"//真实值
			}
		});
	}else if(type == '6'){//企业客户和个人客户
		 title = '选择客户';
		 if(parmTitle != undefined){
			 title = parmTitle;
         }

        ajaxUrl = webPath + "/mfUserPermission/getCustomerDataSourceAjax?formId=" + formId + "&element=" + element;//请求数据URL;
        if (cusBaseType) {//客户类型
            ajaxUrl += "&cusBaseType=" + cusBaseType;
        }

        $(obj).popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl: ajaxUrl,//请求数据URL
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: title,//标题
			changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name="+element+"]"));
					var sltVal = elem.data("selectData");
					if(typeof(callback)== "function"){
						callback(sltVal);
					}
//					$("input[name='assureNo']").val(sltVal.cusNo);
//					$("input[name='idNum']").val(sltVal.idNum);
				},
			tablehead:{//列表显示列配置
				"cusName":"客户名称",
				"idNum":"证件号码"
			},
			returnData:{//返回值配置
				disName:"cusName",//显示值
				value:"cusNo"//真实值
			}
		});
	}else if(type == '60'){//企业客户和个人客户(逾期客户)
        title = '选择客户';
        if(parmTitle != undefined){
            title = parmTitle;
        }

        ajaxUrl = webPath + "/mfUserPermission/getCustomerDataSourceAjaxByOver?formId=" + formId + "&element=" + element;//请求数据URL;
        if (cusBaseType) {//客户类型
            ajaxUrl += "&cusBaseType=" + cusBaseType;
        }

        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: ajaxUrl,//请求数据URL
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: title,//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                var sltVal = elem.data("selectData");
                if(typeof(callback)== "function"){
                    callback(sltVal);
                }
//					$("input[name='assureNo']").val(sltVal.cusNo);
//					$("input[name='idNum']").val(sltVal.idNum);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "cusNo":"客户号"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
    }else if(type == '61'){//获取代偿确认后得数据
        title = '选择客户';
        if(parmTitle != undefined){
            title = parmTitle;
        }

        ajaxUrl = webPath + "/mfUserPermission/getCustomerDataSourceAjaxByCompensatory?formId=" + formId + "&element=" + element;//请求数据URL;
        if (cusBaseType) {//客户类型
            ajaxUrl += "&cusBaseType=" + cusBaseType;
        }

        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: ajaxUrl,//请求数据URL
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: title,//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                var sltVal = elem.data("selectData");
                if(typeof(callback)== "function"){
                    callback(sltVal);
                }
//					$("input[name='assureNo']").val(sltVal.cusNo);
//					$("input[name='idNum']").val(sltVal.idNum);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "cusNo":"客户号"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
    }else if(type == 'B'){//选择客户信息，按客户类型
		 title = '选择客户';
		 if(parmTitle != undefined){
			 title = parmTitle;
		 }
		 if(cusType == undefined){
             cusType = '';
		 }
        if(ifFilterFlag == undefined){
            ifFilterFlag = '';
        }
        if(app_relNo == undefined){
            app_relNo = '';
        }
        if(cusBaseType == undefined){
            cusBaseType = '';
        }
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfUserPermission/getCustomerInfoAjax?formId="+formId+"&element="+element+"&cusType="+cusType+"&ifFilterFlag="+ifFilterFlag+"&cusNo="+app_relNo+"&cusBaseType="+cusBaseType,//请求数据URL
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: title,//标题
			changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name="+element+"]"));
					if(typeof(callback)== "function"){
                        var sltVal = elem.data("selectData");
						callback(sltVal);
					}
				},
			tablehead:{//列表显示列配置
				"cusName":"客户名称",
                "idNum":"证件号码"
			},
			returnData:{//返回值配置
				disName:"cusName",//显示值
				value:"cusNo"//真实值
			}
		});
	}else if(type=='19'){
        var cusNo_2 = $(obj).parents('form').find('input[name=cusNo]').val();
        if (!ifFilterFlag) {
            ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
        }
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfUserPermission/getContractLoanAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag+"&cusNo="+cusNo_2,// 请求数据URL
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: "选择在贷信息",//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                var sltVal = elem.data("selectData");
                $("input[name='pactNo']").val(sltVal.pactNo);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "appId":"申请Id",
                "pactNo":"合同号"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"pactNo"//真实值
            }
        });
    }else if(type=='18'){
		var fincId = $(obj).parents('form').find('input[name=fincId]').val();
		if (!ifFilterFlag) {
			ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
		}
		multipleFlag = false;//多选标志 true多选,false 单选
		 if(parmMultipleFlag != undefined){
			 multipleFlag = parmMultipleFlag;
		 }
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: multipleFlag, //false单选，true多选，默认多选
			ajaxUrl : webPath+"/mfUserPermission/getAmountSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag+"&fincId="+fincId,// 请求数据URL
			valueElem:"input[name="+hide+"]",//真实值选择器
			title: "选择追偿期数",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name="+element+"]"));
				var sltVal = elem.data("selectData");
				$("input[name='planId']").val(sltVal.planId);
			},
			tablehead:{//列表显示列配置
				"termNum":"期号",
				"compensatoryFeeSum":"代偿总额",
				"planId":"流水号"	
			},
			returnData:{//返回值配置
				disName:"compensatoryFeeSum",//显示值
				value:"compensatoryFeeSum"//真实值
			}
		});
	}else if(type=='20'){// 合作银行
        var kindNo = $('[name=kindNo]').val();
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListByKindNoAjax?kindNo="+kindNo,// 请求数据URL
            valueElem : "input[name=agenciesId1]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName1]").val(sltVal.agenciesName);
                $("input[name=agenciesId1]").val(sltVal.agenciesId);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "address" : "地址",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
	}else if(type=='21'){// 开票信息
        var cusNo = $('[name=cusNo]').val();
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfCusInvoiceMation/findByCusNoPageAjax?cusNo="+cusNo,// 请求数据URL
            valueElem : "input[name=invoiceMationId]",//真实值选择器
            title : "选择纳税人识别号",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=taxpayerNo]").val(sltVal.taxpayerNo);
                $("input[name=tel]").val(sltVal.tel);
                $("input[name=address]").val(sltVal.address);
                $("input[name=billBankName]").val(sltVal.bankName);
                $("input[name=accountNumber]").val(sltVal.accountNumber);
            },
            tablehead : {//列表显示列配置
                "taxpayerNo" : "纳税人识别号",
                "tel" : "电话",
                "address" : "地址",
                "bankName" : "开户行",
                "accountNumber" : "账号"
            },
            returnData : {//返回值配置
                disName : "taxpayerNo",//显示值
                value : "id"//真实值
            }
        });
    }else if(type=='22'){// 受益人
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfCusCooperativeAgency/findSearchByPageAjax",// 请求数据URL
            valueElem : "input[name=beneficiary]",//真实值选择器
            title : "选择受益人",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=ext4]").val(sltVal.orgaName);
            },
            tablehead : {//列表显示列配置
                "orgaName" : "受益人名称",
                "socialCreditCode" : "社会信用代码",
                "legalRepresName" : "法人代表姓名",
                "legalIdNum" : "法人代表证件号码",
            },
            returnData : {//返回值配置
                disName : "orgaName",//显示值
                value : "orgaNo"//真实值
            }
        });
    }else if(type=='23'){// 领导班子委员
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/wkfApprovalUser/findByPageRoleAjax?roleNo=PS0001",// 请求数据URL
            valueElem : $(obj).parents("tr").prev().find("[name='reviewMemberNo']"),//真实值选择器
            title : parmTitle,//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $(obj).val(sltVal.displayname);
            },
            tablehead : {//列表显示列配置
                "wkfUserName" : "审批用户编号",
                "displayname" : "审批用户名称",
            },
            returnData : {//返回值配置
                disName : "displayname",//显示值
                value : "wkfUserName"//真实值
            }
        });
    }else if(type=='24'){// 业务部门委员
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/wkfApprovalUser/findByPageRoleAjax?roleNo=PS0002",// 请求数据URL
            valueElem : $(obj).parents("tr").prev().find("[name='reviewMemberNo']"),//真实值选择器
            title : parmTitle,//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $(obj).val(sltVal.displayname);
            },
            tablehead : {//列表显示列配置
                "wkfUserName" : "审批用户编号",
                "displayname" : "审批用户名称",
            },
            returnData : {//返回值配置
                disName : "displayname",//显示值
                value : "wkfUserName"//真实值
            }
        });
    }else if(type=='25'){// 职能部门委员
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/wkfApprovalUser/findByPageRoleAjax?roleNo=PS0003",// 请求数据URL
            valueElem : $(obj).parents("tr").prev().find("[name='reviewMemberNo']"),//真实值选择器
            title : parmTitle,//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $(obj).val(sltVal.displayname);
            },
            tablehead : {//列表显示列配置
                "wkfUserName" : "审批用户编号",
                "displayname" : "审批用户名称",
            },
            returnData : {//返回值配置
                disName : "displayname",//显示值
                value : "wkfUserName"//真实值
            }
        });
    }else if(type=='26'){// 业务部门替补委员
        multipleFlag = false;//多选标志 true多选,false 单选
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        $(obj).popupList({
            searchOn : true, //启用搜索
            multiple : multipleFlag, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/wkfApprovalUser/findByPageRoleAjax?roleNo=PS0004",// 请求数据URL
            valueElem : $(obj).parents("tr").prev().find("[name='reviewMemberNo']"),//真实值选择器
            title : parmTitle,//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $(obj).val(sltVal.displayname);
            },
            tablehead : {//列表显示列配置
                "wkfUserName" : "审批用户编号",
                "displayname" : "审批用户名称",
            },
            returnData : {//返回值配置
                disName : "displayname",//显示值
                value : "wkfUserName"//真实值
            }
        });
    }
	else{
		 title = '选择共同借款人';
		 multipleFlag = true;//多选标志 true多选,false 单选
		if(typeof(cusNo) == "undefined"){
			var  cusNo_3 = $("input[name=cusNo]").val();
		}
		 ajaxUrl =webPath+"/mfUserPermission/getPerDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo_3 + "&cusType=" + cusType;//请求数据URL;
		 if(parmTitle != undefined){
			 title = parmTitle;
		 }
		 if(parmMultipleFlag != undefined){
			 multipleFlag = parmMultipleFlag;
		 }
		 if(cusBaseType != undefined){//客户类型
			 ajaxUrl+="&cusBaseType="+cusBaseType;
		 }
		 if(ifAdd){
			 $(obj).popupList({
				 searchOn: true, //启用搜索
				 multiple: multipleFlag, //false单选，true多选，默认多选
				 ajaxUrl:ajaxUrl,
				 valueElem:"input[name="+hide+"]",//真实值选择器
				 title: title,//标题
				 labelShow:false,
                 searchplaceholder: "客户名称/证件号码",//查询输入框的悬浮内容
				 changeCallback:function(elem){//回调方法
					 BASE.removePlaceholder($("input[name="+element+"]"));
					 var sltVal = elem.data("selectData");
				 },
				 tablehead:{//列表显示列配置
					 "cusName":"客户名称",
					 "idNum":"证件号码"
				 },
				 returnData:{//返回值配置
					 disName:"cusName",//显示值
					 value:"cusNo"//真实值
				 }
			 });
		 }else{
			 $(obj).popupList({
				 searchOn: true, //启用搜索
				 multiple: multipleFlag, //false单选，true多选，默认多选
				 ajaxUrl:ajaxUrl,
				 valueElem:"input[name="+hide+"]",//真实值选择器
				 title: title,//标题
				 labelShow:false,
                 searchplaceholder: "客户名称/证件号码",//查询输入框的悬浮内容
				 changeCallback:function(elem){//回调方法
					 BASE.removePlaceholder($("input[name="+element+"]"));

					 var sltVal = elem.data("selectData");
				 },
				 tablehead:{//列表显示列配置
					 "cusName":"客户名称",
					 "idNum":"证件号码"
				 },
				 returnData:{//返回值配置
					 disName:"cusName",//显示值
					 value:"cusNo"//真实值
				 },
				 addBtn:{//添加扩展按钮
					 "title":"新增",
					 "fun":function(hiddenInput, elem){
						 top.window.openBigForm(webPath+"/mfCusCustomer/inputCoborr","新增客户", function(){
							 $(obj).popupList("initItems",$(obj),$(obj).data("options"));
						 });
					 }
				 }
			 });
		 }
	}

	$('input[name='+element+']').next().click();
}

/*
 * To custom css style for td of table.
 */
function setTableTdCSS(tableId, attrName, attrValue) {
	$( (tableId + " tr>td") ).each(function(){
		$(this).css(attrName, attrValue);
	});
}


//半角转换为全角函数 
function halfToFullWidth(obj) {
var	txtstring = obj.value;
var tmp = ""; 
	for(var i=0;i<txtstring.length;i++) { 
		if(txtstring.charCodeAt(i)==32) { 
		tmp= tmp+ String.fromCharCode(12288); 
		} else if(txtstring.charCodeAt(i)<127 && txtstring.charCodeAt(i)>32) { 
		 	tmp=tmp+String.fromCharCode(txtstring.charCodeAt(i)+65248); 
		} else{
			tmp += String.fromCharCode(txtstring.charCodeAt(i));
		}
	} 
obj.value=tmp;
} 
//全角转换为半角函数 
function fullToHalfWidth(obj) { 
var str = obj.value;
var tmp = ""; 
for(var i=0;i<str.length;i++) { 
	if(str.charCodeAt(i)==12288) { 
		tmp= tmp+ String.fromCharCode(32); 
	} else if(str.charCodeAt(i)>65280&&str.charCodeAt(i)<65375) { 
		tmp += String.fromCharCode(str.charCodeAt(i)-65248); 
	} else { 
		tmp += String.fromCharCode(str.charCodeAt(i)); 
	} 
} 
obj.value=tmp;
}
//父页面跳转
function refparent(url){
	if(parent.document.getElementById('wrapper')){
		parent.location.href = url;
	}else{
		window.location.href = url;
	}
}
//银行卡格式化
function inputAccount(obj) {
	var str = $(obj).val();
	var i, charValue;
	for(i = 0; i < str.length; i++) {
		charValue = str.charAt(i);
		if(isNaN(parseInt(charValue, 10)) && (charValue != " ")) {
			obj.value = str.substring(0, i) + str.substring(i + 1, str.length);
			return;
		}
	}
	var c = str.replace(/\s/g, "");
	if(str != "" && c.length > 4 && c.length % 4 == 1) {
		$(obj).val(str.substring(0, str.length - 1) + " " + str.substring(str.length - 1, str.length));
	}

	if(obj.setSelectionRange) { //W3C  
		setTimeout(function() {
			obj.setSelectionRange(obj.value.length, obj.value.length);
			obj.focus();
		}, 0);
	} else if(obj.createTextRange) { //IE  
		var textRange = obj.createTextRange();
		textRange.moveStart("character", obj.value.length);
		textRange.moveEnd("character", 0);
		textRange.select();
	}
}
;

function bindCnapsDataSource(obj) {
    var element = $(obj).attr('name');
    $(obj).popupList({
        searchOn : true, //启用搜索
        multiple : false, //false单选，true多选，默认多选
        ajaxUrl : webPath + "/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
        valueElem : "input[name=bankNumbei]",//真实值选择器
        title : "选择行号",//标题
        changeCallback : function(elem) {//回调方法
            BASE.removePlaceholder($("input[name=bankNumbei]"));
            BASE.removePlaceholder($("input[name=bank]"));
            var sltVal = elem.data("selectData");
            $("input[name=bankNumbei]").val(sltVal.bankcode);
            $("input[name=bank]").val(sltVal.bankname);
        },
        tablehead : {//列表显示列配置
            "bankcode" : "行号",
            "bankname" : "行名"
        },
        returnData : {//返回值配置
            disName : "bankcode",//显示值
            value : "bankname"//真实值
        }
    });
    $('input[name='+element+']').next().click();
};
function bindCnapsDataSourceDetail(obj) {
    var element = $(obj).attr('name');
    $(obj).popupList({
        searchOn : true, //启用搜索
        multiple : false, //false单选，true多选，默认多选
        ajaxUrl : webPath + "/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
        valueElem : "input[name=bankNumbei]",//真实值选择器
        title : "选择行号",//标题
        changeCallback : function(elem) {//回调方法
            var sltVal = elem.data("selectData");
            BASE.oneRefreshTable("bank",sltVal.bankname);
            $("input[name=bankNumbei]").val(sltVal.bankcode);
            $("input[name=bank]").val(sltVal.bankname);
            $(obj).parents().parents().find(".ok_dbl").click();
        },
        tablehead : {//列表显示列配置
            "bankcode" : "行号",
            "bankname" : "行名"
        },
        returnData : {//返回值配置
            disName : "bankcode",//显示值
            value : "bankname"//真实值
        }
    });
    $('input[name='+element+']').next().click();
};

function getRelCorpInfo(){
    $("input[name=relCorpName]").popupList({
        searchOn : true, // 启用搜索
        multiple : true, // false单选，true多选，默认多选
        ajaxUrl : webPath+"/mfUserPermission/getRelCorpInfo",// 请求数据URL;,
        valueElem : "input[name=relCorpNo]",// 真实值选择器
        title : "选择客户",// 标题
        labelShow : false,
        changeCallback : function(elem) {// 回调方法
            BASE.removePlaceholder($("input[name=relCorpName]"));
        },
        tablehead : {// 列表显示列配置
            "cusName" : "客户名称",
            "idNum" : "证件号码"
        },
        returnData : {// 返回值配置
            disName : "cusName",// 显示值
            value : "cusNo"// 真实值
        }
    });
    $('input[name=relCorpName]').next().click();
}

var BASE = function () {
	//选择组件新增数据字典
	var _openDialogForSelect = function (title, keyName, elem) {
		dialog({
			id:"parmDialog",
    		title:title,
    		url: webPath+'/parmDic/inputForSelect?keyName='+keyName,
    		width:750,
    		height:400,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				//返回对象的属性:实体类MfCusCustomer中的所有属性
					var parmDicInfo = this.returnValue;
					if(parmDicInfo != undefined){
						var newNode = {"id":parmDicInfo.optCode,"name":parmDicInfo.optName};
						$(elem).popupSelection("addItem",newNode);
						//新增后选择该数据
						$(elem).popupSelection("selectedItem",newNode);
					}
    			}
    		}
    	}).showModal();
	}
	var _saveParmDic=function saveParmDic(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {
						parent.dialog.get('parmDialog').close(data.parmDicInfo).remove();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				},
				complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	}
	var _removePlaceholder = function(obj) {
		$(obj).parent().find('.wrap-placeholder').remove();
	}
	//下拉单字段编辑更新列表 name下拉字段名称 value 修改后的值
	var _oneRefreshTable = function (name,value){
		//由于页面存在相同的字段不能直接通过th去获取
		var index = $(".listshow-tr").parents("table").parent().find("th[name="+name+"]").index();
		//index=-
		if(index!=-1){
			var $col = $(".listshow-tr").prev().find("td").eq(index);
			var length = $col.find('a').length;
			//长度过长显示 modified by YaoWenHao
			if(value!=null&&value.length>12){
				value=value.substring(0,12)+"...";
			}
			//普通字段
			if(length==0){
				$col.html(value);
			}else if (length==1){//超链接字段
				$col.find('a').html(value);
			}
		}
		
		
	}
	
	
	return {
		openDialogForSelect : _openDialogForSelect,
		saveParmDic : _saveParmDic,
		removePlaceholder : _removePlaceholder,
		oneRefreshTable:_oneRefreshTable,
		getIconInTd : function (obj) {
			var $i = $(obj).parents("td").find(".input-group-addon i");
			if ($i.length > 0) {
				return $i[0];
			}
		}
	};
} ();