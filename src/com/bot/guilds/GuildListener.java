package com.bot.guilds;

import java.io.IOException;

import com.bot.botlist.BFD;
import com.bot.botlist.DiscordBotsOrg;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		JDA bot = event.getJDA(); 
		String guildName = event.getGuild().getName();
		System.out.println("Guild Added : " + guildName);
		try {
			BFD bfd = new BFD(bot.getSelfUser().getId(), bot);
			DiscordBotsOrg dbg = new DiscordBotsOrg(bot.getSelfUser().getId(), bot);
		} catch (IOException e) {e.printStackTrace();}
	}
}
