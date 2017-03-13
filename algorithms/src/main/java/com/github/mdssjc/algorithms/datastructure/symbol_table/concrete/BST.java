package com.github.mdssjc.algorithms.datastructure.symbol_table.concrete;

import com.github.mdssjc.algorithms.datastructure.linkedlist.NodeBST;
import com.github.mdssjc.algorithms.datastructure.symbol_table.OrderedST;

/**
 * Implementação de Symbol Table com Binary Search Tree.
 *
 * @author Marcelo dos Santos
 *
 * @param <Key>
 *     O tipo de dado da chave
 * @param <Value>
 *     O tipo de dado do valor
 */
public class BST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {

  private NodeBST<Key, Value> root;

  @Override
  public void put(final Key key, final Value value) {
    this.root = put(this.root, key, value);
  }

  private NodeBST<Key, Value> put(final NodeBST<Key, Value> x, final Key key, final Value value) {
    if (x == null) {
      return new NodeBST<>(key, value, 1);
    }

    final int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = put(x.left, key, value);
    } else if (cmp > 0) {
      x.right = put(x.right, key, value);
    } else {
      x.value = value;
    }
    x.n = size(x.left) + size(x.right) + 1;
    return x;
  }

  @Override
  public Value get(final Key key) {
    return get(this.root, key);
  }

  private Value get(final NodeBST<Key, Value> x, final Key key) {
    if (x == null) {
      return null;
    }

    final int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      return get(x.left, key);
    } else if (cmp > 0) {
      return get(x.right, key);
    }
    return x.value;
  }

  @Override
  public int size() {
    return size(this.root);
  }

  private int size(final NodeBST<Key, Value> x) {
    if (x == null) {
      return 0;
    } else {
      return x.n;
    }
  }

  @Override
  public Key min() {
    return min(this.root).key;
  }

  private NodeBST<Key, Value> min(final NodeBST<Key, Value> x) {
    if (x.left == null) {
      return null;
    }
    return min(x.left);
  }

  @Override
  public Key max() {
    return max(this.root).key;
  }

  private NodeBST<Key, Value> max(final NodeBST<Key, Value> x) {
    if (x.right == null) {
      return null;
    }
    return min(x.right);
  }

  @Override
  public Key floor(final Key key) {
    final NodeBST<Key, Value> x = floor(this.root, key);
    if (x == null) {
      return null;
    }
    return x.key;
  }

  private NodeBST<Key, Value> floor(final NodeBST<Key, Value> x, final Key key) {
    if (x == null) {
      return null;
    }

    final int cmp = key.compareTo(x.key);
    if (cmp == 0) {
      return x;
    }
    if (cmp < 0) {
      return floor(x.left, key);
    }
    final NodeBST<Key, Value> t = floor(x.right, key);
    if (t != null) {
      return t;
    } else {
      return x;
    }
  }

  @Override
  public Key ceiling(final Key key) {
    final NodeBST<Key, Value> x = ceiling(this.root, key);
    if (x == null) {
      return null;
    }
    return x.key;
  }

  private NodeBST<Key, Value> ceiling(final NodeBST<Key, Value> x, final Key key) {
    if (x == null) {
      return null;
    }

    final int cmp = key.compareTo(x.key);
    if (cmp == 0) {
      return x;
    }
    if (cmp > 0) {
      return floor(x.right, key);
    }
    final NodeBST<Key, Value> t = ceiling(x.left, key);
    if (t != null) {
      return t;
    } else {
      return x;
    }
  }

  @Override
  public int rank(final Key key) {
    return 0;
  }

  @Override
  public Key select(final int k) {
    return null;
  }

  @Override
  public Iterable<Key> keys(final Key lo, final Key hi) {
    return null;
  }
}
