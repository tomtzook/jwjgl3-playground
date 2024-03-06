package com.github.tomtzook.rendering.obj;

public class Face {

    public final FaceVertex[] mVertices;

    public Face(FaceVertex[] vertices) {
        assert vertices.length == 3;
        mVertices = vertices;
    }
}
