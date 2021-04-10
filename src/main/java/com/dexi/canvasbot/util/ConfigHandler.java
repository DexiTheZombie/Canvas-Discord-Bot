package com.dexi.canvasbot.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigHandler
{
    public static void CreateConfig(String filename, String[] initialKeys, String[] initialVals) throws Exception
    {
        JSONObject jObj = new JSONObject();

        for (int i = 0; i < initialKeys.length; i++)
        {
            jObj.put(initialKeys[i], initialVals[i]);
        }

        Files.write(Paths.get(filename), jObj.toJSONString().getBytes());
    }

    public static void UpdateConfig(String filename, String key, String val) throws Exception
    {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jObj = (JSONObject) jsonParser.parse(reader);

        jObj.put(key, val);

        Files.write(Paths.get(filename), jObj.toJSONString().getBytes());
    }

    public static Object ReadConfig(String filename) throws Exception
    {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public static String GetJsonString(String filename,String key) throws Exception
    {
        JSONObject jsonObject = (JSONObject) ReadConfig(filename);

        return jsonObject.get(key).toString();
    }

    public static Integer GetJsonInt(String filename, String key) throws Exception
    {
        JSONObject jsonObject = (JSONObject) ReadConfig(filename);

        try
        {
            return Integer.parseInt(jsonObject.get(key).toString());
        }
        catch (Exception e)
        {
            System.out.println("Value of key " + key + ", is not an integer. If it is an int then fuck.");
            return 1;
        }
    }
}
