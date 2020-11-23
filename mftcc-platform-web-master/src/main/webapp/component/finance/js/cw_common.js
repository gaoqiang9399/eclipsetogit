/**
 * 财务弹窗返回参数:
 * 科目弹窗：
 * 		.parentId （1001）
 * 		.id	（100101）
 * 		.name （库存现金）
 * 		.showName （100101/库存现金）
 * 
 * 摘要弹窗：
 * 		data
 */
top.cwBackData;
//弹窗选择科目<!-- 0:查询全部；1：查询一级科目；2：查询明细科目；3：查询挂辅助核算科目 -->
var openComItemDialog = function(dataType, callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/comItemTree.jsp?dataType=' + dataType), "选择科目",'450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};
//弹窗选择摘要
var openRemarksDialog = function(callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/CwVoucherRemarks_List.jsp'), "选择摘要",'450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};

//弹窗选择辅助核算
var openAssistDialog = function(txType, callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/assistDialog.jsp?txType=' + txType), "选择辅助核算",'450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};

//弹窗选择凭证模版
var openVchPlateDialog = function(callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/CwVchPlateMst_List.jsp'), "选择模版",'450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};

//弹窗选择报表项列表   返回值：  reportItemId  reportName 
var openReportItemDialog = function(reportTypeId,callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/reportItemDialog.jsp?reportTypeId=' + reportTypeId), '报表项','450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};
//弹窗选择报表项 现金流量表树型
var openCashItemTreeDialog = function(reportTypeId,callback){
	top.cwBackData='';
	top.createShowDialog(encodeURI(webPath + '/component/finance/voucher/reportItemTree.jsp?reportTypeId=' + reportTypeId), '报表项','450px','600px',function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};
//弹框
var openCreatShowDialog = function(url,title,high,width,callback){
	top.cwBackData='';
	if(!high){
		high = 70;
	}
	if(!width){
		width = 70;
	}
	top.createShowDialog(encodeURI(webPath + url), title,high,width,function(){
		if(callback&&typeof(callback)=="function"){
			callback.call(this, top.cwBackData);
		}
	});
};

var autoComData = [];
// type: 0:查询全部；1：查询一级科目；2：查询明细科目；3：查询挂辅助核算科目 -->
function autoComPleter(input, type , callback){
	if(autoComData.length == 0){
		$.getJSON(webPath+"/cwComItem/getComCacheListAjax", {'accType': type}, function(data){
			autoComData = data.items;
			for(var item in autoComData){
				autoComData[item].label = autoComData[item].accNo + '/' + autoComData[item].accName;
			}
			prodAutoMenu(input,autoComData,callback,null,'',false);
		});
	}else{
		prodAutoMenu(input,autoComData,callback,null,'',false);
	}
}




/**
 * 验证查询条件
 * @param opnWeek
 * @param endWeek
 */
function checkQueryWeeks(opnWeek, endWeek){
	if(opnWeek == ''){
		alert(top.getMessage("NOT_FORM_EMPTY", "开始周期"), 1);
		return false;
	}else if(endWeek == ''){
		alert(top.getMessage("NOT_FORM_EMPTY", "结束周期"), 1);
		return false;
	}else{
		if(opnWeek.substring(0, 4) != endWeek.substring(0, 4)){
			//alert('不允许跨年查询！', 1);
			alert(top.getMessage("NO_CW_REPORT_SELECT", ""), 1);
			return false;
		}
		if(Number(opnWeek) > Number(endWeek)){
			alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"开始周期" , "timeTwo": "结束周期"}), 1);
//			alert('开始周期不能大于结束周期！', 1);
			return false;
		}
	}
	return true;
}

/**
 * 是否是输入金额键盘
 * @param keyCode 键盘输入值
 */
function isAmtKeyCode(keyCode){
	//大键盘数字
	if(keyCode > 47 && keyCode < 58){
		return true;
	}
	//数字键盘数字
	if(keyCode > 95 && keyCode < 106){
		return true;
	}
	//大小键盘 . -
	if(keyCode == 189 || keyCode == 190 || keyCode == 109 || keyCode == 110){
		return true;
	}
	return false;
}


//获取url传入的参数
function getQueryStringByName(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}


function Map() {     
    /** 存放键的数组(遍历用到) */    
    this.keys = new Array();     
    /** 存放数据 */    
    this.data = new Object();     
         
    /**   
     * 放入一个键值对   
     * @param {String} key   
     * @param {Object} value   
     */    
    this.put = function(key, value) {     
        if(this.data[key] == null){     
            this.keys.push(key);     
        }     
        this.data[key] = value;     
    };     
         
    /**   
     * 获取某键对应的值   
     * @param {String} key   
     * @return {Object} value   
     */    
    this.get = function(key) {     
        return this.data[key];     
    };     
         
    /**   
     * 删除一个键值对   
     * @param {String} key   
     */    
    this.remove = function(key) {     
        this.keys.remove(key);     
        this.data[key] = null;     
    };     
         
    /**   
     * 遍历Map,执行处理函数   
     *    
     * @param {Function} 回调函数 function(key,value,index){..}   
     */    
    this.each = function(fn){     
        if(typeof fn != 'function'){     
            return;     
        }     
        var len = this.keys.length;     
        for(var i=0;i<len;i++){     
            var k = this.keys[i];     
            fn(k,this.data[k],i);     
        }     
    };     
         
    /**   
     * 获取键值数组(类似<a href="http://lib.csdn.net/base/javase" class='replace_word' title="Java SE知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java</a>的entrySet())   
     * @return 键值对象{key,value}的数组   
     */    
    this.entrys = function() {     
        var len = this.keys.length;     
        var entrys = new Array(len);     
        for (var i = 0; i < len; i++) {     
            entrys[i] = {     
                key : this.keys[i],     
                value : this.data[i]     
            };     
        }     
        return entrys;     
    };     
         
    /**   
     * 判断Map是否为空   
     */    
    this.isEmpty = function() {     
        return this.keys.length == 0;     
    };     
         
    /**   
     * 获取键值对数量   
     */    
    this.size = function(){     
        return this.keys.length;     
    };     
         
    /**   
     * 重写toString    
     */    
    this.toString = function(){     
        var s = "{";     
        for(var i=0;i<this.keys.length;i++,s+=','){     
            var k = this.keys[i];     
            s += k+"="+this.data[k];     
        }     
        s+="}";     
        return s;     
    };     
} 

//金额小写转大写
function chineseNumber(dValue) {
	var maxDec = 2;
	// 验证输入金额数值或数值字符串：
	dValue = dValue.toString().replace(/,/g, "");
	dValue = dValue.replace(/^0+/, ""); // 金额数值转字符、移除逗号、移除前导零
	if(dValue == "") {
		return "零元整";
	} // （错误：金额为空！）
	else if(isNaN(dValue)) {
		return "错误：金额不是合法的数值！";
	}
	var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
	var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
	if(dValue.length > 1) {
		if(dValue.indexOf('-') == 0) {
			dValue = dValue.replace("-", "");
			minus = "负";
		} // 处理负数符号“-”
		if(dValue.indexOf('+') == 0) {
			dValue = dValue.replace("+", "");
		} // 处理前导正数符号“+”（无实际意义）
	}
	var vInt = "";
	var vDec = ""; // 字符串：金额的整数部分、小数部分
	var resAIW; // 字符串：要输出的结果
	var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
	var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
	var zeroCount; // 零计数
	var i, p, d; // 循环因子；前一位数字；当前位数字。
	var quotient, modulus; // 整数部分计算用：商数、模数。
	// 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
	var NoneDecLen = (typeof(maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
	parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
	if(parts.length > 1) {
		vInt = parts[0];
		vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分
		if(NoneDecLen) {
			maxDec = vDec.length > 5 ? 5 : vDec.length;
		} // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
		var rDec = Number("0." + vDec);
		rDec *= Math.pow(10, maxDec);
		rDec = Math.round(Math.abs(rDec));
		rDec /= Math.pow(10, maxDec); // 小数四舍五入
		var aIntDec = rDec.toString().split('.');
		if(Number(aIntDec[0]) == 1) {
			vInt = (Number(vInt) + 1).toString();
		} // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
		if(aIntDec.length > 1) {
			vDec = aIntDec[1];
		} else {
			vDec = "";
		}
	} else {
		vInt = dValue;
		vDec = "";
		if(NoneDecLen) {
			maxDec = 0;
		}
	}
	if(vInt.length > 44) {
		return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！";
	}
	// 准备各字符数组 Prepare the characters corresponding to the digits:
	digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
	radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
	bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
	decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
	resAIW = ""; // 开始处理
	// 处理整数部分（如果有）
	if(Number(vInt) > 0) {
		zeroCount = 0;
		for(i = 0; i < vInt.length; i++) {
			p = vInt.length - i - 1;
			d = vInt.substr(i, 1);
			quotient = p / 4;
			modulus = p % 4;
			if(d == "0") {
				zeroCount++;
			} else {
				if(zeroCount > 0) {
					resAIW += digits[0];
				}
				zeroCount = 0;
				resAIW += digits[Number(d)] + radices[modulus];
			}
			if(modulus == 0 && zeroCount < 4) {
				resAIW += bigRadices[quotient];
			}
		}
		resAIW += "元";
	}
	// 处理小数部分（如果有）
	for(i = 0; i < vDec.length; i++) {
		d = vDec.substr(i, 1);
		if(d != "0") {
			resAIW += digits[Number(d)] + decimals[i];
		}
	}
	// 处理结果
	if(resAIW == "") {
		resAIW = "零" + "元";
	} // 零元
	if(vDec == "") {
		resAIW += "整";
	} // 元整
	resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负元角分/整
	return resAIW;
}
/**
 * 金额保留两位
 */
function changeTwoDecimal(x) {
	var f_x = parseFloat(x);
	if (isNaN(f_x)) {
		alert('function:changeTwoDecimal->parameter error');
		return false;
	}
	f_x = Math.round(f_x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
	return s_x;
}