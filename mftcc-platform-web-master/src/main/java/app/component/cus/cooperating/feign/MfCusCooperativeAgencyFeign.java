package app.component.cus.cooperating.feign;

import app.component.cus.entity.MfCusCustomer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.cooperating.entity.MfCusCooperativeAgency;
import app.util.toolkit.Ipage;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Title: MfCusCooperativeAgencyBo.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 02 10:07:33 CST 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface MfCusCooperativeAgencyFeign {

    @RequestMapping(value = "/mfCusCooperativeAgency/insert")
    public void insert(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/delete")
    public void delete(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/update")
    public void update(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/getById")
    public MfCusCooperativeAgency getById(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/getBylegalIdNum")
    public MfCusCooperativeAgency getBylegalIdNum(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/getBySocialCreditCode")
    public MfCusCooperativeAgency getBySocialCreditCode(@RequestBody MfCusCooperativeAgency mfCusCooperativeAgency) throws ServiceException;

    @RequestMapping(value = "/mfCusCooperativeAgency/getCusInfoByName")
    MfCusCooperativeAgency getCusInfoByName(@RequestParam(name ="orgaName")String orgaName) throws ServiceException;
}
