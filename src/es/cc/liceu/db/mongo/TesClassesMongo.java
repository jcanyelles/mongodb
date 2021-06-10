package es.cc.liceu.db.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;

public class TesClassesMongo {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost",27017);

        MongoDatabase baseDades = client.getDatabase("test");

        MongoCollection<Document> collection = baseDades.getCollection("inventory");

        System.out.println("Documents de inventory: "+ collection.countDocuments());
        //{item: "Exemple Array", status : "A", qty : 12, tags : ["Tag1", "Tag2", "Tag3"] }
        Document exempleArray  = new Document()
                .append("item" , "Exemple Array")
                .append("status", "A")
                .append("qty", 12)
                .append("tags", Arrays.asList("Tag1", "Tag3", "Tag2"));

        //SELECT * FROM inventory
        FindIterable<Document> iterador = collection.find();
        for (Document document : iterador) {
            Number quantitat = (Number)document.get("qty");
            System.out.println(
                    document.getString("item") + " " +
                            quantitat.intValue() + " " +
                            document.getString("status")
            );

        }
        System.out.println(" **** inseratm nou item");
        /*
        * {item  : "Pencil",
        *  qty : 120, status : "A",
        *  size : {h : 8.5, w:10.5, uom : "cm"}
        * }
        * */
        Document jsonParsetjat = Document.parse("{item : 'Boligaf' , status : ' D'}");

        Document nouItem = new Document()
                .append("item", "Pencil")
                .append("qty", 120)
                .append("status", "A")
                .append("size",
                        new Document("h", 8.5)
                                .append("w", 10.5)
                                .append("uom", "cm")
                );
        System.out.println("Nou item: " +  nouItem.toJson());
        collection.insertOne(nouItem);

        System.out.println("********* Accedint objectes Json com atributs ***********");
        List<Document> llista = collection.find().into(new ArrayList<Document>());
        for (Document document : llista) {
            String item = document.getString("item");
            //"size" : { "h" : 8.5, "w" : 11, "uom" : "in" }
            System.out.println("Item:" + item);
            Document objecteSize = (Document)document.get("size");
            System.out.println("\t" + objecteSize.getDouble("h") + " " +
                    objecteSize.getDouble("w") + " " +
                    objecteSize.getString("uom")
            );
        }

        System.out.println("***** llista filtrada per status ****** ");
        Document documentFiltratge = Document.parse("{status : 'A', qty : 25.0}");
        List<Document> llistaFiltrada  = collection.find(documentFiltratge).into(new ArrayList<Document>());
        for (Document document : llistaFiltrada) {
            System.out.println(document.toJson());
        }

        System.out.println("**** exemple agreggate *****");

        /* select status, sum(qty) from inventory where item='prova' group by status order by status desc */
        List<Document> agregacions = collection.aggregate(Arrays.asList(
                group("$status", Accumulators.sum("qty", 1)),
                sort(Sorts.descending("status"))))
                .into(new ArrayList<>());
        for (Document agregacion : agregacions) {
            System.out.println(agregacion.toJson());
        }
        collection.aggregate(Arrays.asList(
                group("$status", Accumulators.sum("qty", 1)),
                sort(Sorts.descending("status")),
                out("sumatori2")
                )).toCollection();
                ;

    }
}
