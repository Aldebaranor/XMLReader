package song.Service.Impl;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import song.Service.xmlReaderService;

import java.io.File;
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
        Node e = list.get(8).content().get(3);//5、7、9、11、15、19、23、27、31、
        
        System.out.println("");
    }
}
