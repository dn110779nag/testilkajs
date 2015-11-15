/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps;

import java.io.BufferedReader;
import java.io.IOException;
import javax.script.ScriptException;

/**
 *
 * @author Новомлинов Александр
 */
public abstract class AbstractScriptRunner {

    protected void read(StringBuilder b, BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            b.append(line).append("\n");
        }
    }
    
    public abstract IOValues runScript(String filePath, String jsonFilePath) throws IOException, ScriptException;
    public abstract IOValues runScriptFromStrings(String script, String inputJson) throws IOException, ScriptException;
}
