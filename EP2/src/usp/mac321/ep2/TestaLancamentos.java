package usp.mac321.ep2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestaLancamentos {

	private String testLancamentosFilePath = "csv/testWriterLancamentos.csv";
	private CSVWriterDAOLancamentoImpl csvWriterDAOLancamento = new CSVWriterDAOLancamentoImpl(testLancamentosFilePath, true);
	private LeitorFinancasPessoaisDAOImpl leitorFinancasPessoaisDAO = new LeitorFinancasPessoaisDAOImpl();

	@Before
	public void setup() {
		File testFile = new File(testLancamentosFilePath);
		if (testFile.exists()) {
		    if (!testFile.delete()) {
		        throw new RuntimeException("Failed to delete test file");
		    }
		}

		if (!testFile.exists()) {
	        try {
	            testFile.createNewFile();
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to create test file", e);
	        }
	    }

	    
	    // Read all necessary files in the correct order
	    leitorFinancasPessoaisDAO.leUsuarios("csv/usuarios.csv");
	    leitorFinancasPessoaisDAO.leTiposDespesas("csv/tiposDespesas.csv");
	    leitorFinancasPessoaisDAO.leTiposReceitas("csv/tiposReceitas.csv");
	}

	
    @Test
    public void testValorNeg() {
        Lancamento lancamento = new Lancamento(1, new Date("20/03/24"), new TipoDespesa("Educação", "Curso de Idioma"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", -100);
        assertEquals(lancamento.getState(leitorFinancasPessoaisDAO), EstadoLançamento.INVALIDO);
    }
    
    @Test
    public void testUsuarioInv() {
        Lancamento lancamento = new Lancamento(1, new Date("20/03/24"), new TipoDespesa("Educação", "Curso de Idioma"), new Usuario("Alguem", "Epaminondas Encerrabodes Eleutério"), "descricao", 100);
        assertEquals(lancamento.getState(leitorFinancasPessoaisDAO), EstadoLançamento.INVALIDO);
    }
    
    @Test
    public void testCategoriaInv() {
        Lancamento lancamento = new Lancamento(1, new Date("20/03/24"), new TipoDespesa("naoexiste", "Curso de Idioma"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 100);
        assertEquals(lancamento.getState(leitorFinancasPessoaisDAO), EstadoLançamento.INVALIDO);
    }

}