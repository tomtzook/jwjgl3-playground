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
        double q0 = quaternion.x();
        double q1 = quaternion.y();
        double q2 = quaternion.z();
        double q3 = quaternion.w();

        double r00 = 2 * (q0 * q0 + q1 * q1) - 1;
        double r01 = 2 * (q1 * q2 - q0 * q3);
        double r02 = 2 * (q1 * q3 + q0 * q2);

        double r10 = 2 * (q1 * q2 + q0 * q3);
        double r11 = 2 * (q0 * q0 + q2 * q2) - 1;
        double r12 = 2 * (q2 * q3 - q0 * q1);

        double r20 = 2 * (q1 * q3 - q0 * q2);
        double r21 = 2 * (q2 * q3 + q0 * q1);
        double r22 = 2 * (q0 * q0 + q3 * q3) - 1;

        return Matrix.from(new double[][] {
                {r00, r01, r02, 0},
                {r10, r11, r12, 0},
                {r20, r21, r22, 0},
                {0, 0, 0, 1}
        });
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
