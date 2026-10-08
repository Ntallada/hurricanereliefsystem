
public class reliefrequest {
    public int requestID;
    public String user;
    public String victimName;
    public String specialRequest;
    public String status;
    public boolean petNeeds;
    public enum location{
        //add zip codes later
    }
    public enum aidType{
        medical,
        trapped,
        other,
        //add more later
    }
    public enum emergencyLv{
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
    }
    
}
