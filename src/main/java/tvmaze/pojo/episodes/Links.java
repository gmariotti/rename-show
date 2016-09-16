
package tvmaze.pojo.episodes;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Links {

    @SerializedName("self")
    @Expose
    private Self self;

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

}
