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
	public void categorys()  {
		eb.setAuthor("Jeopardy Categorys", null, null);
		eb.setColor(Color.CYAN);
		eb.addField("General", "General\nMythology\nSports\nGeography\nHistory\nPolitics\nArt\nCelebrities\nAnimals\nVehicles", false);
		eb.addField("Entertainment", "Books\nFilm\nMovies\nMusic\nMusicals\nTV\nVideo Games\nBoard Games\nCartoons\nComics\nAnime", false);
		eb.addField("Science", "Nature\nMath\nComputers\nGadgets", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
		
	}
	
	public void question(String category) throws Exception {
		eb.setAuthor("Question", null, null);
		eb.setColor(Color.CYAN);
		int catg = 0;
		System.out.println(category);
		switch(category.toLowerCase()) {
		case "" : catg = 0;
		break;
		case " general" : catg = 9;
		break;		
		case " books" : catg = 10;
		break;
		case " film" : catg = 11;
		break;
		case " movies" : catg = 11;
		break;
		case " music" : catg = 12;
		break;
		case " musicals" : catg = 13;
		break;
		case " musical" : catg = 13;
		break;
		case " tv" : catg = 14;
		break;
		case " television" : catg = 14;
		break;
		case " video games" : catg = 15;
		break;
		case " video game" : catg = 15;
		break;
		case " videogames" : catg = 15;
		break;
		case " board games" : catg = 16;
		break;
		case " board game" : catg = 16;
		break;
		case " nature" : catg = 17;
		break;
		case " computers" : catg = 18;
		break;
		case " computer" : catg = 18;
		break;
		case " math" : catg = 19;
		break;
		case " mythology" : catg = 20;
		break;
		case " sports" : catg = 21;
		break;
		case " sport" : catg = 21;
		break;
		case " geography" : catg = 22;
		break;
		case " history" : catg = 23;
		break;
		case " politics" : catg = 24;
		break;
		case " art" : catg = 25;
		break;
		case " celebrities" : catg = 26;
		break;
		case " animals" : catg = 27;
		break;
		case " vehicles" : catg = 28;
		break;
		case " cars" : catg = 28;
		break;
		case " comics" : catg = 29;
		break;
		case " gadgets" : catg = 30;
		break;
		case " anime" : catg = 31;
		break;
		case " manga" : catg = 31;
		break;
		case " cartoons" : catg = 32;
		break;
		case " animations" : catg = 32;
		break;
		
		
		}
		getQ question = new getQ(catg);
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
			bot.addEventListener(new ReactionListener(message.getAuthor().getId(), rightAns, bot, channel));
		});
	}
}
