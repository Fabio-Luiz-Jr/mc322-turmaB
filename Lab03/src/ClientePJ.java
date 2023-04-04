import java.sql.Date;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;

    public ClientePJ(String nome, String endereco, Date dataLiscenca, String educacao, String genero,
                     String classeEconomica, Veiculo listaVeiculos, String cnpj, Date dataFundacao) {
        super(nome, endereco, dataLiscenca, educacao, genero, classeEconomica, listaVeiculos);
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
    
}
