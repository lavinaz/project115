import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    public static int[][][] profit=new int[12][28][5];/*static final int MONTHS = 12;
                                                        static final int DAYS = 28;
                                                        static final int COMMS = 5;
                                                        içeren array yazdik
                                                                                    */

    public static void loadData()  { //dosya okuma func
        for (int m = 0; m < 12; m++) //her bir ay icin
        {
            String fileName ="Data_Files/" + months[m] + ".txt"; //konuma bakiyo (ad değiştirilebilir)

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) //okuyo
            {
                String line;

                while ((line = br.readLine()) != null) //her bir line icin
                {
                    String[] parts = line.split(",");  //dosyalardaki elemanlar virgülle ayrıldığı için virgül
                    if (parts.length != 3) continue; //hata ariyo

                    int day;
                    try {
                        day = Integer.parseInt(parts[0].trim()); //dayi ayırıyo
                    } catch (Exception e) //hata
                    {
                        continue;
                    }

                    String commodity = parts[1].trim();  //commodityi ayırıyo
                    int profitValue;
                    try {
                        profitValue = Integer.parseInt(parts[2].trim()); //value yu ayırıyo
                    } catch (Exception e)  //hata
                     {
                        continue;
                    }

                    int cIndex = getCommodityIndex(commodity);//commodityi (normalde string) int e ceviriyo
                    if (cIndex == -1) continue; //hata
                    if (day < 1 || day > 28) continue;//hata

                    profit[m][day - 1][cIndex] = profitValue; //Bir ayın, gününün, malın, profit valuesunu eşitliyo
                }

            } catch (IOException e) {

            }
        }
    }
    private static int getCommodityIndex(String c) {
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(c)) return i;
        }
        return -1;
    }




    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11){
            return "INVALID_MONTH";//hata
        }
        int[] sums = new int[5]; //mal sayısı boyutunda arr

        for (int d=0;d<28;d++){
            for (int c=0;c<5;c++){
                sums[c] +=profit[month][d][c];//her gündeki her profiti ekliyo
            }
        }
        int mostp = 0;//maks bulma
        for (int m=1;m<5;m++) {
            if (sums[m] > sums[mostp])//maks bulma
            {
                mostp = m; }  //maks ise degistiriyo
            }
        return commodities[mostp] + " " + sums[mostp];
    }

    public static int totalProfitOnDay(int month, int day) {
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {
        return 1234;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries "+ mostProfitableCommodityInMonth(2));
    }
}