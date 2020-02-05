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
