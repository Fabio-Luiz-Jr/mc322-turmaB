import java.util.ArrayList;

public abstract class Cliente{
    private String nome;
    private String endereco;
    private ArrayList<Veiculo> listaVeiculos;
    private double valorSeguro;

    public Cliente(){}

    public Cliente(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.listaVeiculos = new ArrayList<Veiculo>();
        valorSeguro = 0;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public ArrayList<Veiculo> getListaVeiculos(){
        return listaVeiculos;
    }

    public void addVeiculo(Veiculo veiculo){
        this.listaVeiculos.add(veiculo);
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    @Override
    public String toString(){
        return "{" +
            " nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", listaVeiculos='" + getListaVeiculos() + "'" +
            ", valorSeguro='" + getValorSeguro() + "'";
    }

    public abstract double calculaScore();
}
