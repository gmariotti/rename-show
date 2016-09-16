package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Show {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("genres")
	@Expose
	private List<String> genres = new ArrayList<String>();
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("runtime")
	@Expose
	private Integer runtime;
	@SerializedName("premiered")
	@Expose
	private String premiered;
	@SerializedName("schedule")
	@Expose
	private Schedule schedule;
	@SerializedName("rating")
	@Expose
	private Rating rating;
	@SerializedName("weight")
	@Expose
	private Integer weight;
	@SerializedName("network")
	@Expose
	private Network network;
	@SerializedName("webChannel")
	@Expose
	private WebChannel webChannel;
	@SerializedName("externals")
	@Expose
	private Externals externals;
	@SerializedName("image")
	@Expose
	private Image image;
	@SerializedName("summary")
	@Expose
	private String summary;
	@SerializedName("updated")
	@Expose
	private Integer updated;

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return The url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url The url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return The language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language The language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return The genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @param genres The genres
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	/**
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return The runtime
	 */
	public Integer getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime The runtime
	 */
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return The premiered
	 */
	public String getPremiered() {
		return premiered;
	}

	/**
	 * @param premiered The premiered
	 */
	public void setPremiered(String premiered) {
		this.premiered = premiered;
	}

	/**
	 * @return The schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule The schedule
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return The rating
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * @param rating The rating
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	/**
	 * @return The weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight The weight
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * @return The network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * @param network The network
	 */
	public void setNetwork(Network network) {
		this.network = network;
	}

	/**
	 * @return The webChannel
	 */
	public WebChannel getWebChannel() {
		return webChannel;
	}

	/**
	 * @param webChannel The webChannel
	 */
	public void setWebChannel(WebChannel webChannel) {
		this.webChannel = webChannel;
	}

	/**
	 * @return The externals
	 */
	public Externals getExternals() {
		return externals;
	}

	/**
	 * @param externals The externals
	 */
	public void setExternals(Externals externals) {
		this.externals = externals;
	}

	/**
	 * @return The image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image The image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return The summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary The summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return The updated
	 */
	public Integer getUpdated() {
		return updated;
	}

	/**
	 * @param updated The updated
	 */
	public void setUpdated(Integer updated) {
		this.updated = updated;
	}

}
