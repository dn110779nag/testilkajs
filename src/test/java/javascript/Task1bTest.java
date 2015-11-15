/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javascript;

import com.jayway.jsonpath.JsonPath;
import com.pb.apb.convscritps.AbstractScriptRunner;
import com.pb.apb.convscritps.NashornScriptRunner;
import com.pb.apb.convscritps.IOValues;
import com.pb.apb.convscritps.RhinoScriptRunner;
import java.io.IOException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minidev.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Новомлинов Александр
 */
public class Task1bTest {
    
    public Task1bTest() {
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

    private final AbstractScriptRunner sr = new RhinoScriptRunner();
    
    
    @Test
    public void test1() throws IOException, ScriptException {
        System.out.println("test1");
        
        IOValues ret = sr.runScript(
                        "src/main/javascript/task1b.js", 
                        "src/test/javascript/task1b.json");
        System.out.println("input="+ret.getInputValue()+"\n\n");
        
        System.out.println("output="+ret.getOutputValue());
        
        //Object obj = JsonPath.read(ret.getInputValue(), "data.listHOS[3].listProviderService.providerServices");
//        Object obj = JsonPath.read(ret.getInputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices");
//        System.out.println("in="+((JSONArray)obj).size());
//        Object outObj = JsonPath.read(ret.getOutputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices");
//        System.out.println("out="+((JSONArray)outObj).size());
//        assertEquals(1, ((JSONArray)outObj).size());
//        String provideId = JsonPath.read(ret.getOutputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices[0].provideId");
//        assertEquals("403281",provideId);
    }
    
    
    
    
}
