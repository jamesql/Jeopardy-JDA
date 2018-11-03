package com.bot.reactions;

import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

	boolean answered = false;
	String user;
	
	public ReactionListener(String userid) {
		user = userid;
		System.out.println("reg");
	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		String eMessageId = event.getMessageId();
		MessageReaction reaction = event.getReaction();
		ReactionEmote emote = reaction.getReactionEmote();
		User auth = event.getUser();
		boolean isBot = auth.isBot();
		
		if(!answered) {
			if(!isBot) {
				if(auth.getId().equals(user)) {
					System.out.println("WTF");
				}
			}
		}
	}
	
}
