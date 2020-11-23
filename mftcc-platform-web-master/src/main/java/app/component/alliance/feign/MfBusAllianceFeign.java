package app.component.alliance.feign;

import app.component.alliance.entity.MfBusAlliance;
import app.component.alliance.entity.MfBusAllianceCustomer;
import app.component.alliance.entity.MfBusAllianceTakeupHis;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfAssureInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 20 15:11:16 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAllianceFeign {
	@RequestMapping("/mfBusAlliance/insert")
	public void insert(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAlliance/getById")
	public MfBusAlliance getById(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAlliance/getByName")
	public MfBusAlliance getByName(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAlliance/update")
	public void update(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAllianceCustomer/update")
	public void updateAllianceCustomer(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer) throws Exception;

	@RequestMapping("/mfBusAlliance/delete")
	public void delete(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAlliance/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfBusAlliance/getList")
	public List<MfBusAlliance> getList(@RequestBody MfBusAlliance mfBusAlliance) throws Exception;

	@RequestMapping("/mfBusAllianceCustomer/findAllianceCus")
    public List<Map<String,Object>> findAllianceCus(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer);

	@RequestMapping("/mfBusAllianceCustomer/insert")
	public void insertMfBusAllianceCustomer(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer);

	@RequestMapping("/mfBusAllianceTakeupHis/getList")
	public List<MfBusAllianceTakeupHis> getTakeupList(@RequestBody MfBusAllianceTakeupHis mfBusAllianceTakeupHis);

	@RequestMapping("/mfBusAllianceTakeupHis/insert")
	public void insertMfBusAllianceTakeupHis(@RequestBody MfBusAllianceTakeupHis mfBusAllianceTakeupHis);

	@RequestMapping("/mfBusAllianceCustomer/delete")
	public void deleteCustomer(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer);

    /**
     * 借款人数
     * @param alliance
     * @return
     */
	@RequestMapping("/mfBusAllianceCustomer/getMemberNumByAllianceId")
    public int getLoanMemberNumByAllianceId(@RequestBody MfBusAlliance alliance);

	@RequestMapping("/mfBusAlliance/getTotalApplyDataByAllianceId")
	public Map<String,Object> getTotalApplyDataByAllianceId(@RequestBody MfBusAlliance alliance);

	@RequestMapping("/mfBusAlliance/getFinishedListByPage")
    public Ipage getFinishedListByPage(@RequestBody Ipage ipage);

	@RequestMapping("/mfBusAllianceCustomer/setLeader")
    public void setLeader(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer);

	/**
	 * 验证联保体或者联保体成员是否可删除
	 * @return
	 */
	@RequestMapping("/mfBusAlliance/validateDelete")
    boolean validateDelete(@RequestBody Map<String, Object> paramMap);

	@RequestMapping("/mfBusAlliance/getAllianceByCusNo")
	public MfBusAlliance getAllianceByCusNo(String cusNo) throws Exception;

	@RequestMapping("/mfBusAlliance/getOtherById")
	public Ipage getOtherById(@RequestBody Ipage ipage)throws  Exception;

	@RequestMapping("/mfBusAllianceCustomer/getById")
	public MfBusAllianceCustomer getCustomerById(@RequestBody MfBusAllianceCustomer mfBusAllianceCustomer);


	@RequestMapping("/mfBusAlliance/findCustomerByPage")
	public Ipage findCustomerByPageAjax(Ipage ipage);
}
