/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Новомлинов Александр
 */
public class NashornScriptRunnerTest {
    
    public NashornScriptRunnerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of runScript method, of class ScriptRunner.
     */
    @Test
    public void testRunScript() throws Exception {
        System.out.println("runScript");
        String filePath = "src/test/javascript/com/pb/apb/convscritps/test.js";
        String jsonFilePath = "src/test/javascript/com/pb/apb/convscritps/test.json";
        NashornScriptRunner instance = new NashornScriptRunner();
        String str =  instance.runScript(filePath, jsonFilePath).getOutputValue();
        System.out.println("json="+str);

    }
    
}
