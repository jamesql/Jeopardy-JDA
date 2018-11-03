package com.bot.commands;

import java.awt.Color;

import com.bot.database.DBC;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Commands {

	public MessageReceivedEvent GMsg;
	public Message message;
	public String content;
	public User user;
	public MessageChannel channel;
	public Guild guild;
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
	}
	
	public void pingCommand() {
		eb.setTitle("Jepordy!");
		eb.addField("Pong!", "Response Time : " + GMsg.getJDA().getPing(), false);
		eb.setFooter("Get Thinking!", null);
		eb.setColor(Color.CYAN);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void helpCommand() {
		eb.setAuthor("Jepordy", null, null);
		eb.setTitle("Use j!q <Category> to generate a question!", null);
		eb.setColor(Color.CYAN);
		
		eb.addField("j!categorys", "List of categorys", false);
		eb.addField("j!stats", "Get your stats", false);
		eb.addField("j!ping", "Test the bots connection", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void statsCommand() throws Exception {
		eb.setAuthor("Jepordy Stats", null, null);
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
		
		channel.sendMessage(eb.build()).queue(m ->{
			m.addReaction("\uD83C\uDDE6").queue();
			m.addReaction("\uD83C\uDDE7").queue();
			m.addReaction("\uD83C\uDDE8").queue();
			m.addReaction("\uD83C\uDDE9").queue();
		});
	}
	
	
}
