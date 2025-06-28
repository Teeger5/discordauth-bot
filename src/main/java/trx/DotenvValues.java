package trx;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DotenvValues {
	public static int BOT_PORT = 25566;
	public static String PLUGIN_HOST = "localhost";
	public static int PLUGIN_PORT = 25567;
	public static int AUTH_TIMEOUT_SECONDS = 120;

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
