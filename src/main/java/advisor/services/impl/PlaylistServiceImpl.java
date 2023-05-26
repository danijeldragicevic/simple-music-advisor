package advisor.services.impl;

import advisor.models.Category;
import advisor.models.Playlist;
import advisor.services.IPlaylistService;

import java.util.List;

public class PlaylistServiceImpl implements IPlaylistService {
    private List<Playlist> playlists = List.of(
            new Playlist("Mellow Morning"),
            new Playlist("Wake Up and Smell the Coffee"),
            new Playlist("Monday Motivation"), 
            new Playlist("Songs to Sing in the Shower")
    );
    
    private List<Playlist> moodPlaylists = List.of(
            new Playlist("Walk Like A Badass", Category.MOOD),
            new Playlist("Rage Beats", Category.MOOD),
            new Playlist("Arab Mood Booster", Category.MOOD),
            new Playlist("Sunday Stroll", Category.MOOD)
    );
    
    @Override
    public List<Playlist> getAll() {
        return playlists;
    }

    @Override
    public List<Playlist> getAllByCategoryName(String categoryName) {
        return moodPlaylists;
    }
}
