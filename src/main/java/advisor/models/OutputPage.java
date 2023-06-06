package advisor.models;

import java.util.List;

public class OutputPage {
    int totalPagesToDisplay;
    int currentPage;
    String nextPageUrl;
    String previousPageUrl;
    List<Object> items;

    public OutputPage() {}

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getTotalPagesToDisplay() {
        return totalPagesToDisplay;
    }

    public void setTotalPagesToDisplay(int totalPagesToDisplay) {
        this.totalPagesToDisplay = totalPagesToDisplay;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
    
}
