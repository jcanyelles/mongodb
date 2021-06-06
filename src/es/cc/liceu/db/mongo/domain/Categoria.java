package es.cc.liceu.db.mongo.domain;

public class Categoria {
    private Integer codi;
    private String nom;
    private String descripcio;

    public Categoria() {
    }

    public Categoria(Integer codi) {
        this.codi = codi;
    }

    public Integer getCodi() {
        return codi;
    }

    public void setCodi(Integer codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

}
