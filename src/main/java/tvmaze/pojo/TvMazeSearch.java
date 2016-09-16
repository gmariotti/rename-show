package tvmaze.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import tvmaze.pojo.search.Show;

@Generated("org.jsonschema2pojo")
public class TvMazeSearch {

	@SerializedName("score")
	@Expose
	private Double score;
	@SerializedName("show")
	@Expose
	private Show show;

	/**
	 * @return The score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score The score
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * @return The show
	 */
	public Show getShow() {
		return show;
	}

	/**
	 * @param show The show
	 */
	public void setShow(Show show) {
		this.show = show;
	}

}
