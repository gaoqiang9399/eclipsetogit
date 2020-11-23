package app.component.stamp.feign;

import app.base.ServiceException;
import app.component.stamp.entity.MfStampBaseInfo;
import app.component.stamp.entity.MfStampPact;
import app.component.stamp.entity.MfStampSealPact;
import app.component.stamp.entity.MfStampTemplate;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfStampPactFeign {
    @RequestMapping(value = "/mfStampPact/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfStampPact/insertStartProcess")
    public MfStampPact insertStartProcess(@RequestBody Map<String,Object> dataMap) throws ServiceException;
    @RequestMapping(value = "/mfStampPact/findAllList")
    public List<MfStampPact> findAllList(@RequestBody MfStampPact mfStampPact) throws ServiceException;

    @RequestMapping("/mfStampPact/getById")
    public MfStampPact getById(@RequestBody MfStampPact mfStampPact) throws Exception;

    @RequestMapping(value = "/mfStampPact/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id")  String id, @RequestParam("opinionType")  String opinionType, @RequestParam("transition")  String transition, @RequestParam("opNo")  String opNo, @RequestParam("nextUser") String nextUser, @RequestBody  Map<String, Object> dataMap) throws Exception;
    @RequestMapping("/mfStampPact/getStampBaseList")
    public Ipage getStampBaseList(@RequestBody Ipage ipage) throws Exception;
    @RequestMapping("/mfStampPact/getStampBaseListNew")
    public Ipage getStampBaseListNew(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfStampPact/getStampBaseInfoById")
    public MfStampBaseInfo getStampBaseInfoById(@RequestBody MfStampBaseInfo mfStampBaseInfo) throws Exception;
    @RequestMapping("/mfStampPact/initData")
    public MfStampPact initData(@RequestBody String  stampId) throws Exception;
    @RequestMapping("/mfStampPact/getMfStampTemplateList")
    public List<MfStampTemplate> getMfStampTemplateList(@RequestBody MfStampTemplate  mfStampTemplate) throws Exception;

    @RequestMapping(value = "/mfStampPact/findPactListGCDB")
    Ipage findPactListGCDB(@RequestBody Ipage ipage)throws Exception;
    @RequestMapping(value = "/mfStampPact/findByPageStamp")
    Ipage findByPageStamp(@RequestBody Ipage ipage)throws Exception;
}
