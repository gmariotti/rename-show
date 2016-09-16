
package tvmaze.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tvmaze.pojo.episodes.Links;

@Generated("org.jsonschema2pojo")
public class TvMazeEpisode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("season")
    @Expose
    private Integer season;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("airdate")
    @Expose
    private String airdate;
    @SerializedName("airtime")
    @Expose
    private String airtime;
    @SerializedName("airstamp")
    @Expose
    private String airstamp;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("_links")
    @Expose
    private Links links;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The season
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * 
     * @param season
     *     The season
     */
    public void setSeason(Integer season) {
        this.season = season;
    }

    /**
     * 
     * @return
     *     The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The airdate
     */
    public String getAirdate() {
        return airdate;
    }

    /**
     * 
     * @param airdate
     *     The airdate
     */
    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    /**
     * 
     * @return
     *     The airtime
     */
    public String getAirtime() {
        return airtime;
    }

    /**
     * 
     * @param airtime
     *     The airtime
     */
    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    /**
     * 
     * @return
     *     The airstamp
     */
    public String getAirstamp() {
        return airstamp;
    }

    /**
     * 
     * @param airstamp
     *     The airstamp
     */
    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    /**
     * 
     * @return
     *     The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * 
     * @param runtime
     *     The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     * 
     * @return
     *     The image
     */
    public Object getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Object image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The _links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

}
