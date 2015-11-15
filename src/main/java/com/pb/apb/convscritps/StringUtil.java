/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 *
 * @author Новомлинов Александр
 */
public class StringUtil {
    public static StringBuilder readFile(File f, StringBuilder ... buf) throws IOException{
        StringBuilder b = buf.length!=0?buf[0]:new StringBuilder();
        try(BufferedReader br = Files.newBufferedReader(f.toPath(), Charset.forName("utf-8"))){
            String line;
            while((line=br.readLine())!=null){
                b.append(line).append("\n");
            }
        }
        return b;
    }
}
