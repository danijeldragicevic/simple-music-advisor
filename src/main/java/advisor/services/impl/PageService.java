package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.OutputPage;
import advisor.services.IPageService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class PageService implements IPageService {
    protected String resourceName;
    
    protected PageService(String resourceName) {
        this.resourceName = resourceName;
    }
    
    @Override
    public OutputPage createOutputPage(String sResponse, String resourceName, int currentPage) {
        JsonObject jResponse = JsonParser.parseString(sResponse).getAsJsonObject().getAsJsonObject(resourceName);

        OutputPage outputPage = new OutputPage();
        outputPage.setCurrentPage(currentPage);
        outputPage.setTotalPagesToDisplay(calcTotalNumOfPages(jResponse, ExternalApiConfig.API_PAGE_LIMIT));
        outputPage.setNextPageUrl(createNextPageUrl(jResponse));
        outputPage.setPreviousPageUrl(createPreviousPageUrl(jResponse));
        outputPage.setResourcePageName(resourceName);

        return outputPage;
    }
    
    @Override
    public int calcTotalNumOfPages(JsonObject jResponse, int apiPageLimit) {
        int totalRecords = Integer.parseInt(jResponse.getAsJsonObject().get("total").toString().replaceAll("\"", ""));
        int result = (int) Math.ceil((double) totalRecords / apiPageLimit);

        return result;
    }

    @Override
    public String createNextPageUrl(JsonObject jResponse) {
        return jResponse.getAsJsonObject().get("next").toString().replaceAll("\"", "");
    }
    
    @Override
    public String createPreviousPageUrl(JsonObject jResponse) {
        return jResponse.getAsJsonObject().get("previous").toString().replaceAll("\"", "");
    }
}
