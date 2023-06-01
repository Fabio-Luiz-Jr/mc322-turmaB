import java.util.Date;

public class SeguroPF extends Seguro{
    private Veiculo veiculo;
    private ClientePF cliente;

    public SeguroPF(int id, Date dataInicio, Date dataFim, Seguradora seguradora, int valorMensal, Veiculo veiculo, ClientePF cliente){
        super(id, dataInicio, dataFim, seguradora, valorMensal);
        this.veiculo = veiculo;
        this.cliente = cliente;
    }
    //#region Getters e Setters
    public Veiculo getVeiculo(){return this.veiculo;}
    public void setVeiculo(Veiculo veiculo){this.veiculo = veiculo;}
    public ClientePF getCliente(){return this.cliente;}
    public void setCliente(ClientePF cliente){this.cliente = cliente;}
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
               "\nVeículo: " + getVeiculo() +
               "\nCliente: " + getCliente();
    }

    @Override
    public void desautorizarCondutor(){
        
    }

    @Override
    public void autorizarCondutor(){
        
    }

    @Override
    public void calcularValor(){
        double fator_idade = super.calculaIdade(getCliente().getDataNascimento()) < 30 ? CalcSeguro.FATOR_18_30.getValor() : super.calculaIdade(getCliente().getDataNascimento()) > 60 ? CalcSeguro.FATOR_60_90.getValor() : CalcSeguro.FATOR_30_60.getValor();
        int quantidadeVeiculos = cliente.getListaVeiculos().size(),
            quantidadeSinistrosCliente = getListaSinistros().size(),
            quantidadeSinistrosCondutor = 0;

        for(Condutor c: getListaCondutores()) quantidadeSinistrosCondutor += c.getListaSinistros().size();
        
        setValorMensal(CalcSeguro.VALOR_BASE.getValor() * fator_idade
                                                        * (1 + 1 / (quantidadeVeiculos + 2))
                                                        * (2 + quantidadeSinistrosCliente / 10.0)
                                                        * (5 + quantidadeSinistrosCondutor / 10.0));
    }

    @Override
    public void gerarSinistro(){
        
    }
}
