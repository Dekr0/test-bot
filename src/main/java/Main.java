import libs.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

/**
 * main argument -> bot token
 */
public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        // Builder for construct a JDA instance (Discord bot)
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Builder Configuration
        builder.addEventListeners(new CommandListener());  // Add a new ListenAdapter
        builder.setActivity(Activity.playing("Deez Nuts"));  // Playing status
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        JDA jda = builder.build();

        // Blocking the bot, make sure that JDA is completely loaded before working
        jda.awaitReady();
    }
}
