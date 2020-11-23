package app.component.accntinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.entity.MfAccntTransfer;
import app.util.toolkit.Ipage;

/**
 * Title: AppInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Mon May 16 20:50:51 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface AccntInterfaceFeign {
	@RequestMapping(value = "/accntInterface/getListPage")
	public List<MfAccntTransfer> getListPage(Ipage ipage, MfAccntTransfer mfAccntTransfer) throws Exception;

	@RequestMapping(value = "/accntInterface/getAccntListForUnRepay")
	public List<MfAccntTransfer> getAccntListForUnRepay(MfAccntTransfer mfAccntTransfer) throws Exception;

	@RequestMapping(value = "/accntInterface/getAccntDetailList")
	public List<MfAccntRepayDetail> getAccntDetailList(MfAccntRepayDetail mfAccntRepayDetail) throws Exception;

	@RequestMapping(value = "/accntInterface/insert")
	public void insert(MfAccntRepayDetail mfAccntRepayDetail) throws Exception;

}
