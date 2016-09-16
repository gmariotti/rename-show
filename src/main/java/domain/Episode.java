package domain;

import tvmaze.pojo.TvMazeEpisode;

public class Episode {
	private String name;
	private String show;
	private int number;
	private int season;

	private Episode(String name, String show, int number, int season) {
		this.name = name;
		this.show = show;
		this.number = number;
		this.season = season;
	}

	public static Episode createEpisode(Object object, String show) {
		if (object instanceof TvMazeEpisode) {
			TvMazeEpisode episode = (TvMazeEpisode) object;
			return new Episode(episode.getName(), show, episode.getNumber(), episode.getSeason());
		} else {
			throw new ClassCastException("Not a valid type for episode");
		}
	}

	public static Integer getNumberFromEpisode(Episode episode) {
		return episode.getNumber();
	}

	public String getName() {
		return name;
	}

	public String getShow() {
		return show;
	}

	public int getNumber() {
		return number;
	}

	public int getSeason() {
		return season;
	}
}
