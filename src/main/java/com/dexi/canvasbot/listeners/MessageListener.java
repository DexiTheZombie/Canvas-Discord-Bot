package com.dexi.canvasbot.listeners;

import com.dexi.canvasbot.Main;
import com.dexi.canvasbot.handler.CommandHandler;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Arrays;

public class MessageListener extends ListenerAdapter
{

    // if you ask why this is the only commented script then fuck you its 5am and im bloody tired ive been working all night trying to get this fucking thing optimised. but chances are ill get around to commenting the other scripts one day.

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        }
        else if(event.getAuthor().isBot() || !event.getMessage().getContentDisplay().startsWith(Main.prefix)) { return; }
        else
        {
            // Split input via spaces [might do spaces and split content inside ""]
            String[] rawInputSplit = event.getMessage().getContentDisplay().split(" ");

            // Get string and remove prefix.
            String cmd = rawInputSplit[0];
            cmd = cmd.substring(1);

            // Detect and store args if non are found issue lone command with blank args.
            String[] args;
            if(rawInputSplit.length > 1)
            {
                // Populate args.
                args = new String[rawInputSplit.length - 1];
                for (int i = 1; i < rawInputSplit.length; i++)
                {
                    args[i-1] = rawInputSplit[i];
                }
            }
            else
            {
                // Set args[0] to 0 as a blank. used for getindex as a default value
                args = new String[]{"0"};
            }

            //System.out.println("cmd: " + cmd + ", args: " + Arrays.toString(args)); // Debug message to display args and command used. will later reflex user who issued command.

            switch (cmd)
            {
                case "getindex": // List a specific assignment based on index provided.
                {
                    // Parse int from args[0]
                    try
                    {
                        int index = Integer.parseInt(args[0]);
                        event.getChannel().sendMessage(CommandHandler.GetAssignmentIndex(index)).complete();
                        System.out.printf("[%s] issued command: %s with args: %s\n", event.getAuthor().getName(), cmd, Arrays.toString(args));
                    }
                    catch (Exception e)
                    {
                        // Catches invalid args (if the argument given in args[0] is not parsable as an integer then it returns an error.)
                        System.out.printf("[%s] is not an int. Error: %s\n", args[0], e);
                        event.getChannel().sendMessage("Invalid index.").complete();
                    }
                    break;
                }
                case "list": // Lists assignment names + index.
                {
                    // Gets lists from api and prints them into the chat.
                    try
                    {
                        event.getChannel().sendMessage(CommandHandler.GetAssignmentList()).complete();
                        System.out.printf("[%s] issued command: %s with args: %s\n", event.getAuthor().getName(), cmd, Arrays.toString(args));
                    }
                    catch (Exception e)
                    {
                        // If something fails getting the list then do this.
                        System.out.println("Something broke" + e);
                        event.getChannel().sendMessage("Something broke, please try again in a few minutes.").complete();
                    }
                    break;
                }
                default: // If they fuck up.
                {
                    // Debug prints.
                    event.getChannel().sendMessage("Command not found.\nAvailable commands are: !getindex <index>, !list").complete();
                    System.out.printf("[%s] didnt use a command.\n", event.getAuthor().getName());
                    break;
                }
            }
        }
    }
}
