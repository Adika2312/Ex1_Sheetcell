package API;

import Impl.Sheet;

public interface Engine {
    //void loadSystemFile(String filePath);
    Sheet GetSheet();
    String getCellValue(int row, int col);
    boolean IsCellInBounds(int row, int col);
    //void updateCellValue(int row, int column, String value);
    //List<Version> getDocumentVersions(String documentTitle);
}
