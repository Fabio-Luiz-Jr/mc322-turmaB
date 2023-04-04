public class Seguradora{
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private Sinistro listaSinistros;
    private Cliente listaClientes;

    public Seguradora(String nome, String telefone, String email, String endereco, Sinistro listaSinistros,
                      Cliente listaClientes) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.listaSinistros = listaSinistros;
        this.listaClientes = listaClientes;
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

    public Sinistro getListaSinistros() {
        return this.listaSinistros;
    }

    public void setListaSinistros(Sinistro listaSinistros) {
        this.listaSinistros = listaSinistros;
    }

    public Cliente getListaClientes() {
        return this.listaClientes;
    }

    public void setListaClientes(Cliente listaClientes) {
        this.listaClientes = listaClientes;
    }

    @Override
    public String toString() {
        return "{" +
            " nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", listaSinistros[]='" + getListaSinistros() + "'" +
            ", listaClientes[]='" + getListaClientes() + "'" +
            ", getListaSinistros[]='" + getListaSinistros() + "'" +
            ", listaSinistros[]='" + getListaSinistros() + "'" +
            ", getListaClientes[]='" + getListaClientes() + "'" +
            ", listaClientes[]='" + getListaClientes() + "'" +
            "}";
    }

}