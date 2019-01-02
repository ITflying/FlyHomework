package com.hiabby.flyleetcode.treestructure.btree;

/**
 * @desc B-树，多叉树，用于提高磁盘搜索数据效率
 * @date 2019/01/02
 **/
public class BTree<K extends Comparable<K>, V> {
    private BTNode<K, V> mRoot;
    private int degree;
    private int number;

    public BTree(int degree){
        if (degree < 2){
            throw new IllegalArgumentException("根节点的度必须大于2");
        }
        this.degree = degree;
    }

    public class BTNode<K extends Comparable<K>, V>{

    }
}
