package  app.component.calc.core.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayHistoryDetail;
import app.util.toolkit.Ipage;

import java.util.List;

/**
* Title: MfRepayHistoryDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 26 11:34:32 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayHistoryDetailFeign {
	
	@RequestMapping(value = "/mfRepayHistoryDetail/insert")
	public void insert(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetail/delete")
	public void delete(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetail/update")
	public void update(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetail/getById")
	public MfRepayHistoryDetail getById(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayHistoryDetail") MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;

	/**
	 * 
	 * 方法描述： 根据repayid获取最大的一期
	 * @param detailbean
	 * @return
	 * @throws Exception
	 * MfRepayHistoryDetail
	 * @author lzshuai
	 * @date 2017-9-29 上午10:52:06
	 */
	@RequestMapping(value = "/mfRepayHistoryDetail/getMaxNumBeanbyId")
	public MfRepayHistoryDetail getMaxNumBeanbyId(@RequestBody MfRepayHistoryDetail detailbean) throws Exception;
	/**
	 * 
	 * 方法描述：根据 repayid这一期最小的
	 * @param detailbean
	 * @return
	 * @throws Exception
	 * MfRepayHistoryDetail
	 * @author lzshuai
	 * @date 2017-9-29 下午2:25:41
	 */
	@RequestMapping(value = "/mfRepayHistoryDetail/getMinNumBeanbyId")
	public MfRepayHistoryDetail getMinNumBeanbyId(@RequestBody MfRepayHistoryDetail detailbean)throws Exception;

	/**
	 *
	 * 方法描述：查还款历史明细
	 *
	 * @param detailbean
	 * @return
	 * @throws Exception
	 *             MfRepayHistoryDetail
	 * @date 2018-11-09
	 */
	@RequestMapping(value = "/mfRepayHistoryDetail/getMfRepayHistoryDetailList")
	public List<MfRepayHistoryDetail> getMfRepayHistoryDetailList(@RequestBody MfRepayHistoryDetail detailbean) throws Exception;
	/**
	 *
	 * 方法描述：查还款历史明细(关联了还款计划表）
	 *
	 * @param detailbean
	 * @return
	 * @throws Exception
	 *             MfRepayHistoryDetail
	 * @date 2018-11-09
	 */
	@RequestMapping(value = "/mfRepayHistoryDetail/findRepayHisDetailList")
	public List<MfRepayHistoryDetail> findRepayHisDetailList(@RequestBody MfRepayHistoryDetail detailbean) throws Exception;
	/**
	 *
	 * 方法描述：获取正常还款数据
	 *
	 * @param detailbean
	 * @return
	 * @throws Exception
	 *             MfRepayHistoryDetail
	 * @date 2018-11-09
	 */
	@RequestMapping(value = "/mfRepayHistoryDetail/getRepayHistoryDetailList")
	public List<MfRepayHistoryDetail> getRepayHistoryDetailList(@RequestBody MfRepayHistoryDetail detailbean) throws Exception;
}
