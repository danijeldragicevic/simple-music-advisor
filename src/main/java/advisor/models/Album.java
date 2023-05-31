package advisor.models;

import com.google.gson.JsonElement;

import java.util.List;

public class Album {
    private String name;
    private String externalUrl;
    private List<String> artists;
    
    public Album() {}
    
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
    
    public String getExternalUrl() { return externalUrl; }
    
    public void setExternalUrl(String externalUrl) { this.externalUrl = externalUrl; }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return name + "\n" + 
               artists + "\n" + 
               externalUrl + "\n";
    }
}
