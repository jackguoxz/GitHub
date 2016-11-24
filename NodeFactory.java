package com.dianping.workflow;


import java.util.ArrayList;
import java.util.Arrays;

public class NodeFactory {


// create a node graph like this
//        1
//      / | \
//     2  3  4
//    /  /|  |
//   5--' 6  |
//   \     \ /
//    `-----7

    public static Node createNode() {

        Node node1 = new Node(1, 0);
        Node node2 = new Node(2, 1);
        Node node3 = new Node(3, 1);
        Node node4 = new Node(4, 1);
        Node node5 = new Node(5, 2);
        Node node6 = new Node(6, 1);
        Node node7 = new Node(7, 3);

        node1.setPostNodeList(Arrays.asList(node2, node3, node4));
        node2.setPostNodeList(Arrays.asList(node5));
        node3.setPostNodeList(Arrays.asList(node5, node6));
        node4.setPostNodeList(Arrays.asList(node7));
        node5.setPostNodeList(Arrays.asList(node7));
        node6.setPostNodeList(Arrays.asList(node7));
        node7.setPostNodeList(new ArrayList<Node>());

        return node1;
    }

    public static int getNNode() {
        return 7;
    }
}
