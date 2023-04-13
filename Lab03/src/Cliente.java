import java.util.Date;
import java.util.ArrayList;

public class Cliente{
    private String nome;
    private String endereco;
    private Date dataLiscenca;
    private String educacao;
    private String genero;
    private String classeEconomica;
    private ArrayList<Veiculo> listaVeiculos;

    public Cliente(){}

    public Cliente(String nome, String endereco, Date dataLiscenca, String educacao, String genero,
            String classeEconomica) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataLiscenca = dataLiscenca;
        this.educacao = educacao;
        this.genero = genero;
        this.classeEconomica = classeEconomica;
        this.listaVeiculos = new ArrayList<Veiculo>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDataLiscenca() {
        return dataLiscenca;
    }

    public void setDataLiscenca(Date dataLiscenca) {
        this.dataLiscenca = dataLiscenca;
    }

    public String getEducacao() {
        return educacao;
    }

    public void setEducacao(String educacao) {
        this.educacao = educacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClasseEconomica() {
        return classeEconomica;
    }

    public void setClasseEconomica(String classeEconomica) {
        this.classeEconomica = classeEconomica;
    }

    public ArrayList<Veiculo> getListaVeiculos() {
        return listaVeiculos;
    }

    public void addVeiculo(Veiculo veiculo){
        this.listaVeiculos.add(veiculo);
    }


    @Override
    public String toString() {
        return "{" +
            " nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", dataLiscenca='" + getDataLiscenca() + "'" +
            ", educacao='" + getEducacao() + "'" +
            ", genero='" + getGenero() + "'" +
            ", classeEconomica='" + getClasseEconomica() + "'" +
            ", listaVeiculos='" + getListaVeiculos() + "'" +
            "}";
    }

}
