package app.component.ueditor.api;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Constant {


    public static  final  Integer PAGE_SIZE = 10;
    public static  final  String SUCCESS = "000000";
    public static  final  String SAVE_SUCCESS = "-1";
    public static  final  String SUCCESS_MSG = "请求成功";
    public static  final  String SUCCESS_MSG1 = "保存失败";
    public static  final  String SUCCESS_MESSAGE = "保存成功";
    public static  final  String ERROR = "999999";
    public static  final  String ERROR_MSG = "服务器异常";
    public static  final  String ERROR1 = "222222";
    public static  final  String ERROR_MSG1 = "表间校验错误，保存失败";
    public static  final  String ERROR_MSG2 = "表内校验错误，保存失败";

    public static  final  String DOWNLOAD_FAIL = "下载失败!";
    public static  final  String DOWNLOAD_SUCCEED = "下载成功!";

    // 主办风控
    public static final String RISK_CONTROL_ROLE_TYPE = "5";
    // 法律合规
    public static final String LEGAL_COMPLIANCE_ROLE_TYPE = "6";

    public static final String CLAIM_PROJECT_FILE_MSG = "认证失败";
    public static final String CLAIM_PROJECT_SUCCESS_MSG = "认证成功";

    public static final Integer CLAIM_PROJECT_CODE = 333333;

    public static final String CLAIM_PROJECT_MSG = "已认领，无需重复认领";

    public static final String CLAIM_PROJECT_NO_PERMISSION = "没有权限认领";

    public static final String PROJECT_LOSE_EFFICACY = "项目已失效";

    public static final Integer ERROR_CODE = 999999;

    public static final Integer PARAM_NULL = 111111;
    public static final String PARAM_NULL_MSG = "参数为空";

    public static  final  String ERROR_MESSAGE = "该角色已被使用";

    //尽调报告URL
//    public static final String REPORT_URL = System.getProperty("catalina.home")+"/webapps/customer-data/reportword";
    public static final String REPORT_URL = "/usr/local/file/reportword";

    //尽调报告模板URL
//     public static final String TEMPLATE_URL = System.getProperty("catalina.home")+"/webapps/customer-data/template";
     public static final String TEMPLATE_URL = "/usr/local/file/template";

    /*尽调报告查看URL*/
    //dev
//    public static final String REPORT_VIEW_URL = "http://localhost:8080/customer-data/reportword/";
    //test
    public static final String REPORT_VIEW_URL = "/usr/local/file/reportword/";
    /*生产*/
    //public static final String REPORT_VIEW_URL = "http://101.132.32.76:8080/customer-data/reportword/";

    //暂无图片URL
//    public static final String NO_PICTURE_URL = System.getProperty("catalina.home")+"/webapps/customer-data/template/no_picture.jpg";
    public static final String NO_PICTURE_URL = "/usr/local/file/reportword/no_picture.jpg";
    //public static final String NO_PICTURE_URL = "";

    /*图片上传路径*/
    //test,prod
    public static final String URL = "/usr/local/file/upload/";//224 test目录
    //dev
//    public static final String URL = "/usr/local/file/upload/";

    /*各种模板文件路径*/
    //dev
//    public static final String FILEPATH = "/usr/local/file/template/";
//    public static final String FILEPATH = System.getProperty("catalina.home")+"/webapps/customer-data/assets/lib/template/";
    //Test 224
    public static final String FILEPATH = "/usr/local/file/template/";
    //prod
    //public static final String FILEPATH = "/opt/crm/apache/webapps/customer-data/assets/lib/template/";

    public static final String PDF = ".pdf";

    /*指定输出的文件*/
    public static final String HETONGADDR = "/usr/local/ywxt/hetong/";

    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 6, 3, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());


}
