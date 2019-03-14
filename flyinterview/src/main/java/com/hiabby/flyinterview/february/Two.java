package com.hiabby.flyinterview.february;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * @desc: 白板遍历二叉树
 * @author: ITflying
 * @create: 2019-02-26 21:16
 **/
public class Two {
    public static void main(String[] args) {
        // 前序遍历的顺序插入
        List<Integer> keys = Lists.newArrayList(8, 3, 1, 6, 4, 7, 10, 14, 13);

        BSTree bsTree = new BSTree();
        keys.forEach(bsTree::insert);

        bsTree.preOrder();
    }
}


/**
 * 二叉树
 */
class BSTree<T extends Comparable<T>> {
    public BSTNode mRoot;

    public class BSTNode<T extends Comparable<T>> {
        T key;
        BSTNode<T> leftChild;
        BSTNode<T> rightChild;
        BSTNode<T> parent;

        public BSTNode(T key, BSTNode<T> leftChilde, BSTNode<T> rightChilde, BSTNode<T> parent) {
            this.key = key;
            this.leftChild = leftChilde;
            this.rightChild = rightChilde;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }
    }

    private void preOrder(BSTNode<T> node) {
        if (Objects.nonNull(node)) {
            System.out.println(node.getKey());
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    public void preOrder() {
        System.out.print("前序遍历：\n");
        preOrder(mRoot);
    }

    private void insert(BSTree<T> tree, BSTNode<T> insertNode) {
        int compareRes;
        BSTNode<T> insertNodeParent = null;
        BSTNode<T> compareNode = tree.mRoot;

        // 1. 首先比较父节点位置
        while (Objects.nonNull(compareNode)) {
            insertNodeParent = compareNode;

            compareRes = insertNode.key.compareTo(compareNode.key);
            if (compareRes > 0) {
                compareNode = compareNode.rightChild;
            } else if (compareRes < 0) {
                compareNode = compareNode.leftChild;
            } else {
                return;
            }
        }

        // 2. 再确定具体的插入位置插入
        insertNode.parent = insertNodeParent;
        if (Objects.isNull(insertNodeParent)) {
            tree.mRoot = insertNode;
        } else {
            compareRes = insertNode.key.compareTo(insertNodeParent.key);
            if (compareRes > 0) {
                insertNodeParent.rightChild = insertNode;
            } else if (compareRes < 0) {
                insertNodeParent.leftChild = insertNode;
            }
        }
    }

    public void insert(T key) {
        BSTNode<T> insertNode = new BSTNode<>(key, null, null, null);

        insert(this, insertNode);
    }

}