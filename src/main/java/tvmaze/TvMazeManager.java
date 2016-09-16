package tvmaze;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import retrofit2.Response;
import tvmaze.pojo.TvMazeEpisode;
import tvmaze.pojo.TvMazeSearch;

public class TvMazeManager {
	public static Optional<List<TvMazeSearch>> getTvShowFromName(TvMaze tvMaze, String tvShow) {
		try {
			Response<List<TvMazeSearch>> response = tvMaze.getShows(tvShow).execute();
			if (!response.isSuccessful()) {
				System.out.println(response.errorBody());
				return Optional.empty();
			}
			return Optional.of(response.body());
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<List<TvMazeEpisode>> getEpisodes(TvMaze tvMaze, int showID) {
		try {
			Response<List<TvMazeEpisode>> response = tvMaze.getEpisodes(showID).execute();
			if (!response.isSuccessful()) {
				System.out.println(response.errorBody());
				return Optional.empty();
			}
			return Optional.of(response.body());
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static boolean searchForKeyword(@NotNull Path file, List<String> keywords) {
		for (String keyword : keywords) {
			String filename = file.toString();
			if (filename.contains(keyword))
				return true;
		}
		return false;
	}

	public static boolean isOfSeason(@NotNull Path file, @NotNull String season) {
		return file.toString().toUpperCase().contains(season);
	}
}
