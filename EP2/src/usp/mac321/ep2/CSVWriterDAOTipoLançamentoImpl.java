package usp.mac321.ep2;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVWriterDAOTipoLançamentoImpl implements CSVWriterDAOTipoLançamento {

    private String filename;
    private boolean createIfNotExists;

    public CSVWriterDAOTipoLançamentoImpl(String filename, boolean createIfNotExists) {
        this.filename = filename;
        this.createIfNotExists = createIfNotExists;

        if (!Files.exists(Paths.get(filename)) && !createIfNotExists) {
            throw new RuntimeException("File not found: " + filename);
        }
    }

    @Override
    public void insertTipoLancamento(TipoLançamento tipoLancamento) throws IOException {
        try (Writer writer = new FileWriter(filename, true);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] data = {tipoLancamento.getCategoria(), tipoLancamento.getSubcategoria()};
            csvWriter.writeNext(data);
        }
    }

    @Override
    public void insertTipoLancamentos(List<TipoLançamento> tipoLancamentos) throws IOException {
        try (Writer writer = new FileWriter(filename, true);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            for (TipoLançamento tipoLancamento : tipoLancamentos) {
                String[] data = {tipoLancamento.getCategoria(), tipoLancamento.getSubcategoria()};
                csvWriter.writeNext(data);
            }
        }
    }

    @Override
    public void updateTipoLancamento(TipoLançamento tipoLancamento) throws IOException, CsvException {
        // Since we're appending to the file, we can't directly update a row.
        // We'll have to read the entire file, update the row, and then write it back.
        // This is not very efficient, but it's the best we can do with a CSV file.

        // Read the entire file
        List<String[]> allRows;
        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            allRows = csvReader.readAll();
        }

        // Update the row
        for (String[] row : allRows) {
            if (row[0].equals(tipoLancamento.getCategoria())) {
                row[1] = tipoLancamento.getSubcategoria();
                break;
            }
        }

        // Write the entire file back
        try (Writer writer = new FileWriter(filename);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(allRows);
        }
    }
}