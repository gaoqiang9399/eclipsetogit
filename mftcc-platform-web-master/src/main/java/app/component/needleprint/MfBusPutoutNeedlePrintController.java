package app.component.needleprint;

import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.MathExtend;
import com.core.struts.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/mfBusPutoutNeedlePrint")
public class MfBusPutoutNeedlePrintController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
    @Autowired
    private MfCusBankAccManageFeign mfCusBankAccManageFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;
    @RequestMapping("/getLoanInfo")
    public String  getLoanInfo(Model model,String fincId, String cusNo, String appId) throws Exception {
        ActionContext.initialize(request, response);
        MfBusFincApp mfBusFincApp=new MfBusFincApp();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        MfBusApply mfBusApply=new MfBusApply();
        String putoutYear="";
        String putoutMonth="";
        String putoutDay="";
        String endYear="";
        String endMonth="";
        String endDay="";
        String upperPutoutAmt="";
        Double putoutAmt=0.00;
        String [] putoutAmtArr=new String[11];
        String accordContractPers="";
        String repayManPers="";
        String accordContractCorp="";
        String repayManCorp="";
        String other="";
        String repayType="";
        String idType="";
        try {
            if(fincId!=null&&!"".equals(fincId)){
                mfBusFincApp.setFincId(fincId);
                mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                String putoutDate=mfBusFincApp.getPutoutDate();
                String endDate="";
                String cusType="";
                CodeUtils cu = new CodeUtils();
                repayType=cu.getMapByKeyName("REPAY_TYPE").get(mfBusFincApp.getRepayType());
                mfBusFincApp.setRepayType(repayType);
                upperPutoutAmt=MathExtend.numberToChinese(mfBusFincApp.getPutoutAmt());
                putoutAmt=mfBusFincApp.getPutoutAmt();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");// 格式化设置
                String putoutAmtNum = decimalFormat.format(putoutAmt);
                putoutAmtNum = putoutAmtNum.replace(".", "");
                if(putoutAmtNum.length()<12){
                int c=11-putoutAmtNum.length();
                for(int j=0;j<c;j++){
                    putoutAmtArr[j]="\\\\";
                }
                for( int i=0;i<putoutAmtNum.length();i++){
                    putoutAmtArr[i+c]=String.valueOf(putoutAmtNum.charAt(i));
                }
                }
                if(putoutDate!=null&&!"".equals(putoutDate)){
                    putoutYear=putoutDate.substring(0,4);
                    putoutMonth=putoutDate.substring(4,6);
                    putoutDay=putoutDate.substring(6,8);
                    }
                    if("0".equals(mfBusFincApp.getExpFlag())){
                      endDate=mfBusFincApp.getIntstEndDateShow();
                        endYear=endDate.substring(0,4);
                        endMonth=endDate.substring(4,6);
                        endDay=endDate.substring(6,8);
                    }else if("1".equals(mfBusFincApp.getExpFlag())){
                        endDate=mfBusFincApp.getIntstEndDateExp();
                        endYear=endDate.substring(0,4);
                        endMonth=endDate.substring(4,6);
                        endDay=endDate.substring(6,8);
                    }else {
                    }

               if(cusNo!=null&&!"".equals(cusNo)) {
                   mfCusCustomer.setCusNo(cusNo);
                   mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                   idType=cu.getMapByKeyName("ID_TYPE").get(mfCusCustomer.getIdType());
                   mfCusCustomer.setIdType(idType);
               }
               cusType=mfCusCustomer.getCusType();
                if("102".equals(cusType)){
                    accordContractCorp="√";
                    repayManCorp="√";
                }else  if ("202".equals(cusType)){
                    accordContractPers="√";
                    repayManPers="√";
                }else{
                    other="√";
                }
                mfBusFincApp.setIdNum(mfCusCustomer.getIdNum());
                //还款账户
                MfCusBankAccManage merchantBank = new MfCusBankAccManage();
                if(mfBusFincApp.getRepayAccId()!=null&&!"".equals(mfBusFincApp.getRepayAccId())){
                    merchantBank.setId(mfBusFincApp.getRepayAccId());
                    MfCusBankAccManage  mfCusBankAccManage = mfCusBankAccManageFeign.getById(merchantBank);
                    mfBusFincApp.setRepayAccount(mfCusBankAccManage.getAccountNo());
                    mfBusFincApp.setCollectAccName(mfCusBankAccManage.getAccountName());
                    mfBusFincApp.setCollectBank(mfCusBankAccManage.getBank());
                }
                if(appId!=null&&!"".equals(appId)){
                    mfBusApply.setAppId(appId);
                    mfBusApply=mfBusApplyFeign.getById(mfBusApply);
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("putoutYear",putoutYear);
        model.addAttribute("putoutMonth",putoutMonth);
        model.addAttribute("putoutDay", putoutDay);
        model.addAttribute("endYear",endYear);
        model.addAttribute("endMonth",endMonth);
        model.addAttribute("endDay", endDay);
        model.addAttribute("upperPutoutAmt", upperPutoutAmt);
        model.addAttribute("putoutAmtArr", putoutAmtArr);
        model.addAttribute("accordContractCorp", accordContractCorp);
        model.addAttribute("accordContractPers", accordContractPers);
        model.addAttribute("repayManCorp", repayManCorp);
        model.addAttribute("repayManPers", repayManPers);
        model.addAttribute("other", other);
        model.addAttribute("mfBusFincApp",mfBusFincApp);
        model.addAttribute("mfCusCustomer",mfCusCustomer);
        model.addAttribute("mfBusApply", mfBusApply);
        model.addAttribute("query", "");
        return "/component/needleprint/ZMLoan";
    }

}
