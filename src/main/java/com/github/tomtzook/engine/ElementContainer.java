package com.github.tomtzook.engine;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ElementContainer<T extends Element> {

    private final List<T> mElements;

    public ElementContainer() {
        mElements = new LinkedList<>();
    }

    public void addElement(T element) {
        mElements.add(element);
        element.onAdd();
    }

    public void removeElement(T element) {
        if (mElements.remove(element)) {
            element.onRemove();
        }
    }

    public void removeAll() {
        for (T element : mElements) {
            element.onRemove();
        }

        mElements.clear();
    }

    public void forEach(Consumer<T> consumer) {
        for (T element : mElements) {
            consumer.accept(element);
        }
    }
}
