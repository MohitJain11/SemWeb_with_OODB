import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.util.Set;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class Owl2NeoLoader {
//    private static final String ONT_URL ="src/main/resources/Restaurant_Ontology.owl";
    public static void main(String[] args){
        Driver driver = GraphDatabase.driver(
                "bolt://localhost:7687", AuthTokens.basic("neo4j", Pass.pass));

        Session session = driver.session();
        //Creating Nodes...
        session.run("CREATE (x:Restaurant {name:\"Pizza Hut\"}) ");
        session.run("CREATE (x:Food {name:\"Veg Loaded\", ingredients:\"bread, cheese\", price:\"100\"}) ");
        session.run("CREATE (x:Menu {StartDate:\"01-01-2021\", endDate:\"01-03-2021\"}) ");
        session.run("CREATE (x:Cuisine {cuisineName:\"Italian\"}) ");
        session.run("CREATE (x:Location {city:\"Delhi\", address:\"CP\"}) ");
        session.run("CREATE (x:Restaurant {name:\"Domino's\"}) ");

        //creating relationship
        session.run("match(a:Restaurant), (b:Food) "+
                "create (a)-[r:serves]->(b) return type(r)");

        session.run("match(a:Food), (b:Restaurant) "+
                "create (a)-[r:isServedIn]->(b) return type(r)");

        session.run("match(a:Restaurant), (b:Location) "+
                "create (a)-[r:isLocatedIn]->(b) return type(r)");

        session.run("match(a:Restaurant), (b:Menu) "+
                "create (a)-[r:hasMenu]->(b) return type(r)");

        session.run("match(a:Restaurant), (b:Cuisine) "+
                "create (a)-[r:isSpecializedIn]->(b) return type(r)");

        session.close();
        driver.close();
    }
}
