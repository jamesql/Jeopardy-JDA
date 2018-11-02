package com.bot.commands;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.bot.database.DBC;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandListener extends ListenerAdapter {

	
	
	String prefix = "j!";
	String pingCmd = prefix + "ping";
	String helpCmd = prefix + "help";
	String stats = prefix + "stats";
	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	Commands cmd = new Commands(event);
    	
        //These are provided with every event in JDA
        JDA jda = event.getJDA();                       //JDA, the core of the api.
        long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        Guild guild = event.getGuild();                 //  This could be a TextChannel, PrivateChannel, or Group!


		
        
        String msg = message.getContentDisplay();              //This returns a human readable version of the Message. Similar to
                                                        // what you would see in the client.

        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
       if(message.getContentRaw().startsWith(prefix)){
           DBC db;
   		try {
   			db = new DBC(author.getId());
        if (!bot) {
        	try {
				if(db.getUser() == null){
					db.inputUser();
				}
				if (message.getContentRaw().equalsIgnoreCase(helpCmd)) cmd.helpCommand();
				if (message.getContentRaw().equalsIgnoreCase(stats)) cmd.statsCommand();
				if (message.getContentRaw().equalsIgnoreCase(pingCmd)) cmd.pingCommand();
			} catch (Exception e) {e.printStackTrace();}

        	}
		}
   		catch (Exception e1) {e1.printStackTrace();}
		
       }
    }
}
