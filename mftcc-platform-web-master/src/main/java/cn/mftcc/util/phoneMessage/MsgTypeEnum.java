package cn.mftcc.util.phoneMessage;

public enum MsgTypeEnum{
	/**
	 *验证码短信
	 */
	DX_YZM("01","YZMDX","验证码短信"),
	/**
	 *提醒短信
	 */
	DX_TX("02","TXDX","提醒短信"),
	/**
	 *营销短信
	 */
	DX_YX("03","YXDX","营销短信");
	
	private String msgTypeNo;// 供应商编号
	private String msgTypeCode;// 供应商名称
	private String msgTypeName;// 供应商名称
	
	private MsgTypeEnum(String msgTypeNo, String msgTypeCode,String msgTypeName) {
		this.msgTypeNo = msgTypeNo;
		this.msgTypeCode = msgTypeCode;
		this.msgTypeName = msgTypeName;
	}

	public String getMsgTypeNo() {
		return msgTypeNo;
	}

	public void setMsgTypeNo(String msgTypeNo) {
		this.msgTypeNo = msgTypeNo;
	}

	public String getMsgTypeCode() {
		return msgTypeCode;
	}

	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}

	public String getMsgTypeName() {
		return msgTypeName;
	}

	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}
	
	public static  MsgTypeEnum getMsgTypeEnum(String msgTypeCode){
		for(MsgTypeEnum e :MsgTypeEnum.values() )
		{
			if(e.getMsgTypeCode().equals(msgTypeCode))
			{
				return e;
			}
		}
		return null;
	}
}