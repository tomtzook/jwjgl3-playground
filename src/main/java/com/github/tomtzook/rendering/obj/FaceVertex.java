package com.github.tomtzook.rendering.obj;

public class FaceVertex {

    public final int mPosIndex;
    public final int mTextCoordsIndex;
    public final int mNormalIndex;

    public FaceVertex(int posIndex, int textCoordsIndex, int normalIndex) {
        mPosIndex = posIndex;
        mTextCoordsIndex = textCoordsIndex;
        mNormalIndex = normalIndex;
    }
}
