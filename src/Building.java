public class Building
{
     private int buildingNumber;
     private int executed_time;
     private int total_time;

    Building()
    {
        buildingNumber = 0;
        executed_time = 0;
        total_time = 0;
    }

    // parameterized constructor to initiate the building values
    Building(int buildingNumber, int executed_time, int total_time)
    {
        this.buildingNumber = buildingNumber;
        this.executed_time= executed_time;
        this.total_time = total_time;
    }

    // Setter Method to set to the executed time
    public void setExecutedTime(int executed_time)
    {
        this.executed_time = executed_time;
    }

    // Getter Method to get the executed time
    public int getExecutedTime()
    {
        return this.executed_time;
    }

    // Getter Method to get the Building Number
    public int getBuildingNumber()
    {
        return this.buildingNumber;
    }

    // Getter Method to get the Total Time
    public int getTotalTime()
    {
        return this.total_time;
    }

}
