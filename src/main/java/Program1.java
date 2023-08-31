//Number of matches played per year of all the years in IPL.

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Program1 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = null;
        try {
            //Reading from file...........
            FileReader fileReader = new FileReader(new File("./src/main/raw_csv_data/matches.csv"));
            BufferedReader reader = new BufferedReader(fileReader);
            String temp;
            map = new HashMap<>();
            reader.readLine();//ignoring 1st line
            //Iterating each line...........
            while (((temp = reader.readLine()) != null)) {
                String year = temp.split(",")[1];
                //check if year is mapped to some value and increment by 1. If not add a new key
                //for that year and assign value 0 and then increment by 1
                map.put(year, map.getOrDefault(year, 0) + 1);
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Displaying Results in console
        System.out.println("_______Matches Played in each year_________\n");
        for (Map.Entry<String, Integer> year :
                map.entrySet()) {
            System.out.println(year);
        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/matchesPlayed")));
            stream.writeObject(map);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

