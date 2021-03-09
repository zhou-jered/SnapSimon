package me.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LCA {
    static class Node {
        int val;
        List<Node> children;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, children);
        }
    }

    Node root;
    List<Integer> depth
    int traverseIdx = 1;
    Map<Node, Integer> nodeTraverseIdxMap = new HashMap<>();

    public void init(Node root) {

    }

    private void dfs(Node root) {
        if (!nodeTraverseIdxMap.containsKey(root)) {
            nodeTraverseIdxMap.put(root, traverseIdx);
        }
        traverseIdx++;
        if (root.children != null) {
            for (Node node : root.children) {
                dfs(node);
            }
        }
    }
}
