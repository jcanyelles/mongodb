package es.cc.liceu.db.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import es.cc.liceu.db.mongo.dao.ProducteDao;
import es.cc.liceu.db.mongo.dao.impl.ProducteDaoImpl;
import es.cc.liceu.db.mongo.domain.Categoria;
import es.cc.liceu.db.mongo.domain.Producte;
import es.cc.liceu.db.mongo.util.Color;

import java.util.*;


public class LimboMongo {

    private MongoDatabase database;
    private ProducteDao producteDao;

    private Map<Producte, Integer> cistella;

    public LimboMongo(MongoDatabase database) {
        this.database = database;
        this.producteDao = new ProducteDaoImpl(database);
        this.cistella = new LinkedHashMap<>();
    }

    public void cercaProductes(Scanner scanner){
        System.out.println(Color.GREEN_BOLD +"*******************************"+Color.RESET);
        System.out.println(Color.GREEN_BOLD +"**          Cerca            **"+Color.RESET);
        System.out.println(Color.GREEN_BOLD +"*******************************"+Color.RESET);
        String nom = pregunta(scanner, "Nom producte");
        String descripcio = pregunta(scanner, "Descripció");
        String marca = pregunta(scanner, "Marca");

        System.out.println(Color.YELLOW_BACKGROUND + "" + Color.WHITE_BRIGHT + "Categoria:" + Color.RESET);
        Collection<Categoria> categories = producteDao.llistaCategories();
        for (Categoria category : categories) {
            System.out.println("\t(" + Color.YELLOW_BOLD + category.getCodi() + Color.RESET + ") " + category.getNom() + " " + category.getDescripcio());
        }
        String categoria = scanner.nextLine();
        Integer codiCategoria = null;
        if (categoria!=null && !categoria.isEmpty()){
            codiCategoria = Integer.parseInt(categoria);
        }


        List<Producte> productes =  new ArrayList<>(producteDao.llistaProductes(null,nom, descripcio,marca,codiCategoria));
        info("S'han trobat " + productes.size() + " productes");
        int i = 0;
        for (Producte producte : productes) {
            System.out.println(Color.GREEN_BOLD + "" + i + Color.RESET + "\t" + producte.getNom() + "\t" + producte.getDescripcio() + "\t" + Color.CYAN_BOLD + producte.getMarca()+ Color.RESET + "\t" + Color.BLUE_BOLD  + producte.getPreu()+ "€" + Color.RESET);
            i++;
        }
        if (i>0){
            System.out.println("Seleccioni el producte (0-" + (i-1) + "): ");
            System.out.println("x) Sortir cerca ");
            String opcio = pregunta(scanner, "Esculli l'opció: ");
            while (!opcio.equalsIgnoreCase("x")){
                int seleccionat = Integer.parseInt(opcio);
                Producte producteSeleccionat =  productes.get(seleccionat);
                info("Ha seleccionat el producte " + seleccionat + " " + producteSeleccionat.getNom() + " " +producteSeleccionat.getPreu().toString());
                String unitatsSeleccionades  = pregunta(scanner, "Unitats: ");
                Integer unitats = Integer.parseInt(unitatsSeleccionades);

                cistella.put(producteSeleccionat, (cistella.get(producteSeleccionat)==null ? 0 : cistella.get(producteSeleccionat)) + unitats);

                System.out.println("Seleccioni el producte (0-" + (i-1) + "): ");
                System.out.println("x) Sortir cerca ");
                opcio = pregunta(scanner, "Esculli l'opció: ");
            }


        }


    }
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("limbo");
        Scanner scanner = new Scanner(System.in);
        LimboMongo limbo = new LimboMongo(database);
        limbo.cercaProductes(scanner);

        /*
        System.out.println("Monstram totes les bases de dades");
        MongoCursor<String> dbsCursor = mongoClient.listDatabaseNames().iterator();
        while(dbsCursor.hasNext()) {
            System.out.println(dbsCursor.next());
        }
        System.out.println("***********************");
        MongoDatabase database = mongoClient.getDatabase("sample");
        MongoIterable<String> nomsCollections =  database.listCollectionNames();
        nomsCollections.iterator()

        MongoCollection<Document> collection = database.getCollection("people");
        collection.find()
        */

    }

    public String pregunta(Scanner scanner, String texte){
        System.out.println(Color.YELLOW_BACKGROUND + "" + Color.WHITE_BRIGHT + texte + Color.RESET);
        return scanner.nextLine();
    }
    public void info(String texte){
        System.out.println(Color.BLUE_BOLD + "\t" + texte + Color.RESET);
    }
    public void errada(String texte){
        System.out.println(Color.RED_BOLD + "\t" + texte + Color.RESET);
    }
}
