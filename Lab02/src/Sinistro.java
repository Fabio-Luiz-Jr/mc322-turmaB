public class Sinistro{
    private int id;
    private static int id_aux = 0;
    private String data;
    private String endereco;

    public Sinistro(String data, String endereco){
        id = id_aux;
        this.data = data;
        this.endereco = endereco;
        id_aux++;
    }

    public int getId(){
        return id;
    }

    public void setId(){
        id = id_aux;
        id_aux++;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    
}
