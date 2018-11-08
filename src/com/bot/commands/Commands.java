package com.bot.commands;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.bot.api.getQ;
import com.bot.challenge.ChalListener;
import com.bot.challenge.Challenge;
import com.bot.database.DBC;
import com.bot.reactions.ReactionListener;

import argo.saj.InvalidSyntaxException;
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
	
	public String[] lbIds = new String[10];
	
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
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void helpCommand() {
		eb.setAuthor("Jeopardy", null, null);
		eb.setTitle("Use j!q <Category> to generate a question!", null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		
		eb.addField("j!categories", "List of categories", false);
		eb.addField("j!stats", "Get your stats", false);
		eb.addField("j!challenge", "Challenge your friends", false);
		eb.addField("j!leaderboard", "Global leaderboard", false);
		eb.addField("j!challengelb", "Global challenge leaderboard", false);
		eb.addField("j!ping", "Test the bots connection", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	
	}
	
	public void statsCommand() throws Exception {
		eb.setAuthor("Jeopardy Stats", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		DBC db = new DBC(user.getId());
		eb.addField("Level", db.level + "", false);
		eb.addField("# Correct", db.correct + "", false);
		eb.addField("Challenge Stats", db.wins + "W - "  + db.lose + "L - " + db.ties + "T", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
		
	}
	
	public void statsDuo(String userId) throws Exception {
		eb.setAuthor("Jeopardy Stats", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		DBC db = new DBC(userId);
		eb.addField("Level", db.level + "", false);
		eb.addField("# Correct", db.correct + "", false);
		eb.addField("Challenge Stats", db.wins + "W - "  + db.lose + "L - " + db.ties + "T", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void categorys()  {
		eb.setAuthor("Jeopardy Categories", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		eb.addField("General", "General\nMythology\nSports\nGeography\nHistory\nPolitics\nArt\nCelebrities\nAnimals\nVehicles", false);
		eb.addField("Entertainment", "Books\nFilm\nMovies\nMusic\nMusicals\nTV\nVideo Games\nBoard Games\nCartoons\nComics\nAnime", false);
		eb.addField("Science", "Nature\nMath\nComputers\nGadgets", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
		
	}
	
	
	
	public void lbCmd() throws Exception {
		eb.setAuthor("Jeopardy Leaderboard", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		DBC db = new DBC(user.getId());
		db.getLb();
		lbIds[0] = db.arr[0][0];
		lbIds[1] = db.arr[1][0];
		lbIds[2] = db.arr[2][0];
		lbIds[3] = db.arr[3][0];
		lbIds[4] = db.arr[4][0];
		User lb1 = bot.getUserById(lbIds[0]);
		User lb2 = bot.getUserById(lbIds[1]);
		User lb3 = bot.getUserById(lbIds[2]);
		User lb4 = bot.getUserById(lbIds[3]);
		User lb5 = bot.getUserById(lbIds[4]);
		eb.addField("Correct Answers Leaderboard", "1.) " + lb1.getName() + "#" + lb1.getDiscriminator() + " - " + db.arr[0][1] + "\n" + "2.) " + lb2.getName() + "#" + lb2.getDiscriminator() + " - " + db.arr[1][1] + "\n" + "3.) " + lb3.getName() + "#" + lb3.getDiscriminator() + " - " + db.arr[2][1] + "\n" + "4.) " + lb4.getName() + "#" + lb4.getDiscriminator() + " - " + db.arr[3][1] + "\n" + "5.) " + lb5.getName() + "#" + lb5.getDiscriminator() + " - " + db.arr[4][1] + "\n", false);
		eb.addField("Your Ranking", user.getAsMention() + " - " + db.correct, false);
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void challengeStart(User su, String category) throws IOException, InvalidSyntaxException {
		int cat = 0;
		if (category.contains("sports")) {
			cat = 21;
		}
		bot.addEventListener(new ChalListener(user, su, channel, bot, cat));
		
	}
	
	public void chalerror1(){
		eb.setAuthor("Jeopardy Challenge Error", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
		eb.addField("Error", "You need to mention another user to do this!", false);
		
		eb.setFooter("Get Thinking!", null);
		
		channel.sendMessage(eb.build()).queue();
	}
	
	public void question(String category) throws Exception {
		eb.setAuthor("Question", null, null);
		eb.setColor(guild.getMember(bot.getSelfUser()).getColor());
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
			bot.addEventListener(new ReactionListener(message.getAuthor().getId(), rightAns, bot, channel, question.answer));
		});
	}
}
