import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
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
        inferedModel.write(System.out);



    }

}
