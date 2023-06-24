import java.util.*;

public class SeguroPF extends Seguro{
    private Veiculo veiculo;
    private ClientePF clientePF;

    public SeguroPF(Date dataInicio, Date dataFim, Seguradora seguradora, Veiculo veiculo, ClientePF clientePF){
        super(dataInicio, dataFim, seguradora);
        this.veiculo = veiculo;
        this.clientePF = clientePF;
        setValorMensal(calcularValor());
    }
    //#region Getters e Setters
    public Veiculo getVeiculo(){return this.veiculo;}
    public void setVeiculo(Veiculo veiculo){this.veiculo = veiculo;}
    public ClientePF getClientePF(){return this.clientePF;}
    public void setClientePF(ClientePF clientePF){this.clientePF = clientePF;}
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
               "\nVeículo: " + veiculo;
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
        double fator_idade = super.calculaIdade(getClientePF().getDataNascimento()) < 30 ? CalcSeguro.FATOR_18_30.getValor() : super.calculaIdade(getClientePF().getDataNascimento()) > 60 ? CalcSeguro.FATOR_60_90.getValor() : CalcSeguro.FATOR_30_60.getValor();
        int quantidadeVeiculos = clientePF.getListaVeiculos().size(),
            quantidadeSinistrosCliente = getListaSinistros().size(),
            quantidadeSinistrosCondutor = 0;

        for(Condutor c: getListaCondutores()) quantidadeSinistrosCondutor += c.getListaSinistros().size();
        
        return CalcSeguro.VALOR_BASE.getValor() * fator_idade
                                                * (1 + 1 / (quantidadeVeiculos + 2))
                                                * (2 + quantidadeSinistrosCliente / 10.0)
                                                * (5 + quantidadeSinistrosCondutor / 10.0);
    }

    @Override
    public boolean gerarSinistro(String endereco, String cpf, Seguro seguro){
        for(Condutor condutor: seguro.getListaCondutores())
            if(Objects.equals(condutor.getCpf().replaceAll("\\.|-", ""), cpf.replaceAll("\\.|-", ""))){
                getListaSinistros().add(new Sinistro(endereco, condutor, seguro));
                setValorMensal(calcularValor());
                return true;
            }
        return false;
    }
}
