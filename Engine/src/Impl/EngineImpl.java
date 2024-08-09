package Impl;

import API.Engine;

public class EngineImpl implements Engine {
    private Sheet currentSheet;

    @Override
    public Sheet GetSheet() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return currentSheet;
    }

    @Override
    public void Exit() {
    }
}
