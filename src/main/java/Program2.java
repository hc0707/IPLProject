//Number of matches played per year of all the years in IPL.

import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class Program2 {
    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> yearlyMap = new HashMap<>();
        try {
            //Reading from file...........
            BufferedReader reader;
            FileReader fileReader = new FileReader(new File("./src/main/raw_csv_data/matches.csv"));
            reader = new BufferedReader(fileReader);
            String temp;
            reader.readLine();//ignoring 1st line
            //Iterating each line...........
            while (((temp = reader.readLine()) != null)) {
                String[] split = temp.split(",");
                String year = split[1];
                String winner = split[10];
                if (!winner.isEmpty()) {//In case we have matches that were draw or abandoned we don't include
                    if (!yearlyMap.containsKey(year)) {
                        yearlyMap.put(year, new HashMap<String, Integer>());
                    }
                    HashMap<String, Integer> teamMap = yearlyMap.get(year);
                    //check if winner team is mapped to some value and increment by 1. If not
                    //add a new key for that team and assign value 0 and then increment by 1
                    teamMap.put(winner, teamMap.getOrDefault(winner, 0) + 1);
                    yearlyMap.put(year, teamMap);
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
        for (Map.Entry<String,HashMap<String,Integer>> year:yearlyMap.entrySet()){
            System.out.println("___________________"+year.getKey()+"_____________________\n");
            for (Map.Entry<String,Integer> team:year.getValue().entrySet()) {
                System.out.println(team);
            }
            System.out.println();
        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/matchesWonByTeam")));
            stream.writeObject(yearlyMap);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

