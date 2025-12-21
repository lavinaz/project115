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
                    int cIndex = -1;
                    for (int i = 0; i < commodities.length; i++) { //commodityi inte ceviriyor ama yeni func kullanmadan
                        if (commodities[i].equals(commodity)) {
                            cIndex = i;
                            break;
                        }
                    }
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
        int cIndex = -1;
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex == -1) return -99999;
        if (fromDay <1||toDay>28||toDay<1||fromDay>toDay){
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
        int cIndex = -1;
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                cIndex = i;
                break;
            }
        }
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
        int cIndex = -1;
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex==-1){
            return -1;}
        int current =0;
        int best=0;
        for (int m=0; m <12; m++)
            for (int d =0;d<28;d++) {
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
        int cIndex = -1;
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex==-1){
            return -1;}
        int count=0;
        for (int m=0;m<12;m++)
            for (int d=0;d<28;d++){
                if (profit[m][d][cIndex] > threshold){
                    count++;}
            }
        return count;
    }




    public static int biggestDailySwing(int month) {
        if (month<0||month>11) {
            return -99999;
        }
        int maxSwing=0;
        for (int d=0;d<27;d++){
            int today=0;
            int tomorrow=0;
            for (int c=0;c<5;c++) {
                today+=profit[month][d][c];
                tomorrow+=profit[month][d + 1][c];
            }
            int diff=today-tomorrow;
            if (diff<0)  {
                diff=-diff;} // Math.abs yerine(func slaytından alıntı)
            if (diff>maxSwing){
                maxSwing=diff;}
        }
        return maxSwing; }





    public static String compareTwoCommodities(String c1, String c2) {
        int i1=-1;
        for (int i=0;i<commodities.length;i++) {
            if (commodities[i].equals(c1)){
                i1 = i;
                break;}
            }
        int i2=-1;
        for (int i=0;i<commodities.length;i++) {
            if (commodities[i].equals(c2)){
                i2=i;
                break;}
        }
        if (i1==-1||i2==-1) {
            return "INVALID_COMMODITY";
        }
        int sum1=0;
        int sum2=0;
        for (int m=0;m<12;m++) {
            for (int d=0;d<28;d++) {
                sum1+=profit[m][d][i1];
                sum2+=profit[m][d][i2];} }
        if (sum1==sum2){
            return "Equal";}
        if (sum1>sum2) {
            return c1+" is better by "+(sum1 - sum2); }
             return c2+" is better by "+(sum2 - sum1); }//else gerek yok cünkü returnlerse bakmiyo diğerlelrine





    public static String bestWeekOfMonth(int month) {
        if (month<0||month>11){
            return "INVALID_MONTH";  }
        int bestWeek=1;
        int bestProfit=0;
        // 1.haftanın toplamı(başlangıç için)
        for (int d=0;d<7;d++)
            for (int c=0;c<5;c++)
                bestProfit+=profit[month][d][c];
        // Diğer haftalar (2, 3, 4)
        for (int w=1;w<4;w++) {
            int sum=0;
            int start=w*7;
            for (int d=start;d<start+7;d++)
                for (int c=0;c<5;c++)
                    sum+=profit[month][d][c]; //min.value yerine
            if (sum>bestProfit) {
                bestProfit=sum;
                bestWeek=w+1; }
        }
        return "Week " +bestWeek;}




    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries 1:"+ mostProfitableCommodityInMonth(2));//test yapiyoz 1.func
        System.out.println("Data loaded. Example test 2: " + commodityProfitInRange("Gold" , 2, 6));//test yapiyoz 2.func
        System.out.println("Data loaded. Example test 3: " + totalProfitOnDay(2, 26)); //test yapiyoz func 3
        System.out.println("Data loaded. Example test 4: " + bestDayOfMonth(3));//test yapiyoz 4.func
        System.out.println("Data loaded. Example test 5: " + bestMonthForCommodity("Silver"));//test yapiyoz 5.func
        System.out.println("Data loaded. Example test 6: " + consecutiveLossDays("Copper"));//test yapiyoz 6.func
        System.out.println("Data loaded. Example test 7: " + daysAboveThreshold("Oil", 2000));//test yapiyoz 7.func
        System.out.println("Data loaded. Example test 8: " + biggestDailySwing(9));//test yapiyoz 8.func
        System.out.println("Data loaded. Example test 9:" + compareTwoCommodities("Wheat", "Gold"));//test yapiyoz 9.func
        System.out.println("Data loaded. Example test 10: " + bestWeekOfMonth(6));//test yapiyoz 10.func
        }
}