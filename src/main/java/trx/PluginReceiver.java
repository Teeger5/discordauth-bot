package trx;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import trx.discordauth.socketcomm.bot.PluginNotifier;

import java.time.Instant;

/**
 * A plugintól érkező parancsok fogadása
 */
@Slf4j
public class PluginReceiver {
	private final JDA jda;

	public PluginReceiver(JDA jda) {
		this.jda = jda;
	}

	/**
	 * Üzenet küldése a felhasználónak:
	 * A plugin továbbengedte a sikeres hitelesítés hírére
	 * @param jda JDA példány a játékos lekérdezésére ID alapján
	 * @param uuid játékos UUID
	 */
	public static void sendAuthMessage(JDA jda, String uuid) {
		log.debug("Hitelesítési kérés érkezett, UUIDt: " + uuid);

		var discordId = Database.getUserDiscordID(uuid);
		if (discordId.isPresent()) {
			log.debug("Felhasználó megvan, UUID: %s Discord ID: %d", uuid, discordId.get());
			User user = jda.retrieveUserById(discordId.get()).complete();
			if (user != null) {
				log.info("Discord felhasználó létezik, ID: " + discordId.get());
				user.openPrivateChannel()
						.flatMap(channel -> channel.sendMessage("A Minecraft szerveren való belépéshez kattints a gombra!")
								.addActionRow(Button.success(
										String.format("verify:%s:%d", uuid, Instant.now().getEpochSecond()),
										"✅ Hitelesítem magam")))
						.queue(x -> PluginNotifier.notifyAuthInitiated(uuid));
			}
		} else {
			log.debug("Hitelesítés: nem ismert UUID (a mock adatbázisban): " + uuid);
			PluginNotifier.notifyAuthUserNotFound(uuid);
		}
	}

	/**
	 * Üzenet küldése a felhasználónak:
	 * A plugin továbbengedte a sikeres hitelesítés hírére
	 * @param jda JDA példány a játékos lekérdezésére ID alapján
	 * @param uuid játékos UUID
	 */
	public static void sendLoginConfirmedMessage(JDA jda, String uuid) {
		var discordId = Database.getUserDiscordID(uuid);
		User user = jda.retrieveUserById(discordId.get()).complete();
		user.openPrivateChannel()
				.flatMap(channel -> channel.sendMessage("✅ Hitelesítés sikeres, jó játékot!"))
				.queue();
	}
}
