class StringDecimalsConversion {
    
    static class WrongDecimalFormatException extends Exception {
        @Override public String getMessage() { return "Given string is not a properly formatted decimal"; }
    }
    
    
    public static Double StringToDouble(String strNum) {
        Integer decimalSeparatorPos;            // position of decimal separator (from right)
        Double  doubleNum;                      // decimal as Double  
        Integer numberOfDifferentSeparators;
        
        // step 1. analyze string and find decimal separator
        numberOfDifferentSeparators = 0;
        if (strNum.contains(".")) numberOfDifferentSeparators++;
        if (strNum.contains(",")) numberOfDifferentSeparators++;
        if (strNum.contains(" ")) numberOfDifferentSeparators++;
        
        try {
            if (numberOfDifferentSeparators > 2) throw new WrongDecimalFormatException();
            else {
                decimalSeparatorPos = Integer.max(strNum.lastIndexOf("."), strNum.lastIndexOf(","));
                
                decimalSeparatorPos = (decimalSeparatorPos > 0 ? strNum.length() - decimalSeparatorPos : 0);
                
                if (decimalSeparatorPos         == 4
                &&  numberOfDifferentSeparators == 1) {
                    decimalSeparatorPos = 0;
                }
                
                // step 2. delete all separators
                strNum = strNum.replaceAll(" ", "");
                strNum = strNum.replaceAll("\\,", "");
                strNum = strNum.replaceAll("\\.", "");
                
                // step 3. insert decimal separator (if existed)
                if (decimalSeparatorPos > 0) {                                 
                    strNum = String.format( "%s.%s", 
                                            strNum.substring(0, strNum.length() - decimalSeparatorPos + 1), 
                                            strNum.substring(strNum.length() - decimalSeparatorPos + 1, strNum.length()));
                }
                
                // step 4. convert to real
                try {
                    doubleNum = Double.parseDouble(strNum);

                    return doubleNum;
                } catch (NumberFormatException e) {
                    throw new WrongDecimalFormatException();
                } 
            }
        } catch (WrongDecimalFormatException e) {
            System.out.print(e.getMessage());
        }
              
        return null;
    }
    
    
    public static void test() {
        LinkedList <String>     listDecimalsStr;        // input - list of decimals in different formats
        Iterator                iterator;
        String                  strNum;                 // decimal as String
        Double                  realNum;                // decimal as Real
    
        
        listDecimalsStr = new LinkedList<>();
        
        listDecimalsStr.add("1234,56");
        listDecimalsStr.add("1234.56");
        listDecimalsStr.add("1 234.56");
        listDecimalsStr.add("1 234,56");
        listDecimalsStr.add("1,234.56");
        listDecimalsStr.add("1.234,56");
        listDecimalsStr.add("1.234,5632");
        listDecimalsStr.add("1.234,56456");
        listDecimalsStr.add("1 234.561");
        listDecimalsStr.add("1234,5");
        listDecimalsStr.add("34.560001");
        listDecimalsStr.add("4,5601");
        listDecimalsStr.add("1 234 567 890");
        listDecimalsStr.add("1,000,000.01");
        listDecimalsStr.add("1.000.000,01");
        listDecimalsStr.add("1.000.000");
        listDecimalsStr.add("3,456,789");
        listDecimalsStr.add("2 333 444");
        listDecimalsStr.add("1 333,444.555");       // error
        listDecimalsStr.add("abcd");                // error
       
        iterator = listDecimalsStr.iterator();
        
        while (iterator.hasNext()) {
            strNum = (String) iterator.next();
            
            System.out.format("\n \"%s\" -> ", strNum);
            
            realNum = StringDecimalsConversion.StringToDouble(strNum);
            
            if (realNum != null) System.out.format("%f", realNum);         
        }
    }
}
