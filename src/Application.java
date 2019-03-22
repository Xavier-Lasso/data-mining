import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Application {
    private static final Logger logger = LogManager.getLogger(Jena1.class);

    private static final String URI = "http://www.webdatasem-project#";

    private static void searchMovieWithActor(OntModel model, String actorName) {
        OntClass movie = model.getOntClass(URI + "Movie");
        OntProperty title = model.getOntProperty(URI + "title");
        OntProperty year = model.getOntProperty(URI + "year");
        OntProperty country = model.getOntProperty(URI + "country");
        OntProperty hasActor = model.getOntProperty(URI + "hasActor");
        OntProperty name = model.getOntProperty(URI + "name");

        System.out.println("List of movies with \"" + actorName + "\" as actor:");
        for (ExtendedIterator<? extends OntResource> instances = movie.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasActor); nodeit.hasNext(); ) {
                Resource actor = nodeit.next().asResource();
                if (actor.getProperty(name).getString().toLowerCase().equals(actorName.toLowerCase())) {
                    System.out.println("    -" + inst.getProperty(title).getString() + " - " + inst.getProperty(year).getString() + " - " + inst.getProperty(country).getString());
                }
            }
        }
    }

    private static void searchMovieWithDirector(OntModel model, String directorName) {
        OntClass movie = model.getOntClass(URI + "Movie");
        OntProperty title = model.getOntProperty(URI + "title");
        OntProperty year = model.getOntProperty(URI + "year");
        OntProperty country = model.getOntProperty(URI + "country");
        OntProperty hasDirector = model.getOntProperty(URI + "hasDirector");
        OntProperty name = model.getOntProperty(URI + "name");

        System.out.println("List of movies with \"" + directorName + "\" as director:");
        for (ExtendedIterator<? extends OntResource> instances = movie.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasDirector); nodeit.hasNext(); ) {
                Resource director = nodeit.next().asResource();
                if (director.getProperty(name).getString().toLowerCase().equals(directorName.toLowerCase())) {
                    System.out.println("    -" + inst.getProperty(title).getString() + " - " + inst.getProperty(year).getString() + " - " + inst.getProperty(country).getString());
                }
            }
        }
    }

    private static void searchMovieWithWriter(OntModel model, String writerName) {
        OntClass movie = model.getOntClass(URI + "Movie");
        OntProperty title = model.getOntProperty(URI + "title");
        OntProperty year = model.getOntProperty(URI + "year");
        OntProperty country = model.getOntProperty(URI + "country");
        OntProperty hasWriter = model.getOntProperty(URI + "hasWriter");
        OntProperty name = model.getOntProperty(URI + "name");

        System.out.println("List of movies with \"" + writerName + "\" as writer:");
        for (ExtendedIterator<? extends OntResource> instances = movie.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasWriter); nodeit.hasNext(); ) {
                Resource writer = nodeit.next().asResource();
                if (writer.getProperty(name).getString().toLowerCase().equals(writerName.toLowerCase())) {
                    System.out.println("    -" + inst.getProperty(title).getString() + " - " + inst.getProperty(year).getString() + " - " + inst.getProperty(country).getString());
                }
            }
        }
    }

    private static void searchMovieWithGenre(OntModel model, String genre) {
        OntClass movie = model.getOntClass(URI + "Movie");
        OntProperty title = model.getOntProperty(URI + "title");
        OntProperty year = model.getOntProperty(URI + "year");
        OntProperty country = model.getOntProperty(URI + "country");
        OntProperty hasGenre = model.getOntProperty(URI + "hasGenre");
        OntProperty name = model.getOntProperty(URI + "name");

        System.out.println("List of movies with genre \"" + genre + "\":");
        for (ExtendedIterator<? extends OntResource> instances = movie.listInstances(); instances.hasNext(); ) {
            OntResource inst = instances.next();
            for (NodeIterator nodeit = model.listObjectsOfProperty(inst, hasGenre); nodeit.hasNext(); ) {
                Resource gen = nodeit.next().asResource();
                if (gen.getLocalName().toLowerCase().equals(genre.toLowerCase())) {
                    System.out.println("    -" + inst.getProperty(title).getString() + " - " + inst.getProperty(year).getString() + " - " + inst.getProperty(country).getString());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        final String fileName = "./data/ontology.owl";

        OntModel model = ModelFactory.createOntologyModel();

        InputStream in = FileManager.get().open(fileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + fileName + " not found.");
        }

        model.read(in, null);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean quit = false;
        while (quit == false) {
            System.out.println("\nWhat do you want to do:");
            System.out.println("1 - Search movies with actors");
            System.out.println("2 - Search movies with directors");
            System.out.println("3 - Search movies with writers");
            System.out.println("4 - Search movies with genres");
            System.out.println("5 - Quit\n");
            System.out.print("Your choice: ");
            String input = reader.readLine();
            switch (input) {
                case "1":
                    System.out.print("Enter actor name: ");
                    String actor = reader.readLine();
                    searchMovieWithActor(model, actor);
                    break;

                case "2":
                    System.out.print("Enter director name: ");
                    String director = reader.readLine();
                    searchMovieWithDirector(model, director);
                    break;

                case "3":
                    System.out.print("Enter writer name: ");
                    String writer = reader.readLine();
                    searchMovieWithWriter(model, writer);
                    break;

                case "4":
                    System.out.print("Enter genre: ");
                    String genre = reader.readLine();
                    searchMovieWithGenre(model, genre);
                    break;

                case "5":
                    System.out.print("Closure of program.");
                    quit = true;
                    break;

                default:
                    System.out.println("\nThe choice \"" + input + "\" isn't valid.");
            }
        }
    }
}
