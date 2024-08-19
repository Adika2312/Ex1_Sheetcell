package api;


import generated.STLSheet;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface Engine {
    void loadFile(String filePath) throws IOException, JAXBException;
    DTO getSheetDTO();
    String getCellValue(int row, int col);
    boolean isCellInBounds(int row, int col);
    void updateCellValue(int row, int column, CellValue value, String originalValue);
    DTO getCellDTO(int row, int col);
    STLSheet buildSTLSheetFromXML(String filePath)throws IOException, JAXBException;
    void buildSheetFromSTLSheet(STLSheet currentSTLSheet);
    //List<Version> getDocumentVersions(String documentTitle);
}
