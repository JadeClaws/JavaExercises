class StringDecimalsConversion {
    public static void test() {
        LinkedList <String> listDecimalsStr; // input - list of decimals in different formats
        Iterator iterator;
        String strNum;                  // decimal as String
        Double realNum;                 // decimal as Real
        Integer decimalSeparatorPos;    // position of decimal separator (from right)
        
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
       
        iterator = listDecimalsStr.iterator();
        
        while (iterator.hasNext()) {
            strNum = (String) iterator.next();
            
            System.out.format("\n \"%s\"", strNum);
            
            // step 1. delete space thousand separators
            strNum = strNum.replace(" ", ""); 
            
            // step 2. find decimal separator replace it with dot and remove all other separators
            decimalSeparatorPos = Integer.max(strNum.lastIndexOf("."), strNum.lastIndexOf(","));
            
            if (decimalSeparatorPos > 0) {
                decimalSeparatorPos = strNum.length() - decimalSeparatorPos;
                strNum = strNum.replaceAll("\\,", "");
                strNum = strNum.replaceAll("\\.", "");
                strNum = String.format( "%s.%s", 
                                        strNum.substring(0, strNum.length() - decimalSeparatorPos + 1), 
                                        strNum.substring(strNum.length() - decimalSeparatorPos + 1, strNum.length()));
            }
                       
            // step 3. convert to real
            realNum = Double.parseDouble(strNum);
            
            System.out.format(" -> %f", realNum);
        }
    }
}
