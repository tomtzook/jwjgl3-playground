package com.github.tomtzook.rendering;

import com.github.tomtzook.math.Transform2;

public interface ElementRenderer2 {

    void render(Transform2 transform, Mesh mesh);

    void finish();
}
