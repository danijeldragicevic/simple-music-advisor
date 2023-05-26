package advisor.models;

import java.util.List;

public class Album {
    private String name;
    private List<String> artists;
    
    public Album(String name, List<String> artists) {
        this.name = name;
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
}
