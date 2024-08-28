package usp.mac321.ep2;

import java.util.Date;

public class Lancamento {
	private static int counter = 0;
	// poderia ser uma string
	private int id;
	private Date data;
	private TipoLançamento tipo;
	private Usuario usuario;
	private String descrição;
	// Valor em centavos usando um inteiro para evitar problemas de precisão
	private int valor;
	 
	
	public EstadoLançamento getState(LeitorFinancasPessoaisDAO leitor) {
		 if (leitor.isInvalid(this)) return EstadoLançamento.INVALIDO;
		 else if (data.before(new Date())) return EstadoLançamento.EXECUTADO;
		 else return EstadoLançamento.PLANEJADO;
		 
	}
	 
	@Override
    public String toString() {
        return "Lancamento{" +
                "id=" + id +
                ", data=" + data +
                ", tipo=" + tipo +
                ", usuario=" + usuario +
                ", descrição='" + descrição + '\'' +
                ", valor=" + valor +
                '}';
    }
	public Lancamento(Date data, TipoLançamento tipo, Usuario usuario, String descrição, int valor) {
		this.id = counter++;
		// Manter contador atualizado
		if (counter < id) counter = id + 1;
		this.data = data;
		this.tipo = tipo;
		this.usuario = usuario;
		this.descrição = descrição;
		this.valor = valor;
	}

	
	public Lancamento(int id, Date data, TipoLançamento tipo, Usuario usuario, String descrição, int valor) {
		this.id = id;
		// Manter contador atualizado
		if (counter < id) counter = id + 1;
		this.data = data;
		this.tipo = tipo;
		this.usuario = usuario;
		this.descrição = descrição;
		this.valor = valor;
	}
	
	public int getId() {
		return id;
	}
	public Date getData() {
		return data;
	}
	public TipoLançamento getTipo() {
		return tipo;
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public String getDescrição() {
		return descrição;
	}
	public int getValor() {
		return valor;
	}
	
	// Edição dos campos de lançamento é feita através de setters; Os dados são validados quando vão ser salvos pelos DAOs
	public void setData(Date data) {
		this.data = data;
	}

	public void setTipo(TipoLançamento tipo) {
		this.tipo = tipo;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}


}
