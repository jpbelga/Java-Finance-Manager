package usp.mac321.ep2;

public abstract class TipoLançamento {
    private String categoria;
    private String subcategoria;

    public TipoLançamento(String cat, String subcat) {
        this.categoria = cat;
        this.subcategoria = subcat;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "TipoLançamento{" +
                "categoria='" + categoria + '\'' +
                ", subcategoria='" + subcategoria + '\'' +
                '}';
    }
    
    public boolean equals(TipoLançamento other) {
    	System.out.println("using the correct equals");
    	return other.getCategoria() == this.categoria && other.getSubcategoria() == this.subcategoria;
    }
}