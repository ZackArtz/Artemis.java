package commands.audio;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.LoggerFactory;
import secret.InfoUtil;

import java.util.List;
import java.util.logging.Logger;

public class YouTubeSearchHandler {


    private static final Logger log = (Logger) LoggerFactory.getLogger(YouTubeSearchHandler.class);


    public static List<SearchResult> searchResults(String str) {

        try {
            YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {}).setApplicationName(InfoUtil.G_APPLICATION_NAME).build();

            YouTube.Search.List search = youTube.search().list("id,snippet")
                                                .setKey(InfoUtil.G_APPLICATION_API_KEY)
                                                .setQ(str)
                                                .setType("video")
                                                .setFields("items(id/videoId,snippet/title)")
                                                .setMaxResults(10L);

            SearchListResponse searchListResponse = search.execute();
            return searchListResponse.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String search(String str) {
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
            }).setApplicationName(InfoUtil.G_APPLICATION_NAME).build();

            YouTube.Search.List search = youtube.search().list("id,snippet")
                    .setKey(InfoUtil.G_APPLICATION_API_KEY).setQ(str).setType("video")
                    .setFields("items(id/videoId)").setMaxResults(1L);

            SearchListResponse response = search.execute();
            List<SearchResult> searchResults = response.getItems();

            if (searchResults.isEmpty()) {

                return null;
            }

            SearchResult result = searchResults.get(0);
            return "https://www.youtube.com/watch?v=" + result.getId().getVideoId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
