package song.Service;

import song.model.ForcesPlanModel;

import java.util.List;

public interface xmlReaderService {
    List<ForcesPlanModel> readXML(String filePath) throws Exception;
}
