/**
 * TODO
 * Created by LJW on 2017年3月17日.
 */

var creditHandleUtil = function() {};

creditHandleUtil.prototype = {};

/**
 * 将数字格式化为两位小数
 */
creditHandleUtil.numFormat = function(argStr){
	return argStr.toFixed(2);
};

/**
 * 将YYYYMMDD字符串转化为Date类型 
 */
creditHandleUtil.strFormatDate = function(dateStr){ 
	var pattern = /(\d{4})(\d{2})(\d{2})/;
	var formateDate = dateStr.replace(pattern,'$1-$2-$3');
	return new Date(formateDate);
};

/**
 * 将Date转化为YYYY-MM-DD字符串 
 */
creditHandleUtil.convertDateToYYYYMMDD = function(date){
	var curr_y = date.getFullYear();
	var curr_d = date.getDate();
	var curr_m = date.getMonth()+1;
	curr_d = (String(curr_d).length < 2) ? ("0" + curr_d) : curr_d;
	curr_m = (String(curr_m).length < 2) ? ("0" + curr_m) : curr_m;
	return curr_y + "-" + curr_m + "-" + curr_d;
};

/**
 * 将YYYYMMDD字符串转化为YYYY-MM-DD字符串 
 */
creditHandleUtil.convertToYYYYMMDD = function(dateStr){
	var pattern = /(\d{4})(\d{2})(\d{2})/;
	var formateDate = dateStr.replace(pattern,'$1-$2-$3');
	return formateDate;
};
/*
 *   功能:实现VBScript的DateAdd功能.
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   dateAdd( "d", 5, now);
 */
creditHandleUtil.dateAdd = function(interval, number, date) {
    switch (interval) {
	    case "y": {
	        date.setFullYear(date.getFullYear() + number);
	        return date;
	        break;
	    }
	    case "q": {
	        date.setMonth(date.getMonth() + number * 3);
	        return date;
	        break;
	    }
	    case "m": {
	        date.setMonth(date.getMonth() + number);
	        return date;
	        break;
	    }
	    case "w": {
	        date.setDate(date.getDate() + number * 7);
	        return date;
	        break;
	    }
	    case "d": {
	        date.setDate(date.getDate() + number);
	        return date;
	        break;
	    }
	    case "h": {
	        date.setHours(date.getHours() + number);
	        return date;
	        break;
	    }
	    case "mm": {
	        date.setMinutes(date.getMinutes() + number);
	        return date;
	        break;
	    }
	    case "s": {
	        date.setSeconds(date.getSeconds() + number);
	        return date;
	        break;
	    }
	    default: {
	        date.setDate(date.getDate() + number);
	        return date;
	        break;
	    }
    }
}


/**
 * 获取日期+月数 后的结果
 * @param num
 */
creditHandleUtil.getAddMonthRes = function(dateArg,num,argType){
	var tmpDate = creditHandleUtil.dateAdd(argType,num,creditHandleUtil.strFormatDate(dateArg)); 
	return creditHandleUtil.convertDateToYYYYMMDD(tmpDate);
};

//当选择产品时判断是否重复选择了产品
creditHandleUtil.checkKindNo = function(obj){
	var selectVal = obj.value;
	var kindNos = new Array();
	var kindNames = new Array();
	kindNos.push($("select[name=kindNo]").val());
	kindNames.push($("select[name=kindNo]").text());
	if(index != 0){
		for(var i = 1;i<=index;i++){
			var kindNoVal = $("select[name=kindNo_"+i+"]").val();
			var kindNoText = $("select[name=kindNo_"+i+"]").text();
			if(kindNoVal != ""){
				kindNos.push(kindNoVal);
				kindNames.push(kindNoText);
			}
		}
	}
	kindNos.splice(kindNos.indexOf(selectVal), 1);  //删除数组中某一项
	if($.inArray(selectVal, kindNos)>=0){
		alert(top.getMessage("FIRST_SELECT_FIELD","产品，因为你选择的产品种类重复"), 2);
	}
};
//金额格式化
creditHandleUtil.moneyFormatting = function(inputId){
	toMoney(document.getElementById(inputId));
	$("#"+inputId).focus();
	$("#"+inputId).blur();
};