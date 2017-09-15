package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    public boolean startIsDateGreaterThanToday(String startDate) {
        // Create object of SimpleDateFormat class and decide the format
        DateFormat day = new SimpleDateFormat("dd");
        DateFormat month = new SimpleDateFormat("MM");
        DateFormat year = new SimpleDateFormat("YYYY");
        String startDay;
        String startMonth;
        String startYear;

        //get current date time with Date()
        Date date = new Date();

        // Now format the date
        String currentDay = day.format(date);
        if(currentDay.substring(0,1).equals("0")) currentDay=currentDay.substring(1,2);
        String currentMonth = month.format(date);
        if(currentMonth.substring(0,1).equals("0")) currentMonth=currentMonth.substring(1,2);
        String currentYear = year.format(date);

        startYear = startDate.substring(0,4);

        if(startDate.substring(5,6).equals("0")) {
            startMonth = startDate.substring(6,7);
        }
        else startMonth = startDate.substring(5,7);

        if(startDate.substring(8,9).equals("0")) {
            startDay = startDate.substring(9);
        }
        else startDay = startDate.substring(8);

        /*System.out.println(currentDay + " " + startDay);
        System.out.println(currentMonth + " " + startMonth);
        System.out.println(currentYear + " " + startYear);*/

        if(Integer.parseInt(startYear)<Integer.parseInt(currentYear)) return false;
        if(Integer.parseInt(startMonth)<Integer.parseInt(currentMonth)) return false;
        if(Integer.parseInt(startMonth)==Integer.parseInt(currentMonth) && Integer.parseInt(startDay)<=Integer.parseInt(currentDay)) return false;
        return true;
    }

    /*public void numberOfDaysBetween() {
        int days = Days.daysBetween(date1, date2).getDays();
    }*/

    public static void main(String[] args) {
        GetDate getDate = new GetDate();
        System.out.println(getDate.startIsDateGreaterThanToday("2017-09-15"));
    }
}
