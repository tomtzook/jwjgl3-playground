package com.github.tomtzook.rendering.obj;

import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.util.Buffers;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ObjReadingContext {

    private final List<Vector3f> mVertices;
    private final List<Vector2f> mTextCoordinates;
    private final List<Vector3f> mNormals;
    private final List<Face> mFaces;

    public ObjReadingContext() {
        mVertices = new ArrayList<>();
        mTextCoordinates = new ArrayList<>();
        mNormals = new ArrayList<>();
        mFaces = new ArrayList<>();
    }

    public void feedLine(String line) {
        String[] tokens = line.split("\\s+");
        String lineType = tokens[0];

        // todo: catch numberformatexception
        if (lineType.equals("v")) {
            // vertex
            Vector3f vector = parseVector3(tokens);
            mVertices.add(vector);
        } else if (lineType.equals("vt")) {
            // text coordinate
            Vector2f vector = parseVector2(tokens);
            mTextCoordinates.add(vector);
        } else if (lineType.equals("vn")) {
            // normal
            Vector3f vector = parseVector3(tokens);
            mNormals.add(vector);
        } else if (lineType.equals("f")) {
            // face
            Face face = parseFace(tokens);
            mFaces.add(face);
        }
    }

    public Mesh finish() {
        List<Integer> indices = new ArrayList<>();

        float[] positions = new float[mVertices.size() * 3];
        float[] textCoords = new float[positions.length];
        float[] normals = new float[positions.length];

        loadPositions(positions);
        processFaces(indices, textCoords, normals);

        int[] indicesArr = indices.stream().mapToInt((i)->i).toArray();

        return new Mesh(positions, textCoords, normals, indicesArr);
    }

    private void loadPositions(float[] positions) {
        assert positions.length == mVertices.size() * 3;

        for (int i = 0; i < mVertices.size(); i++) {
            Vector3f vertex = mVertices.get(i);
            positions[i * 3] = vertex.x;
            positions[i * 3 + 1] = vertex.y;
            positions[i * 3 + 2] = vertex.z;
        }
    }

    private void processFaces(List<Integer> indices, float[] textCoords, float[] normals) {
        for (Face face : mFaces) {
            for (FaceVertex faceVertex : face.mVertices) {
                processFaceVertex(faceVertex, indices, textCoords, normals);
            }
        }
    }

    private void processFaceVertex(FaceVertex faceVertex, List<Integer> indices, float[] textCoords, float[] normals) {
        int posIndex = faceVertex.mPosIndex;
        indices.add(posIndex);

        if (faceVertex.mTextCoordsIndex >= 0) {
            Vector2f textCoord = mTextCoordinates.get(faceVertex.mTextCoordsIndex);
            textCoords[posIndex * 2] = textCoord.x;
            textCoords[posIndex * 2 + 1] = textCoord.y;
        }

        if (faceVertex.mNormalIndex >= 0) {
            Vector3f normal = mNormals.get(faceVertex.mNormalIndex);
            normals[posIndex * 3] = normal.x;
            normals[posIndex * 3 + 1] = normal.y;
            normals[posIndex * 3 + 2] = normal.z;
        }
    }

    private static Vector3f parseVector3(String[] tokens) {
        float x = Float.parseFloat(tokens[1]);
        float y = Float.parseFloat(tokens[2]);
        float z = Float.parseFloat(tokens[3]);
        return new Vector3f(x, y, z);
    }

    private static Vector2f parseVector2(String[] tokens) {
        float x = Float.parseFloat(tokens[1]);
        float y = Float.parseFloat(tokens[2]);
        return new Vector2f(x, y);
    }

    private static Face parseFace(String[] tokens) {
        FaceVertex[] vertices = new FaceVertex[3];
        vertices[0] = parseFaceVertexIndices(tokens[1]);
        vertices[1] = parseFaceVertexIndices(tokens[2]);
        vertices[2] = parseFaceVertexIndices(tokens[3]);

        return new Face(vertices);
    }

    private static FaceVertex parseFaceVertexIndices(String token) {
        String[] items = token.split("/");

        int pos = Integer.parseInt(items[0]) - 1;
        int textCoords = -1;
        int normal = -1;

        if (items.length > 1 && !items[1].isBlank()) {
            textCoords = Integer.parseInt(items[1]) - 1;
        }

        if (items.length > 2 && !items[2].isBlank()) {
            normal = Integer.parseInt(items[2]) - 1;
        }

        return new FaceVertex(pos, textCoords, normal);
    }
}
