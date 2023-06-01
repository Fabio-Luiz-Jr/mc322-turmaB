import java.util.Date;
import java.text.SimpleDateFormat;

public class Sinistro{
    private final int id;
    private static int id_aux = 0;
    private String data;
    private String endereco;
    private Condutor condutor;
    private Seguro seguro;

    public Sinistro(String endereco, Condutor condutor, Seguro seguro){
        id = id_aux;
        this.data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.endereco = endereco;
        this.condutor = condutor;
        this.seguro = seguro;
        id_aux++;
    }    
    //#region Getters e setters
    public int getId(){return this.id;}
    public String getData(){return this.data;}
    public void setData(String data){this.data = data;}
    public String getEndereco(){return this.endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}
    public Condutor getCondutor(){return condutor;}
    public void setCondutor(Condutor condutor){this.condutor = condutor;}
    public Seguro getSeguro(){return seguro;}
    public void setSeguro(Seguro seguro){this.seguro = seguro;}
    //#endregion
    @Override
    public String toString(){
        return
            "ID: " + getId() + 
            "\nData: " + getData() + 
            "\nEndereço: " + getEndereco() + 
            "\nCondutor: " + getCondutor().getNome() + 
            "\nSeguro: " + getSeguro().getId();
    }
}
