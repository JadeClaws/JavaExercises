package cwiczenia;


import java.util.Date;


class InvalidSimpleDateException extends Exception {
    InvalidSimpleDateException(String message) {
        super(message);
    }
}


public class SimpleDate implements Comparable<SimpleDate> {
    public static final int CURRENT_YEAR = Integer.parseInt( new Date().toString().split(" ")[5] );
    private int day;
    private int month;
    private int year;
    
    
    SimpleDate(int day, int month, int year) {
            
        try {
            if(checkParameters(day, month, year)) {
                this.day = day;
                this.month = month;
                this.year = year;
            }
        } catch(InvalidSimpleDateException e) {
            e.printStackTrace(System.out);
        }      
    } //SimpleDate()
    
    
    //checks if given date parameters(day, month, year) are valid
    private boolean checkParameters(int day, int month, int year) throws InvalidSimpleDateException {
        if(day <= 0 || month <= 0 || year <= 0) { throw new InvalidSimpleDateException("Dzien, miesiac i rok musza byc wartosciami dodatnimi"); }
        if(year > CURRENT_YEAR + 100) { throw new InvalidSimpleDateException("Podany rok przekracza dopuszczalny zakres (1 - " + (CURRENT_YEAR + 100) + ")"); }
        if(month > 12) { throw new InvalidSimpleDateException("Podany miesiac przekracza dopuszczalny zakres (1 - 12)"); }
        
        switch(month) {
            //months with 31 days
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(day > 31) throw new InvalidSimpleDateException("Podany dzien jest zbyt duzy dla tego miesiaca (maks. 31)");
                break;
            //months with 30 days
            case 4:
            case 6:
            case 9:
            case 11:
                if(day > 30) throw new InvalidSimpleDateException("Podany dzien jest zbyt duzy dla tego miesiaca (maks. 30)");
                break;
            //February - 28/29 days
            case 2:
                if(year % 4 == 0 && day > 29) throw new InvalidSimpleDateException("Podany dzien jest zbyt duzy dla tego miesiaca (maks. 29)");
                else if(year % 4 != 0 && day > 28) throw new InvalidSimpleDateException("Podany dzien jest zbyt duzy dla tego miesiaca (maks. 28)");
                break;
        } //switch
        return true;
    }
    
    
    public void set(int day, int month, int year) {
        try {
            checkParameters(day, month, year);
            this.day = day;
            this.month = month;
            this.year = year;
        } catch(InvalidSimpleDateException e) {
            e.printStackTrace(System.out);
        } 
    }
    
    
    //Ensures that given date ranges dont collide with each other. Returns false if they collide
    public static boolean validateDateRange(SimpleDate dateFrom, SimpleDate dateTo, SimpleDate dateRangeFrom, SimpleDate dateRangeTo) {       
        if(!(
            dateFrom.compareTo(dateRangeFrom) < 0 && dateTo.compareTo(dateRangeFrom) < 0
            ||
            dateFrom.compareTo(dateRangeTo) > 0 && dateTo.compareTo(dateRangeTo) > 0
          )) return false;
        else return true;
    }
    
    
    @Override public int compareTo(SimpleDate date) {
        if(this.year > date.year) return 1;
        else if(this.year < date.year) return -1;
        else if(this.year == date.year) {
            if(this.month > date.month) return 1;
            else if(this.month < date.month) return -1;
            else if(this.month == date.month) {
                if(this.day > date.day) return 1;
                else if(this.day < date.day) return -1;
                else return 0;
            }
        }
        
        return 0;
    }
    
     
    @Override public String toString() { return day + "/" + month + "/" + year; }
    
    
    public static void main() {
        //testing
        SimpleDate date = new SimpleDate(31, 2, 2018);
        
        //testing ranges
        SimpleDate dateFrom = new SimpleDate(14, 11, 2018);
        SimpleDate dateTo = new SimpleDate(24, 11, 2018);
        SimpleDate dateRangeFrom = new SimpleDate(10, 11, 2018);
        SimpleDate dateRangeTo = new SimpleDate(20, 11, 2018);
        
        System.out.println("SimpleDate.validateDateRange(): " + SimpleDate.validateDateRange(dateFrom, dateTo, dateRangeFrom, dateRangeTo) );
    }
};
