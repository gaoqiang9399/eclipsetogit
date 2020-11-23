package app.component.common;




public class BizPubParm {
	
	/**
	 * 产品编号（默认开始的编号，后续的产品编号在此基础上累加）
	 */
	public static final String SEQ_KIND_NO = "1000";
	/**
	 * 业务申请编号序列
	 */
	public static final String SEQ_APP_NO = "APP_PROJECT_APP_NO_SEQ";

	/**
	 * 客户类型CUS_TYPE - 1 对公客户
	 */
	public static final String CUS_TYPE_CORP = "1";

	/**
	 * 客户类型CUS_TYPE - 2 个人客户
	 */
	public static final String CUS_TYPE_PERS = "2";
	/**
	 * 操作员类型OP_NO_TYPE - 1 系统操作员
	 */
	public static final String OP_NO_TYPE1 = "1";
	
	/**
	 * 操作员类型OP_NO_TYPE - 2 渠道操作员
	 */
	public static final String OP_NO_TYPE2 = "2";
	/**
	 * 操作员类型OP_NO_TYPE - 3 资金机构操作员
	 */
	public static final String OP_NO_TYPE3 = "3";
	/**
	 * 操作员类型OP_NO_TYPE - 4 仓储机构操作员
	 */
	public static final String OP_NO_TYPE4 = "4";
	/**
	 * 操作员类型OP_NO_TYPE - 5 核心企业操作员
	 */
	public static final String OP_NO_TYPE5 = "5";
	/**
	 * 资产状态ASSETS_STS - 1 抵债
	 */
	public static final String ASSETS_STS_1 = "1";
	/**
	 * 资产状态ASSETS_STS - 2 已租赁
	 */
	public static final String ASSETS_STS_2 = "2";
	/**
	 * 资产状态ASSETS_STS - 3 已处置
	 */
	public static final String ASSETS_STS_3 = "3";
	/**
	 * 自然人与企业申请人的关系CUS_CORP_REL - 1 股东
	 */
	public static final String CUS_CORP_REL_1 = "1";
	/**
	 * 自然人与企业申请人的关系CUS_CORP_REL - 1 实际控制人
	 */
	public static final String CUS_CORP_REL_2 = "2";
	/**
	 * 自然人与企业申请人的关系CUS_CORP_REL - 1 法人
	 */
	public static final String CUS_CORP_REL_3 = "3";
	/**
	 * 自然人与企业申请人的关系CUS_CORP_REL - 1 其他
	 */
	public static final String CUS_CORP_REL_4 = "4";
    /**
     * CREDIT_STS_TAB - A 授信状态(选项卡)- 全部
     */
    public static final String CREDIT_STS_TAB_A = "A";
    /**
     * CREDIT_STS_TAB - B 授信状态(选项卡)- 申请中
     */
    public static final String CREDIT_STS_TAB_B = "B";
    /**
     * CREDIT_STS_TAB - C 授信状态(选项卡)- 待签约
     */
    public static final String CREDIT_STS_TAB_C = "C";
    /**
     * CREDIT_STS_TAB - D 授信状态(选项卡)- 已授信
     */
    public static final String CREDIT_STS_TAB_D = "D";
    /**
     * CREDIT_STS_TAB - E 授信状态(选项卡)- 已过期
     */
    public static final String CREDIT_STS_TAB_E = "E";
    /**
     * CREDIT_STS_TAB - F 授信状态(选项卡)- 补充资料
     */
    public static final String CREDIT_STS_TAB_F = "F";
    /**
     * 授信合同状态0 未提交
     */
    public static final String CREDIT_PACT_STS_0="0";
    /**
     * 授信合同状态1已签约
     */
    public static final String CREDIT_PACT_STS_1="1";
    /**
     * 授信合同状态2审批中
     */
    public static final String CREDIT_PACT_STS_2="2";
    /**
     * 授信合同状态3审批成功
     */
    public static final String CREDIT_PACT_STS_3="3";
    /**
     * 授信合同状态4否决
     */
    public static final String CREDIT_PACT_STS_4="4";
    /**
     * 授信合同状态6已冻结
     */
    public static final String CREDIT_PACT_STS_6="6";
    /**
     * 授信合同状态7已失效
     */
    public static final String CREDIT_PACT_STS_7="7";
    /**
     * 授信合同状态8已终止
     */
    public static final String CREDIT_PACT_STS_8="8";
	/**
	 * 评级提示触发等级 0-无风险
	 */
	public static final String EVAL_TRIGGER_LEVEL_0 = "0";
	/**
	 * 评级提示触发等级 1-低风险
	 */
	public static final String EVAL_TRIGGER_LEVEL_1 = "1";
	/**
	 * 评级提示触发等级 2-高风险
	 */
	public static final String EVAL_TRIGGER_LEVEL_2 = "2";
	/**
	 * 评级提示触发等级描述 0-无风险
	 */
	public static final String EVAL_TRIGGER_DESC_0 = "无风险";
	/**
	 * 评级提示触发等级描述 1-低风险
	 */
	public static final String EVAL_TRIGGER_DESC_1 = "低风险";
	/**
	 * 评级提示触发等级描述 2-高风险
	 */
	public static final String EVAL_TRIGGER_DESC_2 = "高风险";
    /**
     * 	冻结/解冻类型 1 冻结
     */
    public static final String OPERA_TYPE_1="1";
    /**
     * 	冻结/解冻类型 2 解冻
     */
    public static final String OPERA_TYPE_2="2";
    /**
     * CREDIT_STS 0 授信状态- 未完善
     */
    public static final String CREDIT_STS_0 = "0";
    /**
     * CREDIT_STS 1 授信状态- 未提交
     */
    public static final String CREDIT_STS_1 = "1";
    /**
     * CREDIT_STS 2 授信状态- 审批中
     */
    public static final String CREDIT_STS_2 = "2";
    /**
     * CREDIT_STS 3 授信状态- 审批通过
     */
    public static final String CREDIT_STS_3 = "3";
    /**
     * CREDIT_STS 4 授信状态- 已否决
     */
    public static final String CREDIT_STS_4 = "4";
    /**
     * CREDIT_STS 5 授信状态- 已完结
     */
    public static final String CREDIT_STS_5 = "5";
    /**
     * CREDIT_STS 6 授信状态- 已冻结
     */
    public static final String CREDIT_STS_6 = "6";
    /**
     * 授信状态7已失效
     */
    public static final String CREDIT_STS_7="7";
    /**
     * 授信状态8已终止
     */
    public static final String CREDIT_STS_8="8";
	/**
	 *  公私PUB_NOT - 01 对公
	 */
	public static final String PUB_NOT_01 = "01";
	
	/**
	 *  公私PUB_NOT - 02 对私
	 */
	public static final String PUB_NOT_02 = "02";
    /**
     * 客户配置模板时所属产品编号 CUS_TEMPLATE_KIND_NO - cus_template 客户模板
     */
    public static final String CUS_TEMPLATE_KIND_NO = "cus_template";
	
	/**
	 * JOB_STS - 1 在职状态-正式
	 */
	public static final String JOB_STS1 = "1";
	/**
	 * JOB_STS - 2 在职状态-实习
	 */
	public static final String JOB_STS2 = "2";
	/**
	 * JOB_STS - 3 在职状态-离职
	 */
	public static final String JOB_STS3 = "3";
	/**
	 * JOB_STS - 4 在职状态-试用
	 */
	public static final String JOB_STS4 = "4";
	/**
	 * SHOW_TYPE - 1 展示类型-新增
	 */
	public static final String SHOW_TYPE1 = "1";
	/**
	 * SHOW_TYPE - 2  展示类型-详情
	 */
	public static final String SHOW_TYPE2 = "2";
	/**
	 * SHOW_TYPE - 3  展示类型-列表
	 */
	public static final String SHOW_TYPE3 = "3";
	/**
	 * FORM_TYPE - 1 表单类型-人事档案
	 */
	public static final String FORM_TYPE1 = "1";
	/**
	 * FORM_TYPE - 2  表单类型-入职申请
	 */
	public static final String FORM_TYPE2 = "2";
	/**
	 * OP_STS - 1 获取满足调薪调岗的正式员工
	 */
	public static final String OP_STS1 = "1";
	/**
	 * OP_STS - 2 获取满足入职的实习和试用员工
	 */
	public static final String OP_STS2 = "2";
	/**
	 * OP_STS - 3 获取满足离职条件的正式员工
	 */
	public static final String OP_STS3 = "3";
	/**
	 * OP_STS - 4 获取满足全职转兼职的员工
	 */
	public static final String OP_STS4 = "4";
	/**
	 * EMPLOYEE_TYPE - 1 员工类型-全职
	 */
	public static final String EMPLOYEE_TYPE1 = "1";
	/**
	 * EMPLOYEE_TYPE - 2  员工类型-兼职
	 */
	public static final String EMPLOYEE_TYPE2 = "2";
	/**
	 * ADJUSTMENT_TYPE - 1 职务/职级调整类型-调薪
	 */
	public static final String ADJUSTMENT_TYPE1 = "1";
	/**
	 * ADJUSTMENT_TYPE - 2 职务/职级调整类型-调岗
	 */
	public static final String ADJUSTMENT_TYPE2 = "2";
	/**
	 * ADJUSTMENT_TYPE - 3 职务/职级调整类型-晋职
	 */
	public static final String ADJUSTMENT_TYPE3 = "3";
	/**
	 * ADJUSTMENT_TYPE - 4 职务/职级调整类型-降职
	 */
	public static final String ADJUSTMENT_TYPE4 = "4";
	/**
	 * ADJUSTMENT_TYPE - 5 职务/职级调整类型-晋级
	 */
	public static final String ADJUSTMENT_TYPE5 = "5";
	/**
	 * ADJUSTMENT_TYPE - 6职务/职级调整类型-降级
	 */
	public static final String ADJUSTMENT_TYPE6 = "6";
	/**
	 * 三方 TONG_DUN-同盾
	 */
	public static final String THREE_PARTY_TONG_DUN = "TONG_DUN";
	/**
	 * 三方 TD_DATA-同盾贷前保镖
	 */
	public static final String THREE_PARTY_TD_DATA = "TD_DATA";
	
	/**
	 * 三方 MG-蜜罐（海卡）
	 */
	public static final String THREE_PARTY_MG = "MG";
	/**
	 * 三方 FH_SX-法海-个人失信公告（海卡）
	 */
	public static final String THREE_PARTY_FH_SX = "FH_SX";
	
	/**
	 * 三方 FH_ZX-法海-个人执行公告（海卡）
	 */
	public static final String THREE_PARTY_FH_ZX = "FH_ZX";
	/**
	 * 三方 TTYY-天天有余反欺诈
	 */
	public static final String THREE_PARTY_TTYY = "TTYY";
	/**
	 * 三方 BR-百融-手机号在网时长
	 */
	public static final String THREE_PARTY_BR_DURATION = "BR_DURATION";
	/**
	 * 三方 BR-百融-贷前管理法院被执行人特殊名单借贷意向
	 */
	public static final String THREE_PARTY_BR_PRE_LOAN1 = "BR_STRATEGY_ID_1";
	/**
	 * 三方 BR-百融-贷前管理个人资质客制化
	 */
	public static final String THREE_PARTY_BR_PRE_LOAN2 = "BR_STRATEGY_ID_2";
	/**
	 * 三方 BR-百融
	 */
	public static final String THREE_PARTY_BR_DATA = "BR_DATA";
	/**
	 * 三方 BR-聚信立-蜜罐
	 */
	public static final String THREE_PARTY_JXL_MG = "JXL_MG";
	
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/蜜蜂
	 */
	public static final String THIRD_SERVICE_TYPEHONEYBEE = "honeybee";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/蜜罐
	 */
	public static final String THIRD_SERVICE_TYPEHONEYPOT = "honeypot";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/企业工商综合信息
	 */
	public static final String THIRD_SERVICE_TYPE1 = "1";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/贷款轨迹
	 */
	public static final String THIRD_SERVICE_TYPE111 = "111";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/个人身份认证
	 */
	public static final String THIRD_SERVICE_TYPE15 = "15";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/个人涉诉案件
	 */
	public static final String THIRD_SERVICE_TYPE23 = "23";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/个人裁判文书解析摘要
	 */
	public static final String THIRD_SERVICE_TYPE32 = "32";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/企业涉诉案件
	 */
	public static final String THIRD_SERVICE_TYPE53 = "53";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/个人风险信息
	 */
	public static final String THIRD_SERVICE_TYPE54 = "54";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/企业风险信息
	 */
	public static final String THIRD_SERVICE_TYPE55 = "55";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/企业裁判文书解析摘要
	 */
	public static final String THIRD_SERVICE_TYPE56 = "56";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/个人工商综合信息
	 */
	public static final String THIRD_SERVICE_TYPE7 = "7";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/车辆评估价查询
	 */
	public static final String THIRD_SERVICE_TYPECHE300 = "che300";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/GPS查询
	 */
	public static final String THIRD_SERVICE_TYPEGPS = "gps";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/房产产权状态查询
	 */
	public static final String THIRD_SERVICE_TYPEHOUSESTS = "housests";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/房产产权数量查询
	 */
	public static final String THIRD_SERVICE_TYPEHOUSECNT = "housecnt";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/手机号三要素实名认证
	 */
	public static final String THIRD_SERVICE_TYPE70 = "70";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/手机号在网状态
	 */
	public static final String THIRD_SERVICE_TYPE71 = "71";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/手机号在网时长
	 */
	public static final String THIRD_SERVICE_TYPE72 = "72";
	/**
	 * THIRD_SERVICE_TYPE - 三方服务类型/用户银行画像
	 */
	public static final String THIRD_SERVICE_TYPE80 = "80";
	
	/**
	 * 百融-策略编号-1-法院被执行人特殊名单借贷意向
	 */
	public static final String STRATEGY_ID_1 = "1";
	/**
	 * 百融-策略编号-2-个人资质客制化
	 */
	public static final String STRATEGY_ID_2 = "2";
	/**
	 * 百融-策略编号-3-百融数据
	 */
	public static final String STRATEGY_ID_3 = "3";
	/**
	 * 运营商OPERATOR - 1移动
	 */
	public static final String OPERATOR1 = "1";
	/**
	 * 运营商OPERATOR - 2联通
	 */
	public static final String OPERATOR2 = "2";
	/**
	 * 运营商OPERATOR - 3电信
	 */
	public static final String OPERATOR3 = "3";
    /**
     * 签章类型 ESIGN_TYPE - 1 客户签
     */
    public static final String ESIGN_TYPE_1 = "1";
    /**
     * 签章类型 ESIGN_TYPE - 2 贷前签
     */
    public static final String ESIGN_TYPE_2 = "2";
    /**
     * 签章类型 ESIGN_TYPE - 3 合同签
     */
    public static final String ESIGN_TYPE_3 = "3";
    /**
     * 签章类型 ESIGN_TYPE - 4 出账签
     */
    public static final String ESIGN_TYPE_4 = "4";
	/**
	 * 匹配类型-客户数
	 * parm_dic---MATCH_TYPE-1
	 */
	public static final String MATCH_TYPE_1 = "1";
	/**
	 * 匹配类型-放款金额
	 * parm_dic---MATCH_TYPE-2
	 */
	public static final String MATCH_TYPE_2 = "2";
	/**
	 * 匹配类型-放款次数
	 * parm_dic---MATCH_TYPE-3
	 */
	public static final String MATCH_TYPE_3 = "3";
	/**
	 * 匹配类型-放款本息（本金+应收利息）
	 * parm_dic---MATCH_TYPE-4
	 */
	public static final String MATCH_TYPE_4 = "4";
	/**
	 * 匹配类型-应收利息
	 * parm_dic---MATCH_TYPE-5
	 */
	public static final String MATCH_TYPE_5 = "5";
	/**
	 * 计算方式-区间-1
	 * parm_dic---MATCH_TYPE
	 */
	public static final String CALC_METHOD_1 = "1";
	/**
	 * 计算方式-分段-2
	 * parm_dic---MATCH_TYPE
	 */
	public static final String CALC_METHOD_2 = "2";
	/**
	 * 计算系数-固额-1
	 * parm_dic---CALC_COEFFICIENT
	 */
	public static final String CALC_COEFFICIENT_1 = "1";
	/**
	 * 计算系数-比例-2
	 * parm_dic---CALC_COEFFICIENT
	 */
	public static final String CALC_COEFFICIENT_2 = "2";
	/**
	 * 办公TEMPLATE_NO - OA转正管理
	 */
	public static final String OA_TEMPLATE_NO1 = "18011110274269411";
	/**
	 * 办公TEMPLATE_NO - OA离职管理
	 */
	public static final String OA_TEMPLATE_NO2 = "18011311510527422";
	/**
	 * 办公TEMPLATE_NO - OA调薪调岗
	 */
	public static final String OA_TEMPLATE_NO3 = "18011509452247627";
	/**
	 * 办公TEMPLATE_NO - OA入职管理
	 */
	public static final String OA_TEMPLATE_NO4 = "18011514573162444";
	/**
	 * 办公TEMPLATE_NO - OA人力需求
	 */
	public static final String OA_TEMPLATE_NO5 = "18011617504791297";
	/**
	 * 办公TEMPLATE_NO - OA请假管理
	 */
	public static final String OA_TEMPLATE_NO6= "18011711010143525";
	/**
	 * 办公TEMPLATE_NO - OA培训需求
	 */
	public static final String OA_TEMPLATE_NO7 = "18011711355410449";
	/**
	 * 办公TEMPLATE_NO - OA事项呈报
	 */
	public static final String OA_TEMPLATE_NO8 = "18011714231304120";
	/**
	 * 办公TEMPLATE_NO - OA全职转兼职
	 */
	public static final String OA_TEMPLATE_NO9 = "18011714590321837";
	/**
	 * 请假类型LEAVE_TYPE -  丧假
	 */
	public static final String LEAVE_TYPE0 = "0";
	/**
	 * 请假类型LEAVE_TYPE -  病假
	 */
	public static final String LEAVE_TYPE1 = "1";
	/**
	 * 请假类型LEAVE_TYPE -  倒休假
	 */
	public static final String LEAVE_TYPE2 = "2";
	/**
	 * 请假类型LEAVE_TYPE -  产假
	 */
	public static final String LEAVE_TYPE3 = "3";
	/**
	 * 请假类型LEAVE_TYPE -  婚假
	 */
	public static final String LEAVE_TYPE4 = "4";
	/**
	 * 请假类型LEAVE_TYPE -  探亲假
	 */
	public static final String LEAVE_TYPE5 = "5";
	/**
	 * 请假类型LEAVE_TYPE -  带薪年假
	 */
	public static final String LEAVE_TYPE6 = "6";
	/**
	 * 请假类型LEAVE_TYPE -  事假
	 */
	public static final String LEAVE_TYPE7 = "7";
	/**
	 * 请假类型LEAVE_TYPE -  陪产假
	 */
	public static final String LEAVE_TYPE8 = "8";
	/**
	 * 请假类型LEAVE_TYPE -  其他
	 */
	public static final String LEAVE_TYPE9 = "9";
	
	/** 客户类型设置CUS_BASE_TYPE - 企业借款客户 */
	public static final String CUS_BASE_TYPE_CORP = "1";
	
	/** 客户类型设置CUS_BASE_TYPE - 个人借款客户 */
	public static final String CUS_BASE_TYPE_PERSON = "2";
	
	/** 客户类型设置CUS_BASE_TYPE - 核心企业客户 */
	public static final String CUS_BASE_TYPE_HEXIAN = "3";
	
	/** 客户类型设置CUS_BASE_TYPE - 渠道商 */
	public static final String CUS_BASE_TYPE_QUDAO  = "4";
	
	/** 客户类型设置CUS_BASE_TYPE - 资金机构 */
	public static final String CUS_BASE_TYPE_ZIJIN = "5";
	
	/** 客户类型设置CUS_BASE_TYPE - 仓储机构 */
	public static final String CUS_BASE_TYPE_WAERHOUSE = "6";
	
	/** 客户类型设置CUS_BASE_TYPE - 保险机构 */
	public static final String CUS_BASE_TYPE_BAOXIN = "7";
	
	/** 客户类型设置CUS_BASE_TYPE - 物流企业 */
	public static final String CUS_BASE_TYPE_WULIU = "8";
	
	/** 客户类型设置CUS_BASE_TYPE - 担保公司 */
	public static final String CUS_BASE_TYPE_DANBAO = "9";
	
	/** 客户类型设置CUS_BASE_TYPE - 集团客户 */
	public static final String CUS_BASE_TYPE_GROUP = "A";
	
	/** 客户类型设置CUS_BASE_TYPE - 个人渠道 */
	public static final String CUS_BASE_TYPE_QUDAOB = "B";
	/** 客户类型设置CUS_BASE_TYPE - 通用合作机构 */
	public static final String CUS_BASE_TYPE_COOPAGENCY = "C";
	
	/**
	 * 渠道商编号开头 - trh渠道商
	 */
	public static final String CUS_TYPE_SET_QUDAO_START = "trh";
	/**
	 * 资金机构编号开头 - agy
	 */
	public static final String CUS_TYPE_SET_ZIJIN_START = "agy";
	
	/**
	 * 租赁物编号序列
	 */
	public static final String SEQ_LEASE_NO = "LEASE_ITEM_INFO_LEASE_NO_SEQ";
	
	/**
	 * 客户号序列
	 */
	public static final String SEQ_CUS_NO = "CUS_NO_SEQ";

	/**
	 * 保后收费
	 */
	public static final String FEE_COLLECT_ALONE = "FEE_COLLECT_ALONE";
	
	
	/**
	 * 对公股东序列
	 */
	
	
	public static final String CUS_CORP_GD_INFO_NO = "CUS_CORP_GD_INFO_SEQ";
	
	
	/**
	 * 对公高管序列
	 */
	public static final String CUS_CORP_ADM_NO = "CUS_CORP_ADM_SEQ";
	
	
	/**
	 * 对公资质序列
	 */
	public static final String CUS_CORP_CERT_NO = "CUS_CORP_CERT_SEQ";
	
	/**
	 * 对公股票序列
	 */
	public static final String CUS_CORP_STOCK_NO = "CUS_CORP_STOCK_SEQ";
	
	
	
	/**
	 * 对公债券序列
	 */
	public static final String CUS_CORP_BOND_NO = "CUS_CORP_BOND_SEQ";
	
	/**
	 * 对公投资序列
	 */
	public static final String CUS_CORP_INV_NO = "CUS_CORP_INV_SEQ";
	/**
	 * 涉诉
	 * INVOLVEMENT_STS - 1
	 */
	public static final String INVOLVEMENT_STS_1 ="1";
	/**
	 * 查封
	 * INVOLVEMENT_STS - 2
	 */
	public static final String INVOLVEMENT_STS_2 ="2";
	/**
	 * 解封
	 * INVOLVEMENT_STS - 3
	 */
	public static final String INVOLVEMENT_STS_3 ="3";
	/**
	 * 抵债
	 * INVOLVEMENT_STS - 4
	 */
	public static final String INVOLVEMENT_STS_4 ="4";
	/**
	 * 续查封
	 * INVOLVEMENT_STS - 5
	 */
	public static final String INVOLVEMENT_STS_5 ="5";
	
	/**
	 * 个人客户联系人序列
	 */
	public static final String CUS_PERS_CONTACT_NO = "CUS_PERS_CONTACT_SEQ";
	
	/**
	 * 个人客户履历序列
	 */
	public static final String CUS_PERS_CAREER_NO = "CUS_PERS_CAREER_SEQ";
	
	/**
	 * 个人资产序列
	 */
	public static final String CUS_PERS_ASS_NO = "CUS_PERS_ASS_SEQ";
	/**
	 * 保证金信息序列
	 */
	public static final String CUS_DEPOSIT_LIST_SEQ = "CUS_DEPOSIT_LIST_SEQ";
	
	/**
	 * 账户序列
	 */
	public static final String CUS_BANK_INFO_NO = "CUS_BANK_INFO_SEQ";
	
	/**
	 * 租赁物发票编号
	 */
	public static final String LEASE_LNVOICE_NO = "LEASE_LNVOICE_SEQ";
	
	/**
	 * 业务变更主键
	 */
	public static final String BIZ_INFO_CHANGE_SEQ = "BIZ_INFO_CHANGE_SEQ";
	
	/**
	 * 业务变更主键
	 */
	public static final String BIZ_INFO_CHANGE_SUB_SEQ = "BIZ_INFO_CHANGE_SUB_SEQ";
	
	/**
	 * 租赁物维修信息编号
	 */
	public static final String LEASE_REPAIR_NO = "LEASE_REPAIR_SEQ";
	
	/**
	 * 租赁物保养信息编号
	 */
	public static final String LEASE_MAINTAIN_NO = "LEASE_MAINTAIN_SEQ";
	
	/**
	 * 租赁物行车记录编号
	 */
	public static final String LEASE_OPER_INFO_NO = "LEASE_OPER_INFO_SEQ";
	
	
	/**
	 * 保险定义编号
	 */
	public static final String INSURE_DEF_NO = "INSURE_DEF_SEQ";
	
	/**
	 * 租赁物保险登记（续保）编号
	 */
	public static final String INSURE_REGISTER_NO = "INSURE_REGISTER_SEQ";
	
	/**
	 * 出险（理赔）编号
	 */
	public static final String CLAIMS_REGISTER_NO = "CLAIMS_REGISTER_SEQ";
	
	
	/**
	 * 黑名单序列
	 */
	public static final String CUS_BLACK_INFO_NO = "CUS_BLACK_INFO_SEQ";
	
	
	/**
	 * 个人家庭成员序列
	 */
	public static final String CUS_PERS_FAM_NO = "CUS_PERS_FAM_SEQ";
	/**
	 * 个人对外投资序列
	 */
	public static final String CUS_PERS_INV_NO = "CUS_PERS_INV_SEQ";
	
	
	/**
	 * 个人生产经营序列
	 */
	public static final String CUS_PERS_PROD_NO = "CUS_PERS_PROD_SEQ";
	
	/**
	 * 个人社会保险序列
	 */
	public static final String CUS_PERS_INSU_SOC_NO = "CUS_PERS_INSU_SOC_SEQ";
	
	/**
	 * 个人商业保险序列
	 */
	public static final String CUS_PERS_INSU_COM_NO = "CUS_PERS_INSU_COM_SEQ";
	
	/**
	 * 个人社会关系序列
	 */
	public static final String CUS_PERS_SOC_NO = "CUS_PERS_SOC_SEQ";
	
	/**
	 * 个人纳税序列
	 */
	public static final String CUS_PERS_TAX_NO = "CUS_PERS_TAX_SEQ";
	
	/**
	 * 客户移交序列
	 */
	public static final String CUS_MOVE_APP_SEQ = "CUS_MOVE_APP_SEQ";
	
	
	/**
	 * 客户跟踪
	 */
	public static final String CUS_TRACE_NO = "CUS_TRACE_SEQ";
	
	
	/**
	 * 个人客户大事件序列
	 */
	public static final String CUS_PERS_IMP_NO = "CUS_PERS_IMP_SEQ";

	/**
	 * 客户系统评价配置指标编号
	 */
	public static final String CUS_SYS_EVAL_PARM_CODE = "CUS_SYS_EVAL_PARM_SEQ";
	/**
	 * 资金选项明细序列
	 */
	public static final String DIC_ID_SEQ="DIC_ID_SEQ";
	
	/**
	 * 客户资金池明细序列
	 */
	public static final String CUS_FUND_NO_SEQ="FUND_NO_SEQ";
	
	/**
	 * 资金流水序列
	 */
	public static final String LOG_ID_SEQ="DIC_ID_SEQ";
	/**
	 * 资金账号序列
	 */
	public static final String FUND_NO_SEQ="DIC_ID_SEQ";
	
	
	/**
	 * 品牌管理序列
	 */
	public static final String CUS_BRAND_MANG_NO = "CUS_BRAND_MANG_SEQ";
	
	
	/**
	 * 活动模板序列
	 */
	public static final String CUS_EVENT_MODEL_NO = "CUS_EVENT_MODEL_SEQ";
	

	/**
	 * 活动信息序列
	 */
	public static final String CUS_LATEST_EVENT_NO = "CUS_LATEST_EVENT_SEQ";
	
	/**
	 * 合同申请表序列
	 */
	public static final String APP_CONT_SEQ="APP_CONT_SEQ";
	
	/**
	 * 提前结清序列
	 */
	public static final String ALP_BASE_SEQ="ALP_BASE_SEQ";
	
	/**
	 * 租赁物配件序列
	 */
	public static final String LEASE_ITEM_PARTS_INFO_SEQ="LEASE_ITEM_PARTS_INFO_SEQ";
	
	
	/**
	 * 融资管理序列
	 */
	public static final String FIN_BASE_SEQ="FIN_BASE_SEQ";

    /**
     * 编号类型 1 部门编号
     */
    public static final String NO_TYPE_ORG = "1";
    /**
     * 编号类型 2 员工编号
     */
    public static final String NO_TYPE_USER = "2";
    /**
     * 编号类型 3 客户编号
     */
    public static final String NO_TYPE_CUS = "3";
    /**
     * 编号类型 4 合同编号
     */
    public static final String NO_TYPE_PACTNO = "4";
    /**
     * 编号类型 5 从合同编号
     */
    public static final String NO_TYPE_FOLLOW_PACTNO = "5";
    /**
     * 编号类型 6 押品编号
     */
    public static final String NO_TYPE_COLLATERAL = "6";
    /**
     * 编号类型7 借据编号
     */
    public static final String NO_TYPE_IOU = "7";
    /**
     * 编号类型8 展期合同编号
     */
    public static final String NO_TYPE_EXTEN = "8";
    /**
     * 编号类型9 授信合同编号
     */
    public static final String NO_TYPE_CREDIT = "9";
    /**
     * 编号类型10 合同类型编号
     */
    public static final String NO_TYPE_PACT_TYPE = "10";
    /**
     * 编号类型10 核心机构编号
     */
    public static final String NO_TYPE_CORE = "11";
    /**
     * 编号类型12 帐款转让编号
     */
    public static final String NO_TYPE_RECE_TRANS = "12";
    /**
     * 编号类型13 渠道编号
     */
    public static final String NO_TYPE_TRENCH = "13";
    /**
     * 编号类型14 资金机构编号
     */
    public static final String NO_TYPE_AGENCIES = "14";
    /**
     * 编号类型15 仓储机构编号
     */
    public static final String NO_TYPE_WAREHOUSE = "15";
    /**
     * 编号类型20 立项项目编号
     */
    public static final String NO_TYPE_PROJECT = "20";
    /**
     * 编号类型21 立项尽调报告编号
     */
    public static final String NO_TYPE_REPORT = "21";
	
	/**
	 * 产品类型 --乘用车
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_PASSENGER = "1"; 
	
	/**
	 * 产品类型 --商用车
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_COMMERICAL = "2"; 
	
	/**
	 * 产品类型 --工程机械
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_MACHINERY = "3"; 
	
	/**
	 * 产品类型 --设备类
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_DEVICE = "4"; 
	
	/**
	 * 产品类型 --飞机类
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_PLANE = "5"; 
	
	/**
	 * 产品类型 --轮船类
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_STEAM = "6"; 
	
	/**
	 * 产品类型 --商业物业
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_ESTATE = "7"; 
	
	/**
	 * 产品类型 --无形资产
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_INTAN = "8"; 
	
	/**
	 * 产品类型 --混合
	 * parm_dic -PROD_TYPE
	 */
	public static final String PROD_TYPE_MIX = "9"; 
	
	/**
	 * 资产类型 --车
	 */
	public static final String ASSETS_TYPE_CAR = "1"; 
	
	/**
	 * 资产类型 --房屋
	 */
	public static final String ASSETS_TYPE_HOUSE = "2"; 
	/**
	 * 资产类型 --设备
	 */
	public static final String ASSETS_TYPE_EQUIPMENT = "3"; 
	
	/**
	 * 资产类型 --其它经营性资产
	 */
	public static final String ASSETS_TYPE_OTHER_BUS_ASSETS = "4"; 
	/**
	 * 资产类型 --其它非经营性资产
	 */
	public static final String ASSETS_TYPE_OTHER_NOT_BUS_ASSETS = "5"; 
	
	/**
	 * 客户标识分隔符
	 */
    public static final String CUS_FORMAL_SEPARATOR = "|";
	
    /**
	 * 对公客户标识 --对公承租人
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_RENTER = "C01";
	
	/**
	 * 对公客户标识 --保证人
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_GUARANTOR = "C02";
	
	/**
	 * 对公客户标识 --供货商
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_SUPPLIER = "C03";
	
	/**
	 * 对公客户标识 --经销商
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_PROXY = "C04";
	
	/**
	 * 对公客户标识 --其他合作伙伴
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_PARTNER = "C05";
	
	/**
	 * 对公客户标识 --挂靠公司
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_AFFILIATE = "C06";
	
	/**
	 * 对公客户标识 --抵押人
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_MORTGAGOR = "C07";
	
	/**
	 * 对公客户标识 --质押人
	 * parm_dic - CUS_CORP_FORMAL
	 */
	public static final String CUS_CORP_FORMAL_PLEDGOR = "C08";

	/**
	 * 个人客户标识 --承租人
	 * parm_dic - CUS_PERS_FORMAL
	 */
	public static final String CUS_PERS_FORMAL_RENTER = "P01";
	
	/**
	 * 个人客户标识 --保证人
	 * parm_dic - CUS_PERS_FORMAL
	 */
	public static final String CUS_PERS_FORMAL_GUARANTOR = "P02";
	
	/**
	 * 个人客户标识 --抵押人
	 * parm_dic - CUS_PERS_FORMAL
	 */
	public static final String CUS_PERS_FORMAL_MORTGAGOR = "P03";
	
	/**
	 * 个人客户标识 --质押人
	 * parm_dic - CUS_PERS_FORMAL
	 */
	public static final String CUS_PERS_FORMAL_PLEDGOR = "P04";
	
	/**
	 * 个人客户标识 --合作伙伴
	 * parm_dic - CUS_PERS_FORMAL
	 */
	public static final String CUS_PERS_FORMAL_PARTNER = "P05";
	
	
	
	/**
	 * 是否 --是
	 * parm_dic -YES_NO -1
	 */
	public static final String YES_NO_Y = "1";
	
	/**
	 * 是否 --否
	 * parm_dic -YES_NO -0
	 */
	public static final String YES_NO_N = "0"; 
    /**
     * 上收息
     */
	public static final String INTERESTCOLLECTTYPE_1="1";
	/**
	 * 下收息
	 */
	public static final String INTERESTCOLLECTTYPE_2="2";
	
	/**
	 * 1 上收费
	 */
	public static final String FEECOLLECTTYPE_1="1";
    /**
     * 2 下收费
     */
	public static final String FEECOLLECTTYPE_2="2";
	
	/**
	 * 费用计划使用  1 收取费用计划
	 */
	public static final String  FEETYPE_1="1"; 
	
	/**
	 * 费用计划使用  2-退费费用计划
	 */
	public static final String  FEETYPE_2="2"; 
	
	/**
	 * 费用计划使用  3-冲抵费用计划
	 */
	public static final String  FEETYPE_3="3";
	
	/**
	 * 1-放款时收取
	 */
	public static final String FEECOLLECTTIME_1="1";
		
	/**
	 * 2-还款时收取
	 */
	public static final String FEECOLLECTTIME_2="2";
	
	/**
	 * 3-单独收取
	 */
	public static final String FEECOLLECTTIME_3="3";
	
	/**
	 * 费率类型 1 百分比%
	 */
	public static final String FEETAKETYPE_1="1";
	
	/**
	 * 费率类型  2 固定金额（分摊）
	 */
	public static final String FEETAKETYPE_2="2";
	
	/**
	 * 费率类型  3 固定金额(额定) 
	 */
	public static final String FEETAKETYPE_3="3";
	
	/**
	 * 费率类型  4 利息差额（铁甲网使用）
	 */
	public static final String FEETAKETYPE_4="4";
	
	/**
	 * 所占权重默认值1.00
	 * 
	 */
	public static final Double WEIGHT = 1.00;
	
	/**
	 * 是否 --是
	 * parm_dic 
	 */

	public static final String YES_Y = "Y";
	
	/**
	 * 是否 --否
	 * parm_dic -YES_NO -0
	 */

	public static final String NO_N = "N"; 

	
	
	
	/**
	 * 证件类型 --身份证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_ID_CARD = "0"; 
	
	/**
	 * 证件类型 --户口簿
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_RESI = "1";
	
	/**
	 * 证件类型 --护照
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_PASSPORT = "2"; 
	
	/**
	 * 证件类型 --军官证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_OFFICER = "3"; 
	
	/**
	 * 证件类型 --士兵证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_SOLIDER = "4"; 
	
	/**
	 * 证件类型 --港澳居民来往内地通行证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_GANGANG = "5"; 
	
	/**
	 * 证件类型 --台湾同胞来往内地通行证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_TAIWAN = "6";
	
	/**
	 * 证件类型 --临时身份证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_TEMP_ID = "7"; 
	
	/**
	 * 证件类型 --外国人居留证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_ALIEN = "8"; 
	
	/**
	 * 证件类型 --警官证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_POLI = "9"; 
	
	/**
	 * 证件类型 --组织机构代码
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_CERT = "A";  
	
	/**
	 * 证件类型 --信用代码证
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_CREDIT = "B"; 
	/**
	 * 证件类型 --	营业执照
	 * parm_dic -ID_TYPE
	 */
	public static final String ID_TYPE_LICENCE = "C"; 
	
	
	/**
	 * 婚姻状态 --未婚
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_SINGLE = "10";
	
	/**
	 * 婚姻状态 --已婚
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_MARRIED = "20";
	
	/**
	 * 婚姻状态 --丧偶
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_WIDOWED = "30";
	
	/**
	 * 婚姻状态 --离异
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_DIVORCE = "40";
	/**
	 * 婚姻状态 --未婚
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_1 = "1";
	/**
	 * 婚姻状态 --已婚有子女
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_2 = "2";
	/**
	 * 婚姻状态 --已婚
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_3 = "3";
	/**
	 * 婚姻状态 --已婚无子女
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_4 = "4";
	/**
	 * 婚姻状态 --丧偶
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_5= "5";
	/**
	 * 婚姻状态 --离异
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_6 = "6";
	/**
	 * 婚姻状态 --其他
	 * parm_dic -MARRIGE
	 */
	public static final String MARRIGE_7 = "7";
	
	/**
	 * 个人与申请人的关系--配偶
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_1 = "1";
	/**
	 * 个人与申请人的关系--子女
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_2 = "2";
	/**
	 * 个人与申请人的关系--父母
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_3 = "3";
	/**
	 * 个人与申请人的关系--亲戚
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_4 = "4";
	/**
	 * 个人与申请人的关系--其他
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_5 = "5";
	/**
	 * 个人与申请人的关系--兄弟
	 * parm_dic -CUS_PERS_REL
	 */
	public static final String CUS_PERS_REL_6 = "6";
	/**
	 * 民族 --汉族
	 * parm_dic -NATION
	 */
	public static final String NATION_DIVORCE = "1";
	
	
	/** 
	 * 业务申请状态 -- 未提交
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_UN_SUBMIT = "0"; 
	
	/** 
	 * 业务申请状态 -- 流程中
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_PROCESS = "1"; 
	
	/**
	 * 业务申请状态 -- 审批流程结束 业务还处在申请阶段
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_SUB_PASS = "2";
	
	
	/** 
	 * 业务申请状态 -- 退回
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_SEND_BACK = "3"; 
	
	/** 
	 * 业务申请状态 -- 通过
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_PASS = "4"; 
	
	/** 
	 * 业务申请状态 -- 否决
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_DISAGREE = "5"; 

	/**
	 * 业务申请状态 -- 补充资料(业务审批退回第一岗位处理)
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_SUPPLEMENT = "6";
	/**
	 * 业务申请状态 -- 申请信息保存后未启动流程 状态（目前用于进件处多表单中）
	 * parm_dic - APP_STS
	 */
	public static final String APP_STS_UN_COMPLETE = "7";

	/**
	 * 租赁合同状态 -- 未生效
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_UN = "0";
	
	/**
	 * 租赁合同状态 -- 生效
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_NORMAL = "1"; // 生效
	
	/**
	 * 租赁合同状态 -- 逾期30天以内
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_VOER = "2"; // 逾期30天以内
	
	/**
	 * 租赁合同状态 -- 逾期30到60
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_OVER30 = "3"; // 逾期30到60
	
	/**
	 * 租赁合同状态 -- 逾期60到90
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_OVER60 = "4"; // 逾期60到90
	
	/**
	 * 租赁合同状态 -- 逾期90天以上
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_OVER90 = "5"; // 逾期90天以上
	
	/**
	 * 租赁合同状态 -- 撤销
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_BACK = "6"; // 撤销
	
	/**
	 * 租赁合同状态 -- 部分生效
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_NORMAL_PART = "7"; // 部分生效
	
	/**
	 * 租赁合同状态 -- 已注销
	 * parm_dic - CON_STS
	 */
	public static final String CON_STS_CANCEL = "9"; // 已注销
	
	/**
	 * 租赁台账状态 -- 出帐未确认
	 * parm_dic - ACC_STS
	 */
	public static final String ACC_STS_UN ="0";//出帐未确认
	
	/**
	 * 租赁台账状态 -- 生效
	 * parm_dic - ACC_STS
	 */
	public static final String ACC_STS_NORMAL ="1";//生效
	
	/**
	 * 租赁台账状态 -- 撤销
	 * parm_dic - ACC_STS
	 */
	public static final String ACC_STS_BACK ="2";//撤销
	
	/**
	 * 租赁台账状态 -- 核销
	 * parm_dic - ACC_STS
	 */
	public static final String ACC_STS_CANCEL ="8";//核销
	
	/**
	 * 租赁台账状态 -- 已还清
	 * parm_dic - ACC_STS
	 */
	public static final String ACC_STS_CLEAR ="9";//已还清
	
	/**
	 * 放款对象 -- 经销商
	 * parm_dic - LOAN_OBJ
	 */
	public static final String LOAN_OBJ_DEAL ="1";//经销商
	
	/**
	 * 放款对象 -- 供货商
	 * parm_dic - LOAN_OBJ
	 */
	public static final String LOAN_OBJ_SUPPLIER ="2";//经销商
	
	/**
	 * 授信额度占用方式 -- 不占用额度
	 * parm_dic - AUTH_FLAG
	 */
	public static final String AUTH_FLAG_1 ="1";
	
	/**
	 * 授信额度占用方式 -- 独立经销商额度
	 * parm_dic - AUTH_FLAG
	 */
	public static final String AUTH_FLAG_2 ="2";
	
	/**
	 * 授信额度占用方式 -- 厂商分配额度
	 * parm_dic - AUTH_FLAG
	 */
	public static final String AUTH_FLAG_3 ="3";
	
	/**
	 * 授信额度占用方式 -- 厂商整体额度
	 * parm_dic - AUTH_FLAG
	 */
	public static final String AUTH_FLAG_4 ="4";
	
	/**
	 * 授信额度占用方式 -- 承租人额度
	 * parm_dic - AUTH_FLAG
	 */
	public static final String AUTH_FLAG_5 ="5";
	
	/**
	 * 五级分类 -- 正常
	 * parm_dic - FIVE_STS
	 */
	public static final String FIVE_STS_1TH = "1"; // 正常
	
	/**
	 * 五级分类 -- 关注
	 * parm_dic - FIVE_STS
	 */
	public static final String FIVE_STS_2TH = "2"; // 关注
	
	/**
	 * 五级分类 -- 次级
	 * parm_dic - FIVE_STS
	 */
	public static final String FIVE_STS_3TH = "3"; // 次级
	
	/**
	 * 五级分类 -- 可疑
	 * parm_dic - FIVE_STS
	 */
	public static final String FIVE_STS_4TH = "4"; // 可疑
	
	/**
	 * 五级分类 -- 损失
	 * parm_dic - FIVE_STS
	 */
	public static final String FIVE_STS_5TH = "5"; // 损失
	
	/**
	 * 停记罚息标识 -- 未停罚息
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String STOP_CUMU_IND_NO  = "0";//未停罚息
	
	/**
	 * 停记罚息标识 -- 已停罚息
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String STOP_CUMU_IND_YES = "1";//已停罚息
	
	

	/**
	 * 出险（理赔）状态 --出险登记
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String CLA_STATUS_1 = "1";
	
	/**
	 * 出险（理赔）状态 --理赔登记
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String CLA_STATUS_2 = "2";
	
	/**
	 * 出险（理赔）状态 --撤销
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String CLA_STATUS_3 = "3";
	
	/**
	 * 出险（理赔）状态 --完成
	 * parm_dic - STOP_CUMU_IND
	 */
	public static final String CLA_STATUS_4 = "4";
	
	/**
	 * 非正常结束类型 --提前结清(按租赁物)
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_PRE_REPAY_LEASE = "1";
	
	/**
	 * 非正常结束类型 --提前结清(按金额)
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_PRE_REPAY_MONEY = "2";
	
	/**
	 * 非正常结束类型 --回购(按租赁物)
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_SPECIAL_LEASE = "3";
	
	/**
	 * 非正常结束类型 --回购(按金额)
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_SPECIAL_MONEY = "4";
	
	/**
	 * 非正常结束类型 --业务终止
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_CLOSE = "5";
	
	/**
	 * 非正常结束类型 --产权转移
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_LEASE_TRANSFER = "6";
	
	/**
	 * 非正常结束类型 --合同变更
	 * parm_dic - ALP_TYPE
	 */
	public static final String ALP_TYPE_CON_CHANGE = "7";
	
	/**
	 * 租后类型 --提前结清
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_1 = "1";
	
	/**
	 * 租后类型 --回购
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_2 = "2";
	
	/**
	 * 租后类型 --保证金抵扣
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_3 = "3";
	
	/**
	 * 租后类型 --保证金退回
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_4 = "4";
	
	
	/**
	 * 租后类型 --产权转移
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_5 = "5";
	
	
	/**
	 * 租后类型 --合同变更
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_6 = "6";
	
	/**
	 * 租后类型 --合同终止
	 * parm_dic - ALP_MAIN_STS
	 */
	public static final String ALP_MAIN_STS_7 = "7";
	
	
	/** 
	 * 租后流程状态 -- 未提交
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_UN_SUBMIT = "0"; 
	
	/**
	 * 租后流程状态 -- 上传客户资料
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_UPLOAD = "1";
	
	/** 
	 * 租后流程状态 -- 流程中
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_PROCESS = "2"; 
	
	/** 
	 * 租后流程状态 -- 退回
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_SEND_BACK = "3"; 
	
	/** 
	 * 租后流程状态 -- 通过
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_PASS = "4"; 
	
	/** 
	 * 租后流程状态 -- 否决
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_DISAGREE = "5"; 
	
		/** 
	 * 租后流程状态 -- 完成
	 * parm_dic - ALP_STATUS
	 */
	public static final String ALP_STATUS_FINISHED = "6";
	
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-正常状态
	 */
	public static final String ALP_TYPE_STS_NORM =  "0";
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-提前结清(按租赁物)
	 */
	public static final String ALP_TYPE_STS_PRE_REPAY_LEASE = "1";
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-提前结清(按金额)
	 */
	public static final String ALP_TYPE_STS_PRE_REPAY_MONEY = "2";
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-回购(按租赁物)
	 */
	public static final String ALP_TYPE_STS_SPECIAL_LEASE = "3";
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-回购(按金额)
	 */
	public static final String ALP_TYPE_STS_SPECIAL_MONEY = "4";
	
	/**
	 * 租赁物非正常结束类型
	 * ALP_TYPE_STS-业务终止
	 */
	public static final String ALP_TYPE_STS_CLOSE = "5";
	/**
	 * 移交状态 
	 * TRANS_STS-成功
	 */
	public static final String TRANS_STS0 = "0";
	/**
	 * 移交状态 
	 * TRANS_STS-审批中
	 */
	public static final String TRANS_STS1 = "1";
	/**
	 * 移交状态 
	 * TRANS_STS-否决
	 */
	public static final String TRANS_STS2 = "2";

	/**
	 * 基准利率类型
	 */
	public static final String BASE_RATE_TYPE1 = "1";//贷款基准利率

	public static final String BASE_RATE_TYPE2 = "2";//公积金利率

	public static final String BASE_RATE_TYPE3 = "3";//贴现利率

	public static final String BASE_RATE_TYPE4 = "4";//lpr市场报价利率


	/*临时使用*/
	/*
	 * 属性类型
	 */
	public static final String ATTRIBUTE_TYPE_1 = "1"; //流程变量
	public static final String ATTRIBUTE_TYPE_2 = "2"; //业务数据
	
	
	/**
	 * 场景类型-文档管理
	 */
	public static final String SCENCE_TYPE_DOC = "01";
	/**
	 * 场景类型-个人客户
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 *//*
	public static final String SCENCE_TYPE_DOC_PERS = "0000000000"; //个人客户
*/	/**
	 * 场景类型-客户登记
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_CUS = "0000000001"; //客户登记
	/**
	 * 场景类型-业务申请
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_APP = "0000000002";  //业务申请
	/**
	 * 场景类型-贷前调查
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_INVESTIGATION = "0000000003";  //贷前调查、尽职调查
	/**
	 * 场景类型-业务审批
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_BUS_APPROVAL = "0000000004"; //业务审批
	/**
	 * 场景类型-合同签约
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_CONT = "0000000005"; //合同签约
	/**
	 * 场景类型-合同审批
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_PACT_APPROVAL = "0000000006"; //合同审批
	/**
	 * 场景类型-放款申请
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_FINC_APP = "0000000007"; //放款申请
	/**
	 * 场景类型-放款审批
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_FINC_APPROVAL = "0000000008"; //放款审批
	/**
	 * 场景类型-押品登记
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_PLE_REG = "pledge_reg"; //押品登记
	/**
	 * 场景类型-权证审批
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_CERTIDINFO_APPR = "certidInfo_appr"; //权证审批
	/**
	 * 场景类型-贷后检查
	 * SCENCE 中 SCENCE_TYPE = 02 的
	 */
	public static final String SCENCE_TYPE_DOC_AFTER_CHECK = "loanAfterExamine"; //贷后检查
	/**
	 * 场景类型-贷后检查
	 * SCENCE 中 SCENCE_TYPE = 02 的
	 */
	public static final String SCENCE_TYPE_DOC_RISK_AUDIT = "riskAuditExamine"; //风控稽核
	
	/**
	 * 风险等级调整类型-升级
	 */
	public static final String RISK_LEVEL_ADJUST_TYPE1 = "1"; 
	
	/**
	 * 风险等级调整类型-降级
	 */
	public static final String RISK_LEVEL_ADJUST_TYPE2 = "2"; 
	
	/**
	 * 风险等级调整类型-解除
	 */
	public static final String RISK_LEVEL_ADJUST_TYPE3 = "3"; 
	/**
	 * 风险等级调整类型-处置
	 */
	public static final String RISK_LEVEL_ADJUST_TYPE4 = "4"; 
	/**
	 * 风险处置状态-未处置
	 */
	public static final String RISK_MANAGE_STS1 = "1"; 
	
	/**
	 * 风险处置状态-处置中
	 */
	public static final String RISK_MANAGE_STS2 = "2"; 
	
	/**
	 * 风险处置状态-已否决
	 */
	public static final String RISK_MANAGE_STS3 = "3"; 
	/**
	 * 风险处置状态-待审核
	 */
	public static final String RISK_MANAGE_STS4 = "4"; 
	/**
	 * 风险处置状态-审核中
	 */
	public static final String RISK_MANAGE_STS5 = "5"; 
	
	/**
	 * 风险处置状态-已否决
	 */
	public static final String RISK_MANAGE_STS6 = "6"; 
	/**
	 * 风险处置状态-审核完成
	 */
	public static final String RISK_MANAGE_STS7 = "7"; 
	
	/**
	 * 风险来自客户视角 
	 */
	public static final String RISK_COME_FROM1 = "1"; 
	
	/**
	 * 风险来自业务视角 
	 */
	public static final String RISK_COME_FROM2 = "2"; 
	
	/**
	 * 贷后检查风险等级-最高风险
	 */
	public static final String EXAM_RISK_LEVEL0 = "0"; 
	
	/**
	 * 贷后检查风险等级-高风险
	 */
	public static final String EXAM_RISK_LEVEL1 = "1"; 
	
	/**
	 * 贷后检查风险等级-中风险
	 */
	public static final String EXAM_RISK_LEVEL2 = "2"; 
	/**
	 * 贷后检查风险等级-低风险
	 */
	public static final String EXAM_RISK_LEVEL3 = "3"; 
	
	/**
	 * 贷后检查风险等级-无风险
	 */
	public static final String EXAM_RISK_LEVEL4 = "4"; 
	
	
	
	
	public static final String SCENCE_TYPE_RISK_APP = "0000000005"; //拦截业务申请

	public static final String SCENCE_TYPE_DOC_ALP_BASE = "0000000008"; //提前结清
	
	public static final String SCENCE_TYPE_DOC_ALP_SPECIAL = "0000000009"; //回购
	
	public static final String SCENCE_TYPE_DOC_ALP_DEPOSIT_OFFSET = "0000000010"; //保证金抵扣
	
	public static final String SCENCE_TYPE_DOC_ALP_DEPOSIT_BACK = "0000000011"; //保证金退回
	
	public static final String SCENCE_TYPE_DOC_ALP_LEASE_TRANSFER = "0000000012"; //产权转移
	
	public static final String SCENCE_TYPE_DOC_ALP_CON_CHANGE = "0000000013"; //合同变更
	
	public static final String SCENCE_TYPE_DOC_ALP_CANCEL = "0000000016"; //合同终止
	
	/**
	 * 场景类型-调查报告
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_RESEARCH = "0000000018"; //调查报告
	
	/**
	 * 场景类型-风控报告
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_APP_RISK = "0000000019"; //风控报告
	
	/**
	 * 场景类型-签订合同
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	// 使用SCENCE_TYPE_DOC_CONT public static final String SCENCE_TYPE_DOC_SING_CONT = "0000000020"; //签订合同
	
	public static final String SCENCE_TYPE_DOC_EXAM = "0000000021"; //检查审批
	
	/**
	 * 场景类型-法律诉讼
	 */
	public static final String SCENCE_TYPE_DOC_LAWSUIT = "0000000023";
	/**
	 * 场景类型-承购审批
	 */
	public static final String SCENCE_TYPE_DOC_RECEPLE = "0000000023";
	/**
	 * 场景类型-进件
	 */
	public static final String SCENCE_TYPE_DOC_JINJIAN = "0000000024";//第一次提报资料
	/**
	 * 场景类型-初评评估
	 */
	public static final String SCENCE_TYPE_DOC_PRE_EVALUATION = "0000000025";//初评评估
	/**
	 * 场景类型-第二次提报资料
	 */
	public static final String SCENCE_TYPE_DOC_TIBAO = "0000000026";//第二次提报资料
	/**
	 * 场景类型-公证
	 */
	public static final String SCENCE_TYPE_DOC_NOTARIZATION= "0000000027";//公证
	/**
	 * 场景类型-权证
	 */
	public static final String SCENCE_TYPE_DOC_WARRANT= "0000000028";//权证
	

	/**
	 * 场景类型-分单
	 */
	public static final String SCENCE_TYPE_DOC_REINSURANCE_POLICY= "0000000029";//分单
	
	/**
	 * 场景类型-收费
	 */
	public static final String SCENCE_TYPE_DOC_FEE= "0000000030";//收费

	/**
	 * 场景类型-还款计划
	 */
	public static final String SCENCE_TYPE_DOC_REVIEW_FINC = "0000000031";//还款计划

	/**
	 * 场景类型-征信
	 */
	public static final String SCENCE_TYPE_DOC_CREDIT_INVESTIGATION = "0000000032";//征信

	/** 场景类型-放款确认 */
	public static final String SCENCE_TYPE_DOC_LOAN_CONFIRM = "0000000033";// 放款确认
	
	/** 场景类型-价值确认 */
	public static final String SCENCE_TYPE_DOC_CONFIRM_EVALUATION = "0000000034";// 价值确认
	/**
	 * 场景类型-共同借款
	 * SCENCE 中 SCENCE_TYPE = 01 的
	 */
	public static final String SCENCE_TYPE_DOC_BORROWER = "0000000035";  //共同借款人
	/**
	 * 场景类型-风险管理
	 */
	public static final String SCENCE_TYPE_RISK = "02";
	
	public static final String SCENCE_TYPE_AUTH_AUTH = "0000000014"; //
	
	public static final String SCENCE_TYPE_AUTH_FORMULAR = "0000000017";

	public static final String SCENCE_TYPE_AUTH = "03";
	
	/**
	 * 文档类别-客户资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_CUS = "01";
	
	/**
	 * 文档类别-申请资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_APP = "02";
	
	/**
	 * 文档类别-合同资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_CONT = "03";
	/**
	 * 文档类别-企业资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_01 = "01";

	/**
	 * 文档类别-案件资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_L3 = "L3";

	/**
	 * 文档类别-受托支付文件
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_L4 = "L4";
	/**
	 * 文档类别-应收账款资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_03 = "03";

	/**
	 * 文档类别-调查资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_04 = "04";

	/**
	 * 文档类别-合同资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_05 = "05";
	/**
	 * 文档类别-个人资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_06 = "06";
	/**
	 * 文档类别-签约证据资料
	 * parmDic DOC_TYPE
	 */
	public static final String DOC_TYPE_07 = "07";

	/**
	 * 文档类别-维度-对公乘用车
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_01 = "01";
	
	/**
	 * 文档类别-维度-个人乘用车
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_02 = "02";
	
	/**
	 * 文档类别-维度-对公商用车
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_03 = "03";
	
	/**
	 * 文档类别-维度-个人商用车
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_04 = "04";
	
	/**
	 * 文档类别-维度-对公设备
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_05 = "05";
	
	/**
	 * 文档类别-维度-个人设备
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_06 = "06";
	
	/**
	 * 文档类别-维度-默认
	 * parmDic DOC_DIMM1
	 */
	public static final String DOC_DIMM1_99 = "99";
	
	
	/**
	 * 担保编号序列
	 */
	public static final String SEQ_VOU_NO = "VOU_NO_SEQ";
	
	/**
	 * 担保类型 -- 信用担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_1 = "1";
	
	/**
	 * 担保类型 -- 保证担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_2 = "2";
	
	/**
	 * 担保类型 -- 抵押担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_3 = "3";
	
	/**
	 * 担保类型 -- 质押担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_4 = "4";

	/**
	 * 担保类型 -- 转让担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_5 = "5";
	/**
	 * 担保类型 -- 租赁担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_6 = "6";

	/**
	 * 担保类型 -- 最高额担保合同担保
	 * parm_dic - VOU_TYPE
	 */
	public static final String VOU_TYPE_10 = "10";
	/**
	 * 保险登记状态
	 */
	public static final String INS_REG_STATUS_1 = "1";//启用
	
	public static final String INS_REG_STATUS_2 = "2";//停用
	
	
	
	/**
	 * 资金流水状态--登记
	 */
	public static final String FUND_LOG_STS_1="1";//登记
	/**
	 * 资金流水状态--待确认
	 */
	public static final String FUND_LOG_STS_2="2";//待确认
	/**
	 * 资金流水状态--确认
	 */
	public static final String FUND_LOG_STS_3="3";//确认
	/**
	 * 资金流水状态--撤销
	 */
	public static final String FUND_LOG_STS_4="4";//撤销
	
	/**
	 * 资金流水-收款
	 */
	public static final String DIC_TYPE_1="1";//收款
	
	/**
	 * 资金流水-付款
	 */
	public static final String DIC_TYPE_2="2";//付款
	
	/**
	 * 资金明细 -- 手续费
	 */
	public static final String DIC_ID_0001="0001";//手续费
	/**
	 * 资金明细 -- 管理费
	 */
	public static final String DIC_ID_0002="0002";//管理费
	/**
	 * 资金明细 -- 杂费
	 */
	public static final String DIC_ID_0003="0003";//杂费
	/**
	 * 资金明细 -- 收保证金
	 */
	public static final String DIC_ID_0004="0004";//收保证金
	/**
	 * 资金明细 -- 放款
	 */
	public static final String DIC_ID_0005="0005";//放款
	/**
	 * 资金明细 -- 首付款
	 */
	public static final String DIC_ID_0006="0006";//首付款
	/**
	 * 资金明细 -- 租金
	 */
	public static final String DIC_ID_0007="0007";//租金
	/**
	 * 资金明细 -- 提前结清款
	 */
	public static final String DIC_ID_0008="0008";//提前结清款
	/**
	 * 资金明细 -- 回购款
	 */
	public static final String DIC_ID_0009="0009";//回购款
	/**
	 * 资金明细 -- 退保证金
	 */
	public static final String DIC_ID_0010="0010";//退保证金
	/**
	 * 资金明细 -- 名义价款
	 */
	public static final String DIC_ID_0012="0012";//名义价款
	/**
	 * 资金明细 -- 服务费
	 */
	public static final String DIC_ID_0015="0015";//服务费
	
	/**
	 * 融资项目类型-整体
	 * FIN_TYPE
	 */
	public static final String FIN_TYPE_1="1";	
	/**
	 * 融资项目类型-资产证券化
	 * FIN_TYPE
	 */
	public static final String FIN_TYPE_2="2";	
	
	/**
	 * 融资项目类型-P2P
	 * FIN_TYPE
	 */
	public static final String FIN_TYPE_3="3";
	/**
	 * 融资项目类型-按项目
	 * FIN_TYPE
	 */
	public static final String FIN_TYPE_4="4";	
	
	/**
	 * 还款计划生成方式 -- 按合同
	 * CRE_PLN_FLAG
	 */
	public static final String CRE_PLN_FLAG_1="1";	
	
	/**
	 * 还款计划生成方式 -- 按租赁物
	 * parm_dic - CRE_PLN_FLAG
	 */
	public static final String CRE_PLN_FLAG_2="2";	
	
	/**
	 * 租赁物状态 -- 录入
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_01="01";	
	
	/**
	 * 租赁物状态 -- 申请提交
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_02="02";
	
	/**
	 * 租赁物状态 -- 审批通过
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_03="03";
	
	
	/**
	 * 租赁物状态 -- 生效
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_04="04";
	
	
	/**
	 * 租赁物状态 -- 待产权转移
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_05="05";
	
	/**
	 * 租赁物状态 -- 否决
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_06="06";
	
	/**
	 * 租赁物状态 -- 终止
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_07="07";
	
	/**
	 * 租赁物状态 -- 产权转移中
	 * parm_dic - LEASE_STS
	 */
	public static final String LEASE_STS_08="08";
	
	
	/**
	 * 活动模板类型 -- 个人客户
	 * parm_dic - EVENT_MODEL_TYPE
	 */
	public static final String EVENT_MODEL_TYPE_1="1";
	
	/**
	 * 活动模板类型 -- 对公客户
	 * parm_dic - EVENT_MODEL_TYPE
	 */
	public static final String EVENT_MODEL_TYPE_2="2";
	
	/**
	 * 活动模板类型 -- 合同
	 * parm_dic - EVENT_MODEL_TYPE
	 */
	public static final String EVENT_MODEL_TYPE_3="3";
	
	/**
	 * 活动信息类型 -- 风险级
	 * parm_dic - EVENT_TYPE
	 */
	public static final String EVENT_TYPE_1="1";
	
	/**
	 * 活动信息类型 -- 提示级
	 * parm_dic - EVENT_TYPE
	 */
	public static final String EVENT_TYPE_2="2";

	/**
	 * 利率浮动方式 -- 按比例浮动
	 * parm_dic - FLOAT_TYPE
	 */
	public static final String FLOAT_TYPE_1="1";

	/**
	 * 利率浮动方式 -- 按值浮动
	 * parm_dic - FLOAT_TYPE
	 */
	public static final String FLOAT_TYPE_2="2";
	
	/**
	 * 租赁物签订申请状态---登记
	 */
	public static final String APP_LEASE_SIGN_STS_1="1";
	
	/**
	 * 租赁物签订申请状态---完成
	 */
	public static final String APP_LEASE_SIGN_STS_2="2";

	/**
	 * 租赁物签订申请序列
	 */
	public static final String APP_LEASE_SIGN_SEQ="APP_LEASE_SIGN_SEQ";
	
	/**
	 * 抵押物类型 -- 交易类应收账款
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_01="01";

	/**
	 * 抵押物类型 -- 居住用房地产
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_02="02";

	/**
	 * 抵押物类型 -- 商用房地产 
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_03="03";

	/**
	 * 抵押物类型 -- 流动资产
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_04="04";

	/**
	 * 抵押物类型 -- 机器设备
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_05="05";

	/**
	 * 抵押物类型 -- 交通运输设备
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_06="06";

	/**
	 * 抵押物类型 -- 工程机械
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_07="07";

	/**
	 * 抵押物类型 -- 通用设备
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_09="09";

	/**
	 * 质押物类型 -- 土地使用
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_10="10";

	/**
	 * 质押物类型 -- 专用设备
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_11="11";

	/**
	 * 质押物类型 -- 在建工程
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_12="12";

	/**
	 * 质押物类型 -- 车辆
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_13="13";

	/**
	 * 质押物类型 -- 存货
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_15="15";

	/**
	 * 质押物类型 -- 仓单
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_16="16";

	/**
	 * 质押物类型 -- 票据
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_17="17";

	/**
	 * 质押物类型 -- 保单
	 * parm_dic - COLLATERAL_TYPE
	 */
	public static final String COLLATERAL_TYPE_18="18";
	
	/**
	 * 租后检查任务类型--定期
	 * parm_dic--LLC_TASK_TYPE
	 */
	public static final String LLC_TASK_TYPE_1="1";
	/**
	 * 租后检查任务类型--不定期
	 * parm_dic--LLC_TASK_TYPE
	 */
	public static final String LLC_TASK_TYPE_2="2";
	
	/**
	 * 接收人类型（五级分类，租后检查任务）--角色
	 * parm_dic--REV_TYPE
	 */
	public static final String REV_TYPE_1="1";
	/**
	 * 接收人类型（五级分类，租后检查任务）--个人
	 * parm_dic--REV_TYPE
	 */
	public static final String REV_TYPE_2="2";
	/**
	 * 接收人类型（五级分类，租后检查任务）--客户经理
	 * parm_dic--REV_TYPE
	 */
	public static final String REV_TYPE_3="3";
	
	/**
	 * 检查规则（五级分类，租后检查任务）--固定日期检查
	 * parm_dic-FIVE_CHECK_RULE
	 */
	public static final String FIVE_CHECK_RULE_1="1";
	/**
	 * 检查规则（五级分类，租后检查任务）--顺延检查
	 * parm_dic--FIVE_CHECK_RULE
	 */
	public static final String FIVE_CHECK_RULE_2="2";
	
	/**
	 * 五级分类任务状--登记
	 * parm_dic--FIVE_TASK_STS
	 */
	public static final String FIVE_TASK_STS_1="1";
	/**
	 * 五级分类任务状--完成
	 * parm_dic--FIVE_TASK_STS
	 */
	public static final String FIVE_TASK_STS_2="2";
	
	/**
	 * 五级分类检查频率--月
	 * parm_dic---FIVE_CHECK_PERIOD
	 */
	public static final String FIVE_CHECK_PERIOD_1="1";
	
	/**
	 * 五级分类检查频率--季
	 * parm_dic---FIVE_CHECK_PERIOD
	 */
	public static final String FIVE_CHECK_PERIOD_2="2";
	
	/**
	 * 五级分类检查频率--半年
	 * parm_dic---FIVE_CHECK_PERIOD
	 */
	public static final String FIVE_CHECK_PERIOD_3="3";
	
	/**
	 * 五级分类检查频率--年
	 * parm_dic---FIVE_CHECK_PERIOD
	 */
	public static final String FIVE_CHECK_PERIOD_4="4";
	
	public static final String AUTH_MODE_1 = "1"; //经销商授信
	
	public static final String AUTH_MODE_2 = "2"; //厂商授信
	
	public static final String AUTH_MODE_3 = "3";//经销商额度分配
	
	public static final String AUTH_USE_MODE_1 = "1"; //占用租赁公司给经销商分配的授信台账
	
	public static final String AUTH_USE_MODE_2 = "2";//占用厂商给经销商分配的授信台账
	
	public static final String AUTH_USE_MODE_3 = "3";//直接占用厂商的授信台账

			
	
	
	public static final String AUTH_APP_NO = "201641610533158";


	/**
	 * 黑名单状态--生效
	 * parm_dic---BLACK_STS
	 */
	public static final String BLACK_STS_ACTIVE="1";
	
	
	/**
	 * 黑名单状态--失效
	 * parm_dic---BLACK_STS
	 */
	public static final String BLACK_STS_UNACTIVE="2";
	
	/**
	 * 保险定义状态--启用
	 * parm_dic---INS_DEF_STATUS
	 */
	public static final String INS_DEF_STATUS_ACTIVE="1";
	
	/**
	 * 保险定义状态--停用
	 * parm_dic---INS_DEF_STATUS
	 */
	public static final String INS_DEF_STATUS_UNACTIVE="2";
	
	/**
	 * 1.等于 
	 * opSymbol_1
	 */
	public static final String OPSYMBOL_1 = "1";
	/**
	 * 2.大于 
	 * opSymbol_2
	 */
	public static final String OPSYMBOL_2 = "2";
	/**
	 * 3.小于
	 * opSymbol_3
	 */
	public static final String OPSYMBOL_3 = "3";
	/**
	 *  4.大于等于
	 * opSymbol_4
	 */
	public static final String OPSYMBOL_4 = "4";
	/**
	 * 5.小于等于
	 * opSymbol_5
	 */
	public static final String OPSYMBOL_5 = "5";
	
	/**
	 * 收支日志编号
	 */
	public static final String BUDGET_LOG_SEQ="BUDGET_LOG_SEQ";
	
	/**
	 * 融资项目状态--登记
	 * parm_dic---FIN_STS
	 */
	public static final String FIN_STS_REGISTER="1";
	
	/**
	 * 融资项目状态--撤销
	 * parm_dic---FIN_STS
	 */
	public static final String FIN_STS_CANCEL="2";
	
	/**
	 * 融资项目状态--生效
	 * parm_dic---FIN_STS
	 */
	public static final String FIN_STS_EFFECT="3";
	
	/**
	 * 融资项目状态--完成
	 * parm_dic---FIN_STS
	 */
	public static final String FIN_STS_FINISHED="4";
	
	/**
	 * 表单单字段编辑权限  客户
	 * 
	 */
	public static final String FORM_EDIT_FLAG_CUS ="1";
	/**
	 * 表单单字段编辑权限  押品
	 * 
	 */
	public static final String FORM_EDIT_FLAG_PLE ="2";
	/**
	 * 表单单字段编辑权限  业务
	 * 
	 */
	public static final String FORM_EDIT_FLAG_BUS ="3";
	/**
	 * 表单单字段编辑权限  业务
	 *
	 */
	public static final String FORM_EDIT_FLAG_FILE ="4";
	/**
	 * 字段类型 -1 字符串
	 * 
	 */
	public static final String FIELD_TYPE_1 ="1";
	/**
	 * 字段类型 -2 字典项
	 * 
	 */
	public static final String FIELD_TYPE_2 ="2";
	/**
	 * 字段类型 -3日期
	 * 
	 */
	public static final String FIELD_TYPE_3 ="3";
	/**
	 * 字段类型 -4 金额
	 * 
	 */
	public static final String FIELD_TYPE_4 ="4";
	/**
	 * 字段类型 -5 百分比
	 * 
	 */
	public static final String FIELD_TYPE_5 ="5";
	/**
	 * 字段类型 -6 整型
	 *
	 */
	public static final String FIELD_TYPE_6 ="6";
	/**
	 * 信息变更类型--开户
	 * parm_dic---
	 */
	public static final String CHANGE_TYPE_OPEN ="1";
	
	/**
	 * 信息变更类型--预警
	 * parm_dic---
	 */
	public static final String CHANGE_TYPE_WARN ="2";
	
	/**
	 * 信息变更类型--消息
	 * parm_dic---
	 */
	public static final String CHANGE_TYPE_INFO ="3";
	
	/**
	 * 信息变更类型--注销
	 * parm_dic---
	 */
	public static final String CHANGE_TYPE_CANCEL ="4";
	
	
	/**
	 * 信息变更业务类型--客户
	 * parm_dic---
	 */
	public static final String BIZ_TYPE_CUS ="1";
	
	/**
	 * 信息变更业务类型--业务
	 * parm_dic---
	 */
	public static final String BIZ_TYPE_APP ="2";
	
	/**
	 * 评级结构--未评级
	 * parm_dic---GRADE
	 */
	public static final String GRADE_0 ="0";
	
	/**
	 * 项目评级场景号
	 * evalinterface 评级借口
	 */
	public static final String  PROJECT_SCEN_NO ="1000000084";
	/**
	 * 数据权限编号
	 */
	public static final String PMS_DATA_RANG_NO = "1000000055";
	/**
	 * 资产负债表
	 */
	public static final String FIN_REPORT_TYPE_1 = "1";
	/**
	 * 利润分配表
	 */
	public static final String FIN_REPORT_TYPE_2 = "2";
	/**
	 * 现金流量表
	 */
	public static final String FIN_REPORT_TYPE_3 = "3";

	/**
	 * 科目余额表
	 */
	public static final String FIN_REPORT_TYPE_5 = "5";

	/** 
	 * 合同状态 -- 未补录
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_UN_SIGN = "0"; 
	/** 
	 * 合同状态 -- 未提交
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_UN_SUBMIT = "1"; 
	/** 
	 * 合同状态 -- 流程中
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_PROCESS = "2"; 
	/** 
	 * 合同状态 -- 退回
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_SEND_BACK = "3"; 
	/** 
	 * 合同状态 -- 审批通过
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_PASS = "4"; 
	/** 
	 * 合同状态 -- 已否决
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_DISAGREE = "5"; 
	
	/** 
	 * 合同状态 -- 合同放款完结
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_PUTOUT = "6"; 
	
	/** 
	 * 合同状态 -- 合同已完结
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_FINISH = "7"; 
	/** 
	 * 合同状态 -- 已封档
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_SEAL = "8"; 
	
	/**
	 * 合同状态 -- 补充资料(合同审批退回第一岗位处理)
	 * parm_dic - PACT_STS
	 */
	public static final String PACT_STS_SUPPLEMENT = "9";
	
	/** 
	 * 放款申请状态 -- 申请
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_UN_SIGN = "0"; 
	/** 
	 * 放款申请状态  -- 未提交
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_UN_SUBMIT = "1"; 
	/** 
	 * 放款申请状态  -- 审批中
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_PROCESS = "2"; 
	/** 
	 * 放款申请状态  -- 否决
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_DISAGREE = "3"; 
	/** 
	 * 放款申请状态  -- 审批通过
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_PASS = "4"; 
	/** 
	 * 放款申请状态  -- 放款已复核
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_AGREE = "5"; 
	/** 
	 * 放款申请状态  -- 还款复核中
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_REPAY = "6"; 
	/** 
	 * 放款申请状态  -- 完结
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_FINISH = "7"; 
	/**
	 * 放款申请状态 -- 补充资料(放款审批退回第一岗位处理)
	 * parm_dic - FINC_STS
	 */
	public static final String FINC_STS_SUPPLEMENT = "8";
	/** 
	 * 借据逾期状态  -- 未逾期
	 * parm_dic - OVERDUE_STS
	 */
	public static final String OVERDUE_STS_0 = "0"; 
	/** 
	 * 借据逾期状态  -- 逾期
	 * parm_dic - OVERDUE_STS
	 */
	public static final String OVERDUE_STS_1 = "1"; 
	
	
	/** 
	 * 利息计算区间状态-- 1-算头不算尾 
	 * 
	 */
	public static final String CALC_INTST_FLAG_1 = "1"; 
	/** 
	 * 利息计算区间状态-- 2-首尾都计算
	 */
	public static final String CALC_INTST_FLAG_2 = "2"; 
	
	/**
	 * 1-显示结束日期 
	 */
	public static final String PACT_ENDDATE_SHOW_FLAG_1 = "1"; 
	/**
	 * 2-显示结束日期减一天
	 */
	public static final String PACT_ENDDATE_SHOW_FLAG_2 = "2";
	/**
	 * 3-实际结束日期减一天，显示结束日期再减一天
	 */
	public static final String PACT_ENDDATE_SHOW_FLAG_3 = "3";

	/**
	 * 合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为4时值为显示日期减去一天 实际结束日期减去一天  显示日期 和 实际结束一致  均是少一天
	 */
	public static final String PACT_ENDDATE_SHOW_FLAG_4 = "4";
	
	/**
	 * 到期还本按月结息支持按日计息（正常还款时是收取到当天的利息还是收取整月的利息）1 收取到当天利息 
	 */
	public static final String REPAY_CHARGE_INTST_FLAG_1="1";
	/**
	 * 到期还本按月结息支持按日计息（正常还款时是收取到当天的利息还是收取整月的利息）2  收取整月利息
	 */
	public static final String REPAY_CHARGE_INTST_FLAG_2="2";

	/** 
	 * 还款状态 -- 未还款
	 * parm_dic - OUT_FLAG
	 */
	public static final String OUT_FLAG_0 = "0"; 
	/** 
	 * 还款状态 -- 已还款
	 * parm_dic - OUT_FLAG
	 */
	public static final String OUT_FLAG_1 = "1"; 
	/** 
	 * 还款状态 -- 部分还款
	 * parm_dic - OUT_FLAG
	 */
	public static final String OUT_FLAG_2 = "2"; 
	/** 
	 * 还款状态 -- 逾期欠款
	 * parm_dic - OUT_FLAG
	 */
	public static final String OUT_FLAG_3 = "3"; 
	
	/**
	 * 还款历史——还款类型：1-正常还款
	 */
	public static final String REPAY_HIS_TYPE_1 = "1";
	/**
	 * 还款历史——还款类型：2-逾期还款
	 */
	public static final String REPAY_HIS_TYPE_2 = "2";
	/**
	 * 还款历史——还款类型：3-提前还款
	 */
	public static final String REPAY_HIS_TYPE_3 = "3";
	/**
	 * 还款历史——还款类型：4-上收息时收取
	 */
	public static final String REPAY_HIS_TYPE_4 = "4";
	/**
	 * 还款历史——还款类型：5-放款时收取（存在固定还款日且预先支付利息方式是 放款时收取时）
	 */
	public static final String REPAY_HIS_TYPE_5 = "5"; 
	/**
	 * 还款历史——还款类型：6-放款时收取（放款时收取一次性费用）
	 */
	public static final String REPAY_HIS_TYPE_6 = "6";
	/**
	 * 还款历史——还款类型：7-定金
	 */
	public static final String REPAY_HIS_TYPE_7 = "7";
	/**
	 * 还款历史——还款类型：8-首付
	 */
	public static final String REPAY_HIS_TYPE_8 = "8";
	/**
	 * 还款历史——还款类型：9-代偿
	 */
	public static final String REPAY_HIS_TYPE_9 = "9";
	/**
	 * 还款历史——还款类型：A-提前结清A
	 */
	public static final String REPAY_HIS_TYPE_A = "A";
	/** 
	 * 费用收取方式 -- 一次性
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_COLLECT_TYPE_1 = "1"; 
	/** 
	 * 费用收取方式 -- 按照还款计划期数
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_COLLECT_TYPE_2 = "2"; 
	/** 
	 * 费用收取方式 -- 按年收取
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_COLLECT_TYPE_3 = "3"; 
	/** 
	 * 费用收取方式 -- 按季收取
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_COLLECT_TYPE_4 = "4"; 
	/** 
	 * 费用收取方式 -- 按月收取
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_COLLECT_TYPE_5 = "5"; 
	
	/**
	 * 按天收取 
	 */
	public static final String FEE_COLLECT_TYPE_6= "6"; 
	
	/**
	 * 一次按年收取
	 */
	public static final String FEE_COLLECT_TYPE_7= "7"; 
	
	/** 
	 * 费用类型 -- 收费项
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_ITEM_TYPE_SHOU = "1"; 
	/** 
	 * 费用类型 -- 退费项
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_ITEM_TYPE_TUI = "2";
	/** 
	 * 费用类型 -- 冲抵项
	 * parm_dic - FEE_COLLECT_TYPE
	 */
	public static final String FEE_ITEM_TYPE_CHONG = "3";

	/**
	 * 业务模式 -- 动产质押
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_1 = "1"; 
	/** 
	 * 业务模式 -- 仓单
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_2 = "2"; 
	/** 
	 * 业务模式 -- 保单
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_3 = "3"; 
	/** 
	 * 业务模式 -- 保兑仓
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_4 = "4"; 
	
	/** 
	 * 业务模式 -- 应收账款融资
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_5 = "5"; 
	/** 
	 * 业务模式 -- 应收账款管理
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_6 = "6"; 
	/** 
	 * 业务模式 -- 传统信贷
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_7 = "7"; 
	/** 
	 * 业务模式 -- 消费金融
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_8 = "8"; 
	/** 
	 * 业务模式 -- 互联网小贷
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_9 = "9"; 
	/** 
	 * 业务模式 --  融资性担保
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_10 = "10"; 
	/** 
	 * 业务模式 --  中汇鑫得业务模式
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_12 = "12";

	/**
	 * 业务模式 -- 中汇鑫德-临汾地区-惠商通卡业务流程<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_14 = "14";	
	/**
	 * 业务模式 -- 中汇鑫德-惠商通卡（太原）业务流程<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_15 = "15";
	/**
	 * 业务模式 -- 中汇鑫德-临汾地区-小贷业务流程<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_16 = "16";

	/**
	 * 业务模式 -- 中汇鑫德_临汾地区_小额业务担保流程<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_17 = "17";

	/**
	 * 业务模式 -- 中汇鑫德_临汾地区_贷款用信业务<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_18 = "18";

	/**
	 * 业务模式 -- 中汇鑫德_临汾地区_承兑敞口业务<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_19 = "19";

	/**
	 * 业务模式 -- 中汇鑫德_临汾地区_保证金业务<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_20 = "20";

	/**
     * 业务模式--保理业务
     */
    public static final String BUS_MODEL_13 = "13";
	/**
	 * 业务模式 -- 金宝行_信贷<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_21 = "21";
	
	/**
	 * 业务模式 --网信_现金贷<br>
	 * parm_dic - BUS_MODEL
	 */

	/**
	 * 业务模式 --网信-车商贷<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_27 = "27";

	/**
	 * 业务模式 --展期业务<br>
	 * parm_dic - BUS_MODEL
	 */
	public static final String BUS_MODEL_99= "99";

	/**
	 * 催收状态--登记
	 * parm_dic---RECALL_STS 
	 */
	public static final String RECALL_STS_1="1";
	/**
	 * 催收状态--进行中
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_2="2";
	/**
	 * 催收状态--审批中
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_3="3";
	/**
	 * 催收状态--退回
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_4="4";
	/**
	 * 催收状态--超期
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_5="5";
	/**
	 * 催收状态--关闭
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_6="6";
	/**
	 * 催收状态--关闭
	 * parm_dic---RECALL_STS
	 */
	public static final String RECALL_STS_9="9";
	/**
	 * 催收执行方式--自动
	 */
	public static final String RECALL_TYPE_1="1";
	/**
	 * 催收执行方式--人工
	 */
	public static final String RECALL_TYPE_2="2";
	/**
	 * 催收发起方式--系统催收
	 */
	public static final String START_RECALL_WAY_1="1";
	/**
	 * 催收发起方式--人工催收
	 */
	public static final String START_RECALL_WAY_2="2";
	
	/**
	 * 评级对公流程编号
	 */
	public static final String  EVAL_CORP_WKF_NO ="320161858396635";
	
	
	/**
	 * 评级个人流程编号
	 */
	public static final String  EVAL_PER_WKF_NO ="320161858396635";
	
	/**
	 * 信用评级场景号(对公)
	 * evalinterface 评级接口
	 */
	public static final String  EVAL_CREDIT_SCEN_NO ="1000000001";
	
	
	/**
	 * 信用评级场景号(对公)
	 * evalinterface 评级接口
	 */
	public static final String  EVAL_CREDIT_PERS_SCEN_NO ="1000000085";
	/**
	 * 租赁方式--直租
	 * parm_dic----LEASE_TYPE
	 */
	public static final String LEASE_TYPE_1="1";
	/**
	 * 租赁方式--售后回租
	 * parm_dic----LEASE_TYPE
	 */
	public static final String LEASE_TYPE_2="2";
	/**
	 * 租赁方式--厂商租赁
	 * parm_dic----LEASE_TYPE
	 */
	public static final String LEASE_TYPE_3="3";
	
	/**
	 * 租赁方式--委托租赁
	 * parm_dic----LEASE_TYPE
	 */
	public static final String LEASE_TYPE_4="4";
	
	/**
	 * 业务控制参数--新增经销商时是否新增对应的机构信息
	 * parm_dic----
	 */
	public static final String BUS_CTL_PARM_PROXY_ORG="PROXY_ORG_INFO_ADD_FLAG";
	
	
	/**
	 * 业务控制参数--新增供货商时是否新增对应的机构信息
	 * parm_dic----
	 */
	public static final String BUS_CTL_PARM_SUPPLIER_ORG="SUPPLIER_ORG_INFO_ADD_FLAG";
	
	/**
	 *机构类型--经销商
	 * parm_dic----BR_TYPE
	 */
	public static final String BR_TYPE_3="3";
	
	/**
	 *机构类型--供货商
	 * parm_dic----BR_TYPE
	 */
	public static final String BR_TYPE_4="4";
	
	/**
	 *机构状态--启用
	 * parm_dic----USER_STS
	 */
	public static final String USER_STS_1="1";
	
	/**
	 *业务品种状态生效
	 * parm_dic----暂无字典项
	 */
	public static final String USE_FLAG_1="1";
	
	/**
	 *业务品种状态失效
	 * parm_dic----暂无字典项
	 */
	public static final String USE_FLAG_0="0";
	
	/**
	 *关注点生效
	 * parm_dic----暂无字典项
	 */
	public static final String CHART_POINT_STS_1="1";
	
	/**
	 *关注点失效
	 * parm_dic----暂无字典项
	 */
	public static final String CHART_POINT_STS_0="0";
	
	/**
	 * 租赁物出险-出险登记
	 *  parm_dic----CLA_STATUS
	 */
	public static final String CLA_STATUS_STS_1="1";
	
	/**
	 * 租赁物出险-理赔登记
	 * parm_dic----CLA_STATUS
	 */
	public static final String CLA_STATUS_STS_2="2";
	
	/**
	 * 融资管理-合同已选
	 * parm_dic----暂无字典项
	 */
	public static final String FIN_CHOOSTS_CHOOSE = "已选";
	
	/**
	 * 融资管理-合同未选
	 * parm_dic----暂无字典项
	 */
	public static final String FIN_CHOOSTS_UNCHOOSE = "未选"; 
	
	/**
	 * 还款周期类型 -- 月
	 * parm_dic - LEAVE_CYCLE
	 */
	public static final String LEAVE_CYCLE_MONTH="3";
	
	/**
	 * 还款周期类型 -- 季
	 * parm_dic - LEAVE_CYCLE
	 */
	public static final String LEAVE_CYCLE_SEASON="4";
	
	/**
	 * 还款周期类型 -- 年
	 * parm_dic - LEAVE_CYCLE
	 */
	public static final String LEAVE_CYCLE_YEAR="5";
	
	/**
	 * 还款周期 -- 1
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_1="1";
	
	/**
	 * 还款周期 -- 2
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_2="2";
	
	/**
	 * 还款周期 -- 3
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_3="3";
	
	
	/**
	 * 还款周期 -- 4
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_4="4";
	
	
	/**
	 * 还款周期 -- 5
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_5="5";
	
	/**
	 * 还款周期 -- 6
	 * parm_dic - LEAVE_CYCLE_TERM
	 */
	public static final String LEAVE_CYCLE_TERM_6="6";
	/**
	 * 保证金注资--1
	 * 
	 */
	public static final String CUS_DEPOSIT_INJECT="1";
	/**
	 * 保证金撤资--2
	 * 
	 */
	public static final String CUS_DEPOSIT_OUT="2";
	/**
	 * 抵扣租金--3
	 * 
	 */
	public static final String CUS_DEPOSIT_DEDU="3";
	/**
	 * 评级入口--1 客户评级
	 *
	 */
	public static final String USE_TYPE_1 = "1";
	/**
	 * 评级入口--2 业务评级
	 *
	 */
	public static final String USE_TYPE_2 = "2";
	/**
	 * 评级入口--3 授信评级
	 *
	 */
	public static final String USE_TYPE_3 = "3";
	/**
	 * 评级入口--4 额度测算
	 *
	 */
	public static final String USE_TYPE_4 = "4";
	/**
	 * 合同模板类型--汽车融资租赁业
	 */
	public static final String TMPT_TYPE_1 ="1";
	/**
	 * 合同模板类型--非汽车融资租赁业务
	 */
	public static final String TMPT_TYPE_2 ="2";
	/**
	 * 合同模板类型--准入合作
	 */
	public static final String TMPT_TYPE_3 ="3";
	/**
	 * 合同模板类型--保兑仓业务
	 */
	public static final String TMPT_TYPE_4 ="4";
	/**
	 * 合同模板类型细分--融资租赁合同
	 */
	public static final String TMPT_TYPE_SUB_1 = "1";
	/**
	 * 合同模板类型细分--售后回租合同
	 */
	public static final String TMPT_TYPE_SUB_2 = "2";
	/**
	 * 合同模板类型细分--购买合同
	 */
	public static final String TMPT_TYPE_SUB_3 = "3";
	/**
	 * 合同模板类型细分--合作协议
	 */
	public static final String TMPT_TYPE_SUB_4 = "4";
	/**
	 * 合同模板类型细分--保证合同
	 */
	public static final String TMPT_TYPE_SUB_5 = "5";
	/**
	 * 合同模板类型细分--租赁合同补充协议
	 */
	public static final String TMPT_TYPE_SUB_6 = "6";
	/**
	 * 合同模板类型细分--购买合同补充协议
	 */
	public static final String TMPT_TYPE_SUB_7 = "7";
	/**
	 * 合同文档表序列
	 */
	public static final String SEQ_REL_NO = "TEMPLET_CONT_REL_SEQ";
	/**
	 * 使用状态 使用中
	 * parm_dic - USE_STS
	 */
	public static final String USE_STS_INUSE = "1";
	/**
	 * 使用状态 使用完
	 * parm_dic - USE_STS
	 */
	public static final String USE_STS_USED = "2";
	/**
	 * 使用状态 归还
	 * parm_dic - USE_STS
	 */
	public static final String USE_STS_RETURN = "3";
	/**
	 * 使用状态 未使用
	 * parm_dic - USE_STS
	 */
	public static final String USE_STS_UNUSE = "4";
	/**
	 * 盖章类型 内部使用
	 * parm_dic - USE_TYPE
	 */
	public static final String USE_TYPE_IN = "1";
	/**
	 * 盖章类型 外借
	 * parm_dic - USE_TYPE
	 */
	public static final String USE_TYPE_OUT = "2";
	
	/**
	 * 视角编号 --车业务视角编号
	 * parm_dic - PMS_VIEWPOINT_NAME
	 */
	public static final String VP_NO_CAR_APP = "2";
	
	/**
	 * 视角编号 --非车业务视角编号
	 * parm_dic - PMS_VIEWPOINT_NAME
	 */
	public static final String VP_NO_NOT_CAR_APP = "14";
	
	public static final String FIN_AUTH_APP_SEQ="FIN_AUTH_APP_SEQ";
	
	/**
	 * 流程自定义机构级别--向下一级
	 */
	public static final String SELF_CANDIATE_DOWN_ONE="down_one";
	
	/**
	 * 流程自定义机构级别--向下二级
	 */
	public static final String SELF_CANDIATE_DOWN_TWO="down_two";
	
	/**
	 * 流程自定义机构级别--向下全部
	 */
	public static final String SELF_CANDIATE_DOWN_ALL="down_all";
	
	/**
	 * 流程自定义机构级别--本机构所有同级机构
	 */
	public static final String SAME_ORG = "same";
	
	/**
	 * 流程自定义机构级别--本机构及上级机构
	 */
	public static final String SELF_OR_UP_ONE = "self_up_one";
	
	/**
	 * 流程自定义机构级别--本区域
	 */
	public static final String SELF_REGION = "self_region";
	
	/**
	 * 授信协议未签订
	 * parm_dic - AUTH_STS
	 */
	public static final String AUTH_STS_UNSIGN = "0";
	
	/**
	 * 授信协议签订
	 * parm_dic - AUTH_STS
	 */
	public static final String AUTH_STS_SIGN = "1";
	
	/**
	 * 合同归档状态
	 * parm_dic - ARCHI_STS
	 * 未归档
	 */
	public static final String ARCHI_STS_NOT_ARCHIVED = "0";
	
	/**
	 * 合同归档状态
	 * parm_dic - ARCHI_STS
	 * 已归档
	 */
	public static final String ARCHI_STS_ALREADY_ARCHIVED = "1";
	
	/**
	 * 合同归档状态
	 * parm_dic - ARCHI_STS
	 * 补充待归档
	 */
	public static final String ARCHI_STS_SUPPLE_BE_ARCHIVED = "2";
	
	/**
	 * 合同归档状态
	 * parm_dic - ARCHI_STS
	 * 待归档
	 */
	public static final String ARCHI_STS_BE_ARCHIVED = "3";
	/**
	 * 行业分类
	 */
	public static String  nmdWay = "";
	/**
	 * 行政区划
	 */
	public static String  nmdArea = "";
	/**
	 * 银行城市
	 */
	public static String  bankArea = "";
	
	/**
	 * 展业区域
	 */
	public static String  exhibitionArea = "";
	
	/**
	 * 合同的融资状态
	 * parm_dic - CON_FIN_STS
	 * 待融资
	 */
	public static final String CON_FIN_STS_BE_FIN = "01";
	
	/**
	 * 合同的融资状态
	 * parm_dic - CON_FIN_STS
	 * 已融资
	 */
	public static final String CON_FIN_STS_ALREADY_FIN = "02";
	
	/**
	 * 放款申请对公
	 */
	public static final String APP_PROJECT_ADVANCE_CORP = "app_project_advance_corp_2016920182123390";
	
	/**
	 * 放款申请个人
	 */
	public static final String APP_PROJECT_ADVANCE_PERS = "app_project_davance_pers_2016920174238941";
	
	/**
	 * 法律诉讼序列
	 */
	public static final String ZC_LAW_INF_SEQ = "ZC_LAW_INF_SEQ";
	/**
	 * 基础利率的状态
	 * parm_dic - RATE_STS
	 * 未生效
	 */
	public static final String RATE_STS_NO_EFFECT = "0";
	
	/**
	 * 基础利率的状态
	 * parm_dic - RATE_STS
	 * 正常
	 */
	public static final String RATE_STS_EFFECT = "1";
	
	/**
	 * 沈浩兵添加
	 * 押品新增表单模板编号
	 */
	public static final String pledge_form_id_add = "pledyadd0001";
	/**
	 * 沈浩兵添加
	 * 押品详情表单模板编号
	 */
	public static final String pledge_form_id_detail = "pledydetail0001";
	
	/**
	 * 沈浩兵添加
	 * 押品新增表单模板编号 清单
	 */
	public static final String pledge_class_form_add = "ple0009";
	/**
	 * 沈浩兵添加
	 * 押品详情表单模板编号 清单
	 */
	public static final String pledge_class_form_detail = "ple0010";
	/**
	 * 沈浩兵添加
	 * 新登记
	 */
	public static final String pledge_new_add_sts = "1";
	/**
	 * 沈浩兵添加
	 * 已应用
	 */
	public static final String pledge_quoted_sts = "2";
	/**
	 * 沈浩兵添加
	 * 已入库
	 */
	public static final String pledge_import_sts = "3";
	/**
	 * 沈浩兵添加
	 * 已出库
	 */
	public static final String pledge_export_sts = "4";
	/**
	 * 沈浩兵添加
	 * 已处理
	 */
	public static final String pledge_deal_sts = "5";
	/**
	 * 沈浩兵添加
	 * 未入库
	 */
	public static final String pledge_detail_no_import = "1";
	/**
	 * 沈浩兵添加
	 * 已入库
	 */
	public static final String pledge_detail_import = "2";
	/**
	 * 沈浩兵添加
	 * 部分出库
	 */
	public static final String pledge_detail_export = "3";
	/**
	 * 沈浩兵添加
	 * 全部出库
	 */
	public static final String pledge_detail_all_export = "4";
	/**
	 * 动产质押业务流程
	 */
	public static final String CHATTEL_MORTGAGE_WORKFLOW_NO = "201672316638"; //动产质押业务流程
	
	/**
	 * 仓单业务流程
	 */
	public static final String WAREHOUSE_RECEIPTS_WORKFLOW_NO = "201682617036"; //仓单融资业务流程
	
	/**
	 * 保兑仓业务流程
	 */
	public static final String CONFIRMED_WAREHOUSE_WORKFLOW_NO = "201692914183"; //保兑仓业务流程
	
	/**
	 * 应收账款融资业务流程
	 */
	public static final String ACCNT_FINANCING_WORKFLOW_NO = "2016111811197"; //应收账款融资业务流程
	/**
	 * 小贷业务流程
	 */
	public static final String LOAN_WORKFLOW_NO = "2017322103545"; //应收账款融资业务流程
	/**
	 * 传统信贷业务流程
	 */
	public static final String TRADITION_CREDIT_WORKFLOW_NO = "2017413181916"; //传统信贷业务流程
	/**
	 * 消费金融业务流程
	 */
	public static final String CONSUME_FINANCE_WORKFLOW_NO = "20174149203"; //消费金融业务流程
	/**
	 * 互联网小贷业务流程
	 */
	public static final String INTERNET_LOAN_WORKFLOW_NO = "20174149910"; //互联网小贷业务流程
	/**
	 * 融资性担保流程
	 */
	public static final String FINA_GUARA_WORKFLOW_NO = "2017425144821"; //融资性担保流程
	/**
	 * 中汇鑫得流程
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO = "201752594256"; //中汇鑫得个贷业务流程

	/**
	 * 中汇鑫德_临汾地区_惠商通卡业务流程
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_1 = "zh_lf_201752410526505"; // 中汇鑫德_临汾地区_惠商通卡业务流程

	/**
	 * 中汇鑫德_临汾地区_小额业务担保流程
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_3 = "2017679750"; // 中汇鑫德_临汾地区_小额业务担保流程

	/**
	 * 中汇鑫德_临汾地区_贷款用信业务
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_4 = "201767192940"; // 中汇鑫德_临汾地区_贷款用信业务

	/**
	 * 中汇鑫德_临汾地区_承兑敞口
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_2_1 ="zh_lf_2_1_20176110423764";

	/**
	 * 中汇鑫德_临汾地区_保证金
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_2_2 ="20176894219";

	/**
	 * 中汇鑫德惠商通卡（太原）业务流程
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_TY = "201763163952"; // 中汇鑫德惠商通卡（太原）业务流程
	/**
	 * 中汇鑫德_临汾地区_小贷业务
	 */
	public static final String ZHOGNHUI_WORKFLOW_NO_LF_2 = "2017681111"; // 中汇鑫德_临汾地区_小贷业务流程
	/**
     *保理业务流程
     */
    public static final String FACTOR_WORKFLOW_NO = "201751019048904"; //互联网小贷业务流程
	/**
	 * 风险拦截维度
	 */
	public static final String risk_prevent_sce_gen = "CUS_TYPE|BUS_MODEL|VOU_TYPE|KIND_NO"; //风险拦截维度
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_01 ="01";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_02 ="02";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_03 ="03";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_04 ="04";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_05 ="05";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_06 ="06";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_07 ="07";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_08 ="08";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_09 ="09";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_10 ="10";
	/**
	 * 文件生成阶段
	 * parm_dic - GENERATE_PHASE
	 */
	public static final String GENERATE_PHASE_11 ="11";
	
	/**
	 * 
	 */
	public static final String DOCUTEMPLATE ="examDocuTemp.doc";
	/**
	 * 检查表单模板表单名称
	 */
	public static final String FORMTEMPLATE ="baseexaminfoNew";
	    
	/**
     * 贷后检查状态1申请中
     */
    public static final String EXAMINE_APPLY_STS="1";
    /**
     * 贷后检查状态2审批中
     */
    public static final String EXAMINE_APPROVE_STS="2";
    /**
     * 贷后检查状态3审批成功
     */
    public static final String EXAMINE_APPROV_SUCCESS_STS="3";
    /**
     * 贷后检查状态4否决
     */
    public static final String EXAMINE_VETO_STS="4";
    /**
     * 贷后检查风险级别 1高风险
     */
    public static final String EXAMINE_RISK_LEVEL_HIGH="1";
    /**
     * 贷后检查风险级别 2高风险
     */
    public static final String EXAMINE_RISK_LEVEL_MIDDLE="2";
    /**
     * 贷后检查风险级别 3高风险
     */
    public static final String EXAMINE_RISK_LEVEL_LOW="3";
    /**
     * 贷后检查阶段1已配置模型
     */
    public static final String EXAMINE_STAGE_MODEL="1";
    /**
     * 贷后检查阶段2已登记检查卡
     */
    public static final String EXAMINE_STAGE_CARD="2";
    /**
     * 贷后检查阶段3检查结论已提交
     */
    public static final String EXAMINE_STAGE_SUBMIT="3";
    /**
     * 贷后检查阶段4已完成
     */
    public static final String EXAMINE_STAGE_COMPLETE="4";
	/**
	 * 授信申请动态表单ID
	 * @author LJW
	 */
	public static final String CREDIT_FORM_ID = "creditapply0001";
	
	/**
	 * 客户授信场景编号
	 * @author LJW
	 */
	public static final String CUSTOMER_CREDIT_ID = "0000000022";
	
	/**
	 * 授信申请审批流程id
	 * @author LJW
	 */
	public static final String CREDIT_APPROVE_WKF_ID = "201733134412374";
	
	/**
	 * 授信业务流程检查前置条件 规则编号
	 * @author LJW
	 */
	public static final String CREDIT_RULES_NO = "CREDIT_RULES1";
	
	/**
	 * 授信模式 1 客户授信
	 * @author LJW
	 */
	public static final String CREDIT_MODEL_CUS = "1";
	/**
	 * 授信模式 2 项目授信
	 * @author LJW
	 */
	public static final String CREDIT_MODEL_PROJECT = "2";
	/**
	 * 授信模式 3 调整授信
	 * @author LJW
	 */
	public static final String CREDIT_MODEL_ADJUST = "3";
	/**
	 * 授信模式 4 临额调整
	 * @author LJW
	 */
	public static final String CREDIT_MODEL_TEMPORARY = "4";
	/**
	 * 授信类型9转贷授信
	 */
	public static final String CREDIT_TYPE_TRANSFER="9";
	/**
	 * 应收账款回购审批流程
	 * @author LJW
	 */
	public static final String REPO_APPROVE_WKF_ID = "201754174121";
	/**
	 * 应收账款转让审批流程
	 */
	public static final String RECE_TRAN_APPROVE_WKF_ID = "201755154038";
	/**
	 * 应收账款折让审批流程
	 */
	public static final String RECE_REBATE_APPROVE_WKF_ID = "2017515141420";
	/**
	 * 应收账款争议审批流程
	 */
	public static final String RECE_DISPUTED_APPROVE_WKF_ID = "2017516161614";
	/**
	 * 动产移库审批流程
	 */
	public static final String MOVEABLED_TRANSFFER_APPROVE_WKF_ID = "201761015758";
	
	/**
	 * 预算申请审批流程
	 */
	public static final String BUDGET_APPROVE_WKF_ID = "201761092216";
	
	/**
	 * 文件会签审批流程
	 */
	public static final String SIGN_FILE_APPROVE_WKF_ID = "2017612162417";
	
	/**
	 * 动产调价审批流程
	 */
	public static final String MOVEABLED_MODIFY_APPROVE_WKF_ID = "2017612174740";
	/**
	 *  动产提货审批流程
	 */
	public static final String MOVEABLED_CLAIM_APPROVE_WKF_ID = "2017612172847";
	/**
	 * 巡库审批流程
	 */
	public static final String MOVEABLED_PATROL_APPROVE_WKF_ID = "201761484335";
	/**
	 * 回购审批流程
	 */
	public static final String MOVEABLED_BUYBACK_APPROVE_WKF_ID = "2017619173012";
	/**
	 * 垫款审批流程
	 */
	public static final String ADVANCES_APPROVE_WKF_ID = "205001647686";
	/**
     * 五级分类 规则编号 个人客户
     * @author GuoXY
     */
    public static final String FIVECLASS_RULES_NO1 = "FIVE_CLASS1";
    /**
     * 五级分类 规则编号 企业客户
     * @author GuoXY
     */
    public static final String FIVECLASS_RULES_NO2 = "FIVE_CLASS2";
    

	/** 规则引擎-利率工厂 */
	public static final String RATE_RULE = "RATE_RULE";

	/**
     * 押品预警规则编号
     * @author GuoXY
     */
    public static final String COLLATERAL_WARNING = "collateralWarning1.0";
    /**
     * 围绕应收账款的转让、折让、争议、反转让交易的状态
     * 0：申请中
     */
    public static final String RECE_DEAL_APP_STS="0";
    /**
     * 围绕应收账款的转让、折让、争议、反转让交易的状态
     * 1：审批中
     */
    public static final String RECE_DEAL_APPROV_STS="1";
    /**
     * 围绕应收账款的转让、折让、争议、反转让交易的状态
     * 2：审批通过
     */
    public static final String RECE_DEAL_APPROV_SUCCESS_STS="2";
    /**
     * 围绕应收账款的转让、折让、争议、反转让交易的状态
     * 3：否决
     */
    public static final String RECE_DEAL_VETO_STS="3";
    /**
     * 围绕应收账款的转让、折让、争议、反转让交易的状态
     * 4：已确认
     */
    public static final String RECE_DEAL_AFFIRM_STS="4";
    /**
     * 担保登记。交易类型1质押
     */
    public static final String DEAL_TYPE_1="1";
    /**
     * 担保登记。交易类型2转让
     */
    public static final String DEAL_TYPE_2="2";
    /**
     * 担保登记。交易类型3抵押
     */
    public static final String DEAL_TYPE_3="3";
    /**
     * 押品业务模式 01应收账款
     */
    public static final String CLASS_MODEL_RECE="01";
    /**
     * 押品业务模式 02动产质押
     */
    public static final String CLASS_MODEL_MOVEABLE="02";
    /**
     * 押品业务模式 03仓单
     */
    public static final String CLASS_MODEL_WARE_RECEIPT="03";
    /**
     * 押品业务模式 04其他
     */
    public static final String CLASS_MODEL_OTHER="04";
    /**
     * 押品业务模式 05 融资租赁
     */
    public static final String CLASS_MODEL_LEASE="05";

    /**
     * 授信类型1授信新增
     */
    public static final String CREDIT_TYPE_ADD="1";
    /**
     * 授信类型2授信调整
     */
    public static final String CREDIT_TYPE_ADJUST="2";
	/**
	 * 授信类型2授信续签
	 */
	public static final String CREDIT_TYPE_RENEW="3";
	/**
	 * 授信类型授信意向
	 */
	public static final String CREDIT_TYPE_INTENTION="4";
	/**
	 * 授信类型授信意向
	 */
	public static final String CREDIT_TYPE_LINSHI="5";
    /**
     * 授信业务状态1申请中
     */
    public static final String CREDIT_APPLY_STS="1";
    /**
     * 授信业务状态2审批中
     */
    public static final String CREDIT_APPROVE_STS="2";
    /**
     * 授信业务状态3审批成功
     */
    public static final String CREDIT_APPROV_SUCCESS_STS="3";
    /**
     * 授信业务状态4否决
     */
    public static final String CREDIT_VETO_STS="4";
    /**
     * 授信业务状态5已签约
     */
    public static final String CREDIT_SIGN_STS="5";
    /**
     * 授信标识0未授信
     */
    public static final String CREDIT_FLAG_NO="0";
    /**
     * 授信标识1已授信
     */
    public static final String CREDIT_FLAG_YES="1";
    /**
     * 期限类型-月
     */
    public static final String TERM_TYPE_MONTH="1";
    /**
     * 期限类型-天
     */
    public static final String TERM_TYPE_DAY="2";
    /**
     * 是否在期限内标识0不在期限内
     */
    public static final String TERM_FLAG_NO="0";
    /**
     * 是否在期限内标识1在期限内
     */
    public static final String TERM_FLAG_YES="1";
    /**
     * 模板类型 word类型
     */
    public static final String TEMPLATE_SUFFIX_1="1";
    /**
     * 模板类型 excel类型
     */
    public static final String TEMPLATE_SUFFIX_2="2";
    /**
     * 模板类型 pdf类型
     */
    public static final String TEMPLATE_SUFFIX_3="3";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 归档待确认
	 */
	public static final String ARCHIVE_STATUS_01 = "01";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 已归档
	 */
	public static final String ARCHIVE_STATUS_02 = "02";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 已封档
	 */
	public static final String ARCHIVE_STATUS_03 = "03";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 移交待确认
	 */
	public static final String ARCHIVE_STATUS_04 = "04";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 已接收确认
	 */
	public static final String ARCHIVE_STATUS_05 = "05";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 借出申请中
	 */
	public static final String ARCHIVE_STATUS_06 = "06";

	/**
	 * 档案归档状态
	 * parm_dic - ARCHI_STATUS
	 * 已借出
	 */
	public static final String ARCHIVE_STATUS_07 = "07";

	/**
	 * 资料类型
	 * parm_dic - ARCHIVE_TYPE
	 *资料
	 */
	public static final String ARCHIVE_DOC_TYPE_01 = "1";

	/**
	 * 资料类型
	 * parm_dic - ARCHIVE_TYPE
	 * 合同
	 */
	public static final String ARCHIVE_DOC_TYPE_02 = "2";

	/**
	 * 资料类型
	 * parm_dic - ARCHIVE_TYPE
	 * 凭证
	 */
	public static final String ARCHIVE_DOC_TYPE_03 = "3";

	/**
	 * 资料类型
	 * parm_dic - ARCHIVE_TYPE
	 * 纸质资料
	 */
	public static final String ARCHIVE_DOC_TYPE_04 = "4";

	/**
	 * 资料类型
	 * parm_dic - ARCHIVE_TYPE
	 * 非系统生成合同
	 */
	public static final String ARCHIVE_DOC_TYPE_05 = "5";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *归档待确认
	 */
	public static final String ARCHIVE_DOC_STATE_01 = "01";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 * 在库
	 */
	public static final String ARCHIVE_DOC_STATE_02 = "02";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 * 已打回
	 */
	public static final String ARCHIVE_DOC_STATE_03 = "03";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 * 借出审批中
	 */
	public static final String ARCHIVE_DOC_STATE_04 = "04";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 * 已借出
	 */
	public static final String ARCHIVE_DOC_STATE_05 = "05";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *凭证处置审批中
	 */
	public static final String ARCHIVE_DOC_STATE_06 = "06";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *凭证退还已否决
	 */
	public static final String ARCHIVE_DOC_STATE_07 = "07";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *凭证退还待确认
	 */
	public static final String ARCHIVE_DOC_STATE_08 = "08";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *凭证已退还
	 */
	public static final String ARCHIVE_DOC_STATE_09 = "09";

	/**
	 * 资料状态
	 * parm_dic - ARCHIVE_DOC_STATE
	 *已出库
	 */
	public static final String ARCHIVE_DOC_STATE_10 = "10";

	/**
	 * 借阅/借出
	 *借阅
	 */
	public static final String ARCHIVE_OPT_TYPE_01 = "01";

	/**
	 * 借阅/借出
	 * 借出
	 */
	public static final String ARCHIVE_OPT_TYPE_02 = "02";

	/**
	 * 企业客户信息类型 1-基本信息
	 */
	public static final String COMPANY_INFO_TYPE_1 = "1";
	/**
	 * 企业客户信息类型 2-工商信息
	 */
	public static final String COMPANY_INFO_TYPE_2 = "2";
	/**
	 * 企业客户信息类型 3-法人信息
	 */
	public static final String COMPANY_INFO_TYPE_3 = "3";
	/**
	 * 企业客户信息类型 4-网点信息
	 */
	public static final String COMPANY_INFO_TYPE_4 = "4";
	/**
	 * 个人客户信息类型 1-基本信息
	 */
	public static final String PERSON_INFO_TYPE_1 = "1";
	/**
	 * 个人客户信息类型 2-个人概况信息
	 */
	public static final String PERSON_INFO_TYPE_2 = "2";
    /**
     * 模板文档类型 其他文档模板
     */
    public static final String TEMPLATE_TYPE_1="1";
    /**
     * 模板文档类型 保证从合同模板
     */
    public static final String TEMPLATE_TYPE_2="2";
    /**
     * 模板文档类型 抵押从合同模板
     */
    public static final String TEMPLATE_TYPE_3="3";
    /**
     * 模板文档类型 质押从合同模板
     */
    public static final String TEMPLATE_TYPE_4="4";
    /**
     * 模板文档类型 转让从合同模板
     */
    public static final String TEMPLATE_TYPE_5="5";
    /**
     * 模板文档类型 功能按钮模板
     */
    public static final String TEMPLATE_TYPE_6="6";
    /**
     * 模板文档类型 主合同模板
     */
    public static final String TEMPLATE_TYPE_7="7";
    /**
     * 模板文档类型 尽调报告模板
     */
    public static final String TEMPLATE_TYPE_8="8";
    /**
     * 模板文档类型 还款凭证模板
     */
    public static final String TEMPLATE_TYPE_9="9";
    /**
     * 模板文档类型 放款凭证模板
     */
    public static final String TEMPLATE_TYPE_10="10";
    /**
     * 模板文档类型 附件协议
     */
    public static final String TEMPLATE_TYPE_11="11";
    /**
     * 基础模板模板 word模板 templateFile.doc
     */
    public static final String TEMPLATE_BASE_WORD_FILE="templateFile.doc";
    /**
     * 基础模板模板 excel模板 templateFile.xlsx
     */
    public static final String TEMPLATE_BASE_EXCEL_FILE="templateFile.xlsx";
    /**
     * 用信类型 1额度新增
     */
    public static final String CREDIT_AMT_ADD="1";
    /**
     * 用信类型 2额度调整
     */
    public static final String CREDIT_AMT_ADJUST="2";    
    /**
     * 用信类型 3业务用信
     */
    public static final String CREDIT_AMT_BUSS_USE="3";    
    /**
     * 用信类型 4链属企业用信
     */
    public static final String CREDIT_AMT_CORE_USE="4";    
    /**
     * 用信类型 5担保用信
     */
    public static final String CREDIT_AMT_GUAR_USE="5";    
    /**
     * 用信类型 6还款恢复
     */
    public static final String CREDIT_AMT_REP_RECOVER="6";    
    /**
     * 用信类型 7业务否决恢复
     */
    public static final String CREDIT_AMT_BUSS_VETO_RECOVER="7";    
    /**
     * 用信类型 8数据清理恢复
     */
    public static final String CREDIT_AMT_DATA_CLEAR="8"; 
    /**
     *进件节点编号
     */
    public static final String CUS_APPLY_NODE_NO="cus_apply";  
    /**
     *进件节点编号
     */
    public static final String CUS_APPLY_NODE_NAME="进件";
    /**
     *复议节点编号
     */
    public static final String RECONSIDERATION="reconsideration";
    /**
     *复议节点编号
     */
    public static final String RECONSIDERATION_NAME="复议";
    /**
     *业务模式基础数据 （是）
     */
    public static final String MODEL_TYPE_BASE="base";  
    /**
     *业务模式基础数据 （否）
     */
    public static final String MODEL_TYPE_NORMAL="normal";

	/**
	 * weisd
	 * 到账确认类型-主流程业务
	 */
	public static final String COLLECT_TYPE_1 = "1";
	/**
	 * weisd
	 * 到账确认类型--不走主流程
	 */
	public static final String COLLECT_TYPE_2 = "2";
	/**
	 * weisd
	 * 到账确认状态--未到账确认
	 */
	public static final String COLLECT_STATUS_1 = "1";
	/**
	 * weisd
	 * 到账确认状态--已到账确认
	 */
	public static final String COLLECT_STATUS_2 = "2";
	/**
	 * weisd
	 * 到账确认状态--已开票
	 */
	public static final String COLLECT_STATUS_3 = "3";

	/**
	 * weisd
	 * 到账确认状态--已寄送
	 */
	public static final String COLLECT_STATUS_4 = "4";
	/**
	 * weisd
	 * 到账确认状态--已收妥确认
	 */
	public static final String COLLECT_STATUS_5 = "5";

    /**
     *流程类别  业务主流程
     */
    public static final String FLOW_CATEGORY_1="1";  
    /**
     *流程类别 业务审批流程
     */
    public static final String FLOW_CATEGORY_2="2";  
    /**
     *流程类别 押品审批流程
     */
    public static final String FLOW_CATEGORY_3="3";  
    /**
     *流程类别 贷后审批流程
     */
    public static final String FLOW_CATEGORY_4="4";  
    /**
     *流程类别 客户相关审批流程
     */
    public static final String FLOW_CATEGORY_5="5";  
    /**
     *风险拦截类型 1-风险拦截预警
     */
    public static final String RISK_ITEM_TYPE_1="1";  
    
    /**
     *风险拦截类型 2-日终批量预警
     */
    public static final String RISK_ITEM_TYPE_2="2";  
    
    /**
     *风险拦截类型 3-通用预警
     */
    public static final String RISK_ITEM_TYPE_3="3";  
    
    /**
     *风险拦截类型 4-客户准入
     */
    public static final String RISK_ITEM_TYPE_4="4";  
    /**
     *风险拦截类型 5-产品准入
     */
    public static final String RISK_ITEM_TYPE_5="5";  
    /**
     *风险拦截类型 6-要件表单字段（取值机制与拦截项一致，所以单独一个类型）
     */
    public static final String RISK_ITEM_TYPE_6="6"; 
    
    /**
     *拦截项类型 1-准入类型
     */
    public static final String INTERCEPT_TYPE_1="1"; 
    /**
     *拦截项类型 2-拦截类型
     */
    public static final String INTERCEPT_TYPE_2="2"; 
    
    /**
     * 流转到当前岗位的方式,标识当前岗位的提交类型。
     * pass正常审批流转到的岗位 rollback发回重审流转到的岗位
     */
    public static final String APPROVE_TYPE_PASS="pass";
    /**
     * 流转到当前岗位的方式,标识当前岗位的提交类型。
     * pass正常审批流转到的岗位 rollback发回重审流转到的岗位
     */
    public static final String APPROVE_TYPE_ROLLBACK="rollback";
    
    /**
     *逾期违约金分类1-正常还款逾期违约金，2-提前还款违约金额
     */
    public static final String PENALTY_TYPE_1="1"; 
    
    /**
     *逾期违约金分类1-正常还款逾期违约金，2-提前还款违约金额
     */
    public static final String PENALTY_TYPE_2="2"; 
    
    /**
     * 发回重审后再次提交时，选择提交岗位类型标识，
     * 0提交下一步
     * 1提交到执行发回重审的岗位。
     */
    public static final String ASSIGN_NEXT_FLAG="0";
    /**
     * 发回重审后再次提交时，选择提交岗位类型标识，
     * 0提交下一步
     * 1提交到执行发回重审的岗位。
     */
    public static final String ASSIGN_BEFORE_SUB_FLAG="1";
    /**
     * 访问钉钉后台服务接口的唯一票据
     */
    public static String ACCESS_TOKEN="";
    /**
     * 访问钉钉Js_api接口的唯一票据
     */
    public static String JS_API_TICKET="";
    /**
     * 贷后检查审批流程
     */
    public static final String EXAMINE_APPROVE_WKF_ID="201721514416";
    
    /**
     * 资产处置方式。拍卖
     */
    public static final String ASSET_HANDLE_TYPE_AUCTION="1";
    /**
     * 资产处置方式。租赁
     */
    public static final String ASSET_HANDLE_TYPE_LEASE="2";
    /**
     * 资产处置方式。联合开发
     */
    public static final String ASSET_HANDLE_TYPE_COOPERATION="3";
	/**
	 * 资产处置方式。协议处置
	 */
	public static final String ASSET_HANDLE_TYPE_NEGOTIATE="4";
    /**
     * 资产处置方式。其他
     */
    public static final String ASSET_HANDLE_TYPE_OTHER="0";
	/**
	 * 资产状态。0 新增
	 */
    public static final String ASSET_TYPE_ADD="0";
    /**
	 * 资产状态。1引入
	 */
    public static final String ASSET_TYPE_QUOTE="1";
    /**
	 * 资产状态。2审批中
	 */
    public static final String ASSET_TYPE_APPROVING="2";
    /**
	 * 资产状态。3 待处置
	 */
    public static final String ASSET_TYPE_STAY_HANDLE="3";
    /**
	 * 资产状态。4 已处置
	 */
    public static final String ASSET_TYPE_HANDLED="4";
    /**
     * 资产保全审批流程
     */
    public static final String ASSET_APPROVE_WKF_ID="201781414472944";
    /**
     * 利率类型 1年利率%
     */
    public static final String RATE_TYPE_YEAR="1";
    /**
     * 利率类型 2千分号月利率‰
     */
    public static final String RATE_TYPE_MONTH_2="2";
    /**
     * 利率类型 3日利率
     */
    public static final String RATE_TYPE_DAY="3";
    /**
     * 利率类型 4百分号月利率
     */
    public static final String RATE_TYPE_MONTH_4="4";
    /**
     * 合同主体中放款标识类型 1可放款
     */
    public static final String PACT_PUTOUT_STS_1="1";
    /**
     * 合同主体中放款标识类型 2放款中
     */
    public static final String PACT_PUTOUT_STS_2="2";
    /**
     * 合同主体中放款标识类型 3放款完结
     */
    public static final String PACT_PUTOUT_STS_3="3";
    
    /**
     * 操作员数据
     */
    public static final String OP_DATA_SOURCE="1";
    
    /**
     * 共同借款人数据
     */
    public static final String PER_DATA_SOURCE="2";
    /**
     * 渠道商数据
     */
    public static final String CHANNEL_SOURCE="3";
    /**
     * 保证人数据
     */
    public static final String ASSURE_DATA_SOURCE="4";
    
	/**
	 * 固定利率
	 */
	public static final String IC_TYPE_1 = "1";
	/**
	 * 按月浮动
	 */
	public static final String IC_TYPE_2 = "2";
	/**
	 * 按月对日浮动
	 */
	public static final String IC_TYPE_3 = "3";
	/**
	 * 按季浮动
	 */
	public static final String IC_TYPE_4 = "4";
	/**
	 * 按季对日浮动
	 */
	public static final String IC_TYPE_5 = "5";
	/**
	 * 按年
	 */
	public static final String IC_TYPE_6 = "6";
	/**
	 * 按年对月对日
	 */
	public static final String IC_TYPE_7 = "7";
	/**
	 * 立即调整
	 */
	public static final String IC_TYPE_8 = "8";
	/**
	 * 固定利息
	 */
	public static final String IC_TYPE_9 = "9";
	
	/**
     * 还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式
     * 0 不收取  1 收取
     */
	public static final String FESTIVALTYPE0 = "0"; 

	/**
     * 还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式
     * 0 不收取  1 收取
     */
	public static final String FESTIVALTYPE1 = "1"; 

	
	/**
	 * 展期申请状态 0申请中（未提交）
	 */
	public static final String EXTENSION_APP_STS_APPLY = "0";
	/**
	 * 展期申请状态1审批中
	 */
	public static final String EXTENSION_APP_STS_APPROVING = "1";
	/**
	 * 展期申请状态 2审批完成
	 */
	public static final String EXTENSION_APP_STS_PASS = "2";
	/**
	 * 展期申请状态 3发回重审
	 */
	public static final String EXTENSION_APP_STS_RESTART = "3";
	/**
	 * 展期申请状态 4发回补充资料
	 */
	public static final String EXTENSION_APP_STS_DEALER = "4";
	/**
	 * 展期申请状态 5否决
	 */
	public static final String EXTENSION_APP_STS_REFUSE = "5";
    /**
     * 征信查询申请状态 0申请中（未提交）
     */
    public static final String CREDIT_QUERY_APP_STS_APPLY = "0";
    /**
     * 征信查询申请状态1审批中
     */
    public static final String CREDIT_QUERY_APP_STS_APPROVING = "1";
    /**
     * 征信查询申请状态 2审批完成
     */
    public static final String CREDIT_QUERY_APP_STS_PASS = "2";
    /**
     * 征信查询申请状态 3发回重审
     */
    public static final String CREDIT_QUERY_APP_STS_RESTART = "3";
    /**
     * 征信查询申请状态 4发回补充资料
     */
    public static final String CREDIT_QUERY_APP_STS_DEALER = "4";
    /**
     * 征信查询申请状态 5否决
     */
    public static final String CREDIT_QUERY_APP_STS_REFUSE = "5";
    /**
     * 征信查询申请状态 6已签约
     */
    public static final String CREDIT_QUERY_APP_STS_ESIGNED = "6";
    /**
     * 征信查询申请状态 7已作废
     */
    public static final String CREDIT_QUERY_APP_STS_INVALID = "7";
	/**
	 * 展期业务阶段1展期申请
	 */
	public static final String EXTENSION_BUS_STAGE_APPLY = "1";
	/**
	 * 展期业务阶段2担保信息
	 */
	public static final String EXTENSION_BUS_STAGE_PLEDGE = "2";
	/**
	 * 展期业务阶段3尽调报告
	 */
	public static final String EXTENSION_BUS_STAGE_INVEST = "3";
	/**
	 * 展期业务阶段4业务审批
	 */
	public static final String EXTENSION_BUS_STAGE_APPROVE = "4";
	/**
	 * 展期业务阶段5展期签约
	 */
	public static final String EXTENSION_BUS_STAGE_SIGN = "5";
	/**
	 * 展期业务阶段6还款计划
	 */
	public static final String EXTENSION_BUS_STAGE_REVIEW = "6";
	/**
	 * 展期业务阶段7已完成展期
	 */
	public static final String EXTENSION_BUS_STAGE_COMPLATE = "7";
	/**
     * 展期业务阶段8已否决
     */
    public static final String EXTENSION_BUS_STAGE_DISAGREE = "8";
    /**
     * 展期业务流程
     */
    public static final String EXTENSION_BUS_WKF_ID = "203164855276";
	/**
	 * 展期业务标识
	 */
	public static final String EXTENSION_BUSS= "extension_buss";
	/**
	 * 合同展期状态 0未展期
	 */
	public static final String PACT_EXTEN_STS_NOT= "0";
	/**
	 * 合同展期状态 1展期中
	 */
	public static final String PACT_EXTEN_STS_HANDLING= "1";
	/**
	 * 合同展期状态 2展期完成
	 */
	public static final String PACT_EXTEN_STS_COMPLATE= "2";

	/**
	 * 借据展期标记 0 未展期
	 */
	public static final String EXPFLAG0= "0";
	/**
	 * 借据展期标记 1 已展期
	 */
	public static final String EXPFLAG1= "1";
	/**
	 * 借据展期业务状态0未展期
	 */
	public static final String EXTENSTS0= "0";
	/**
	 * 借据展期业务状态 1展期中
	 */
	public static final String EXTENSTS1= "1";
	/**
	 * 借据展期业务状态 2展期完成
	 */
	public static final String EXTENSTS2= "2";
	/**
	 *展期前利息是否全部收取：0-不收取
	 */
	public static final String EXTENLIXITYPE0= "0";
	/**
	 *展期前利息是否全部收取：1-收取
	 */
	public static final String EXTENLIXITYPE1= "1";
	/**
	 * 展期前利息收取按钮是否能够操作（收取利息）：0-能收取(利息未收取)、
	 */
	public static final String EXTENLIXIBUTTONTYPE0= "0";
	/**
	 * 展期前利息收取按钮是否能够操作（收取利息）：1-不能收取（利息已经收取）
	 */
	public static final String EXTENLIXIBUTTONTYPE1= "1";
	/**
	 * 机构设置中根部门ID
	 */
	public static final String SYS_ORG_BR_NO= "100000";
    
	/**
	 * 资产类型 	现金及银行存款
	 */
	public static final String ASSETS_TYPE_bank= "1";
	/**
	 * 资产类型 	应收帐款
	 */
	public static final String ASSETS_TYPE_YS= "2";
	
	/**
	 * 资产类型 	预付帐款
	 */
	public static final String ASSETS_TYPE_repay= "3";
	/**
	 * 资产类型 		存货
	 */
	public static final String ASSETS_TYPE_goods= "4";
	/**
	 * 评级阶段1发起评级
	 */
	public static final String EVAL_STAGE_LAUNCH= "1";
	/**
	 * 评级阶段2评分卡
	 */
	public static final String EVAL_STAGE_GRADE_CARD= "2";
	/**
	 * 评级阶段3评级结果
	 */
	public static final String EVAL_STAGE_RESULT= "3";
	/**
	 * 评级阶段4评级审批中
	 */
	public static final String EVAL_STAGE_APPROVING= "4";
	/**
	 * 评级阶段5评级完成
	 */
	public static final String EVAL_STAGE_COMPLATE= "5";
	/**
	 * 垫款状态 0：未提交申请 未垫款
	 */
	public static final String ADVANCES_STS_UNAPPLY="0";
	/**
	 * 垫款状态 1：已申请但未完成 垫款中
	 */
	public static final String ADVANCES_STS_APPLYING="1";
	/**
	 * 垫款状态 2：已确认打钱给收款方 已完成
	 */
	public static final String ADVANCES_STS_COMPLETED="2";
	/**
	 * 垫款状态 3：流程中被拒绝 已拒绝
	 */
	public static final String ADVANCES_STS_REFUSED="3";
	/**
	 * 入库出库审批流程编号
	 */
	public static final String  INSTOCK_APPROVE_WKF_NO ="205110470339";
	
	/**
	 * 入职申请审批流程编号
	 */
	public static final String  ENTRY_APPLY_NO ="204082015430";
	
	/**
	 * 转正申请审批流程编号
	 */
	public static final String  BECOME_QUALIFIEDID_NO ="204321329425";

	/**
	 * 调薪调岗审批流程编号
	 */
	public static final String  ADJUSTMENT_NO ="204697166937";
	/**
	 * 离职申请审批流程编号
	 */
	public static final String  DIMISSION_NO ="204770946085";

	/**
	 * 机构申请审批流程编号
	 */
	public static final String  INSTITUTION_NO = "204951574319";
	
	/**
	 * 合同结项审批流程编号
	 */
	public static final String  POST_PROJECT_NO ="203958028296";


	/**
	 * 全职转兼职申请审批流程编号
	 */
	public static final String  FULL_TO_PART_NO ="204786335952";
	
	/**
	 * 人力需求审批流程编号
	 */
	public static final String  HUMAN_RESOURCES_NO ="204686084918";
	/**
	 * 事项呈报审批流程编号
	 */
	public static final String  MATTERS_REPORTED_NO ="204863515504";
	/**
	 * 培训需求申请审批流程编号
	 */
	public static final String  TRAINING_NEEDS_NO ="204854874309";
	
	/**
	 * 风险等级调整-升级审批流程编号
	 */
	public static final String  RISK_LEVEL_ADJUST_1 ="204395628935";
	/**
	 * 风险等级调整-升级审批流程编号
	 */
	public static final String  RISK_LEVEL_ADJUST_2 ="204451151261";
	/**
	 * 风险等级调整-升级审批流程编号
	 */
	public static final String  RISK_LEVEL_ADJUST_3 ="204451237407";
	
	/**
	 * 出入库状态0未出/入库
	 */
	public static final String  IN_OUT_STS_NOT ="0";
	/**
	 * 出入库状态1出/入库审批中
	 */
	public static final String  IN_OUT_STS_APPROVING ="1";
	/**
	 * 出入库状态2已出/入库
	 */
	public static final String  IN_OUT_STS_COMPLATE ="2";
	/**
	 * 消息类型1发送
	 */
	public static final String  MESSAGE_TYPE_SEND ="1";
	/**
	 * 消息类型2接受
	 */
	public static final String  MESSAGE_TYPE_ACCEPTS ="2";
	/**
	 * 反欺诈报告 类型
	 */
	public static final String  ANTIFRAUD_ITEM_TYPE ="RE_GRFQZ";
	/**
	 * 反欺诈报告服务编号
	 */
	public static final String  ANTIFRAUD_ITEM_NO ="FY009006";	
	/**
	 * 性别 男
	 */
	public static final String  SEX_MAN ="0";
	/**
	 * 性别 女
	 */
	public static final String  SEX_WOMAN ="1";
	/**
	 * 性别 未知
	 */
	public static final String  SEX_UNKNOWN ="2";
    /**
     * 发回重审后再次提交时，下一岗位审批人员是按照流程角色配置还是该岗位审批过得人员，
     * 0提交给该岗位之前审批过的人员进行审批
     * 1提按照流程配置的角色进行审批 。
     */
    public static final String ROLEBACK_APPROVE_HIS="0";
    /**
     * 发回重审后再次提交时，下一岗位审批人员是按照流程角色配置还是该岗位审批过得人员，
     * 0提交给该岗位之前审批过的人员进行审批
     * 1提按照流程配置的角色进行审批 。
     */
    public static final String ROLEBACK_APPROVE_ROLE="1";
    /**
     *分之中用于判断下一个节点的相对执行机构
     * 0按照分支前的最后一个节点
     * 1按照分支中的最后一个审批人员 。
     */
    public static final String DECISION_APPROVE_OPT="0";
    
    /**
     * 企业结束日期
     */
    public static final String END_DATE_CHANGQI="长期";
    /**
     * 数据权限类型 1 本人
     */
    public static final String FUN_ROLE_TYPE_1="1";
    /**
     * 数据权限类型 2 本部门
     */
    public static final String FUN_ROLE_TYPE_2="2";
    /**
     * 数据权限类型 3 本部门及其以下
     */
    public static final String FUN_ROLE_TYPE_3="3";
    /**
     * 数据权限类型 6 指定部门
     */
    public static final String FUN_ROLE_TYPE_6="6";
    /**
     * 数据权限类型 9 指定部门
     */
    public static final String FUN_ROLE_TYPE_9="9";
    
    /**
     * 征信查询客户类型 1借款客户
     */
    public static final String CREDIT_QUERY_CUS_TYPE_LOAN="1";
    
    /**
     * 法执情况客户类型 1借款客户
     */
    public static final String LAW_QUERY_CUS_TYPE_LOAN="1";
    
    /**
     * 征信查询客户类型 2借款人当前业务担保人
     */
    public static final String CREDIT_QUERY_CUS_TYPE_ASSURER="2";
    /**
     * 法执情况客户类型 2借款人当前业务担保人
     */
    public static final String LAW_QUERY_CUS_TYPE_ASSURER="2";
    /**
     * 征信查询客户类型 3借款人配偶
     */
    public static final String CREDIT_QUERY_CUS_TYPE_SPOUSE="3";
    /**
     * 法执情况客户类型 3借款人配偶
     */
    public static final String LAW_QUERY_CUS_TYPE_SPOUSE="3";
    /**
     * 系统默认渠道标识,如果系统新添加客户信息或业务信息时没有传递渠道信息
     * 默认使用该渠道编号
     */
    public static final String SYS_CHANNEL_ID="0";
    /**
     * 渠道状态 1正常
     */
    public static final String TRENCH_STATUS_MORMAL="1";
    /**
     * 渠道状态 2终止
     */
    public static final String TRENCH_STATUS_STOP="2";
    /**
     * 渠道状态 3暂停
     */
    public static final String TRENCH_STATUS_SUSPEND="3";
    /**
     * 渠道操作员数据权限1本人
     */
    public static final String TRENCH_USER_DATA_RANG_1="1";
    /**
     * 渠道操作员数据权限2本渠道
     */
    public static final String TRENCH_USER_DATA_RANG_2="2";
    /**
     * 渠道操作员数据权限3本渠道及其子渠道
     */
    public static final String TRENCH_USER_DATA_RANG_3="3";
    /**
     * 评级指标类型 EVAL_INDEX_TYPE  1 财务
     */
    public static final String EVAL_INDEX_TYPE_FIN="1";
    /**
     * 评级指标类型 EVAL_INDEX_TYPE  2 定量评分
     */
    public static final String EVAL_INDEX_TYPE_DL="2";
    /**
     * 评级指标类型 EVAL_INDEX_TYPE  3 定性评分
     */
    public static final String EVAL_INDEX_TYPE_DX="3";
    /**
     * 评级指标类型 EVAL_INDEX_TYPE  4 调整评分
     */
    public static final String EVAL_INDEX_TYPE_ADJ="4";
    /**
     * 评级指标类型 EVAL_INDEX_TYPE  5 约束等级
     */
    public static final String EVAL_INDEX_TYPE_RESTRICT="5";
    
    /**
     * 评级指标类型 EVAL_INNER_TYPE  1 内部评级
     */
    public static final String EVAL_TYPE_INNER="1";
    
    /**
     * 评级指标类型 EVAL_TYPE_EXTERIOR  2 外部评级
     */
    public static final String EVAL_TYPE_EXTERIOR="2";
    
    /**
     * 评级EVAL_TIME_IN首次发起的评级
     */
    public static final String EVAL_TIME_First="3";
    
    /**
     * 评级EVAL_TIME_OUT已过期
     */
    public static final String EVAL_TIME_OUT="2";
    
    /**
     * 评级EVAL_TIME_IN未到期
     */
    public static final String EVAL_TIME_IN="1";
	/**
	 * 订单状态 0未提交
	 */
	public  static  final  String ORDER_STS_0 ="0";
	/**
	 * 订单状态 1审批中
	 */
	public  static  final  String ORDER_STS_1 ="1";
	/**
	 * 订单状态 2已驳回
	 */
	public  static  final  String ORDER_STS_2 ="2";
	/**
	 * 订单状态 3已取消
	 */
	public  static  final  String ORDER_STS_3 ="3";
	/**
	 * 订单状态 4已撤销
	 */
	public  static  final  String ORDER_STS_4 ="4";
	/**
	 * 订单状态 5已拒绝
	 */
	public  static  final  String ORDER_STS_5 ="5";
	/**
	 * 订单状态 6待缴纳定金
	 */
	public  static  final  String ORDER_STS_6 ="6";
	/**
	 * 订单状态 7待签协议
	 */
	public  static  final  String ORDER_STS_7 ="7";
	/**
	 * 订单状态 8待付首金
	 */
	public  static  final  String ORDER_STS_8 ="8";
	/**
	 * 订单状态 9待提车
	 */
	public  static  final  String ORDER_STS_9 ="9";
	/**
	 * 订单状态 10重签协议
	 */
	public  static  final  String ORDER_STS_10 ="10";
	/**
	 * 订单状态 11合同审核
	 */
	public  static  final  String ORDER_STS_11 ="11";
	/**
	 * 订单状态 12订单完成
	 */
	public  static  final  String ORDER_STS_12 ="12";
	/**
	 * 订单状态 13车管验车
	 */
	public  static  final  String ORDER_STS_13 ="13";
	/**
	 * 订单状态 14用户验车
	 */
	public  static  final  String ORDER_STS_14 ="14";

	/**
	 * 账款状态 0 登记
	 */
	public  static  final  String RECE_STS_0 ="0";
	/**
	 * 账款状态 1 已使用
	 */
	public  static  final  String RECE_STS_1 ="1";
	/**
	 * 账款状态 2 已转让
	 */
	public  static  final  String RECE_STS_2 ="2";
	/**
	 * 账款状态 3 已反转
	 */
	public  static  final  String RECE_STS_3 ="3";
	/**
	 * 账款状态 4 已回购
	 */
	public  static  final  String RECE_STS_4 ="4";
	/**
	 * 账款状态 5 已结清
	 */
	public  static  final  String RECE_STS_5 ="5";
	/**
	 * 账款状态 6 转让中
	 */
	public  static  final  String RECE_STS_6 ="6";
	/**
	 * 账款状态 7 反转中
	 */
	public  static  final  String RECE_STS_7 ="7";
	/**
	 * 账款状态 8 反转确认
	 */
	public  static  final  String RECE_STS_8 ="8";
	/**
	 * 账款状态 9 回购中
	 */
	public  static  final  String RECE_STS_9 ="9";
	/**
	 * 还款计划每一期结束日期展示起息日期减一天标志（1 每期结束日期减一天）
	 */
	public static final String REPAYPLANSHOWFLAG_1 = "1";

	public static final String pact_change_type = "pactChange";

	/**
	 * 授信合同起始节点名称
	 */
	public static final String CREDIT_SUPPLEMENT_DATA = "supplement_data";
	/**
	 * 授信预审批第一个节点
	 */
	public static final String CREDIT_SUPPLEMENT_PRIMARY = "credit_supplement_primary";
	/**
	 * 授信审批第一个节点
	 */
	public static final String CREDIT_SUPPLEMENT = "credit_supplement";
	/**
	 * 授信清稿第一个节点
	 */
	public static final String CREDIT_SUPPLEMENT_PACT_PRIMARY = "credit_supplement_pact_primary";
	/**
	 * 授信合同审批第一个节点
	 */
	public static final String CREDIT_SUPPLEMENT_PACT = "credit_supplement_pact";

	public static final String fee_refund_type = "feeRefund";
	public static final String fee_refund_confirm = "fee_refund_confirm";

	public static final String PLEDGE_STATUS_01 = "01";//未质押
	public static final String PLEDGE_STATUS_02 = "02";//全部质押
	public static final String PLEDGE_STATUS_03 = "03";//部分质押
	public static final String PLEDGE_STATUS_04 = "04";//全部解押
	public static final String PLEDGE_STATUS_05 = "05";//部分解押

	public static final String REDEEM_STATUS_01 = "01";//未赎回
	public static final String REDEEM_STATUS_02 = "02";//全部赎回
	public static final String REDEEM_STATUS_03 = "03";//部分赎回
//	public static final String charge_fee_template_no = "20031618283008097";


	/**
	 * 关联类型pledge押品lease租赁物account应收账款
	 */
	public static final String COLLATERAL_TYPE_PLEDGE = "pledge";
	/**
	 * 关联类型pledge押品lease租赁物account应收账款
	 */
	public static final String COLLATERAL_TYPE_LEASE = "lease";
	/**
	 * 关联类型pledge押品lease租赁物account应收账款
	 */
	public static final String COLLATERAL_TYPE_ACCOUNT = "account";


	/**
	 * 评级类型 1 客户评级
	 */
	public  static  final  String GRADE_TYPE_1 ="1";

	/**
	 * 评级类型 2 风险评级
	 */
	public  static  final  String GRADE_TYPE_2 ="2";

	/**
	 * 评级类型 3 额度测算
	 */
	public  static  final  String GRADE_TYPE_3 ="3";
	/**
	 * 评级类型 4 业务评级
	 */
	public  static  final  String GRADE_TYPE_4 ="4";

	/**
	 * 评级类型 1 信用评级
	 */
	public  static  final  String EVAL_CLASS_1 ="1";
	/**
	 * 评级类型 2 债项评级
	 */
	public  static  final  String EVAL_CLASS_2 ="2";
	/**
	 * 计算押品货值规则编号
	 */
	public static final String PLEDGE_VALUE_RULES_NO = "pledgeValue1";
	
	/** 审批流程节点枚举 */
	public static enum WKF_NODE {
		/**
		 * <p>
		 * 申请相关
		 * </p>
		 * <p>
		 * 功能描述: 打开申请新增页面<br>
		 * 相关代码: {@link MfBusApplyAction#input}
		 * </p>
		 */
		apply_input("apply", "业务申请", SCENCE_TYPE_DOC_APP, "MfBusApplyAction_Input.action"),

		/**
		 * <p>
		 * 申请相关
		 * </p>
		 * <p>
		 * 功能描述: 打开申请更新页面，含要件上传<br>
		 * 相关代码: {@link MfLoanApplyAction#inputUploadForm}
		 * </p>
		 */
		apply_update("apply", "业务申请", SCENCE_TYPE_DOC_APP, "MfLoanApplyAction_inputUploadForm.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 申请相关
		 * </p>
		 * <p>
		 * 功能描述: 提交业务申请<br>
		 * 相关代码: {@link MfLoanApplyAction#commitProcessAjax}
		 * </p>
		 */
		apply_commit("apply", "业务申请", SCENCE_TYPE_DOC_APP, "MfLoanApplyActionAjax_commitProcessAjax.action?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 申请丰富
		 * </p>
		 * <p>
		 * 功能描述: 丰富业务申请<br>
		 * 相关代码: {}
		 * </p>
		 */
		apply_rich("apply_rich", "申请丰富", SCENCE_TYPE_DOC_APP, "/mfBusApply/inputUpdateQuery?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 关联企业
		 * </p>
		 * <p>
		 * 功能描述: 登记核心企业<br>
		 * 相关代码: {@link MfCusCustomerAction#input2}
		 * </p>
		 */
		core_reg("core_reg", "核心企业", "", "MfCusCustomerAction_input2.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 关联企业
		 * </p>
		 * <p>
		 * 功能描述: 买方信息<br>
		 * 相关代码: {@link MfCusCustomerAction#input2}
		 * </p>
		 */
		buyinfo_reg("buyinfo_reg", "买方信息", "", "MfCusCustomerAction_input2.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 关联企业
		 * </p>
		 * <p>
		 * 功能描述: 资金机构<br>
		 * 相关代码: {@link MfCusCustomerAction#input2}
		 * </p>
		 */
		findorg_reg("findorg_reg", "资金机构", "", "MfCusCustomerAction_input2.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 关联企业
		 * </p>
		 * <p>
		 * 功能描述: 仓储方<br>
		 * 相关代码: {@link MfCusCustomerAction#input2}
		 * </p>
		 */
		warehouse_reg("warehouse_reg", "仓储方", "", "MfCusCustomerAction_input2.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 押品登记<br>
		 * 相关代码: {@link MfBusCollateralRelAction#insertInput}
		 * </p>
		 */
		pledge_reg("pledge_reg", "押品登记", "", "MfBusCollateralRelAction_insertInput.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business"),
		/**
		 * <p>
		 * 补录历史
		 * </p>
		 * <p>
		 * 功能描述: 补录历史<br>
		 * 相关代码: {@link MfBusCollateralRelAction#input_history}
		 * </p>
		 */
		input_history("input_history", "补录历史", "", "MfBusCollateralRelAction_inputHistory.action?appId=#{appId}&cusNo=#{cusNo}"),
		/**
		 * <p>
		 * 应收账款相关
		 * </p>
		 * <p>
		 * 功能描述: 应收账款信息采集<br>
		 * 相关代码: {@link app.component.collateral.controller.MfBusCollateralRelController#infoCollectInput}
		 * </p>
		 */
		account_info_collect("account_info_collect", "账款信息采集", "", "/mfBusCollateralRel/infoCollectInput?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business&infoType=account"),
		/**
		 * <p>
		 * 租赁物相关
		 * </p>
		 * <p>
		 * 功能描述: 租赁物信息采集<br>
		 * 相关代码: {@link app.component.collateral.controller.MfBusCollateralRelController#infoCollectInput}
		 * </p>
		 */
        lease_info_collect("lease_info_collect", "租赁物信息采集", "", "/mfBusCollateralRel/infoCollectInput?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business&infoType=lease"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 质押核库<br>
		 * 相关代码: {@link MfMoveableCheckInventoryInfoAction#input}
		 * </p>
		 */
		check_inventory("check_inventory", "质押核库", "", "MfMoveableCheckInventoryInfoAction_input.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 质押登记<br>
		 * 相关代码: {@link MfReceivablesPledgeInfoAction#input}
		 * </p>
		 */
		storage_confirm("storage_confirm", "质押登记", "", "/certiInfo/inputBus?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 权证登记<br>
		 * 相关代码: {@link MfReceivablesPledgeInfoAction#input}
		 * </p>
		 */
		certidInfo_reg("certidInfo_reg", "权证登记", "", "/certiInfo/inputCertiInfoList?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business&type=1"),
		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 押品评估<br>
		 * 相关代码: {@link EvalInfoAction_inputForFlow}
		 * </p>
		 */
		pledge_eval("pledge_eval", "押品评估", "", "EvalInfoAction_inputForFlow.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),
		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 押品入库<br>
		 * 相关代码: {@link MfBusCollateralRelAction_inStockBatchInputForBuss}
		 * </p>
		 */
		pledge_inStockBatch("pledge_inStockBatch", "押品入库", "", "MfBusCollateralRelAction_inStockBatchInputForBuss.action?popFlag=1&appId=#{appId}"),
		
		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 转让申请<br>
		 * 相关代码: {@link MfReceivablesTransferInfoAction#input}
		 * </p>
		 */
		rece_transfer("rece_transfer", "转让申请", "", "MfReceivablesTransferInfoAction_input.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 转让审批<br>
		 * 相关代码: {@link MfReceivablesTransferInfoAction#processSubmitAjax}
		 * </p>
		 */
		rece_approval("rece_approval", "转让审批", "", "MfReceivablesTransferInfoActionAjax_processSubmitAjax.action?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 押品相关
		 * </p>
		 * <p>
		 * 功能描述: 办理公证<br>
		 * 相关代码: {@link MfBusCollateralRelAction#pleNotarizationList}
		 * </p>
		 */
		notarization("notarization", "办理公证", SCENCE_TYPE_DOC_NOTARIZATION, "MfBusCollateralRelAction_pleNotarizationList.action?popFlag=1&appId=#{appId}&pleFlag=notariza"),

		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 分单<br>
		 * 相关代码: {@link MfLoanApplyAction#inputReinPolicyForm}
		 * </p>
		 */
		reinsurance_policy("reinsurance_policy", "分单", SCENCE_TYPE_DOC_REINSURANCE_POLICY, "MfLoanApplyAction_inputReinPolicyForm.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),

		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 面签分单<br>
		 * 相关代码: {@link MfLoanApplyAction#inputReinPolicyForm}
		 * </p>
		 */
		reinsurance_sign_policy("reinsurance_sign_policy", "面签分单", SCENCE_TYPE_DOC_REINSURANCE_POLICY, "MfLoanApplyAction_inputReinPolicySignForm.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),
		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 上抵分单<br>
		 * 相关代码: {@link MfLoanApplyAction#inputReinPolicyForm}
		 * </p>
		 */
		reinsurance_arrival_policy("reinsurance_arrival_policy", "上抵分单", SCENCE_TYPE_DOC_REINSURANCE_POLICY, "MfLoanApplyAction_inputReinPolicyArrivalForm.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}"),
		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 征信<br>
		 * 相关代码: {@link MfLoanApplyAction#inputCreditInquiry}
		 * </p>
		 */
		credit_investigation("credit_investigation", "征信", SCENCE_TYPE_DOC_CREDIT_INVESTIGATION, "MfLoanApplyAction_inputCreditInquiry.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 贷前调查, 保前调查, 尽职调查<br>
		 * 相关代码: {@link MfLoanApplyAction#confirmTuningReport}
		 * </p>
		 */
		resp_investigation("resp_investigation", "贷前调查", SCENCE_TYPE_DOC_INVESTIGATION, "MfLoanApplyAction_confirmTuningReport.action?popFlag=1&appId=#{appId}"),
		credit_resp("credit_resp", "贷前调查", SCENCE_TYPE_DOC_INVESTIGATION, "MfLoanApplyAction_confirmTuningReport.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 主业务批单答应<br>
		 * 相关代码: {@link mfLoanApply#batchPrinting}
		 * </p>
		 */
		batch_printing("batch_printing", "批单打印", "", "mfLoanApply/batchPrinting?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 主业务担保意向书<br>
		 * 相关代码: {@link mfLoanApply#letterIntent}
		 * </p>
		 */
		letter_intent("letter_intent", "担保意向书", "", "mfLoanApply/letterIntent?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 初评评估<br>
		 * 相关代码: {@link MfBusCollateralRelAction#insertEvalInput}
		 * </p>
		 */
		pre_evaluation("pre_evaluation", "初评评估", SCENCE_TYPE_DOC_PRE_EVALUATION, "MfBusCollateralRelAction_insertEvalInput.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business"),
		
		/**
		 * <p>
		 * 初评评估之后的价值确认业务节点
		 * </p>
		 * <p>
		 * 功能描述: 价值确认<br>
		 * 相关代码: {@link MfBusCollateralRelAction#insertEvalInput}
		 * </p>
		 */
		confirm_evaluation("confirm_evaluation", "价值确认", SCENCE_TYPE_DOC_CONFIRM_EVALUATION, "MfBusCollateralRelAction_getJustConfirmEvalInfo.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business"),
		/**
		 * <p>
		 * 签约前功能
		 * </p>
		 * <p>
		 * 功能描述: 二次提报<br>
		 * 相关代码: {@link MfLoanApplyAction#inputTwoReportForm}
		 * </p>
		 */
		two_report("two_report", "二次提报", SCENCE_TYPE_DOC_TIBAO, "MfLoanApplyAction_inputTwoReportForm.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business"),
		
		/**
		 * <p>
		 * 业务初选
		 * </p>
		 * <p>
		 * 功能描述: 业务初选审批<br>
		 * 相关代码: {@link MfBusApplyAction#generalProcessSubmitAjax}
		 * </p>
		 */
		primary_apply_approval("primary_apply_approval", "业务初选", SCENCE_TYPE_DOC_BUS_APPROVAL, "/mfBusApply/primaryProcessSubmitAjax?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 业务审批
		 * </p>
		 * <p>
		 * 功能描述: 业务审批<br>
		 * 相关代码: {@link MfBusApplyAction#processSubmitAjax}
		 * </p>
		 */
		apply_approval("apply_approval", "业务审批", SCENCE_TYPE_DOC_BUS_APPROVAL, "MfBusApplyActionAjax_processSubmitAjax.action?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 否决复议审批
		 * </p>
		 * <p>
		 * 功能描述: 否决复议审批<br>
		 * 相关代码: {@link MfQueryDisagreeAction#getViewPoint}
		 * </p>
		 */
		disagree_reconsider("disagree_reconsider", "业务审批", null, "QueryDisagreeAction_getViewPoint.action?appId=#{appId}&vpNo=2"),

		/**
		 * <p>
		 * 签约
		 * </p>
		 * <p>
		 * 功能描述: 合同签约<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		contract_sign("contract_sign", "合同签约", SCENCE_TYPE_DOC_CONT, "MfBusPactAction_input.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 签约
		 * </p>
		 * <p>
		 * 功能描述: 合同签约亿联<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		contract_signYL("contract_signYL", "合同签约亿联", SCENCE_TYPE_DOC_CONT, "MfBusPactAction_input.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 签约
		 * </p>
		 * <p>
		 * 功能描述: 合同签约东亚<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		contract_signDY("contract_signDY", "合同签约东亚", SCENCE_TYPE_DOC_CONT, "MfBusPactAction_input.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 合同清稿
		 * </p>
		 * <p>
		 * 功能描述: 合同审批<br>
		 * 相关代码: {@link MfBusPactController#primaryProcessSubmitAjax}
		 * </p>
		 */
		primary_contract_approval("primary_contract_approval", "合同清稿", SCENCE_TYPE_DOC_PACT_APPROVAL, "/mfBusPact/primaryProcessSubmitAjax?popFlag=0&appId=#{appId}"),

		/**
		 * <p>
		 * 合同审批
		 * </p>
		 * <p>
		 * 功能描述: 合同审批<br>
		 * 相关代码: {@link MfBusPactAction#processSubmitAjax}
		 * </p>
		 */
		contract_approval("contract_approval", "合同审批", SCENCE_TYPE_DOC_PACT_APPROVAL, "MfBusPactActionAjax_processSubmitAjax.action?popFlag=0&appId=#{appId}"),
		/**
		 * <p>
		 * 权证审批
		 * </p>
		 * <p>
		 * 功能描述: 权证审批
		 * </p>
		 */
		certidInfo_appr("certidInfo_appr", "权证审批", SCENCE_TYPE_DOC_CERTIDINFO_APPR, "/certiInfo/inputCertiInfoList?popFlag=1&appId=#{appId}&cusNo=#{cusNo}&entrFlag=business&type=2"),
		/**
		 * <p>
		 * 收费确认
		 * </p>
		 * <p>
		 * 功能描述: 收费确认
		 * </p>
		 */
		charg_confirmation("charg_confirmation", "收费确认", SCENCE_TYPE_DOC_CONT, "/mfBusApply/chargConfirmation?appId=#{appId}"),

		/**
		 * <p>
		 * 合同审批子流程中
		 * </p>
		 * <p>
		 * 功能描述: 审批流程中的收费节点<br>
		 * 相关代码: {@link MfBusPactWkfAction#getViewPoint}
		 * </p>
		 */
		fee_approval("fee_approval", "审批收费", SCENCE_TYPE_DOC_PACT_APPROVAL, "MfBusPactWkfAction_getViewPoint.action?pactId=#{pactId}"),
		
		/**
		 * <p>
		 * 合同阶段
		 * </p>
		 * <p>
		 * 功能描述: 合同确认， 修改合同信息<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		contract_confirm("contract_confirm", "合同确认", SCENCE_TYPE_DOC_CONT, "MfBusPactAction_input.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 放款申请<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		putout_apply("putout_apply", "放款申请", SCENCE_TYPE_DOC_FINC_APP, "MfBusFincAppAction_input.action?popFlag=1&appId=#{appId}"),
        /**
         /**
         * <p>
         * 放款签约
         * </p>
         * <p>
         * 功能描述: 放款申请<br>
         * 相关代码: {@link MfBusFincAppAction#input}
         * </p>
         */
        putout_print("putout_print", "放款签约", SCENCE_TYPE_DOC_FINC_APP, "MfBusFincAppAction_input.action?popFlag=1&appId=#{appId}"),

        /**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 放款申请明细新增环节<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		putout_apply_sub("putout_apply_sub", "放款申请明细", SCENCE_TYPE_DOC_FINC_APP, "MfBusFincAppAction_inputForBatch.action?pactId=#{pactId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 亿联银行放款申请<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		putout_applyYL("putout_applyYL", "放款确认亿联", SCENCE_TYPE_DOC_FINC_APP, "MfBusFincAppAction_input.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 东亚银行放款申请<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		putout_applyDY("putout_applyDY", "放款确认东亚", SCENCE_TYPE_DOC_FINC_APP, "MfBusFincAppAction_input.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 融资额度确认
		 * </p>
		 * <p>
		 * 功能描述: 融资额度确认<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		finc_amt_confirm("finc_amt_confirm", "融资额度确认", SCENCE_TYPE_DOC_FINC_APP, "/mfBusFincAmtConfirm/input?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 融资额度确认审批
		 * </p>
		 * <p>
		 * 功能描述: 融资额度确认<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		finc_amt_confirm_approval("finc_amt_confirm_approval", "融资额度确认审批", SCENCE_TYPE_DOC_FINC_APP, "/mfBusFincAmtConfirm/getViewPoint?appId=#{appId}&confirmId=#{confirmId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 调用资方<br>
		 * 相关代码: {@link MfBusFincAppAction#input}
		 * </p>
		 */
		call_service("call_service", "调用资方", SCENCE_TYPE_DOC_FINC_APPROVAL, "MfLoanApplyAction_confirmTuningReportForApp.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 放款审批<br>
		 * 相关代码: {@link MfBusFincAppAction#processSubmitAjax}
		 * </p>
		 */
		putout_approval("putout_approval", "放款审批", SCENCE_TYPE_DOC_FINC_APPROVAL, "MfBusFincAppActionAjax_processSubmitAjax.action?popFlag=0&appId=#{appId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 放款审批<br>
		 * 相关代码: {@link MfBusFincAppAction#processSubmitAjax}
		 * </p>
		 */
		finc_approval("finc_approval", "融资审批", SCENCE_TYPE_DOC_FINC_APPROVAL, "/mfBusFincAppMain/processSubmitAjax?popFlag=0&appId=#{appId}&fincMainId=#{fincMainId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 还款计划<br>
		 * 相关代码: {@link MfRepayPlanAction#repayPlanList}
		 * </p>
		 */
		review_finc("review_finc", "还款计划", SCENCE_TYPE_DOC_REVIEW_FINC, "MfRepayPlanAction_repayPlanList.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 还款计划<br>
		 * 相关代码: {@link MfRepayPlanAction#repayPlanListForBatch}
		 * </p>
		 */
		review_finc_batch("review_finc_batch", "还款计划", SCENCE_TYPE_DOC_REVIEW_FINC, "MfRepayPlanAction_repayPlanList.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 收费<br>
		 * 相关代码: {@link GuaranteeApplyAction#fee}
		 * </p>
		 */
		fee("fee", "收费", SCENCE_TYPE_DOC_FEE, "GuaranteeApplyAction_fee.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 费用录入
		 * 收费主体和业务下的费用项关联<br>
		 * 相关代码: {@link GuaranteeApplyAction#fee}
		 * </p>
		 */
		fee_collect("fee_collect", "费用录入", SCENCE_TYPE_DOC_FEE, "/guaranteeApply/feeCollect.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 费用确认
		 * 相关代码: {@link GuaranteeApplyAction#fee}
		 * </p>
		 */
		fee_collect_confirm("fee_collect_confirm", "费用确认", SCENCE_TYPE_DOC_FEE, "/guaranteeApply/feeCollectConfirm.action?popFlag=1&appId=#{appId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 收费2（复制上个收费节点）<br>
		 * 相关代码: {@link GuaranteeApplyAction#fee}
		 * </p>
		 */
		fee_two("fee_two", "收费", SCENCE_TYPE_DOC_FEE, "GuaranteeApplyAction_fee.action?popFlag=1&appId=#{appId}"),
		/**
		 * <p>
		 * 费用收取（保理应收账款使用）
		 * </p>
		 * <p>
		 * 功能描述: 费用收取（保理应收账款使用）<br>
		 * 相关代码: {@link GuaranteeApplyAction#fee}
		 * </p>
		 */
		fee_rece_finc("fee_rece_finc","费用收取",SCENCE_TYPE_DOC_FEE,"/guaranteeApply/feeForReceFinc?popFlag=1&appId=#{appId}&fincMainId=#{fincMainId}"),

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 放款确认、发卡<br>
		 * 相关代码: {@link GuaranteeApplyAction#loanConfirm}
		 * </p>
		 */
		loan_confirm("loan_confirm", "放款确认", SCENCE_TYPE_DOC_LOAN_CONFIRM, "GuaranteeApplyAction_loanConfirm.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 放款阶段
		 * </p>
		 * <p>
		 * 功能描述: 发放确认。合并放款申请放款复核功能，提交后进入贷后<br>
		 * 相关代码: {@link GuaranteeApplyAction#mergeLoan}
		 * </p>
		 */
		, mergeLoan("mergeLoan", "发放确认", null, "GuaranteeApplyAction_mergeLoan.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 签票业务
		 * </p>
		 * <p>
		 * 功能描述: 34票据部，跟踪出票<br>
		 * 相关代码: {@link VoucherAction#ticket}
		 * </p>
		 */
		, ticket_1("ticket_1", "跟踪出票", null, "VoucherAction_ticket.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 签票业务
		 * </p>
		 * <p>
		 * 功能描述: 19客户服务部，跟踪出票（客户经理）<br>
		 * 相关代码: {@link VoucherAction#ticket}
		 * </p>
		 */
		, ticket_2("ticket_2", "跟踪出票", null, "VoucherAction_ticket.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 签票业务
		 * </p>
		 * <p>
		 * 功能描述: 20票据部，判断回款方式(负责人) [全承兑][全现金][部分承兑,部分现金]<br>
		 * 相关代码: {@link VoucherAction#confirmRepayPattern}
		 * </p>
		 */
		, confirm_repay_pattern("confirm_repay_pattern", "判断回款方式", null, "VoucherAction_confirmRepayPattern.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 签票业务
		 * </p>
		 * <p>
		 * 功能描述: 22票据部，承兑回款操作（负责人）<br>
		 * 相关代码: {@link VoucherAction#acceptance}
		 * </p>
		 */
		, acceptance("acceptance", "承兑回款", null, "VoucherAction_acceptance.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 25财务部，收回票据（出纳）<br>
		 * 相关代码: {@link VoucherAction#repossessVoucher}
		 * </p>
		 */
		, repossess_voucher("repossess_voucher", "收回票据", null, "VoucherAction_repossessVoucher.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 26财务部，收回票据审核（总监）<br>
		 * 相关代码: {@link VoucherAction#repossessAudit}
		 * </p>
		 */
		, repossess_audit("repossess_audit", "收回票据审核", null, "VoucherAction_repossessAudit.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 33财务部，确定票据处理方式(总监)[贴现/托收]<br>
		 * 相关代码: {@link VoucherAction#confirmVoucherPattern}
		 * </p>
		 */
		, confirm_voucher_pattern("confirm_voucher_pattern", "确定票据处理方式", null, "VoucherAction_confirmVoucherPattern.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 27票据部，手工分单（负责人）<br>
		 * 相关代码: {@link VoucherAction#allocation}
		 * </p>
		 */
		, allocation("allocation", "手工分单", null, "VoucherAction_allocation.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 32财务部，到期托收<br>
		 * 相关代码: {@link VoucherAction#collection}
		 * </p>
		 */
		, collection("collection", "到期托收", null, "VoucherAction_collection.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		
		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 31总经理，确认<br>
		 * 相关代码: {@link VoucherAction#generalManager}
		 * </p>
		 */
		, general_manager("general_manager", "总经理确认", null, "VoucherAction_generalManager.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 28票据部，回款（票据专员）<br>
		 * 相关代码: {@link VoucherAction#payment}
		 * </p>
		 */
		, payment("payment", "回款", null, "VoucherAction_payment.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 卖断业务
		 * </p>
		 * <p>
		 * 功能描述: 30财务部，回款查款（出纳）<br>
		 * 相关代码: {@link VoucherAction#paymentInquiry}
		 * </p>
		 */
		, payment_inquiry("payment_inquiry", "回款查款", null, "VoucherAction_paymentInquiry.action?popFlag=1&appId=#{appId}&pactId=#{pactId}&fincId=#{fincId}&wkfAppId=#{wkfAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信申请<br>
		 * 相关代码: {@link MfCusCreditApplyAction#processSubmitAjax}
		 * </p>
		 */
		,credit_apply("credit_apply", "授信申请", "", "MfCusCreditApplyAction_input.action?wkfAppId=#{wkfAppId}&cusNo=#{cusNo}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 立项传签<br>
		 * 相关代码: {@link MfCusCreditApplyAction#processSubmitAjax}
		 * </p>
		 */
		,pass_sign("pass_sign", "立项传签", "", "/mfCreditProjectPassSign/input.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 尽调计划<br>
		 * 相关代码: {@link mfCusCreditApply/geReportPlan.action}
		 * </p>
		 */
		,report_plan("report_plan", "尽调计划", "", "/mfCusCreditApply/geReportPlan.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信尽调报告<br>
		 * 相关代码: {@link MfCusCreditApplyAction#processSubmitAjax}
		 * </p>
		 */
		,report("report", "授信尽调报告", "", "MfCusCreditApplyAction_getOrderDescFirst.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信批单打印<br>
		 * 相关代码: {@link mfCusCreditApply#creditBatchPrinting}
		 * </p>
		 */
		,credit_batch_printing("credit_batch_printing", "授信批单打印", "", "mfCusCreditApply/creditBatchPrinting?popFlag=1&creditAppId=#{creditAppId}&wkfAppId=#{wkfAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信申请意向书<br>
		 * 相关代码: {@link mfCusCreditApply#creditBatchPrinting}
		 * </p>
		 */
		,credit_letter_intent("credit_letter_intent", "授信申请意向书", "", "mfCusCreditApply/creditBatchPrinting?popFlag=1&creditAppId=#{creditAppId}&wkfAppId=#{wkfAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 首笔申请
		 * 相关代码: {@link mfCusCreditApply#creditBatchPrinting}
		 * </p>
		 */
		,first_apply("first_apply", "首笔申请", "", "mfCusCreditApply/creditBatchPrinting?popFlag=1&creditAppId=#{creditAppId}&wkfAppId=#{wkfAppId}")

		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 确认项目产品<br>
		 * 相关代码: {@link mfCusCreditApply/confirmProduct.action}
		 * </p>
		 */
		,confirm_product("confirm_product", "确认项目产品", "", "/mfCusCreditApply/confirmProduct.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信合同签约<br>
		 * 相关代码: {@link MfCusCreditApplyAction#processSubmitAjax}
		 * </p>
		 */
		,protocolPrint("protocolPrint", "合同签约", "", "MfCusCreditApplyAction_protocolPrint.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信审批<br>
		 * 相关代码: {@link MfCusCreditApplyAction#processSubmitAjax}
		 * </p>
		 */
		,credit_approval("credit_approval", "授信审批", "", "MfCusCreditApplyActionAjax_processSubmitAjax.action?popFlag=0&creditAppId=#{creditAppId}")
        /**
         * <p>
         * 客户授信相关
         * </p>
         * <p>
         * 功能描述: 授信预审批<br>
         * </p>
         */
        ,credit_primary_approval("credit_primary_approval", "授信预审批", "", "/mfCusCreditApproveInfo/getViewPointForPrimary?cusNo=#{cusNo}&creditApproveId=#{creditApproveId}&primaryAppId=#{primaryAppId}")

        /**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信合同清稿审批<br>
		 * 相关代码: {@link mfCusCreditPactWkf#getViewPoint}
		 * </p>
		 */
		,primary_credit_pact_approval("primary_credit_pact_approval", "授信合同清稿审批", "", "/mfCusCreditPactWkf/getViewPoint?cusNo=#{cusNo}&pactId=#{pactId}&isPrimary=1")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信审批<br>
		 * 相关代码: {@link mfCusCreditPactWkf#getViewPoint}
		 * </p>
		 */
		,credit_pact_approval("credit_pact_approval", "授信合同审批", "", "/mfCusCreditPactWkf/getViewPoint?cusNo=#{cusNo}&pactId=#{pactId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信合同签约<br>
		 * 相关代码: {@link mfCusCreditPactWkf#getViewPoint}
		 * </p>
		 */
		,protocol_manage("protocol_manage", "授信合同签约", "", "/mfCusCreditApply/protocolForManage?popFlag=1&creditAppId=#{creditAppId}")

		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 授信子合同签约<br>
		 * 相关代码: {@link mfCusCreditChildContractController#input}
		 * </p>
		 */
		,child_pact("child_pact", "立项合同", "", "/mfCusCreditChildContractController/applyInput.action?popFlag=1&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 法务上传合同<br>
		 * 相关代码: {@link /mfCusCreditChildContractController/pactDataUpload}
		 * </p>
		 */
		,upload_pact_data("upload_pact_data", "法务上传合同", "", "/mfCusCreditChildContractController/pactDataUpload.action?popFlag=0&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 立项合同签约<br>
		 * 相关代码: {@link /mfCusCreditChildContractController/input}
		 * </p>
		 */
		,child_pact_sign("child_pact_sign", "立项合同签约", "", "/mfCusCreditChildContractController/input.action?popFlag=0&creditAppId=#{creditAppId}")
		/**
		 * <p>
		 * 客户相关
		 * </p>
		 * <p>
		 * 功能描述: 合同归档<br>
		 * 相关代码: {@link /mfCusCreditChildContractController/pactDataRegular}
		 * </p>
		 */
		,pact_data_regular("pact_data_regular", "合同归档", "", "/mfCusCreditChildContractController/pactDataRegular.action?popFlag=0&creditAppId=#{creditAppId}")
		/**
		 * 展期业务担保信息登记
		 * 相关代码: {@link MfBusCollateralRelAction#insertInput}
		 */
		,extension_pledge("extension_pledge", "展期担保信息", "", "MfBusCollateralRelAction_insertInput.action?popFlag=1&cusNo=#{cusNo}&entrFlag=business&supportSkipFlag=1&extensionApplyId=#{extensionApplyId}")
		/**
		 * 展期业务尽调报告
		 * 相关代码: {@link MfBusCollateralRelAction#investigationInput}
		 */
		,extension_investigation("extension_investigation", "展期业务尽调报告", "", "MfBusExtensionApplyAction_investigationInput.action?popFlag=1&extensionApplyId=#{extensionApplyId}")
		/**
		 * 展期业务审批
		 * 相关代码: {@link MfBusExtensionApplyAction#processSubmitAjax}
		 */
		,extension_approve("extension_approve", "展期审批", "", "MfBusExtensionApplyActionAjax_processSubmitAjax.action?popFlag=0&extensionApplyId=#{extensionApplyId}")
		/**
		 * 展期业务签约
		 * 相关代码: {@link MfBusExtensionApplyAction#extensionContractSign}
		 */
		,extension_contract_sign("extension_contract_sign", "展期业务签约", "", "MfBusExtensionApplyAction_extensionContractSign.action?popFlag=1&extensionApplyId=#{extensionApplyId}")
		/**
		 * 展期业务还款计划
		 * 相关代码: {@link MfBusExtensionApplyAction#processSubmitAjax}
		 */
//		,extension_review_finc("extension_review_finc", "展期还款计划", "", "MfBusExtensionApplyActionAjax_processSubmitAjax.action?popFlag=1&extensionApplyId=#{extensionApplyId}")
		,extension_review_finc("extension_review_finc", "展期还款计划", "", "MfBusExtensionRepayPlanAction_extensionRepayPlanList.action?popFlag=1&extensionApplyId=#{extensionApplyId}")
		/**
		 * 补充资料节点
		 * 相关代码: {@link MfBusApplyWkfAction#getViewPoint}
		 */
		,supplement_data("supplement_data", "补充资料", "", "MfBusApplyWkfAction_getViewPoint.action?appId=#{appId}")

		/**
		 * 合同审批-补充资料节点 相关代码: {@link MfBusPactWkfAction#getViewPoint}
		 */
		, pact_supplement_data("pact_supplement_data", "补充资料", "", "MfBusPactWkfAction_getViewPoint.action?pactId=#{pactId}&vpNo=4")

		/**
		 * 放款审批-补充资料节点 相关代码: {@link MfBusFincAppAction#getViewPoint}
		 */
		, finc_supplement_data("finc_supplement_data", "补充资料", "", "MfBusFincAppAction_getViewPoint.action?fincId=#{fincId}&vpNo=5")
		/**
		 * 抵质押审批-补充资料节点 相关代码: {@link MfBusPledgeApprovalAction#getViewPoint}
		 */
		, pledge_supplement_data("pledge_supplement_data", "补充资料", "", "MfBusPledgeApprovalAction_getViewPoint.action?pactId=#{pactId}&vpNo=6")
		
		/**
		 * 贷后的统称
		 * 相关代码: {@link ""}
		 */
		,loan_after("loan_after", "贷后", "", "")
		/**
		 * 业务拒绝
		 * 相关代码: {@link ""}
		 */
		,loan_refuse("loan_refuse", "业务拒绝", "", "")
		/**
		 * 合同完结
		 * 相关代码: {@link ""}
		 */
		,loan_over("loan_over", "合同完结", "", "")
		/**
		 * 校验表单列表
		 * 相关代码: {@link MfCusCustomerAction#mfCusKindTableListPage}
		 */
		,kind_table_val("kind_table_val", "校验表单列表", "", "MfCusCustomerAction_mfCusKindTableListPage.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}")
		/**
		 * GPS信息登记节点
		 * 相关代码: {@link MfBusGpsRegAction#input}
		 */
		,gps_reg("gps_reg","GPS信息登记","","MfBusGpsRegAction_input.action?appId=#{appId}")
		/**
		 * 风控查询（主要用来查询第三方获取相关数据，目前天诚普惠使用）
		 * 相关代码: {@link MfBusApplyAction#riskReport}
		 */
		,risk_report("risk_report","风控查询","","MfBusApplyAction_riskReport.action?cusNo=#{cusNo}&appId=#{appId}")
		/**
		 * 预约登记（主要展示押品基本信息，上传预约回执单，目前天诚普惠的车贷使用）
		 * 相关代码: {@link MfBusCollateralRelAction#reservationReg}
		 */
		,reservation_reg("reservation_reg","预约登记","","MfBusCollateralRelAction_reservationReg.action?appId=#{appId}")
		/**
		 * 风控调查（主要展示申请基本信息，提供要件线下已经获取系统进行勾选操作，目前天诚普惠的赎楼&过桥使用）
		 * 相关代码: {@link MfLoanApplyAction#riskInvestigate}
		 */
		,risk_investigate("risk_investigate","风控调查","","MfLoanApplyAction_riskInvestigate.action?appId=#{appId}")
		/**
		 * <p>
		 * 合同阶段
		 * </p>
		 * <p>
		 * 功能描述: 合同打印， 打印合同信息  合同签约只读表单<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		,contract_print("contract_print", "合同打印", "", "MfBusPactAction_input.action?popFlag=1&appId=#{appId}")
		/**
		 * <p>
		 * 合同阶段
		 * </p>
		 * <p>
		 * 功能描述: 到账确认<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		,account_confirm("account_confirm", "到账确认", "", "mfBusPact/accountConfirm.action?popFlag=1&appId=#{appId}")
		/**
		 * <p>
		 * 合同阶段
		 * </p>
		 * <p>
		 * 功能描述: 费用确认<br>
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		,expense_confirm("expense_confirm", "费用确认", "", "mfBusPact/expenseConfirm.action?popFlag=1&appId=#{appId}")
		/**
		 * <p>
		 * 合同阶段
		 * </p>
		 * <p>
		 * 功能描述: 合同签约主办人员确认
		 * 相关代码: {@link MfBusPactAction#input}
		 * </p>
		 */
		,contract_sign_manage("contract_sign_manage", "合同签约（主办人员确认)", "", "MfBusPactAction_inputForManage.action?popFlag=1&appId=#{appId}")

		/**
		 * GPS信息（主要展示车辆信息和gps设备信息，表单信息由三方推送或者手动录入，目前天诚普惠的车贷使用）
		 * 相关代码: {@link MfBusCollateralRelAction#getGpsInfo}
		 */
		,third_gps("third_gps","GPS信息","","MfBusCollateralRelAction_getGpsInfo.action?appId=#{appId}")
		/**
		 * 银行还款计划节点
		 * 相关代码: {@link MfFundChannelRepayPlanAction#input}
		 */
		,channel_repay_plan_reg("channel_repay_plan_reg","银行还款计划","","MfFundChannelRepayPlanAction_input.action?pactId=#{pactId}")
		/**
		 * 征信授权
		 * 相关代码: {@link mfBusApply#commitBusProcessAjax}
		 */
		,credit_authorization("credit_authorization","征信授权","","/mfBusApply/commitBusProcessAjax?popFlag=0&appId=#{appId}")
		/**
		 * 缴纳定金
		 * 相关代码: {@link mfBusApply#commitBusProcessAjax}
		 */
		,pay_deposit("pay_deposit","缴纳定金","","/mfBusApply/commitBusProcessAjax?popFlag=0&appId=#{appId}")
		/**
		 * 车管验车
		 * 相关代码: {@link pledgeBaseInfo#getCarCheckDetail}
		 */
		,car_check("car_check","车管验车","","/pledgeBaseInfo/getCarCheckDetail.action?popFlag=1&appId=#{appId}&cusNo=#{cusNo}")
		/**
		 * 缴纳首付款
		 * 相关代码: {@link mfBusApply#commitBusProcessAjax}
		 */
		,pay_down_payment("pay_down_payment","缴纳首付款","","/mfBusApply/commitBusProcessAjax?popFlag=0&appId=#{appId}")
		/**
		 * 用户验车
		 * 相关代码: {@link mfBusApply#commitBusProcessAjax}
		 */
		,cus_check("cus_check","用户验车","","/mfBusApply/commitBusProcessAjax?popFlag=0&appId=#{appId}")
		/**
		 * 登记提车信息
		 * 相关代码: {@link mfCarCheckForm#inputGetCarInfo}
		 */
		,lift_car("lift_car","登记提车信息","","/mfCarCheckForm/inputGetCarInfo?popFlag=1&appId=#{appId}")
		/**
		 * 合同变更申请
		 * 相关代码: {@link mfBusPactChange#input}
		 */
		,pact_change_apply("pact_change_apply","合同变更申请","","/mfBusPactChange/input")
		/**
		 * 合同变更审批
		 * 相关代码: {@link mfBusPactChange#getViewPoint}
		 */
		,pact_change_approve("pact_change_approve","合同变更审批","","/mfBusPactChange/getViewPoint")
		/**
		 * 风险审查
		 * 相关代码: {@link MfLoanApplyAction#confirmTuningReport}
		 */
		,risk_review("risk_review","风险审查","","")
		/**
		 * 额度测算
		 */
		,quota_calc("quota_calc","额度测算","","")
		/**
		 * 评级提示
		 */
		,cus_eval_tip("cus_eval_tip","评级提示","","")
		/**
		 * 退费申请
		 * 相关代码: {@link mfBusFeeRefund#input}
		 */
		,fee_refund_apply("fee_refund_apply","退费申请","","/mfBusFeeRefund/input")
		/**
		 * 退费审批
		 * 相关代码: {@link mfBusFeeRefund#getViewPoint}
		 */
		,fee_refund_approve("fee_refund_approve","退费审批","","/mfBusFeeRefund/getViewPoint")
		/**
		 * 退费审批
		 * 相关代码: {@link mfBusFeeRefund#getViewPoint}
		 */
		,fee_refund_confirm("fee_refund_confirm","退费确认","","/mfBusFeeRefund/getViewPoint")
		/**
		 * 缴款通知书
		 */
		,charge_fee("charge-fee","缴款通知书","","")
		/**
		 * 缴款通知书审核最后一个岗位
		 */
		,charge_last_approve("charge_last_approve","缴款通知书审核最后一个岗位","","")
		/**
		 * 缴款通知书审核到账确认
		 */
		,fee_account_confirm("fee_account_confirm","缴款通知书审核到账确认","","")
		/**
		 * 保函组
		 */
		,gua_confirm_fee("gua_confirm_fee","保函组确认收费","","")
		/**
		 * 缴款通知书审核退回补充资料
		 */
		,fee_supplement_data("fee_supplement_data","缴款通知书审核退回补充资料","","")
		/**
		 * 保后变更判定节点
		 */
		,change_flag_1("change_flag_1","","","")
		/**
		 * 保后变更判定节点
		 */
		,change_flag_2("change_flag_2","","","")
		/**
		 * 保后变更判定节点
		 */
		,change_flag_3("change_flag_3","","","")
		/**
		 * 保后变更判定节点
		 */
		,change_flag_end("change_flag_end","","","")
		/**
		 * 授信判定
		 */
		,if_repeat_pact("if_repeat_pact","","","")
		/**
		 * 授信判定
		 */
		,ifRepeatPactEnd("ifRepeatPactEnd","","","")
		/**
		 * 授信判定
		 */
		,if_first_apply("if_first_apply","","","")
		/**
		 * 是否授信下业务
		 */
		,is_rel_credit("is_rel_credit","","","")
		,is_rel_credit1("is_rel_credit1","","","")
		;
		private String nodeNo;// 流程节点编号
		private String nodeName;// 流程节点名称
		private String nodeUrl;// 流程节点url

		/** 使用节点编号, 此值不再使用 2017-07-07 wangchao */

		private String scenceTypeDoc;// 要件场景编号

		private WKF_NODE(String nodeNo, String nodeName, String scenceTypeDoc, String nodeUrl) {
			this.nodeNo = nodeNo;
			this.nodeName = nodeName;
			this.scenceTypeDoc = scenceTypeDoc;
			this.nodeUrl = nodeUrl;
		}

		/**
		 * 流程节点编号
		 * 
		 * @return
		 * @author WangChao
		 * @date 2017-6-8 下午3:38:40
		 */
		public String getNodeNo() {
			return nodeNo;
		}

		/**
		 * 流程节点名称
		 * 
		 * @return
		 * @author WangChao
		 * @date 2017-6-8 下午3:39:17
		 */
		public String getNodeName() {
			return nodeName;
		}

		/**
		 * 要件场景编号
		 * 
		 * @return
		 * @author WangChao
		 * @date 2017-6-8 下午3:39:29
		 *  使用节点编号, 此值不再使用 2017-07-07 wangchao
		 */

		public String getScenceTypeDoc() {
			return scenceTypeDoc;
		}

		/**
		 * 流程节点url
		 * 
		 * @return
		 * @author WangChao
		 * @date 2017-6-8 下午3:39:39
		 */
		public String getNodeUrl() {
			return nodeUrl;
		}
	}

}
