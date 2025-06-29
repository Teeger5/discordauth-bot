package trx.discordauthbot;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DotenvValues {
	public static int BOT_PORT = 25566;
	public static String PLUGIN_HOST = "localhost";
	public static int PLUGIN_PORT = 25567;
	public static int AUTH_TIMEOUT_SECONDS = 120;

	/**
	 * Dotenv változók betöltése, és az értékek statikus változókhoz rendelése
	 * Emellett beállítja a PLUGIN_HOST és PLUGIN_PORT helyi környezeti változókat is,
	 * ami a szökséges üzenetek küldéséhez
	 */

	public static void init () {
		var dotenv = Dotenv.load();
		BOT_PORT = Integer.parseInt(dotenv.get("BOT_PORT"));
		log.info("Bot listening port: " + BOT_PORT);
		PLUGIN_HOST = dotenv.get("PLUGIN_HOST");
		PLUGIN_PORT = Integer.parseInt(dotenv.get("PLUGIN_PORT"));
		log.info("Plugin elérhetősége: {}:{}", PLUGIN_HOST,  PLUGIN_PORT);

		AUTH_TIMEOUT_SECONDS  = Integer.parseInt(dotenv.get("AUTH_TIMEOUT_SECONDS"));

		System.setProperty("PLUGIN_HOST", PLUGIN_HOST);
		System.setProperty("PLUGIN_PORT", Integer.toString(PLUGIN_PORT));
	}
}
