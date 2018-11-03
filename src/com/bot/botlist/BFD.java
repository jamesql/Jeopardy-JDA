package com.bot.botlist;

import net.dv8tion.jda.core.JDA;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BFD {

	private JDA bot;
	private String apiToken = "";
	private String url = "https://botsfordiscord.com/api/bot/";
	
	public BFD(String botId, JDA importBot) {
		url += botId;
		bot = importBot;
	}
	
	public void postServerCount() {
	    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	    RequestBody body = RequestBody.create(JSON, "{'server_count':" + bot.getGuilds().size() + "}");
	    
	    Request request = new Request.Builder()
	            .url(url)
	            .post(body)
	            .addHeader("Authorization", apiToken)
	            .addHeader("Content-Type", "application/json")
	            .build();
	}
}
