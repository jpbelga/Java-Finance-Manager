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

public class CSVWriterDAOUsuarioImpl implements CSVWriterDAOUsuario {

    private String filename;
    private boolean createIfNotExists;

    public CSVWriterDAOUsuarioImpl(String filename, boolean createIfNotExists) {
        this.filename = filename;
        this.createIfNotExists = createIfNotExists;

        if (!Files.exists(Paths.get(filename)) && !createIfNotExists) {
            throw new RuntimeException("File not found: " + filename);
        }
    }

    @Override
    public void insertUsuario(Usuario usuario) throws IOException {
        try (Writer writer = new FileWriter(filename, true);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] data = {usuario.getApelido(), usuario.getNome()};
            csvWriter.writeNext(data);
        }
    }

    @Override
    public void insertUsuarios(List<Usuario> usuarios) throws IOException {
        try (Writer writer = new FileWriter(filename, true);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            for (Usuario usuario : usuarios) {
                String[] data = {usuario.getApelido(), usuario.getNome()};
                csvWriter.writeNext(data);
            }
        }
    }

    @Override
    public void updateUsuario(Usuario usuario) throws IOException, CsvException {
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
            if (row[0].equals(usuario.getApelido())) {
                row[1] = usuario.getNome();
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