package DataRecording;

import Visualization.Visualizer;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.core.fs.FileSystem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jake on 3/23/2015.
 */
public class InSituCollector{

    private static Visualizer visualizer;
    String outputPath;
    TypeInformation dataFormat;

    public InSituCollector(Visualizer visualizer, TypeInformation typeInfo, Class... c ) {
        this.visualizer = visualizer;
        outputPath = "C:\\Users\\Jake\\Desktop\\TestOutput";
        dataFormat = typeInfo;


    }

    /**
     * Collects a dataset taken from an external job graph and creates a local copy. This is achieved through storing
     * the contained data and then reading it into a new dataset which has the InSituCollector's ExecutionEnvironment
     * @param data External DataSet to be collected
     */
    public void collect(DataSet data){

        dataFormat.getTypeClass();
        //Initialize local data set
        ArrayList<Tuple> dataSet = new ArrayList<Tuple>();

        //Write external data set to CSV
        data.writeAsCsv(outputPath, FileSystem.WriteMode.OVERWRITE);

        //Read data originally from external data set into internal one
        File dir = new File(outputPath);
        File[] directoryListing = dir.listFiles();
        BufferedReader reader = null;
        String line;

        for(File file: directoryListing) {
            try {
                reader = new BufferedReader(new FileReader(file.getPath()));
                while ((line = reader.readLine()) != null) {


                    Tuple addedTuple = parseUnknownTuple(line);
                    dataSet.add(addedTuple);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        visualizer.addData(dataSet);
        dir.deleteOnExit();
    }

    /**
     * Parses a line of text data from a CSV into a tuple object of the appropriate size and field types.
     * @param line The CSV line to be read
     * @return The generated tuple
     */
    public Tuple parseTuple(String line){
        String[] tuple = line.split(",");
        List values = new ArrayList<>();
        Tuple created = null;

        return created;
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