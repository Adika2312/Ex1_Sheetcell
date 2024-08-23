package api;

import impl.sheet.Sheet;

public interface Editable {
    void update(CellValue value, String originalValue, boolean isFromFile, Sheet alternativeSheet);
}
