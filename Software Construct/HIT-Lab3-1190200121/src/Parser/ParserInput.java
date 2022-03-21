package Parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;

public class ParserInput {

    public List<List<String>> regexMatchAll(String regex, String text) {

        Matcher fgnb = Pattern.compile(regex).matcher(text);
        List<List<String>> ret = new ArrayList<>();
        
        while (fgnb.find()) {
            List<String> tmp = new ArrayList<>();
            for (int i = 1; i <= fgnb.groupCount(); i++) {
                tmp.add(fgnb.group(i));
            }
            ret.add(tmp);
        }
        return ret;
    }
    public void regTest(String content) {
        content = content.replace("　", " ");//chinese english half & full design
        int pre = 0;
        int stsize = -1;
        int length = content.length();
        for (int i = 0; i < length; i++) {
            if (content.charAt(i) == '{') {
                if (stsize == -1) {
                    stsize = 0;
                }
                stsize++;
            }
            if (content.charAt(i) == '}') {
                stsize--;
            }
            if (stsize == 0) {
                String substr = content.substring(pre, i + 1).strip().replace("\n", "");
                if (substr.substring(0, 8).equals("Employee")) {
                    System.out.println("-----------------------------------------");
                    System.out.println("Employee");
                    System.out.println(substr);
                    substr = substr.substring(9, substr.length() - 1).strip();
                    System.out.println(regexMatchAll("(\\S[\\S].*?)\\{(.*?),(.*?)\\}", substr));
                }
                if (substr.substring(0, 6).equals("Period")) {
                    System.out.println("-----------------------------------------");
                    System.out.println("Period");
                    System.out.println(substr);
                    substr = substr.substring(7, substr.length() - 1).strip();
                    System.out.println(regexMatchAll("(\\d{4}-\\d{2}-\\d{2})", substr));
                }
                if (substr.substring(0, 6).equals("Roster")) {
                    System.out.println("-----------------------------------------");
                    System.out.println("Roster");
                    System.out.println(substr);
                    substr = substr.substring(7, substr.length() - 1).strip();
                    System.out.println(regexMatchAll("(\\S[\\S]*?)\\{({0,1}\\d{4}-\\d{2}-\\d{2})(?:,({0,1}\\d{4}-\\d{2}-\\d{2}))+\\}", substr));
                }
                pre = i + 1;
                stsize = -1;
            }
        }
    }
    public void solve(int i) {
        try {
            File file = new File("./txt/parser/" + "test" + String.valueOf(i) + ".txt");
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while (true) {
                String tmp = br.readLine();
                if (tmp != null) {
                    str += tmp;
                } else {
                    break;
                }
            }
            regTest(str);
        } catch (IOException ignored) {
        }
    }
    public static void main(String[] args) {
        ParserInput m = new ParserInput();
        for (int i = 1; i <= 8; i++) {// scan test 1-8
            m.solve(i);
        }
    }
}

