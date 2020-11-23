package app.component.ueditor.api;

/**
 * @Description:响应参数说明
 * @Author: FrankSu
 * @Date: 2018-12-13 13:30
 */
public enum ResponseEnum {
    SUCCESS("成功", 200),
    PRIVATE_SUCCESS("成功（特定）", 0),
    FAIL("失败", 400),
    UNAUTHORIZED("未认证（签名错误）", 401),
    NOT_FOUND("接口不存在", 404),
    PARAM_NULL("参数不能为空", 405),
    USER_NOT_EXIST("用户不存在", 406),
    INCORRECT_USERNAME_OR_PASSWORD("用户名或密码错误", 407),
    DISABLED_ACCOUNT("账号不可用", 408),
    FROZEN_ROLE("角色被冻结", 409),
    PASSWORD_LENTH_TOO_SHORT("密码过短", 410),
    PASSWORD_LENTH_TOO_LONG("密码过长", 411),
    ORIGINAL_PASSWORD_INCORRECT("原始密码错误", 412),
    ACCOUNT_ALREADY_EXISTS("用户已存在", 413),
    ROLE_ALREADY_EXISTS("角色已存在", 414),
    INTERNAL_SERVER_ERROR("服务器内部错误", 500),
    DOWNLOAD_FAIL("下载失败", 415),
    DOWNLOAD_SUCCEED("下载成功", 416),

    REPORT_STATISTICS_RANGE_EMPTY("统计区间为空", 1001),
    REPORT_STATISTICS_Date_EMPTY("统计日期为空", 1002),
    REPORT_STATISTICS_DIMENSION_NULL("统计维度不存在", 1003),
    TIMESTAMP_NULL("时间戳为空", 2001),
    TIMESTAMP_ERROR("时间戳错误", 2002),
    SIGN_ERROR("签名错误", 2003),
    EXPORT_ERROR("导出数据错误", 3001),
    QUERY_REPEAT_ERROR("编号已存在，请重新输入", 6001),
    ADD("新增成功", 6002),
    UPDATE("更新成功", 6003),
    QUERY("查询成功", 6004),
    QUERY_CONTRACT("查询合同信息列表成功", 6005),
    QUERY_CUSTOMER("查询客服信息列表成功", 6006),
    REPEAL("撤销成功", 6007),

    DATA_NULL("结果为空", 9999),;


    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    ResponseEnum(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isValid(int code) {
        if (this.code == code) {
            return true;
        }
        return false;
    }
}
