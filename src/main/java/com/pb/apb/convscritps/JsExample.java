/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 *
 * @author Новомлинов Александр
 */
public class JsExample {
    
    public static class MyClassFilter  implements ClassFilter{

        @Override
        public boolean exposeToScripts(String s) {
            return true;
            //return s.startsWith("allowed.classes");
        }

    }
    
    public static void main(String[] args) throws ScriptException {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine(new MyClassFilter());
        ScriptContext newContext = new SimpleScriptContext();
        engine.setContext(newContext);
        engine.eval("java.lang.System.out.println(java.lang.System.getProperties())");
    }
    
    
}
