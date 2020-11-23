var stack = new Stack(); //用于把中缀式转换成后缀式
var errorMsg = ''; //记录错误信息
var curFormula; //记录当前解析的公式
/*
 * 提交表单保存时验证输入框数据类型,验证表单内公式
 */
function func_uior_calAll(frm) {
	var allResult = 0;
	errorMsg = func_uior_valForm(frm);	//验证必填项
	if (errorMsg == '' || errorMsg == 'undefined') {
		var formulasObj = frm.elements['formulavalidate'];
		if (formulasObj != undefined && formulasObj != null) {
			var formulas = formulasObj.value;
			var funcs = formulas.split(';');
			for ( var i = 0; i < funcs.length; i++) {
				var formula = funcs[i];
				if (formula != null && formula != undefined && formula != '') {
					curFormula = formula;
					var result = func_uior_cal(formula, frm);
					//返回值是否为true
					if (!result) {
						allResult = 1;
						curFormula = parseFormula(curFormula);
						var strs = curFormula.split(/\||\&>=|<=|!=|=|>|<|\+|-|\*|\/|\(|\)/);
						for ( var j = 0; j < strs.length; j++) {
							var title = '';
							//var obj = document.all(strs[j]);
							var obj = func_getobj(frm,strs[j]);
							if (strs[j].charAt(0) == '$') {
								obj = document.getElementById("sysDate");
								title = "交易日期";
							} else {
								if(typeof obj == "NodeList" && obj.length>0){
									obj = obj.item(0);
									title = obj.getAttribute('title');
								} else {
									if(obj){
										if(obj.length>1){
											title = obj[0].getAttribute('title');
											//console.log(obj[0].getAttribute('title')+"("+obj.length+"个)");
										}else{
											title = obj.getAttribute('title');
										}
									}else {
										title = strs[j];
									}
								}
							}
							if(obj && title){
								curFormula = curFormula.replace(strs[j], title);
							}
						}
						if(errorMsg!=''){
							errorMsg = errorMsg + "\n校验条件:[ "+curFormula+" ]不满足!";
						} else {
							errorMsg = "校验条件:[ "+curFormula+" ]不满足!";
						}
						break;
					}
				}
			}
		}
	}
	//有错误信息,则表示没通过校验
	if (errorMsg != '') {
			window.top.alert(errorMsg,0);
		return false;
	} else {
		//如果所有公式都返回true
		if (allResult == 0) {
			return true;
		} else {
			return false;
		}
	}

}
//大表单document获取字段为了防止获取多个,遍历form获取。
function func_getobj(frm,name){
	var obj;
	if(name!==undefined&&name!=null&&name!=""){
		for (var i=0;i<frm.length;i++){
			if(frm.elements[i].name==name){
				return frm.elements[i];
			}
		}
	}
	return obj;
}

//主方法入口
function func_uior_cal(formula, frm) {
	formula = parseFormula(formula); //对公式进行格式化
	formula = transFormula(formula, frm); //对公式进行转换
	var result = logic(formula); //对公式含有逻辑运算符的情况下,进行逻辑运算
	if (/@/.test(result)) { //进行逻辑运算返回的值为 1@或0@用于区分四则运算结果为1,0的情况
		var flag = result.charAt(0);
		if (flag == 1) {
			result = true;
		} else {
			result = false;
		} 
	}

	return result;
}

//对公式进行格式化
function parseFormula(formula) {

	formula = formula.replace(/{/g, '('); //将所有的{}替换成()
	formula = formula.replace(/}/g, ')');
	formula = formula.replace(/\[/g, '('); //将所有的[]替换成()
	formula = formula.replace(/\]/g, ')');
	formula = formula.replace(/\&\&/g, '&'); //将所有的&&替换成&
	formula = formula.replace(/\|\|/g, '|'); //将所有的||替换成|

	return formula;
}

//对公式进行转换
function transFormula(formula, frm) {
	//将公式根据|和&分成数组
	var strs = formula.split(/\||\&/);
	for ( var i = 0; i < strs.length; i++) {
		var str = strs[i];
		str = parseParen(str); //解决分成数组后括号不匹配的问题
		//将公式根据>= <= != == > <分成数组,>=要在>之前,否则会把1>=2分成 1,=2
		var eles = str.split(/>=|<=|!=|=|>|</);
        var eleLeft,resultLeft;
		//如果长度>1说明含有以上符号
		if (eles.length > 1) {
			eleLeft = eles[0];
			var eleRight = eles[1];
			//对左半部分的四则运算进行解析并计算结果
			resultLeft = infixTrans(eleLeft, frm);
			//对右半部分的四则运算进行解析并计算结果
			var resultRight = infixTrans(eleRight, frm);
			//获得操作符
			var index = str.indexOf(eleLeft) + eleLeft.length;
			var op = str.substring(index, index + 2);
			//判断是>=类型,还是>类型
			if (op.charAt(1) != '=') {
				op = op.charAt(0);
			}
			//进行逻辑运算
			var result = doCalculate(resultLeft, resultRight, op);
			formula = formula.replace(str, result);
			if (strs.length == 1) {
				formula = formula + '@';
			}
		} else if (eles.length == 1) { //只是四则运算的情况
			eleLeft = eles[0];
			resultLeft = infixTrans(eleLeft, frm);
			formula = resultLeft;
		}
	}
	return formula;
}

//解决括号不匹配的问题
function parseParen(str) {
	var left = 0;
	var right = 0;
	for ( var i = 0; i < str.length; i++) {
		var ch = str.charAt(i);
		if (ch == '(') {
			left++;
		}
		if (ch == ')') {
			right++;
		}
	}
	var temp = left - right;
	if (temp > 0) {
		return str.substring(temp);
	} else if (temp < 0) {
		return str.substring(0, str.length + temp);
	} else {
		return str;
	}
}

//解析四则运算
function infixTrans(formula,frm) {
	var first = formula.charAt(0);
	var reg = /\d*\(*\+*-*/;	//首字母是数字,(,+,-
	if (!reg.test(first)) {
		errorMsg=errorMsg.concat(curFormula+'公式解析错误!\n');
	}
	var outPut = doTrans(formula);	//对公式进行转换
	var regTest = /(\(+)|(\)+)/;	//转换后的公式不含有()
	if (regTest.test(outPut)) {
		errorMsg=errorMsg.concat(curFormula+'公式解析错误!\n');
	} else {
		var fir = outPut.charAt(0);	//如果首位有正负符号
		if (fir == '@') {
			outPut = outPut.replace(fir, '0' + fir);
		}
		outPut = outPut.replace(/@$/, '');
		outPut = outPut.replace(/@@/g, '@');
		var strs = outPut.split('@');
		if(strs.length%2!=1){
			errorMsg=errorMsg.concat(curFormula+'表达式不正确!\n');
			return '';
		}
		strs = getValue(strs,frm);			//从页面获取值

		var result = loopCalculate(strs);//循环计算

		return result;
	}
}

//从页面获取值
function getValue(strs,frm) {
	for ( var i = 0; i < strs.length; i++) {
		var ch = strs[i];
		//(/\d/.test(ch)
		if (ch != '+' && ch != '-' && ch != '*' && ch != '/'&&/^[A-Za-z]/.test(ch)
				&& ch != '@'&&ch!='') {
			var text =0;
			ch=func_uior_trim(ch);
			if(ch.charAt(0)!='$'){
				var obj = func_getobj(frm,ch);
				if(obj!=undefined&&obj!=null){
					text = obj.value;
					var type=obj.getAttribute('datatype');
					if(type==12){
						text=text.replace(/,/g,'');
					}else if(type==6){
						text=text.replace(/-/g,'');
					}
				}else{
					return strs;
				}
			}else{
				text = document.getElementById("sysDate").value;
			}
			var num = parseFloat(text);
			if (isNaN(num)) {
			strs[i] = text;
			}else{
			strs[i] = num;
			}
		}
	}
	return strs;
}
//将中缀表达式转换成后缀表达式
function doTrans(formula) {
	var str = "";
	for ( var i = 0; i < formula.length; i++) {
		var ch = formula.charAt(i);
		switch (ch) {
		case '+':
		case '-':
			str = str.concat('@');
			str = str.concat(gotOper(ch, 1));
			break;
		case '*':
		case '/':
			str = str.concat('@');
			str = str.concat(gotOper(ch, 2));
			break;
		case '(':
			stack.push(ch);
			break;
		case ')':
			str = str.concat(gotParen(stack));
			break;
		default:
			str = str.concat(ch);
		}
		if (i == formula.length - 1)
			str = str.concat('@');
	}

	while (!stack.isEmpty()) {
		str = str.concat(stack.pop() + '@');
	}

	return str;
}

//普通四则运算
function gotOper(str, prec) {
	var outPut = "";
	if (stack.isEmpty()) {
		stack.push(str);
		return outPut;
	}
	while (!stack.isEmpty()) {
		var top = stack.peek();
		if (top == '(') {
			stack.push(str);
			break;
		} else {
			var precTop;
			if (top == '+' || top == '-') {
				precTop = 1;
			} else {
				precTop = 2;
			}
			if (precTop < prec) {
				stack.push(str);
				break;
			} else {
				outPut = outPut.concat(stack.pop() + '@');
				stack.push(str);
				break;
			}
		}
	}

	return outPut;
}

//获得(括号
function gotParen(sta) {
	var outPut = "";
	while (!sta.isEmpty()) {
		var ch = sta.pop();
		if (ch == '(') {
			break;
		} else {
			outPut = outPut.concat('@' + ch);
		}
	}

	return outPut;
}

//循环计算
function loopCalculate(strs) {
	var length = strs.length;
	while (true) {
		while (length >= 2) {
			for ( var i = 0; i < strs.length; i++) {
				if (strs[i] == '+' || strs[i] == '-' || strs[i] == '*'
						|| strs[i] == '/' || strs[i] == '|' || strs[i] == '&') {
					strs[i - 2] = doCalculate(strs[i - 2], strs[i - 1], strs[i]
							.toString());
					for ( var j = i; j < strs.length - 1; j++) {
						strs[j - 1] = strs[j + 1];
					}
					break;
				}
			}
			length -= 2;
		}
		break;
	}
	return strs[0];
}

//进行逻辑运算
function logic(str) {
	var reg = /\||\&/;
	if (reg.test(str)) {
		var logOut = "";
		var logStack = new Stack();
		for ( var i = 0; i < str.length; i++) {
			var ch = str.charAt(i);
			switch (ch) {
			case '(':
				logStack.push(ch);
				break;
			case ')':
				logOut = logOut.concat(gotParen(logStack) + '@');
				break;
			case '|':
			case '&':
				logStack.push(ch);
				break;
			default:
				logOut = logOut.concat(ch + '@');
			}

			if (i == str.length - 1)
				logOut = logOut.concat('@');
		}
		while (!logStack.isEmpty()) {
			logOut = logOut.concat(logStack.pop());
		}
		logOut = logOut.replace(/@@/g, '@');
		var strs = logOut.split('@');
		if(strs.length%2!=1){
			errorMsg=errorMsg.concat(curFormula+'表达式不正确!\n');
			return ;
		}
		var result = loopCalculate(strs)+'@';
	} else {
		result = str;
	}
	
	return result;
}

//计算
function doCalculate(left, right, op) {
	left = parseFloat(left);
	right = parseFloat(right);
	if(isNaN(left)||isNaN(right)){
		return ;
	}
    var r1,r2,m;
    if (op == '+') {
		//var r1,r2,m;
		try{r1=left.toString().split(".")[1].length}catch(e){r1=0} 
		try{r2=right.toString().split(".")[1].length}catch(e){r2=0} 
		m=Math.pow(10,Math.max(r1,r2)) 
		return (left*m+right*m)/m ;
	}
	if (op == '-') {
		//var r1,r2,m,n;
		var n;
		try{r1=left.toString().split(".")[1].length}catch(e){r1=0} 
		try{r2=right.toString().split(".")[1].length}catch(e){r2=0} 
		m=Math.pow(10,Math.max(r1,r2)); 
		//动态控制精度长度 
		n=(r1>=r2)?r1:r2; 
		return ((left*m-right*m)/m).toFixed(n); 
	}
	if (op == '*') {
        m=0;
		var s1=left.toString(),s2=right.toString();
		try{m+=s1.split(".")[1].length}catch(e){} ;
		try{m+=s2.split(".")[1].length}catch(e){} ;
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) ;
	}
	if (op == '/') {
		var t1=0,t2=0;
		try{t1=left.toString().split(".")[1].length}catch(e){} 
		try{t2=right.toString().split(".")[1].length}catch(e){} 
		r1=Number(left.toString().replace(".","")) ;
		r2=Number(right.toString().replace(".","")) ;
		return (r1/r2)*Math.pow(10,t2-t1); 
	}
	if (op == '>') {
		if (left > right) {
			return 1;
		}
		return 0;
	}
	if (op == '<') {
		if (left < right) {
			return 1;
		}
		return 0;
	}
	if (op == '>=') {
		if (left >= right) {
			return 1;
		}
		return 0;
	}
	if (op == '<=') {
		if (left <= right) {
			return 1;
		}
		return 0;
	}
	if (op == '!=') {
		if (left != right) {
			return 1;
		}
		return 0;

	}
	if (op == '==') {
		if (left == right) {
			return 1;
		}
		return 0;
	}
	if (op == '|') {
		return left | right;
	}
	if (op == '&') {
		return left & right;
	}
	if (op == '=') {
		if (left == right) {
			return 1;
		}
		return 0;
	}
}

//自定义栈,用来存放操作符
function Stack() {
	this.stack = new Array();
	this.push = function(value) {
		var length = this.stack.length;
		this.stack[length] = value;
	}, this.pop = function() {
		return this.stack.pop();
	}, this.peek = function() {
		return this.stack[this.stack.length - 1];
	}
	this.isEmpty = function() {
		var length = this.stack.length;
		if (length == 0) {
			return true;
		} else {
			return false;
		}
	}
}
//失去焦点验证必填项
function func_uior_val_empty(obj){
	var msg='';
	var value=obj.value;
	var mustInput=obj.getAttribute('mustinput');
	if(mustInput=='1'){
		if(value=='undefined'||func_uior_trim(value)==''){
			var name=obj.getAttribute('title');
			msg=msg.concat('不能为空！');
		}else{
		msg=msg.concat(func_uior_valStringType(obj));
		}
	}
	msg=msg.concat(func_uior_valLength(obj));
	return msg;
}


//提交表单验证必填项
function func_uior_valForm(frm){
	var msg='';
	for(var i=0;i<frm.length;i++){
		var obj=frm.elements[i];
		var value=obj.value;
		var mustInput=obj.getAttribute('mustinput');
		if(mustInput=='1'){
			if(value=='undefined'||func_uior_trim(value)==''){
				var name=obj.getAttribute('title');
				msg=msg.concat('提交的表单中名称为「'+name+'」的元素未赋值!\n<br/>');
				//$(obj.parentNode).prev("td").css("color","red");//将必填项为空的字段名标红
				func_uior_addTips(obj,"不能为空！");
				if(obj!=null && obj.tagName!=null && (obj.tagName=="textarea"||obj.tagName=="TEXTAREA")){
					$(obj).parent("div").parent("td").prev("td").css({"color":"red","font-weight":"bolder"});
				}else {
					$(obj.parentNode).prev("td").css({"color":"red","font-weight":"bolder"});
				}
			}else{
				if(obj!=null && obj.tagName!=null && (obj.tagName=="textarea"||obj.tagName=="TEXTAREA")){
					$(obj).parent("div").parent("td").prev("td").css({"color":"","font-weight":""});
				}else {
					$(obj.parentNode).prev("td").css({"color":"","font-weight":""});
				}
				$(obj).removeClass("Required");
				$(obj).parents("td").find(".Required-font").remove();
				msg=msg.concat(func_uior_valStringType(obj));
			}
		}
		msg=msg.concat(func_uior_valLength(obj));
		msg=msg.concat(func_uior_valueFormat(obj));
	}
	
	
	return msg;
}

function func_uior_isNum(obj){
	var str=obj.value;
	var msg='';
	var reuslt=isNaN(parseFloat(str));
	if(!result){
		msg=obj.getAttribute('title')+'中的内容不是数字\n';
		obj.value="0";
	}
	
	return msg;
}
function func_uior_isAmt(obj){
    var str=obj.value;
   // 金额类型 0.00;1.00;1,000.00;1,000;1
    var reg=/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d*)?$/;
    if(!reg.test(str)){
        if(str==""){
            obj.value="0.00";
        }else{
            window.alert(obj.getAttribute('title')+'中的内容不是金额类型!\n',1,function(){
                obj.value="0.00";
            });
        }
    }
}

function func_uior_valStringType(obj){
	var str=obj.value;
	var type=obj.getAttribute('datatype');
	var msg='';
    var reg;
	if(type!='undefined'&&func_uior_trim(type)!=''){	
		if(type==1){
			reg=/^-?[1-9]\d*|0$/;
			if(!reg.test(str)){
			msg=obj.getAttribute('title')+'中的内容不是整数!\n';
		obj.value="0";
			}	
		}else if(type==2){
			reg=/^-?[1-9]\d*|0$/;
			if(!reg.test(str)){
			msg=obj.getAttribute('title')+'中的内容不是长整数!\n';
		obj.value="0";
			}	
		}else if(type==3 ||type==8 || type==9 ||type==13||type==14||type==15||type==16||type==17||type==18||type==19){
			reg=/^-?([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;
			if(!reg.test(str)){
				msg=obj.getAttribute('title')+'中的内容不是数字!\n'
		obj.value="0";
			}
		}else if(type==4){
			reg=/^[1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/;
			if(!reg.test(str)){
				msg=obj.getAttribute('title')+'中的内容不是数字(正数)!\n';
		obj.value="0";
			}
		}else if(type==5){
			if(str!='true'&&str!='false'){
				msg=obj.getAttribute('title')+'中的内容不是布尔值!\n';
			}
		}else if(type==6){
			if(str.length==10 || str.length==8){
   			 	var yy=0,mm=0,dd=0;
        		if(str.length==8){
        			yy = parseInt(str.substring(0,4), 10);
	        		mm = parseInt(str.substring(4,6), 10);
	        		dd = parseInt(str.substring(6,8), 10);
        		}else {
        			yy = parseInt(str.substring(0,4), 10);
	        		mm = parseInt(str.substring(5,7), 10);
	        		dd = parseInt(str.substring(8,10), 10);
        		}
        		if(mm > 12 || mm <= 0 || dd <= 0 || dd > 31) 
        			return obj.getAttribute('title')+'中的内容不是日期!\n';
       		 	var rndd = ((yy%4==0)&&(yy%100!=0)||(yy%400 == 0))?29:28;
        		switch(mm){	
         			 case 4:
          			case 6:
          			case 9:
          			case 11:
            		if(dd > 30 || dd<=0)
              			msg=obj.getAttribute('title')+'中的内容不是日期!\n';
            			break;
          			case 1:
          			case 3:
          			case 5:
          			case 7:
          			case 8:
          			case 10:
          			case 12:
            		if(dd>31 || dd<=0)
                		msg=obj.getAttribute('title')+'中的内容不是日期!\n';
            		break;
          			case 2:
            			if(dd > rndd || dd <= 0)
                		msg=obj.getAttribute('title')+'中的内容不是日期!\n';
            		break;
       				}
				}else{
   					msg=obj.getAttribute('title')+'中的内容不是日期!\n';
   				}
		}else if(type==12){
//			金额类型 0.00;1.00;1,000.00;1,000;1
			reg=/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d*)?$/;
			if(!reg.test(str)){
				if(str==""){
					obj.value="0.00";
				}else{
					msg=obj.getAttribute('title')+'中的内容不是金额类型!\n';
					obj.value="0.00";
				}
			}
		}
	}	
	return msg;
}

function func_uior_valLength(obj){
	var type=obj.getAttribute('datatype');
	if((type!=0 && type!="0") || (obj.getAttribute("type")!="TEXT" && obj.getAttribute("type")!="text")){
		return '';
	}
	var str=obj.value;
	var msg='';
	var maxLengthStr=$(obj).attr('maxLength');
	if(maxLengthStr=="null" || maxLengthStr==null || maxLengthStr=='undefined' || !isNaN(maxLengthStr)){
		maxLengthStr = $(obj).attr('maxlength');
	}
	var reg=/^[1-9]*|0$/;
	if(maxLengthStr && maxLengthStr!='undefined' && reg.test(maxLengthStr)){
		var maxLength =parseInt(maxLengthStr, 10);
		//var reg=/[^\x00-\xff]/g;
		//str=str.replace(reg,'a');
		var count = window.top.SysConstant? window.top.SysConstant.Chinese_characters_count:1;
		var tempStr = "";
		for(var i = 0;i<count;i++){
			tempStr+="m";
		}
		str = str.replace(/[\u4e00-\u9fa5]/g,tempStr);//将中文字符进行转化
		str = str.replace(/[^\x00-\xff]/g,'qq');//将全角字符进行转化
		if(str.length>maxLength){
			msg=obj.getAttribute('title')+'中的内容长度不能超过'+maxLength+'个字符或'+parseInt((maxLength/count), 10)+'个汉字!\n';
		}
	}
	
	return msg;
}

//给必填项添加*号
//function func_uior_addMust(name){
//	var objs=document.getElementsByName(name);
//	var obj=objs[0];
//	var text=obj.innerHTML;
//	var add='<font color="red">*</font>';
//	obj.innerHTML=text+add;
//}

//去除首尾空格
function func_uior_trim(str){
	var result='';
	if(str!=null){
	result=str.replace(/^\s|\s$/g,'');
	}
	return result;
}

//用于时时验证数据类型
function func_uior_valTypeImm(obj){
	var str=obj.value;
	var type=obj.getAttribute('datatype');
	if ($(obj).hasClass("Required")) {
		$(obj).removeClass("Required");
	}
	if($(obj).parent().find(".Required-font").length>0){
		$(obj).parent().find(".Required-font").remove();
	}
	
	// 1.验证必填项
	var msg='';
	msg = func_uior_val_empty(obj);
	if(msg!=""){
		func_uior_addTips(obj,msg);
		return;
	}
	
	// 2.验证长度
	msg = func_uior_valLength(obj);
	if(msg!=""){
		func_uior_addTips(obj,msg);
		return;
	}
	
	// 3.验证数据类型
	msg = func_uior_valType(obj);
	if(msg!=""){
		func_uior_addTips(obj,msg);
		return;
	}
	
	// 4.验证数据格式
	msg = func_uior_valueFormat(obj);
	if(msg!=""){
		func_uior_addTips(obj,msg);
		return;
	}
	
	if(obj.value!=''&&obj.getAttribute('datatype')!='undifined')
	{
	//$(obj.parentNode).prev("td").css("color","black");
		if(obj!=null && obj.tagName!=null && (obj.tagName=="textarea"||obj.tagName=="TEXTAREA")){
			$(obj).parent("div").parent("td").prev("td").css({"color":"black","font-weight":"normal"});
		}else {
			$(obj.parentNode).prev("td").css({"color":"black","font-weight":"normal"});
		}
	}
}
//验证格式
function func_uior_valueFormat(obj){
	var msg='';
	var str=obj.value;
	var type=obj.getAttribute('alt');
	if(str!=undefined){
		if(type!='undefined'&&func_uior_trim(type)!=''){
			switch (type) {
				case "idnum":
					msg = CheckIdValue(obj);
				    break;
				case "organ":
					msg = CheckOrganFormat(obj);
				    break;
				case "licence":
					msg = checkBusLicence(obj);
					break;
				case "credit":
					msg = creditCheck(obj);
				    break;
				case "mobile":
					msg = isMobile(obj);
					break;
				case "telephone":
					msg = checkTelePhone(obj);
				    break;
				case "zipcode":
					msg = isZipcode(obj);
				    break;
				case "email":
					msg = checkEmail(obj);
				    break;
				case "qq":
					msg = isTrueQq(obj);
				    break;
				case "rate":
					msg = checkValRate(obj);
				    break;
				case "nonnegative":
					msg = num_validate(obj);
				    break;
				case "length":
					msg = checkTextareaLen(obj);
				    break;
			}
		}
	}
	return msg;
}

//验证数据类型
function func_uior_valType(obj){
	var msg='';
	var str=obj.value;
	var type=obj.getAttribute('datatype');
	if(str!=undefined){
		if(type!='undefined'&&func_uior_trim(type)!=''){
            var reg;
			if(type==1){
				if(str==""){
					obj.value="0";
				} else {
					//var reg=/^-?[1-9]\d*|0$/;
					reg=/^-?[0-9]*$/;//此写法允许首字符为0
					if(!reg.test(str)){
						msg=obj.getAttribute('title')+'中的内容不是整数!\n';
						obj.value="0";
					}	
				}
			}else if(type==2){
				if(str==""){
					obj.value="0";
				} else {
					reg=/^-?[1-9]\d*|0$/;
					if(!reg.test(str)){
					msg=obj.getAttribute('title')+'中的内容不是长整数!\n';
					obj.value="0";
					}	
				}
			}else if(type==3 ||type==8 || type==9 ||type==13||type==14||type==15||type==16||type==17||type==18||type==19){
				if(str==""){
					obj.value="0.00";
				} else {
					reg=/^-?([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;
					if(!reg.test(str)){
						msg=obj.getAttribute('title')+'中的内容不是数字!\n';
						obj.value="0";
					}
				}
			}else if(type==4){
				if(str==""){
					obj.value="0.00";
				} else {
					reg=/^[1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/;
					if(!reg.test(str)){
						msg=obj.getAttribute('title')+'中的内容不是数字(正数)!\n';
						obj.value="0";
					}
				}
			}else if(type==5){
				if(str==""){
					obj.value="";
				} else {
					if(str!='true'&&str!='false'){
						msg=obj.getAttribute('title')+'中的内容不是布尔值!\n';
					}
				}
			}else if(type==6){
//			if(str.length==10){
//	   			 	var yy = parseInt(str.substring(0,4));
//	        		var mm = parseInt(str.substring(5,7));
//	        		var dd = parseInt(str.substring(8,10));
//	        		if(mm > 12 || mm <= 0 || dd <= 0 || dd > 31) 
//	        			return obj.getAttribute('title')+'中的内容不是日期!\n';
//	       		 	var rndd = ((yy%4==0)&&(yy%100!=0)||(yy%400 == 0))?29:28;
//	        		switch(mm){	
//	         			 case 4:
//	          			case 6:
//	          			case 9:
//	          			case 11:
//	            		if(dd > 30 || dd<=0)
//	              			msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            			break;
//	          			case 1:
//	          			case 3:
//	          			case 5:
//	          			case 7:
//	          			case 8:
//	          			case 10:
//	          			case 12:
//	            		if(dd>31 || dd<=0)
//	                		msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            		break;
//	          			case 2:
//	            			if(dd > rndd || dd <= 0)
//	                		msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	            		break;
//	       				}
//					}else{
//	   				msg=obj.getAttribute('title')+'中的内容不是日期!\n'
//	   				}
			}else if(type==12){
				reg=/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d*)?$/;
				if(!reg.test(str)){
					if(str==""){
						obj.value="0.00";
					}else{
						msg = obj.getAttribute('title')+'中的内容不是金额类型!\n';
						obj.value="0.00";
					}
				} else {
					//为金额型输入框末尾添加.00
					var strPoints = str.split("\.");	//判断字符串包含几个小数点符号
					if (strPoints.length==2){
						if(strPoints[1].length==0){
								obj.value = strPoints[0] + ".00";
						}else if(strPoints[1].length==1){
								obj.value = str+"0";
						}else if(strPoints[1].length>2){
								obj.value = "0.00";
								msg = obj.getAttribute('title')+'金额只保留2位小数点!\n';
						}
					} else if(strPoints.length==1){
						obj.value = str+".00";
					} else {
						msg = obj.getAttribute('title')+'中的内容不是金额类型!\n';
						obj.value="0.00";
					}
					try{
						if(document.getElementsByName("bigAmt_"+obj.getAttribute('name'))[0]){
							document.all["bigAmt_"+obj.getAttribute('name')].value = bigAmt(obj.value);
						}
					}catch(e){}
				}
			}
			
		}
		
		}
	return msg;
}
//表单信息提示
function func_uior_addTips(obj,msg){
var $this =$(obj);
var val = $this.val();
if ($this.hasClass("Required")) {
	$this.removeClass("Required");
}
if($this.parent().find(".Required-font").length>0){
	$this.parent().find(".Required-font").remove();
}
//if(val==null||val==""||typeof(val)=="undefined"){
	var $label = $('<label class="Required-font"><i class="i i-jingbao"></i>'+msg+'</label>');
	$label.appendTo($this.parent());
	$this.addClass("Required");
//}
}

function bigAmt(n) { 
 n = (n+"").replace(/,/g, "");
 var unit = "千百拾亿千百拾万千百拾元角分", str = "";
 n += "00";
 var p = n.indexOf('.');
 if (p >= 0)
 n = n.substring(0, p) + n.substr(p+1, 2);
 unit = unit.substr(unit.length - n.length);
 for (var i=0; i < n.length; i++){
   str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
 }
 return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}
