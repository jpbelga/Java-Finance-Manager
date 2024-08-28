package usp.mac321.ep2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class TestaGerenciador {

    @Test
    void testPrintFinancialReport_OneTypeOfExpense_OneTypeOfRevenue() {
        Gerenciador gerenciador = new Gerenciador();
        
        // Create users
        gerenciador.criarUsuario("user1", "User 1");
        gerenciador.criarUsuario("user2", "User 2");
        
        // Create types of expenses and revenues
        gerenciador.criarTipoDespesa("Food", "Restaurant");
        gerenciador.criarTipoReceita("Salary", "Monthly");
        
        // Create lancamentos
        Date date = new Date();
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(0), gerenciador.usuarios.get(0), "Lunch", 20);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(0), gerenciador.usuarios.get(0), "Salary", 1000);
        
        // Print financial report
        StringBuilder report = new StringBuilder();
        for (Lancamento l : gerenciador.lancamentos) {
            if (l.getData().equals(date)) {
                report.append(l).append("\n");
            }
        }
        
        // Verify report
        String expectedReport = "Lancamento{id=0, data=" + date + ", tipo=TipoLançamento{categoria='Food', subcategoria='Restaurant'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Lunch', valor=20}\n"
                              + "Lancamento{id=1, data=" + date + ", tipo=TipoLançamento{categoria='Salary', subcategoria='Monthly'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Salary', valor=1000}\n";
        assertEquals(expectedReport, report.toString());
    }
    
    @Test
    void testPrintFinancialReport_TwoTypesOfExpenses_TwoTypesOfRevenues() {
        Gerenciador gerenciador = new Gerenciador();
        
        // Create users
        gerenciador.criarUsuario("user1", "User 1");
        gerenciador.criarUsuario("user2", "User 2");
        
        // Create types of expenses and revenues
        gerenciador.criarTipoDespesa("Food", "Restaurant");
        gerenciador.criarTipoDespesa("Transport", "Gasoline");
        gerenciador.criarTipoReceita("Salary", "Monthly");
        gerenciador.criarTipoReceita("Investment", "Dividend");
        
        // Create lancamentos
        Date date = new Date();
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(0), gerenciador.usuarios.get(0), "Lunch", 20);
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(1), gerenciador.usuarios.get(0), "Gas", 30);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(0), gerenciador.usuarios.get(0), "Salary", 1000);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(1), gerenciador.usuarios.get(0), "Dividend", 500);
        
        // Print financial report
        StringBuilder report = new StringBuilder();
        for (Lancamento l : gerenciador.lancamentos) {
            if (l.getData().equals(date)) {
                report.append(l).append("\n");
            }
        }
        
        // Verify report
        String expectedReport = "Lancamento{id=2, data=" + date + ", tipo=TipoLançamento{categoria='Food', subcategoria='Restaurant'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Lunch', valor=20}\n"
                              + "Lancamento{id=3, data=" + date + ", tipo=TipoLançamento{categoria='Transport', subcategoria='Gasoline'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Gas', valor=30}\n"
                              + "Lancamento{id=4, data=" + date + ", tipo=TipoLançamento{categoria='Salary', subcategoria='Monthly'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Salary', valor=1000}\n"
                              + "Lancamento{id=5, data=" + date + ", tipo=TipoLançamento{categoria='Investment', subcategoria='Dividend'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Dividend', valor=500}\n";
        assertEquals(expectedReport, report.toString());
    }
    
    @Test
    void testPrintFinancialReport_ThreeTypesOfExpenses_ThreeTypesOfRevenues() {
        Gerenciador gerenciador = new Gerenciador();
        
        // Create users
        gerenciador.criarUsuario("user1", "User 1");
        gerenciador.criarUsuario("user2", "User 2");
        
        // Create types of expenses and revenues
        gerenciador.criarTipoDespesa("Food", "Restaurant");
        gerenciador.criarTipoDespesa("Transport", "Gasoline");
        gerenciador.criarTipoDespesa("Entertainment", "Movie");
        gerenciador.criarTipoReceita("Salary", "Monthly");
        gerenciador.criarTipoReceita("Investment", "Dividend");
        gerenciador.criarTipoReceita("Gift", "Birthday");
        
        // Create lancamentos
        Date date = new Date();
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(0), gerenciador.usuarios.get(0), "Lunch", 20);
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(1), gerenciador.usuarios.get(0), "Gas", 30);
        gerenciador.criarLancamento(date, gerenciador.tiposDespesa.get(2), gerenciador.usuarios.get(0), "Movie", 40);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(0), gerenciador.usuarios.get(0), "Salary", 1000);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(1), gerenciador.usuarios.get(0), "Dividend", 500);
        gerenciador.criarLancamento(date, gerenciador.tiposReceita.get(2), gerenciador.usuarios.get(0), "Birthday Gift", 200);
        
        // Print financial report
        StringBuilder report = new StringBuilder();
        for (Lancamento l : gerenciador.lancamentos) {
            if (l.getData().equals(date)) {
                report.append(l).append("\n");
            }
        }
        
        // Verify report
        String expectedReport = "Lancamento{id=6, data=" + date + ", tipo=TipoLançamento{categoria='Food', subcategoria='Restaurant'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Lunch', valor=20}\n"
                              + "Lancamento{id=7, data=" + date + ", tipo=TipoLançamento{categoria='Transport', subcategoria='Gasoline'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Gas', valor=30}\n"
                              + "Lancamento{id=8, data=" + date + ", tipo=TipoLançamento{categoria='Entertainment', subcategoria='Movie'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Movie', valor=40}\n"
                              + "Lancamento{id=9, data=" + date + ", tipo=TipoLançamento{categoria='Salary', subcategoria='Monthly'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Salary', valor=1000}\n"
                              + "Lancamento{id=10, data=" + date + ", tipo=TipoLançamento{categoria='Investment', subcategoria='Dividend'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Dividend', valor=500}\n"
                              + "Lancamento{id=11, data=" + date + ", tipo=TipoLançamento{categoria='Gift', subcategoria='Birthday'}, usuario=Usuario{apelido='user1', nome='User 1'}, descrição='Birthday Gift', valor=200}\n";
        assertEquals(expectedReport, report.toString());
    }
}