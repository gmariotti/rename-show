package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class WebChannel {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("country")
	@Expose
	private Country country;

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
	 * @return The country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country The country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

}
