package advisor.services;

import advisor.models.OutputPage;
import com.google.gson.JsonObject;

public interface IPageService {
    OutputPage createOutputPage(String sResponse, String resourceName, int currentPage);
    int calcTotalNumOfPages(JsonObject jResponse, int apiPageLimit);
    String createNextPageUrl(JsonObject jResponse);
    String createPreviousPageUrl(JsonObject jResponse);
}
