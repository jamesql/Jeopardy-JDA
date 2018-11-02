package com.bot.api;

import java.io.IOException;

import argo.jdom.JdomParser;
import argo.saj.InvalidSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getQ {
	
	private int cat;
	public String ctgName;
	public String answer;
	public String question;
	public String difficulty;
	public String[] incAnswer = new String[3];
	private String api = "https://opentdb.com/api.php?amount=1&category=" + cat + "&type=multiple";
	private String res;
	
	private OkHttpClient client = new OkHttpClient();
	
	public getQ(int category) throws IOException, InvalidSyntaxException {
		cat = category;
		if (cat > 8) api = "https://opentdb.com/api.php?amount=1&category=" + cat + "&type=multiple";
		else api = "https://opentdb.com/api.php?amount=1&type=multiple";
		res = loadJSON();
		question = getQuestion();
		difficulty = getDif();
		answer = getAnswer();
		ctgName = getCat();
		initIncorrect();
		
	}
	
	public String getQuestion() throws InvalidSyntaxException {
		String q = new JdomParser().parse(res)
				.getNullableStringValue("results", 0, "question");
		return q;
	}
	
	public String getCat() throws InvalidSyntaxException {
		String q = new JdomParser().parse(res)
				.getNullableStringValue("results", 0, "category");
		return q;
	}
	
	public String getDif() throws InvalidSyntaxException {
		String q = new JdomParser().parse(res)
				.getNullableStringValue("results", 0, "difficulty");
		return q;
	}
	
	public String getAnswer() throws InvalidSyntaxException {
		String q = new JdomParser().parse(res)
				.getNullableStringValue("results", 0, "correct_answer");
		return q;
	}
	
	public void initIncorrect() throws InvalidSyntaxException {
		for (int x = 0; x < incAnswer.length; x++) {
			String ic = new JdomParser().parse(res)
					.getNullableStringValue("results", 0, "incorrect_answers", x);
			incAnswer[x] = ic;
		}
	}
	
	private String loadJSON() throws IOException
	{
		Request rq = new Request.Builder()
				.url(api)
				.build();
		Response response = client.newCall(rq).execute();
		return response.body().string();
	}
	
	
}
