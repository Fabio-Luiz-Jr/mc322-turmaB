import java.util.ArrayList;
import java.util.Objects;

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

    public void removeVeiculo(int index){
        this.listaVeiculos.remove(index);
    }

    public void removeVeiculo(String placa){
        for(Veiculo v: getListaVeiculos())
            if(Objects.equals(v.getPlaca(), placa))
                this.listaVeiculos.remove(v);
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    @Override
    public String toString(){
        return "Nome: " + getNome() + "\n" +
            "Endereço: " + getEndereco() + "\n" +
            "Lista de veículos {" + getListaVeiculos() + "}\n" +
            "Valor do seguro: R$" + getValorSeguro() + "\n";
    }

    public abstract double calculaScore();
}
