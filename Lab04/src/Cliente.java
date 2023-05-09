import java.util.ArrayList;

public class Cliente{
    private String nome;
    private String endereco;
    private ArrayList<Veiculo> listaVeiculos;

    public Cliente(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.listaVeiculos = new ArrayList<Veiculo>();
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

    @Override
    public String toString(){
        return "{" +
            " nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", listaVeiculos='" + getListaVeiculos() + "'";
    }
}
