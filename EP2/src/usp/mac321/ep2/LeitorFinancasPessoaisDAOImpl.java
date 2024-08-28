package usp.mac321.ep2;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeitorFinancasPessoaisDAOImpl implements LeitorFinancasPessoaisDAO {
	private List<Usuario> usuarios;
	private List<TipoDespesa> tiposDespesaSave;
	private List<TipoReceita> tiposReceitaSave;
	private List<Lancamento> lancamentosSave;

	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<TipoDespesa> getTiposDespesaSave() {
		return tiposDespesaSave;
	}

	public List<TipoReceita> getTiposReceitaSave() {
		return tiposReceitaSave;
	}

	public LeitorFinancasPessoaisDAOImpl() {
		usuarios = new ArrayList<Usuario>();
		tiposDespesaSave = new ArrayList<TipoDespesa>();
		tiposReceitaSave = new ArrayList<TipoReceita>();
	} 
	 
	@Override
	public List<Usuario> leUsuarios(String nomeArquivoUsuarios) {
	    List<Usuario> usuarios = new ArrayList<>();
	    Map<String, Usuario> usuariosMap = new HashMap<>();
	    try (CSVReader reader = new CSVReader(new FileReader(nomeArquivoUsuarios))) {
	        String[] line;
	        // Skip header
	        reader.readNext(); 
	        while ((line = reader.readNext()) != null) {
	            String apelido = line[0];
	            if (usuariosMap.containsKey(apelido)) {
	                throw new DuplicateSurnameException(apelido);
	            }
	            Usuario usuario = new Usuario(line[0], line[1]);
	            usuarios.add(usuario);
	            usuariosMap.put(apelido, usuario);
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("File not found: " + nomeArquivoUsuarios, e);
	    } catch (IOException | CsvException e) {
	        throw new RuntimeException("Erro ao ler arquivo de usuários", e);
	    }
	    this.usuarios = usuarios;
	    return usuarios;
	} 
	
	@Override
	public List<TipoDespesa> leTiposDespesas(String nomeArquivoTiposDespesas) {
	    List<TipoDespesa> tiposDespesas = new ArrayList<>();
	    Set<String> identifiers = new HashSet<>();
	    try (CSVReader reader = new CSVReader(new FileReader(nomeArquivoTiposDespesas))) {
	        String[] line;
	        // Skip header
	        reader.readNext(); 
	        while ((line = reader.readNext()) != null) {
	            String identifier = line[1];
	            if (identifiers.contains(identifier)) {
	                throw new RuntimeException("Two expense categories with the same identifier: " + identifier);
	            }
	            TipoDespesa tipoDespesa = new TipoDespesa(line[0], line[1]);
	            tiposDespesas.add(tipoDespesa);
	            identifiers.add(identifier);
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("File not found: " + nomeArquivoTiposDespesas, e);
	    } catch (IOException | CsvException e) {
	        throw new RuntimeException("Erro ao ler arquivo de tipos de despesas", e);
	    }
	    this.tiposDespesaSave = tiposDespesas;
	    return tiposDespesas;
	}

	@Override
	public List<TipoReceita> leTiposReceitas(String nomeArquivoTiposReceitas) {
	    List<TipoReceita> tiposReceita = new ArrayList<>();
	    Set<String> identifiers = new HashSet<>();
	    try (CSVReader reader = new CSVReader(new FileReader(nomeArquivoTiposReceitas))) {
	        String[] line;
	        // Skip header
	        reader.readNext(); 
	        while ((line = reader.readNext()) != null) {
	            String identifier = line[1];
	            if (identifiers.contains(identifier)) {
	                throw new RuntimeException("Two revenue categories with the same identifier: " + identifier);
	            }
	            TipoReceita tipoReceita = new TipoReceita(line[0], line[1]);
	            tiposReceita.add(tipoReceita);
	            identifiers.add(identifier);
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("File not found: " + nomeArquivoTiposReceitas, e);
	    } catch (IOException | CsvException e) {
	        throw new RuntimeException("Erro ao ler arquivo de tipos de receitas", e);
	    }
	    this.tiposReceitaSave = tiposReceita;
	    return tiposReceita;
	}


	@Override
	public List<Lancamento> leLancamentos(String nomeArquivoLancamentos) {
	    List<Lancamento> lancamentos = new ArrayList<>();
	    Map<Integer, Lancamento> lancamentosMap = new HashMap<>();
	    try (CSVReader reader = new CSVReader(new FileReader(nomeArquivoLancamentos))) {
	        String[] line;
	        // Skip header
	        reader.readNext();
	        while ((line = reader.readNext()) != null) {
	            int id = Integer.parseInt(line[0]);
	            if (lancamentosMap.containsKey(id)) {
	                throw new RuntimeException("Two launches with the same identifier: " + id);
	            }
	            Date data = new SimpleDateFormat("dd/MM/yy").parse(line[1]);
	            EnumLançamento tipo = line[3].equals("TRUE") ? EnumLançamento.DESPESA : EnumLançamento.RECEITA;
	            String usuarioApelido = line[2];
	            Usuario usuario = usuarios.stream().filter(u -> u.getApelido().equals(usuarioApelido)).findFirst().orElseThrow(() -> new RuntimeException("Launch is associated with a non-existent user: " + usuarioApelido));
	            String subcategoria = line[4];
	            TipoDespesa tipoDespesa = null;
	            TipoReceita tipoReceita = null; 
	            if (tipo == EnumLançamento.DESPESA) {
	                tipoDespesa = tiposDespesaSave.stream().filter(td -> td.getSubcategoria().equals(subcategoria)).findFirst().orElseThrow(() -> new RuntimeException("Launch is associated with a non-existent expense category: " + subcategoria));
	            } else {
	                tipoReceita = tiposReceitaSave.stream().filter(tr -> tr.getSubcategoria().equals(subcategoria)).findFirst().orElseThrow(() -> new RuntimeException("Launch is associated with a non-existent revenue category: " + subcategoria));
	            }
	            String descricao = line[6];
	            int valor = (int) (Float.parseFloat(line[5]) * 100);
	            if (valor < 0) {
	                throw new RuntimeException("Launch is associated with a negative value: " + valor);
	            }
	            Lancamento lancamento = new Lancamento(id, data, (tipo == EnumLançamento.DESPESA ? tipoDespesa : tipoReceita), usuario, descricao, valor);
	            lancamentos.add(lancamento);
	            lancamentosMap.put(id, lancamento);
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("File not found: " + nomeArquivoLancamentos, e);
	    } catch (IOException | CsvException | ParseException e) {
	        throw new RuntimeException("Erro ao ler arquivo de lançamentos", e);
	    }
	    this.lancamentosSave = lancamentos;
	    return lancamentos;
	}

	@Override
	public boolean isInvalid(Lancamento lancamento) {
		if (this.lancamentosSave != null) {
			for (Lancamento l : this.lancamentosSave) {
				if (l.getId() == lancamento.getId()) return true;
			}
		}
		
		if (lancamento.getTipo().getClass() == TipoReceita.class) {
			for (TipoDespesa d : this.tiposDespesaSave) {
				if (d.getCategoria() == lancamento.getTipo().getCategoria()) return true;
			}
		}
		
		if (lancamento.getTipo().getClass() == TipoDespesa.class) {
			for (TipoReceita r : this.tiposReceitaSave) {
				if (r.getCategoria() == lancamento.getTipo().getCategoria()) return true;
			}
		}
		
		boolean existe = false;
		for (Usuario u : this.usuarios) {
			if (u.getApelido() == lancamento.getUsuario().getApelido()) existe = true;
		}
		if (!existe) return true;
		
		return lancamento.getValor() < 0;	
	}
}