class Node
{
    int timeWorked;
    Building building;
    RBNode rbNode;

    public Node(int item, Building b, RBNode newRBNode)
    {
        timeWorked = item;
        building = b;
        rbNode = newRBNode;
    }

    void setTimeWorked(int key)
    {
        this.timeWorked = key;
    }

    Building getBuilding()
    {
        return building;
    }

    int getTimeWorked()
    {
        return timeWorked;
    }
}


public class MinHeap {

    int buildingCount;
    Node head[];

    MinHeap()
    {
        head = new Node[2000];
        buildingCount =0;
    }

    // Heapify up - after insertion
    public void percolateUp(int idxChild)
    {

        int idxParent;

        if (idxChild != 0)
        {

            idxParent = (idxChild - 1) / 2;

            Node nodeParent = head[idxParent];
            Node nodeChild = head[idxChild];

            if(nodeParent.getTimeWorked() > nodeChild.getTimeWorked())
            {
                head[idxParent] = nodeChild;
                head[idxChild] = nodeParent;
                percolateUp(idxParent);
            }
            else if(nodeParent.getTimeWorked() == nodeChild.getTimeWorked()  && (nodeParent.getBuilding().getBuildingNumber() > nodeChild.getBuilding().getBuildingNumber()))
            {
                head[idxParent] = nodeChild;
                head[idxChild] = nodeParent;
                percolateUp(idxParent);
            }
        }
    }

    // Heapify down after remove min operation
    public void goingDown(int idx) {
        int smallest = idx;
        int left = 2*idx + 1;
        int right = 2*idx + 2;

        if(left < buildingCount)
        {
            if(head[left].getTimeWorked() < head[smallest].getTimeWorked())
                smallest = left;
            else if((head[left].getTimeWorked() == head[smallest].getTimeWorked()) && (head[left].getBuilding().getBuildingNumber() < head[smallest].getBuilding().getBuildingNumber()))
                smallest = left;
        }


        if(right < buildingCount)
        {
            if(head[right].getTimeWorked() < head[smallest].getTimeWorked())
                smallest = right;
            else if(head[right].getTimeWorked()==head[smallest].getTimeWorked() && (head[right].getBuilding().getBuildingNumber()<head[smallest].getBuilding().getBuildingNumber()))
            {
                smallest = right;
            }
        }

        if(smallest != idx)
        {
            Node temp = head[idx];
            head[idx] = head[smallest];
            head[smallest] = temp;

            goingDown(smallest);
        }
    }


    // Removes the min node from the Heap.
    public Node removeMin()
    {
        if(buildingCount ==0)
            return null;

        Node temp = head[0];
        buildingCount--;

        if(buildingCount >0 )
        {
            head[0] = head[buildingCount];

            goingDown(0);
        }
        else
            head[0] = null;

        return temp;
    }

    // Insert a New node into the MinHeap.
    public void insertToHeap(Building b, RBNode newRBNode)
    {
        Node newNode = new Node(0, b, newRBNode);
        buildingCount++;
        int i = buildingCount - 1;
        head[i] = newNode;
        // Fix the min heap property if it is violated
        percolateUp(buildingCount -1);
    }

    // Insert an Old building, whose work is still left to be done, into the minHeap.
    public void insertToHeap(Node oldBuildingNode)
    {
        if(oldBuildingNode != null)
        {

            buildingCount++;
            int i = buildingCount - 1;

            head[i] = oldBuildingNode;
            // Fix the min heap property if it is violated
            percolateUp(buildingCount -1);

        }
    }



}