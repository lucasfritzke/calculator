public class RomanNumeralsConverter {

    public RomanNumeralsConverter() {

    }

    private static final String[] UNIT = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    private static final String[] TEN = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] HUNDRED = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] THOUSAND = new String[]{"", "M", "MM", "MMM"};


    public String convert(int number) {
        if (number < 0 || number > 3999) {
            throw new IllegalArgumentException("The number must be between 1 and 3999");
        }
        StringBuilder result = new StringBuilder();
        String numberString = Integer.toString(number);
        char[] numberSplit = numberString.toCharArray();
        int length = numberSplit.length;
        int position = 0;
        length--;

        if (length >= 0) {
            position = Character.getNumericValue(numberSplit[length]);
            result.insert(0, UNIT[position]);
            length--;
        }

        if (length >= 0) {
            position = Character.getNumericValue(numberSplit[length]);
            result.insert(0, TEN[position]);
            length--;
        }

        if (length >= 0) {
            position = Character.getNumericValue(numberSplit[length]);
            result.insert(0, HUNDRED[position]);
            length--;
        }

        if (length >= 0) {
            position = Character.getNumericValue(numberSplit[length]);
            result.insert(0, THOUSAND[position]);
            length--;
        }

        return result.toString();

    }


}
