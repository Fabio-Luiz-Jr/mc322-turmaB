import java.util.Date;

public class SeguroPJ extends Seguro{
    private Frota frota;
    private ClientePJ cliente;

    public SeguroPJ(int id, Date dataInicio, Date dataFim, Seguradora seguradora, int valorMensal, Frota frota, ClientePJ cliente) {
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
}
