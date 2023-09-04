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
                int matchId= Integer.parseInt(split[0]);
                int totalRuns= Integer.parseInt(split[17]);// Means runs scored per ball
                int wideRuns= Integer.parseInt(split[10]);//wide balls are not counted
                int noBallRuns= Integer.parseInt(split[13]);//no balls are not counted
                int byeRuns= Integer.parseInt(split[11]);//byes are not charged to the bowler's analysis
                int legByeRuns= Integer.parseInt(split[12]);//leg byes are not charged to the bowler's analysis
                int netRuns = totalRuns - (legByeRuns + byeRuns);
                //For the year 2015 the match ids are in range of 518-576
                if (matchId>=518&&matchId<=576) {
                    if (!map.containsKey(bowlerName))
                        // index 0 denotes total runs scored and index 1 denotes total balls bowled
                        map.put(bowlerName, new double[]{0.0, 0.0});

                    double[] runsAndBalls = map.get(bowlerName);
                    runsAndBalls[0] += netRuns;// adding current ball's runs scored to the existing total runs
                    if (wideRuns==0&&noBallRuns==0)
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
        //Storing bowler name and economy rate in a separate hashmap
        HashMap<String, Double> finalMap = new HashMap<>();
        for (Map.Entry<String,double[]> entry:
                map.entrySet()) {
            //economy rate(6*Total runs/total balls bowled).
            finalMap.put(entry.getKey(),6*entry.getValue()[0]/entry.getValue()[1]);
        }
        //Sorting to display the most economical bowler------------------------
        //Defining custom comparator
        Comparator<Map.Entry<String, Double>> comparator = new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                //Comparing the economy rate. Lesser the average, more the economy rate for the bowler
                if (e1.getValue() < e2.getValue())
                    return -1;
                return 1;
            }
        };
        //Sorting using tree set
//        TreeSet<Map.Entry<String, double[]>> treeSet = new TreeSet<>(comparator);
//        treeSet.addAll(map.entrySet());
        //Sorting using array list
        ArrayList<Map.Entry<String,Double>> list = new ArrayList<>(finalMap.entrySet());
        Collections.sort(list, comparator);
//        //Displaying Results in console
        System.out.println("________Most Economical Bowler________\n");
        for (Map.Entry<String,Double> entry:
             list) {
            System.out.println(entry.getKey()+": "+String.format("%.2f",entry.getValue()));
        }

//        for(Map.Entry<String, double[]> entry:treeSet){
//            System.out.println(entry.getKey());
//        }
        //Serialization for saving the object of hashmap in file in local storage(object_data)
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("./src/main/object_data/mostEconomicalBowler")));
            stream.writeObject(finalMap);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

