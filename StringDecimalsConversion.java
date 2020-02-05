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
        Map <String, Double>    mapTestStringDecimals;  // input - map of string decimals and expected results
       
        
        mapTestStringDecimals = new LinkedHashMap<>(); // to preserve input order
        
        mapTestStringDecimals.put("1234,56", 		1234.56);		
        mapTestStringDecimals.put("1234.56", 		1234.56);		
        mapTestStringDecimals.put("1 234.56", 		1234.56);		
        mapTestStringDecimals.put("1 234,56", 		1234.56);		
        mapTestStringDecimals.put("1,234.56", 		1234.56);		
        mapTestStringDecimals.put("1.234,56", 		1234.56);		
        mapTestStringDecimals.put("1.234,5632", 	1234.5632);		
        mapTestStringDecimals.put("1.234,56456", 	1234.56456);	
        mapTestStringDecimals.put("1 234.561", 		1234.561);		
        mapTestStringDecimals.put("1234,5", 		1234.5);		
        mapTestStringDecimals.put("34.560001", 		34.560001);		
        mapTestStringDecimals.put("4,5601", 		4.5601);		
        mapTestStringDecimals.put("1 234 567 890", 	1234567890.00);	
        mapTestStringDecimals.put("1,000,000.01", 	1000000.01);	
        mapTestStringDecimals.put("1.000.000,01", 	1000000.01);	
        mapTestStringDecimals.put("1.000.000", 		1000000.00);	
        mapTestStringDecimals.put("3,456,789", 		3456789.00);	
        mapTestStringDecimals.put("2 333 444", 		2333444.00);	
        mapTestStringDecimals.put("1 333,444.555", 	null);			
        mapTestStringDecimals.put("abcd",               null);	
        
        mapTestStringDecimals.entrySet().forEach(new Consumer<Map.Entry<String, Double>>() {
            Double tmp;
            
            @Override public void accept(Map.Entry<String, Double> e) {
                System.out.format("\n\"%s\" -> ", e.getKey());
                
                tmp = StringDecimalsConversion.StringToDouble(e.getKey());
                
                if (tmp != null) {
                    System.out.format("%f", tmp);
                    System.out.format(" (%s)", tmp.equals(e.getValue()) ? "OK" : "WRONG");
                }                              
            }
        });
    }
}
