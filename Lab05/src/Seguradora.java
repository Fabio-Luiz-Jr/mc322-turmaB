import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Seguradora{
    private final String cnpj;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Seguro> listaSeguros;

    public Seguradora(String cnpj, String nome, String telefone, String email, String endereco){
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.listaClientes = new ArrayList<Cliente>();
        this.listaSeguros = new ArrayList<Seguro>();
    }
    //#region Getters e setters
    public String getCnpj(){return this.cnpj;}
    public String getNome(){return this.nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getTelefone(){return this.telefone;}
    public void setTelefone(String telefone){this.telefone = telefone;}
    public String getEndereco(){return this.endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}
    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}
    public ArrayList<Cliente> getListaClientes(){return listaClientes;}
    public void setListaClientes(ArrayList<Cliente> listaClientes){this.listaClientes = listaClientes;}
    public ArrayList<Seguro> getListaSeguros(){return listaSeguros;}
    public void setListaSeguros(ArrayList<Seguro> listaSeguros){this.listaSeguros = listaSeguros;}
    public Cliente getCliente(String nome){
        for(int i = listarClientes("todos").size() - 1; i >= 0; i--)
            if(Objects.equals(listaClientes.get(i).getNome(), nome))
                return listaClientes.get(i);
        return null;
    }
    //#endregion
    @Override
    public String toString(){
        return "CNPJ: " + getCnpj() + 
               "\nNome: " + getNome() + 
               "\nTelefone: " + getTelefone() + 
               "\nEndereço: " + getEndereco() + 
               "\nEmail: " + getEmail() + 
               "\nLista de clientes: " + getListaClientes() + 
               "\nLista de seguros: " + getListaSeguros();
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

    public boolean gerarSeguro(Seguradora seguradora, Date dataInicio, Date dataFim, double valorMensal, Veiculo veiculo, ClientePF clientePF){
        if(!Validacao.validaCPF(clientePF.getCpf()))
            return false;
        this.listaSeguros.add(new SeguroPF(dataInicio, dataFim, seguradora, valorMensal, veiculo, clientePF));
        return true;
    }

    public boolean gerarSeguro(Seguradora seguradora, Date dataInicio, Date dataFim, double valorMensal, Frota frota, ClientePJ clientePJ){
        if(!Validacao.validaCNPJ(clientePJ.getCnpj()))
            return false;
        this.listaSeguros.add(new SeguroPJ(dataInicio, dataFim, seguradora, valorMensal, frota, clientePJ));
        return true;
    }

    public boolean cancelarSeguro(int id){
        for(Seguro s: listaSeguros)
            if(s.getId() == id){
                this.listaSeguros.remove(s);
                return true;
            }
        return false;
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
