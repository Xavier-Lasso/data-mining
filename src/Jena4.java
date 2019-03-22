import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Jena4 {
    private static final Logger logger = LogManager.getLogger(Jena1.class);

    public static void main(String[] args) throws IOException {
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

        // Read input from user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter movie title: ");
        String movieName = reader.readLine();

        // Define all properties
        OntClass movie = model.getOntClass(URI + "Movie");
        OntProperty title = model.getOntProperty(URI + "title");
        OntProperty year = model.getOntProperty(URI + "year");
        OntProperty country = model.getOntProperty(URI + "country");
        OntProperty hasGenre = model.getOntProperty(URI + "hasGenre");
        OntProperty hasActor = model.getOntProperty(URI + "hasActor");
        OntProperty name = model.getOntProperty(URI + "name");
        OntProperty age = model.getOntProperty(URI + "age");
        OntProperty nationality = model.getOntProperty(URI + "nationality");

        boolean found = false;
        for (ExtendedIterator<? extends OntResource> instances = movie.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            if (inst.getProperty(title).getString().toLowerCase().equals(movieName.toLowerCase())) {
                found = true;
                System.out.println("Movie found:");
                System.out.println(inst.getProperty(title).getString() + ", " + inst.getProperty(year).getString() + ", " + inst.getProperty(country).getString());
                // Search for Genres
                System.out.print("Genres: ");
                for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasGenre); nodeit.hasNext(); ) {
                    Resource genre = nodeit.next().asResource();
                        System.out.print(genre.getLocalName());
                        if (nodeit.hasNext()) {
                            System.out.print(", ");
                        }
                        else {
                            System.out.println();
                        }
                }
                // Search for Actors
                System.out.println("Actors:");
                for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasActor); nodeit.hasNext(); ) {
                    Resource actor = nodeit.next().asResource();
                    if (actor.getProperty(nationality) != null) {
                        System.out.println("    -" + actor.getProperty(name).getString() + ", " + actor.getProperty(age).getString() + " years" + ", " + actor.getProperty(nationality).getString());
                    }
                    else {
                        System.out.println("    -" + actor.getProperty(name).getString() + ", " + actor.getProperty(age).getString() + " years");
                    }
                }
            }
            if (!instances.hasNext() && !found){
                System.out.println("No movie found with the title: " + movieName);
            }
        }
    }
}
