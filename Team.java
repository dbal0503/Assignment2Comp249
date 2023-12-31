import java.io.Serializable;

public class Team implements Serializable {
   private String name;
   private String sport;
   private int year;
   private String record;
   private String flag;

    public Team(String name, String sport, String year, String record, String flag){
        this.name = name;
        this.sport = sport;
        this.year =  Integer.parseInt(year);
        this.record = record;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", sport='" + sport + '\'' +
                ", year=" + year +
                ", record='" + record + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
