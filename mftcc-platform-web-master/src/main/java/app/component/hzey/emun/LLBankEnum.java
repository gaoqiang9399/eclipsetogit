package app.component.hzey.emun;

public enum LLBankEnum {
	/**
	 *"农业银行","01030000"
	 */
	NONG_YE("农业银行","01030000"),
	/**
	 *"交通银行","03010000"
	 */
	JIAO_TONG("交通银行","03010000"),
	/**
	 *"工商银行","01020000"
	 */
	GONG_SHANG("工商银行","01020000"),
	/**
	 *"邮储银行","01000000"
	 */
	YOU_ZHENG("邮储银行","01000000"),
	/**
	 *"浦发银行","03100000"
	 */
	PU_FA("浦发银行","03100000"),
	/**
	 *"广发银行","03060000"
	 */
	GUANG_FA("广发银行","03060000"),
	/**
	 *"平安银行","03070000"
	 */
	PING_AN("平安银行","03070000"),
	/**
	 *"招商银行","03080000"
	 */
	ZHAO_SHANG("招商银行","03080000"),
	/**
	 *"民生银行","03050000"
	 */
	MIN_SHENG("民生银行","03050000"),
	/**
	 *"中国银行","01040000"
	 */
	ZHONG_GUO("中国银行","01040000"),
	/**
	 *"建设银行","01050000"
	 */
	JIAN_SHE("建设银行","01050000"),
	/**
	 *"光大银行","03030000"
	 */
	GUANG_DA("光大银行","03030000"),
	/**
	 *"兴业银行","03090000"
	 */
	XING_YE("兴业银行","03090000"),
	/**
	 *"中信银行","03020000"
	 */
	ZHONG_XIN("中信银行","03020000"),
	/**
	 *"华夏银行","03040000"
	 */
	HUA_XIA("华夏银行","03040000"),
	/**
	 *"杭州银行","04233310"
	 */
	HANG_ZHOU("杭州银行","04233310"),
	/**
	 *"北京银行","04031000"
	 */
	BEI_JING("北京银行","04031000"),
	/**
	 *"浙商银行","03160000"
	 */
	ZHE_SHANG("浙商银行","03160000"),
	/**
	 *"上海银行","04012900"
	 */
	SHANG_HAI("上海银行","04012900"),
	/**
	 *"广州银行","64135810"
	 */
	GUANG_ZHOU("广州银行","64135810"),
	/**
	 *"深圳农村商业银行","14045840"
	 */
	SHEN_ZHEN_NONG_CUN_SHANG_YE("深圳农村商业银行","14045840"),
	/**
	 *"宁波银行","04083320"
	 */
	NING_BO("宁波银行","04083320"),
	/**
	 *"甘肃省农村信用社联合社","14538200"
	 */
	GAN_SU_SHENG_NONG_CUN_XIN_YONG_SHE_LIAN_HE_SHE("甘肃省农村信用社联合社","14538200"),
	/**
	 *"江苏银行","05083000"
	 */
	JIANG_SU("江苏银行","05083000"),
	/**
	 *"江苏长江商业银行","04933120"
	 */
	JIANG_SU_CHANG_JIANG_SHANG_YE("江苏长江商业银行","04933120"),
	/**
	 *"黑龙江省农村信用社联合社","14572600"
	 */
	HEI_LONG_JIANG_SHENG_NONG_CUN_XIN_YONG_SHE_LIAN_HE_SHE("黑龙江省农村信用社联合社","14572600"),
	/**
	 *"广东南粤银行","64895910"
	 */
	GUANG_DONG_NAN_YUE("广东南粤银行","64895910"),
	/**
	 *"东莞银行","04256020"
	 */
	DONG_GUAN("东莞银行","04256020"),
	/**
	 *"武汉农村商业银行","14595210"
	 */
	WU_HAN_NONG_CUN_SHANG_YE("武汉农村商业银行","14595210");
	
	private String name;
	private String code;
	
	private LLBankEnum( String name , String code ){
        this.name = name ;
        this.code = code ;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
