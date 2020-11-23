;
var DingUtil = function(window,$){
	/**
	 * 格式化金额，默认保留2位小数
	 */
	var _fmoney = function (s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	};
	/**
	 * 将yyyyMMdd日期格式化为yyyy-MM-dd日期 并校验时间是否真实
	 */
	var _fdate = function(d){
		var m=d.replace(/(^\s+|\s+$)/g,'');//去两边空格; 
		if(m == ""){
			return "";
		}
		m = d.substring(0,4)+"-"+d.substring(4,6)+"-"+d.substring(6,8);
		//如果格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D就替换为''
		/*var s=m.replace(/[\d]{4}[\-]{1}[\d]{1,2}[\-]{1}[\d]{1,2}/g,''); 
		if(s == ""){
			 var t=new Date(m.replace(/\-/g,'/')); 
			    var ar=m.split(/[-\:]/); 
			    if(ar[0]!=t.getFullYear()||ar[1]!=t.getMonth()+1||ar[2]!=t.getDate()){//alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。'); 
			      return ""; 
			    } 
		}else{
			return "";
		}*/
		return m;
	};
	/**
	 * 控制输入框只能输入数字并且小数点只出现一次
	 */
	var _inputOnlyNumber=function(obj){ 
        //先把非数字的都替换掉，除了数字和. 
        obj.value = obj.value.replace(/[^\d\.]/g,''); 
        //必须保证第一个为数字而不是. 
        obj.value = obj.value.replace(/^\./g,'0.'); 
        //保证只有出现一个.而没有多个. 
        obj.value = obj.value.replace(/\.{2,}/g,'.'); 
        //保证.只出现一次，而不能出现两次以上 
        obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
         //只能输入两个小数
//        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); 
    } ;
	/**
	 * 根据身份证号自动填充表单中性别和生日
	 * @param obj:身份证号所对应DOM对象;
	 * @param sex:表单中性别字段的name,可以为null;
	 * @param birthday:表单中生日字段name,可以为null或者不传
	 * @author LiuAo
	 * @例子 StringUtil.setBirthyAndSexByID("input[name='idNo']", null, 'birthday');
	 * @例子 StringUtil.setBirthyAndSexByID(this, 'sex', 'birthday');		//在身份证号的输入框的事件内调用，this指向DOM对象。
	 */
	_setBirthyAndSexByID =function (obj,sex,birthday){
		var val = $(obj).val();
		if(18==val.length){
			if(sex){
				var sexValue;
				if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16))
					sexValue = '0';
				else
					sexValue = '1';
				$(obj).parents("form").find("[name='"+sex+"']").val(sexValue);
			}
			if(birthday){
				var birthdayValue = val.substring(6,10)+'-'+val.substring(10,12)+'-'+val.substring(12,14);
				$(obj).parents("form").find("[name='"+birthday+"']").val(birthdayValue);
			}
		};
	};
	/**
	 * 校验身份证，手机号码为一性，参照checkUniqueVal.js
	 * 参数：
	 * unVal:唯一值
	 * unValtitle：对应页面中字段名
	 * relationId：唯一值关联编号 客户号或是列表中流水号
	 * tabName：唯一值录入对应表单（暂时系统没有去判断指定表，验证所有系统下的值）
	 * unValType：唯一值类型 （01证件号20手机号）
	 * saveType：保单保存类型 新增保存/编辑保存
	 * cusNoExclude：客户号（如果传入值则表示排除当前客户下存在的该值）
	 * 代码实例
	 * 	var relationId = "";
	 * if(saveType == "update"){
	 *		var cusNo = $("input[name=cusNo]").val();
	 *		relationId = cusNo;
	 *	}	
	 *	var checkFlag = "",columnTitle = "";
	 *	var unValCheckResult = null;
	 *	var cusType = $("[name=cusType]").val();
	 *	//社会信用代码 证件号码唯一性验证
	 *	var unVal = $("[name=idNum]").val();
	 *	if(cusType.indexOf("2") != 0){  //企业客户
	 *		columnTitle = "社会信用代码";
	 *	} else {
	 *		columnTitle = $("select[name=idType]").find("option:selected").text();
	 *  }
	 *	unValCheckResult = checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","01",saveType,"");
	 *	checkFlag = unValCheckResult.split("&")[0];//idNumResult.split("&")[0];
	 *	if(checkFlag == "1"){//有重复，获取重复信息
	 * 		unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];	
	 *	}
	 */
	var _checkUniqueVal = function(unVal,unValtitle,relationId,tabName,unValType,saveType,cusNoExclude){
		var result = "";
		var	relationIdTmp = "";
		var flag = "1";//验证通过与否标识 0通过 1不通过
		if(saveType == "update"){
			relationIdTmp = relationId;
		}else if(saveType == "insert"){
			relationIdTmp = "";
		}
		$.ajax({
			url:webPath+"/dingUniqueVal/doCheckUniqueAjax",
			data:{unVal:unVal,unType:unValType,tabName:tabName,relationId:relationIdTmp,saveType:saveType,cusNoExclude:cusNoExclude},
			type:'post',
			dataType:'json',
			async:false,
			success:function(data){
				if(data.flag == "1"){
//					result=top.getMessage("ERROR_INFO_REPEAT", {"unValtitle":unValtitle , "unVal": unVal,"msg":data.msg})
					result = "您登记的 "+unValtitle+unVal+" 与 以下信息重复：<br>"+data.msg;
				}else{
					flag = "0";
				}
			},error:function(){
				$.alert("未请求到服务器，请刷新当前页面重试");
//				 window.top.alert(top.getMessage("ERROR_REQUEST_URL"),0);
			}
		});
		return flag+"&"+result;
	};
	/**
	 * obj当前银行卡号input
	 */
	_getBankByCardNumber = function(obj){
		var identifyNumber = obj.value.trim().replace(/\s/g,"");
		var $this = $(obj);
		if(!identifyNumber || identifyNumber == ""){
			return ;
		}
		$.ajax({
			url:webPath+"/dingCusBusiness/getBankInfoByIdAjax",
			data:{identifyNumber:identifyNumber},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
//					BASE.removePlaceholder($("input[name=bankNumbei]"));
//					BASE.removePlaceholder($("input[name=bank]"));
					$this.parents("form").find("input[name=bankNumbei]").val(data.bankId);
					$this.parents("form").find("input[name=bank]").val(data.bankName);
				}else{
					$this.parents("form").find("input[name=bankNumbei]").val("");
					$this.parents("form").find("input[name=bank]").val("");
				}	
			},error:function(){
				$.alert("未请求到服务器，请刷新当前页面重试");
//				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	return {
		fmoney:_fmoney,
		fdate:_fdate,
		setBirthyAndSexByID:_setBirthyAndSexByID,
		checkUniqueVal:_checkUniqueVal,
		inputOnlyNumber:_inputOnlyNumber,
		getBankByCardNumber:_getBankByCardNumber
	};
}(window,jQuery);