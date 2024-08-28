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

public class TestaEscritaPlanilhas {

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
    public void testInsertLancamento() throws IOException {
    	System.out.println(leitorFinancasPessoaisDAO);
        System.out.println(leitorFinancasPessoaisDAO.getUsuarios());
        System.out.println(leitorFinancasPessoaisDAO.getTiposDespesaSave());
        System.out.println(leitorFinancasPessoaisDAO.getTiposReceitaSave());
        Lancamento lancamento = new Lancamento(1, new Date("20/03/24"), new TipoDespesa("Educação", "Curso de Idioma"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 100);
        csvWriterDAOLancamento.insertLancamento(lancamento);

        List<Lancamento> lancamentos = leitorFinancasPessoaisDAO.leLancamentos(testLancamentosFilePath);
        System.out.println(lancamentos);

        assertEquals(1, lancamentos.size());
        assertEquals(lancamento.toString(), lancamentos.get(0).toString());
    }

  
    @Test
    public void testUpdateLancamento() throws IOException, CsvException {
        Lancamento lancamento = new Lancamento(1, new Date("25/06/24"), new TipoDespesa("Educação", "Curso de Idioma"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 100);
        csvWriterDAOLancamento.insertLancamento(lancamento);

        Lancamento updatedLancamento = new Lancamento(1, new Date("25/06/24"), new TipoReceita("Salário", "Principal"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 200);
        csvWriterDAOLancamento.updateLancamento(updatedLancamento);

        List<Lancamento> lancamentos = leitorFinancasPessoaisDAO.leLancamentos(testLancamentosFilePath);
        assertEquals(1, lancamentos.size());
        assertEquals(updatedLancamento.toString(), lancamentos.get(0).toString());
    }

    @Test
    public void testInsertLancamentos() throws IOException {
        Lancamento lancamento1 = new Lancamento(1, new Date("25/06/24"), new TipoDespesa("Educação", "Jardim da Infância"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 100);
        Lancamento lancamento2 = new Lancamento(2, new Date("25/06/24"), new TipoReceita("Salário", "Principal"), new Usuario("Pai", "Epaminondas Encerrabodes Eleutério"), "descricao", 200);

        List<Lancamento> lancamentos = List.of(lancamento1, lancamento2);
        csvWriterDAOLancamento.insertLancamentos(lancamentos);

        List<Lancamento> readLancamentos = leitorFinancasPessoaisDAO.leLancamentos(testLancamentosFilePath);
        assertNotNull(readLancamentos);
        assertEquals(2, readLancamentos.size());
        assertEquals(lancamento1.toString(), readLancamentos.get(0).toString());
        assertEquals(lancamento2.toString(), readLancamentos.get(1).toString());
    }
}