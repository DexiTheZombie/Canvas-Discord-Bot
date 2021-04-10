package com.dexi.canvasbot.handler;

import com.dexi.canvasbot.util.ConfigHandler;
import com.dexi.canvasbot.util.RequestHandler;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandHandler
{
    public static Message GetAssignmentIndex(int index) throws Exception
    {
        int courseID = ConfigHandler.GetJsonInt("canvasConfig.json", "courseID");

        JSONArray jObj = RequestHandler.GetJSON("https://bfc.instructure.com/", "api/v1/courses/" + courseID + "/assignments");

        if(index >= jObj.size()) { return new MessageBuilder("Invalid index.").build(); }

        JSONObject object = (JSONObject) jObj.get(index);

        String[] dateSplit = object.get("due_at").toString().replace('T', ' ').replace('Z', ' ').split(" ");

        String[] date = dateSplit[0].split("-");
        String newDate = date[2] + "-" + date[1] + "-" + date[0] + " ";

        String time = dateSplit[1];

        String newDueDate = newDate + time;


        Message embedMessage = EmbedHandler.CreateEmbedMsg(object.get("name").toString(), object.get("html_url").toString(), object.get("description").toString(), newDueDate, object.get("points_possible").toString(), object.get("id").toString());
        return embedMessage;
    }

    public static Message GetAssignmentList() throws Exception
    {
        int courseID = ConfigHandler.GetJsonInt("canvasConfig.json", "courseID");

        JSONArray jObj = RequestHandler.GetJSON("https://bfc.instructure.com/", "api/v1/courses/" + courseID + "/assignments");

        StringBuilder msgString = new StringBuilder();
        for (int i = 0; i < jObj.size(); i++)
        {
            JSONObject object = (JSONObject) jObj.get(i);
            String name = ("```" + i + " : " + object.get("name").toString() + "```");
            msgString.append(name);
        }

        return new MessageBuilder(msgString).build();
    }
}
