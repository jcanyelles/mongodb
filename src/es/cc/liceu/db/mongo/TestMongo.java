package es.cc.liceu.db.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class TestMongo {
    static Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
    static {
        mongoLogger.setLevel(Level.SEVERE);
    }

    public static void main(String[] args) {
        Document document = new Document("codi" , 1)
                    .append("nom", "Nom producte")
                    .append("categoria", new Document("codi" , 1).append("nom", "Categoria 1"))
                    .append("tags", Arrays.asList("tag1, tag2, tag3"));
        System.out.println(document.toJson());


        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("limbo");
        llistaCategories(database);
        insertaCaregoria(database,100d, "Categoria prova", "Descipcio categoria");
        insertaCaregoria(database,101d, "Categoria prova1", "Descipcio categoria1");
        update(database,101d, "Categoria prova modificat", "Descipcio modificat");
        llistaCategoriesList(database);


    }

    public static void llistaCategories(MongoDatabase database){
        MongoCollection<Document> collection =  database.getCollection("categories");
        FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()){
            Document doc = cursor.next();
            System.out.println(doc.getString("nom") + " " + doc.getString("descripcio"));
        }
    }

    public static void llistaCategoriesList(MongoDatabase database){
        MongoCollection<Document> collection =  database.getCollection("categories");
        List<Document> categories = collection.find().into(new ArrayList<>());
        for (Document category : categories) {
            System.out.println(category.getDouble("codi") + " " + category.getString("nom") + " " + category.getString("descripcio"));
        }
    }
    public static void insertaCaregoria(MongoDatabase database, Double codi, String nom, String descripcio){
        Document doc = new Document("codi", codi).append("nom",nom).append("descripcio", descripcio);
        MongoCollection<Document> collection =  database.getCollection("categories");
        collection.insertOne(doc);
    }

    public static void update(MongoDatabase database, Double codi, String nom, String descripcio){
        Bson filter = eq("codi", codi);
        Document update = new Document("nom", nom).append("descripcio", descripcio);
        MongoCollection<Document> collection =  database.getCollection("categories");
        collection.updateOne(filter,new Document("$set", update));
    }

}
