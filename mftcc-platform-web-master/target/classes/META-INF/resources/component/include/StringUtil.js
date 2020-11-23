;
var StringUtil = function(window, $) {

	return {
		/**
	     * 将str中的html符号转义,默认将转义''&<>''及单双引号五个字符，可自定义reg来确定需要转义的字符
	     * <strong>不建议使用自定义reg</strong>
	     * @name unhtml
	     * @grammar StringUtil.unhtml(str);  => String
	     * @grammar StringUtil.unhtml(str,reg)  => String
	     * @example
	     * var html = '<body>You say:"你好！Baidu & UEditor!"</body>';
	     * StringUtil.unhtml(html);   ==>  &lt;body&gt;You say:&quot;你好！Baidu &amp; UEditor!&quot;&lt;/body&gt;
	     */
		unhtml : function(str, reg) {
	        return str ? str.replace(reg || /[&<">'](?:(amp|lt|quot|gt|#39|nbsp);)?/g, function (a, b) {
                  if (b) {
                      return a;
                  } else {
                      return {
                          '<':'&lt;',
                          '&':'&amp;',
                          '"':'&quot;',
                          '>':'&gt;',
                          "'":'&#39;'
                      }[a];
                  }
              }) : '';
		},
		/**
	     * 将str中的转义字符还原成html字符
	     * @name html
	     * @grammar UM.utils.html(str)  => String   //详细参见<code><a href = '#unhtml'>unhtml</a></code>
	     */
	    html:function (str) {
	        return str ? str.replace(/&((g|l|quo)t|amp|#39);/g, function (m) {
	            return {
	                '&lt;':'<',
	                '&amp;':'&',
	                '&quot;':'"',
	                '&gt;':'>',
	                '&#39;':"'"
	            }[m]
	        }) : '';
	    },
		/**
		 * urlstr 要解析的url exitParm 仅需要其中的一个参数，则传入该参数的名称，找到后不再继续解析并返回该结果。
		 * 
		 * @author LiuYF
		 * @return 1、没有参数返回{}；2、没有传入exitParm，返回所有参数；3、传入exitParm，则返回截至解析到该参数时的所有参数。
		 * @例子： 1、 var rslt =
		 *      getUrlParams("MfBusApplyAction_getSummary.action");//——返回{}
		 *      rslt["appId"] === undefined; 2、 var rslt =
		 *      getUrlParams("Mf……y.action?appId=123&cifNo=666");//——返回{"appId":"123","cifNo":"666"}
		 *      rslt["appId"] === "123"; rslt["cifNo"] === "666"; 3、 var rslt =
		 *      getUrlParams("Mf……y.action?appId=123&cifNo=666",
		 *      "appId");//——返回{"appId":"123456"} rslt["appId"] === "123";
		 *      rslt["cifNo"] === undefined;
		 */
		urlParamsToObj : function(urlstr, exitParm) {
			var urlSplit = urlstr.split("?");
			if (urlSplit.length > 1) {
				var parms = urlSplit[1];
				return this.parmsToObj(parms);
			} else {
				// 没有问号，直接就是参数串。
				return this.parmsToObj(urlstr);
			}
		},
		/**
		 * 不带问号的参数传解析
		 */
		parmsToObj : function(urlstr, exitParm){
			var resultObj = {};
			var parms = urlstr.split("&");
			for ( var i = 0; i < parms.length; i++) {
				var keyValue = parms[i].split("=");
				
				resultObj[keyValue[0]] = keyValue[1];
				
				if (keyValue[0] === exitParm) {
					// 终止循环，返回单个值
					return resultObj;
				}
			}
			return resultObj;
		},
		/**
		 * 根据身份证号自动填充表单中性别和生日
		 * @param obj:身份证号所对应DOM对象;
		 * @param sex:表单中性别字段的name,可以为null;
		 * @param birthday:表单中生日字段name,可以为null或者不传
		 * @author LiuAo
		 * @例子 StringUtil.setBirthyAndSexByID("input[name='idNo']", null, 'birthday');
		 * @例子 StringUtil.setBirthyAndSexByID(this, 'sex', 'birthday');		//在身份证号的输入框的事件内调用，this指向DOM对象。
		 */
		setBirthyAndSexByID :　function (obj,sex,birthday,age){
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
				if(age){
					var myDate = new Date(); 
					var month = myDate.getMonth() + 1; 
					var day = myDate.getDate(); 
					var ageValue = myDate.getFullYear() - val.substring(6, 10) - 1; 
					if (val.substring(10, 12) < month || val.substring(10, 12) == month && val.substring(12, 14) <= day) { 
						ageValue++; 
					}
					$(obj).parents("form").find("[name='"+age+"']").val(ageValue);
				}
			};
		}
	};
}(window, jQuery);