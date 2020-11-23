package app.component.invoicemanage.feign;


import app.component.invoicemanage.entity.MfBusCancelInvoicemanage;
import app.component.invoicemanage.entity.MfBusInvoicemanage;
import app.component.pact.entity.MfBusFincAppHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfBusInvoicemanageFeign {

    @RequestMapping("/mfBusInvoicemanage/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/findCustomerByPage")
    public Ipage findCustomerByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/findCusProByPage")
    public Ipage findCusProByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/getRepayHistoryListByCusNo")
    public Ipage getRepayHistoryListByCusNo(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/submitProcess")
    public MfBusInvoicemanage submitProcess(@RequestBody MfBusInvoicemanage mfBusInvoicemanage)throws Exception;

    @RequestMapping("/mfBusInvoicemanage/submitProcessOut")
    public MfBusCancelInvoicemanage submitProcessOut(@RequestBody MfBusCancelInvoicemanage mfBusCancelInvoicemanage)throws Exception;

    @RequestMapping("/mfBusInvoicemanage/insert")
    public void insert(@RequestBody MfBusInvoicemanage mfBusInvoicemanage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/insertOut")
    public void insertOut(@RequestBody MfBusCancelInvoicemanage mfBusCancelInvoicemanage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/getByinvoiceoutId")
    public MfBusCancelInvoicemanage getByinvoiceoutId(@RequestBody MfBusCancelInvoicemanage mfBusCancelInvoicemanage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/getById")
    public MfBusInvoicemanage getById(@RequestBody MfBusInvoicemanage mfBusInvoicemanage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> formDataMap);

    @RequestMapping("/mfBusInvoicemanage/doCommitOut")
    public Result doCommitOut(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> formDataMap);

    @RequestMapping("/mfBusInvoicemanage/delete")
    public void delete(@RequestBody MfBusInvoicemanage mfBusInvoicemanage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/update")
    public void update(@RequestBody MfBusInvoicemanage mfBusInvoicemanage) throws Exception;

    //获取贷后列表
    @RequestMapping(value = "/mfBusInvoicemanage/findFincByPage")
    public Ipage findFincByPage(@RequestBody Ipage ipage) throws Exception;


    //获取还款计划
    @RequestMapping("/mfBusInvoicemanage/getRepayPlan")
    public Ipage getRepayPlan(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfBusInvoicemanage/getFincHisByFincId")
    List<MfBusFincAppHis> getFincHisByFincId(@RequestBody MfBusFincAppHis mfBusFincAppHis)throws Exception;


    @RequestMapping("/mfBusInvoicemanage/getInvoiceList")
    List<MfBusInvoicemanage> getInvoiceList()throws Exception;
}
