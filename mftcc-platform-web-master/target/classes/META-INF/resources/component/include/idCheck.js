window.idNoCheck = function (idType,idNo){
	if(idType.value==0&&idType.value!=""){
		if(idNo.value!=""){
			if(!CheckIdValue(idNo)){
				idNo.value="";
			} 
		}
	} else if(idType.value=="A"&&idType.value!=""){
		if(idNo.value!=""){
			if(!CheckOrganFormat(idNo.value)){
				idNo.value="";
			}
		}
	} else if(idType.value=="B"&&idType.value!=""){
		if(idNo.value!=""){
			if(!creditCheck(idNo.value)){
				idNo.value="";
			}
		}
	}
};

/**
 * 文本域长度验证
 * @param text
 * @returns {Boolean}
 */
function checkTextareaLen(obj){
	var value = trim(obj.value);
	var title = obj.title;
	var valLen = value.length;
	if(valLen>parseInt(len)){
		return title + "长度不能超过"+len+"位！";
	}
	return '';
}
/**
 * 一笔或多笔发票号码验证，-分隔。
 * @returns {Boolean}
 */
function checkBillMulti(obj){
	var value = trim(obj.value);
	var title = obj.title;
	var valLen = value.length;

	var re = /^\d+[-]?\d+$/;
	if(!re.test(value)){
		msg = "发票号码格式不正确";
		return msg;
	}
	
	return '';
}

//身份证
function CheckIdValue(idNo){
	var idCard = trim(idNo.value);
	var tiltle = idNo.title;
	var msg='';
	var sex = "1";
    /*
     * 检查身份证号函数
     * 返回值为true是身份证号符合规则 为false身份证号不符合规则
     */	
    var id=idCard;
    var id_length=id.length;

    var re;

//    if (id_length==0){
//        alert("请输入身份证号码!"+tiltle,0);
//        return false;
//    }
    if (id_length!=15 && id_length!=18){
        //alert(tiltle+"长度应为15位或18位！",0);
        msg = "身份证号码长度应为15位或18位！";
        return msg;
    }

    if (id_length==15){
		re = /^\d{15}$/;
		if(!re.test(id)){
			msg = "15位身份证号必须为数字";
			return msg;
		}
        yyyy="19"+id.substring(6,8);
        mm=id.substring(8,10);
        dd=id.substring(10,12);

        if (mm>12 || mm<=0){
            //myAlert("输入身份证号,月份非法！");
            msg="输入身份证号,月份非法！";
            return msg;
        }

        if (dd>31 || dd<=0){
            //myAlert("输入身份证号,日期非法！");
            msg="输入身份证号,日期非法！";
            return msg;
        }

        birthday=yyyy+ "-" +mm+ "-" +dd;

        if ("13579".indexOf(id.substring(14,15))!=-1){
            sex="1";
        }else{
            sex="2";
        }
        var id17 = id.substring(0,6)+"19"+id.substring(6);
        id = id17 + GetIdVerifyBit(id17);
        idNo.value=id;
    }else if (id_length==18){
		re = /^\d{17}[\dXx]$/;
		if(!re.test(id)){
			//myAlert("身份证前17位必须为数字,第18位为数字或X");
			msg="身份证前17位必须为数字,第18位为数字或X";
	        return msg;
		}

        yyyy=id.substring(6,10);
        
        if(yyyy>new Date().getFullYear() || yyyy<1800){
            //myAlert("输入身份证号,年度非法！");
            msg = "输入身份证号,年度非法！";
            return msg;
        }

        mm=id.substring(10,12);
        if (mm>12 || mm<=0){
            //myAlert("输入身份证号,月份非法！");
        	msg="输入身份证号,月份非法！";
            return msg;
        }

        dd=id.substring(12,14);
        if (dd>31 || dd<=0){
            //myAlert("输入身份证号,日期非法！");
        	msg="输入身份证号,日期非法！";
            return msg;
        }

        if (id.charAt(17)=="x" || id.charAt(17)=="X")
        {
            if ("x"!=GetIdVerifyBit(id) && "X"!=GetIdVerifyBit(id)){
               //myAlert("身份证校验错误，请检查最后一位！");
                msg="身份证校验错误，请检查输入是否正确！";
                return msg;
            }

        }else{
            if (id.charAt(17)!=GetIdVerifyBit(id)){
                //myAlert("身份证校验错误，请检查最后一位！");
            	msg="身份证校验错误，请检查输入是否正确！";
                return msg;
            }
        }

        birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
        if ("13579".indexOf(id.substring(16,17)) > -1){
            sex="1";
        }else{
            sex="2";
        }
    }

    return msg;
}
//验证固定电话
function checkTelePhone(obj) {
	var title = obj.title;
	var telno = trim(obj.value);
	var msg = "";
	if (typeof(telno) == 'undefined' || telno == null || telno == "") {
		return msg;
	}
	var patrntel = /^0\d{2,3}-\d{4,8}(-\d{1,6})?$/;
	if (!patrntel.test(telno)) {
		msg="请输入正确的电话号码\n例如:\"区号-1234567\"";
		return msg;
	}
	return msg;
}

//营业执照
function checkBusLicence(obj) {
	var title = obj.title;
	var busLicence = trim(obj.value);
	var msg = "";
	if (typeof(busLicence) == 'undefined' || busLicence == null || busLicence == "") {
		return msg;
	}else if(busLicence.length>0&&busLicence.length<13){
        msg="营业执照长度应为13位";
        return msg;
	}
	var regExp = /^([^\u4e00-\u9fa5]+)$/;
	if(!regExp.test(busLicence)){
		msg="请输入正确的营业执照号！";
		return msg;
	}
	return msg;
}
//通过身份证号计算校验位的数值
function GetIdVerifyBit(id){
    var result;
    var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
    nNum=nNum%11;
    switch (nNum) {
       case 0 :
          result="1";
          break;
       case 1 :
          result="0";
          break;
       case 2 :
          result="X";
          break;
       case 3 :
          result="9";
          break;
       case 4 :
          result="8";
          break;
       case 5 :
          result="7";
          break;
       case 6 :
          result="6";
          break;
       case 7 :
          result="5";
          break;
       case 8 :
          result="4";
          break;
       case 9 :
          result="3";
          break;
       case 10 :
          result="2";
          break;
    }
    //document.write(result);
    return result;
}

	/*
     * 检查组织机构号函数
     * 返回值为true是组织机构号符合规则 为false组织机构号不符合规则
     */
function CheckOrganFormat(code)
	{
	var val = trim(code.value);
	var title = code.title;
	var msg = '';
		//旧的组织机构代码证规则
		if(val.length!=10){
			//myAlert("组织机构代码必须为9位！");
			//alert("组织机构代码必须为9位！",0);
			msg = "组织机构代码必须为10位！";
			return msg;
		}
		var old_style = /^X\d{10}$/;
		if(old_style.test(val)){
			//myAlert("请输入正确的组织机构代码");
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
		}
		//现有的组织机构代码证规则
		var w_i = new Array();
		var c_i = new Array();
		var s = 0;
		var financecode =new Array();
		
		financecode[0]=val.charCodeAt(0);
		financecode[1]=val.charCodeAt(1);
		financecode[2]=val.charCodeAt(2);
		financecode[3]=val.charCodeAt(3);
		financecode[4]=val.charCodeAt(4);
		financecode[5]=val.charCodeAt(5);
		financecode[6]=val.charCodeAt(6);
		financecode[7]=val.charCodeAt(7);
		financecode[8]=val.charCodeAt(8);
		financecode[9]=val.charCodeAt(9);
		financecode[10]=val.charCodeAt(10);
		

		if (val=="00000000-0"){
			//myAlert("请输入正确的组织机构代码");
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
		}
		w_i[0] = 3;
		w_i[1] = 7;
		w_i[2] = 9;
		w_i[3] = 10;
		w_i[4] = 5;
		w_i[5] = 8;
		w_i[6] = 4;
		w_i[7] = 2;
		if (financecode[8] != 45){
			//myAlert("请输入正确的组织机构代码");	
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
			}
		var c;
		for (var i = 0; i < 10; i++)
		{
			c = financecode[i];
			if (c <= 122 && c >= 97)
			{
				//myAlert("请输入正确的组织机构代码");
				//alert("请输入正确的组织机构代码",0);
				msg = "请输入正确的组织机构代码!";
				return msg;
			}
		}

		fir_value = financecode[0];
		sec_value = financecode[1];
		if (fir_value >= 65 && fir_value <= 90)
			c_i[0] = (fir_value + 32) - 87;
		else
		if (fir_value >= 48 && fir_value <= 57)
			c_i[0] = fir_value - 48;
		else
		{
			//myAlert("请输入正确的组织机构代码");
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
		}
		s += w_i[0] * c_i[0];
		if (sec_value >= 65 && sec_value <= 90)
			c_i[1] = (sec_value - 65) + 10;
		else
		if (sec_value >= 48 && sec_value <= 57)
			c_i[1] = sec_value - 48;
		else
		{
			//myAlert("请输入正确的组织机构代码");
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
		}
		s += w_i[1] * c_i[1];
		for (var j = 2; j < 8; j++)
		{
			if (financecode[j] < 48 || financecode[j] > 57){
				//myAlert("请输入正确的组织机构代码");
				//alert("请输入正确的组织机构代码",0);
				msg = "请输入正确的组织机构代码!";
				return msg;
				}
				
			c_i[j] = financecode[j] - 48;
			s += w_i[j] * c_i[j];
		}

		c = 11 - s % 11;
		if(financecode[9] == 88 && c == 10 || c == 11 && financecode[9] == 48 || c == financecode[9] - 48)
		{
			return msg;
		}else{
			//myAlert("请输入正确的组织机构代码");	
			//alert("请输入正确的组织机构代码",0);
			msg = "请输入正确的组织机构代码!";
			return msg;
		}
	}
//社会信用代码
function creditCheck(obj){
	var idNo = trim(obj.value);
	var title = obj.title;
	var msg = '';
	idNo = idNo.toUpperCase();
	if(idNo.length!=18){
		//myAlert("社会信用代码必须为18位！");
		//alert("社会信用代码必须为18位！",0);
		msg = "社会信用代码必须为18位！";
		return msg;
	}
	
	var sum = 0;
	var arr=new Array();
	arr=idNo.split("");
	//myAlert(arr[0]);
	for(var i=0;i<arr.length-1;i++){
		var c=arr[i];
		if(typeof(c)=="NaN"||typeof(c)=="undefined"){
			//myAlert("请输入正确的社会信用代码！");
			//alert("请输入正确的社会信用代码！",0);
			msg = "请输入正确的社会信用代码！";
			return msg;
		}
		var a=getMapC(c);
		if(typeof(a)=="NaN"||typeof(a)=="undefined"){
			//myAlert("请输入正确的社会信用代码！");
			msg = "请输入正确的社会信用代码！";
			return msg;
		}
		var b=getMapW((i+1).toString());
		if(typeof(b)=="NaN"||typeof(b)=="undefined"){
			//myAlert("请输入正确的社会信用代码！");
			msg = "请输入正确的社会信用代码！";
			return msg;
		}
		sum += a * b;
		if(typeof(sum)=="NaN"||typeof(sum)=="undefined"){
			//myAlert("请输入正确的社会信用代码！");
			msg = "请输入正确的社会信用代码！";
			return msg;
		}
	}
    var res;
	var mod=sum%31;
	if(mod == 0)
	{
		res = "0";
	}
	else {
		res = getMapR((31-mod).toString());
	}
	if(typeof(res)=="NaN"||typeof(res)=="undefined"){
			//myAlert("请输入正确的社会信用代码！");
		msg = "请输入正确的社会信用代码！";
		return msg;
	}
	if(arr[arr.length-1]!=(res.toString())){
		//myAlert("请输入正确的社会信用代码！");
		msg = "请输入正确的社会信用代码！";
		return msg;
	}
	return msg;
//	else{
//		myAlert("正确的三证合一！");
//	}
//	myAlert(idNo);
}
//纳税人识别号
function taxpayerCheck(obj){
    var idNo = trim(obj.value);
    var title = obj.title;
    var msg = '';
    idNo = idNo.toUpperCase();
    if(idNo.length!=18){
        //myAlert("纳税人识别号必须为18位！");
        //alert("纳税人识别号必须为18位！",0);
        msg = "纳税人识别号必须为18位！";
        return msg;
    }

    var sum = 0;
    var arr=new Array();
    arr=idNo.split("");
    //myAlert(arr[0]);
    for(var i=0;i<arr.length-1;i++){
        var c=arr[i];
        if(typeof(c)=="NaN"||typeof(c)=="undefined"){
            //myAlert("请输入正确的纳税人识别号！");
            //alert("请输入正确的纳税人识别号！",0);
            msg = "请输入正确的纳税人识别号！";
            return msg;
        }
        var a=getMapC(c);
        if(typeof(a)=="NaN"||typeof(a)=="undefined"){
            //myAlert("请输入正确的纳税人识别号！");
            msg = "请输入正确的纳税人识别号！";
            return msg;
        }
        var b=getMapW((i+1).toString());
        if(typeof(b)=="NaN"||typeof(b)=="undefined"){
            //myAlert("请输入正确的纳税人识别号！");
            msg = "请输入正确的纳税人识别号！";
            return msg;
        }
        sum += a * b;
        if(typeof(sum)=="NaN"||typeof(sum)=="undefined"){
            //myAlert("请输入正确的纳税人识别号！");
            msg = "请输入正确的纳税人识别号！";
            return msg;
        }
    }
    var res;
    var mod=sum%31;
    if(mod == 0)
    {
        res = "0";
    }
    else {
        res = getMapR((31-mod).toString());
    }
    if(typeof(res)=="NaN"||typeof(res)=="undefined"){
        //myAlert("请输入正确的纳税人识别号！");
        msg = "请输入正确的纳税人识别号！";
        return msg;
    }
    if(arr[arr.length-1]!=(res.toString())){
        //myAlert("请输入正确的纳税人识别号！");
        msg = "请输入正确的纳税人识别号！";
        return msg;
    }
    return msg;
}
// 正确的手机
function isMobile(obj) {
	var msg = '';
	// 排除空格
	//var financecode = val.replace(/(^\s*)|(\s*$)/g, "");
	var str =trim(obj.value);
	var title = obj.title;
	var len = str.length;
	if (len != 11) {
		//alert(title+"必须是11位！",0);
		msg = title+"必须是11位！";
		return msg;
	}
	var regu = /^((13)|(14)|(15)|(16)|(17)|(18)|(19))[0-9]\d{8}$/;
	if (!regu.test(str)) {
		//alert(title+"格式不正确！",0);
		msg = title+"格式不正确！";
		return msg;
	}
	return msg;
}
//正确的qq
function isTrueQq(obj) {
	var msg = '';
	var str =trim(obj.value);
	var title = obj.title;
	var regu = /^\s*[.0-9]{5,11}\s*$/;
	if (!regu.test(str)) {
		//alert(title+"格式不正确！",0);
		msg = title+"必须是5位到11位的数字！";
		return msg;
	}
	return msg;
}
//密码验证
function checkPwd(obj) {
	var msg = '';
	var str =trim(obj.value);
	var title = obj.title;
	if(str.length<6){
		msg = title+"长度不小于6位！";
		return msg;
	}
	var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? ／＼］\\.；：%……+ ￥（）【】‘”“'．。，、？１２３４５６７８９０－＝＿＋～？！＠＃＄％＾＆＊）]");      
	 if(pattern.test(str)){  
		 msg = title+"包含特殊字符！";
			return msg; 
    } 
	return msg;
}
//金额数字不能小于零
function num_validate(obj) {
	var number = trim(obj.value);
	var title = obj.title;
	var msg = '';
	if(number.length>0){
		if(number.substring(0,1)=="-"){
			msg = "输入的"+title+"不能小于零！";
			return msg;
		}
	}
	if (parseFloat(number) < 0) {
		msg = "输入的"+title+"不能小于零！";
		return msg;
	}
	return msg;
}
/**
 * 验证邮政编号
 * @param obj 验证内容
 * @returns {Boolean}
 */
function isZipcode(obj){
	var str = obj.value;
	var title = obj.title;
	var msg = '';
	var len = str.length;
	var zipcode = /^\d{6}$/;
	if (!zipcode.test(str)) {
		msg = title+"格式必须为六位数字！";
		return msg;
	}
	return msg;
}

/**
 * 校验数据是否为合法的email地址
 * @param umail 要校验的数据
 */
function checkEmail(obj) {
	var title = obj.title;
    var umail = trim(obj.value);
    var msg = '';
    if (typeof(umail) == 'undefined' || umail == null || umail == "") {
        return msg;
    }

    var re = /^[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+@[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+(\.[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+)+$/;
    if (!re.test(umail)) {
        msg = title+"格式错误！";
        return msg;
    }
    return msg;
}
/**
 * 校验比率
 */
function checkValRate(obj) {
	var title = obj.title;
    var rate = trim(obj.value);
    var ratetmp = rate;
    var len = rate.length;
    var msg = '';
    if (typeof(rate) == 'undefined' || rate == null || rate == "") {
        return msg;
    }
    if (!isNaN(parseFloat(rate))) {
        rate = parseFloat(rate);
        if(len>0){
    		if(ratetmp.substring(0,1)=="-"){
    			msg = title+"不能小于零！";
    			return msg;
    		}
    	}
        if (rate < 0) {
            msg = title+"不能小于0";
            return msg;
        }
        if (rate > 100) {
            msg = title+"不能大于100%";
            return msg;
        }
    }
    return msg;
}
/**
 * 校验抵质押率（大于0，小于等于100）
 */
function checkValRateRange(obj) {
	var title = obj.title;
    var rate = trim(obj.value);
    var ratetmp = rate;
    var len = rate.length;
    var msg = '';
    if (typeof(rate) == 'undefined' || rate == null || rate == "") {
        return msg;
    }
    if (!isNaN(parseFloat(rate))) {
        rate = parseFloat(rate);
        if(len>0){
    		if(ratetmp.substring(0,1)=="-"){
    			msg = title+"不能小于等于0！";
    			return msg;
    		}
    	}
        if (rate <= 0) {
            msg = title+"不能小于等于0！";
            return msg;
        }
        if (rate > 100) {
            msg = title+"不能大于100%！";
            return msg;
        }
    }
    return msg;
}

/**
 * 验证军官证
 * officers
 */
function officersNo(obj){
	var title = obj.title;
	var brNo = trim(obj.value);//取消空格
    var msg = '';
    if (typeof(brNo) == 'undefined' || brNo == null || brNo == "") {
        return msg;
    }
    var re = /^[0-9]{8}$/; //正则表达式：数字型；八位
    if(!re.test(brNo)){
    	msg = title+"格式错误！请输入8位军官证号码";
        return msg;
    }
    return msg;
}

/**
 * 验证客户登陆名称
 */
function checkOpNo(obj){
	var title = obj.title;
    var brNo = trim(obj.value);
    var msg = '';
    if (typeof(brNo) == 'undefined' || brNo == null || brNo == "") {
        return msg;
    }
    var re = /^[a-zA-Z0-9_]+$/;
    if (!re.test(brNo)) {
        msg = title+"格式错误！";
        return msg;
    }
    return msg;
}
function getMapC(pa){
	var result;
	switch (pa) {
	   case "0" :
          result=0;
          break;
       case "1" :
          result=1;
          break;
       case "2" :
          result=2;
          break;
       case "3" :
          result=3;
          break;
       case "4" :
          result=4;
          break;
       case "5" :
          result=5;
          break;
       case "6" :
          result=6;
          break;
       case "7" :
          result=7;
          break;
       case "8" :
          result=8;
          break;
       case "9" :
          result=9;
          break;
       case "A" :
          result=10;
          break;
       case "B" :
          result=11;
          break;
       case "C" :
          result=12;
          break;
       case "D" :
          result=13;
          break;
       case "E" :
          result=14;
          break;
       case "F" :
          result=15;
          break;
       case "G" :
          result=16;
          break;
       case "H" :
          result=17;
          break;
       case "J" :
          result=18;
          break;
       case "K" :
          result=19;
          break;
       case "L" :
          result=20;
          break;
       case "M" :
          result=21;
          break;
       case "N" :
          result=22;
          break;
       case "P" :
          result=23;
          break;
       case "Q" :
          result=24;
          break;
       case "R" :
          result=25;
          break;
       case "T" :
          result=26;
          break;
       case "U" :
          result=27;
          break;
       case "W" :
          result=28;
          break;
       case "X" :
          result=29;
          break;
       case "Y" :
          result=30;
          break;
	}
	return result;
}
function getMapW(pa){
	var result;
	switch (pa) {
	   case "1" :
          result=1;
          break;
       case "2" :
          result=3;
          break;
       case "3" :
          result=9;
          break;
       case "4" :
          result=27;
          break;
       case "5" :
          result=19;
          break;
       case "6" :
          result=26;
          break;
       case "7" :
          result=16;
          break;
       case "8" :
          result=17;
          break;
       case "9" :
          result=20;
          break;
       case "10" :
          result=29;
          break;
       case "11" :
          result=25;
          break;
       case "12" :
          result=13;
          break;
       case "13" :
          result=8;
          break;
       case "14" :
          result=24;
          break;
       case "15" :
          result=10;
          break;
       case "16" :
          result=30;
          break;
       case "17" :
          result=28;
          break;
	}
	return result;
}
function getMapR(pa){
	var result;
	switch (pa) {
	   case "0" :
          result="0", "31";
          break;
       case "1" :
          result="1";
          break;
       case "2" :
          result="2";
          break;
       case "3" :
          result="3";
          break;
       case "4" :
          result="4";
          break;
       case "5" :
          result="5";
          break;
       case "6" :
          result="6";
          break;
       case "7" :
          result="7";
          break;
       case "8" :
          result="8";
          break;
       case "9" :
          result="9";
          break;
       case "10" :
          result="A";
          break;
       case "11" :
          result="B";
          break;
       case "12" :
          result="C";
          break;
       case "13" :
          result="D";
          break;
       case "14" :
          result="E";
          break;
       case "15" :
          result="F";
          break;
       case "16" :
          result="G";
          break;
       case "17" :
          result="H";
          break;
       case "18" :
          result="J";
          break;
       case "19" :
          result="K";
          break;
       case "20" :
          result="L";
          break;
       case "21" :
          result="M";
          break;
       case "22" :
          result="N";
          break;
       case "23" :
          result="P";
          break;
       case "24" :
          result="Q";
          break;
       case "25" :
          result="R";
          break;
       case "26" :
          result="T";
          break;
       case "27" :
          result="U";
          break;
       case "28" :
          result="W";
          break;
       case "29" :
          result="X";
          break;
       case "30" :
          result="Y";
          break;
	}
	return result;
}
/**去掉空格**/
function trim(str) {
	if (typeof str !== "string") {
		return str;
	}
	if (!str) {
		return "";
	}
    return str.replace(/\s+/g, "");
}

//校验利率数字不能小于等于零
function rate_validate(obj) {
	var number = trim(obj.value);
	var title = obj.title;
	var msg = '';
	if(number.length>0){
		if(number.substring(0,1)=="-"){
			msg = "输入的"+title+"不能小于零！";
			return msg;
		}
	}
	if (parseFloat(number) < 0) {
		msg = "输入的"+title+"不能小于零！";
		return msg;
	}
	if (parseFloat(number) == 0) {
		msg = "输入的"+title+"不能等于零！";
		return msg;
	}
	return msg;
}

//全部证件类型
function checkAllIdType(obj,idType){
	var $this = $(obj);
	var $idType = $this.parents("form").find("[name='"+idType+"']");

	if($idType == undefined || $idType == null || $idType == ""){
		return "未找到证件类型！";
	}
	//alert($idType.html());
	var idTypeLableName = $idType.prop("tagName");
	//alert(idTypeLableName)
	var idTypeVal = "";
	if(idTypeLableName == 'INPUT'){
		idTypeVal = $idType.val();
	}else if(idTypeLableName == 'SELECT'){
		idTypeVal = $idType.find("option:selected").val();
	}else{
		return "证件类型字段标签不支持！"
	}
	if(idTypeVal == "0"){
		return CheckIdValue(obj);
	}else if(idTypeVal == "A"){
		return CheckOrganFormat(obj);
	}else if(idTypeVal == "B"){
		return creditCheck(obj);
	}else if(idTypeVal == "C"){
		return checkBusLicence(obj);
	}else{
		return ""
	}
}

//车架号
function checkCarFrameNo(carFarm){
	var carFrameNo = trim(carFarm.value);
	var msg='';
    var carFrameNoLength=carFrameNo.length;
    if (carFrameNoLength!=17){
        msg = "车架号长度应为17位！";
        return msg;
    }
    return "";
}