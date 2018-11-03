package com.bot.botlist;

import java.io.IOException;

import net.dv8tion.jda.core.JDA;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BFD {

	private JDA bot;
	private String apiToken = "///";
	private String url = "https://botsfordiscord.com/api/bot/";
	private OkHttpClient client = new OkHttpClient();
	
	  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	
	public BFD(String botId, JDA importBot) throws IOException {
		url += botId;
		bot = importBot;
		System.out.println(doPostRequest(url, "{\"server_count\": " + bot.getGuilds().size() + "}"));
	}
	
    String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
          Request request = new Request.Builder()
              .url(url)
              .addHeader("Content-Type", "application/json")
              .addHeader("Authorization", apiToken)
              .post(body)
              .build();
          Response response = client.newCall(request).execute();
          return response.body().string();
    }
}
