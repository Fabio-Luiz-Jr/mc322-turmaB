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
}
