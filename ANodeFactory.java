package com.dianping.workflow;


import java.util.ArrayList;
import java.util.Arrays;

public class ANodeFactory {


// create a node graph like this
//        1
//      / | \
//     2  3  4
//    /  /|  |
//   5--' 6  |
//   \     \ /
//    `-----7

    public static ANode createNode() {

        ANode node1 = new ANode(1, 0);
        ANode node2 = new ANode(2, 1);
        ANode node3 = new ANode(3, 1);
        ANode node4 = new ANode(4, 1);
        ANode node5 = new ANode(5, 2);
        ANode node6 = new ANode(6, 1);
        ANode node7 = new ANode(7, 3);

        node1.setPostNodeList(Arrays.asList(node2, node3, node4));
        node2.setPostNodeList(Arrays.asList(node5));
        node3.setPostNodeList(Arrays.asList(node5, node6));
        node4.setPostNodeList(Arrays.asList(node7));
        node5.setPostNodeList(Arrays.asList(node7));
        node6.setPostNodeList(Arrays.asList(node7));
        node7.setPostNodeList(new ArrayList<ANode>());

        return node1;
    }

    public static int getNNode() {
        return 7;
    }
}
