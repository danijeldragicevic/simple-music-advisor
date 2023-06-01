package advisor.models;

public class Playlist {
    private String name;
    private String externalUrl;
    
    public Playlist() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    @Override
    public String toString() {
        return name + "\n" + 
               externalUrl + "\n";
    }
}
