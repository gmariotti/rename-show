package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Country {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("code")
	@Expose
	private String code;
	@SerializedName("timezone")
	@Expose
	private String timezone;

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
	 * @return The code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code The code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return The timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone The timezone
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
