package es.cc.liceu.db.mongo.dao;

import es.cc.liceu.db.mongo.domain.Categoria;
import es.cc.liceu.db.mongo.domain.Producte;

import java.util.Collection;

public interface ProducteDao {
    Producte carrega(Integer id);
    Collection<Categoria> llistaCategories();
    Collection<Producte> llistaProductes(Integer id, String nom, String descripcio, String marca, Integer categoria);
}
