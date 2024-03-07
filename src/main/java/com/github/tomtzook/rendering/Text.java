package com.github.tomtzook.rendering;

import com.github.tomtzook.util.Unboxer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Text {

    public static Mesh buildMesh(String text, String textFilepath, int colsCount, int rowsCount) {
        Texture texture = new Texture(textFilepath);

        Mesh mesh = buildTextTilesMesh(text, texture.getWidth(), texture.getHeight(), colsCount, rowsCount);
        mesh.setTexture(texture);

        return mesh;
    }

    private static Mesh buildTextTilesMesh(String text, float width, float height, int colsCount, int rowsCount) {
        // todo: change a lot
        final float Z_POS = 0.0f;
        final int VERTICES_PER_QUAD = 4;

        byte[] chars = text.getBytes(StandardCharsets.ISO_8859_1);

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals = new float[0];
        List<Integer> indices = new ArrayList<>();

        float tileWidth = width / colsCount;
        float tileHeight = height / rowsCount;

        for(int i = 0; i < chars.length; i++) {
            byte currChar = chars[i];
            int col = currChar % colsCount;
            int row = currChar / colsCount;

            // Build a character tile composed by two triangles

            // Left Top vertex
            positions.add(i * tileWidth);
            positions.add(0.0f);
            positions.add(Z_POS);
            textCoords.add(col / (float) colsCount);
            textCoords.add(row / (float) rowsCount);
            indices.add(i * VERTICES_PER_QUAD);

            // Left Bottom vertex
            positions.add(i * tileWidth);
            positions.add(tileHeight);
            positions.add(Z_POS);
            textCoords.add(col / (float) colsCount);
            textCoords.add((row + 1) / (float) rowsCount);
            indices.add(i * VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add(i * tileWidth + tileWidth);
            positions.add(tileHeight);
            positions.add(Z_POS);
            textCoords.add((col + 1)/ (float) colsCount);
            textCoords.add((row + 1) / (float) rowsCount);
            indices.add(i * VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add(i * tileWidth + tileWidth);
            positions.add(0.0f);
            positions.add(Z_POS);
            textCoords.add((col + 1)/ (float) colsCount);
            textCoords.add(row / (float) rowsCount);
            indices.add(i * VERTICES_PER_QUAD + 3);

            // top and bottom right vertices
            indices.add(i * VERTICES_PER_QUAD);
            indices.add(i * VERTICES_PER_QUAD + 2);
        }

        float[] positionsArr = Unboxer.unboxFloat(positions);
        float[] textCoordsArr = Unboxer.unboxFloat(textCoords);
        int[] indicesArr = Unboxer.unboxInt(indices);

        return new Mesh(positionsArr, textCoordsArr, normals, indicesArr);
    }
}
