package com.github.tomtzook.rendering;

import com.github.tomtzook.math.Transform3;

public interface ElementRenderer3 {

    void render(Transform3 transform, Mesh mesh);

    void finish();
}
