package song.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class PropertyItem implements Serializable {
    private static final long serialVersionUID = -3875092298323244872L;
    private String name;
    private String text;
    private String value;

    public PropertyItem(String name, String text, String value) {
        this.name = name;
        this.text = text;
        this.value = value;
    }

    public PropertyItem() {
    }

    public static PropertyItem build() {
        PropertyItem ncv = new PropertyItem();
        return ncv;
    }

    public static PropertyItem build(String name, String text, String value) {
        PropertyItem ncv = new PropertyItem(name, text, value);
        return ncv;
    }
}
