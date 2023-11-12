package song.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ForcesPlanModel implements Serializable {
    private static final long serialVersionUID = 6013874907746744305L;
    private Long id;
    private String name;
    private Long simId;
    private Long forcesId;
    private String taskType;
    /**
     * 是否事件触发。默认0 为时间触发
     */
    private Boolean beTrigger;
    /**
     * 任务开始时间，单位秒，默认-1 如果不为空则表示是时间触发
     */
    private Long taskStartTime;
    /**
     * 任务持续时长，单位秒，默认-1 表示不限制结束时间
     */
    private Long taskDuration;

    /**
     * 任务航线
     */
    private String pointsId;

    private List<Point> points;
    /**
     * 任务区域
     */

    private String areaId;

    private List<Point> area;
    /**
     * 任务目标
     */
    private List<Long> targets;
    /**
     * 协同兵力
     */
    private List<Long> executors;
    /**
     * 附加属性
     */
    private List<PropertyItem> attributes;

    private List<PropertyItem> outAttributes;
    /**
     * 事件触发
     */
    private Trigger trigger;

}
