package ru.didcvee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnionFind {
    private final List<Integer> ranks = new ArrayList<>();
    private final List<Integer> parents = new ArrayList<>();

    public void add(int element) {
        parents.add(element);
        ranks.add(0);
    }

    public int findRoot(int element) {
        if (parents.get(element) != element) {
            parents.set(element, findRoot(parents.get(element)));
        }
        return parents.get(element);
    }

    public void union(int x, int y) {
        int rootX = findRoot(x);
        int rootY = findRoot(y);
        if (rootX == rootY) return;

        if (Objects.equals(ranks.get(rootX), ranks.get(rootY))) {
            ranks.set(rootX, ranks.get(rootX) + 1);
        }
        if (ranks.get(rootX) < ranks.get(rootY)) {
            parents.set(rootX, rootY);
        } else {
            parents.set(rootY, rootX);
        }
    }

    public int size() {
        return parents.size();
    }
}
