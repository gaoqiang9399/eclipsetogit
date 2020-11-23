package app.component.finance.ztbooks.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.util.R;
import app.component.finance.ztbooks.entity.CwZtBooks;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;

/**
 * Title: CwZtBooksBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 19 09:27:42 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwZtBooksFeign {

	@RequestMapping(value = "/cwZtBooks/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwZtBooks cwZtBooks,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwZtBooks/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwZtBooks cwZtBooks) throws Exception;

	@RequestMapping(value = "/cwZtBooks/update", method = RequestMethod.POST)
	public void update(@RequestBody CwZtBooks cwZtBooks) throws Exception;

	@RequestMapping(value = "/cwZtBooks/getById", method = RequestMethod.POST)
	public CwZtBooks getById(@RequestBody CwZtBooks cwZtBooks) throws Exception;

	@RequestMapping(value = "/cwZtBooks/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 
	 * 方法描述：获取所有的操作员
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 *             List<SysUser>
	 * @author lzshuai
	 * @date 2017-7-20 上午10:56:27
	 */
	@RequestMapping(value = "/cwZtBooks/getAllUserList", method = RequestMethod.POST)
	public List<SysUser> getAllUserList(@RequestBody SysUser sysUser) throws Exception;

	/**
	 * 方法描述： 获取帐套列表
	 * 
	 * @param cwZtBooks
	 * @return
	 * @throws Exception
	 *             List<CwZtBooks>
	 * @author Javelin
	 * @date 2017-7-20 上午9:43:05
	 */
	@RequestMapping(value = "/cwZtBooks/getCwZtBooksList", method = RequestMethod.POST)
	public List<CwZtBooks> getCwZtBooksList(@RequestBody CwZtBooks cwZtBooks) throws Exception;

	/**
	 * lzs
	 * 
	 * @param cwZtBooks
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwZtBooks/getByFinBooks", method = RequestMethod.POST)
	public CwZtBooks getByFinBooks(@RequestBody CwZtBooks cwZtBooks) throws Exception;

	/**
	 * lzs
	 * 查看账套是否显示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwZtBooks/getztShowOrHide", method = RequestMethod.POST) 
	public String getztShowOrHide() throws Exception;

	/**
	 * 
	 * 方法描述： 复制账套的功能
	 * 
	 * @param cwZtBooks
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-7 下午2:56:50
	 */
	@RequestMapping(value = "/cwZtBooks/doCopyAddBooks", method = RequestMethod.POST)
	public R doCopyAddBooks(@RequestBody CwZtBooks cwZtBooks,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 复制账套时使用这个，获取个别账套信息
	 * 
	 * @param cwZtBooks
	 * @return
	 * @throws Exception
	 *             CwZtBooks
	 * @author lzshuai
	 * @date 2017-8-9 上午10:52:50
	 */
	@RequestMapping(value = "/cwZtBooks/getCopyZtById", method = RequestMethod.POST)
	public CwZtBooks getCopyZtById(@RequestBody CwZtBooks cwZtBooks) throws Exception;
}
