package song.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Trigger implements Serializable {

    private static final long serialVersionUID = -5784321157087439551L;
    private String id;

    private String name;

    private Long triggerStartTime;

    private Long triggerEndTime;

    private String type;

    private List<PropertyItem> outAttributes;
}
