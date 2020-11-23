package app.component.thirdservice.dsdatas.feign;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
/**
 * 第三方大圣数据服务
 * @author wzw
 *
 */
public interface TdDsFeign {

	/**
	 * 运营商数据采集
	 * @param String jsonParam
	 * (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getTelecomData")
	Map<String, Object> getTelecomData(String jsonParam)
			throws ServiceException;
	
	/**
	 * 浅橙-黑名单(接口已下线)
	 * @param String jsonParam (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return 
	 * @throws ServiceException
	 */

	@RequestMapping(value = "/tdDs/getNewBlackListCheck")
	Map<String, Object> getNewBlackListCheck(String jsonParam)
			throws ServiceException;

	/**
	 * 百融-多次申请规则评分
	 * @param jsonParam (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getBrRuleApplyLoan")
	Map<String, Object> getBrRuleApplyLoan(String jsonParam)
			throws ServiceException;
	/**
	 * 百融-反欺诈特殊名单
	 * @param jsonParam (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getBrRuleSpecialList")
	Map<String, Object> getBrRuleSpecialList(String jsonParam)
			throws ServiceException;
	/**
	 * 百融-月度收支等级整合报告
	 * @param jsonParam (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getBrAccountChangeC")
	Map<String, Object> getBrAccountChangeC(String jsonParam)
			throws ServiceException;
	
	/**
	 * 圣数-火眼黑名单
	 * @param jsonParam (userName 姓名 ,idCardNo 身份证号 ,phone 手机号)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getFireEyesBlack")
	Map<String, Object> getFireEyesBlack(String jsonParam)
			throws ServiceException;

	/**
	 * 异步通知 运营商数据采集
	 * @param map
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/noticTelecomData")
	void noticTelecomData(@RequestBody Map<String, String[]> map) throws ServiceException;

	/**
	 * 贷后邦-反欺诈
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getDhbGetSauronC")
	Map<String, Object> getDhbGetSauronC(String jsonParam)
			throws ServiceException;

	/**
	 * 天行_运营商三要素
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getMobileVerifyThree")
	Map<String, Object> getMobileVerifyThree(String jsonParam)
			throws ServiceException;

	/**
	 * 天行-银行卡四要素
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdDs/getBankCardVerifyFour")
	Map<String, Object> getBankCardVerifyFour(String jsonParam)
			throws ServiceException;

}
