package trx;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import trx.discordauth.socketcomm.shared.Receiver;
import trx.discordauth.socketcomm.shared.SocketCommand;

@Slf4j
public class Main {
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		String token = dotenv.get("BOT_DISCORD_TOKEN");

		DotenvValues.init();

		try {
			var jda = JDABuilder.createDefault(token)
					.addEventListeners(new EventListener())
					.build();
			log.info("Bot elindult");

			var receiver = initReceiver(jda);

			new Thread(() -> receiver.listen(DotenvValues.BOT_PORT)).start();
		} catch (Exception e) {
			log.error("Hiba a bot indításakor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static Receiver initReceiver(JDA jda) {
		var receiver = new Receiver();

		receiver.registerSocketCommandTask(SocketCommand.AUTH_REQUEST, uuid -> {
			PluginReceiver.sendAuthMessage(jda, uuid);
		});

		receiver.registerSocketCommandTask(SocketCommand.AUTH_CONFIRM_SUCCESS, uuid -> {
			PluginReceiver.sendLoginConfirmedMessage(jda, uuid);
		});

		return receiver;
	}
}