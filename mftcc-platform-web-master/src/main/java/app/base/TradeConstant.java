package app.base;


public class TradeConstant {
	/**
	 * 待修改目录，改动量未知
	 */
	
	/************��Ȩ�ཻ��*************/
	
	public static String TRADE_CODE_LOAN_PAY = "6681";//���֧��
	
	public static String TRADE_CODE_REDISC_OUT_NOTIFY = "6679";//ת��������/������֪ͨ
	
	public static String TRADE_CODE_REDISC_IN_NOTIFY = "6678";//����/ת�������뿪��֪ͨ
	
	public static String TRADE_CODE_ISSUE_ACCP_NOTIFY = "6677";//��г�Ʊ֪ͨ
	
	public static String TRADE_CODE_GAGE_OUT = "6684";//����ѺƷ����֪ͨ
	
	public static String TRADE_CODE_WV_BAD_DEBTS = "6682";//���˺���֪ͨ
	
	public static String TRADE_CODE_LOAN_EXTEND = "6676";//���չ��֪ͨ
	
	public static String TRADE_CODE_GRANT_LOAN = "6675";//����֪ͨ
	
	public static String TRADE_CODE_GAGE_IN = "6683";//����ѺƷ���֪ͨ
	
	public static String TRADE_CODE_SECURITY_MONEY_RATE = "6685";//��֤�����ʵ���
	
	public static String TRADE_CODE_SECURITY_MONEY_WITHDRAW = "6686";//��֤��ȡ��
	
	public static String TRADE_CODE_ISSUE_CVRG_NOTIFY = "6689";//����֪ͨ

	/************ʵʱ�ཻ��*************/
	
	public static String TRADE_CODE_QUERY_LOAN_INFO = "6674";//�����Ϣ��ѯ
	
	public static String TRADE_CODE_QUERY_AUTH_INFO = "6680";//������Ȩ��Ϣ��ѯ
	
	public static String TRADE_CODE_CANCEL_AUTH = "6673";//��Ȩ����
	
	public static String TRADE_CODE_QUERY_AC_BAL = "6672";//�˻�����ѯ
	
	public static String TRADE_CODE_QUERY_CIF_BAL = "6671";//�ͻ�����ѯ
	
	public static String TRADE_CODE_QUERY_CIF_NO = "6670";//�ͻ��Ż�ȡ
	
	public static String TRADE_CODE_QUERY_GUAR_CIF_NO = "6691";//�����ͻ��Ż�ȡ
	
	public static String TRADE_CODE_ALTER_USER_PW = "6692";//�޸Ĳ���Ա����
	
	public static String TRADE_CODE_QUERY_USER_FP = "XXX";//��ѯ����Աָ�� ��δȷ��
	public static String TRADE_CODE_QUERY_USER_VM = "9617";//��ѯ����Ա��¼��֤��ʽ
	
	public static String TRADE_CODE_QUERY_SECURITY_MONEY = "6690";//��ѯ��֤����Ϣ
	
	/*
	 * ��Ȩ����
	 */
	public static String TRADE_AUTH_TYPE_GAGE_IN = "01";//����ѺƷ���֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_LOAN = "02";//����֪ͨ
	
	public static String TRADE_AUTH_TYPE_EXTEND_LOAN = "03";//���չ��֪ͨ
	
	public static String TRADE_AUTH_TYPE_CHANGE_RATE = "04";//���ʱ��֪ͨ
	
	public static String TRADE_AUTH_TYPE_STS_TRANS = "05";//�����̬ת��֪ͨ
	
	public static String TRADE_AUTH_TYPE_WV = "06";//���˺���֪ͨ
	
	public static String TRADE_AUTH_TYPE_GAGE_OUT = "07";//����ѺƷ����֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_ACCP = "08";//��г�Ʊ֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_DISC = "09";//���ֿ���֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_REDISCOUT = "10";//ת����ת������֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_REDISCIN = "11";//ת����ת�뿪��֪ͨ
	
	public static String TRADE_AUTH_TYPE_DUE_CVRG = "14";//��������֪ͨ
	
	public static String TRADE_AUTH_TYPE_SECUTIRY_MONEY = "16";//���ɱ�֤��
	
	public static String TRADE_AUTH_TYPE_LOAN_PAY = "15";//���֧��
	
	public static String TRADE_PAYMENT_CODE="PAYENT";//֧��ָ���ύ
	public static String TRADE_ENTDIS_CODE="ENTDIS";//��ҵ����ύ
	public static String TRADE_PERDIS_CODE="PERDIS";//��������ύ
	public static String TRADE_PERDISCOL_CODE="PERDISCOL";//��۸���ָ���ύ���ܼ���
	public static String TRADE_QACCBAL_CODE="QACCBAL";//����ѯ
	public static String TRADE_QPERDIS_CODE="QPERDIS";//������۲�ѯ
	public static String TRADE_QPD_CODE="QPD";//��ѯ������ϸ
	public static String TRADE_QHISD_CODE="QHISD";//��ѯ��ʷ��ϸ
	public static String TRADE_QBILL_CODE="QBILL";//��ѯ���˵�
	public static String TRADE_QENTDIS_CODE="QENTDIS";//��ҵ��۲�ѯ
	public static String TRADE_QPERINF_CODE="QPERINF";//�ɷѸ�����Ϣ��ѯ
	public static String TRADE_QENTINF_CODE="QENTINF";//�ɷ���ҵ��Ϣ��ѯ
	public static String TRADE_QPERDISTM_CODE="QPERDISTM";//��۸���(��ʱ��)ָ���ѯ
	public static String TRADE_QPAYENT_CODE="QPAYENT";//֧��ָ���ѯ
	
	public static String TRADE_ELECSIG_CODE="ELECSIG";//����ǩ��
	
	public static String TRADE_TYPE_QUERY="01";// ��ѯ��
	public static String TRADE_TYPE_COMMIT="02";// ������
	/**
	 * ͨ�ŷ�ʽ U:tuxedo
	 */
	public static final String COMM_TYPE_U="U";
	/**
	 * ͨ�ŷ�ʽ T:tcp/ip
	 */
	public static final String COMM_TYPE_T="T";
	/**
	 * ͨ�ŷ�ʽ H:http
	 */
	public static final String COMM_TYPE_H="H";
	/**
	 * ���ķ��� 1��
	 */
	public static final String COMM_DIR_SEND="1";
	/**
	 * ���ķ��� 0����
	 */
	public static final String COMM_DIR_RECE="0";
	/**
	 * HTTP ǩ��˿�
	 */
	public static final int SIGN_PORT=449;
	/**
	 * HTTP ����·��
	 */
	public static final String HTTP_SECURITY_PATH="/servlet/ICBCCMPAPIReqServlet";
	/**
	 * �ýڵ�Ϊ����
	 */
	public static final String XML_NODE_INPUT_Y="Y";
	/**
	 * �ýڵ�Ϊ�Ǳ���
	 */
	public static final String XML_NODE_INPUT_N="N";
	/**
	 * �ڵ�ֵ����Ϊ�ַ�
	 */
	public static final String XML_NODE_VALUE_TYPE_STRING="0";
	/**
	 * �ڵ�ֵ����Ϊ����
	 */
	public static final String XML_NODE_VALUE_TYPE_INT="1";
	/**
	 * ����ͷ����
	 */
	public static final int XML_MSG_HEAD_LENGTH=8;
	/**
	 * ��ȡ���ر��ĳ�ʱ
	 */
	public static final String XML_RESULT_MSG_OUT_TIME="OUT_TIME_ERROR";
	
}
