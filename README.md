# Discord hitelesítési bot Spigot szerverekhez
Ez a bot egy pluginhoz tartozik, ami arra kéri a csatlakozott játékost,
hogy hitelesítse magát Discordon. A bot DM üzenetet küld neki, amin egy gomb található.
A gombra kattintva a bot visszajelez a plugin-nek, hogy az továbbengedje a játékost.

Ha nincs az ismert felhasználók közt a játékos, 
akkor regisztrációra vonatkozó üzentet ír a plugin

A kapcsolat a bot és a plugin között Socket-tel van megoldva, ami gyors és egyszerű.

Az adatbázis mockolt, a felhasználók listája előre ismert.

