package Impl;

import API.Engine;

public class EngineImpl implements Engine {
    private Sheet currentSheet = new Sheet();

    @Override
    public Sheet GetSheet() {
        return currentSheet;
    }

    @Override
    public void Exit() {

    }
}
