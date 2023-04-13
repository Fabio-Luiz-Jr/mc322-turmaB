import java.util.ArrayList;
import java.util.Objects;

public class Seguradora{
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private ArrayList<Sinistro> listaSinistros;
    private ArrayList<Cliente> listaClientes;


    public Seguradora(String nome, String telefone, String email, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.listaSinistros = new ArrayList<Sinistro>();
        this.listaClientes = new ArrayList<Cliente>();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Sinistro> getListaSinistros() {
        return listaSinistros;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public boolean cadastrarCliente(Cliente cliente){
        if(listaClientes.contains(cliente))
            return false;
        this.listaClientes.add(cliente);
        return true;
    }

    public boolean removerCliente(String cliente){
        int index = 0;
        for(Cliente c: listaClientes){
            if(Objects.equals(c.getNome(), cliente)){
                listaClientes.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }
}