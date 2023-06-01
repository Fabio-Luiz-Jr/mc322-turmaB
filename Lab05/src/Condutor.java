import java.util.ArrayList;
import java.util.Date;

public class Condutor{
    private final int cpf;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private Date dataNascimento;
    private ArrayList<Sinistro> listaSinistros;

    public Condutor(int cpf, String nome, String telefone, String endereco, String email, Date dataNascimento){
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.listaSinistros = new ArrayList<Sinistro>();
    }
    //#region Getters e Setters
    public int getCpf(){return cpf;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getTelefone(){return telefone;}
    public void setTelefone(String telefone){this.telefone = telefone;}
    public String getEndereco(){return endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public Date getDataNascimento(){return dataNascimento;}
    public void setDataNascimento(Date dataNascimento){this.dataNascimento = dataNascimento;}
    public ArrayList<Sinistro> getListaSinistros(){return listaSinistros;}
    public void setListaSinistros(ArrayList<Sinistro> listaSinistros){this.listaSinistros = listaSinistros;}
    //#endregion
    @Override
    public String toString(){
        return "CPF: " + cpf +
               "\nNome: " + nome + 
               "\nTelefone: " + telefone + 
               "\nEndereço: " + endereco + 
               "\nEmail: " + email + 
               "\nData de nascimento: " + dataNascimento + 
               "\nLista de sinistros: " + listaSinistros;
    }
    
    public void adicionarSinistro(Sinistro sinistro){
        this.listaSinistros.add(sinistro);
    }
}