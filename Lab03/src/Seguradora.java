import java.text.Normalizer;
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

    public ArrayList<Cliente> listarClientes(String tipoCliente) {
        ArrayList<Cliente> listaClientesFiltrada = new ArrayList<Cliente>();
        tipoCliente = Normalizer.normalize(tipoCliente, Normalizer.Form.NFD);
        tipoCliente = tipoCliente.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        if(Objects.equals(tipoCliente, "fisica")){
            for(Cliente c1: listaClientes)
                if(c1 instanceof ClientePF)
                    listaClientesFiltrada.add(c1);
        }else if(Objects.equals(tipoCliente, "juridica")){
            for(Cliente c2: listaClientes)
                    if(c2 instanceof ClientePJ)
                        listaClientesFiltrada.add(c2);
        }else
            for(Cliente c3: listaClientes)
                listaClientesFiltrada.add(c3);
        return listaClientesFiltrada;
    }

    public boolean gerarSinistro(String endereco, Seguradora seguradora, Veiculo veiculo, String cliente){
        for(Cliente c: seguradora.listaClientes)
            if(Objects.equals(c.getNome(), cliente)){
                this.listaSinistros.add(new Sinistro(endereco, seguradora, veiculo, c));
                return true;
            }
        return false;
    }

    public void setSinistro(Sinistro sinistro){
        this.listaSinistros.add(sinistro);
    }

    public boolean visualizarSinistro(String cliente){
        for(Sinistro s: listaSinistros)
            if(Objects.equals(s.getCliente().getNome(), cliente)){
                System.out.println(s);
                return true;
            }
        return false;
    }

    public ArrayList<Sinistro> listarSinistros() {
        return listaSinistros;
    }

}