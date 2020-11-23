/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RiskUtil.java
 * 包名： app.component.risk.util
 * 说明：
 * @author 沈浩兵
 * @date 2016-10-27 下午8:53:18
 * @version V1.0
 */ 
package app.component.risk.util;

import app.component.common.BizPubParm;

/**
 * 类名： RiskUtil
 * 描述：
 * @author 沈浩兵
 * @date 2016-10-27 下午8:53:18
 *
 *
 */
public class RiskUtil {
	/**
	 * 
	 * 方法描述：  获取拦截项风险级别，阀值类型为字典项
	 * @param dicValue
	 * @param codeVal
	 * @param riskLevel
	 * @return
	 * String
	 * @author 沈浩兵
	 * @date 2016-10-28 上午10:21:12
	 */
	public static String JudgeComputing_dic(String dicValue,String codeVal,String riskLevel){
		String riskRestrictLevel = "";
		if(dicValue.equals(codeVal)){
			riskRestrictLevel = riskLevel;
		}
		return riskRestrictLevel;
	}
	/**
	 * 
	 * 方法描述： 获取拦截项风险级别，阀值类型为数值
	 * @param opVal
	 * @param opSymbol
	 * @param codeVal
	 * @param riskLevel
	 * @return
	 * String
	 * @author 沈浩兵
	 * @date 2016-10-28 上午9:57:00
	 */
	public static String JudgeComputing_num(double opVal,String opSymbol,double codeVal,String riskLevel){
		String riskRestrictLevel = "";
		if(BizPubParm.OPSYMBOL_1.equals(opSymbol)){
			if(opVal==codeVal){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol)){
			if(opVal<codeVal){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol)){
			if(opVal>codeVal){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol)){
			if(opVal<=codeVal){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol)){
			if(opVal>=codeVal){
				riskRestrictLevel = riskLevel;
			}
		}else{
			System.out.println("业务参数配置不正确");
		}
		return riskRestrictLevel;
	}
	/**
	 * 
	 * 方法描述： 获取拦截项风险级别，阀值类型为数值范围
	 * @param opVal1
	 * @param opVal2
	 * @param codeVal
	 * @param riskLevel
	 * @return
	 * String
	 * @author 沈浩兵
	 * @date 2016-10-28 上午10:31:37
	 */
	public static String JudgeComputing_range(double opVal1,double opVal2,double codeVal,String riskLevel){
		String riskRestrictLevel = "";
		if(opVal1<=codeVal&&codeVal<=opVal2){
			riskRestrictLevel = riskLevel;
		}
		return riskRestrictLevel;
	}
	/**
	 * 获取拦截项风险级别，阀值类型为数值范围
	 * @param opVal1
	 * @param opVal2
	 * @param opSymbol1
	 * @param opSymbol2
	 * @param opSymbol3
	 * @param codeVal
	 * @param riskLevel
	 * @return
	 */
	public static String JudgeComputing_range(double opVal1,double opVal2,String opSymbol1,String opSymbol2,double codeVal,String riskLevel){
		String riskRestrictLevel = "";
		if(BizPubParm.OPSYMBOL_1.equals(opSymbol1)||BizPubParm.OPSYMBOL_1.equals(opSymbol2)){
			if(opVal1==codeVal||codeVal==opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal>=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal<=opVal2){
				riskRestrictLevel = riskLevel;
			}
		}else{
			System.out.println("业务参数配置不正确");
		}
		return riskRestrictLevel;
	}
}
