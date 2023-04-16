package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    // The value of left Node is less than root's value, meanwhile the value of right Node is larger than root's value.
    private static class BSTNode<K extends Comparable<K>, V> {
        K key;
        V value;
        BSTNode<K, V> left;
        BSTNode<K, V> right;

        public BSTNode(K k, V v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
        }

        public BSTNode<K, V> findParent(BSTNode<K, V> target) {
            if (this.left == target || this.right == target) {
                return this;
            } else if (this.key.compareTo(target.key) < 0) {
                return this.right.findParent(target);
            } else {
                return this.left.findParent(target);
            }
        }

        public BSTNode<K, V> getNode(BSTNode<K, V> b, K k) {
            if (b == null || k == null) {
                return null;
            } else if (k.equals(b.key)) {
                return b;
            } else if (k.compareTo(b.key) > 0) {
                return getNode(b.right, k);
            } else {
                return getNode(b.left, k);
            }
        }

        // alternative solution of get (preferred)
        public BSTNode<K, V> get(K k) {
            if (k == null) {
                return null;
            }
            if (k.equals(this.key)) {
                return this;
            } else if (this.isLeaf()) {
                return null;
            } else if (k.compareTo(this.key) < 0) {
                return this.left.get(k);
            } else {
                return this.right.get(k);
            }
        }

        public void insertNode(BSTNode<K, V> b, K k, V v) {
            if (b == null) {
                b = new BSTNode<>(k, v);
            } else if (k.compareTo(b.key) < 0) {
                if (b.left == null) {
                    b.left = new BSTNode<>(k, v);
                } else {
                    insertNode(b.left, k, v);
                }
            } else {
                if (b.right == null) {
                    b.right = new BSTNode<>(k, v);
                } else {
                    insertNode(b.right, k, v);
                }
            }
        }

        // alternative solution of put (preferred)
        public void put(K k, V v) {
            if (k.compareTo(this.key) < 0) {
                if (this.left == null) {
                    this.left = new BSTNode<>(k, v);
                    return;
                }
                this.left.put(k, v);
            } else {
                if (this.right == null) {
                    this.right = new BSTNode<>(k, v);
                    return;
                }
                this.right.put(k, v);
            }
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        // preorder traversal of the whole BSTMap, return a list of BSTNode
        public List<BSTNode<K, V>> traverse() {
            List<BSTNode<K, V>> ans = new ArrayList<>();
            if (this.left != null) {
                ans.addAll(this.left.traverse());
            }
            ans.add(this);
            if (this.right != null) {
                ans.addAll(this.right.traverse());
            }
            return ans;
        }

        public BSTNode<K, V> findRightMost() {
            if (this.right == null) {
                return this;
            } else {
                return this.right.findRightMost();
            }
        }
    }

    private int size;
    BSTNode<K, V> root;


    public BSTMap() {
        this.clear();
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (this.root == null) {
            return false;
        }
        return root.getNode(this.root, key) != null;
    }

    @Override
    public V get(K key) {
        if (this.root == null || root.get(key) == null) {
            return null;
        }
        return root.get(key).value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        this.size += 1;
        if (this.root == null) {
            root = new BSTNode<>(key, value);
        } else {
            root.put(key, value);
        }
    }

    public String printInOrder() {
        return this.root.traverse().toString();
    }

    @Override
    public Set<K> keySet() {
        if (this.root == null) {
            return null;
        }
        Set<K> ans = new HashSet<>();
        for (BSTNode<K, V> i : this.root.traverse()) {
            ans.add(i.key);
        }
        return ans;
    }

    @Override
    public V remove(K key) {
        for (BSTNode<K, V> i : this.root.traverse()) {
            if (i.key == key) {
                V tmpValue = i.value;
                if (i == root) {
                    if (i.left != null) {
                        BSTNode<K, V> l = i.left, r = i.right;
                        root = i.findRightMost();
                        root.left = l;
                        root.right = r;
                    } else {
                        root = i.right;
                    }
                } else if (i.isLeaf()) {
                    BSTNode<K, V> p = root.findParent(i);
                    if (p.left == i) {
                        p.left = null;
                    } else {
                        p.right = null;
                    }
                } else {
                    BSTNode<K, V> p = root.findParent(i);
                    if (p.left == i) {
                        p.left = i.findRightMost();
                    } else {
                        p.right = i.findRightMost();
                    }
                }
                this.size -= 1;
                return tmpValue;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return this.remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
