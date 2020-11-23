package app.component.pact.feign;

import app.component.pact.entity.MfBusPactChange;
import app.component.pact.entity.MfBusPactChangeHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfBusPactBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusPactChangeFeign {
    /**
     * 查询列表
     * @param ipage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusPactChange/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws  Exception;

    @RequestMapping(value = "/mfBusPactChange/insert")
    public Map<String, Object> insert(@RequestBody  MfBusPactChange mfBusPactChange) throws  Exception;

    @RequestMapping(value = "/mfBusPactChange/getById")
    public MfBusPactChange getById(@RequestBody  MfBusPactChange mfBusPactChange) throws  Exception;

    @RequestMapping(value = "/mfBusPactChange/getPactList")
    public Ipage getPactList(@RequestBody Ipage ipage) throws  Exception;

    @RequestMapping(value = "/mfBusPactChange/getMfBusPactChangeHisByDESC")
    List<MfBusPactChangeHis> getMfBusPactChangeHisByDESC(@RequestBody  MfBusPactChangeHis mfBusPactChangeHis) throws  Exception;

    @RequestMapping(value = "/mfBusPactChange/processData")
    public MfBusPactChange processDataForPact(@RequestBody MfBusPactChange mfBusPactChange) throws Exception;

    @RequestMapping(value = "/mfBusPactChange/disProcessData")
    public MfBusPactChange disProcessDataForPact(@RequestBody MfBusPactChange mfBusPactChange) throws Exception;

    @RequestMapping(value = "/mfBusPactChange/doCommit")
    Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("changePactId") String changePactId,
                    @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
                    @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser,
                    @RequestBody Map<String, Object> dataMap) throws  Exception;


    @RequestMapping(value = "/mfBusPactChange/onfincRatechang")
    public MfBusPactChange onfincRatechang(@RequestParam("pactId") String pactId,@RequestParam("fincRateChange") Double fincRateChange,@RequestParam("termType") String termType)throws Exception;
}

