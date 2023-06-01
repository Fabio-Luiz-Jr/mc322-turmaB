import java.util.ArrayList;
import java.util.Date;

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
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
            "\nCNPJ: " + getCnpj() + 
            "\nData da fundação" + getDataFundacao() + 
            "\nLista de frotas: " + getListaFrota();
    }
}
