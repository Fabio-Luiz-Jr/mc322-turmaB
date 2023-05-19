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

    public Seguradora(){}

    public Seguradora(String nome, String telefone, String email, String endereco){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.listaSinistros = new ArrayList<Sinistro>();
        this.listaClientes = new ArrayList<Cliente>();
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTelefone(){
        return this.telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEndereco(){
        return this.endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
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

    public ArrayList<Sinistro> listarSinistros(){
        return listaSinistros;
    }

    public boolean deletarSinistro(int id){
        for(Sinistro s: listarSinistros())
            if(s.getId() == id){
                this.listaSinistros.remove(s);
                return true;
            }
        return false;
    }

    public ArrayList<Sinistro> listarSinistros(Cliente cliente){
        ArrayList<Sinistro> lista = new ArrayList<Sinistro>();
        if(listaSinistros != null)
            for(Sinistro s: listaSinistros)
                if(Objects.equals(s.getCliente().getNome(), cliente.getNome()))
                    lista.add(s);
        return lista;
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

    public Cliente getCliente(String nome){
        for(int i = listarClientes("todos").size() - 1; i >= 0; i--)
            if(Objects.equals(listaClientes.get(i).getNome(), nome))
                return listaClientes.get(i);
        return null;
    }

    public boolean gerarSinistro(String endereco, Seguradora seguradora, Veiculo veiculo, String cliente){
        //Procura o cliente
        for(Cliente c: seguradora.listaClientes)
            if(Objects.equals(c.getNome(), cliente)){
                //Usa o construtor do Sinistro
                this.listaSinistros.add(new Sinistro(endereco, seguradora, veiculo, c));
                return true;
            }
        return false;
    }

    public double calcularPrecoSeguroCliente(Cliente cliente){
        return cliente.calculaScore() * (1 + listarSinistros(cliente).size());
    }

    public double calcularReceita(){
        double soma = 0;
        for(Cliente c: listarClientes("todos"))
            soma += calcularPrecoSeguroCliente(c);
        return soma;
    }

    public void transferenciaSeguro(Cliente cliente1, Cliente cliente2){
        for(int i = cliente1.getListaVeiculos().size() - 1; i >= 0; i--){
            cliente2.addVeiculo(cliente1.getListaVeiculos().get(i));
            cliente1.removeVeiculo(i);
        }

        for(int i = 0; i < listaSinistros.size(); i++)
            if(Objects.equals(listaSinistros.get(i).getCliente(), cliente1))
                listaSinistros.get(i).setCliente(cliente1);
        
        cliente1.setValorSeguro(calcularPrecoSeguroCliente(cliente1));
        cliente2.setValorSeguro(calcularPrecoSeguroCliente(cliente2));
    }
}
