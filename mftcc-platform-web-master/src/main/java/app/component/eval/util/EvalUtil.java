package app.component.eval.util;

import java.math.BigDecimal;
import java.util.List;

import app.component.common.BizPubParm;
import app.component.eval.entity.EvalScoreGradeConfig;

public class EvalUtil {
	
	/**
	 * 公式计算
	 * @param opVal1
	 * @param opVal2
	 * @param opSymbol1
	 * @param opSymbol2
	 * @param stdCore
	 * @return
	 */
	public static double JudgeComputing(double opVal1,double opVal2,String opSymbol1,String opSymbol2,double codeVal,double stdCore){
		double codeScore = 0.0;
		if(BizPubParm.OPSYMBOL_1.equals(opSymbol1)||BizPubParm.OPSYMBOL_1.equals(opSymbol2)){
			if(opVal1==codeVal||codeVal==opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>=opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<=opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>=opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<=opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal>=opVal2){
				codeScore = stdCore;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal<=opVal2){
				codeScore = stdCore;
			}
		}else{
			System.out.println("业务参数配置不正确");
		}
		return codeScore;
	}
	/**
	 * 获取评级级别
	 * @param opVal1
	 * @param opVal2
	 * @param opSymbol1
	 * @param opSymbol2
	 * @param opSymbol3
	 * @param codeVal
	 * @param evalLevel
	 * @return
	 */
	public static String JudgeComputing(double opVal1,double opVal2,String opSymbol1,String opSymbol2,String opSymbol3,double codeVal,String evalLevel){
		String evalRestrictLevel = "";
		if(BizPubParm.OPSYMBOL_1.equals(opSymbol1)||BizPubParm.OPSYMBOL_1.equals(opSymbol2)){
			if(opVal1==codeVal||codeVal==opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1>codeVal&&codeVal>=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal>opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal<opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1<codeVal&&codeVal<=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
			if(opVal1<=codeVal&&codeVal>=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
			if(opVal1>=codeVal&&codeVal<=opVal2){
				evalRestrictLevel = evalLevel;
			}
		}else{
			System.out.println("业务参数配置不正确");
		}
		return evalRestrictLevel;
	}
	
	/**
	 * 获取评级级别
	 * @param opVal1
	 * @param opVal2
	 * @param opSymbol1
	 * @param opSymbol2
	 * @param opSymbol3
	 * @param codeVal
	 * @param evalLevel
	 * @return
	 */
	public static String JudgeComputingEvalLevel(List<EvalScoreGradeConfig> evalScoreGradeConfigList,double totalScore,String evalLevel){
		for(EvalScoreGradeConfig esgc:evalScoreGradeConfigList){
			String tmpEvalLevel = esgc.getEvalLevel();
			String opSymbol1 = esgc.getOpSymbol1();
			String opSymbol2 = esgc.getOpSymbol2();
			double opScore1 = esgc.getOpScore1();
			double opScore2 = esgc.getOpScore2();
			if(BizPubParm.OPSYMBOL_1.equals(opSymbol1)||BizPubParm.OPSYMBOL_1.equals(opSymbol2)){
				if(opScore1==totalScore||totalScore==opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
				if(opScore1>totalScore&&totalScore>opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
				if(opScore1<totalScore&&totalScore<opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
				if(opScore1>=totalScore&&totalScore>=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
				if(opScore1<=totalScore&&totalScore<=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_2.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
				if(opScore1>totalScore&&totalScore>=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_2.equals(opSymbol2)){
				if(opScore1>=totalScore&&totalScore>opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
				if(opScore1<=totalScore&&totalScore<opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_3.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
				if(opScore1<totalScore&&totalScore<=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_5.equals(opSymbol1)&&BizPubParm.OPSYMBOL_4.equals(opSymbol2)){
				if(opScore1<=totalScore&&totalScore>=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_5.equals(opSymbol2)){
				if(opScore1>=totalScore&&totalScore<=opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else if(BizPubParm.OPSYMBOL_4.equals(opSymbol1)&&BizPubParm.OPSYMBOL_3.equals(opSymbol2)){
				if(opScore1<=totalScore&&totalScore<opScore2){
					evalLevel = tmpEvalLevel;
					break;
				}
			}else{
				System.out.println("业务参数配置不正确");
			}
		}
		return evalLevel;
	}
	/**
	 * 财务指标线性得分
	 * @param stdVal 标准值
	 * @param stdScore 标准得分
	 * @param minVal 最差值
	 * @param minScore 最差得分
	 * @param maxVal 最优值
	 * @param maxScore 最优得分
	 * 公式：（最优/最差-实际值）/（最优/最差得分 - X）=(实际值-标准)/（X-标准得分）
	 *    X为实际得分
	 * @return
	 */
	public static double finLinearScore(double stdVal,double stdScore,double minVal,double minScore,double maxVal,double maxScore,double codeVal){
		double codeScore = 0.0;
		if(codeVal>=maxVal){//最优值
			codeScore = maxScore;
		}else if(codeVal<=minVal){//最差值
			codeScore = minScore;
		}else if(codeVal==stdVal){//标准值
			codeScore = stdScore;
		}else if(stdVal<codeVal&&codeVal<maxVal){//线性值（标准以上）
			codeScore = (maxScore*(codeVal - stdVal)+stdScore*(maxVal - codeVal))/(maxVal - stdVal);
		}else if(minVal<codeVal&&codeVal<stdVal){//线性值（标准以下）
			codeScore = (minScore*(codeVal - stdVal)+stdScore*(minVal - codeVal))/(minVal - stdVal);
		}else {
		}
		//保留两位小数
		BigDecimal bigDecimal = new BigDecimal(codeScore);  
		codeScore = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return codeScore;
	}

}
