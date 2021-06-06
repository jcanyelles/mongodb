package es.cc.liceu.db.mongo.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import es.cc.liceu.db.mongo.dao.ProducteDao;
import es.cc.liceu.db.mongo.domain.Categoria;
import es.cc.liceu.db.mongo.domain.Producte;
import org.bson.BsonRegularExpression;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ProducteDaoImpl implements ProducteDao {
    private MongoDatabase database;

    public ProducteDaoImpl(MongoDatabase database) {
        this.database = database;
    }


    @Override
    public Producte carrega(Integer id) {
        return null;
    }

    @Override
    public Collection<Categoria> llistaCategories() {
        Collection<Categoria> resultat = new ArrayList<>();

        MongoCollection<Document> collection = database.getCollection("categories");
        FindIterable<Document> fi = collection.find();
        MongoCursor<Document> cursor = fi.iterator();
        System.out.println("Categories " + cursor.hasNext() + " " + fi.toString());

        while(cursor.hasNext()) {
            Document document  = cursor.next();
            Categoria categoria = new Categoria();
            categoria.setCodi(document.getDouble("codi").intValue());
            categoria.setNom(document.getString("nom"));
            categoria.setDescripcio(document.getString("descripcio"));
            resultat.add(categoria);
        }
        return resultat;
    }

    @Override
    public Collection<Producte> llistaProductes(Integer codi, String nom, String descripcio, String marca, Integer categoria) {
        Collection<Producte> resultat = new ArrayList<>();

        Document doc = new Document();
        if (codi!=null){
            doc.append("codi", codi);
        }
        if (nom!=null && !nom.isEmpty()){
            doc.append("nom", new BsonRegularExpression(nom));
        }
        if (descripcio!=null && !descripcio.isEmpty()){
            doc.append("descripcio", new BsonRegularExpression(descripcio));
        }
        if (marca!=null && !marca.isEmpty()){
            doc.append("marca", new BsonRegularExpression(marca));
        }
        if (categoria!=null ){
            doc.append("categoria", new Document("codi", categoria));
        }

        System.out.println("Filtrar: " + doc.toJson());

        MongoCollection<Document> collection = database.getCollection("producte");
        FindIterable<Document> fi = collection.find(doc);
        MongoCursor<Document> cursor = fi.iterator();
        while(cursor.hasNext()) {
            Document document  = cursor.next();
            Producte producte = new Producte();
            producte.setCodi(document.getDouble("codi").intValue());
            producte.setNom(document.getString("nom"));
            producte.setDescripcio(document.getString("descripcio"));
            producte.setMarca(document.getString("marca"));
            producte.setUnitat(document.getString("mesura"));
            producte.setPreu(document.getDouble("pvp").floatValue());
            producte.setIva(document.getDouble("iva").intValue());
            producte.setCodiCategoria(document.getInteger("categoria.codi"));
            resultat.add(producte);
        }
        return resultat;
    }
}
