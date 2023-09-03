//For the year 2015, runs scored by each team in a match

import java.io.*;
import java.util.*;

public class Program5 {
    public static void main(String[] args) {
        HashMap<Integer, HashMap<String,Integer>> map = new HashMap<>();
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
                String battingTeam = split[2];
                int totalRuns= Integer.parseInt(split[17]);// Means runs scored per ball
                int matchId= Integer.parseInt(split[0]);
                //For the year 2016 the match ids are in range of 577-636
                if (matchId>=518&&matchId<=576) {
                    if (!map.containsKey(matchId))
                        // index 0 denotes total runs scored and index 1 denotes total balls bowled
                        map.put(matchId, new HashMap<>());
                    HashMap<String, Integer> team = map.get(matchId);
                    team.put(battingTeam,team.getOrDefault(battingTeam,0)+totalRuns);
                    map.put(matchId,team);
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        //Displaying Results in console
        System.out.println("________Runs scored by both teams in a match________\n");
        for (Map.Entry<Integer,HashMap<String,Integer>> entry:map.entrySet()){
            System.out.println("\nMatch Id: "+entry.getKey());
            entry.getValue().entrySet().forEach(System.out::println);
        }

//        for(Map.Entry<String, double[]> entry:treeSet){
//            System.out.println(entry.getKey());
//        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/eachTeamScore")));
            stream.writeObject(map);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

