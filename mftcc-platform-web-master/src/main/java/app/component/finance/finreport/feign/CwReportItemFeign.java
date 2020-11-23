package app.component.finance.finreport.feign;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.finreport.entity.CwItemCalcList;
import app.component.finance.finreport.entity.CwReportAcount;
import app.component.finance.finreport.entity.CwReportItem;
import app.component.finance.finreport.entity.CwReportRelation;
import app.component.finance.finreport.entity.CwSearchReportList;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwReportItemBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 19 14:01:41 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwReportItemFeign {

	/**
	 * 方法描述：查询财务报表
	 * 
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 *             List<CwSearchReportList>
	 * @author Javelin
	 * @date 2017-1-19 下午3:02:14
	 */
	@RequestMapping(value = "/cwReportItem/getSearchReportList", method = RequestMethod.POST)
	public R getSearchReportList(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取财务报表设置列表
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             List<CwSearchReportList>
	 * @author Javelin
	 * @date 2017-3-8 下午5:31:02
	 */
	@RequestMapping(value = "/cwReportItem/getSetReportList", method = RequestMethod.POST)
	public List<CwSearchReportList> getSetReportList(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取财务报表预览数据
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             List<CwSearchReportList>
	 * @author Javelin
	 * @date 2017-3-13 下午2:21:28
	 */
	@RequestMapping(value = "/cwReportItem/getReportViewList", method = RequestMethod.POST)
	public List<CwSearchReportList> getReportViewList(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取报表项详情
	 * 
	 * @param cwReportItem
	 * @return
	 * @throws Exception
	 *             CwReportItem
	 * @author Javelin
	 * @date 2017-1-23 上午11:25:15
	 */
	@RequestMapping(value = "/cwReportItem/getReportItemDetail", method = RequestMethod.POST)
	public CwReportItem getReportItemDetail(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取报表项公司列表
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             List<CwReportRelation>
	 * @author Javelin
	 * @date 2017-1-23 下午2:30:40
	 */
	@RequestMapping(value = "/cwReportItem/getItemCalcSetList", method = RequestMethod.POST)
	public List<CwReportRelation> getItemCalcSetList(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取报表项公式金额组成
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             List<CwItemCalcList>
	 * @author Javelin
	 * @date 2017-2-8 下午4:37:39
	 */
	@RequestMapping(value = "/cwReportItem/getItemCalcViewList", method = RequestMethod.POST)
	public List<CwItemCalcList> getItemCalcViewList(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改报表项排序
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-10 上午9:21:30
	 */
	@RequestMapping(value = "/cwReportItem/updateOrder", method = RequestMethod.POST)
	public R updateOrder(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 报表项新增
	 * 
	 * @param cwReportItem
	 * @throws ServiceException
	 *             void
	 * @author Javelin
	 * @date 2017-3-9 下午4:34:16
	 */
	@RequestMapping(value = "/cwReportItem/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改报表项公式与详情数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-10 下午2:35:24
	 */
	@RequestMapping(value = "/cwReportItem/updateDeployData", method = RequestMethod.POST)
	public R updateDeployData(@RequestBody Map<String, Object> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取报表检查未配置列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author Javelin
	 * @date 2017-3-13 下午2:20:21
	 */
	@RequestMapping(value = "/cwReportItem/getCheckPickComList", method = RequestMethod.POST)
	public R getCheckPickComList(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwReportItem/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReportItem/update", method = RequestMethod.POST)
	public void update(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReportItem/getById", method = RequestMethod.POST)
	public CwReportItem getById(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReportItem/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReportItem/getAll", method = RequestMethod.POST)
	public List<CwReportItem> getAll(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 获取报表项树形
	 * 
	 * @param cwReportItem
	 * @return
	 */
	@RequestMapping(value = "/cwReportItem/getDataItemForTree", method = RequestMethod.POST)
	public List<Map<String, String>> getDataItemForTree(@RequestBody CwReportItem cwReportItem,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReportItem/getReportAccount", method = RequestMethod.POST)
	public CwReportAcount getReportAccount(@RequestBody CwReportAcount cwReportAcount,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 资产负债表行次修改
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-22 下午5:49:08
	 */
	@RequestMapping(value = "/cwReportItem/updateReportDataHangCi", method = RequestMethod.POST)
	public String updateReportDataHangCi(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 
	 * 方法描述： 设置页面修改行次
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-26 下午7:08:20
	 */
	@RequestMapping(value = "/cwReportItem/updateReportItemHangCiForItem", method = RequestMethod.POST)
	public String updateReportItemHangCiForItem(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取行次的功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-27 下午2:41:19
	 */
	@RequestMapping(value = "/cwReportItem/getHangCiFlag", method = RequestMethod.POST)
	public String getHangCiFlag(@RequestParam("type") String type) throws Exception;

	/**
	 * 
	 * 方法描述：修改行次禁用或启用
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-13 下午6:30:42
	 */
	@RequestMapping(value = "/cwReportItem/updateTrOpenCloseAjax", method = RequestMethod.POST)
	public String updateTrOpenCloseAjax(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 财务报表导出Excel
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             HSSFWorkbook
	 * @author lzshuai
	 * @date 2017-12-8 下午2:56:03
	 */
	@RequestMapping(value = "/cwReportItem/downReportToExcel", method = RequestMethod.POST)
	public HSSFWorkbook downReportToExcel(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;
	/**
	 * 
	 * 方法描述： 财务报表导出Excel
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             HSSFWorkbook
	 * @author lzshuai
	 * @date 2017-12-8 下午2:56:03
	 */
	@RequestMapping(value = "/cwReportItem/downReportToExcelBinary", method = RequestMethod.POST)
	public byte[] downReportToExcelBinary(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	
	/**
	 * 
	 * 方法描述： 获取公司名称
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2017-12-18 上午11:53:19
	 */
	@RequestMapping(value = "/cwReportItem/getComNameForReport", method = RequestMethod.POST) 
	public String getComNameForReport() throws Exception;

	/**
	 * 方法描述： 报表数据重置
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2018-1-9 下午5:09:12
	 */
	@RequestMapping(value = "/cwReportItem/resetReportData", method = RequestMethod.POST)
	public String resetReportData(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;
}
