package com.bot;

import javax.security.auth.login.LoginException;
import com.bot.commands.CommandListener;
import com.bot.guilds.GuildListener;
import argo.saj.InvalidSyntaxException;
import net.dv8tion.jda.core.entities.Game;
import java.io.IOException;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;

public class connection {
	
	final static Game game = Game.playing("Get Thinking! (Beta)");
	final static String token = "///";

    public static void main(String[] args) throws LoginException, IOException, InvalidSyntaxException {
        new DefaultShardManagerBuilder()
        		.addEventListeners(new CommandListener(), new GuildListener())
        		.setGame(game)
        		.setToken(token)
        		.build();
    }
}