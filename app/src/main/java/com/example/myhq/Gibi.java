package com.example.myhq;

public class Gibi {
    private int id, numero, imagem;
    private String titulo, editora,serie;

    public Gibi() {

    }

    public Gibi(int numero, int imagem, String titulo, String editora, String serie) {
        this.numero = numero;
        this.imagem = imagem;
        this.titulo = titulo;
        this.editora = editora;
        this.serie = serie;
    }

    public Gibi(String titulo, String serie) {
        this.titulo = titulo;
        this.serie = serie;

    }

    public String toString(){
        return this.titulo + " | Nro.: "+this.getNumero();
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

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
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
}
