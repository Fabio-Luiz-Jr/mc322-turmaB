import java.util.Date;
import java.text.SimpleDateFormat;

public class Sinistro{
    private final int id;
    private static int id_aux = 0;
    private String data;
    private String endereco;
    private Seguradora seguradora;
    private Veiculo veiculo;
    private Cliente cliente;

    public Sinistro(String endereco, Seguradora seguradora, Veiculo veiculo, Cliente cliente){
        id = id_aux;
        this.data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.endereco = endereco;
        this.seguradora = seguradora;
        this.veiculo = veiculo;
        this.cliente = cliente;
        id_aux++;
    }    

    public int getId(){
        return this.id;
    }


    public String getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getEndereco(){
        return this.endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public Seguradora getSeguradora(){
        return this.seguradora;
    }

    public void setSeguradora(Seguradora seguradora){
        this.seguradora = seguradora;
    }

    public Veiculo getVeiculo(){
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo){
        this.veiculo = veiculo;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    @Override
    public String toString(){
        return
            "-----------------------------\n" + 
            "ID: " + getId() + ";\n" +
            "Data: " + getData() + ";\n" +
            "Endereço: " + getEndereco() + ";\n" +
            "Seguradora: " + getSeguradora().getNome() + ";\n" +
            "Veículo: " + getVeiculo() + ";\n" +
            "Cliente: " + getCliente() + ".";
    }
}
