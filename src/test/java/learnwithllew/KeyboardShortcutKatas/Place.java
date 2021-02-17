package learnwithllew.KeyboardShortcutKatas;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class Place {
    public static Random random = new Random();
    public final int className;
    public final int packageName;
    public Place next;
    public String word;

    public Place(String word) {

        this.word = word;
        this.className = random.nextInt(4) + 2;
        this.packageName = random.nextInt(2) + 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return className == place.className && packageName == place.packageName && Objects.equals(word, place.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, packageName, word);
    }

    @Override
    public String toString() {
        return "org.navigation." + getPackageName() + "." + getClassName() + "." + word;
    }

    public String getClassName() {
        return "_____________________".substring(0, className);
    }

    public String getPackageName() {
        return "_____________________".substring(0, packageName);
    }

    public String getFile() {
        return getPackageName() + "/" + getClassName() + ".java";
    }
}
