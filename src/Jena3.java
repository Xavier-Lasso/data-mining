import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import tools.JenaEngine;

import java.io.InputStream;

public class Jena3 {
    private static final Logger logger = LogManager.getLogger(Jena3.class);

    public static void main(String[] args) {org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        final String fileName = "./data/ontology.owl";
        final String rulesFile = "./data/rules.txt";
        final String URI = "http://www.webdatasem-project#";

        OntModel model = ModelFactory.createOntologyModel();

        InputStream in = FileManager.get().open(fileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + fileName + " not found.");
        }

        Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, rulesFile);

        inferedModel.read(in, null);
        // inferedModel.write(System.out);

        Property name = inferedModel.getProperty(URI + "name");
        Property age = inferedModel.getProperty(URI + "age");
        Property nationality = inferedModel.getProperty(URI + "nationality");
        Property isActorOf = inferedModel.getProperty(URI + "isActorOf");

        System.out.println("List of actors:");
        for (ExtendedIterator<? extends Resource> instances = inferedModel.listResourcesWithProperty(isActorOf); instances.hasNext(); ) {
            Resource inst = instances.next();
            if (inst.getProperty(nationality) != null) {
                System.out.println(inst.getProperty(name).getString() + ", " + inst.getProperty(age).getString() + " years" + ", " + inst.getProperty(nationality).getString());
            }
            else {
                System.out.println(inst.getProperty(name).getString() + ", " + inst.getProperty(age).getString() + " years");
            }
        }
    }

}
