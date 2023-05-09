import java.util.Date;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;

    public ClientePJ(String cnpj){
        super(null, null);
        this.cnpj = cnpj;
    }

    public ClientePJ(String nome, String endereco, String cnpj, Date dataFundacao){
        super(nome, endereco);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }

    public String getCnpj(){
        return cnpj;
    }

    public Date getDataFundacao(){
        return dataFundacao;
    }

    public void setDataFundacao(Date dataFundacao){
        this.dataFundacao = dataFundacao;
    }

    @Override
    public String toString(){
        return super.toString() +
            " cnpj='" + getCnpj() + "'" +
            ", dataFundacao='" + getDataFundacao() + "'" +
            "}";
    }

    private int digitoVerificador(String cnpj, int digVerificador){
        int soma = 0, aux = 0, digito;

        //#region Converte cada caracter em int e aplica a fórmula adequada para calcular os digitos verificadores
        if(digVerificador == 1)
            aux = 1;
        for(int i = 5 + aux; i <= 9; i++)
            soma += (cnpj.charAt(i - 5 - aux) - '0') * i;
        for(int i = 2; i <= 9; i++)
            soma += (cnpj.charAt(3 - aux + i) - '0') * i;
        digito = soma % 11;
        //#endregion
        
        return digito;
    }

    public boolean validarCNPJ(String cnpj){
        int digito_1, digito_2;

        //Remove caracteres desnecessários
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
