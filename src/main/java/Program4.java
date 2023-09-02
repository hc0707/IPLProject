//For the year 2015 get the top economical bowlers.

import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program4 {
    public static void main(String[] args) {
        HashMap<String, double[]> map = new HashMap<>();
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
                String bowlerName = split[8];
                int totalRuns= Integer.parseInt(split[17]);// Means runs scored per ball
                int matchId= Integer.parseInt(split[0]);
                //For the year 2016 the match ids are in range of 577-636
                if (matchId>=518&&matchId<=576) {
                    if (!map.containsKey(bowlerName))
                        // index 0 denotes total runs scored and index 1 denotes total balls bowled
                        map.put(bowlerName, new double[]{0.0, 0.0});

                    double[] runsAndBalls = map.get(bowlerName);
                    runsAndBalls[0]+=totalRuns;// adding current ball's runs scored to the existing total runs
                    runsAndBalls[1]++;//Increment the balls bowled by 1.
                    map.put(bowlerName,runsAndBalls);
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Sorting to display the most economical bowler------------------------
        //Defining custom comparator
        Comparator<Map.Entry<String, double[]>> comparator = new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, double[]> e1, Map.Entry<String, double[]> e2) {
                //Comparing the average(Total runs/total balls bowled). Lesser the average, more the economy
                //rate for the bowler
                if ((e1.getValue()[0] / e1.getValue()[1]) < (e2.getValue()[0] / e2.getValue()[1]))
                    return -1;
                return 1;
            }
        };
        //Sorting using tree set
//        TreeSet<Map.Entry<String, double[]>> treeSet = new TreeSet<>(comparator);
//        treeSet.addAll(map.entrySet());
        //Sorting using array list
        ArrayList<Map.Entry<String,double[]>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, comparator);
//        //Displaying Results in console
        System.out.println("________Most Economical Bowler________\n");
        for (Map.Entry<String,double[]> entry:
             list) {
            System.out.println(entry.getKey());
        }

//        for(Map.Entry<String, double[]> entry:treeSet){
//            System.out.println(entry.getKey());
//        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/mostEconomicalBowler")));
            stream.writeObject(map);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

