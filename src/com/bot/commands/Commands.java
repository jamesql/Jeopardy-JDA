package com.bot.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.bot.api.getQ;
import com.bot.database.DBC;
import com.bot.reactions.ReactionListener;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class Commands {

	public MessageReceivedEvent GMsg;
	public Message message;
	public String content;
	public User user;
	public MessageChannel channel;
	public Guild guild;
	public Guild mainGuild;
	public JDA bot;
	
	EmbedBuilder eb = new EmbedBuilder();
	
	public Commands(MessageReceivedEvent msg) {
		GMsg = msg;
		message = msg.getMessage();
		content = message.getContentRaw();
		user = msg.getAuthor();
		channel = msg.getChannel();
		guild = msg.getGuild();
		bot = msg.getJDA();
		mainGuild = bot.getGuildById("479518188711706627");
	}
	
	public void pingCommand() {
		eb.setTitle("Jeopardy!");
		eb.addField("Pong!", "Response Time : " + GMsg.getJDA().getPing(), false);
		eb.setFooter("Get Thinking!", null);
		eb.setColor(Color.CYAN);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void helpCommand() {
		eb.setAuthor("Jeopardy", null, null);
		eb.setTitle("Use j!q <Category> to generate a question!", null);
		eb.setColor(Color.CYAN);
		
		eb.addField("j!categorys", "List of categorys", false);
		eb.addField("j!stats", "Get your stats", false);
		eb.addField("j!ping", "Test the bots connection", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	
	}
	
	public void statsCommand() throws Exception {
		eb.setAuthor("Jeopardy Stats", null, null);
		eb.setColor(Color.CYAN);
		DBC db = new DBC(user.getId());
		eb.addField("Level", db.level + "", false);
		eb.addField("# Correct", db.correct + "", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
		
	}
	
	public void question(String category) throws Exception {
		eb.setAuthor("Question", null, null);
		eb.setColor(Color.CYAN);
		getQ question = new getQ(0);
		eb.addField("Category", question.ctgName, false);
		eb.addField("Difficulty", question.difficulty, false);
		eb.addField("Question", question.question, false);
		String[] answers = new String[4];
		answers[0] = question.answer;
		answers[1] = question.incAnswer[0];
		answers[2] = question.incAnswer[1];
		answers[3] = question.incAnswer[2];
		List<String> a = Arrays.asList(answers);
		Collections.shuffle(a);
		
		eb.addField("Choices", "A : " + a.get(0) + "\nB : " + a.get(1) + "\nC : " + a.get(2) + "\nD : " + a.get(3) + "\n", false);
		eb.setFooter("Get Thinking!", null); 

		System.out.println(question.answer);
		List<Emote> emote = mainGuild.getEmotes();
		

		channel.sendMessage(eb.build()).queue(m ->{
			m.addReaction(emote.get(0)).queue();
			m.addReaction(emote.get(2)).queue();
			m.addReaction(emote.get(1)).queue();
			m.addReaction(emote.get(3)).queue();
			int rightAns = 0;
			for(int x = 0; x < a.size(); x++) {
				if (a.get(x) == question.answer) {
					rightAns = x;
				}
			}
			bot.addEventListener(new ReactionListener(message.getAuthor().getId(), rightAns, bot));
		});
	}
}
