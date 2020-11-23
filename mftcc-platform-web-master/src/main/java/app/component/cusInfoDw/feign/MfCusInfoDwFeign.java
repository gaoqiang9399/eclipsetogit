package app.component.cusInfoDw.feign;

import app.component.cusInfoDw.entity.MfCusInfoDw;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@FeignClient("mftcc-platform-factor")
public interface MfCusInfoDwFeign {

    /**
     *
     * 方法描述： 根据客户号选择合同
     * @param MfBusPact
     * @return
     * @throws Exception
     * Ipage
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value ="/mfCusInfoDw/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    /**
     *
     * 方法描述： 查询客户信息生成txt文件
     * @param MfCusInfoDw
     * @return
     * @throws Exception
     * Map<String,Object>
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value ="/mfCusInfoDw/queryAndDw")
    public MfCusInfoDw queryAndDw(@RequestBody MfCusInfoDw mfCusInfoDw) throws Exception;

    /**
     *
     * 方法描述： 客户信息下载列表
     * @param MfCusInfoDw
     * @return
     * @throws Exception
     * Ipage
     * @author cd
     * @date 2019-4-9 下午19:00:30
     */
    @RequestMapping(value ="/mfCusInfoDw/findByPageList")
    public Ipage findByPageList(@RequestBody Ipage ipage) throws Exception;
}
