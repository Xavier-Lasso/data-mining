import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import java.io.InputStream;

public class Jena2 {
    private static final Logger logger = LogManager.getLogger(Jena2.class);

    public static void main(String[] args) {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        final String fileName = "./data/ontology.owl";
        final String URI = "http://www.webdatasem-project#";

        OntModel model = ModelFactory.createOntologyModel();

        InputStream in = FileManager.get().open(fileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + fileName + " not found.");
        }

        model.read(in, null);

        OntProperty name = model.getOntProperty(URI + "name");
        OntProperty age = model.getOntProperty(URI + "age");
        OntProperty nationality = model.getOntProperty(URI + "nationality");

        String queryString =
                "PREFIX xmlns:<http://www.webdatasem-project#>\n" +
                        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "SELECT ?person\n" +
                        "WHERE {?person  rdf:type xmlns:Person}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        ResultSet results = qexec.execSelect();
        if(results.hasNext()) {
            System.out.println("Has results.");
        }
        else {
            System.out.println("No Results.");
        }

        while(results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            Resource r = sol.getResource("person");
            if (r.getProperty(nationality) != null) {
                System.out.println(r.getProperty(name).getString() + ", " + r.getProperty(age).getString() + " years" + ", " + r.getProperty(nationality).getString());
            }
            else {
                System.out.println(r.getProperty(name).getString() + ", " + r.getProperty(age).getString() + " years");
            }
        }

    }
}
