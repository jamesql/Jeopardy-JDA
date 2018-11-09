package com.bot.challenge;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bot.api.getQ;
import com.bot.database.DBC;

import argo.saj.InvalidSyntaxException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
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
	private int Rounds = 3;
	private int curRound = 1;
	private Guild mainGuild;
	private String readyEmoteId;
	boolean answered1 = false;
	boolean answered2 = false;
	int rightAns = 0;
	private String aVar = "508121630103437323";
	private String bVar = "508121676869795860";
	private String cVar = "508121720574705665";
	private String dVar = "508121771132846090";
	private String global;
	int plyscore1 = 0;
	int plyscore2 = 0;
	boolean sent = false;
	boolean right1 = false;
	boolean right2 = false;
	boolean finished = false;
	
	EmbedBuilder eb1 = new EmbedBuilder();
	EmbedBuilder eb2 = new EmbedBuilder();
	EmbedBuilder eb3 = new EmbedBuilder();
	EmbedBuilder eb4 = new EmbedBuilder();
	EmbedBuilder eb5 = new EmbedBuilder();
	
	public ChalListener(User p1, User p2, MessageChannel ch, JDA self, int cat) throws IOException, InvalidSyntaxException {
		player1 = p1;
		player2 = p2;
		channel = ch;
		bot = self;
		category = cat;
		mainGuild = bot.getGuildById("479518188711706627");
		sendReadyResponse();
	}
	
	public void sendQuestion() throws IOException, InvalidSyntaxException {
		question = new getQ(category);
		System.out.println(question.answer);
		sent = true;
		String[] answers = new String[4];
		answers[0] = question.answer;
		answers[1] = question.incAnswer[0];
		answers[2] = question.incAnswer[1];
		answers[3] = question.incAnswer[2];
		List<String> a = Arrays.asList(answers);
		Collections.shuffle(a);
		eb1.setAuthor("Jeopardy Challenge", null, null);
		eb1.setColor(Color.CYAN);
		eb1.setTitle(player1.getName() + " V " + player2.getName());
		eb1.addField("Category", question.ctgName, false);
		eb1.addField("Difficulty", question.getDif(), false);
		eb1.addField("Question", question.question, false);
		eb1.setFooter("Get Thinking!", null);
		eb1.addField("Choices", "A : " + a.get(0) + "\nB : " + a.get(1) + "\nC : " + a.get(2) + "\nD : " + a.get(3) + "\n", false);
		channel.sendMessage(eb1.build()).queue(m -> {
			List<Emote> emote = mainGuild.getEmotes();
			m.addReaction(emote.get(0)).queue();
			m.addReaction(emote.get(2)).queue();
			m.addReaction(emote.get(1)).queue();
			m.addReaction(emote.get(3)).queue();
			for(int x = 0; x < a.size(); x++) {
				if (a.get(x) == question.answer) {
					rightAns = x;
				}
			}
		});
	}
	
	public void sendReadyResponse() {
		eb2.setTitle("Challenge has been started");
		eb2.addField("How to accept", "React with the A button to accept\nReact with the B button to decline/cancel", false);
		eb2.setFooter("Get Thinking!", null);
		channel.sendMessage(eb2.build()).queue(m -> {
			List<Emote> emote = mainGuild.getEmotes();
			m.addReaction(emote.get(0)).queue();
			m.addReaction(emote.get(2)).queue();
		});
	}
	
	public void validate() throws Exception {
		finished = true;
		DBC player1db = new DBC(player1.getId());
		DBC player2db = new DBC(player2.getId());
		// tie
		if (right1 && right2) {
			eb3.setAuthor("Challenge Results", null, null);
			eb3.setColor(Color.CYAN);
			eb3.setTitle("Jeopardy Challenge");
			eb3.addField("It's a tie!", "Good job " + player1.getAsMention() + " & " + player2.getAsMention() + "!", false);
			player1db.addTie();
			player2db.addTie();
		}
		if (right1 && !right2) {
			eb3.setAuthor("Challenge Results", null, null);
			eb3.setColor(Color.CYAN);
			eb3.setTitle("Jeopardy Challenge");
			eb3.addField(player1.getName() + " Wins!", "Better luck next time " + player2.getAsMention() + "!", false);
			player1db.addWin();
			player2db.addLose();
		}
		
		if (!right1 && right2) {
			eb3.setAuthor("Challenge Results", null, null);
			eb3.setColor(Color.CYAN);
			eb3.setTitle("Jeopardy Challenge");
			eb3.addField(player2.getName() + " Wins!", "Better luck next time " + player1.getAsMention() + "!", false);
			player1db.addLose();
		}
		// Both Lose
		if (!right1 && !right2) {
			eb3.setAuthor("Challenge Results", null, null);
			eb3.setColor(Color.CYAN);
			eb3.setTitle("Jeopardy Challenge");
			eb3.addField("Everyone loses!", "Better luck next time " + player1.getAsMention() + " & " + player2.getAsMention() + "!", false);
			player1db.addLose();
			player2db.addLose();
		}
		eb3.setFooter("Get Thinking!", null);
		channel.sendMessage(eb3.build()).queue();
	}
	
	public void cancelp1() {
		eb4.setTitle("Challenge Cancelled");
		eb4.addField("Game Cancelled", "Cancelled by : " + player1.getName(), false);
		eb4.setFooter("Get Thinking!", null);
		channel.sendMessage(eb4.build()).queue();
	}
	
	public void cancelp2() {
		eb5.setTitle("Challenge Cancelled");
		eb5.addField("Game Cancelled", "Cancelled by : " + player1.getName(), false);
		eb5.setFooter("Get Thinking!", null);
		channel.sendMessage(eb5.build()).queue();
	}

	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event){
		User eu = event.getUser();
		boolean isBot = event.getUser().isBot();
		ReactionEmote reaction = event.getReactionEmote();
		List<Emote> emotes = mainGuild.getEmotes();
		aVar = emotes.get(0).getId();
		bVar = emotes.get(2).getId();
		cVar = emotes.get(1).getId();
		dVar = emotes.get(3).getId();
		if(!isBot) {
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
			
			
			if(eu.getId().equals(player1.getId())) {
				if (reaction.getId().equals(bVar) && !sent) {
					cancelp1();
					finished =  true;
				}
				
				if (ready1 && sent) {
					if (!answered1) {
						answered1 = true;
						if(reaction.getId().equals(global)){ 
							right1 = true;
						}
					}
				}
				if (!ready1) {
					if (reaction.getId().equals(aVar)) {
						ready1 = true;
					}
				}
			}
			
			if(eu.getId().equals(player2.getId())) {
				if (reaction.getId().equals(bVar) && !sent) {
					cancelp2();
					finished =  true;
				}
				if (ready2 && sent) {
					if (!answered2) {
						answered2 = true;
						if(reaction.getId().equals(global)){ 
							right2 = true;
						}
					}
				}
				if (!ready2) {
					if (reaction.getId().equals(aVar)) {
						ready2 = true;
					}
				}
			}
			
			
			if (ready1 && ready2 && !sent && !finished) {
				try {
					sendQuestion();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidSyntaxException e) {
					e.printStackTrace();
				}
			}
			
			if (answered1 && answered2 && !finished) {
				try {
					validate();
				}catch (Exception e){e.printStackTrace();}
			}
			
		}
	}
}
