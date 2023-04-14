import java.util.Date;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;

    public ClientePJ(String cnpj){
        this.cnpj = cnpj;
    }

    public ClientePJ(String nome, String endereco, String cnpj, Date dataFundacao) {
        super(nome, endereco);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Date getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(Date dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    @Override
    public String toString() {
        return "{" +
            " cnpj='" + getCnpj() + "'" +
            ", dataFundacao='" + getDataFundacao() + "'" +
            "}";
    }

    public int digitoVerificador(String cpf, int digVerificador){
        int soma = 0, aux = 0, digito;

        if(digVerificador == 2)
            aux = 1;
        for(int i = 5; i >= 2; i--)
            soma += (cpf.charAt(5 - i) - '0') * i;
        for(int i = 9 + aux; i >= 2; i--)
            soma += (cpf.charAt(14 + aux - i) - '0') * i;
        
        if(soma % 11 <= 1)
            digito = 0;
        else
            digito = 11 - (soma % 11);
        
        return digito;
    }

    public boolean validarCNPJ(String cnpj){
        int digito_1, digito_2;

        cnpj = cnpj.replaceAll("\\.|-|/", "");
        if(cnpj.length() != 14)
            return false;
        
        digito_1 = digitoVerificador(cnpj, 1);
        digito_2 = digitoVerificador(cnpj, 2);
        if((digito_1 != cnpj.charAt(12) - '0') || (digito_2 != cnpj.charAt(13) - '0'))
            return false;

        return true;
    }
}
