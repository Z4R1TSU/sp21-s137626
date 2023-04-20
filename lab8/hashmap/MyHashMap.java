package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Zari Tsu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private int size;
    private int length;
    private double loadFactor;
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.size = 16;
        this.length = 0;
        this.loadFactor = 0.75;
        this.buckets = createTable(this.size);
    }

    public MyHashMap(int initialSize) {
        this.size = initialSize;
        this.length = 0;
        this.loadFactor = 0.75;
        this.buckets = createTable(this.size);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = initialSize;
        this.length = 0;
        this.loadFactor = maxLoad;
        this.buckets = createTable(this.size);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        this.size = 16;
        this.length = 0;
        this.buckets = createTable(this.size);
    }

    @Override
    public boolean containsKey(K key) {
        return key != null && this.get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int posi = getPosition(key);
        Collection<Node> Nodes = this.buckets[posi];
        if (Nodes == null) {
            return null;
        }
        for (Node i : Nodes) {
            if (i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }

    private int getPosition(K key) {
        int hashValue = key.hashCode();
        return Math.floorMod(hashValue, this.size);
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public void put(K key, V value) {
        // Determine whether the array exceeds loadFactor, if so, reSize to double the former size.
        if ((double) length / size > loadFactor) {
            reSize(this.size * 2);
        }

        // Find which bucket is this key in. If the bucket does not exist, then create one.
        int posi = getPosition(key);
        if (this.buckets[posi] == null) {
            this.buckets[posi] = createBucket();
        }
        // If the key has existed in the bucket, change the value of the map.
        for (Node i : this.buckets[posi]) {
            if (i.key == key) {
                i.value = value;
                return;
            }
        }
        this.length += 1;
        this.buckets[posi].add(createNode(key, value));
    }

    @Override
    public Set<K> keySet() {
        if (this.buckets == null) {
            return null;
        }
        Set<K> ans = new HashSet<>();
        for (Collection<Node> i : this.buckets) {
            if (i != null) {
                for (Node j : i) {
                    ans.add(j.key);
                }
            }
        }
        return ans;
    }

    private void reSize(int s) {
        Collection<Node>[] newBuckets = createTable(s);
        if (this.size >= 0) System.arraycopy(this.buckets, 0, newBuckets, 0, this.size);
        this.buckets = newBuckets;
        this.size *= 2;
    }

    @Override
    public V remove(K key) {
        if (key == null || get(key) == null) {
            return null;
        } else {
            V ansValue = get(key);
            int posi = getPosition(key);
            Collection<Node> Nodes = this.buckets[posi];
            Nodes.removeIf(i -> i.key.equals(key));
            return ansValue;
        }
    }

    @Override
    public V remove(K key, V value) {
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        class HashMapIter implements Iterator<K>{
            private int bucketIndex;
            private Collection<Node> curBucket = buckets[bucketIndex];
            private Iterator<Node> curIter = curBucket.iterator();

            HashMapIter() {
                this.bucketIndex = 0;
            }

            public boolean hasNext() {
                return this.bucketIndex < size() || curIter.hasNext();
            }

            public K next() {
                if (curIter.hasNext()) {
                    return curIter.next().key;
                } else {
                    this.bucketIndex += 1;
                    return this.next();
                }
            }
        }
        return new HashMapIter();
    }
}
