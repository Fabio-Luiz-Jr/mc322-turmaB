import java.util.*;

public class SeguroPJ extends Seguro{
    private Frota frota;
    private ClientePJ clientePJ;

    public SeguroPJ(Date dataInicio, Date dataFim, Seguradora seguradora, Frota frota, ClientePJ clientePJ){
        super(dataInicio, dataFim, seguradora);
        this.frota = frota;
        this.clientePJ = clientePJ;
    }
    //#region Getters e Setters
    public Frota getFrota(){return this.frota;}
    public void setFrota(Frota frota){this.frota = frota;}
    public ClientePJ getClientePJ(){return this.clientePJ;}
    public void setClientePJ(ClientePJ clientePJ){this.clientePJ = clientePJ;}
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
               "\nFrota: " + getFrota() +
               "\nCliente: " + getClientePJ();
    }

    @Override
    public boolean desautorizarCondutor(Condutor condutor){
        if(getListaCondutores().contains(condutor)){
            getListaCondutores().remove(condutor);
            return true;
        }
        return false;
    }

    @Override
    public boolean autorizarCondutor(Condutor condutor){
        if(getListaCondutores().contains(condutor))
            return false;
        getListaCondutores().add(condutor);
        return true;
    }

    @Override
    public double calcularValor(){
        int quantidadeFunc =  getListaCondutores().size(),
            quantidadeVeiculos = getFrota().getListaVeiculos().size(),
            anosPosFundacao = super.calculaIdade(clientePJ.getDataFundacao()),
            quantidadeSinistrosCliente = getListaSinistros().size(),
            quantidadeSinistrosCondutor = 0;
        
        for(Condutor c: getListaCondutores()) quantidadeSinistrosCondutor += c.getListaSinistros().size();

        return CalcSeguro.VALOR_BASE.getValor() 
                * (10.0 + (quantidadeFunc / 10.0))
                * (1.0 + 1.0 / (quantidadeVeiculos + 2.0))
                * (1.0 + 1.0 / (anosPosFundacao + 2.0))
                * (2.0 + quantidadeSinistrosCliente / 10.0)
                * (5.0 + quantidadeSinistrosCondutor / 10.0);
    }
    
    @Override
    public boolean gerarSinistro(String endereco, String cpf, Seguro seguro){
        for(Condutor condutor: seguro.getListaCondutores())
            if(Objects.equals(condutor.getCpf(), cpf)){
                getListaSinistros().add(new Sinistro(endereco, condutor, seguro));
                return true;
            }
        return false;
    }
}
