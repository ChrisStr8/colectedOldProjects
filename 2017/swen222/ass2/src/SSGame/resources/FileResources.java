package SSGame.resources;

import java.io.File;

/**
 * Created on 5/09/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public enum FileResources {
    PIECES("pieces.txt");

    public final File file;

    FileResources(String resourceName) {
        file = new File(resourceName);
    }
}

