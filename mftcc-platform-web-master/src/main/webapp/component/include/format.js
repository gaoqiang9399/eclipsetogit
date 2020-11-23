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
	        var outputInt    = '';
	        zero = formatInt.match(/0*$/)[0].length;
	        var comma        = null;
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
			outputInt = outputInt.substr(0, outputInt.length % comma) + outputInt.substring(outputInt.length % comma).replace(newReg, (comma != null ? ',' : '') + '$1');
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
	
/**
 * 将数字型转化为金额千分位型
 * @param intInput
 * @param intLength
 * @returns
 */
function commafy(intInput, intLength) {
	var strInput;
	if(intInput==null || intInput==""){
		return intInput;
	}else if(intInput=="NaN"){
		return "0.00";
	} else {
		//将输入参数转换为字符串形式
		strInput = Math.abs(intInput).toString();
		strInput = strInput.replace(/,/g,'');
		var reg = /^[0-9]+\.{0,1}[0-9]*$/;
		if(!reg.test(strInput)){
			return intInput;
		}
	}
	
	//如果有小数，把小数部分提取出来
	var strXS = "";
	if (strInput.indexOf(".", 0) != -1) {
		strXS = strInput.substring(strInput.split(".")[0].length,
				strInput.length);
		if (strXS.length < intLength) {
			for ( var m = strXS.length; m < intLength; m++) {
				strXS = strXS + "0";
			}
		} else {
            var inte,strIn;
			if(intLength==0){
				strIn = strXS.substr(1,1);
				if(strIn>=5){
					inte = parseFloat(strInput.split(".")[0]) + 1;
					strInput = inte + strXS;
				}
			}else{
				inte = strXS.substr(1,intLength);
				strIn = strXS.substr(intLength+1,1);
				if(strIn>=5){
					var end = inte.substr(inte.length-1,1);
					if(end==9){
						inte = parseFloat(inte.substr(0,inte.length-1)) + 1;
						if(inte==10){
							strInput = parseFloat(strInput)+1;
							inte=0;
						}
					}else{
						end = parseFloat(end) + 1;
						inte = inte.substr(0,inte.length-1) + end;
					}
					
					strXS = "." + inte;
				}else{
					strXS = "." + inte;
				}
			}
		}
		strInput = strInput.toString().split(".")[0];
	} else {
		for ( var n = 0; n < intLength; n++) {
			if (strXS == "") {
				strXS = ".0";
			} else {
				strXS = strXS + "0";
			}
		}
	}
	//获取输入参数的长度
	var iLen = strInput.length;
	//如果输入参数的长度小于等于3，则直接返回
	//否则，再进行处理
	if (iLen <= 3) {
		//处理负数的情况
		if (intInput < 0) {
			return  "-" +strInput + strXS;
		}else{
		return strInput + strXS;
		}
	} else {
		//首先取模，以作为起始点，每3位截取一次存入数组，最后再进行拼接返回
		var iMod = iLen % 3;
		//每3位截取的起始点  
		var iStart = iMod;
		//每3位截取的存储数组
		var aryReturn = [];

		//循环处理：每3位截取一次 存储到数组
		while (iStart + 3 <= iLen) {
			aryReturn[aryReturn.length] = strInput
					.substring(iStart, iStart + 3);
			iStart = iStart + 3;
		}
		//将数组中的数据连接起来
		aryReturn = aryReturn.join(",");

		//处理输入参数长度不是3的倍数的情况
		if (iMod != 0) {
			aryReturn = strInput.substring(0, iMod) + "," + aryReturn;
		}
		//处理负数的情况
		if (intInput < 0) {
			aryReturn = "-" + aryReturn;
		}
		if (intLength > 0) {
			return aryReturn + strXS;
		} else {
			return aryReturn + ".00";
		}
	}
}