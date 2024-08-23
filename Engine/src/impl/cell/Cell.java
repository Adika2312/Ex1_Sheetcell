package impl.cell;

import api.CellValue;
import api.Editable;
import impl.cell.value.StringValue;
import impl.sheet.Sheet;

import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private final Sheet mySheet;
    private final String identity;
    private CellValue effectiveValue = new StringValue("");
    private String originalValue = "";
    private Set<Cell> cellsImInfluencing = new HashSet<>();
    private Set<Cell> cellsImDependentOn = new HashSet<>();
    private int version = 1;

    public Cell(Sheet sheet, String identity) {
        mySheet = sheet;
        this.identity = identity;
    }

    public Cell(Sheet sheet, Cell cellToCopy) {
        mySheet = sheet;
        this.identity = cellToCopy.getIdentity();
        effectiveValue = cellToCopy.getEffectiveValue();
        originalValue = cellToCopy.getOriginalValue();
        cellsImInfluencing = cellToCopy.getCellsImInfluencing();
        cellsImDependentOn = cellToCopy.getCellsImDependentOn();
        version = cellToCopy.getVersion();
    }

    public Sheet getSheet() {
        return mySheet;
    }

    @Override
    public void update(CellValue value, String originalValue, boolean isFromFile, Sheet alternativeSheet) {
        value.setActivatingCell(alternativeSheet.getCell(identity));
        value.calculateAndSetEffectiveValue();
        alternativeSheet.getCell(identity).setEffectiveValue(value);
        alternativeSheet.getCell(identity).setOriginalValue(originalValue);
        if(!isFromFile)
            alternativeSheet.getCell(identity).updateVersion();
    }

    public int getVersion() {
        return version;
    }

    public CellValue getEffectiveValue() {
        return effectiveValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public Set<Cell> getCellsImInfluencing() {
        return cellsImInfluencing;
    }

    public Set<Cell> getCellsImDependentOn() {
        return cellsImDependentOn;
    }

    public String getIdentity() {
        return identity;
    }

    public void setEffectiveValue(CellValue value) {
        effectiveValue = value;
    }

    public void setOriginalValue(String value) {
        originalValue = value;
    }

    public void updateVersion() {
        version++;
    }

}
