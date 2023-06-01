package advisor.repositories.impl;

import advisor.models.Playlist;
import advisor.repositories.IPlaylistRepository;

import java.util.List;

public class PlaylistRepositoryImpl implements IPlaylistRepository {
    private List<Playlist> playlists = List.of(
            new Playlist("Mellow Morning"),
            new Playlist("Wake Up and Smell the Coffee"),
            new Playlist("Monday Motivation"), 
            new Playlist("Songs to Sing in the Shower")
    );
    
    private List<Playlist> moodPlaylists = List.of(
            new Playlist("Walk Like A Badass"),
            new Playlist("Rage Beats"),
            new Playlist("Arab Mood Booster"),
            new Playlist("Sunday Stroll")
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
