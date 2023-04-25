import java.util.Date;

public class ClientePF extends Cliente{
    private final String cpf;
    private String genero;
    private Date dataLiscenca;
    private String educacao;
    private Date dataNascimento;
    private String classeEconomica;

    public ClientePF(String cpf){
        super(null, null);
        this.cpf = cpf;
    }

    public ClientePF(String nome, String endereco, Date dataLiscenca, String educacao, String genero,
                     String classeEconomica, String cpf, Date dataNascimento) {
        super(nome, endereco);
        this.cpf = cpf;
        this.genero = genero;
        this.dataLiscenca = dataLiscenca;
        this.educacao = educacao;
        this.dataNascimento = dataNascimento;
        this.classeEconomica = classeEconomica;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataLiscenca() {
        return this.dataLiscenca;
    }

    public void setDataLiscenca(Date dataLiscenca) {
        this.dataLiscenca = dataLiscenca;
    }

    public String getEducacao() {
        return this.educacao;
    }

    public void setEducacao(String educacao) {
        this.educacao = educacao;
    }

    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getClasseEconomica() {
        return this.classeEconomica;
    }

    public void setClasseEconomica(String classeEconomica) {
        this.classeEconomica = classeEconomica;
    }

    @Override
    public String toString() {
        return super.toString() +
            " cpf='" + getCpf() + "'" +
            ", genero='" + getGenero() + "'" +
            ", dataLiscenca='" + getDataLiscenca() + "'" +
            ", educacao='" + getEducacao() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", classeEconomica='" + getClasseEconomica() + "'" +
            "}";
    }

    public int digitoVerificador(String cpf, int digVerificador){
        int soma = 0, aux = 0, digito;

        if(digVerificador == 2)
            aux = 1;
        for(int i = 10 + aux; i >= 2; i--)
            soma += (cpf.charAt(10 + aux - i) - '0') * i;
        
        if((soma % 11 == 0) || (soma % 11 == 1))
            digito = 0;
        else
            digito = 11 - (soma % 11);
        
        return digito;
    }

    public boolean validarCPF(String cpf){
        int digito_1, digito_2;

        cpf = cpf.replaceAll("\\.|-", "");
        if(cpf.length() != 11)
            return false;
        
        digito_1 = digitoVerificador(cpf, 1);
        digito_2 = digitoVerificador(cpf, 2);
        if((digito_1 != cpf.charAt(9) - '0') || (digito_2 != cpf.charAt(10) - '0'))
            return false;

        return true;
    }

}
