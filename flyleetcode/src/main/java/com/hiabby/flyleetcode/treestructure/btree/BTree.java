package com.hiabby.flyleetcode.treestructure.btree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * @desc B-树，多叉树，用于提高磁盘搜索数据效率
 * @date 2019/01/02
 * @thanks https://blog.csdn.net/qq_29864971/article/details/79612967 author:V_Pan
 **/
public class BTree<K extends Comparable<K>, V> {
    private static int limitRootDegree = 2;

    private BTNode<K, V> mRoot;

    /**
     * 度
     */
    private int degree;
    /**
     * 树中结点的数量
     */
    private int number;

    public BTree(int degree) {
        // 根结点至少有两个子女
        if (degree < limitRootDegree) {
            throw new IllegalArgumentException("根结点的度必须大于2");
        }
        this.mRoot = null;
        this.number = 0;
        this.degree = degree;
    }

    public class BTNode<K extends Comparable<K>, V> {
        /**
         * 父结点
         */
        BTNode<K, V> parent;

        /**
         * 存放的键值对列表，也就是关键字
         * Entry 为下面定义的匿名内部类
         */
        List<Entry<K, V>> keyList;

        /**
         * 子树指针的列表
         */
        List<BTNode<K, V>> pointers;

        public BTNode() {
            parent = null;
            keyList = new LinkedList<Entry<K, V>>();
            pointers = new LinkedList<BTNode<K, V>>();
        }

        /**
         * 返回键值对的数量
         *
         * @return
         */
        public int getKeyNum() {
            return keyList.size();
        }

        /**
         * 判断当前结点是否已满
         * 每个结点可包含最多2t-1个关键字。所以一个内结点至多可有2t个子女。如果一个结点恰好有2t-1个关键字，我们就说这个结点是满的
         * （而B*树作为B树的一种常用变形，B*树中要求每个内结点至少为2/3满，而不是像这里的B树所要求的至少半满）
         *
         * @return
         */
        public boolean isFull() {
            return getKeyNum() == (2 * degree - 1);
        }

        /**
         * 判断是否为叶子结点
         *
         * @return
         */
        public boolean isLeafNode() {
            return this.pointers.size() == 0;
        }


        /**
         * 判断当前结点的键值对是否符合约束条件
         *
         * @return
         */
        public boolean isQualified() {
            int keyNum = getKeyNum();
            if (Objects.nonNull(mRoot)) {
                if (keyNum < (degree - 1)) {
                    throw new IllegalArgumentException("关键字数量不能最少不能小于（度数-1）！");
                } else if (keyNum > (2 * degree - 1)) {
                    throw new IllegalArgumentException("每个结点最多可包含2t-1个关键字！");
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "结点 [关键字：" + keyList + "]" + ", 数量：" + getKeyNum();
        }

    }

    /**
     * 返回容器的大小
     *
     * @return
     */
    public int size() {
        return number;
    }

    /**
     * 判断容器是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 匿名内部类，键值对（关键字）
     */
    private static class Entry<K extends Comparable<K>, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 判断是否相同
         *
         * @param object 与之比较的对象
         * @return
         */
        @Override
        public final boolean equals(Object object) {
            if (Objects.equals(object, this)) {
                return true;
            }
            if (object instanceof Entry) {
                Entry<?, ?> temp = (Entry<?, ?>) object;
                if (Objects.equals(key, temp.key) && Objects.equals(value, temp.value)) {
                    return true;
                }
            }
            return false;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "实体[key = " + key + ",value=" + value + "]";
        }
    }

    /**
     * 查找方法
     * B树的查找方法跟二叉树一致，递归遍历循环比较。不同的是B树具有多个key，需要每个都比较，为了提高性能，可以使用二分法加速结点的查找
     *
     * @param node 结点
     * @param key  值
     * @return
     */
    public Entry<K, V> search(BTNode<K, V> node, K key) {
        int pos = 0;
        Entry<K, V> entry, next;

        // 1. 循环本结点内的值，判断是否符合要求
        while (pos < node.getKeyNum()) {
            // 1.1 内部元素是否符合要求
            entry = node.keyList.get(pos);
            if (key.equals(entry.getKey())) {
                return entry;
            }

            K lastRightKey = node.keyList.get(node.getKeyNum() - 1).getKey();
            if (key.compareTo(lastRightKey) > 0) {
                // 1.2 如果大于最右结点的值说明需要要遍历子结点
                pos = node.getKeyNum();
            } else {
                // 1.3 如果不大于最右结点，那么就需要继续判断
                next = (pos == node.getKeyNum() - 1) ? null : node.keyList.get(pos + 1);
                if (key.compareTo(entry.getKey()) < 0) {
                    // 1.3.1 小于说明在左结点
                    pos = 0;
                    break;
                } else if (key.compareTo(entry.getKey()) > 0 && key.compareTo(next.getKey()) < 0) {
                    // 1.3.2 如果值大于当前结点但是小于下一个结点的值，说明在当前子结点中
                    pos++;
                    break;
                }
            }
            pos++;
        }

        // 2. 最后判断是否有叶子结点，有的话往下遍历循环
        if (node.isLeafNode()) {
            return null;
        } else {
            // pos对应的是子树指向的链表的位置，用一个尾递归。
            return search(node.pointers.get(pos), key);
        }
    }

    /**
     * 获取插入的位置
     *
     * @param current
     * @param insert
     * @return
     */
    private int getPosition(BTNode<K, V> current, Entry<K, V> insert) {
        List<Entry<K, V>> keyList = current.keyList;
        int index = 0;
        Entry<K, V> keyEntry, next;

        for (int pos = 0; pos < keyList.size(); pos++) {
            keyEntry = keyList.get(pos);
            K lastKey = keyList.get(keyList.size() - 1).getKey();

            if (insert.getKey().compareTo(lastKey) > 0) {
                // 如果插入的值大于最后的值，就插入到最后的位置
                index = keyList.size();
            } else {

                next = (pos == current.getKeyNum() - 1) ? null : keyList.get(pos + 1);
                if (insert.getKey().compareTo(keyEntry.getKey()) < 0) {
                    // 在左边位置
                    index = 0;
                    break;
                } else if (insert.getKey().compareTo(next.getKey()) <= 0) {
                    // 在中间位置
                    index = pos + 1;
                    break;
                }
            }
        }

        return index;
    }

    /**
     * 分裂，B树
     *
     * @param node
     * @return
     */
    private BTNode<K, V> splitNode(BTNode<K, V> node) {
        return null;
    }


    /**
     * 插入结点
     * 1、先判断根节点是否为空，为空新建结点
     * 2、判断结点是否已经满了，满了就分裂
     * 3、如果结点为叶子结点就插入，不是则向下递归
     *
     * @param current
     * @param entry
     */
    private void insertAction(BTNode<K, V> current, Entry<K, V> entry) {
        // 1. 判断根节点是否为空，为空则新建结点
        if (Objects.isNull(mRoot)) {
            mRoot = new BTNode<>();
            current = mRoot;
        }

        // 2. 如果当前结点已满，需要分裂
        if (current.isFull()) {
            // 分裂
            current = splitNode(current);
        }

        // 3. 判断是否为叶子结点，也就是结点数量为零，是则插入，不是则向下递归
        int index = getPosition(current, entry);
        if (current.isLeafNode()) {
            // 插入
            current.keyList.add(index, entry);
            number++;
            return;
        } else {
            // 向下递归查找
            insertAction(current.pointers.get(index), entry);
        }
    }

    public void insert(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        insertAction(mRoot, entry);
    }
}
