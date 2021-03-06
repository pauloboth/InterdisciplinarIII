package model;

import java.util.Date;
import java.util.List;

public class ProdutoCusto {
    private String nomedespesa;

   
    private int mess;
    private String messtring;
    private Produto produto;
    private Custo custo;
    private Maquina maquina;
    private Despesa despesa;
    private DespesaMes despesames;
    private CustoDespesa custodespesa;
    private int total;
    private int quantidade_produzida_mes;
    private int quantidade_produzida_ano;
    private int quantidade_produzida_total;
    private int participacao;
    private String notas;
    private int tempo;
    private double valor;
    private double valor_mes;
    private double valor_mes_sp;
    private double valor_total;
    private double media_ano;
    private double media_total;
    private double valor_unitario;
    private List<CustoDespesa> lsCustoDespsa;
    private double custo_compra_total;
    private double custo_compra_unidade;
    
    
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
     public String getNomedespesa() {
        return nomedespesa;
    }

    public void setNomedespesa(String nomedespesa) {
        this.nomedespesa = nomedespesa;
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

 

    public int getQuantidade_produzida_mes() {
        return quantidade_produzida_mes;
    }

    public void setQuantidade_produzida_mes(int quantidade_produzida_mes) {
        this.quantidade_produzida_mes = quantidade_produzida_mes;
    }

    public int getQuantidade_produzida_ano() {
        return quantidade_produzida_ano;
    }

    public void setQuantidade_produzida_ano(int quantidade_produzida_ano) {
        this.quantidade_produzida_ano = quantidade_produzida_ano;
    }

    public int getQuantidade_produzida_total() {
        return quantidade_produzida_total;
    }

    public void setQuantidade_produzida_total(int quantidade_produzida_total) {
        this.quantidade_produzida_total = quantidade_produzida_total;
    }

    public double getValor_mes() {
        return valor_mes;
    }

    public void setValor_mes(double valor_mes) {
        this.valor_mes = valor_mes;
    }

    public double getCusto_compra_total() {
        return custo_compra_total;
    }

    public void setCusto_compra_total(double custo_compra_total) {
        this.custo_compra_total = custo_compra_total;
    }

    public double getCusto_compra_unidade() {
        return custo_compra_unidade;
    }

    public void setCusto_compra_unidade(double custo_compra_unidade) {
        this.custo_compra_unidade = custo_compra_unidade;
    }

    public double getValor_mes_sp() {
        return valor_mes_sp;
    }

    public void setValor_mes_sp(double valor_mes_sp) {
        this.valor_mes_sp = valor_mes_sp;
    }

    public int getMess() {
        return mess;
    }

    public void setMess(int mess) {
        this.mess = mess;
    }

    public String getMesstring() {
        return messtring;
    }

    public void setMesstring(String messtring) {
        this.messtring = messtring;
    }

   

}
