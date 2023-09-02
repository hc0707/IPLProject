//For the year 2016 get the extra runs conceded per team.

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Program3 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            //Reading from file...........
            BufferedReader reader;
            FileReader fileReader = new FileReader(new File("./src/main/raw_csv_data/deliveries.csv"));
            reader = new BufferedReader(fileReader);
            String temp;
            reader.readLine();//ignoring 1st line
            //Iterating each line...........
            while (((temp = reader.readLine()) != null)) {
                String[] split = temp.split(",");
                int matchId = Integer.parseInt(split[0]);
                String team = split[2];
                int extraRuns= Integer.parseInt(split[16]);
                //For the year 2016 the match ids are in range of 577-636
                if (matchId>=577&&matchId<=636) {
                    //check if team is mapped to some value and increment by extra runs. If not
                    //add a new key for that team and assign value 0 and then increment by extra runs
                    map.put(team,map.getOrDefault(team,0)+extraRuns);
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Displaying Results in console
        System.out.println("________Extra runs conceded in year 2016________\n");
        for (Map.Entry<String,Integer> runs:map.entrySet()){
            System.out.println(runs);
        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/extraRunsConceded")));
            stream.writeObject(map);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

