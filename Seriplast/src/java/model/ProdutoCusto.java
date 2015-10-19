package model;

public class ProdutoCusto {

    private Produto produto;
    private Custo custo;
    private int total;
    private int participacao;
    private String notas;

    public ProdutoCusto() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getTotal() {
        if (total == 0 && this.produto != null && this.produto.getLsProdutoPedido() != null) {
            for (ProdutoPedido pp : this.produto.getLsProdutoPedido()) {
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

    public Custo getCusto() {
        return custo;
    }

    public void setCusto(Custo custo) {
        this.custo = custo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

}
