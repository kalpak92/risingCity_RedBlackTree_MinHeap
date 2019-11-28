import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BuildingDevMaster
{

    MinHeap minHeapObj;
    RedBlack rbObj;
    PrintWriter writeToFileObj;

    BuildingDevMaster() throws IOException
    {
        minHeapObj = new MinHeap();
        rbObj = new RedBlack();
    }

    // Creates the PrintWriter Object to write to the output file.
    public void setOutputToPrintWriter(FileWriter outputToPrintWriter)
    {
        writeToFileObj = new PrintWriter(outputToPrintWriter);
    }

    // Calls the removeMin function of MinHeap and returns the corresponding Node.
    public Node getMinOfMinHeap() {
        return minHeapObj.removeMin();
    }

    // Increments the execution time of the building
    public boolean buildBuilding(Node currentBuildingNode)
    {
        int currentExecutionTime = currentBuildingNode.getBuilding().getExecutedTime() + 1;

        currentBuildingNode.getBuilding().setExecutedTime(currentExecutionTime);
        currentBuildingNode.setTimeWorked(currentExecutionTime);

        if(currentExecutionTime == currentBuildingNode.getBuilding().getTotalTime())
            return true;                                                                    // building has finished construction. So remove it.
        else
            return false;
    }

    // Writes to the output file.
    public void displayBuildingPreprocess(String output)
    {
        String words[]= output.split("\\(");

        //String firstWord = words[0];
        String extractText = words[1].substring(0, words[1].length() - 1);

        if(extractText.contains(","))
        {
            String idx_list[] = extractText.split(",");

            int startIdx = Integer.parseInt(idx_list[0]);
            int endIdx = Integer.parseInt(idx_list[1]);

            writeToFileObj.println(rbObj.displayBuildlingRange(startIdx, endIdx));
        }
        else {
            int idx = Integer.parseInt(extractText);
            writeToFileObj.println(rbObj.displayBuilding(idx));
        }
    }

    // Pre-processes the string from the input file and inserts the building into the minHeap and the Red Black Tree.
    public void insertBuilding(String string) throws Exception
    {
        String words[]= string.split("\\(");
        String extractWord = words[1].substring(0, words[1].length() -1);

        String indexes[] = extractWord.split(",");

        int newBldngNo = Integer.parseInt(indexes[0]);
        int newTotalTimeRequired = Integer.parseInt(indexes[1]);

        Building objBuilding = new Building(newBldngNo, 0, newTotalTimeRequired);

        RBNode found = rbObj.searchTree(newBldngNo);

        if(found.building.getBuildingNumber() !=0)
            throw new Exception("Building already present!");

        RBNode newRBNode = rbObj.insertNewRBNode(objBuilding);
        minHeapObj.insertToHeap(objBuilding, newRBNode);

    }

    // If the current building on which work is being done is still incomplete, re-insert it into the minHeap.
    public void reinsertWorkingBuilding(Node insertNode) {
        minHeapObj.insertToHeap(insertNode);
    }

    // Delete the building from the Red Black Tree.
    public void RBNodeDeletion(int buildingNum) {
        rbObj.deleteRedBlackNode(buildingNum);
    }


}