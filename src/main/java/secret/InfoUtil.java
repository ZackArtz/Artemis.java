package secret;

public class InfoUtil {

    /**
     * This is essentially a config file, includes things like the bot versions, and tokens for Discord and the Google Api.
     */


    public static final String
            PREFIX = "$",
            TOKEN = System.getenv("token"),
            CODE_NAME = "Artemis",
            CODE_VERSION = "1.0",
            G_API_KEY = System.getenv("gapi");

    public static final String botOwner = "133314498214756352";
}
