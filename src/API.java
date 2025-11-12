import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class API {

    private final HttpClient client = HttpClient.newHttpClient();
    String base_url = "http://localhost:4567/api/";

    private HttpRequest getHttpRequest(String url, String method, HttpRequest.BodyPublisher body) {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(java.net.URI.create(url))
                .method(method, body)
                .build();
    }

    private HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private List<Song> SongsList(String url){
        HttpRequest request = getHttpRequest(url, "GET", HttpRequest.BodyPublishers.noBody());
        HttpResponse<String> response = getHttpResponse(request);

        Gson gson = new Gson();
        Type songListType = new TypeToken<List<Song>>(){}.getType();
        List<Song> apiResponse = response != null ? gson.fromJson(response.body(), songListType) : null;

        if(apiResponse == null){
            System.out.println("Error: Api response is null");
            return null;
        }

        return apiResponse;
    }

    public List<Artist> getArtists(){

        String url = base_url + "artisti";
        HttpRequest request = getHttpRequest(url, "GET",  HttpRequest.BodyPublishers.noBody());
        HttpResponse<String> response = getHttpResponse(request);

        Gson gson = new Gson();
        Type artistListType = new TypeToken<List<Artist>>(){}.getType();
        List<Artist> apiResponse = response != null ? gson.fromJson(response.body(), artistListType) : null;

        if(apiResponse == null){
            System.out.println("Error: Api response is null");
            return null;
        }

        return apiResponse;
    }

    public Artist getArtist(int id){

        String url = base_url + "artisti/" + id;
        HttpRequest request = getHttpRequest(url,  "GET",  HttpRequest.BodyPublishers.noBody());
        HttpResponse<String> response = getHttpResponse(request);

        Gson gson = new Gson();
        Artist apiResponse = response != null ? gson.fromJson(response.body(), Artist.class) : null;

        if(apiResponse == null){
            System.out.println("Error: Api response is null");
            return null;
        }

        return apiResponse;
    }

    public List<Song> getArtistSongs(int id){

        String url = base_url + "artisti/" + id + "/canzoni";
        return SongsList(url);
    }

    public List<Song> getSongs(){

        String url = base_url + "canzoni";
        return SongsList(url);
    }

    public Song getSong(int id){

        String url = base_url + "canzoni/" + id;
        HttpRequest request = getHttpRequest(url, "GET",  HttpRequest.BodyPublishers.noBody());
        HttpResponse<String> response =  getHttpResponse(request);

        Gson gson = new Gson();
        Song apiResponse = response != null ? gson.fromJson(response.body(), Song.class) : null;

        if(apiResponse == null){
            System.out.println("Error: Api response is null");
            return null;
        }

        return apiResponse;
    }

    public boolean addArtist(Artist artist) {

        String url = base_url + "artisti";
        String artistToJson = new Gson().toJson(artist);
        HttpRequest request = getHttpRequest(url, "POST",  HttpRequest.BodyPublishers.ofString(artistToJson));
        return sendHttpRequest(request);
    }

    public boolean editArtist(Artist artist, int id) {

        String url = base_url + "artisti/" + id;
        String artistToJson = new Gson().toJson(artist);
        HttpRequest request = getHttpRequest(url, "PUT",  HttpRequest.BodyPublishers.ofString(artistToJson));
        return sendHttpRequest(request);
    }

    public boolean deleteArtist(int id) {

        String url = base_url + "artisti/" + id;
        HttpRequest request = getHttpRequest(url, "DELETE",  HttpRequest.BodyPublishers.noBody());
        return sendHttpRequest(request);
    }

    public boolean sendHttpRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response != null && response.statusCode() == 200;
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
