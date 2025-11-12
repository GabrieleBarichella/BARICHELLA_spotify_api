import com.google.gson.annotations.SerializedName;

public class Artist {
    public int id;
    @SerializedName("nome")
    public String name;
    @SerializedName("genere")
    public String genre;
    @SerializedName("paese")
    public String country;

    public Artist(String name, String genre, String country) {
        this.name = name;
        this.genre = genre;
        this.country = country;
    }
}
