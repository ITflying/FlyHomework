package com.hiabby.flyleetcode.treestructure;

import com.hiabby.flyleetcode.treestructure.bstree.BSTree;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @desc AVL树的测试用例
 * @date 2018/11/15
 **/
public class BSTreeTest {

    // region 配置


    // endregion


    // region 公有方法


    /**
     * 三种遍历：前序、中序、后续
     */
    @Test
    public void test_preOrder() {
        BSTree<Integer> tree = initTree();
        tree.preOrder();
        tree.middleOrder();
        tree.backOrder();
    }

    /**
     * 查找指定值的结点
     */
    @Test
    public void test_find() {
        Integer key = 13;
        BSTree<Integer> tree = initTree();
        BSTree.BSTNode findNode = tree.recursiveSearch(key);
        System.out.println("通过递归查询到的结点：" + findNode + " | " + findNode.getKey());

        BSTree.BSTNode findNode2 = tree.commonSearch(key);
        System.out.println("通过非递归查询到的结点：" + findNode2 + " | " + findNode2.getKey());

        Assert.assertEquals(findNode, findNode2);
    }

    /**
     * 查找最大值和最小值
     */
    @Test
    public void test_find_max_min() {
        BSTree<Integer> tree = initTree();
        BSTree.BSTNode findMaxNode = tree.findMax();
        System.out.println("最大值：" + findMaxNode + " | " + findMaxNode.getKey());

        BSTree.BSTNode findMinNode = tree.findMin();
        System.out.println("最小值：" + findMinNode + " | " + findMinNode.getKey());
    }

    /**
     * 查找前驱结点和后继结点
     */
    @Test
    public void test_find_front_back() {
        Integer key = 13;
        BSTree<Integer> tree = initTree();
        BSTree.BSTNode findFrontNode = tree.findMiddleFrontNode(key);
        System.out.println("前驱结点：" + findFrontNode + " | " + findFrontNode.getKey());

        BSTree.BSTNode findBackNode = tree.findMiddleBackNode(key);
        System.out.println("后继结点：" + findBackNode + " | " + findBackNode.getKey());
    }

    /**
     * 删除和插入
     */
    @Test
    public void test_delete_insert() {
        Integer key = 6;
        BSTree<Integer> tree = initTree();

        System.out.println("\n 最开始的结果");
        tree.display();
//
//        tree.remove(key);
//        System.out.println("\n 删除后的结果");
//        tree.display();
//
//        tree.insert(key);
//        System.out.println("\n 再插入后的结果");
//        tree.display();

        // 指定层数
        System.out.println("\n 指定层数的结果");
        System.out.println(tree.getTargetLevelData(tree.getmRoot(), 2));
    }

    /**
     * 遍历输出结点
     */
    @Test
    public void test_print() {
        BSTree<Integer> tree = initTree();
        tree.print();
    }

    /**
     * 获取树的结点数量高度
     */
    @Test
    public void test_getTreeHeight() {
        BSTree<Integer> tree = initTree();

        // 树的结点数量
        int nodeNum = tree.getNodeNum();
        System.out.println("树的结点数量：" + nodeNum);
        Assert.assertEquals(nodeNum, 9);

        int height = tree.getTreeHeight();
        System.out.println("树的高度：" + height);
        Assert.assertEquals(height, 4);
    }

    /**
     * 以树的形式展示数据
     */
    @Test
    public void display() {
        BSTree<Integer> tree = initTree();
        tree.display();
    }

    // endregion

    // region 私有方法

    /**
     * 初始化树结构，以前序遍历的顺序插入
     */
    private BSTree<Integer> initTree() {
        // 前序遍历的顺序插入
        List<Integer> keys = Lists.newArrayList(8, 3, 1, 6, 4, 7, 10, 14, 13);

        BSTree<Integer> bsTree = new BSTree<>();
        for (Integer key : keys) {
            bsTree.insert(key);
        }
        return bsTree;
    }

    // endregion
}
