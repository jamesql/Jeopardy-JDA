package com.bot.guilds;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {

	private String apitoken = "";
	private String botId = "";
	
	
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		JDA bot = event.getJDA(); 
		int serverCount  = bot.getGuilds().size();
		String guildName = event.getGuild().getName();
		System.out.println("Guild Added : " + guildName);
	}
}
