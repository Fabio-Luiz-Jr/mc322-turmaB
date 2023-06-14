import java.util.*;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;
    private ArrayList<Frota> listaFrota;

    public ClientePJ(String nome, String telefone, String endereco, String email, String cnpj, Date dataFundacao){
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
    private Frota getFrota(String code){
        for(Frota f: this.listaFrota)
            if(Objects.equals(f.getCode(), code))
                return f;
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
        if(getFrota(frota.getCode()) != null)
            return false;
        this.listaFrota.add(frota);
        return true;
    }

    public boolean atualizarFrota(String code){
        for(Frota f: this.listaFrota){
            if(Objects.equals(f.getCode(), code))
                this.listaFrota.remove(f);
            return true;
        }
        return false;
    }
    
    public boolean atualizarFrota(String code, Veiculo veiculo){
        return getFrota(code) != null ? getFrota(code).addVeiculo(veiculo) : false;
    }

    public boolean atualizarFrota(String code, String placa){
        return getFrota(code) != null ? getFrota(code).removeVeiculo(placa) : false;
    }

    public ArrayList<Veiculo> getVeiculosPorFrota(String code){
        return getFrota(code) != null ? getFrota(code).getListaVeiculos() : null;
    }
}
