var stack = new Stack(); //用于把中缀式转换成后缀式
var errorMsg = ''; //记录错误信息
var curFormula; //记录当前解析的公式
/*
 * 表单实时验证及联动计算公式
 */
function onCalculation(formulas) {
	if (formulas != null && formulas != '') {
		var allResult = 0;
		var funcs = formulas.split(';');
		for ( var i = 0; i < funcs.length; i++) {
			var formula = funcs[i];
			if (formula != null && formula != undefined && formula != '') {
				curFormula = formula;
				var result = func_xcqi_cal(formula);
				//返回值是否为true
				if (!result) {
					allResult = 1;
					//break;
					errorMsg = errorMsg + '\n' + curFormula;
				}
			}
		}
	}
}


//主方法入口
function func_xcqi_cal(formula) {
	formula = xcqi_parseFormula(formula); //对公式进行格式化
	formula = xcqi_transFormula(formula); //对公式进行转换
	var result = xcqi_logic(formula); //对公式含有逻辑运算符的情况下,进行逻辑运算
	if (result == '') {
		return '';
	}
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
function xcqi_parseFormula(formula) {

	formula = formula.replace(/{/g, '('); //将所有的{}替换成()
	formula = formula.replace(/}/g, ')');
	formula = formula.replace(/\[/g, '('); //将所有的[]替换成()
	formula = formula.replace(/\]/g, ')');
	formula = formula.replace(/\&\&/g, '&'); //将所有的&&替换成&
	formula = formula.replace(/\|\|/g, '|'); //将所有的||替换成|

	return formula;
}

//对公式进行转换
function xcqi_transFormula(formula) {
	//将公式根据|和&分成数组
	var strs = formula.split(/\||\&/);
	for ( var i = 0; i < strs.length; i++) {
		var str = strs[i];
		str = xcqi_parseParen(str); //解决分成数组后括号不匹配的问题
		//将公式根据>= <= != == > <分成数组,>=要在>之前,否则会把1>=2分成 1,=2
		var eles = str.split(/=/);
		if (eles[0] == undefined || eles[1] == undefined) {
			alert('公式' + formula + '配置不正确!',0);
		}
		var eleLeft = eles[1];
		var resultLeft = xcqi_infixTrans(eleLeft);
		if (typeof resultLeft!="number" && resultLeft == '') {
			return '';
		}
		if (resultLeft != undefined) {
			var obj = document.all(eles[0]);
			var type = obj.getAttribute('datatype');
			var scale = obj.getAttribute('dataScale');
            var scaleLength,formatStr,sc;
			//对金额型进行处理
			if (type == 12) {
				//10000.000
                var value,parValue;
				if (/\./g.test(resultLeft)) {
					resultLeft = resultLeft + '';
					var values = resultLeft.split('.');
					value = values[0];
					parValue = '#.' + values[1];
				} else {
					value = resultLeft;
					parValue = '#';
				}
				value = value + '';
				if (value.length > 3) {
					for ( var j = value.length - 1; j >= 0; j--) {
						if ((value.length - j) % 3 == 0) {
							//							sAlert(value.substring(j, j+3));
							parValue = parValue.replace('#', '#,' + value
									.substring(j, j + 3));
						}
					}
					if (parValue.substring(0, 2) == '#,') {
						parValue = parValue.substring(2, parValue.length);
					}
					if (value.length % 3 != 0) {
						parValue = value.substring(0, value.length % 3) + ','
								+ parValue;
					}
				} else {
					parValue = value + parValue;
					parValue = parValue.replace(/#/g, '');
				}
				resultLeft = format(resultLeft,'##.00');
				try{
					if(document.getElementsByName("bigAmt_"+obj.getAttribute('name'))[0]){
						document.all["bigAmt_"+obj.getAttribute('name')].value = bigAmt(resultLeft);
					}
				}catch(e){}
			} else if(type==3){
				resultLeft = format(resultLeft,'##.00');
			} else if(type==8){
				resultLeft = format(resultLeft,'##.0000');
			} else if(type==9){
				resultLeft = format(resultLeft,'##.000000');
			} else if(type==17){
				scaleLength = 2;
				if(typeof(scale)!="undefined" && scale!=null){
					if(/[0-9]{1,}/g.test(scale)){
						scaleLength = parseInt(scale,10);
					}
				}
				formatStr = "##.";
				if(scaleLength<=0){
					scaleLength = 2;
				}
				for(sc=0;sc<scaleLength;sc++){
					formatStr += "0";
				}
				resultLeft = format(resultLeft,formatStr);
			} else if(type==18){
				scaleLength = 3;
				if(typeof(scale)!="undefined" && scale!=null){
					if(/[0-9]{1,}/g.test(scale)){
						scaleLength = parseInt(scale,10);
					}
				}
				formatStr = "##.";
				if(scaleLength<=0){
					scaleLength = 3;
				}
				for(sc=0;sc<scaleLength;sc++){
					formatStr += "0";
				}
				resultLeft = format(resultLeft,formatStr);
			} else if(type==19){
				resultLeft = format(resultLeft,'##.00');
			} else if(type==1||type==2||type==4){
				resultLeft = format(resultLeft,'##');
			} else if(type==12||type==13||type==14||type==15||type==16){
				resultLeft = format(resultLeft,'##.00');
			} 

			obj.value = resultLeft;
		}
	}
	return resultLeft;
}

//解决括号不匹配的问题
function xcqi_parseParen(str) {
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
function xcqi_infixTrans(formula) {
	var first = formula.charAt(0);
	var reg = /\d*\(*\+*-*/;	//首字母是数字,(,+,-
	if (!reg.test(first)) {
		errorMsg=errorMsg.concat(curFormula+'公式解析错误!\n');
	}
	var outPut = xcqi_doTrans(formula);	//对公式进行转换
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
		strs = xcqi_getValue(strs);			//从页面获取值
		if(strs){
		var result = xcqi_loopCalculate(strs);//循环计算
		}else {
			return '';
		}
		return result;
	}
}

//从页面获取值
function xcqi_getValue(strs) {
	for ( var i = 0; i < strs.length; i++) {
		var ch = strs[i];
		if (ch != '+' && ch != '-' && ch != '*' && ch != '/' && !/\d/.test(ch)
				&& ch != '@'&&ch!='') {
			var text =0;
			ch=func_xcqi_trim(ch);
			if(ch.charAt(0)!='$'){
				var obj = document.all(ch);
				text = obj.value;
				if(text==undefined||text==null||text==''){
					return false;
				}
				var type=obj.getAttribute('datatype');
				if(type==12){
					text=text.replace(/,/g,'');
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
function xcqi_doTrans(formula) {
	var str = "";
	for ( var i = 0; i < formula.length; i++) {
		var ch = formula.charAt(i);
		switch (ch) {
		case '+':
		case '-':
			str = str.concat('@');
			str = str.concat(xcqi_gotOper(ch, 1));
			break;
		case '*':
		case '/':
			str = str.concat('@');
			str = str.concat(xcqi_gotOper(ch, 2));
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
function xcqi_gotOper(str, prec) {
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
function xcqi_gotParen(sta) {
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
function xcqi_loopCalculate(strs) {
	var length = strs.length;
	while (true) {
		while (length >= 2) {
			for ( var i = 0; i < strs.length; i++) {
				if (strs[i] == '+' || strs[i] == '-' || strs[i] == '*'
						|| strs[i] == '/' || strs[i] == '|' || strs[i] == '&') {
					strs[i - 2] = xcqi_doCalculate(strs[i - 2], strs[i - 1], strs[i]
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
function xcqi_logic(str) {
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
		var result = xcqi_loopCalculate(strs)+'@';
	} else {
		result = str;
	}
	
	return result;
}

//计算
function xcqi_doCalculate(left, right, op) {
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

//去除首尾空格
function func_xcqi_trim(str){
	var result='';
	if(str!=null){
	result=str.replace(/^\s|\s$/g,'');
	}
	return result;
}


//格式化数字  pattern 为 0000.00 或##.00
function format(number,pattern){
  var str            = number.toString();
  var strInt;
  var strFloat;
  var formatInt;
  var formatFloat;
  var zero;
  if(/\./g.test(pattern)){
      formatInt        = pattern.split('.')[0];
      formatFloat        = pattern.split('.')[1];
  }else{
      formatInt        = pattern;
      formatFloat        = null;
  }
  if(/\./g.test(str)){
      if(formatFloat!=null){
          var tempFloat    = Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
          strInt        = (Math.floor(number)+Math.floor(tempFloat)).toString();                
          strFloat    = /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';            
      }else{
          strInt        = Math.round(number).toString();
          strFloat    = '0';
      }
  }else{
      strInt        = str;
      strFloat    = '0';
  }
  if(formatInt!=null){
      var outputInt = '';
      var comma = null;
      zero = formatInt.match(/0*$/)[0].length;
      if(/,/g.test(formatInt)){
          comma        = formatInt.match(/,[^,]*/)[0].length-1;
      }
      var newReg        = new RegExp('(\\d{'+comma+'})','g');

      if(strInt.length<zero){
          outputInt        = new Array(zero+1).join('0')+strInt;
          outputInt        = outputInt.substr(outputInt.length-zero,zero)
      }else{
          outputInt        = strInt;
      }

      //var
      outputInt = outputInt.substr(0, outputInt.length % comma) + outputInt.substring(outputInt.length % comma).replace(newReg, (comma != null ? ',' : '') + '$1')
      outputInt = outputInt.replace(/^,/, '');

      strInt    = outputInt;
  }

  if(formatFloat!=null){
      var outputFloat    = '';
      zero = formatFloat.match(/^0*/)[0].length;

      if(strFloat.length<zero){
          outputFloat        = strFloat+new Array(zero+1).join('0');
          //outputFloat        = outputFloat.substring(0,formatFloat.length);
          var outputFloat1    = outputFloat.substring(0,zero);
          var outputFloat2    = outputFloat.substring(zero,formatFloat.length);
          outputFloat        = outputFloat1+outputFloat2.replace(/0*$/,'');
      }else{
          outputFloat        = strFloat.substring(0,formatFloat.length);
      }

      strFloat    = outputFloat;
  }else{
      if(pattern!='' || (pattern=='' && strFloat=='0')){
          strFloat    = '';
      }
  }

  return strInt+(strFloat==''?'':'.'+strFloat);
}

