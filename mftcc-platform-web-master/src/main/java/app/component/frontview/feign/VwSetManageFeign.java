package app.component.frontview.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.frontview.entity.VwSetManage;
import app.util.toolkit.Ipage;

/**
 * Title: VwSetManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 28 15:40:30 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface VwSetManageFeign {
	/**
	 * 新增
	 * 
	 * @param vwSetManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/insert")
	public void insert(@RequestBody VwSetManage vwSetManage) throws ServiceException, Exception;

	/**
	 * 删除
	 * 
	 * @param vwSetManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwSetManage/delete")
	public void delete(@RequestBody VwSetManage vwSetManage) throws ServiceException, Exception;

	/**
	 * 更新
	 * 
	 * @param vwSetManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/update")
	public void update(@RequestBody VwSetManage vwSetManage) throws ServiceException, Exception;

	/**
	 * 详情
	 * 
	 * @param vwSetManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/getById")
	public VwSetManage getById(@RequestBody VwSetManage vwSetManage) throws ServiceException, Exception;

	/**
	 * 列表
	 * 
	 * @param ipage
	 * @param vwSetManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("vwSetManage") VwSetManage vwSetManage) throws ServiceException, Exception;

	/**
	 * 获取唯一一条数据
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/getVwSetBean")
	public VwSetManage getVwSetBean() throws ServiceException, Exception;

	/**
	 * 获取已上产的二维码图片base64字符串
	 * 
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwSetManage/getImgBase64")
	public String getImgBase64(@RequestBody String type) throws ServiceException, Exception;

	/**
	 * 保存上传图片并返回base64字符
	 * 
	 * @param upload base64字符串
	 * @param fileType
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/vwSetManage/updateImg",method=RequestMethod.POST)
	public String updateImg(@RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType, @RequestParam("flag") String flag) throws Exception;

}
