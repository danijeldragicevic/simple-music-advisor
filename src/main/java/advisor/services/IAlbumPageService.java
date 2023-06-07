package advisor.services;

import advisor.models.OutputPage;
import com.google.gson.JsonObject;

import java.util.List;

public interface IAlbumPageService {
    OutputPage getAlbumsPage(String accessToken);
    OutputPage getAlbumsSubPage(String accessToken, String nextPage, int currentPage);
    List<Object> createAlbumItems(JsonObject apiResponseJson);
}
