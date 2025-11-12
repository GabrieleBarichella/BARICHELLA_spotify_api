import com.google.gson.annotations.SerializedName;

public class Song {
    public int id;
    @SerializedName("titolo")
    public String title;
    @SerializedName("durata")
    public int length;
    @SerializedName("annoPubblicazione")
    public int publication_year;

    public Song(String title, int id, int publication_year, int length) {
        this.title = title;
        this.id = id;
        this.publication_year = publication_year;
        this.length = length;
    }
}
