package domain

import tvmaze.pojo.TvMazeEpisode

data class Episode(val name: String, val show: String, val number: Int, val season: Int) {
    companion object {
        fun createEpisode(anyEpisode: Any, show: String): Episode {
            when (anyEpisode) {
                is TvMazeEpisode -> return Episode(
                        anyEpisode.name, show, anyEpisode.number!!, anyEpisode.season!!
                )
                else -> throw ClassCastException("Not a valid type for anyEpisode")
            }
        }
    }
}
