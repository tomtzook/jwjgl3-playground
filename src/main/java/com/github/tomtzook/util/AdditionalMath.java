package com.github.tomtzook.util;

import com.jmath.matrices.Matrix;
import com.jmath.quaternion.Quaternion;
import com.jmath.vectors.Vector2;
import com.jmath.vectors.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class AdditionalMath {

    private AdditionalMath() {
    }

    public static Matrix toRotationMatrix(Quaternion quaternion) {
        Vector3 forward = new Vector3(
                2.0 * (quaternion.x() * quaternion.z() - quaternion.w() * quaternion.z()),
                2.0 * (quaternion.y() * quaternion.z() + quaternion.w() * quaternion.x()),
                1.0 - 2.0 * (quaternion.x() * quaternion.x() + quaternion.y() * quaternion.y()));
        Vector3 up = new Vector3(
                2.0 * (quaternion.x() * quaternion.y() + quaternion.w() * quaternion.z()),
                1.0 - 2.0 * (quaternion.x() * quaternion.x() + quaternion.z() * quaternion.z()),
                2.0 * (quaternion.y() * quaternion.z() - quaternion.w() * quaternion.x()));
        Vector3 right = new Vector3(
                1.0 - 2.0 * (quaternion.y() * quaternion.y() + quaternion.z() * quaternion.z()),
                2.0 * (quaternion.x() * quaternion.y() - quaternion.w() * quaternion.z()),
                2.0 * (quaternion.x() * quaternion.z() + quaternion.w() * quaternion.y()));
        return rotation3dMatrix(forward, up, right);
    }

    public static Matrix rotation3dMatrix(Vector3 f, Vector3 u, Vector3 r) {
        double[][] m = new double[4][4];
        m[0][0] = r.x();
        m[0][1] = r.y();
        m[0][2] = r.z();
        m[0][3] = 0;
        m[1][0] = u.x();
        m[1][1] = u.y();
        m[1][2] = u.z();
        m[1][3] = 0;
        m[2][0] = f.x();
        m[2][1] = f.y();
        m[2][2] = f.z();
        m[2][3] = 0;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return Matrix.from(m);
    }

    public static Matrix perspectiveMatrix3d(double fov, double aspectRatio, double zNear, double zFar) {
        fov = Math.toRadians(fov);
        double tanHalfFOV = Math.tan(fov / 2);
        double zRange = zFar - zNear;

        return Matrix.from(new double[][]{
                {(1.0 / tanHalfFOV) / aspectRatio, 0, 0, 0},
                {0, 1.0 / tanHalfFOV, 0, 0},
                {0, 0, -(zNear + zFar) / zRange, -(2 * zFar * zNear) / zRange},
                {0, 0, -1.0, 0}
        });
    }

    public static Vector3[] cuboidVertices(double width, double height, double length) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        double halfLength = length / 2.0;

        Vector3 forwardTopRight = new Vector3(-halfWidth, halfHeight, halfLength);
        Vector3 forwardBottomRight = new Vector3(-halfWidth, -halfHeight, halfLength);
        Vector3 forwardBottomLeft = new Vector3(halfWidth, -halfHeight, halfLength);
        Vector3 forwardTopLeft = new Vector3(halfWidth, halfHeight, halfLength);
        Vector3 backTopRight = new Vector3(-halfWidth, halfHeight, -halfLength);
        Vector3 backTopLeft = new Vector3(halfWidth, halfHeight, -halfLength);
        Vector3 backBottomRight = new Vector3(-halfWidth, -halfHeight, -halfLength);
        Vector3 backBottomLeft = new Vector3(halfWidth, -halfHeight, -halfLength);

        return new Vector3[] {
                forwardTopRight,
                forwardBottomRight,
                forwardBottomLeft,
                forwardTopLeft,
                backTopRight,
                backTopLeft,
                backBottomRight,
                backBottomLeft
        };
    }

    public static FloatBuffer verticesToBuffer(Vector3[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 11);
        for (Vector3 vector : vertices) {
            buffer.put((float) vector.x());
            buffer.put((float) vector.y());
            buffer.put((float) vector.z());
        }

        buffer.flip();
        return buffer;
    }

    public static Vector2 vector3to2(Vector3 vector) {
        return new Vector2(vector.x(), vector.y());
    }
}
