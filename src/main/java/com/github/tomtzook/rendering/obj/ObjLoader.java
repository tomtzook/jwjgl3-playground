package com.github.tomtzook.rendering.obj;

import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.util.Resources;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ObjLoader {

    public static Mesh load(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        return load(lines);
    }

    public static Mesh loadFromResource(String path) throws IOException {
        URL resource = Resources.class.getResource(path);
        if (resource == null) {
            throw new FileNotFoundException(path);
        }

        try {
            File file = new File(resource.toURI());
            return load(file.toPath());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public static Mesh load(Iterable<String> lines) throws IOException {
        ObjReadingContext context = new ObjReadingContext();

        for (String line : lines) {
            context.feedLine(line);
        }

        return context.finish();
    }
}
