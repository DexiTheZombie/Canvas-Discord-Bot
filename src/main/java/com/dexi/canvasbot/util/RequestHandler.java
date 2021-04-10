package com.dexi.canvasbot.util;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler
{
    public static JSONArray GetJSON(String baseUrl, String path) throws Exception
    {
        try
        {
            String authToken = ConfigHandler.GetJsonString("canvasConfig.json", "authToken");

            JSONParser jsonParser = new JSONParser();
            String _url = baseUrl + path;
            URL url = new URL(_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + authToken);
            con.connect();

            int responseCode = con.getResponseCode();

            System.out.println("Sending 'GET' request to " + (baseUrl + path) + ".");
            System.out.println("Response Code : " + responseCode);


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();

            return (JSONArray) jsonParser.parse(response.toString());

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
