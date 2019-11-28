import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class risingCity
{

    Node presentNode;                   // present working building node
    int masterTimeTracker;              // tracks the global time
    int countTemp;                      // tracks the no. of days worked


    // Invoked by main to keep working until risingCity is built completely.
    public void workUntilEnd(File fileInput, FileWriter outputFileWriterObj)  throws Exception
    {

        String presentLine = "";

        masterTimeTracker = 1;
        countTemp = 0;
        presentNode = null;
        int dayNum;

        String wordsOfPresentLine[];

        BuildingDevMaster constructCitySimulate = new BuildingDevMaster();
        constructCitySimulate.setOutputToPrintWriter(outputFileWriterObj);

        BufferedReader br = new BufferedReader(new FileReader(fileInput));

        while((presentLine = br.readLine()) != null)
        {
            if(presentLine.contentEquals(""))
                continue;

            wordsOfPresentLine = presentLine.split(": ");
            dayNum = Integer.parseInt(wordsOfPresentLine[0]);

            while(dayNum > masterTimeTracker)
            {
                masterTimeTracker++;
                countTemp = oneDayWork(constructCitySimulate, "");
            }


            if(wordsOfPresentLine[1].charAt(0) == 'I')
            {
                constructCitySimulate.insertBuilding(wordsOfPresentLine[1]);
                countTemp = oneDayWork(constructCitySimulate, "");
                masterTimeTracker++;
            }

            else if(wordsOfPresentLine[1].charAt(0) == 'P')
            {
                countTemp = oneDayWork(constructCitySimulate, wordsOfPresentLine[1]);
                masterTimeTracker++;

            }
            else {
                System.out.println("Error Occurred.");
            }
        }

        while((constructCitySimulate.minHeapObj.buildingCount > 0) || (presentNode != null))
        {
            countTemp = oneDayWork(constructCitySimulate, "");
            masterTimeTracker++;
        }

        constructCitySimulate.writeToFileObj.close();

    }

    // Performs the work of a single day.
    private int oneDayWork(BuildingDevMaster constructCitySimulate, String displayStr)
    {
        Building currentBuilding;
        boolean isFinished;

        if(countTemp == 0 || presentNode == null)
        {
            presentNode = constructCitySimulate.getMinOfMinHeap();
        }

        if(presentNode != null)
        {
            currentBuilding = presentNode.getBuilding();
            countTemp++;
            isFinished = constructCitySimulate.buildBuilding(presentNode);

            if(isFinished == true)
            {
                countTemp = 0;

                if(displayStr.length() > 0)
                {
                    constructCitySimulate.displayBuildingPreprocess(displayStr);
                    displayStr = "";
                }

                constructCitySimulate.RBNodeDeletion(currentBuilding.getBuildingNumber());
                presentNode = null;

                String output = "(" + currentBuilding.getBuildingNumber() + "," + masterTimeTracker + ")";
                constructCitySimulate.writeToFileObj.println(output);
            }

            if(countTemp == 5)
            {
                countTemp = 0;              // reset the temporary counter
                constructCitySimulate.reinsertWorkingBuilding(presentNode);
            }

            if(displayStr.length() > 0)
            {
                constructCitySimulate.displayBuildingPreprocess(displayStr);
                //displayStr = "";
            }
        }
        return countTemp;
    }

    public static void main(String args[]) throws Exception
    {

        if(args.length > 1)
        {
            System.out.println("Argument count incorrect!");
            return;
        }

        Path relativePathCurrent = Paths.get("");       // get the path
        
        String absolutePathCurrent = relativePathCurrent.toAbsolutePath().toString();

        String nameOfFile = absolutePathCurrent + "/" + args[0];
        
        File fileInput = new File(nameOfFile);
        
        FileWriter outputFileWriterObj = new FileWriter(absolutePathCurrent + "/output_file.txt");
        
        risingCity rcObj = new risingCity();
        rcObj.workUntilEnd(fileInput, outputFileWriterObj);
    }


}