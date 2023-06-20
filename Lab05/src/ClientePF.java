import java.util.*;

public class ClientePF extends Cliente{
    private final String cpf;
    private String genero;
    private String educacao;
    private Date dataNascimento;
    private ArrayList<Veiculo> listaVeiculos;

    public ClientePF(String nome, String telefone, String endereco, String email, String cpf, String genero,String educacao, Date dataNascimento){
        super(nome, telefone, endereco, email);
        this.cpf = cpf;
        this.genero = genero;
        this.educacao = educacao;
        this.dataNascimento = dataNascimento;
        this.listaVeiculos = new ArrayList<Veiculo>();
    }
    //#region Getters e setters
    public String getCpf(){return this.cpf;}
    public String getGenero(){return this.genero;}
    public void setGenero(String genero){this.genero = genero;}
    public String getEducacao(){return this.educacao;}
    public void setEducacao(String educacao){this.educacao = educacao;}
    public Date getDataNascimento(){return this.dataNascimento;}
    public void setDataNascimento(Date dataNascimento){this.dataNascimento = dataNascimento;}
    public ArrayList<Veiculo> getListaVeiculos(){return listaVeiculos;}
    public void setListaVeiculos(ArrayList<Veiculo> listaVeiculos){this.listaVeiculos = listaVeiculos;}
    public Veiculo getVeiculo(String placa){
        for(Veiculo v: this.listaVeiculos)
            if(Objects.equals(v.getPlaca(), placa))
                return v;
        return null;
    }
    //#endregion
    @Override
    public String toString(){
        return super.toString() +
            "\nCPF: " + getCpf() + 
            "\nGênero: " + getGenero() + 
            "\nEducação: " + getEducacao() + 
            "\nData de nascimento: " + getDataNascimento() + 
            "\nLista de veículos: " + getListaVeiculos();
    }

    public boolean cadastrarVeiculo(Veiculo veiculo){
        if(getListaVeiculos().contains(veiculo))
            return false;
        this.listaVeiculos.add(veiculo);
        return true;
    }

    public boolean removerVeiculo(String placa){
        for(int i = 0; i < getListaVeiculos().size(); i++)
            if(Objects.equals(this.listaVeiculos.get(i).getPlaca(), placa)){
                this.listaVeiculos.remove(i);
                return true;
            }
        return false;
    }
}
