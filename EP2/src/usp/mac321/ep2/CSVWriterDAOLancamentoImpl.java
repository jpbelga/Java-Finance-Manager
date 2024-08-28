package usp.mac321.ep2;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVWriterDAOLancamentoImpl implements CSVWriterDAOLancamento {

    private String filename;
    private boolean createIfNotExists;

    public CSVWriterDAOLancamentoImpl(String filename, boolean createIfNotExists) {
        this.filename = filename;
        this.createIfNotExists = createIfNotExists;

        if (!Files.exists(Paths.get(filename)) && !createIfNotExists) {
            throw new RuntimeException("File not found: " + filename);
        }
    }

    @Override
    public void insertLancamento(Lancamento lancamento) throws IOException {
    	File file = new File(filename);
    	Writer writer = new FileWriter(file, true);        	
    	if(file.length() == 0) {
    		writer.append("ID,Data,Responsável,Despesa?,SubCategoria,Valor,Descrição\n");
    	}
    	CSVWriter csvWriter = new CSVWriter(writer);
        String[] data = {
                String.valueOf(lancamento.getId()),
                new SimpleDateFormat("dd/MM/yy").format(lancamento.getData()),
                lancamento.getUsuario().getApelido(),
                lancamento.getTipo().getClass() == TipoDespesa.class ? "TRUE" : "FALSE",
                lancamento.getTipo().getSubcategoria(),
                String.valueOf((float) lancamento.getValor() / 100),
                lancamento.getDescrição()
        };
        csvWriter.writeNext(data);
        csvWriter.close();
      }

    @Override
    public void insertLancamentos(List<Lancamento> lancamentos) throws IOException {
	    	File file = new File(filename);
	    	Writer writer = new FileWriter(file, true);        	
	    	if(file.length() == 0) {
	    		writer.append("ID,Data,Responsável,Despesa?,SubCategoria,Valor,Descrição\n");
	    	}
	    	CSVWriter csvWriter = new CSVWriter(writer);

            for (Lancamento lancamento : lancamentos) {
                String[] data = {
                        String.valueOf(lancamento.getId()),
                        new SimpleDateFormat("dd/MM/yy").format(lancamento.getData()),
                        lancamento.getUsuario().getApelido(),
                        lancamento.getTipo().getClass() == TipoDespesa.class ? "TRUE" : "FALSE",
                        lancamento.getTipo().getSubcategoria(),
                        String.valueOf((float) lancamento.getValor() / 100),
                        lancamento.getDescrição()
                };
                csvWriter.writeNext(data);
            }
            
            csvWriter.close();
        }

    @Override
    public void updateLancamento(Lancamento lancamento) throws IOException, CsvException {
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
            if (row[0].equals(String.valueOf(lancamento.getId()))) {
                row[1] = new SimpleDateFormat("dd/MM/yy").format(lancamento.getData());
                row[2] = lancamento.getUsuario().getApelido();
                row[3] = lancamento.getTipo().getClass() == TipoDespesa.class ? "TRUE" : "FALSE";
                row[4] = lancamento.getTipo().getSubcategoria();
                row[5] = String.valueOf((float) lancamento.getValor() / 100);
                row[6] = lancamento.getDescrição();
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