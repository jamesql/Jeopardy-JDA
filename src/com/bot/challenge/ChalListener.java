package com.bot.challenge;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bot.api.getQ;

import argo.saj.InvalidSyntaxException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ChalListener extends ListenerAdapter {

	User player1;
	User player2;
	JDA bot;
	MessageChannel channel;
	getQ question;
	int category;
	boolean ready1 = false;
	boolean ready2 = false;
	String correctAnswer = "";
	private int Rounds = 1;
	
	
	private EmbedBuilder eb = new EmbedBuilder();
	
	public ChalListener(User p1, User p2, MessageChannel ch, JDA self, int cat) throws IOException, InvalidSyntaxException {
		player1 = p1;
		player2 = p2;
		channel = ch;
		bot = self;
		category = cat;
	}
	
	public void sendQuestion() throws IOException, InvalidSyntaxException {
		question = new getQ(category);
		
		String[] answers = new String[4];
		answers[0] = question.answer;
		answers[1] = question.incAnswer[0];
		answers[2] = question.incAnswer[1];
		answers[3] = question.incAnswer[2];
		List<String> a = Arrays.asList(answers);
		Collections.shuffle(a);
		eb.addField("Choices", "A : " + a.get(0) + "\nB : " + a.get(1) + "\nC : " + a.get(2) + "\nD : " + a.get(3) + "\n", false);
	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event){
		User eu = event.getUser();
		boolean isBot = event.getUser().isBot();
		
		if(!isBot) {
			if(eu == player1) {
				if(ready1) {
					// Check their answer and check if its right
				}else{
					// Check if reaction is ready emoji
				}
			}
			
			if(eu == player2) {
				
			}
		}
		
	}
	
}
