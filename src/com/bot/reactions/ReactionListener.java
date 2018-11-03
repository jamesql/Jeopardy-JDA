package com.bot.reactions;

import java.awt.Color;
import java.util.List;

import com.bot.database.DBC;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

	private String aVar = "508121630103437323";
	private String bVar = "508121676869795860";
	private String cVar = "508121720574705665";
	private String dVar = "508121771132846090";
	private String global;
	private JDA bot;
	private MessageChannel channel;
	
	
	boolean answered = false;
	String user;
	int rightAns;
	private String rightString;
	
	EmbedBuilder eb = new EmbedBuilder();
	
	public ReactionListener(String userid, int right, JDA jda,  MessageChannel inputChannel, String rightAnswer) {
		user = userid;
		rightAns = right;
		bot = jda;
		channel = inputChannel;
		rightString = rightAnswer;
	}
	
	
	public void correctResponse() {
		eb.setTitle("Nice!");
		eb.addField("You Answered Correct", "+1 Correct Answer!", false);
		eb.setFooter("Get Thinking!", null);
		eb.setColor(Color.CYAN);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void wrongResponse() {
		eb.setTitle("Try Again!");
		eb.addField("You Answered Wrong", "Better luck next time!", false);
		eb.addField("Correct Answer :", "" + rightString, false);
		eb.setFooter("Get Thinking!", null);
		eb.setColor(Color.CYAN);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		Guild mainGuild = bot.getGuildById("479518188711706627");
		String eMessageId = event.getMessageId();
		MessageReaction reaction = event.getReaction();
		ReactionEmote emote = reaction.getReactionEmote();
		User auth = event.getUser();
		boolean isBot = auth.isBot();
		
		List<Emote> emotes = mainGuild.getEmotes();
		aVar = emotes.get(0).getId();
		bVar = emotes.get(2).getId();
		cVar = emotes.get(1).getId();
		dVar = emotes.get(3).getId();
		
		
		if(!answered) {
			if(!isBot) {
				if(auth.getId().equals(user)) {
					switch(rightAns) {
					case 0 : global = aVar;
						   break;
					case 1 : global = bVar;
					   		break;
					case 2 : global = cVar;
							break;
					case 3 : global = dVar;
					   		break;
					}
					
					if (emote.getId().equals(global)) {
						try {
							DBC db = new DBC(user, channel);
							db.addCorrect();
							correctResponse();
						} catch (Exception e) {e.printStackTrace();}
					}else{
						wrongResponse();
					}
				
					answered = true;
				}
			}
		}
	}
}
