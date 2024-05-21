
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class bst<Key extends Comparable<Key>, Value> {
    private bst<Key, Value>.TreeNode root;
    private int nodeCount;

    public bst() {
    }

    public void insert(Key key, Value value) {
        this.root = this.insert(this.root, key, value);
        ++this.nodeCount;
    }

    private bst<Key, Value>.TreeNode insert(bst<Key, Value>.TreeNode node, Key key, Value value) {
        if (node == null) {
            return new TreeNode(this, key, value);
        } else {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node.left = this.insert(node.left, key, value);
            } else if (cmp > 0) {
                node.right = this.insert(node.right, key, value);
            } else {
                node.value = value;
            }

            return node;
        }
    }

    public Value search(Key key) {
        bst<Key, Value>.TreeNode node = this.root;

        while(node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else {
                if (cmp <= 0) {
                    return node.value;
                }

                node = node.right;
            }
        }

        return null;
    }

    public void remove(Key key) {
        this.root = this.remove(this.root, key);
        --this.nodeCount;
    }

    private bst<Key, Value>.TreeNode remove(bst<Key, Value>.TreeNode node, Key key) {
        if (node == null) {
            return null;
        } else {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node.left = this.remove(node.left, key);
            } else if (cmp > 0) {
                node.right = this.remove(node.right, key);
            } else {
                if (node.right == null) {
                    return node.left;
                }

                if (node.left == null) {
                    return node.right;
                }

                bst<Key, Value>.TreeNode temp = node;
                node = this.min(node.right);
                node.right = this.removeMin(temp.right);
                node.left = temp.left;
            }

            return node;
        }
    }

    private bst<Key, Value>.TreeNode min(bst<Key, Value>.TreeNode node) {
        while(node.left != null) {
            node = node.left;
        }

        return node;
    }

    private bst<Key, Value>.TreeNode removeMin(bst<Key, Value>.TreeNode node) {
        if (node.left == null) {
            return node.right;
        } else {
            node.left = this.removeMin(node.left);
            return node;
        }
    }

    public Iterable<Key> traverse() {
        List<Key> keys = new ArrayList();
        Deque<bst<Key, Value>.TreeNode> stack = new ArrayDeque();

        for(bst<Key, Value>.TreeNode current = this.root; current != null || !stack.isEmpty(); current = current.right) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }

            current = (TreeNode)stack.pop();
            keys.add(current.key);
        }

        return keys;
    }

    public int treeSize() {
        return this.nodeCount;
    }

    public static void main(String[] args) {
        bst<Integer, String> bst = new bst();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");
        bst.insert(2, "Two");
        bst.insert(4, "Four");
        bst.insert(6, "Six");
        bst.insert(8, "Eight");
        System.out.println("Tree Size: " + bst.treeSize());
        System.out.println("Keys in order:");
        Iterator var2 = bst.traverse().iterator();

        while(var2.hasNext()) {
            Integer key = (Integer)var2.next();
            System.out.println("Key: " + key + ", Value: " + (String)bst.search(key));
        }

    }

    private class TreeNode {
        Key key;
        Value value;
        bst<Key, Value>.TreeNode left;
        bst<Key, Value>.TreeNode right;

        public TreeNode(final bst var1, Comparable key, Object value) {
            this.key = (Key) key;
            this.value = (Value) value;
        }
    }
}
