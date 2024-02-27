package com.github.tomtzook.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class Resources {

    private Resources() {}

    public static BufferedImage readImage(String path) throws IOException {
        URL resource = Resources.class.getResource(path);
        if (resource == null) {
            throw new FileNotFoundException(path);
        }

        try {
            File file = new File(resource.toURI());
            return ImageIO.read(file);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public static BufferedImage readImageOrThrow(String path, String errorMessage) {
        try {
            return readImage(path);
        } catch (IOException e) {
            throw new Error(errorMessage, e);
        }
    }

    public static String readFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream stream = Resources.class.getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException(path);
        }

        try (stream;
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }

            return builder.toString();
        }
    }

    public static String readFileOrThrow(String path, String errorMessage) {
        try {
            return readFile(path);
        } catch (IOException e) {
            throw new Error(errorMessage, e);
        }
    }
}
