package usp.mac321.ep2;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public interface CSVWriterDAOTipoLançamento {
    void insertTipoLancamento(TipoLançamento tipoLancamento) throws IOException;
    void insertTipoLancamentos(List<TipoLançamento> tipoLancamentos) throws IOException;
    void updateTipoLancamento(TipoLançamento tipoLancamento) throws IOException, CsvException;
}