package impl;

import api.*;
import generated.STLSheet;
import impl.cell.Cell;
import impl.cell.value.BooleanValue;
import impl.cell.value.FunctionValue;
import impl.cell.value.NumericValue;
import impl.cell.value.StringValue;
import impl.sheet.Sheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EngineImpl implements Engine {
    private Sheet currentSheet;
    private final DTOFactory DTOFactory;
    private final String JAXB_XML_PACKAGE_NAME = "generated";

    public EngineImpl(DTOFactory DTOFactory) {
        this.DTOFactory = DTOFactory;
    }

    @Override
    public void loadFile(String filePath) throws IOException {
        STLSheet currentSTLSheet;
        try {
            currentSTLSheet = buildSTLSheetFromXML(filePath);
        }
        catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        buildSheetFromSTLSheet(currentSTLSheet);
    }

    @Override
    public STLSheet buildSTLSheetFromXML(String filePath)throws IOException, JAXBException{
        InputStream inputStream = new FileInputStream(filePath);
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (STLSheet) unmarshaller.unmarshal(inputStream);
    }

    @Override
    public void buildSheetFromSTLSheet(STLSheet currentSTLSheet) {
        currentSheet = new Sheet();
        currentSheet.setName(currentSTLSheet.getName());
        currentSheet.setNumOfCols(currentSTLSheet.getSTLLayout().getColumns());
        currentSheet.setNumOfRows(currentSTLSheet.getSTLLayout().getRows());
        currentSheet.setColWidth(currentSTLSheet.getSTLLayout().getSTLSize().getColumnWidthUnits());
        currentSheet.setRowHeight(currentSTLSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());
        currentSheet.setActiveCells(currentSTLSheet.getSTLCells().getSTLCell());
    }

    @Override
    public DTO getSheetDTO() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createSheetDTO(currentSheet);
    }

    @Override
    public DTO getCellDTO(String cellIdentity) {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        Cell currentCell = currentSheet.getCell(cellIdentity);
        if(currentCell == null)
            return null;
        return DTOFactory.createCellDTO(currentSheet.getCell(cellIdentity));
    }

    @Override
    public boolean isCellInBounds(int row, int col) {
        return(row >= 0 && row < currentSheet.getNumOfRows() && col >= 0 && col < currentSheet.getNumOfCols());
    }

    @Override
    public void updateCellValue(String cellIdentity, CellValue value, String originalValue) {
        currentSheet.setCellValues(cellIdentity, value, originalValue);
    }

    public static CellValue convertStringToCellValue(String newValue) {
        CellValue cellValue;

        // Check for Boolean
        if (newValue.equals("TRUE") || newValue.equals("FALSE")) {
            cellValue = new BooleanValue(Boolean.parseBoolean(newValue));
        }
        // Check for Numerical
        else if (newValue.matches("-?\\d+(\\.\\d+)?")) {
            try {
                double numericValue = Double.parseDouble(newValue);
                cellValue = new NumericValue(numericValue);
            }
            catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid numeric value.");
            }
        }
        // Check for Function
        else if (newValue.matches("\\{[A-Z]+(,[^,]+)*\\}")) {
            cellValue = new FunctionValue(newValue);
        }
        // Otherwise, treat as String
        else {
            cellValue = new StringValue(newValue);
        }

        return cellValue;
    }

    @Override
    public boolean isSheetLoaded(){
        return currentSheet != null;
    }
}
