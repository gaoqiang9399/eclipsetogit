package cn.mftcc.function.SMS;

import java.util.Map;
import cn.mftcc.function.bean.MfSysMsgInfo;

public interface ISMS {
	
	/**
	 * 因为批量和非批量保存在不同的表结构中所以需要不同的发送方法
	 */
	public MfSysMsgInfo sendSMS(MfSysMsgInfo mfSysMsgInfo,Map<String,String>map);
}
