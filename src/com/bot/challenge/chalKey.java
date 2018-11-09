package com.bot.challenge;

public class chalKey {

	public static int counter = 0;
	public static String[] idStorage = new String[999];
	private boolean init = false;
	
	public chalKey() {
		counter++;
		if (!init)
			for (int x = 0; x < idStorage.length; x++)
				idStorage[x] = "";
		init = true;
	}
}
