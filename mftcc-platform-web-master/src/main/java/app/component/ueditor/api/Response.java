package app.component.ueditor.api;

import java.util.LinkedHashMap;

/**
 * Created by zhiang.feng on 2018/6/18.
 */
public class Response<T> extends LinkedHashMap<String, T> {

    private static final long serialVersionUID = -9060500137533012366L;

    public boolean isSuccess() {
        return (Boolean) this.get("success");
    }

    public String getMsg() {
        return (String)this.get("msg");
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }

    //不能设置为private,否则motan服务类型转化Response-->HashMap错
    public Response() {
    }


    public static Response error(Integer code,String msg) {
        Response r = new Response();
        r.put("code",code);
        r.put("msg",msg);
        r.put("success",false);
        return r;
    }

    /**  
     * 功能描述：错误信息封装
     * @param code, msg
     * @return com.maoju.credit.common.credit.Response
     * @author wumingchang
     * @date 2018/11/26 14:28
     */
    public static Response error(String code,String msg) {
        Response r = new Response();
        r.put("code",code);
        r.put("msg",msg);
        r.put("success",false);
        return r;
    }



    public  static Response ok(String msg) {
        Response r = ok();
        r.put("msg",msg);
        return r;
    }

    public static Response ok() {
        Response r = new Response();
        r.put("code",new Integer(0));
        r.put("msg","成功");
        r.put("success",true);

        return r;
    }

    public Response<T> putData(T value) {
        super.put("data", value);
        return this;
    }

    public T getData() {
        return (T) super.get("data");
    }


}
