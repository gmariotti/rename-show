package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Image {

	@SerializedName("medium")
	@Expose
	private String medium;
	@SerializedName("original")
	@Expose
	private String original;

	/**
	 * @return The medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param medium The medium
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @return The original
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @param original The original
	 */
	public void setOriginal(String original) {
		this.original = original;
	}

}
