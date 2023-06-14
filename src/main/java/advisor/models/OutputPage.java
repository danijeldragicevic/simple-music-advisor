package advisor.models;

import java.util.List;

public class OutputPage {
    static int currentPage;
    int totalPagesToDisplay;
    String nextPageUrl;
    String previousPageUrl;
    String resourcePageName;
    List<Object> pageItems;

    public OutputPage() {}

    public String getResourcePageName() {
        return resourcePageName;
    }

    public void setResourcePageName(String resourcePageName) {
        this.resourcePageName = resourcePageName;
    }
    
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

    public List<Object> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<Object> pageItems) {
        this.pageItems = pageItems;
    }
    
}
