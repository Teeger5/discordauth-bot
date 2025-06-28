package trx;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import trx.discordauth.socketcomm.shared.SocketCommand;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Üzenetek küldése a Spigot pluginnak
 */
@Slf4j
public class PluginNotifierBot {

	/**
	 * Plugin értesítése sikeres hitelesítésről
	 * @param uuid hitelesített jateékos UUID
	 */
	public static void notifyAuthSuccess(String uuid) {
		notifyPlugin(SocketCommand.AUTH_SUCCESS, uuid);
	}

	/**
	 * Plugin értesítése nem található felhasználóról
	 * @param uuid játékos UUID
	 */
	public static void notifyAuthUserNotFound(String uuid) {
		notifyPlugin(SocketCommand.USER_NOT_FOUND, uuid);
	}

	public static void notifyAuthUserFound(String uuid) {
		notifyPlugin(SocketCommand.USER_FOUND, uuid);
	}

	public static void notifyAuthInitiated(String uuid) {
		notifyPlugin(SocketCommand.AUTH_INITIATED, uuid);
	}

	/**
	 * Socketen keresztül elküldi a parancsot és az UUID-et a pluginnak,
	 * @param command parancs a pluginnak
	 * @param uuid felhasználó UUID
	 */
	private static void notifyPlugin(SocketCommand command, String uuid) {
		var dotenv = Dotenv.load();
		try (Socket socket = new Socket(DotenvValues.PLUGIN_HOST,  DotenvValues.PLUGIN_PORT);
		     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

			var message = String.format("%s,%d", command, uuid);
			out.println(message);
			log.debug("Hitelesítés sikeres üzenet elküldve a plugin-nak: " + message);
		} catch (Exception e) {
			log.error("Hiba a plugin értesítésekor: command: {}, UUID: {}", command, uuid);
			e.printStackTrace();
		}
	}
}
