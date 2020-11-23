package app.component.msgconf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.msgconf.entity.CuslendWarning;
import app.util.toolkit.Ipage;

/**
 * Title: CuslendWarningBo.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 13 10:44:08 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface CuslendWarningFeign {

    @RequestMapping(value = "/cuslendWarning/insert")
    public void insert(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/delete")
    public void delete(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/update")
    public void update(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/updateFlag")
    public int updateFlag(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/getById")
    public CuslendWarning getById(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/cuslendWarning/getAll")
    public List<CuslendWarning> getAll(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    /**
     * 根据id 和 flag 查询
     *
     * @param cuslendWarning
     * @return
     * @throws ServiceException
     * @author zhang_dlei
     */
    @RequestMapping(value = "/cuslendWarning/getByIdAndFlag")
    public CuslendWarning getByIdAndFlag(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;

    /**
     * 方法描述： 根据条件获取短信模板
     *
     * @param cuslendWarning
     * @return
     * @throws ServiceException List<CuslendWarning>
     * @author lzshuai
     * @date 2017-11-28 下午1:49:00
     */
    @RequestMapping(value = "/cuslendWarning/geMsgPlateList")
    public List<CuslendWarning> geMsgPlateList(@RequestBody CuslendWarning cuslendWarning) throws Exception;

    /**
     * 方法描述： 获取到期提醒集合
     *
     * @param cuslendWarning
     * @return
     * @throws ServiceException List<CuslendWarning>
     * @author ldy
     * @date 2017-11-28 下午1:49:00
     */
    @RequestMapping(value = "/cuslendWarning/getDueWarningPage")
    public Ipage getDueWarningPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/cuslendWarning/getDueWarningDetailPage")
    public CuslendWarning getDueWarningDetailPage(@RequestBody CuslendWarning cuslendWarning) throws ServiceException;
}
