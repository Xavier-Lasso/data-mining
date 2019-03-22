import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.varia.NullAppender;

import java.io.InputStream;

public class Jena1 {
    private static final Logger logger = LogManager.getLogger(Jena1.class);

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
        // model.write(System.out);
        OntClass person = model.getOntClass(URI + "Person");
        OntProperty name = model.getOntProperty(URI + "name");
        OntProperty age = model.getOntProperty(URI + "age");
        OntProperty nationality = model.getOntProperty(URI + "nationality");

        for (ExtendedIterator<? extends OntResource> instances = person.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            if (inst.getProperty(nationality) != null) {
                System.out.println(inst.getProperty(name).getString() + ", " + inst.getProperty(age).getString() + " years" + ", " + inst.getProperty(nationality).getString());
            }
            else {
                System.out.println(inst.getProperty(name).getString() + ", " + inst.getProperty(age).getString() + " years");
            }
        }
    }
}
