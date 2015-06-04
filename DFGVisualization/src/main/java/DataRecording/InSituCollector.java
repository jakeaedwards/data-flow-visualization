package DataRecording;

import Data.InSituDataSet;
import Visualization.Visualizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jake on 3/23/2015.
 */
public class InSituCollector{

    private static Visualizer visualizer;

    public InSituCollector(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    /**
     * Collects a dataset taken from an external job graph and creates a local copy. This is achieved through storing
     * the contained data and then reading it into a new dataset which has the InSituCollector's ExecutionEnvironment
     * @param data External DataSet to be collected
     */
    public void collect(int id, DataSet data, Class... c){

        //Initialize local data set
        ArrayList<Tuple> dataSet = new ArrayList<>();

        try {
            System.out.println(org.apache.flink.core.fs.FileSystem.getLocalFileSystem().isDistributedFS());
            System.out.println(FileSystems.getDefault().getClass().toString());
            //Local (Non-HDFS) filesystem
            if (!org.apache.flink.core.fs.FileSystem.getLocalFileSystem().isDistributedFS()) {

                String outputPath = "C:\\Users\\Jake\\Desktop\\TestOutput";

                //Write external data set to CSV
                data.writeAsCsv(outputPath, org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE);

                //Read data originally from external data set into internal one
                File dir = new File(outputPath);
                File[] directoryListing = dir.listFiles();
                BufferedReader reader = null;
                String line;

                for (File file : directoryListing) {
                    try {
                        reader = new BufferedReader(new FileReader(file.getPath()));
                        while ((line = reader.readLine()) != null) {
                            Tuple addedTuple = parseTuple(line, c);
                            dataSet.add(addedTuple);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                visualizer.addData(new InSituDataSet(id,dataSet));
                //dir.deleteOnExit();
            }
            else{
                //Write dataset to HDFS
                FileSystem hdfs = FileSystem.get(new Configuration());
                Path workingDir = hdfs.getWorkingDirectory();
                Path outputPath = new Path("/CollectorOutput");
                outputPath = Path.mergePaths(workingDir, outputPath);

                if(hdfs.exists(outputPath)){
                    hdfs.delete(outputPath, true); //Delete existing Directory
                }

                hdfs.mkdirs(outputPath);     //Create new Directory

                byte[] writtenData = data.toString().getBytes();
                FSDataOutputStream outputStream = hdfs.create(outputPath);
                outputStream.write(writtenData);
                outputStream.close();

                //Read data back into new dataset
                BufferedReader reader = new BufferedReader(new InputStreamReader(hdfs.open(outputPath)));
                String line;

                while ((line = reader.readLine())!= null){
                    Tuple addedTuple = parseTuple(line, c);
                    dataSet.add(addedTuple);
                }

            }
        }
        catch(IOException e){
            System.out.println("Problem with the filesystem in the collector");
            e.printStackTrace();
        }
    }

    public void collectPlan(String plan){
        visualizer.setPlan(plan);
    }

    /**
     * Parses a line of text data from a CSV into a tuple object of the appropriate size and field types.
     * @param line The CSV line to be read
     * @return The generated tuple
     */
    public Tuple parseTuple(String line, Class... c){
        String[] tuple = line.split(",");

        if(tuple.length > c.length){
            System.out.println("Too few classes provided");
            System.out.println(Arrays.toString(tuple));
            System.out.println(Arrays.toString(c));
        }
        else if (c.length > tuple.length){
            System.out.println("Missing value in tuple/Too many classes provided");
        }

        Tuple created = null;

        switch(c.length){
            case 1 : created = new Tuple1<>(c[0]);
                break;
            case 2 : created = new Tuple2<>(c[0],c[1]);
                break;
            case 3 : created = new Tuple3<>(c[0],c[1],c[2]);
                break;
            case 4 : created = new Tuple4<>(c[0],c[1],c[2],c[3]);
                break;
            case 5 : created = new Tuple5<>(c[0],c[1],c[2],c[3],c[4]);
                break;
            case 6 : created = new Tuple6<>(c[0],c[1],c[2],c[3],c[4],c[5]);
                break;
            case 7 : created = new Tuple7<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6]);
                break;
            case 8 : created = new Tuple8<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7]);
                break;
            case 9 : created = new Tuple9<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8]);
                break;
            case 10 : created = new Tuple10<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9]);
                break;
            case 11 : created = new Tuple11<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10]);
                break;
            case 12 : created = new Tuple12<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11]);
                break;
            case 13 : created = new Tuple13<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12]);
                break;
            case 14 : created = new Tuple14<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13]);
                break;
            case 15 : created = new Tuple15<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14]);
                break;
            case 16 : created = new Tuple16<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15]);
                break;
            case 17 : created = new Tuple17<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16]);
                break;
            case 18 : created = new Tuple18<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17]);
                break;
            case 19 : created = new Tuple19<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18]);
                break;
            case 20 : created = new Tuple20<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19]);
                break;
            case 21 : created = new Tuple21<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19],c[20]);
                break;
            case 22 : created = new Tuple22<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19],c[20],c[21]);
                break;
            case 23 : created = new Tuple23<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19],c[20],c[21],c[22]);
                break;
            case 24 : created = new Tuple24<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19],c[20],c[21],c[22],c[23]);
                break;
            case 25 : created = new Tuple25<>(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9],c[10],c[11],c[12],c[13],c[14],c[15],c[16],c[17],c[18],c[19],c[20],c[21],c[22],c[23],c[24]);
                break;
            default : System.out.println("Too many classes provided");
        }

        for(int i = 0; i < tuple.length; i++){
            created.setField(convertType(tuple[i],c[i]), i);
        }

        return created;
    }

    /**
     * Takes a given string and class object, and converts the string to an object of the provided class
     * @param s The String to be converted
     * @param c The desired output class type
     * @return AN object of type 'c' based on the input string
     */
    public <T>T convertType(String s, Class<T> c){

        if(c.equals(Integer.class)){
            return (T) Integer.valueOf(s);
        }
        else if(c.equals(Double.class)){
            return (T) Double.valueOf(s);
        }
        else if(c.equals(Float.class)){
            return (T) Float.valueOf(s);
        }
        else if(c.equals(Character.class)){
            return (T) Character.valueOf(s.charAt(0));
        }
        else { //String
            return (T) s;
        }
    }

    /**
     * Parses a line of text data from a CSV into a tuple object of the appropriate size and field types,
     * assuming these features are unknown. Assumes tuple fields are of basic type.
     * @param line The CSV line to be read
     * @return The generated tuple
     */
    public Tuple parseUnknownTuple(String line){
        String[] tuple = line.split(",");
        List values = new ArrayList<>();
        Pattern queryLangPattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        Tuple created = null;


        for(int i = 0; i < tuple.length; i++) {
            if (StringUtils.isAlpha(tuple[i])) { //Field is non-numeric
                matcher = queryLangPattern.matcher(tuple[i]);
                if (matcher.matches()) { //Boolean (probably)
                    values.add(Boolean.parseBoolean(tuple[i]));
                } else { //String
                    values.add(tuple[i]);
                }
            } else try { //Field is numeric
                values.add(Integer.parseInt(tuple[i]));
            } catch (NumberFormatException e) {
                try {
                    values.add(Long.parseLong(tuple[i]));
                } catch (NumberFormatException e1) {
                    try {
                        values.add(Float.parseFloat(tuple[i]));
                    } catch (NumberFormatException e2) {
                        try {
                            values.add(Double.parseDouble(tuple[i]));
                        } catch (NumberFormatException e3) {
                            e3.printStackTrace();
                            System.out.println("Parsed field non-numeric");
                        }
                    }
                }

            }
        }

        switch(tuple.length){
            case 1 : created = new Tuple1<>(values.get(0));
                break;
            case 2 : created = new Tuple2<>(values.get(0), values.get(1));
                break;
            case 3 : created = new Tuple3<>(values.get(0), values.get(1), values.get(2));
                break;
            case 4 : created = new Tuple4<>(values.get(0), values.get(1), values.get(2), values.get(3));
                break;
            case 5 : created = new Tuple5<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
                break;
            case 6 : created = new Tuple6<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5));
                break;
            case 7 : created = new Tuple7<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6));
                break;
            case 8 : created = new Tuple8<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7));
                break;
            case 9 : created = new Tuple9<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8));
                break;
            case 10 : created = new Tuple10<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9));
                break;
            case 11 : created = new Tuple11<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10));
                break;
            case 12 : created = new Tuple12<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11));
                break;
            case 13 : created = new Tuple13<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12));
                break;
            case 14 : created = new Tuple14<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13));
                break;
            case 15 : created = new Tuple15<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14));
                break;
            case 16 : created = new Tuple16<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15));
                break;
            case 17 : created = new Tuple17<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16));
                break;
            case 18 : created = new Tuple18<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17));
                break;
            case 19 : created = new Tuple19<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18));
                break;
            case 20 : created = new Tuple20<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19));
                break;
            case 21 : created = new Tuple21<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19), values.get(20));
                break;
            case 22 : created = new Tuple22<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19), values.get(20), values.get(21));
                break;
            case 23 : created = new Tuple23<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19), values.get(20), values.get(21), values.get(22));
                break;
            case 24 : created = new Tuple24<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19), values.get(20), values.get(21), values.get(22), values.get(23));
                break;
            case 25 : created = new Tuple25<>(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9), values.get(10), values.get(11), values.get(12), values.get(13), values.get(14), values.get(15), values.get(16), values.get(17), values.get(18), values.get(19), values.get(20), values.get(21), values.get(22), values.get(23), values.get(24));
                break;
            default : System.out.println("Parsed tuple was read as being too long");
        }

        return created;
    }
}