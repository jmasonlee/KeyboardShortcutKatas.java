package learnwithllew.KeyboardShortcutKatas;

import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.junit.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class NavigationKata {
    @Test
    public void code() {
        String text = "I once made a car from wood. " +
                "The chassis was made from wood. " +
                "I made the wheels from wood. " +
                "The doors and hinges, all wooden. " +
                "The engine was tricky to make, but all made from wood, right down to the ebony piston rings." +
                "It was a beautiful piece of craftsmanship, the only problem with it was that It wooden go.";
        text = text.replaceAll("[\\.,]", " ").replaceAll("  ", " ");
        String[] words = text.split(" ");
        ArrayList<Place> places = new ArrayList<>();
        for (int i = 0, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i];
            Place p = createPlace(places, word);
            places.add(p);
            if (i != 0) {
                places.get(i - 1).next = p;
            }
        }
        printPlaces(places);
        SimpleLogger.variable("start at ", places.get(0));

    }

    private Place createPlace(ArrayList<Place> places, String word) {
        Place p = new Place(word);
        while (places.contains(p)) {
            p = new Place(word);

        }
        return p;
    }

    private void printPlaces(ArrayList<Place> places) {
        for (int packages = 1; packages <= 4; packages++) {
            for (int clazz = 1; clazz <= 6; clazz++) {
                int pCount = packages;
                int cCount = clazz;
                Queryable<Place> forClass = Query.where(places, p -> p.packageName == pCount && p.className == cCount);
                if (0 < forClass.size()) {
                    writeFile(forClass);
                }
            }
        }
    }

    private void writeFile(Queryable<Place> forClass) {
        Place place = forClass.get(0);
        File file = new File("src/main/java/_/" + place.getFile());
        String text = String.format("package _.%s;\n" +
                "public class %s { %s" +

                "}", place.getPackageName(), place.getClassName(), getMethods(forClass));
        FileUtils.writeFile(file, text);
        //SimpleLogger.event("Writing " + file);
    }

    private String getMethods(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            Place next = place.next;
            text += "\n public static void " + place.word + "(){\n";

            if (true && next != null) {
                text += "_."+next.getPackageName() + "." + next.getClassName() + "." + next.word + "();\n";
            }
            text += "}\n";
        }
        return text;
    }
}
