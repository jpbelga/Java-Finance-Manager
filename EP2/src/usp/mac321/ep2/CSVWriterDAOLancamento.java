package usp.mac321.ep2;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public interface CSVWriterDAOLancamento {
    void insertLancamento(Lancamento lancamento) throws IOException;
    void insertLancamentos(List<Lancamento> lancamentos) throws IOException;
    void updateLancamento(Lancamento lancamento) throws IOException, CsvException;
}