package extensions

import tvmaze.TvMaze
import tvmaze.pojo.TvMazeEpisode
import tvmaze.pojo.TvMazeSearch
import java.io.IOException
import java.util.*

fun TvMaze.getTvShowFromName(tvShow: String): Optional<List<TvMazeSearch>> {
	try {
		val response = this.getShows(tvShow).execute()
		if (!response.isSuccessful) {
			println(response.errorBody())
		} else {
			return Optional.of(response.body())
		}
	} catch (e: IOException) {
		e.printStackTrace()
	}
	return Optional.empty()
}

fun TvMaze.getEpisodes(showID: Int): Optional<List<TvMazeEpisode>> {
	try {
		val response = this.getEpisodes(showID).execute()
		if (!response.isSuccessful) {
			println(response.errorBody())
		} else {
			return Optional.of(response.body())
		}
	} catch (e: IOException) {
		e.printStackTrace()
	}
	return Optional.empty()
}