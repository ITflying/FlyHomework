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
     * 获取树的节点数量高度
     */
    @Test
    public void test_getTreeHeight() {
        BSTree<Integer> tree = initTree();

        // 树的节点数量
        int nodeNum = tree.getNodeNum();
        System.out.println("树的节点数量：" + nodeNum);
        Assert.assertEquals(nodeNum, 9);

        int height = tree.getTreeHeight();
        System.out.println("树的高度：" + height);
        Assert.assertEquals(height, 4);
    }

    /**
     * 以树的形式展示数据
     */
    @Test
    public void show(){
        BSTree<Integer> tree = initTree();
        tree.print();
    }

    // endregion

    // region 私有方法

    /**
     * 初始化树结构
     */
    private BSTree<Integer> initTree() {
        // 前序遍历的顺序插入
        List<Integer> keys = Lists.newArrayList(8, 3, 1, 6, 4 , 7 , 10, 14, 13);

        BSTree<Integer> bsTree = new BSTree<>();
        for (Integer key:keys){
            bsTree.insert(key);
        }
        return bsTree;
    }

    // endregion
}
