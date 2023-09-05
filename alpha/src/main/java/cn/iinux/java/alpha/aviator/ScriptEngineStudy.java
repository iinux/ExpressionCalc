package cn.iinux.java.alpha.aviator;

import javax.script.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptEngineStudy {
    private static final ScriptEngineManager scriptEngineManager;
    private static final ScriptEngine scriptEngine;
    private static final Bindings bindings;

    static {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("js");
        bindings = scriptEngine.createBindings();
    }

    public static Boolean run(String expression, String key, Object value) {

        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        return run(expression, params);

    }

    public static Boolean run(String expression, Map<String, Object> params) {

        Boolean result = false;
        expression = expression.replaceAll("and", "&&").replaceAll("or", "||").replace("AND", "&&").replace("OR", "||");
        bindings.putAll(params);
        if (scriptEngine instanceof Compilable) {
            Compilable compilableScriptEngine = (Compilable) scriptEngine;
            try {
                CompiledScript compiledScript = compilableScriptEngine.compile(expression);
                result = (Boolean) compiledScript.eval(bindings);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result = (Boolean) scriptEngine.eval(expression, bindings);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("Q19", "Y");
        map.put("Q18", "N");
        map.put("Q7", "Y");
        Boolean status1 = run(" ( Q18 ==  'Y'  or Q19 ==  'Y'  or Q8 ==  'Y'  )  and Q7 ==  'Y' ", map);
        System.out.println(status1);

        Boolean status = run("x == 3512 OR x==3513", "x", "3513");
        System.out.println(status);
    }
}
