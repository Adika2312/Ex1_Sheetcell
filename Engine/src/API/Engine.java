package API;

import Impl.Sheet;

public interface Engine {
    //void loadSystemFile(String filePath);
    Sheet GetSheet();
    //String getCellValue(int row, int column);
    //void updateCellValue(int row, int column, String value);
    //List<Version> getDocumentVersions(String documentTitle);
    void Exit();
}
