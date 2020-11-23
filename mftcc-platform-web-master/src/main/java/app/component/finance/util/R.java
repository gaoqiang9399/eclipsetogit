package app.component.finance.util;

import java.util.HashMap;

/**
 * 返回数据
 * 
 * @author LiuXB
 *
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", "0000");
		put("msg", "success");
	}

	public static R r(String code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(String msg) {
		return r("1111", msg);
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public static R okResult(Object obj) {
		R r = new R();
		r.put("result", obj);
		return r;
	}

	public Object getResult() {
		return this.get("result");
	}

	public boolean isOk() {
		return "0000".equals(this.get("code"));
	}

	public String getMsg() {
		return (String) this.get("msg");
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
