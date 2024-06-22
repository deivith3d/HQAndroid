package com.example.myhq;

public class Gibi {
    private int id, numero;
    private String titulo, editora,serie,imagem,ano;

    private int adquirido;

    public Gibi() {

    }

    public Gibi(int numero, String imagem, String titulo, String editora, String serie, String ano, int adquirido) {
        this.numero = numero;
        this.imagem = imagem;
        this.titulo = titulo;
        this.editora = editora;
        this.serie = serie;
        this.ano = ano;
        this.adquirido = adquirido;
    }

    public Gibi(String titulo, String serie) {
        this.titulo = titulo;
        this.serie = serie;

    }

    public String toString(){
        return this.titulo + " | Nro.: "+this.getNumero()
                + " | Ed.: "+this.getEditora()
                + " | Sr.: "+this.getSerie()
                + " | An.: "+this.getAno()
                + " | Im.: "+this.getImagem()
                + " | Ad.: "+this.getAdquirido();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAno(){return this.ano;}

    public void setAdquirido(int adquirido) {
        this.adquirido = adquirido;
    }

    public int getAdquirido(){return this.adquirido;}


}
