package app.component.pss.utils;

import java.math.BigDecimal;
import java.util.List;

import app.component.pss.stock.entity.PssStoreStockDetail;

public class DataUtil {
	
	private static BigDecimal formatVal(double value){
		return new BigDecimal(String.valueOf(value).replace(",", ""));
	}
	
	/**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @param scale 保留位数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2, int scale){
        BigDecimal v1 = formatVal(value1);
        BigDecimal v2 = formatVal(value2);
        return v1.add(v2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @param scale 保留位数
     * @return 两个参数的差
     */
    public static double sub(double value1,double value2, int scale){
    	BigDecimal v1 = formatVal(value1);
        BigDecimal v2 = formatVal(value2);
        return v1.subtract(v2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @param scale 保留位数
     * @return 两个参数的积
     */
    public static double mul(double value1,double value2, int scale){
    	BigDecimal v1 = formatVal(value1);
        BigDecimal v2 = formatVal(value2);
        return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 保留位数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1,double value2,int scale){
    	BigDecimal v1 = formatVal(value1);
        BigDecimal v2 = formatVal(value2);
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 根据存货计价方法计算存货计价金额
     * @param calType：1-移动平均法，2-先进先出法
     * @param inList：入库明细集合
     * @param outlist：出库明细集合
     * @param amount：出库数量
     * @return 出库成本单价
     */
    public static double calGoodsAmt(String calType, List<PssStoreStockDetail> inList, List<PssStoreStockDetail> outList, 
    		double amount, int qtyScale, int amtScale){
    	double goodsAmt = 0;
    	if("1".equals(calType)){//移动平均法
    		double inTotalCost = 0;
    		double inTotalQty = 0;
    		for(PssStoreStockDetail pssd : inList){
    			inTotalCost = add(inTotalCost, pssd.getInCost(), amtScale);
    			if(PssEnumBean.BILL_BUS_TYPE.TYPE4.getValue().equals(pssd.getBillType())){
    				continue;
    			}
    			inTotalQty = add(inTotalQty, pssd.getInBaseUnitQty(), qtyScale);
    		}
    		/*for(PssStoreStockDetail pssd : outList){
    			inTotalCost = sub(inTotalCost, pssd.getOutCost(), amtScale);
    			inTotalQty = sub(inTotalQty, pssd.getOutBaseUnitQty(), qtyScale);
    		}*/
    		if(inTotalQty == 0){
    			goodsAmt = 0;
    		}else{
    			goodsAmt = div(inTotalCost, inTotalQty, amtScale);
    		}
    	}else if("2".equals(calType)){
    		double outTotalQty = 0;
    		for(PssStoreStockDetail pssd : outList){
    			outTotalQty = add(outTotalQty, pssd.getOutBaseUnitQty(), qtyScale);
    		}
    		double inTempTotalQty = 0;
    		double inTempQtyForOut = 0;
    		double inTempTotalQtyForOut = 0;//临时出库总数量
    		double inTempTotalAmtForOut = 0;//临时出库总成本
    		int dealTime = 1;
    		for(PssStoreStockDetail pssd : inList){
    			if(PssEnumBean.BILL_BUS_TYPE.TYPE4.getValue().equals(pssd.getBillType())){
    				continue;
    			}
    			inTempTotalQty = add(inTempTotalQty, pssd.getInBaseUnitQty(), qtyScale);
    			if(inTempTotalQty >= outTotalQty){
    				inTempQtyForOut = sub(inTempTotalQty, outTotalQty, qtyScale);
    				if(inTempQtyForOut >= amount){
    					if(dealTime == 1){
    						goodsAmt = pssd.getInUnitCost();
    						break;
    					}else{
    						double currAccount = sub(amount, inTempTotalQtyForOut, qtyScale);
    						inTempTotalAmtForOut = add(inTempTotalAmtForOut, mul(pssd.getInUnitCost(), currAccount, 
    								amtScale), amtScale);
    						inTempTotalQtyForOut = amount;
    						goodsAmt = div(inTempTotalAmtForOut, inTempTotalQtyForOut, amtScale);
    						break;
    					}
    				}else{
    					if(dealTime == 1){
    						inTempTotalAmtForOut = add(inTempTotalAmtForOut, mul(pssd.getInUnitCost(), inTempQtyForOut, 
    								amtScale), amtScale);
    						inTempTotalQtyForOut = inTempQtyForOut;
    						dealTime++;
    						continue;
    					}else{
    						inTempTotalAmtForOut = add(inTempTotalAmtForOut, pssd.getInCost(), amtScale);
    						inTempTotalQtyForOut = inTempQtyForOut;
    						continue;
    					}
    				}
    			}
    		}
    	}else {
		}
    	
    	return goodsAmt;
    }
    
}
