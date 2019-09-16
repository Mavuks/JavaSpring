package exservlet;



public class ParserMapper {



    public String parse (String input){

        input = input.replace("}","").trim();
        input = input.replace("{","").trim();
        String[] pairs = input.split(",");
        String result = "{";

        for (int i=0;i<pairs.length;i++) {
            String[] keyAndValue = pairs[i].split(":");

            String key = new StringBuilder(keyAndValue[0]).reverse().toString();
            String value = new StringBuilder(keyAndValue[1]).reverse().toString();
            if (pairs.length - 1 != i) {
                result += "" + key + ":";
                result += "" + value + ",";

            }else {
                result += "" + key + ":";
                result += "" + value + "";
            }

        }
            result += "}";

        return result;
    }



}
