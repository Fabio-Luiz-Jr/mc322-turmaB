import java.text.Normalizer;
import java.util.*;

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
    public ArrayList<Cliente> getListaClientes(){return this.listaClientes;}
    public void setListaClientes(ArrayList<Cliente> listaClientes){this.listaClientes = listaClientes;}
    public ArrayList<Seguro> getListaSeguros(){return this.listaSeguros;}
    public void setListaSeguros(ArrayList<Seguro> listaSeguros){this.listaSeguros = listaSeguros;}
    public Cliente getCliente(String cpf_cnpj){
        for(int i = listaClientes.size() - 1; i >= 0; i--)
            if(Objects.equals(((listaClientes.get(i) instanceof ClientePF) ? (((ClientePF)listaClientes.get(i)).getCpf()) : (((ClientePJ)listaClientes.get(i)).getCnpj())), cpf_cnpj))
                return this.listaClientes.get(i);
        return null;
    }
    //#endregion
    @Override
    public String toString(){
        StringBuilder listaClientes = new StringBuilder(),
                      listaSeguros = new StringBuilder();
        for(Cliente c: this.listaClientes){
            listaClientes.append(c.getNome() + " | ");
            listaClientes.append((c instanceof ClientePF) ? "CPF: " + ((ClientePF)c).getCpf() : "CNPJ: " + ((ClientePJ)c).getCnpj());
            listaClientes.append("\n                   ");
        }
        for(Seguro s: this.listaSeguros){
            listaSeguros.append("ID: " + s.getId() + "\n                    Cliente: ");
            if(s instanceof SeguroPF){
                listaSeguros.append(((SeguroPF)s).getClientePF().getNome() + " | CPF: " + ((SeguroPF)s).getClientePF().getCpf());
                listaSeguros.append("\n                    Veículo: " + ((SeguroPF)s).getVeiculo());
            }else{
                listaSeguros.append(((SeguroPJ)s).getClientePJ().getNome() + " | CNPJ: " + ((SeguroPJ)s).getClientePJ().getCnpj());
                listaSeguros.append("\n                    Frota: " + ((SeguroPJ)s).getFrota().getCode());
            }
        }
        if(listaClientes.length() == 0)
            listaClientes.append("Nenhum cliente encontrado\n");
        if(listaSeguros.length() == 0)
            listaSeguros.append("Nenhum seguro encontrado");
        return "CNPJ: " + cnpj + 
               "\nNome: " + nome + 
               "\nTelefone: " + telefone + 
               "\nEndereço: " + endereco + 
               "\nEmail: " + email + 
               "\nLista de clientes: " + listaClientes + 
               "Lista de seguros: " + listaSeguros;
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

    public boolean gerarSeguro(Seguradora seguradora, Date dataInicio, Date dataFim, Veiculo veiculo, ClientePF clientePF){
        for(Seguro s: listaSeguros)
            if(s instanceof SeguroPF && Objects.equals(((SeguroPF)s).getVeiculo(), veiculo))
                return false;
        this.listaSeguros.add(new SeguroPF(dataInicio, dataFim, seguradora, veiculo, clientePF));
        return true;
    }

    public boolean gerarSeguro(Seguradora seguradora, Date dataInicio, Date dataFim, Frota frota, ClientePJ clientePJ){
        for(Seguro s: listaSeguros)
            if(s instanceof SeguroPJ && Objects.equals(((SeguroPJ)s).getFrota(), frota))
                return false;
        this.listaSeguros.add(new SeguroPJ(dataInicio, dataFim, seguradora, frota, clientePJ));
        return true;
    }

    public Seguro getSeguro(int id){
        for(int i = 0; i < listaSeguros.size(); i++)    
            if(listaSeguros.get(i).getId() == id)
                return this.listaSeguros.get(i);
        return null;
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

    public boolean removerCliente(String cpf_cnpj){
        int index = 0;
        for(Seguro s: this.listaSeguros)
            if((Objects.equals(((SeguroPF)s).getClientePF(), getCliente(cpf_cnpj)) || Objects.equals(((SeguroPJ)s).getClientePJ(), getCliente(cpf_cnpj))))
                s.getListaSinistros().removeAll(s.getListaSinistros());//Deleta lista de sinistros do cliente
        this.listaSeguros.removeIf(seguro -> (Objects.equals(((SeguroPF)seguro).getClientePF(), getCliente(cpf_cnpj)) || Objects.equals(((SeguroPJ)seguro).getClientePJ(), getCliente(cpf_cnpj))));//Deleta seguros do cliente
        for(Cliente c: listaClientes){
            if((c instanceof ClientePF && Objects.equals(((ClientePF)c).getCpf(), cpf_cnpj)) || (c instanceof ClientePJ && Objects.equals(((ClientePJ)c).getCnpj(), cpf_cnpj))){
                this.listaClientes.remove(index);//Deleta cliente
                return true;
            }
            index++;
        }
        return false;
    }

    public ArrayList<Seguro> getSegurosPorCliente(String cpf_cnpj){
        ArrayList<Seguro> listaSegurosPorCliente = new ArrayList<Seguro>();
        for(Seguro s: this.listaSeguros)
            if(cpf_cnpj.length() == 14) if((s instanceof SeguroPF) && (Objects.equals(((SeguroPF)s).getClientePF().getCpf(), cpf_cnpj)))
                listaSegurosPorCliente.add(s);
            else if((s instanceof SeguroPJ) && (Objects.equals(((SeguroPJ)s).getClientePJ().getCnpj(), cpf_cnpj)))
                listaSegurosPorCliente.add(s);
        return listaSegurosPorCliente;
    }

    public Condutor getCondutor(String cpf_cnpj, String cpf, int id){
        for(Seguro s: getSegurosPorCliente(cpf_cnpj))
            for(Condutor c: s.getListaCondutores())
                if(Objects.equals(c.getCpf(), cpf)){
                    id = s.getId();
                    return c;
                }
        return null;
    }

    public ArrayList<Sinistro> getSinistrosPorCliente(String cpf_cnpj){
        ArrayList<Sinistro> listaSinistrosPorCliente = new ArrayList<Sinistro>();
        for(Seguro seguro: getSegurosPorCliente(cpf_cnpj))
            for(Sinistro sinistro: seguro.getListaSinistros())
                listaSinistrosPorCliente.add(sinistro);
        return listaSinistrosPorCliente;
    }

    public double calcularReceita(){
        double receita = 0;
        for(Seguro seguro: listaSeguros)
            receita += seguro.getValorMensal();
        return receita;
    }
}
