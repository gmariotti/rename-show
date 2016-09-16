package tvmaze;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import tvmaze.pojo.TvMazeEpisode;
import tvmaze.pojo.TvMazeSearch;

public interface TvMaze {
	@GET("search/shows")
	Call<List<TvMazeSearch>> getShows(@Query("q") String query);

	@GET("shows/{id}/episodes")
	Call<List<TvMazeEpisode>> getEpisodes(@Path("id") int id);
}
