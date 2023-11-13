package song.Service.Impl;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import song.Service.xmlReaderService;
import song.model.ForcesPlanModel;
import song.model.PropertyItem;
import song.model.Trigger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class xmlReaderServiceImpl implements xmlReaderService {
    @Override
    public List<ForcesPlanModel> readXML(String filePath) throws Exception{
        List<ForcesPlanModel> planList = new ArrayList<>();

        SAXReader saxReader = new SAXReader();
        File file = new File(filePath);
        Document document = saxReader.read(file);
//        document.selectNodes("../packagedElement");
        Element root = document.getRootElement();
        List<Element> list = root.elements();
//        List<Element> eList = list.get(8).elements().get(3).elements();
        List<Element> modelList = new ArrayList<>();
        Map<String,String> triggerIdToType = new HashMap<>();
        Map<String,String> taskTypeToId = new HashMap<>();
        for(Element e:list){
            if(e.getQName().getName().equals("Model")){
                modelList = e.elements();
            }
            if(e.getQName().getName().equals("TODO_Owner")){
                //获取triggerType和code,39
                String id = e.attribute("base_Element").getValue();
                String type = e.elementText("TODO");
                triggerIdToType.put(id,type);
            }else if(e.getQName().equals("new_action")){
                //获取taskType,45
                String taskType = e.attribute("taskType").getValue();
                String id = e.attribute("base_CallBehaviorAction").getValue();
                taskTypeToId.put(id,taskType);
            }
        }
        //拿到Model后从model中找ov5b
        List<Element> nodesList = new ArrayList<>();
        //model中找activity的节点
        List<Element> eventList = new ArrayList<>();
        for(Element e:modelList){
            //ov5b
            if(e.attribute("name")!=null&&e.attribute("name").getValue().equals("OV-5b 作战活动模型")){
                nodesList = e.elements();
            }
            //elementList.get(3).attribute("type").getValue().equals("uml:Activity")
            //activity
            else if(e.attribute("type")!=null&&e.attribute("type").getValue().equals("uml:Activity")&&e.attribute("name")!=null){
                eventList.add(e);
            }

        }
        //从Nodes里面找decisionNodes，提取trigger、beTrigger
        List<Element> behaviors = new ArrayList<>();
        Map<String,String> forceMap = new HashMap<>();
        //trigger
        Map<String,Trigger> triggerMap = new HashMap<>();

        for(Element e:nodesList){
            //decisionNodes
            if(e.attribute("type")!=null){
                //activity获取taskType
                if(e.attribute("type").getValue().equals("uml:CallBehaviorAction")){
                    if(e.attribute("name")!=null) {
                        behaviors.add(e);
                    }
                    continue;
                }
                //trigger
                if(e.attribute("type").getValue().equals("uml:DecisionNode")){
                    Trigger trigger = new Trigger();
                    trigger.setName(e.attribute("name").getValue());
                    trigger.setId(e.attribute("id").getValue());
                    //分割字符串
                    String msg = triggerIdToType.get(e.attribute("id").getValue());
                    String[] strArr = msg.split("@");
                    trigger.setType(strArr[0]);
                    Element next = nodesList.get(nodesList.indexOf(e)+1);
                    if(next.attribute("type")!=null&&next.attribute("type").getValue().equals("uml:CallBehaviorAction")){
                        triggerMap.put(next.attribute("name").getValue(),trigger);
                    }
                    continue;
                }
                //xmi:id-兵力名称
                if(e.attribute("type")!=null&&e.attribute("type").getValue().equals("uml:ActivityPartition")){
                    String forceName = e.attribute("name").getValue();
                    String id = e.attribute("id").getValue();
                    forceMap.put(id,forceName);
                }
            }
        }

        //taskType
        Map<String,String> nameId = new HashMap<>();
        for(Element e:behaviors){
            String id = e.attribute("id").getValue();
            String name = e.attribute("name").getValue();
            nameId.put(name,id);
        }
        //将activity提取出来
        for (Element e:eventList) {
            ForcesPlanModel plan = new ForcesPlanModel();
            //设置planName
            plan.setName(e.attribute("name").getValue());
            //设置planAttributes
            List<PropertyItem> attributes = new ArrayList<>();
            List<Element> attList = new ArrayList<>();
            for(Element ele:e.elements()) {
                if (ele.attribute("type") != null && ele.attribute("type").getValue().equals("uml:Parameter")) {
                    attList.add(ele);
                }
            }
            for (Element a : attList) {
                //设置attribute
                PropertyItem p = new PropertyItem();
                //name
                p.setName(a.attribute("name").getValue());
                if(a.attribute("name").getValue().equals("areaId")){
                    plan.setAreaId(a.elements().get(0).attribute("value").getValue());
                    break;
                }
                //text
                if (a.elements().size() > 0 && a.elements().get(0).attribute("body") != null) {
                    p.setText(a.elements().get(0).attribute("body").getValue());
                } else {
                    p.setText("");
                }
                //value
                if (a.elements().size() > 1 && a.elements().get(1).attribute("value") != null) {
                    p.setValue(a.elements().get(1).attribute("value").getValue());
                } else {
                    p.setValue("");
                }
                attributes.add(p);
            }
            plan.setAttributes(attributes);
            //taskType
            plan.setTaskType(taskTypeToId.get(nameId.get(e.attribute("name").getValue())));
            //todo:设置targets、executors
            //planId
            //UUID
            for(Element be: behaviors){
                if(be.attribute("name").getValue().equals(e.attribute("name").getValue())){
                    //todo:根据Map<forceName,forceId>将name转换Id
                    String forceName = forceMap.get(be.elements().get(0).attribute("idref").getValue());
//                    plan.setForcesId();
                }
            }
            plan.setTrigger(triggerMap.get(e.attribute("name").getValue()));
            planList.add(plan);
        }
        return planList;
    }
}
