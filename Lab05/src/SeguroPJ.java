import java.util.Date;

public class SeguroPJ extends Seguro{
    private Frota frota;
    private ClientePJ cliente;

    public SeguroPJ(int id, Date dataInicio, Date dataFim, Seguradora seguradora, int valorMensal, Frota frota, ClientePJ cliente){
        super(id, dataInicio, dataFim, seguradora, valorMensal);
        this.frota = frota;
        this.cliente = cliente;
    }
    //#region Getters e Setters
    public Frota getFrota(){return this.frota;}
    public void setFrota(Frota frota){this.frota = frota;}
    public ClientePJ getCliente(){return this.cliente;}
    public void setCliente(ClientePJ cliente){this.cliente = cliente;}
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
               "\nFrota: " + getFrota() +
               "\nCliente: " + getCliente();
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
            anosPosFundacao = super.calculaIdade(cliente.getDataFundacao()),
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
    public void gerarSinistro(String endereco, Condutor condutor, Seguro seguro){
        getListaSinistros().add(new Sinistro(endereco, condutor, seguro));
    }
}
