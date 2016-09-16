package tvmaze.pojo.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Schedule {

	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("days")
	@Expose
	private List<String> days = new ArrayList<String>();

	/**
	 * @return The time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time The time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return The days
	 */
	public List<String> getDays() {
		return days;
	}

	/**
	 * @param days The days
	 */
	public void setDays(List<String> days) {
		this.days = days;
	}

}
