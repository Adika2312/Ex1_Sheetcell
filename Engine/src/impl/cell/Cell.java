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
        effectiveValue.setActivatingCell(this);
        originalValue = cellToCopy.getOriginalValue();
        cellsImInfluencing = cellToCopy.getCellsImInfluencing();
        cellsImDependentOn = cellToCopy.getCellsImDependentOn();
        version = cellToCopy.getVersion();
    }


    public Sheet getSheet() {
        return mySheet;
    }

    @Override
    public void updateValues(CellValue effectiveValue, String originalValue, boolean isFromFile) {
        effectiveValue.setActivatingCell(this);
        //effectiveValue.calculateAndSetEffectiveValue();
        this.effectiveValue = effectiveValue;
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

    public void calculateEffectiveValue() {
        effectiveValue.calculateAndSetEffectiveValue();
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

    public void clearDependenciesLists() {
        cellsImInfluencing.clear();
        cellsImDependentOn.clear();
    }
}
