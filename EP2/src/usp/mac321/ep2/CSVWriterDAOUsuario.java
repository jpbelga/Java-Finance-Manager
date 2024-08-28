package usp.mac321.ep2;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public interface CSVWriterDAOUsuario {
    void insertUsuario(Usuario usuario) throws IOException;
    void insertUsuarios(List<Usuario> usuarios) throws IOException;
    void updateUsuario(Usuario usuario) throws IOException, CsvException;
}