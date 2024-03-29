import java.util.Date;

public class ClientePJ extends Cliente{
    private final String cnpj;
    private Date dataFundacao;
    private int qtdeFuncionarios;

    public ClientePJ(String nome, String endereco, String cnpj, Date dataFundacao, int qtdeFuncionarios){
        super(nome, endereco);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
        this.qtdeFuncionarios = qtdeFuncionarios;
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

    public int getQtdeFuncionarios(){
        return qtdeFuncionarios;
    }

    public void setQtdeFuncionarios(int qtdeFuncionarios) {
        this.qtdeFuncionarios = qtdeFuncionarios;
    }

    @Override
    public String toString(){
        return super.toString() +
            "CNPJ: " + getCnpj() + "\n" +
            "Data da fundação" + getDataFundacao() + "\n" +
            "Quantidade de funcionários: " + getQtdeFuncionarios() + "\n";
    }

    public double calculaScore(){
        return CalcSeguro.VALOR_BASE.getValor() * (1 + (qtdeFuncionarios / 100)) * getListaVeiculos().size();
    }
}
