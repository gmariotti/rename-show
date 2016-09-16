package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Rating {

	@SerializedName("average")
	@Expose
	private Double average;

	/**
	 * @return The average
	 */
	public Double getAverage() {
		return average;
	}

	/**
	 * @param average The average
	 */
	public void setAverage(Double average) {
		this.average = average;
	}

}
