package app.component.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Stack;

public class NumberUtil {
	
	/**
	 * 默认利息保留位数。
	 */
	public static int DEFAULT_INTST_DIG_COUNT = 12 ;

	/**
	 * 利率保留位数
	 */
	public static int DEFAULT_RATE_DIG_COUNT = 9 ;

	/**
	 * 利率比较值
	 */
	public static double DEFAULT_RATE_COMPARE_VALUE = 1E-9;

	/**
	 * 金额中间计算保留位数
	 */
	public static int DEFAULT_AMT_COMPARE_DIG_COUNT = 11;

	/**
	 * 浮点数比较值
	 */
	public static double DEFAULT_FLOAT_COMPARE_VALUE = 1E-8;
	
	/**
	 * 金额比较值
	 */
	public static double DEFAULT_AMOUNT_COMPARE_VALUE = 1E-7;
	
	/**
	 * 金额与0的比较值
	 */
	public static double DEFAULT_AMOUNT_ZERO_VALUE = 1E-4;

	/**
	 * 0的浮点数
	 */
	public final static Double ZERO_DOUBLE = Double.valueOf(0);
	
	

	/**
	 * 如果Double为空返回零
	 * 
	 * @param input
	 * @return
	 */
	public static Double getZeroDoubleIfNull(Double input) {
		return input == null ? NumberUtil.ZERO_DOUBLE : input;
	}

	
	/**
	 * 两个利率相减,返回四舍五入
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double rateSubs(double left1, double left2) {
		BigDecimal dec1 = getBigDecimal(left1,
				NumberUtil.DEFAULT_RATE_DIG_COUNT + 1);

		BigDecimal dec2 = getBigDecimal(left2,
				NumberUtil.DEFAULT_RATE_DIG_COUNT + 1);
		BigDecimal dec3 = dec1.subtract(dec2);
		
		return getBigDecimal(dec3).doubleValue();
	}
	
	/**
	 * 两个利率相除,返回四舍五入
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double rateDiv(double left1, double left2) {
		return div(left1, left2, NumberUtil.DEFAULT_RATE_DIG_COUNT);
	}

	/**
	 * 两个利率相除,返回四舍五入
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount 保留小数点位数
	 * @return
	 */
	public static double rateDiv(double left1, double left2, int digCount) {
		return div(left1, left2, digCount);
	}

	/**
	 * 利率相乘
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double rateMult(double left1, double left2) {
		return mult(left1, left2, NumberUtil.DEFAULT_RATE_DIG_COUNT) ;
	}

	/**
	 * 利率相乘
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount 小数点保留位数
	 * @return
	 */
	public static double rateMult(double left1, double left2,int digCount) {
		return mult(left1, left2, digCount, RoundingMode.HALF_UP ) ;
	}

	/**
	 * 判断两个利率是否相等
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static boolean isRateEqual(double rate1, double rate0) {
		double result = Math.abs(rateSubs(rate1, rate0));
		
		return getDouble(result, NumberUtil.DEFAULT_RATE_DIG_COUNT) <= NumberUtil.DEFAULT_RATE_COMPARE_VALUE;
	}

	/**
	 * rate1是否大于rate0
	 * 
	 * @param rate1
	 * @param rate0
	 * @return
	 */
	public static boolean isRateGreat(double rate1, double rate0) {
		double result = Math.abs(rateSubs(rate1, rate0));

		return getDouble(result, NumberUtil.DEFAULT_RATE_DIG_COUNT) > NumberUtil.DEFAULT_RATE_COMPARE_VALUE;
	}

	/**
	 * 两个数想减
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double amtSubs(double left1, double left2) {
		BigDecimal dec1 = getBigDecimal(left1,
				NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT + 1);
		BigDecimal dec2 = getBigDecimal(left2,
				NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT + 1);
		BigDecimal dec3 = dec1.subtract(dec2);
		
		return getBigDecimal(dec3).doubleValue();
	}

	/**
	 * 两个数相乘。
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double amtMult(double left1, double left2) {
		return mult(left1, left2, NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT) ;
	}
	
	/**
	 * 两个数乘法
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount  保留位数
	 * @param mode      小数点保留方式
	 * 
	 * @return 
	 */
	public static double mult(double left1, double left2, int digCount, RoundingMode mode) {
		BigDecimal dec1 = BigDecimal.valueOf(left1) ;
		BigDecimal dec2 = BigDecimal.valueOf(left2) ;

		BigDecimal dec3 = dec1.multiply(dec2) ;

		dec3 = dec3.setScale(digCount, mode);
		
		return dec3.doubleValue();
	}
	
	/**
	 * 两个数相乘,四舍五入
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount  保留位数
	 * @return
	 */
	public static double mult(double left1, double left2, int digCount) {
		return mult(left1, left2, digCount, RoundingMode.HALF_UP);
	}

	/**
	 * 两个数相乘
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount  保留位数
	 * @param roundingMode 整数化方法
	 * @return
	 */
	
	public static double amtMult(double left1, double left2, int digCount, RoundingMode roundingMode) {
		BigDecimal amt = getBigDecimal(left1 * left2, digCount + 1, roundingMode);

		return amt.doubleValue();
	}

	/**
	 * 两个数除法,四舍五入
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount  保留位数
	 * 
	 * @return 
	 */
	public static double div(double left1, double left2, int digCount){

		return div(left1, left2, digCount, RoundingMode.HALF_UP) ;
	}
	
	/**
	 * 两个数想除法
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param digCount  保留位数
	 * @param mode      小数点保留方式
	 * 
	 * @return 
	 */
	public static double div(double left1, double left2, int digCount, RoundingMode mode) {
		BigDecimal dec1 = getBigDecimal(left1, digCount+1, mode);
		BigDecimal dec2 = getBigDecimal(left2, digCount+1, mode);
		
		BigDecimal dec3 = dec1.divide(dec2, digCount, mode);
		
		return dec3.doubleValue();
	}
	
	/**
	 * 两个金额相除
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * 
	 * @return 
	 */
	public static double amtDiv(double left1, double left2) {

		return div(left1, left2, NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT);
	}
	
	/**
	 * 两个金额相除,保留小数点位数
	 * 
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @param dig 小数点位数
	 * 
	 * @return 
	 */
	public static double amtDiv(double left1, double left2, int dig) {

		return div(left1, left2, dig);
	}

	/**
	 * 获取金额，四舍五入
	 * 
	 * @param amt   金额
	 * @return
	 */
	public static double getDouble(double amt) {
		
		return getDouble(amt, RoundingMode.HALF_UP) ;
	}

	/**
	 * 按照格式化获取金额
	 * 
	 * @param amt    金额
	 * @param mode   小数点保留方式
	 * @return
	 */
	public static double getDouble(double amt, RoundingMode mode) {
		BigDecimal dec1 = BigDecimal.valueOf(amt);
		dec1 = dec1.setScale(NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT, mode);
		
		return dec1.doubleValue();
	}

	/**
	 * 获取最小值。
	 * 
	 * @param amt0
	 * @param amt1
	 * @return
	 */
	public static double getMin(double amt0, double amt1) {
		if (isAmtGreat(amt1, amt0)) {
			return amt0;
		}// 小于等于
		return amt1;
	}
	
	/**
	 * 指定保留位数，四舍五入。
	 * 
	 * @param amt        金额 
	 * @param digCount   小数点保留位数
	 * @return double
	 */
	public static double getDouble(double amt, int digCount) {
		
		return getDouble(amt, digCount, RoundingMode.HALF_UP) ;
	}

	/**
	 * 指定保留位数及小数点保留方式。
	 * 
	 * @param amt        金额 
	 * @param digCount   小数点保留位数
	 * @param mode   	 小数点保留方式
	 * @return double
	 */
	public static double getDouble(double amt, int digCount, RoundingMode mode) {
		BigDecimal dec1 = BigDecimal.valueOf(amt);
		dec1 = dec1.setScale(digCount, mode);
		
		return dec1.doubleValue();
	}
	
	/**
	 * 指定保留位数，四舍五入。
	 * 
	 * @param amt        金额 
	 * @param digCount   小数点保留位数
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(double amt, int digCount) {
		BigDecimal dec1 = getBigDecimal(amt, digCount, RoundingMode.HALF_UP);
		
		return dec1;
	}

	/**
	 * 指定保留位数及小数点保留方式。
	 * 
	 * @param amt        金额 
	 * @param digCount   小数点保留位数
	 * @param mode   	 小数点保留方式
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(double amt, int digCount, RoundingMode mode) {
		BigDecimal dec1 = BigDecimal.valueOf(amt);
		dec1 = dec1.setScale(digCount, mode);
		
		return dec1;
	}

	/**
	 * double转BigDecimal，默认金额保留位数四舍五入
	 * 
	 * @param amt        金额 
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(double amt) {
		BigDecimal dec1 = getBigDecimal(amt, RoundingMode.HALF_UP);
		
		return dec1;
	}

	/**
	 * double转BigDecimal，默认金额保留位数
	 * 
	 * @param amt        金额 
	 * @param mode       小数点保留方式
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(double amt, RoundingMode mode) {
		BigDecimal dec1 = BigDecimal.valueOf(amt);
		dec1 = dec1.setScale(NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT, mode);
		
		return dec1;
	}

	/**
	 * BigDecimal四舍五入，保留默认金额位数
	 * 
	 * @param amt        金额 
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(BigDecimal amt) {
		amt = getBigDecimal(amt, RoundingMode.HALF_UP);
		
		return amt;
	}

	/**
	 * BigDecimal，保留默认金额位数
	 * 
	 * @param amt        金额
	 * @param mode       小数点保留方式 
	 * 
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(BigDecimal amt, RoundingMode mode) {
		amt = amt.setScale(NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT, mode);
		
		return amt;
	}

	/**
	 * 两个相加
	 * @param left1 算式左边第一个
	 * @param left2 算式左边第二个
	 * @return
	 */
	public static double amtAdd(double left1, double left2) {
		
		BigDecimal dec1 = getBigDecimal(left1,
				NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT + 1);
		BigDecimal dec2 = getBigDecimal(left2,
				NumberUtil.DEFAULT_AMT_COMPARE_DIG_COUNT + 1);
		BigDecimal dec3 = dec1.add(dec2);
		
		return getBigDecimal(dec3).doubleValue();
	}

	/**
	 * 第一个数是比较第二个数 如果左边>右边返回1 如果相等 返回0 如果左边<右边返回负数
	 * 
	 * @param left1
	 * @param left2
	 * @return
	 */
	public static int amtCompare(double left1, double left2) {
		double result = amtSubs(left1, left2);
		if (result > NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return 1;
		}
		if (Math.abs(result) <= NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
            return 0;
        }
		return -1;
	}
	/**
	 * 判断两个金额是否相等，返回true、false
	 * 
	 * @param amt1
	 * @param amt2
	 * 
	 * @return boolean
	 */
	public static boolean isAmtEqual(double amt1, double amt0) {
		double result = Math.abs(amtSubs(amt1, amt0));
		return getDouble(result, 2) < NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE;
	}

	/**
	 * 第一个数是否大于第二个数，返回true、false
	 * 
	 * @param amt1
	 * @param amt0
	 * 
	 * @return boolean
	 */
	public static boolean isAmtGreat(double amt1, double amt0) {
		double result = amtSubs(amt1, amt0);
		if (result > NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * 第一个数是否大于等于第二个数，返回true、false
	 * 
	 * @param amt1
	 * @param amt0
	 * 
	 * @return boolean
	 */
	public static boolean isAmtGreatAndEqual(double amt1, double amt0) {
		double result = amtSubs(amt1, amt0);
		if (result >= -NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * 第一个数是否大于等于第二个数，返回true、false
	 * 
	 * @param amt1
	 * @param amt0
	 * 
	 * @return boolean
	 */
	public static boolean isAmtGreatEqual(double amt1, double amt0) {
		return isAmtGreatAndEqual(amt1, amt0);
	}

	/**
	 * 判断金额是否大于0，返回true、false
	 * 
	 * @param amt
	 * 
	 * @return boolean
	 */
	public static boolean isAmtGreatThanZero(double amt) {
		if (amt > NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断金额是否小于0，返回true、false
	 * 
	 * @param amt
	 * 
	 * @return boolean
	 */
	public static boolean isAmtLessThanZero(double amt) {
		return amt < -NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE;
	}

	/**
	 * 判断一个金额是否<=0;
	 * 
	 * @param amt
	 * 
	 * @return boolean
	 */
	public static boolean isAmtLessThanOrEqualZero(double amt) {
		if (amt <= NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断金额是否=0,返回true、false
	 * 
	 * @param amt
	 * 
	 * @return boolean
	 */
	public static boolean isAmtEqualZero(double amt) {
		if (Math.abs(amt) <= NumberUtil.DEFAULT_AMOUNT_ZERO_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化浮点数。
	 * 
	 * @param d
	 * @param count数位
	 * @return
	 */
	public static String formatDec(double d, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append("0");
		}
		NumberFormat format = new DecimalFormat("#0." + sb.toString());
		return format.format(d);
	}
	
	/**
	 * 外币金额转换成等值人民币金额（小数点后保留两位小数）
	 * 
	 * @param frnCurr
	 *            外币金额
	 * @param exgRate
	 *            汇率（外币兑人民币）
	 * @param tradingUnit
	 *            汇率的交易单位
	 * @return 人民币金额
	 */
	public static double frnCurr2Rmb(double frnCurr, double exgRate,
			int tradingUnit) {
		return amtDiv(amtMult(frnCurr, exgRate), tradingUnit, 2);
	}

	/**
	 * 人民币金额转换成等值外币金额（小数点后保留两位小数）
	 * 
	 * @param rmb
	 *            人民币金额
	 * @param exgRate
	 *            汇率（外币兑人民币）
	 * @param tradingUnit
	 *            汇率的交易单位
	 * @return 外币金额
	 */
	public static double rmb2FrnCurr(double rmb, double exgRate, int tradingUnit) {
		return amtDiv(amtMult(rmb, tradingUnit), exgRate, 2);
	}

	/**
	 * 货币金额等值转换（币种1金额 -> 等值币种2金额，小数点后保留两位小数）
	 * 
	 * @param currAmt1
	 *            币种1的金额
	 * @param exgRate1
	 *            币种1的汇率（外币兑人民币）
	 * @param tradingUnit1
	 *            币种1汇率的交易单位
	 * @param exgRate2
	 *            币种2的汇率（外币兑人民币）
	 * @param tradingUnit2
	 *            币种2汇率的交易单位
	 * @return 币种2金额
	 */
	public static double currAmtTrans(double currAmt1, double exgRate1,
			int tradingUnit1, double exgRate2, int tradingUnit2) {
		return amtDiv(amtMult(currAmt1, amtDiv(exgRate1, tradingUnit1)),
				amtDiv(exgRate2, tradingUnit2), 2);
	}

	/**
	 * 金额格式化
	 * @param s 金额
	 * @param len 小数位数
	 * @return 格式后的金额
	 */
	public static String insertComma(String s, int len) {
	    if("0.0".equals(s)){
	    	return s;
	    }
		if (s == null || s.length() < 1) {
	        return "";
	    }
	    NumberFormat formater = null;
	    double num = Double.parseDouble(s);
	    if (len == 0) {
	        formater = new DecimalFormat("###,###");
	 
	    } else {
	        StringBuffer buff = new StringBuffer();
	        buff.append("###,###.");
	        for (int i = 0; i < len; i++) {
	            buff.append("0");
	        }
	        formater = new DecimalFormat(buff.toString());
	    }
	    return formater.format(num);
	}
	
	public static String formatDouble(double s){
	     DecimalFormat fmt = new DecimalFormat("#,##0.00");
	     return fmt.format(s);
	}
	/**
	 * Double转String
	 * @param s
	 * @return
	 */
	public static String doubleChangeString(double s){
	     DecimalFormat fmt = new DecimalFormat("###0.00");
	     return fmt.format(s);
	}
	/**
	 * 金额去掉“,”
	 * @param s 金额
	 * @return 去掉“,”后的金额
	 */
	public static String delComma(String s) {
	    String formatString = "";
	    if (s != null && s.length() >= 1) {
	        formatString = s.replaceAll(",", "");
	    }
	 
	    return formatString;
	}
	
	/**
	 * @author xinjianxun 通过公式计算出结果
	 * @param formula
	 *            公式
	 * @return 计算的结果
	 * @throws AccountingException
	 */
	public static double caculateFormula(String formula) throws Exception {
		// (57.232*(39.0-67))*(89.3-38473.0)
		double formulaValue = 0.0;
		int i = 0;
		int j = 0;
		Stack<Character> operatorStack = new Stack<Character>();
		Stack<Double> numberStack = new Stack<Double>();
		String[] sequence = new String[20];
		while (i < formula.length()) {
			char c = formula.charAt(i);
			switch (c) {
			case '(':
				operatorStack.push(c);
				i++;
				break;
			case ')':
				while (operatorStack.peek().charValue() != '(') {
					sequence[j++] = String.valueOf(operatorStack.pop().charValue());
				}
				operatorStack.pop();
				i++;
				break;
			case '+':
			case '-':
				if (i > 0) {
					char previousChar = formula.charAt(i - 1);
					if (previousChar != '+' && previousChar != '-' && previousChar != '*' && previousChar != '/'
						&& previousChar != '(') {
						while (!operatorStack.empty() && operatorStack.peek().charValue() != '(') {
							sequence[j++] = String.valueOf(operatorStack.pop().charValue());
						}
						operatorStack.push(c);
					}
				}
				i++;
				break;
			case '*':
			case '/':
				while (!operatorStack.empty()
						&& (operatorStack.peek().charValue() == '*' || operatorStack.peek().charValue() == '/')) {
					sequence[j++] = String.valueOf(operatorStack.pop().charValue());
				}
				operatorStack.push(c);
				i++;
				break;
			case ' ':
				i++;
				break;
			default:
				StringBuffer tempDoubleStr = new StringBuffer("");
			while (i < formula.length()) {
				c = formula.charAt(i);
				if ((c >= '0' && c <= '9' || c == '.')) {
					if (i > 0) {
						char preStr = formula.charAt(i - 1);
						if (preStr == '-') {
							if (i > 1) {
								char prePreStr = formula.charAt(i - 2);
								if (prePreStr == '+' || prePreStr == '-' || prePreStr == '*' || prePreStr == '/'
									|| prePreStr == '(') {
									tempDoubleStr.append(formula.charAt(i - 1));
								}
							} else {
								tempDoubleStr.append(formula.charAt(i - 1));
							}

						}
					}
					tempDoubleStr.append(c);
					i++;
				} else {
					if (!(c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')')) {
						// System.out.println(c);
						throw new Exception("公式" + formula + "不合法");
					}
					break;
				}
			}
			sequence[j++] = tempDoubleStr.toString();
			break;
			}
		}
		while (!operatorStack.empty()) {
			sequence[j++] = String.valueOf(operatorStack.pop().charValue());
		}
		double a = 0;
		double b = 0;
		double c = 0;
		try {
			for (int k = 0; k < sequence.length; k++) {
				String strInSeq = sequence[k];
				if (strInSeq == null) {
					break;
				}
				if ("+".equals(strInSeq)) {
					b = numberStack.pop().doubleValue();
					a = numberStack.pop().doubleValue();
					c = NumberUtil.amtAdd(a, b);
					numberStack.push(c);
				} else if ("-".equals(strInSeq)) {
					b = numberStack.pop().doubleValue();
					a = numberStack.pop().doubleValue();
					c = NumberUtil.amtSubs(a, b);
					numberStack.push(c);
				} else if ("*".equals(strInSeq)) {
					b = numberStack.pop().doubleValue();
					a = numberStack.pop().doubleValue();
					c = NumberUtil.amtMult(a, b);
					numberStack.push(c);
				} else if ("/".equals(strInSeq)) {
					b = numberStack.pop().doubleValue();
					a = numberStack.pop().doubleValue();
					c = NumberUtil.amtDiv(a, b);
					numberStack.push(c);
				} else {
					double doubleValue = Double.parseDouble(strInSeq);
					numberStack.push(doubleValue);
				}
			}
			formulaValue = numberStack.pop().doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(formula + "配置不正确不能计算");
		}
		return formulaValue;
	}
	
	public static void main(String[] args) {
//		double renminbi = NumberUtil.frnCurr2Rmb(123.43, 643.77, 100);
//		System.out.println(renminbi);
//		double foreigncurrency = NumberUtil.rmb2FrnCurr(renminbi, 643.77, 100);
//		System.out.println(foreigncurrency);
//		System.out.println(NumberUtil.currAmtTrans(123.43, 643.77, 100, 100,
//				100));
		System.out.println(NumberUtil.insertComma("5000000.23",0));
	}

}
