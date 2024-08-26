package api;


import generated.STLSheet;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Map;

public interface Engine {
    void loadFile(String filePath) throws IOException, JAXBException;
    DTO getSheetDTO();
    boolean isCellInBounds(int row, int col);
    void updateCellValue(String cellIdentity, CellValue value, String originalValue);
    DTO getCellDTO(String cellIdentity);
    void checkForLoadedFile();
    Map<Integer,DTO> getSheetsPreviousVersionsDTO();
}
