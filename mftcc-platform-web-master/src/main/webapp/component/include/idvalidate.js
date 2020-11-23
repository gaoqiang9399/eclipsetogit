/**去掉空格**/
function trim(str) {
    return str.replace(/\s+/g, "");
}

/**通过elename得到element**/
function getEleByName(elename) {
    return document.getElementsByName(elename)[0];
}

/**通过element得到alt值**/
function getAlt(ele) {
    return ele.getAttribute("alt");
}
/**检测贷款卡编码**/
function checkCreditNo(ele) {
    var alt = '贷款卡编码';
    if (ele == 'undefined' || ele == null) {
        alert(alt + "不存在",0);
        ele.value = "";
        return false;
    }
    var financecode = trim(ele.value);
    if (financecode == "") {
        if (ele.getAttribute('emptyok') == 'false') {
            alert(alt + "不能为空",0);
            ele.focus();
            ele.value = "";
            return false;
        } else {
            return true;
        }
    }
    if (financecode.match(/[A-Z0-9]{16}/) == null) {
        alert(alt + "错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }

    var w_i = new Array(14);
    var c_i = new Array(14);
    var j, s = 0;
    var checkid = 0;
    var c, i;

    w_i[0] = 1;
    w_i[1] = 3;
    w_i[2] = 5;
    w_i[3] = 7;
    w_i[4] = 11;
    w_i[5] = 2;
    w_i[6] = 13;
    w_i[7] = 1;
    w_i[8] = 1;
    w_i[9] = 17;
    w_i[10] = 19;
    w_i[11] = 97;
    w_i[12] = 23;
    w_i[13] = 29;

    for (j = 0; j < 14; j++) {
        if (financecode.charAt(j) >= '0' && financecode.charAt(j) <= '9') {
            c_i[j] = financecode.charCodeAt(j) - '0'.charCodeAt(0);
        }
        else if (financecode.charAt(j) >= 'A' && financecode.charAt(j) <= 'Z') {
            c_i[j] = financecode.charCodeAt(j) - 'A'.charCodeAt(0) + 10;
        }
        else {
            alert(alt + "编码错误!",0);
            ele.focus();
            ele.value = "";
            return false;
        }
        s = s + w_i[j] * c_i[j];
    }

    c = 1 + (s % 97);

    checkid = (financecode.charCodeAt(14) - '0'.charCodeAt(0)) * 10 + financecode.charCodeAt(15) - '0'.charCodeAt(0);
    if (c != checkid) {
        alert(alt + "错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }
    return true;
}

//校验身份证号码：
function CheckIDNo(ele) {
    var alt = '身份证号码';
    var num = trim(ele.value);
    if (typeof(num) == 'undefined' || num == null || num == "") {
        return true;
    }
    var re;
    var len = num.length;

    if (len == 15) {
        if (isNaN(num)) {
            alert(alt + "不是数字！",0);
            ele.focus();
            ele.value = "";
            return false;
        }
        re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
    } else if (len == 18) {
        if (isNaN(num.substring(0, 17))) {
            alert("输入的身份证位数前17位不是数字！",0);
            return false;
        }
        re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\S)$/);
    } else {
        alert(alt + "位数不对！",0);
        ele.value = "";
        ele.focus();
        return false;
    }
    var a = num.match(re);
    if (a != null) {
        var D,B;
        if (len == 15) {
            D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
            B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
        }
        else {
            D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
            B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
        }
        if (!B) {
            alert(alt + a[0] + " 里出生日期不对！",0);
            ele.focus();
            ele.value = "";
            return false;
        }
    }
    return true;
}

//新添加的校验身份证号码，如果输入格式错误，不清空已填入的数据，提示错误，定位焦点并返回false（建议在提交表单前先调用此方法进行验证）：
function checkIdcard(ele) {
	var idcard = trim(ele.value);
	if (typeof(idcard) == 'undefined' || idcard == null || idcard == "") {
        return true;
    }
    var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!");
    var area = {
        11 : "北京", 12 : "天津", 13 : "河北", 14 : "山西", 15 : "内蒙古", 21 : "辽宁", 22 : "吉林", 23 : "黑龙江",
        31 : "上海", 32 : "江苏", 33 : "浙江", 34 : "安徽", 35 : "福建", 36 : "江西", 37 : "山东", 41 : "河南",
        42 : "湖北", 43 : "湖南", 44 : "广东", 45 : "广西", 46 : "海南", 50 : "重庆", 51 : "四川", 52 : "贵州",
        53 : "云南", 54 : "西藏", 61 : "陕西", 62 : "甘肃", 63 : "青海", 64 : "宁夏", 65 : "新疆", 71 : "台湾",
        81 : "香港", 82 : "澳门", 91 : "国外"
    }
    var retflag = false;
    var Y, JYM, S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) {
    	alert(Errors[4],0);
    	ele.focus();
        return false;
    }
    //身份号码位数及格式检验
    switch (idcard.length) {
    case 15:
        if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
        } else {
            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
        }
        if (ereg.test(idcard)) {
        	return true;
        } else {
            alert (Errors[2],0);
            ele.focus();
            return false;
        }
        break;
    case 18:
        //18位身份号码检测
        //出生日期的合法性检查 
        //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9] | 30) | 02(0[1 - 9] | [1 - 2][0 - 9]))
        //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9] | 30) | 02(0[1 - 9] | 1[0 - 9] | 2[0 - 8]))
        if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
        } else {
            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
        }
        if (ereg.test(idcard)) { //测试出生日期的合法性
            //计算校验位
            S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
            Y = S % 11;
            M = "F";
            JYM = "10X98765432";
            M = JYM.substr(Y, 1); //判断校验位
            if (M == idcard_array[17]) { //检测ID的校验位
            	return true;
            } else {
            	alert (Errors[3],0);
            	ele.focus();
                return false;
            }
        } else {
        	alert (Errors[2],0);
        	ele.focus();
            return false;
        }
        break;
    default:
        alert (Errors[1],0);
    	ele.focus();
    	return false;
        break;
    }
}

/**测试组织机构代码**/
function checkORG(ele) {
    var alt = '组织机构代码';
    if (ele == 'undefined' || ele == null) {
        alert("组织机构代码不存在",0);
        ele.focus();
        ele.value = "";
        return false;
    }
    var financecode = trim(ele.value);
    if (financecode == "") {
        if (ele.getAttribute('emptyok') == 'false') {
            alert("组织机构代码不能为空",0);
            ele.focus();
            ele.value = "";
            return false;
        } else {
            return true;
        }
    }
    var fir_value, sec_value;
    var w_i = new Array(8);
    var c_i = new Array(8);
    var j, s = 0;
    var c, i;

    if (financecode == "00000000-0") {
        alert("组织机构代码错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }

    w_i[0] = 3;
    w_i[1] = 7;
    w_i[2] = 9;
    w_i[3] = 10;
    w_i[4] = 5;
    w_i[5] = 8;
    w_i[6] = 4;
    w_i[7] = 2;

    if (financecode.charAt(8) != '-') {
        alert("组织机构代码错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }

    for (i = 0; i < 10; i++) {
        c = financecode.charAt(i);
        if (c <= 'z' && c >= 'a') {
            alert("组织机构代码错误!",0);
            ele.focus();
            ele.value = "";
            return false;
        }
    }

    fir_value = financecode.charCodeAt(0);
    sec_value = financecode.charCodeAt(1);

    if (fir_value >= 'A'.charCodeAt(0) && fir_value <= 'Z'.charCodeAt(0)) {
        c_i[0] = fir_value + 32 - 87;
    } else {
        if (fir_value >= '0'.charCodeAt(0) && fir_value <= '9'.charCodeAt(0)) {
            c_i[0] = fir_value - '0'.charCodeAt(0);
        } else {
            alert("组织机构代码错误!",0);
            ele.focus();
            ele.value = "";
            return false;
        }
    }

    s = s + w_i[0] * c_i[0];

    if (sec_value >= 'A'.charCodeAt(0) && sec_value <= 'Z'.charCodeAt(0)) {
        c_i[1] = sec_value + 32 - 87;
    } else if (sec_value >= '0'.charCodeAt(0) && sec_value <= '9'.charCodeAt(0)) {
        c_i[1] = sec_value - '0'.charCodeAt(0);
    } else {
        alert("组织机构代码错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }

    s = s + w_i[1] * c_i[1];
    for (j = 2; j < 8; j++) {
        if (financecode.charAt(j) < '0' || financecode.charAt(j) > '9') {
            alert("组织机构代码错误!",0);
            ele.focus();
            ele.value = "";
            return false;
        }
        c_i[j] = financecode.charCodeAt(j) - '0'.charCodeAt(0);
        s = s + w_i[j] * c_i[j];
    }

    c = 11 - (s % 11);
    if (! ((financecode.charAt(9) == 'X' && c == 10) || (c == 11 && financecode.charAt(9) == '0') || (c == financecode.charCodeAt(9) - '0'.charCodeAt(0)))) {
        alert("组织机构代码错误!",0);
        ele.focus();
        ele.value = "";
        return false;
    }
    return true;
}

/**
 * 校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
 * @param telno 要检验的数据
 */
function checkTel(ele) {
    return checkTelAndMobile(ele);
}

function checkTelOnly(ele) {
	telno = trim(ele.value);
	if (typeof(telno) == 'undefined' || telno == null || telno == "") {
		return true;
	}
	var patrntel = /^0?\d{2,3}\-\d{7,8}$/;
	if (!patrntel.test(telno)) {
		alert("请输入正确的电话号码\n例如:\"区号-1234567\"",0);
		ele.focus();
		ele.value = "";
		return false;
	}
	ele.value = telno;
	return true;
}

/**
 * 校验手机号码
 * @param telno 要检验的数据
 */
function checkMobile(ele) {
    return checkTelAndMobile(ele);
}

/**
 * 校验手机号码
 * @param telno 要检验的数据
 */
function checkMobileOnly(ele) {
	telno = trim(ele.value);
	if (typeof(telno) == 'undefined' || telno == null || telno == "") {
		return true;
	}
	var patrnmobile = /^\d{11}$/;
	if (!patrnmobile.test(telno)) {
		alert("请输入正确的手机号码\n例如:\"139********\"",0);
		ele.focus();
		ele.value = "";
		return false;
	}
	ele.value = telno;
	return true;
}

/**
 * 校验手机号码 或者 座机号码/传真号码
 * @param telno 要检验的数据
 */
function checkTelAndMobile(ele) {
	telno = trim(ele.value);
	if (typeof(telno) == 'undefined' || telno == null || telno == "") {
		return true;
	}
	var patrntel = /^0?\d{2,3}\-\d{7,8}$/;
	var patrnmobile = /^\d{11}$/;
	if (!patrntel.test(telno) && !patrnmobile.test(telno)) {
		alert("请输入正确的手机号码或电话号码\n例如:\"139********\"或\"区号-1234567\"",0);
		ele.focus();
		ele.value = "";
		return false;
	}
	ele.value = telno;
	return true;
}


/**
 * 校验数据是否为合法的email地址
 * @param umail 要校验的数据
 */
function checkemail(ele) {
    var umail = trim(ele.value);
    var alt = 'email地址';
    if (typeof(umail) == 'undefined' || umail == null || umail == "") {
        return true;
    }

    var re = /^[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+@[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+(\.[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+)+$/;
    var msgLang = 1;
    if (!re.test(umail)) {
        alert(alt + "格式错误！",0);
        ele.focus();
        ele.value = "";
        return false;
    }
    ele.value = umail;
    return true;
}

/**
 * 校验邮政编码
 * @param zip 要检验的数据
 */
function checkPostalCode(ele) {
    var alt = '邮政编码';
    if (ele == 'undefined' || ele == null) {
        alert(alt + "不存在",0);
        ele.value = "";
        return false;
    }
    eleVal = trim(ele.value);
    if (eleVal == "") {
        if (ele.getAttribute('emptyok') == 'false') {
            alert(alt + "不能为空",0);
            ele.focus();
            ele.value = "";
            return false;
        } else {
            return true;
        }
    }
    var patrn = /^[0-9]{6}$/;
    if (!patrn.test(eleVal)) {
        alert(alt + "格式错误！",0);
        ele.focus();
        ele.value = "";
        return false;
    }
    ele.value = eleVal;
    return true;
}

/**
 * 校验比率
 */
function checkRate(ele) {
    var rate = trim(ele.value);
    var alt = '比率';
    if (typeof(rate) == 'undefined' || rate == null || rate == "") {
        return true;
    }
    if (!isNaN(parseFloat(rate))) {
        rate = parseFloat(rate);
        if (rate > 100) {
            alert(alt + "不能大于100%",0);
        }
    } else {
        ele.focus();
        ele.value = "";
    }
}

/**
 * 把ele的值设为空
 */
function cleanInput(ele) {
	if (ele == 'undefined' || ele == null) {
		return;
	}
	ele.value = "";
}
