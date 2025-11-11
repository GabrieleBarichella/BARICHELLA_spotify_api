public class Artist {
    public int id;
    public String name;
    public String genre;
    public String country;
    public Song[] songs;

    public Artist(String name, String genre, String country) {
        this.name = name;
        this.genre = genre;
        this.country = country;
    }
}
