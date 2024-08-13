package api;

public interface CellValue {
    String getFormattedValue();
    Object getRawValue();
    boolean isValid();
}
