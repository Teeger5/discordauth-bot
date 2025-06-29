package trx.discordauthbot;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Mockolt adatbázis
 */
public class Database {
	private static final Map<String, Long> USERS;

	//  Beállított Discord ID és UUID beolvasása az .env fájlból
	static {
		var dotenv = Dotenv.load();
		USERS = new HashMap<>();
		var userId = Long.parseLong(dotenv.get("DISCORD_USER_ID"));
		var playerUuid = dotenv.get("PLAYER_UUID");
		USERS.put(playerUuid,  userId);
	}

	/**
	 * UUID-hez tartozó felhasználó Discord ID lekérdezése
	 * @param uuid játékos UUID
	 * @return lekérdezés eredmény:  Optional: felhasználó ID vagy üres
	 */
	public static Optional<Long> getUserDiscordID (String uuid) {
		var user = USERS.get(uuid);
		return  Optional.ofNullable(user);
	}
}
