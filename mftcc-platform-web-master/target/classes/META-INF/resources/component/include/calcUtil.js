(function() {
	var calcUtil = {
		/*
		 * 函数，加法函数，用来得到精确的加法结果
		 * 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
		 * 参数：arg1：第一个加数；arg2第二个加数; 调用：CalcUtil.Add(arg1,arg2) 返回值：两数相加的结果
		 */
		add : function(arg1, arg2) {
			arg1 = arg1.toString();
			arg2 = arg2.toString();
			var arg1Arr = arg1.split(".");
			var arg2Arr = arg2.split(".");
			var d1 = arg1Arr.length == 2 ? arg1Arr[1] : "";
			var d2 = arg2Arr.length == 2 ? arg2Arr[1] : "";
			var maxLen = Math.max(d1.length, d2.length);
			var m = Math.pow(10, maxLen);
			var result = Number(((arg1 * m + arg2 * m) / m).toFixed(maxLen));
			return result;
		},
		/*
		 * 函数：减法函数，用来得到精确的减法结果 说明：函数返回较为精确的减法结果。 参数：arg1：第一个加数；arg2第二个加数
		 * 调用：CalcUtil.Sub(arg1,arg2) 返回值：两数相减的结果
		 */
		subtract : function(arg1, arg2) {
			return CalcUtil.add(arg1, -Number(arg2));
		},
		/*
		 * 函数：乘法函数，用来得到精确的乘法结果 说明：函数返回较为精确的乘法结果。 参数：arg1：第一个乘数；arg2第二个乘数;
		 * 调用：CalcUtil.Mul(arg1,arg2) 返回值：两数相乘的结果
		 */
		multiply : function(arg1, arg2) {
			var r1 = arg1.toString();
			var r2 = arg2.toString();
			var m;
			var resultVal;
			m = (r1.split(".")[1] ? r1.split(".")[1].length : 0) + (r2.split(".")[1] ? r2.split(".")[1].length : 0);
			resultVal = Number(r1.replace(".", "")) * Number(r2.replace(".", "")) / Math.pow(10, m);
			return resultVal;
		},
		/*
		 * 函数：除法函数，用来得到精确的除法结果 说明：函数返回较为精确的除法结果。 参数：arg1：除数；arg2被除数
		 * 调用：CalcUtil.Div(arg1,arg2) 返回值：arg1除于arg2的结果
		 */
		divide : function(arg1, arg2) {
			var r1 = arg1.toString();
			var r2 = arg2.toString();
			var m;
			var resultVal;
			m = (r2.split(".")[1] ? r2.split(".")[1].length : 0) - (r1.split(".")[1] ? r1.split(".")[1].length : 0);
			resultVal = Number(r1.replace(".", "")) / Number(r2.replace(".", "")) * Math.pow(10, m);
			return resultVal;
		},
		/*
		 * 将数值四舍五入后格式化. @param num 数值(Number或者String) @param cent要保留的小数位(Number)
		 * @return 格式的字符串,如'1,234,567.45' @type String
		 * 调用：CalcUtil.FormatNumber(num,cent,isThousand)
		 */
		formatMoney : function(num, cent) {
			num = num.toString().replace(/\$|\,/g, '');
			if (isNaN(num)) {// 检查传入数值为数值类型.
				num = "0";
			}
			if (isNaN(cent)) {// 确保传入小数位为数值型数值.
				cent = 0;
			}
			var isThousand = 1;
			cent = parseInt(cent);
			cent = Math.abs(cent);// 求出小数位数,确保为正整数.
			isThousand = parseInt(isThousand);

			sign = (num == (num = Math.abs(num)));// 获取符号(正/负数)
			// Math.floor:返回小于等于其数值参数的最大整数
			num = Math.floor(num * Math.pow(10, cent) + 0.5000000000001);// 把指定的小数位先转换成整数.多余的小数位四舍五入.
			cents = num % Math.pow(10, cent); // 求出小数位数值.
			num = Math.floor(num / Math.pow(10, cent)).toString();// 求出整数位数值.
			cents = cents.toString();// 把小数位转换成字符串,以便求小数位长度.
			while (cents.length < cent) {// 补足小数位到指定的位数.
				cents = "0" + cents;
			}
			// 对整数部分进行千分位格式化.
			for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
				num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
			}
			return (((sign) ? '' : '-') + num + '.' + cents);
		},
		sum : function (sumList) {
            var sumAmt = '0';
            sumList.forEach(function (v) {
                if (v === '') {
                    v = '0';
                }
                sumAmt = calcUtil.add(v.replace(/,/g, ''), sumAmt);
            });

            return sumAmt;
        }//比较大小,1大于0等于-1小于
		,compare : function(arg1 , arg2){
			 if (typeof (arg1) == "undefined" || arg1 === null || arg1 == '') {
				 arg1 = '0';
             }
			 if (typeof (arg2) == "undefined" || arg2 === null || arg2 === '') {
				 arg2 = '0';
			 }
			 var result = calcUtil.subtract(arg1.replace(/,/g, ''), arg2.replace(/,/g, ''));
			 if(result > 0){
				 return 1;
			 }else if(result == 0){
				 return 0;
			 }else{
				 return -1;
			 }
		}
	};
	window.CalcUtil = calcUtil;
}());