import libs.ExclCmd;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Builder Configuration

        builder.addEventListeners(new ExclCmd());  // Add a new EventListener
        builder.setActivity(Activity.playing("deez"));  // Add playing status
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        JDA jda = builder.build();

        // Blocking the bot, make sure that JDA is completely loaded before working
        jda.awaitReady();
    }
}
