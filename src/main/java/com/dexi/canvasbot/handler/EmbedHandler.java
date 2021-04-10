package com.dexi.canvasbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import java.awt.*;
import java.time.OffsetDateTime;

public class EmbedHandler
{
    public static Message CreateEmbedMsg(String title, String titleUrl, String description, String dueDate, String pointValue, String assignmentID)
    {
        Message msg =  new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setTitle(title, titleUrl)
                        .setColor(new Color(0xD0021B))
                        .setTimestamp(OffsetDateTime.now())
                        .setFooter("Canvas Bot", "https://pbs.twimg.com/profile_images/1132832989841428481/0Ei3pZ4d_400x400.png")
                        .addField("Points Possible", pointValue, true)
                        .addField("ID", assignmentID, true)
                        .addField("Due", dueDate, false)
                        .build())
                .build();

        return msg;
    }
}
