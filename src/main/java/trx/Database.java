package trx;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Mockolt adatbázis
 */
public class Database {
	private static Map<String, Long> USERS;

	static {
		var dotenv = Dotenv.load();
		USERS = new HashMap<>();
		var userId = Long.parseLong(dotenv.get("DISCORD_USER_ID"));
		var playerUuid = dotenv.get("PLAYER_UUID");
		USERS.put(playerUuid,  userId);
	}

	public static Optional<Long> getUserDiscordID (String username) {
		var user = USERS.get(username);
		return  Optional.ofNullable(user);
	}
}
