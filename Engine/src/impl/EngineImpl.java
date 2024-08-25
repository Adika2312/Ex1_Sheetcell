package impl;

import api.*;
import exception.CellOutOfBoundsException;
import exception.FileNotXMLException;
import exception.InvalidSheetSizeException;
import generated.STLCell;
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

import java.io.*;
import java.util.List;

public class EngineImpl implements Engine {
    private static Sheet currentSheet;
    private final DTOFactory DTOFactory;
    private final String JAXB_XML_PACKAGE_NAME = "generated";

    public EngineImpl(DTOFactory DTOFactory) {
        this.DTOFactory = DTOFactory;
    }

    @Override
    public void loadFile(String filePath) throws IOException, JAXBException {

        checkIfFilePathValid(filePath);
        STLSheet currentSTLSheet = buildSTLSheetFromXML(filePath);
        buildSheetFromSTLSheet(currentSTLSheet);

    }

    private void checkIfFilePathValid(String filePath) throws FileNotFoundException, FileNotXMLException {
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException("File is not found in the file path given.");
        }
        if(!file.getName().endsWith(".xml")){
            throw new FileNotXMLException();
        }
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
        checkDataValidity(currentSTLSheet);
        currentSheet = new Sheet();
        currentSheet.setName(currentSTLSheet.getName());
        currentSheet.setNumOfCols(currentSTLSheet.getSTLLayout().getColumns());
        currentSheet.setNumOfRows(currentSTLSheet.getSTLLayout().getRows());
        currentSheet.setColWidth(currentSTLSheet.getSTLLayout().getSTLSize().getColumnWidthUnits());
        currentSheet.setRowHeight(currentSTLSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());
        currentSheet.setActiveCells(currentSTLSheet.getSTLCells().getSTLCell());
    }


    private void checkDataValidity(STLSheet currentSTLSheet) {
        checkSheetSize(currentSTLSheet.getSTLLayout().getRows(), currentSTLSheet.getSTLLayout().getColumns());
        checkCellsWithinBounds(currentSTLSheet);
    }

    private void checkSheetSize(int rows, int columns) {
        if (rows < 1 || rows > 50 || columns < 1 || columns > 20) {
            throw new InvalidSheetSizeException("The sheet size is out of valid bounds.");
        }
    }

    private void checkCellsWithinBounds(STLSheet sheet) {
        int rowCount = sheet.getSTLLayout().getRows();
        int columnCount = sheet.getSTLLayout().getColumns();

        List<STLCell> cells = sheet.getSTLCells().getSTLCell();

        for (STLCell cell : cells) {
            int row = cell.getRow();
            String columnLetter = cell.getColumn();

            int column = convertColumnLetterToNumber(columnLetter);

            if (row < 1 || row > rowCount || column < 1 || column > columnCount) {
                throw new CellOutOfBoundsException("A cell is defined outside the sheet boundaries: (" + row + ", " + columnLetter + ")");
            }
        }
    }

    private int convertColumnLetterToNumber(String columnLetter) {
        if (columnLetter == null || columnLetter.length() != 1 || !Character.isUpperCase(columnLetter.charAt(0))) {
            throw new IllegalArgumentException("Invalid column letter: " + columnLetter);
        }

        return columnLetter.charAt(0) - 'A' + 1;
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
            return DTOFactory.createEmptyCellDTO();
        return DTOFactory.createCellDTO(currentSheet.getCell(cellIdentity));
    }

    @Override
    public boolean isCellInBounds(int row, int col) {
        return(row >= 0 && row < currentSheet.getNumOfRows() && col >= 0 && col < currentSheet.getNumOfCols());
    }

    @Override
    public void updateCellValue(String cellIdentity, CellValue value, String originalValue) {
        Sheet alternativeSheet = currentSheet.clone();
        List<Cell> topologicalOrder = alternativeSheet.sortActiveCellsTopologicallyByDFS();
        alternativeSheet.updateOrCreateCell(cellIdentity, value, originalValue, false);

        if(!topologicalOrder.contains(alternativeSheet.getCell(cellIdentity)))
            topologicalOrder.addLast(alternativeSheet.getCell(cellIdentity));

        alternativeSheet.recalculateByTopologicalOrder(topologicalOrder);
        currentSheet = alternativeSheet;
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
