import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final API api = new API();
    private static final Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Explore all songs");
            System.out.println("2. Search song by ID");
            System.out.println("3. List artists");
            System.out.println("4. View artist details");
            System.out.println("5. Add new artist");
            System.out.println("6. Edit artist");
            System.out.println("7. Delete artist");
            System.out.println("8. View local collection");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    exploreSongs();
                    break;
                case 2:
                    searchSongById();
                    break;
                case 3:
                    listArtists();
                    break;
                case 4:
                    viewArtistDetails();
                    break;
                case 5:
                    addNewArtist();
                    break;
                case 6:
                    editArtist();
                    break;
                case 7:
                    deleteArtist();
                    break;
                case 8:
                    showLocalCollection();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void exploreSongs() {
        List<Song> songs = api.getSongs();
        System.out.println("\n=== SONG CATALOG ===");
        for (Song s : songs) {
            System.out.println(s.id + ". " + s.title + " (" + s.publication_year + ")");
        }
    }

    private static void searchSongById() {
        System.out.print("Enter song ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Song song = api.getSong(id);
        if (song != null) {
            System.out.println("Title: " + song.title);
            System.out.println("Duration: " + song.length + " sec");
            System.out.println("Year: " + song.publication_year);
        } else {
            System.out.println("Song not found!");
        }
    }

    private static void listArtists() {
        List<Artist> artists = api.getArtists();
        System.out.println("\n=== ARTIST LIST ===");
        for (Artist a : artists) {
            System.out.println(a.id + ". " + a.name + " (" + a.genre + ")");
        }
    }

    private static void viewArtistDetails() {
        System.out.print("Enter artist ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Artist artist = api.getArtist(id);
        if (artist != null) {
            System.out.println("Name: " + artist.name);
            System.out.println("Country: " + artist.country);
            System.out.println("Genre: " + artist.genre);
            System.out.println("Songs:");
            for (Song s : api.getArtistSongs(id)) {
                System.out.println("  - " + s.title + " (" + s.publication_year + ")");
            }
            System.out.print("Do you want to save this artist in the local database? (y/n): ");
            String save = scanner.nextLine();
            if (save.equalsIgnoreCase("y")) {
                db.insert(artist);
                System.out.println("Artist saved!");
            }
        } else {
            System.out.println("Artist not found!");
        }
    }

    private static void addNewArtist() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Country: ");
        String country = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Artist newArtist = new Artist(name, country, genre);
        if (api.addArtist(newArtist)) {
            System.out.println("Artist added successfully!");
        } else {
            System.out.println("Error while adding artist.");
        }
    }

    private static void editArtist() {
        System.out.print("Artist ID to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("New name: ");
        String name = scanner.nextLine();
        System.out.print("New country: ");
        String country = scanner.nextLine();
        System.out.print("New genre: ");
        String genre = scanner.nextLine();

        Artist artist = new Artist(name, genre, country);
        if (api.editArtist(artist, id)) {
            System.out.println("Artist edited successfully!");
        } else {
            System.out.println("Error while editing artist.");
        }
    }

    private static void deleteArtist() {
        System.out.print("Artist ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (api.deleteArtist(id)) {
            System.out.println("Artist deleted!");
        } else {
            System.out.println("Error while deleting artist.");
        }
    }

    private static void showLocalCollection() {
        List<Artist> savedArtists = db.get();
        System.out.println("\n=== LOCAL COLLECTION ===");
        for (Artist a : savedArtists) {
            System.out.println(a.id + ". " + a.name + " (" + a.genre + ")");
        }
    }
}
