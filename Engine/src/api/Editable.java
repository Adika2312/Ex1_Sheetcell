package api;

public interface Editable {
    void updateValues(CellValue value, String originalValue, boolean isFromFile);
}
