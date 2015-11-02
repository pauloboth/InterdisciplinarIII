package model;

import java.util.Date;
import java.util.List;

public class ProdutoCusto {

    private Produto produto;
    private Custo custo;
    private Maquina maquina;
    private Despesa despesa;
    private DespesaMes despesames;
    private CustoDespesa custodespesa;
    private int total;
    private int participacao;
    private String notas;
    private int tempo;
    private double valor;
    private double valor_total;
    private double media_ano;
    private double media_total;
    private double valor_unitario;
    private List<CustoDespesa> lsCustoDespsa;
    
    
    private Date anodespesa;

    public ProdutoCusto() {
    }

    public int getTotal() {
        if (total == 0 && this.produto != null && this.produto.getLsProdutoProducao() != null) {
            for (ProdutoProducao pp : this.produto.getLsProdutoProducao()) {
                total += pp.getPrp_quantidade();
            }
        }
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getParticipacao() {
        return participacao;
    }

    public void setParticipacao(int participacao) {
        this.participacao = participacao;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Custo getCusto() {
        return custo;
    }

    public void setCusto(Custo custo) {
        this.custo = custo;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public DespesaMes getDespesames() {
        return despesames;
    }

    public void setDespesames(DespesaMes despesames) {
        this.despesames = despesames;
    }

    public CustoDespesa getCustodespesa() {
        return custodespesa;
    }

    public void setCustodespesa(CustoDespesa custodespesa) {
        this.custodespesa = custodespesa;
    }

    public List<CustoDespesa> getLsCustoDespsa() {
        return lsCustoDespsa;
    }

    public void setLsCustoDespsa(List<CustoDespesa> lsCustoDespsa) {
        this.lsCustoDespsa = lsCustoDespsa;
    }

    public Date getAnodespesa() {
        return anodespesa;
    }

    public void setAnodespesa(Date anodespesa) {
        this.anodespesa = anodespesa;
    }

    public double getMedia_ano() {
        return media_ano;
    }

    public void setMedia_ano(double media_ano) {
        this.media_ano = media_ano;
    }

    public double getMedia_total() {
        return media_total;
    }

    public void setMedia_total(double media_total) {
        this.media_total = media_total;
    }

}
