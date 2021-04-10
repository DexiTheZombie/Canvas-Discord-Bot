package com.dexi.canvasbot;


import com.dexi.canvasbot.listeners.MessageListener;
import com.dexi.canvasbot.util.ConfigHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main
{

    // if anyone has any notes, 1. suck it. 2. contact me on discord: [ばか] Dexi#2001

    public static String prefix;
    public static JDA jda;

    public static void main(String[] args)
    {
        try
        {
            String token = ConfigHandler.GetJsonString("config.json","token");
            prefix = ConfigHandler.GetJsonString("config.json","prefix");
            jda = JDABuilder.createDefault(token).setActivity(Activity.of(Activity.ActivityType.WATCHING, "the CanvasAPI")).addEventListeners(new MessageListener()).build();
        }
        catch (Exception e)
        {
            System.out.println("An oopsie happened: " + e);
        }
    }
}
