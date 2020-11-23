package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.report.entity.MfCountByCus;
import app.component.report.entity.MfCountByVouType;
import app.component.report.entity.MfLoanBean;

@FeignClient("mftcc-platform-factor")
public interface MfReportLoanFeign {

	/**
	 * 
	 * 方法描述： 获取放款统计明细表
	 * @return
	 * @throws ServiceException
	 * List<MfLoanBean>
	 * @author lwq
	 * @date 2017-7-19 下午6:58:29
	 */
	@RequestMapping(value = "/mfReportLoan/getResultList")
	public List<MfLoanBean> getResultList(@RequestBody Map<String, String>map) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 放款统计表按客户类型统计
	 * @return
	 * @throws ServiceException
	 * List<MfCountByCus>
	 * @author lwq
	 * @date 2017-7-20 下午4:16:10
	 */
	@RequestMapping(value = "/mfReportLoan/getPutListByCus")
	public List<MfCountByCus> getPutListByCus(@RequestBody String opNo,@RequestParam("reportId") String reportId,@RequestParam("sqlMap") String sqlMap) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 获取放款统计表按照担保类型
	 * @return
	 * @throws ServiceException
	 * List<MfCountByVouType>
	 * @author lwq
	 * @date 2017-7-20 下午6:06:10
	 */
	@RequestMapping(value = "/mfReportLoan/getPutListByVouType")
	public List<MfCountByVouType> getPutListByVouType(@RequestBody Map<String, String>map) throws Exception;
}
