package es.cc.liceu.db.mongo.domain;

import java.util.Objects;

public class Producte {
    private Integer codi;
    private String  nom;
    private String  descripcio;
    private Float   preu;
    private Integer iva;
    private String  marca;
    private String  unitat;
    private Float   pes;
    private Integer codiCategoria;

    public Producte() {
    }

    public Producte(Integer codi) {
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

    public Float getPreu() {
        return preu;
    }

    public void setPreu(Float preu) {
        this.preu = preu;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnitat() {
        return unitat;
    }

    public void setUnitat(String unitat) {
        this.unitat = unitat;
    }

    public Float getPes() {
        return pes;
    }

    public void setPes(Float pes) {
        this.pes = pes;
    }

    public Integer getCodiCategoria() {
        return codiCategoria;
    }

    public void setCodiCategoria(Integer codiCategoria) {
        this.codiCategoria = codiCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producte producte = (Producte) o;
        return Objects.equals(codi, producte.codi);
    }

    @Override
    public int hashCode() {

        return Objects.hash(codi);
    }

    @Override
    public String toString() {
        return "Producte{" +
                "id=" + codi +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                '}';
    }
}

