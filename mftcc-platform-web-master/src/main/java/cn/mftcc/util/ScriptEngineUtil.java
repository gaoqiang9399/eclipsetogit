package cn.mftcc.util;

import javax.script.ScriptException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.Compilable;
import javax.script.Bindings;
import javax.script.CompiledScript;
import java.util.HashMap;
import java.util.Map;

public class ScriptEngineUtil {

//    public static void main(String ards[]) throws ScriptException {
//        Map<String,Object> map=new HashMap();
//        map.put("10010", 2.5);
//        map.put("T", 30);
//        map.put("A", 100);
//        map.put("P0", 100);
//        String script = "10010"; //定义函数并调用
//        System.out.println(getCalData(script,map).toString());
//    }
    public static Object getCalData(String script,  Map<String,Object> map) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        Compilable compilable = (Compilable) engine;
        Bindings bindings = engine.createBindings(); //Local级别的Binding
        CompiledScript JSFunction = null; //解析编译脚本函数
        JSFunction = compilable.compile(script);
        for( Map.Entry<String,Object> tmp:map.entrySet()){
            bindings.put(tmp.getKey(), tmp.getValue());
        }
        Object result = JSFunction.eval(bindings);
        return result;
    }
}
