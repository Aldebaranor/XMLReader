package song.Service.Impl;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import song.Service.xmlReaderService;
import song.model.ForcesPlanModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class xmlReaderServiceImpl implements xmlReaderService {
    @Override
    public void readXML(String filePath) throws Exception{
        SAXReader saxReader = new SAXReader();
        File file = new File(filePath);
        Document document = saxReader.read(file);
        document.selectNodes("../packagedElement");
//        document.selectSingleNode("//packagedElement").getText();

        Element root = document.getRootElement();
        List<Element> list = root.elements();
//        List<Element> eList = list.get(8).elements().get(3).elements();
        List<Element> elementList = new ArrayList<>();
        for(Element e:list){
            if(e.getQName().getName().equals("Model")){
                elementList = e.elements();
                break;
            }
        }
        //拿到Model
        List<Element> eList = new ArrayList<>();
        for(Element e:elementList){
            //ov5b
            if(e.attribute("name").getValue().equals("OV-5b 作战活动模型")){
                eList = e.elements();
                //trigger
            }
            //elementList.get(3).attribute("type").getValue().equals("uml:Activity")
            //事件
        }

        List<ForcesPlanModel> modelList = new ArrayList<>();
//        for(:){
//            ForcesPlanModel model = new ForcesPlanModel();
//            model.setName();
//            model.setForcesId();
//            model.setTaskType();
//            model.getAreaId();
//            //uncertain
//            model.setBeTrigger();
//            model.setTargets();
//            model.setExecutors();
//            //attributes
//            List<PropertyItem> attributes = new ArrayList<>();
//            for(:){
//            PropertyItem att = new PropertyItem();
//            att.setName();
//            att.setText();
//            att.setValue();
//            attributes.add(att);
//            }
//            model.setAttributes(attributes);
//            //trigger
//            Trigger trigger = new Trigger();
//            trigger.setName();
//            trigger.setType();
//            //code
//            trigger.setId();
//            model.setTrigger(trigger);
//        }

        System.out.println("");
    }
}
