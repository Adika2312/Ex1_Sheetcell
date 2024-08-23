package impl.cell;

import api.CellValue;
import api.Editable;
import impl.cell.value.FunctionValue;
import impl.sheet.Sheet;

import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private Sheet mySheet;
    private CellValue effectiveValue;
    private String originalValue;
    private final Set<Cell> cellsImInfluencing = new HashSet<>();
    private final Set<Cell> cellsImDependentOn = new HashSet<>();
    private int version = 1;

    public Cell(Sheet sheet) {
        mySheet = sheet;
    }

    public Sheet getSheet() {
        return mySheet;
    }

    @Override
    public void update(CellValue value, String originalValue, boolean isFromFile) {
        this.effectiveValue = value;
        value.setActivatingCell(this);
        effectiveValue.calculateAndSetEffectiveValue();
//        if(value instanceof FunctionValue functionValue)
//        {
//            functionValue.calculateAndSetEffectiveValue();
//        }
        this.originalValue = originalValue;
        if(!isFromFile)
            version++;
    }

//    @Override
//    public String toString() {
//        StringBuilder str = new StringBuilder();
//        str.append("Effective Value: ").append(effectiveValue.getEffectiveValue()).append("\n");
//        str.append("Original Value: ").append(originalValue);
//
//        return str.toString();
//    }

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
}
