package app.component.oa.consumable.consextendinterface;

import java.math.BigDecimal;

import app.component.oa.consumable.entity.OpUserCons;

/**
 * 
 * @author LiuAo
 *
 */
public interface ConsExtendInterface {
	/**
	 * 返回当前操作员与低值易耗品有关统计信息
	 * @return OpUserCons
	 * @throws Exception
	 */
	public OpUserCons getOpUserCons() throws Exception;
	/**
	 * 返回当前操作员借款有关统计信息
	 * @return BigDecimal[]
	 * @throws Exception
	 */
	
	public BigDecimal[] sumAmt(String opNo) throws Exception;
}
