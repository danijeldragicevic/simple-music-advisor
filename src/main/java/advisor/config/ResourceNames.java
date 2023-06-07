package advisor.config;

public enum ResourceNames {
    ALBUMS("albums"),
    CATEGORIES ("categories");
    
    private String name;

    ResourceNames(String resourceName) {
        this.name = resourceName;
    }

    public String getName() {
        return name;
    }
}
