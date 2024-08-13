package impl;

import api.Editable;

import java.util.HashMap;
import java.util.Map;

public class Cell implements Editable {
    private String OriginalValue = "*";
    private String EffectiveValue = " ";
    private String Identity;
    private final Map<String,Cell> id2DepedentCell = new HashMap<>();
    private final Map<String,Cell> id2InfluencedCell = new HashMap<>();
    private int version;

    @Override
    public void edit() {

    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Original Value: ").append(OriginalValue).append("\n");
        str.append("Effective Value: ").append(EffectiveValue).append("\n");
        //str.append("Last Version The Cell Was Changed: ").append(version).append("\n");

        return str.toString();
    }

    public String getCellEffectiveValue(){
        return EffectiveValue;
    }

    public String getOriginalValue() {
        return OriginalValue;
    }

    public int getVersion() {
        return version;
    }
}
