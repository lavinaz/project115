import java.io.*;
import java.util.*;


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

    public static void loadData()// dosya okuma func
        {         for (int m = 0; m < 12; m++)// her bir ay için
        {String fileName = "Data_Files/"+months[m]+".txt";// konuma bakıyor (ad değiştirilebilir)
            try {
                Scanner sc = new Scanner(new File(fileName));// BufferedReader yerine Scanner

                while (sc.hasNextLine())// her bir line için
                {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");// dosyalardaki elemanlar virgülle ayrıldığı için virgül
                    if (parts.length!=3) continue;// hata arıyor
                    int day;
                    try {
                        day = Integer.parseInt(parts[0].trim());// day'i ayırıyor
                    } catch (Exception e)// hata
                        {  continue;
                    }
                    String commodity = parts[1].trim();// commodity'i ayırıyor
                    int profitValue;
                    try {
                        profitValue = Integer.parseInt(parts[2].trim());// value'yu ayırıyor
                    } catch (Exception e)// hata
                        { continue;
                    }
                    int cIndex = getCommodityIndex(commodity);// commodity'yi int'e çeviriyor
                    if (cIndex == -1) continue;// hata
                    if (day < 1 || day > 28) continue;// hata
                    profit[m][day - 1][cIndex] = profitValue;// Bir ayın, gününün, malın profit value'sunu eşitliyor
                }
                sc.close();
            } catch (Exception e)  // dosya yoksa geç
                {
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
        if (day < 1 || day > 28){
            return -99999;} //hata
            if (month < 0 || month > 11){
                return -99999; }//hata
        int sum=0;
        for(int c=0;c<5;c++){
            sum+=profit[month][day-1][c];}
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        int cIndex = getCommodityIndex(commodity);
        if (cIndex == -1) return -99999;

        if (fromDay <1||toDay>28||fromDay>toDay){
            return -99999;
    }
        int sum = 0;
        for (int m = 0; m < 12; m++){
            for (int d = fromDay - 1; d <= toDay - 1; d++){
                sum += profit[m][d][cIndex];}
}
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month > 11) {
            return -1;}
        int bestDay = 1;
        int bestProfit = 0;

        for (int c = 0; c < 5; c++) {
            bestProfit += profit[month][0][c];
        }

        for (int d = 1; d < 28; d++) {
            int sum = 0;
            for (int c = 0; c < 5; c++) {
                sum += profit[month][d][c];
            }
            if (sum > bestProfit) {
                bestProfit = sum;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }

    public static String bestMonthForCommodity(String commodity) {
        int cIndex = getCommodityIndex(commodity);
        if (cIndex == -1){
         return "INVALID_COMMODITY";} //commodity hatasi -1 döndürdüğü için
        int bestMonth = 1;
        int bestProfit = 0;
        for (int d = 0; d < 28; d++) {
            bestProfit += profit[0][d][cIndex];
        }

        for (int m = 1; m < 12; m++) {
            int summ = 0;
            for (int d = 0; d < 28; d++) {
                summ += profit[m][d][cIndex];
            }
            if (summ > bestProfit) {
                bestProfit =summ;
                bestMonth= m; //array returnledigi icin 0 dan basliyo
            }
        }
        return months[bestMonth]; //return month olmama sebebi stringle calismasi
    }

    public static int consecutiveLossDays(String commodity) {
        int cIndex = getCommodityIndex(commodity);
        if (cIndex ==-1){
            return -1;}
        int current =0;
        int best =0;
        for (int m =0; m <12; m++)
            for (int d =0; d <28; d++) {
                if (profit[m][d][cIndex] <0) {
                    current++;
                    if (current > best) best = current;
                }
                else {
                    current =0;
                }
            }
        return best;
    }

    public static int daysAboveThreshold(String commodity, int threshold){
        int cIndex =getCommodityIndex(commodity);
        if (cIndex ==-1){
            return -1;}
        int count =0;
        for (int m =0; m <12;m++)
            for (int d = 0;d <28;d++){
                if (profit[m][d][cIndex] > threshold){
                    count++;}
            }
        return count;
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