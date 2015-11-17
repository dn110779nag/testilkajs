/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javascript;

import com.jayway.jsonpath.JsonPath;
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
public class Task1Test {
    
    public Task1Test() {
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

    private final RhinoScriptRunner sr = new RhinoScriptRunner();
    
    
    @Test
    @Ignore
    public void test1() throws IOException, ScriptException {
        System.out.println("test1");
        
        IOValues ret = sr.runScript(
                        "src/main/javascript/task1.js", 
                        "src/test/javascript/task1.json");
//        System.out.println("input="+ret.getInputValue());
//        
//        System.out.println("output="+ret.getOutputValue());
        
        //Object obj = JsonPath.read(ret.getInputValue(), "data.listHOS[3].listProviderService.providerServices");
        Object obj = JsonPath.read(ret.getInputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices");
        System.out.println("in="+((JSONArray)obj).size());
        Object outObj = JsonPath.read(ret.getOutputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices");
        System.out.println("out="+((JSONArray)outObj).size());
        assertEquals(1, ((JSONArray)outObj).size());
        String provideId = JsonPath.read(ret.getOutputValue(), "data.listHOS[3].mapOflistProviderService.mapOfListProvidersService.39.providerServices[0].provideId");
        assertEquals("403281",provideId);
    }
    
    @Ignore
    @Test
    public void test1b() throws IOException, ScriptException {
        System.out.println("test1b");
        IOValues ret = sr.runScript(
                        "src/main/javascript/task1b.js", 
                        "src/test/javascript/task1b.json");
//        System.out.println("input="+ret.getInputValue());
//        
        System.out.println("output="+ret.getOutputValue());
        
        //Object obj = JsonPath.read(ret.getInputValue(), "data.listHOS[3].listProviderService.providerServices");
        Object err = JsonPath.read(ret.getOutputValue(), "stack");
        assertNull("Ошибка во время выполнения скрипта", err);
        Object obj = JsonPath.read(ret.getOutputValue(), "res.invoiseList");
        assertEquals("invoiseList", 2, ((JSONArray)obj).size());
//        Object osl = JsonPath.read(ret.getOutputValue(), "res.otherServiseList");
//        assertEquals("otherServiseList", 1, ((JSONArray)osl).size());
        Object hhl = JsonPath.read(ret.getOutputValue(), "res.householdList");
        assertEquals("householdList", 4, ((JSONArray)hhl).size());
        //Object irsL = JsonPath.read(ret.getOutputValue(), "householdList[?(@.hos='32GMESVQ3W2J00')][0].irsList");
        Object irsL = JsonPath.read(ret.getOutputValue(), "res.householdList[?(@.hos=='32GMESVQ3W2J00')].irsList");
        irsL = JsonPath.read(irsL, "[0]");
        //Object irsL = JsonPath.read(ret.getOutputValue(), "householdList[2].irsList");
        System.out.println("\n\nret ===> \n"+irsL);
        assertEquals("irsList", 3, ((JSONArray)irsL).size());
        //System.out.println(""+((JSONArray)obj).size());
        
    }
    
    @Ignore
    @Test
    public void testExtractTmpls() throws IOException, ScriptException {
        System.out.println("testExtractTmpls");
        IOValues ret = sr.runScript(
                        "src/main/javascript/extractTmpls.js", 
                        "src/test/javascript/task1result.json");
        System.out.println("output="+ret.getOutputValue());
        Object obj = JsonPath.read(ret.getOutputValue(), "tmpls");
        assertEquals("tmpls", 8, ((JSONArray)obj).size());
    }
    
    
    
}
