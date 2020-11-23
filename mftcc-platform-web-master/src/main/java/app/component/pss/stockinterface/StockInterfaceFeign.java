package app.component.pss.stockinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyBillDetail;
import app.component.pss.sales.entity.PssSaleBill;
import app.component.pss.sales.entity.PssSaleBillDetail;
import app.component.pss.stock.entity.PssAlloTransDetailBill;
import app.component.pss.stock.entity.PssCostAdjustBill;
import app.component.pss.stock.entity.PssCostAdjustDetailBill;
import app.component.pss.stock.entity.PssOtherStockInBill;
import app.component.pss.stock.entity.PssOtherStockInDetailBill;
import app.component.pss.stock.entity.PssOtherStockOutBill;
import app.component.pss.stock.entity.PssOtherStockOutDetailBill;
import app.component.pss.stock.entity.PssStoreStock;
import app.component.pss.stock.entity.PssStoreStockCheck;
import app.component.pss.stock.entity.PssStoreStockDetail;

@FeignClient("mftcc-platform-factor")
public interface StockInterfaceFeign {
	
	/**
	 * 方法描述：调拨单库存校验
	 * @param
	 * list:调拨单明细数据
	 * chkMark 1:出库库存（审核） 0：入库库存（反审核）
	 */

	@RequestMapping(value = "/stockInterface/doCheckStockEnough")
	public List<Map<String, String>> doCheckStockEnough(@RequestBody List<PssAlloTransDetailBill> list,@RequestParam("chkMark")  String chkMark) throws Exception;
	
	/**
	 * 方法描述：调拨单-调拨审核
	 * @return Map<String, String>:code:N-失败;Y-成功; msg:返回消息
	 * @param 调出库存 调入库存 调出库存明细 调入库存明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-8-16 上午10:56:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPATB")
	public Map<String, String> doStockStoreByPATB(PssStoreStock pssStoreStockOut, PssStoreStock pssStoreStockIn, 
@RequestParam("pssStoreStockDetailOut") 			PssStoreStockDetail pssStoreStockDetailOut,@RequestParam("pssStoreStockDetailIn")  PssStoreStockDetail pssStoreStockDetailIn) throws Exception;
	
	/**
	 * 方法描述：调拨单-调拨反审核
	 * @return Map<String, String>:code:N-失败;Y-成功; msg:返回消息
	 * @param 调拨单明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-8-17 下午01:06:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPATB")
	public Map<String, String> doStockStoreByPATB(@RequestBody List<PssAlloTransDetailBill> ptdbList) throws Exception;
	/**
	 * 方法描述：库存校验
	 * @param <T>
	 * @param
	 * list:单据明细数据
	 * map:单据商品、仓库、数量  默认值（goodsId-getGoodsId;storehouseId-getStorehouseId;quantity-getQuantity）
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-05 下午02:56:06
	 */

	@RequestMapping(value = "/stockInterface/doCheckStockEnough")
	public <T> List<Map<String, String>> doCheckStockEnough(@RequestBody List<T> list,@RequestParam("map")  Map<String, String> map) throws Exception;
	/**
	 * 方法描述：购货单审核
	 * @param 购货单、购货单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-05 下午3:56:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPBB")
	public void doStockStoreByPBB(@RequestBody PssBuyBill pssBuyBill, List<PssBuyBillDetail> pbbdList) throws Exception;
	/**
	 * 方法描述：购货单反审核
	 * @param 购货单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-06 上午9:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPBB")
	public void doStockStoreByPBB(@RequestBody List<PssBuyBillDetail> pbbdList) throws Exception;
	/**
	 * 方法描述：销货单审核
	 * @param 销货单、销货单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-06 上午9:26:06
	 */

	@RequestMapping(value = "/stockInterface/doStockStoreByPSB")
	public void doStockStoreByPSB(@RequestBody PssSaleBill pssSaleBill, List<PssSaleBillDetail> psbdList) throws Exception;
	/**
	 * 方法描述：销货单反审核
	 * @param 销货单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-06 上午9:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPSB")
	public void doStockStoreByPSB(@RequestBody List<PssSaleBillDetail> psbdList) throws Exception;
	/**
	 * 方法描述：其他入库单审核
	 * @param 其他入库单、其他入库单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-06 下午02:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPOSIB")
	public void doStockStoreByPOSIB(@RequestBody PssOtherStockInBill pssOtherStockInBill, List<PssOtherStockInDetailBill> list) throws Exception;
	/**
	 * 方法描述：其他入库单反审核
	 * @param 其他入库单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-06 下午02:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPOSIB")
	public void doStockStoreByPOSIB(@RequestBody List<PssOtherStockInDetailBill> list) throws Exception;
	/**
	 * 方法描述：其他出库单审核
	 * @param 其他出库单、其他出库单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-07 下午05:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPOSOB")
	public void doStockStoreByPOSOB(@RequestBody PssOtherStockOutBill pssOtherStockOutBill, List<PssOtherStockOutDetailBill> list) throws Exception;
	/**
	 * 方法描述：其他出库单反审核
	 * @param 其他出库单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-07 下午05:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPOSOB")
	public void doStockStoreByPOSOB(@RequestBody List<PssOtherStockOutDetailBill> list) throws Exception;
	/**
	 * 方法描述：成本调整（新增、修改）
	 * @param 成本调整单、成本调整单明细list
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-18 上午09:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPCAB")
	public void doStockStoreByPCAB(@RequestBody PssCostAdjustBill pssCostAdjustBill, List<PssCostAdjustDetailBill> list) throws Exception;
	/**
	 * 方法描述：成本调整（删除）
	 * @param 成本调整单
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-09-09-18 上午09:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreByPCAB")
	public void doStockStoreByPCAB(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	/**
	 * 方法描述：公共库存校验——最终版
	 * @param 出入库明细  optFlag:1-正向（审核）；0-反向（反审核）
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-12-28 下午02:06:06
	 */
	@RequestMapping(value = "/stockInterface/doCheckStoreStock")
	public PssStoreStockCheck doCheckStoreStock(@RequestBody PssStoreStockDetail pssStoreStockDetail,@RequestParam("optFlag")  String optFlag) throws Exception;
	/**
	 * 方法描述：入库接口-用于所有入库操作
	 * @param 入库库存明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-10-12 下午06:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreInChk")
	public PssStoreStockCheck doStockStoreInChk(@RequestBody PssStoreStockDetail pssStoreStockDetailIn) throws Exception;
	/**
	 * 方法描述：入库接口（反向）-用于所有入库后（反向）操作
	 * @param 入库库存明细（业务操作明细ID必须赋值）
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-10-13 上午11:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreInReChk")
	public PssStoreStockCheck doStockStoreInReChk(@RequestBody PssStoreStockDetail pssStoreStockDetailIn) throws Exception;
	/**
	 * 方法描述：出库接口-用于所有出库操作
	 * @param 出库库存明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-10-12 下午06:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreOutChk")
	public PssStoreStockCheck doStockStoreOutChk(@RequestBody PssStoreStockDetail pssStoreStockDetailOut) throws Exception;
	/**
	 * 方法描述：出库接口（反向）-用于所有出库后（反向）操作
	 * @param 出库库存明细（业务操作明细ID必须赋值）
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-10-13 下午01:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreOutReChk")
	public PssStoreStockCheck doStockStoreOutReChk(@RequestBody PssStoreStockDetail pssStoreStockDetailOut) throws Exception;
	/**
	 * 方法描述：锁定接口-用于销货订单锁定操作
	 * @param 出库库存明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-11-01 上午11:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreLockChk")
	public PssStoreStockCheck doStockStoreLockChk(@RequestBody PssStoreStockDetail pssStoreStockDetailOut) throws Exception;
	/**
	 * 方法描述：解锁接口-用于销货订单解锁操作
	 * @param 出库库存明细
	 * @throws Exception
	 * @author Hgx
	 * @date 2017-11-01 上午11:26:06
	 */
	@RequestMapping(value = "/stockInterface/doStockStoreDeblockChk")
	public PssStoreStockCheck doStockStoreDeblockChk(@RequestBody PssStoreStockDetail pssStoreStockDetailOut) throws Exception;
}
