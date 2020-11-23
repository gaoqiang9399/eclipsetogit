package app.component.pss.stockinterface;

import java.io.File;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.utils.excel.PssExcelResultBean;

@FeignClient("mftcc-platform-factor")
public interface ExcelInterfaceFeign {
	
	/**
	 * 方法描述：获取导入EXCEL模板
	 * @param type
	 * @return
	 * @throws Exception
	 * @author HGX
	 * @date 2018-01-18 下午14:33:56
	 */
	@RequestMapping(value = "/excelInterface/getDownFileStream")
	public HSSFWorkbook getDownFileStream(@RequestBody String type)throws Exception;
	
	/**
	 * 方法描述：EXCEL模版导入
	 * @param file,type
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-18  下午14:33:56
	 */
	@RequestMapping(value = "/excelInterface/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-18  下午14:33:56
	 */
//	@RequestMapping(value = "/excelInterface/downloadToExcel")
	/*public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;*/
	
	
	/**
	 * 方法描述：购货单校验EXCEL信息（业务验证且处理数据）
	 * @param listPssExcelResultBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelInterface/getPssExcelResultBeanList")
	public <T, E> List<PssExcelResultBean<T, E>> getPssExcelResultBeanList(@RequestBody List<PssExcelResultBean<T, E>> listPssExcelResultBean) throws Exception;
	
	/**
	 * 方法描述：处理单据和单据明细信息（导出EXCEL转换数据内容）
	 * @param t
	 * @param list
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelInterface/dealBillAndDetailInfo")
	public <T, E> void  dealBillAndDetailInfo(@RequestBody T t, @RequestParam("list")List<E> list) throws Exception;
}
