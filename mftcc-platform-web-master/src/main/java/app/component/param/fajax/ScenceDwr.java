package app.component.param.fajax;

import app.base.SpringUtil;
import app.component.common.BizPubParm;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;


public class ScenceDwr {
	public String ifIsUsed(String scNo){
		try {
			ScenceFeign scenceFeign = (ScenceFeign) SpringUtil.getBean("scenceFeign");
			Scence scence = new Scence();
			scence.setScNo(scNo);
			
				scence = scenceFeign.getById(scence);
			
			if(BizPubParm.USE_FLAG_1.equals(scence.getUseFlag())){
				return "failed";
			}else{
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
}
