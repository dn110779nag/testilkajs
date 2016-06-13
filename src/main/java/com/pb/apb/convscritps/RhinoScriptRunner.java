/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.UniqueTag;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Новомлинов Александр
 */
public class RhinoScriptRunner extends AbstractScriptRunner {

    private final Context ct = Context.enter();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public IOValues runScript(String filePath, String jsonFilePath) throws IOException, ScriptException {

        // evaluate JavaScript statement
        File scriptFile = new File(filePath);
        File jsonFile = new File(jsonFilePath);
        if (!scriptFile.exists()) {
            throw new FileNotFoundException("Не найден файл со скриптом " + scriptFile.getAbsolutePath());
        } else if (!jsonFile.exists()) {
            throw new FileNotFoundException("Не найден файл со json'ом " + jsonFile.getAbsolutePath());
        }

        try (BufferedReader fr = Files.newBufferedReader(scriptFile.toPath(), Charset.forName("utf-8"));
                BufferedReader jr = Files.newBufferedReader(jsonFile.toPath(), Charset.forName("utf-8"));) {
            StringBuilder json = new StringBuilder();
            read(json, jr);
            StringBuilder buf = new StringBuilder();
            read(buf, fr);
            return runScriptFromStrings(buf.toString(), json.toString());
        }
    }

    @Override
    public IOValues runScriptFromStrings(String script, String inputJson) throws IOException, ScriptException {
        try (
                InputStreamReader pr = new InputStreamReader(this.getClass().getResourceAsStream("/js/util/prefix.js"), "UTF-8");
                BufferedReader bpr = new BufferedReader(pr);
                InputStreamReader sr = new InputStreamReader(this.getClass().getResourceAsStream("/js/util/suffix.js"), "UTF-8");
                BufferedReader bsr = new BufferedReader(sr);) {
            StringBuilder buf = new StringBuilder();
            read(buf, bpr);
            buf.append(script);
            read(buf, bsr);
            logger.debug("script = " + buf);
            Scriptable scope = new Global(ct);
            ScriptableObject.putProperty(scope, "dataStr", inputJson);
            long start = System.nanoTime();
            ct.evaluateString(scope, buf.toString(), "<cmd2>", 1, null);
            logger.debug("execution time: "+(System.nanoTime()-start));
            Scriptable ret = (Scriptable) scope.get("ret", scope);
            return new IOValues(ret.get("i", ret).toString(), ret.get("o", ret).toString());
        }
    }
    
    

}
