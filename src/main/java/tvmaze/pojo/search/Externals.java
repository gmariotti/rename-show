package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Externals {

	@SerializedName("tvrage")
	@Expose
	private Integer tvrage;
	@SerializedName("thetvdb")
	@Expose
	private Integer thetvdb;
	@SerializedName("imdb")
	@Expose
	private String imdb;

	/**
	 * @return The tvrage
	 */
	public Integer getTvrage() {
		return tvrage;
	}

	/**
	 * @param tvrage The tvrage
	 */
	public void setTvrage(Integer tvrage) {
		this.tvrage = tvrage;
	}

	/**
	 * @return The thetvdb
	 */
	public Integer getThetvdb() {
		return thetvdb;
	}

	/**
	 * @param thetvdb The thetvdb
	 */
	public void setThetvdb(Integer thetvdb) {
		this.thetvdb = thetvdb;
	}

	/**
	 * @return The imdb
	 */
	public String getImdb() {
		return imdb;
	}

	/**
	 * @param imdb The imdb
	 */
	public void setImdb(String imdb) {
		this.imdb = imdb;
	}

}
