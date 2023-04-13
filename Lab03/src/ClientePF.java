import java.util.Date;
import java.util.ArrayList;

public class ClientePF extends Cliente{
    private final String cpf;
    private Date dataNascimento;

    public ClientePF(String cpf){
        this.cpf = cpf;
    }

    public ClientePF(String nome, String endereco, Date dataLiscenca, String educacao, String genero,
                     String classeEconomica, String cpf, Date dataNascimento) {
        super(nome, endereco, dataLiscenca, educacao, genero, classeEconomica);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "{" +
            " cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
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
