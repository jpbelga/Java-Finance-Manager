package usp.mac321.ep2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestaLeitorFinancasPessoais {

    private LeitorFinancasPessoaisDAO dao;

    @Before
    public void setup() {
        dao = new LeitorFinancasPessoaisDAOImpl();
    }

    @Test(expected = RuntimeException.class)
    public void testLeUsuarios_FileNotFound() throws Exception {
        dao.leUsuarios("csv/non-existent-file.csv");
    }

    @Test
    public void testLeUsuarios_OK() throws Exception {
        List<Usuario> usuarios = dao.leUsuarios("csv/usuarios.csv");
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test(expected = DuplicateSurnameException.class)
    public void testLeUsuarios_DuplicateSurname() throws Exception {
        dao.leUsuarios("csv/usuarios-duplicados.csv");
    }

    @Test(expected = RuntimeException.class)
    public void testLeTiposDespesas_FileNotFound() throws Exception {
        dao.leTiposDespesas("csv/non-existent-file.csv");
    }

    @Test
    public void testLeTiposDespesas_OK() throws Exception {
        List<TipoDespesa> tiposDespesas = dao.leTiposDespesas("csv/tiposDespesas.csv");
        assertNotNull(tiposDespesas);
        assertEquals(6, tiposDespesas.size());
    }

    @Test(expected = RuntimeException.class)
    public void testLeTiposReceitas_FileNotFound() throws Exception {
        dao.leTiposReceitas("csv/non-existent-file.csv");
    }

    @Test
    public void testLeTiposReceitas_OK() throws Exception {
        List<TipoReceita> tiposReceitas = dao.leTiposReceitas("csv/tiposReceitas.csv");
        assertNotNull(tiposReceitas);
        assertEquals(4, tiposReceitas.size());
    }

    @Test(expected = RuntimeException.class)
    public void testLeLancamentos_FileNotFound() throws Exception {
        dao.leLancamentos("csv/non-existent-file.csv");
    }

    @Test
    public void testLeLancamentos_OK() throws Exception {
	    dao.leUsuarios("csv/usuarios.csv");
	    dao.leTiposDespesas("csv/tiposDespesas.csv");
	    dao.leTiposReceitas("csv/tiposReceitas.csv");
        List<Lancamento> lancamentos = dao.leLancamentos("csv/lancamentos.csv");
        assertNotNull(lancamentos);
        assertEquals(8, lancamentos.size());
    }

    @Test(expected = RuntimeException.class)
    public void testLeLancamentos_RevenueLaunchWithExpenseCategory() throws Exception {
        dao.leLancamentos("csv/lancamentosReceitaErrada.csv");
    }

    @Test(expected = RuntimeException.class)
    public void testLeLancamentos_ExpenseLaunchWithRevenueCategory() throws Exception {
        dao.leLancamentos("csv/lancamentosDespesaErrada.csv");
    }

    @Test(expected = RuntimeException.class)
    public void testLeLancamentos_LaunchWithNonExistentUser() throws Exception {
        dao.leLancamentos("csv/lancamentosSemResponsa.csv");
    }
}