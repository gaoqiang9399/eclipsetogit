package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.invoice.entity.CwInvoice;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 发票
 * @author zhangdongyang
 * @date 2020/5/21 16:09
 */
@FeignClient("mftcc-platform-factor")
public interface CwInvoiceFeign {

    /**
     * 根据到账确认id查询发票列表 到帐确认与发票存在一对多关系
     * @author zhangdongyang
     * @param collectId
     * @date 2020/5/21 16:09
     * @return list
     */
    @RequestMapping(value = "/cwInvoice/findListByCollectId/{collectId}",method= RequestMethod.POST)
    public List<CwInvoice> findListByCollectId(@PathVariable("collectId") String collectId) throws ServiceException;

    /**
     * 根据发票id查询发票详细信息 包含发票明细
     * @author zhangdongyang
     * @param cwInvoice
     * @date 2020/5/21 16:11
     * @return app.component.finance.invoice.entity.CwInvoice
     */
    @RequestMapping("/cwInvoice/getById")
    public CwInvoice getById(@RequestBody CwInvoice cwInvoice) throws Exception;

    /**
     * 查询发票打印列表，通过Ajax方式
     * @author zhangdongyang
     * @param ipage
     * @date 2020/6/2 0:10
     * @return app.util.toolkit.Ipage
     */
    @RequestMapping(value = "/cwInvoice/findInvoiceListByAjax")
    public Ipage findInvoiceListByAjax(@RequestBody Ipage ipage) throws Exception;

    /**
     * 根据id获取发票打印的信息
     * @author zhangdongyang
     * @param id
     * @date 2020/6/2 19:51
     * @return net.sf.json.JSONObject
     */
    @RequestMapping("/cwInvoice/getPrintInfoById")
    JSONObject getPrintInfoById(@RequestParam("id") String id);


    /**
     * 根据ids获取发票打印的信息
     * @author zhangdongyang
     * @param ids
     * @date 2020/6/2 19:53
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("/cwInvoice/getPrintInfoByIds")
    JSONArray getPrintInfoByIds(@RequestParam("ids") String ids);

    /**
     * 根据ids获取发票信息
     * @author chenyingying
     * @param ids
     * @date 2020/6/10 11:53
     * @return
     */
    @RequestMapping("/cwInvoice/getInvoiceInfoByIds")
    List<Map<String,String>> getInvoiceInfoByIds(@RequestParam("ids") String ids);


}
