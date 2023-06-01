import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Objects;

public class Seguradora{
    private final String cnpj;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private ArrayList<Sinistro> listaSinistros;
    private ArrayList<Cliente> listaClientes;

    public Seguradora(String cnpj, String nome, String telefone, String email, String endereco){
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.listaSinistros = new ArrayList<Sinistro>();
        this.listaClientes = new ArrayList<Cliente>();
    }
    //#region Getters e setters
    public String getCnpj(){return this.cnpj;}
    public String getNome(){return this.nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getTelefone(){return this.telefone;}
    public void setTelefone(String telefone){this.telefone = telefone;}
    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}
    public String getEndereco(){return this.endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}
    public ArrayList<Sinistro> getListaSinistros(){return listaSinistros;}
    public void setListaSinistros(Sinistro sinistro){this.listaSinistros.add(sinistro);}
    public ArrayList<Cliente> getListaClientes(){return listaClientes;}
    public void setListaClientes(ArrayList<Cliente> listaClientes){this.listaClientes = listaClientes;}
    //#endregion
    @Override
    public String toString(){
        return "CNPJ: " + getCnpj() + 
               "\nNome: " + getNome() + 
               "\nTelefone: " + getTelefone() + 
               "\nEmail: " + getEmail() + 
               "\nEndereço: " + getEndereco() + 
               "\nLista de sinistros: " + getListaSinistros() + 
               "\nLista de clientes: " + getListaClientes();
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

    public ArrayList<Cliente> listarClientes(String tipoCliente){
        ArrayList<Cliente> listaClientesFiltrada = new ArrayList<Cliente>();
        //#region Remove acentos e deixa todos os caracteres em minúsculo
        tipoCliente = Normalizer.normalize(tipoCliente, Normalizer.Form.NFD);
        tipoCliente = tipoCliente.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        //#endregion

        //instanceof verifica se uma variável pertence a certo Type
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

    public ArrayList<Cliente> listarClientes(){
        return listarClientes("todos");
    }

    public Cliente getCliente(String nome){
        for(int i = listarClientes("todos").size() - 1; i >= 0; i--)
            if(Objects.equals(listaClientes.get(i).getNome(), nome))
                return listaClientes.get(i);
        return null;
    }

}
