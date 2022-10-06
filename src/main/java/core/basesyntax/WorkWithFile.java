package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class WorkWithFile {
    private static final String OPERATION_SUPPLY = "supply";
    private static final String OPERATION_BUY = "buy";
    private static final String RESULT_LOG_STRING = "result";
    private static final String ROW_SEPARATOR = ",";
    private static final int DATA_INDEX_OPERATION_TYPE = 0;
    private static final int DATA_INDEX_AMOUNT = 1;
    private int supply;
    private int buy;

    public void getStatistic(String fromFileName, String toFileName) {
        countOperationsCost(fromFileName);
        writeToFile(toFileName);
    }

    private void countOperationsCost(String fromFileName) {
        for (String row : getFileLines(fromFileName)) {
            String[] rowData = row.split(ROW_SEPARATOR);
            int amount = Integer.parseInt(rowData[DATA_INDEX_AMOUNT]);
            if (OPERATION_SUPPLY.equals(rowData[DATA_INDEX_OPERATION_TYPE])) {
                supply += amount;
            } else {
                buy += amount;
            }
        }
    }

    private List<String> getFileLines(String fromFileName) {
        try {
            return Files.readAllLines(new File(fromFileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from \"" + fromFileName + "\" file", e);
        }
    }

    private void writeToFile(String toFileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(getLogInfo());
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to the file. File name: " + toFileName, e);
        }
    }

    private String getLogInfo() {
        return OPERATION_SUPPLY + ROW_SEPARATOR + supply
                + System.lineSeparator() + OPERATION_BUY + ROW_SEPARATOR + buy
                + System.lineSeparator() + RESULT_LOG_STRING + ROW_SEPARATOR + (supply - buy);
    }
}
