import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;
    private ArrayList<Frota> listaFrota;

    public ClientePJ(String nome, String telefone, String endereco, String email, String cnpj, Date dataFundacao, int qtdeFuncionarios){
        super(nome, telefone, endereco, email);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
        this.listaFrota = new ArrayList<Frota>();
    }
    //#region Getters e setters
    public String getCnpj(){return cnpj;}
    public Date getDataFundacao(){return dataFundacao;}
    public void setDataFundacao(Date dataFundacao){this.dataFundacao = dataFundacao;}
    public ArrayList<Frota> getListaFrota(){return listaFrota;}
    public void setListaFrota(ArrayList<Frota> listaFrota){this.listaFrota = listaFrota;}
    private Frota getFrota(Frota frota){
        for(int i = 0; i < getListaFrota().size(); i++)
            if(Objects.equals(this.listaFrota.get(i), frota))
                return this.listaFrota.get(i);
        return null;
    }
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
            "\nCNPJ: " + getCnpj() + 
            "\nData da fundação: " + getDataFundacao() + 
            "\nLista de frotas: " + getListaFrota();
    }

    public boolean cadastrarFrota(Frota frota){
        if(getListaFrota().contains(frota))
            return false;
        this.listaFrota.add(frota);
        return true;
    }

    public boolean atualizarFrota(Frota frota){
        if(getListaFrota().contains(frota)){
            this.listaFrota.remove(frota);
            return true;
        }
        return false;
    }
    
    public boolean atualizarFrota(Frota frota, Veiculo veiculo){
        if(getFrota(frota) == null)
            return false;
        getFrota(frota).addVeiculo(veiculo);
        return true;
    }
}
