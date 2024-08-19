package impl;

import api.*;
import generated.STLSheet;
import impl.cell.value.BooleanValue;
import impl.cell.value.FunctionValue;
import impl.cell.value.NumericValue;
import impl.cell.value.StringValue;
import impl.sheet.Sheet;
import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EngineImpl implements Engine {
    private Sheet currentSheet;
    private final DTOFactory DTOFactory;
    private final String JAXB_XML_PACKAGE_NAME = "generated";

    @Override
    public void loadFile(String filePath) throws IOException, JAXBException {
        InputStream inputStream = new FileInputStream(filePath);
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        STLSheet currentSTLSheet = (STLSheet) unmarshaller.unmarshal(inputStream);
    }

    @Override
    public DTO getSheetDTO() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createSheetData(currentSheet);
    }

    @Override
    public DTO getCellDTO(int row, int col) {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createCellData(currentSheet.getCell(row, col));
    }

    public EngineImpl(DTOFactory DTOFactory) {
        this.DTOFactory = DTOFactory;
    }

    public DTO getSheetData() {
        return DTOFactory.createSheetData(this.currentSheet);
    }

    @Override
    public String getCellValue(int row, int col) {
        return currentSheet.getCellValues(row, col);
    }

    @Override
    public boolean isCellInBounds(int row, int col) {
        return(row >= 0 && row < currentSheet.getNumOfRows() && col >= 0 && col < currentSheet.getNumOfCols());
    }

    @Override
    public void updateCellValue(int row, int col, CellValue value, String originalValue) {
        currentSheet.setCellValues(row, col, value,originalValue);
    }


    public static CellValue convertStringToCellValue(String newValue) {

        CellValue cellValue = null;

        while (true){
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
                    System.out.println("Invalid numeric value.");
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

            if (true) {
                break;
            }
            else {
                System.out.println("Invalid value entered.");
            }
        }

        return cellValue;
    }
}
