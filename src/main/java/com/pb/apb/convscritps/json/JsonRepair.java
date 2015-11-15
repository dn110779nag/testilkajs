/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.Pattern;

/**
 * Класс для ремонта json-из конвейера
 * @author Новомлинов Александр
 */
public class JsonRepair {
    public String loadJson(File path) throws IOException{
        try(BufferedReader br = Files.newBufferedReader(path.toPath(), Charset.forName("UTF-8"))){
            return loadJson(br);
        }
    }
    
    public String loadJson(BufferedReader br) throws IOException{
            String line;
            StringWriter sw = new StringWriter();
            while((line=br.readLine())!=null){
                sw.write(line);
            }
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(sw.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
            String str = gson.toJson(el);
            return str;
    }
    
    public String loadJson(String in) throws IOException{
        StringReader sr = new StringReader(in);
        BufferedReader br = new BufferedReader(sr);
        return loadJson(br);
    }
    private final Pattern normPattern = Pattern.compile(".*\".*\": *\".*[^\"]");
    
    private final Pattern endPattern = Pattern.compile("[^\"]*\"");
            
    private void normAnalizer(String str){
        
    }
    
    
    public static void main(String[] args) throws IOException {
//        Pattern normPattern = Pattern.compile(".*\".*\": *\".*[^\"]");
//        System.out.println(""+normPattern.matcher("\"asdas\": \"aadsaksld;\"").matches());
        String str = new JsonRepair().loadJson(new File("src/test/resources/1.json"));
        System.out.println(str);
    }
}
