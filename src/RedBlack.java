import java.util.List;
import java.util.ArrayList;

class RBNode
{
    Building building;      // building object
    int data;               // holds the building id
    RBNode parent;          // this is pointing to the parent
    RBNode left;            // this is pointing to left child
    RBNode right;           // this is pointing to right child
    int color;              // This is 1 for Red and 0 for Black

    RBNode() {}

    RBNode(int data)
    {
        building = new Building(0,0,0);
    }
}

public class RedBlack
{
    private RBNode root;
    private RBNode LeafNode;

    public RedBlack()
    {
        LeafNode = new RBNode(0);
        LeafNode.color = 0;
        LeafNode.left = null;
        LeafNode.right = null;
        root = LeafNode;
    }


    // Search the Node that contains the token.
    private RBNode searchTreeHelper(RBNode node, int token)
    {
        if (node == LeafNode || token == node.data) {
            if(node == LeafNode)
            {
                Building building = new Building(0, 0, 0);
                RBNode dummyNode = new RBNode();
                dummyNode.building = building;
                return dummyNode;
            }

            return node;
        }

        if (token < node.data)
        {
            return searchTreeHelper(node.left, token);
        }

        return searchTreeHelper(node.right, token);
    }

    // restore the balance of the RBTree after the deletion.
    private void fixRedBlackDelete(RBNode rbNode)
    {
        RBNode tempNode;
        while (rbNode != root && rbNode.color == 0)
        {
            if (rbNode == rbNode.parent.left)
            {
                tempNode = rbNode.parent.right;

                if (tempNode.color == 1)
                {
                    tempNode.color = 0;
                    rbNode.parent.color = 1;
                    leftRotateTree(rbNode.parent);
                    tempNode = rbNode.parent.right;
                }

                if(tempNode == LeafNode)
                    return;

                if (tempNode.left.color == 0 && tempNode.right.color == 0)
                {
                    tempNode.color = 1;
                    rbNode = rbNode.parent;
                }
                else
                    {
                        if (tempNode.right.color == 0)
                        {
                            tempNode.left.color = 0;
                            tempNode.color = 1;
                            rightRotateTree(tempNode);
                            tempNode = rbNode.parent.right;
                        }

                        tempNode.color = rbNode.parent.color;
                        rbNode.parent.color = 0;
                        tempNode.right.color = 0;
                        leftRotateTree(rbNode.parent);
                        rbNode = root;
                    }
            }
            else {
                tempNode = rbNode.parent.left;
                if (tempNode.color == 1)
                {
                    tempNode.color = 0;
                    rbNode.parent.color = 1;
                    rightRotateTree(rbNode.parent);
                    tempNode = rbNode.parent.left;
                }

                if(tempNode==LeafNode)
                    return;

                if (tempNode.right.color == 0 && tempNode.right.color == 0)
                {
                    tempNode.color = 1;
                    rbNode = rbNode.parent;
                }
                else {
                    if (tempNode.left.color == 0)
                    {
                        tempNode.right.color = 0;
                        tempNode.color = 1;
                        leftRotateTree(tempNode);
                        tempNode = rbNode.parent.left;
                    }

                    tempNode.color = rbNode.parent.color;
                    rbNode.parent.color = 0;
                    tempNode.left.color = 0;
                    rightRotateTree(rbNode.parent);
                    rbNode = root;
                }
            }
        }
        rbNode.color = 0;
    }

    
    private void transplantRedBlack(RBNode nodeU, RBNode nodeV)
    {
        if (nodeU.parent == null)
        {
            root = nodeV;
        }
        else if (nodeU == nodeU.parent.left)
        {
            nodeU.parent.left = nodeV;
        }
        else {
            nodeU.parent.right = nodeV;
        }

        nodeV.parent = nodeU.parent;
    }

    // Helper Method for RBTree Deletion
    private void deleteRedBlackNodeHelper(RBNode nodeToDelete, int token)
    {
        RBNode nodeZ = LeafNode;
        RBNode nodeX, nodeY;
        while (nodeToDelete != LeafNode)
        {
            if (nodeToDelete.data == token)
            {
                nodeZ = nodeToDelete;
            }

            if (nodeToDelete.data <= token)
            {
                nodeToDelete = nodeToDelete.right;
            }
            else {
                nodeToDelete = nodeToDelete.left;
            }
        }

        if (nodeZ == LeafNode)
        {
            return;
        }

        nodeY = nodeZ;

        int yOriginalColor = nodeY.color;

        if (nodeZ.left == LeafNode)
        {
            nodeX = nodeZ.right;
            transplantRedBlack(nodeZ, nodeZ.right);
        }
        else if (nodeZ.right == LeafNode)
        {
            nodeX = nodeZ.left;
            transplantRedBlack(nodeZ, nodeZ.left);
        }
        else {
            nodeY = findMinimumNode(nodeZ.right);
            yOriginalColor = nodeY.color;
            nodeX = nodeY.right;

            if (nodeY.parent == nodeZ)
            {
                nodeX.parent = nodeY;
            }
            else {
                transplantRedBlack(nodeY, nodeY.right);
                nodeY.right = nodeZ.right;
                nodeY.right.parent = nodeY;
            }

            transplantRedBlack(nodeZ, nodeY);
            nodeY.left = nodeZ.left;
            nodeY.left.parent = nodeY;
            nodeY.color = nodeZ.color;
        }

        if (yOriginalColor == 0)
        {
            fixRedBlackDelete(nodeX);
        }
    }


    // Restore the balance of RBTree after insertion of a node into the RBTree.
    private void fixRedBlackInsert(RBNode nodeX)
    {
        RBNode nodeY;
        while (nodeX.parent.color == 1)
        {
            if (nodeX.parent == nodeX.parent.parent.right)
            {
                nodeY = nodeX.parent.parent.left;

                if (nodeY.color == 1)
                {
                    nodeY.color = 0;
                    nodeX.parent.color = 0;
                    nodeX.parent.parent.color = 1;
                    nodeX = nodeX.parent.parent;
                } else {
                    if (nodeX == nodeX.parent.left)
                    {
                        nodeX = nodeX.parent;
                        rightRotateTree(nodeX);
                    }

                    nodeX.parent.color = 0;
                    nodeX.parent.parent.color = 1;

                    leftRotateTree(nodeX.parent.parent);
                }
            } else {
                nodeY = nodeX.parent.parent.right;

                if (nodeY.color == 1) {
                    nodeY.color = 0;
                    nodeX.parent.color = 0;
                    nodeX.parent.parent.color = 1;
                    nodeX = nodeX.parent.parent;
                } else {
                    if (nodeX == nodeX.parent.right) {
                        nodeX = nodeX.parent;
                        leftRotateTree(nodeX);
                    }
                    nodeX.parent.color = 0;
                    nodeX.parent.parent.color = 1;
                    rightRotateTree(nodeX.parent.parent);
                }
            }

            if (nodeX == root)
            {
                break;
            }
        }
        root.color = 0;
    }

    // Search in an RBTree
    public RBNode searchTree(int k)
    {
        return searchTreeHelper(this.root, k);
    }

    public List<RBNode> searchTreeRange(int low, int high)
    {
        List<RBNode> result = new ArrayList<>();
        searchTreeRangeHelper(this.root, result, low, high);

        return  result;
    }

    private void searchTreeRangeHelper(RBNode node, List<RBNode> result, int low, int high)
    {
        if (node == LeafNode)
            return;

        int compareLow = low - node.building.getBuildingNumber();
        int compareHigh = high - node.building.getBuildingNumber();

        if(compareLow < 0)
            searchTreeRangeHelper(node.left, result, low, high);

        if (compareLow <= 0 && compareHigh >= 0)
            result.add(node);

        if (compareHigh > 0)
            searchTreeRangeHelper(node.right, result, low, high);
    }

    // Find the min node in the RBTree
    public RBNode findMinimumNode(RBNode node)
    {
        while (node.left != LeafNode) {
            node = node.left;
        }
        return node;
    }
    
    public void leftRotateTree(RBNode nodeA) {
        RBNode nodeB = nodeA.right;
        nodeA.right = nodeB.left;
        if (nodeB.left != LeafNode) {
            nodeB.left.parent = nodeA;
        }
        nodeB.parent = nodeA.parent;
        if (nodeA.parent == null) {
            this.root = nodeB;
        } else if (nodeA == nodeA.parent.left) {
            nodeA.parent.left = nodeB;
        } else {
            nodeA.parent.right = nodeB;
        }
        nodeB.left = nodeA;
        nodeA.parent = nodeB;
    }

    public void rightRotateTree(RBNode nodeA) {
        RBNode nodeB = nodeA.left;
        nodeA.left = nodeB.right;
        if (nodeB.right != LeafNode) {
            nodeB.right.parent = nodeA;
        }
        nodeB.parent = nodeA.parent;
        if (nodeA.parent == null) {
            this.root = nodeB;
        } else if (nodeA == nodeA.parent.right) {
            nodeA.parent.right = nodeB;
        } else {
            nodeA.parent.left = nodeB;
        }
        nodeB.right = nodeA;
        nodeA.parent = nodeB;
    }

    public RBNode insertNewRBNode(Building b) {
        RBNode node = new RBNode();
        node.building = b;  // assign the building object to the node parameter
        node.parent = null;
        node.data = b.getBuildingNumber();
        node.left = LeafNode;
        node.right = LeafNode;
        node.color = 1; // The colour of new nodes must always be red before fixing the tree

        RBNode y = null;
        RBNode x = this.root;

        while (x != LeafNode)
        {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;

        if (y == null)
        {
            root = node;
        }
        else if (node.data < y.data)
        {
            y.left = node;
        }
        else {
            y.right = node;
        }

        if (node.parent == null)
        {
            node.color = 0;
            return node;
        }

        if (node.parent.parent == null)
        {
            return node;
        }

        fixRedBlackInsert(node);

        return node;
    }


    public void deleteRedBlackNode(int data)
    {
        deleteRedBlackNodeHelper(this.root, data);
    }

    public String displayBuilding(int buildingNum)
    {
        RBNode result = searchTree(buildingNum);

        String output = "(" + result.building.getBuildingNumber() + "," + result.building.getExecutedTime() + "," + result.building.getTotalTime() + ")";

        return output;
    }

    public String displayBuildlingRange(int low, int high)
    {
        List<RBNode> searchRangeResult =  searchTreeRange(low, high);
        StringBuilder sb = new StringBuilder();

        for(RBNode i : searchRangeResult)
        {
            sb.append("(").append(i.building.getBuildingNumber()).append(",").append(i.building.getExecutedTime()).append(",").append(i.building.getTotalTime()).append(")").append(",");
        }
        String result = sb.substring(0, sb.length() - 1);

        return result;
    }
}
