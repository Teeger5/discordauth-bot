package trx.discordauthbot;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import trx.discordauth.socketcomm.bot.PluginNotifier;

import java.time.Instant;
import java.util.List;

/**
 * Eseménykezelők vannak itt
 */
@Slf4j
public class EventListener extends ListenerAdapter {

	/**
	 * A hitelesítési gombra kattintás eseménye
	 * - Ha a beállított határidőn túli, akkor azt jelezni kell, és gomb színe piros + letiltva
	 * - Ha rendben van, akkor plugin értesítése, gomb színe szürke + letiltva
	 * @param event az esemény adatai
	 */
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		var id = event.getComponentId();

		log.debug("Hitelesítési gombnyomás, gomb ID: " + event.getId());

		if (id.startsWith("verify:")) {
			var parts = id.split(":");
			var uuid = parts[1];
			var sent = Long.parseLong(parts[2]);
			var now = Instant.now().getEpochSecond();

			event.deferEdit().queue();

			if (now - sent > DotenvValues.AUTH_TIMEOUT_SECONDS) {
				log.debug("Hitelesítési gombnyomás időtúllépése, eltelt idő a küldés óta: {} mp", now - sent);

				event.getMessage().editMessageComponents(
						List.of(ActionRow.of(
								event.getButton().withStyle(ButtonStyle.DANGER).asDisabled()
						))
				).queue();

				event.getMessage().reply("A hitelesítés időtúllépés miatt nem sikerült. A gomb csak 1 percen át érvényes.")
						.queue();

				return;
			}

			event.getMessage().editMessageComponents(
					List.of(ActionRow.of(event.getButton().asDisabled()))
			).queue();

			event.getMessage().reply("Hitelesítés sikeres!").queue();

			PluginNotifier.notifyAuthSuccess(uuid);
		}
	}
}
