package impl.cell;

import api.CellValue;
import api.Editable;
import impl.cell.value.FunctionValue;
import impl.sheet.Sheet;

import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private final Sheet mySheet;
    private final String identity;
    private CellValue effectiveValue;
    private String originalValue;
    private final Set<Cell> cellsImInfluencing = new HashSet<>();
    private final Set<Cell> cellsImDependentOn = new HashSet<>();
    private int version = 1;

    public Cell(Sheet sheet, String identity) {
        mySheet = sheet;
        this.identity = identity;
    }

    public Sheet getSheet() {
        return mySheet;
    }

    @Override
    public void update(CellValue value, String originalValue, boolean isFromFile) {
        this.effectiveValue = value;
        value.setActivatingCell(this);
        effectiveValue.calculateAndSetEffectiveValue();
        this.originalValue = originalValue;
        if(!isFromFile)
            version++;
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
}
