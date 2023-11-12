package song.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Point implements Serializable {
    private static final long serialVersionUID = 2671344870926666320L;
    /**
     * 1经度
     */
    private Double lon;
    /**
     * 2纬度
     */
    private Double lat;
    /**
     * 3高度
     */
    private Double alt;

}
