package usp.mac321.ep2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Gerenciador {
	List<Usuario> usuarios;
	List<TipoLançamento> tiposDespesa;
	List<TipoLançamento> tiposReceita;
	List<Lancamento> lancamentos;
	
	public Gerenciador() {
		usuarios = new ArrayList<>();
		tiposDespesa= new ArrayList<>();
		tiposReceita = new ArrayList<>();
		lancamentos = new ArrayList<>();
	}
	
	public void criarUsuario(String apelido, String nome) {
		usuarios.add(new Usuario(apelido, nome));
	}
	
	public void deleteUsuario(Usuario user) {
		this.deleteUsuario(user.getApelido(), user.getNome());
	}
	
	public void deleteUsuario(String apelido, String nome) {
		usuarios = usuarios.stream().filter(u -> u.getApelido() == apelido && u.getNome() == nome).collect(Collectors.toList());
	}
	
	public void criarLancamento(Lancamento l) {
		this.criarLancamento(l.getData(), l.getTipo(), l.getUsuario(), l.getDescrição(), l.getValor());
	}
	
	public void criarLancamento(Date data, TipoLançamento tipo, Usuario usuario, String descrição, int valor) {
		lancamentos.add(new Lancamento(data, tipo, usuario, descrição, valor));
	}
	
	public void criarTipoDespesa(TipoDespesa desp) {
		this.criarTipoDespesa(desp.getCategoria(), desp.getSubcategoria());
		
	}
	
	public void criarTipoDespesa(String cat, String subcat) {
		tiposDespesa.add(new TipoDespesa(cat, subcat));
	}
	
	public void criarTipoReceita(TipoReceita rec) {
		this.criarTipoReceita(rec.getCategoria(), rec.getSubcategoria());
	}
	
	public void criarTipoReceita(String cat, String subcat) {
		tiposReceita.add(new TipoReceita(cat, subcat));
	}
	
	public void salvarEstado(String lancamentoFilePath, String usuarioFilePath, String despesaFilePath, String receitaFilePath) throws IOException {
		CSVWriterDAOLancamento writerLancamento = new CSVWriterDAOLancamentoImpl(lancamentoFilePath, true);
		CSVWriterDAOUsuario writerUsuario = new CSVWriterDAOUsuarioImpl(usuarioFilePath, true);
		CSVWriterDAOTipoLançamento writerDespesa = new CSVWriterDAOTipoLançamentoImpl(despesaFilePath, true);
		CSVWriterDAOTipoLançamento writerReceita = new CSVWriterDAOTipoLançamentoImpl(receitaFilePath, true);
		
		writerLancamento.insertLancamentos(lancamentos);
		writerUsuario.insertUsuarios(usuarios);
		writerDespesa.insertTipoLancamentos(tiposDespesa);
		writerReceita.insertTipoLancamentos(tiposReceita);
	}
	
	public int totalExpenses(Date from, Date to) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoDespesa.class && l.getData().before(to) && l.getData().after(from)) {
				total += l.getValor();
			}
		}
		
		return total;
	}
	
	public int totalExpenses(Date from, Date to, TipoLançamento tipo) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoDespesa.class && l.getData().before(to) && l.getData().after(from) && l.getTipo().equals(tipo)) {
				total += l.getValor();
			}
		}
		
		return total;
	}
	
	public int totalExpenses(Date from, Date to, String subcat) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoDespesa.class && l.getData().before(to) && l.getData().after(from) && l.getTipo().getSubcategoria().equals(subcat)) {
				total += l.getValor();
			}
		}
		
		return total;
	}
	
	public int totalRevenue(Date from, Date to) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoReceita.class && l.getData().before(to) && l.getData().after(from)) {
				total += l.getValor();
			}
		}
		
		return total;
	}
	
	public int totalRevenue(Date from, Date to, TipoLançamento tipo) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoLançamento.class && l.getData().before(to) && l.getData().after(from) && l.getTipo().equals(tipo)) {
				total += l.getValor();
			}
		}
		
		return total;
	} 
	
	public int totalRevenue(Date from, Date to, String subcat) {
		int total = 0;
		for (Lancamento l : lancamentos) {
			if (l.getTipo().getClass() == TipoReceita.class && l.getData().before(to) && l.getData().after(from) && l.getTipo().getSubcategoria().equals(subcat)) {
				total += l.getValor();
			}
		}
		
		return total;
	} 
	
	public void printFinancialReport(Date from, Date to) {
		for (Lancamento l : lancamentos) {
			if (l.getData().before(to) && l.getData().after(from)) {
				System.out.println(l);
			}
		}
	}

}
