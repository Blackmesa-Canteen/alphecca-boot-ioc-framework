package io.swen90007sm2.alpheccaboot.common.util;

import java.util.*;

/**
 * @author https://blog.csdn.net/weixin_43145361/article/details/116562757
 * @description util to parse argument
 * @create 2022-08-17 11:49
 */
public class ArgumentsParser {

    public static void main(String args[]) {
        // for test
        args = "-encoding utf8 -t -print 5 -d ./bin -cp /Users/hao/servlet-api.jar ./src/*.java".split(" ");

        ArgumentsParser ap = new ArgumentsParser(args);
        System.out.println("---------- Basic Test ---------");
        String name = ap.getString("encoding");
        int prints = Integer.parseInt(ap.getString("print"));
        for (String path : ap.getStrings("paths"))
            System.out.println(path);

        String out_path = ap.hasKey("out") ? ap.getString("out") : "c:\\output";
        System.out.println("Output Path: " + out_path);

        System.out.println("---------- Get Argument Strings ---------");
        for (String name1 : ap.getKeys())
            System.out.println(name1 + ": " + ap.getStrings(name1).length);

        System.out.println("---------- Key Existance Check ---------");
        System.out.println("hasKey(\"encoding\"): " + ap.hasKey("test"));
        System.out.println("hasKey(\"name\"): " + ap.hasKey("name"));
        System.out.println("hasKey(\"cp\"): " + ap.hasKey("path"));
        System.out.println("hasKey(\"d\"): " + ap.hasKey("paths"));

        System.out.println("---------- use arguments ---------");
        for (int i = 0; i < prints; i++) {
            System.out.print(name + ", ");
        }
    }

    private final HashMap<String, List<String>> map = new HashMap<>();

    public ArgumentsParser(String[] arguments) {
        List<String> args = Arrays.asList(arguments);
        for (String arg : args) {
            // case 1: non-hyphen started arguments.
            if (!arg.startsWith("-"))
                continue;

            // case 2: tags with 0 argument values.
            String key = arg.substring(1);
            int arg_index = args.indexOf(arg);
            if (arg_index == args.size() - 1 || args.get(arg_index + 1).startsWith("-")) {
                map.put(key, new ArrayList<String>());
                continue;
            }

            // case 3: key with 1 or more argument values
            List<String> argstrs = new ArrayList<>();
            int i = 1;
            while (arg_index + i != args.size() && !args.get(arg_index + i).startsWith("-")) {
                argstrs.add(args.get(arg_index + i));
                i++;
            }
            map.put(arg.replace("-", ""), argstrs);
        }
    }

    public boolean hasKey(String keyName) {
        if (map.containsKey(keyName))
            return true;
        return false;
    }

    // Return argument names
    public Set<String> getKeys() {
        return map.keySet();
    }

    public String getString(String keyName) {
        return getString(keyName, null);
    }

    public String getString(String keyName, String defaultValue) {
        return this.hasKey(keyName) ? map.get(keyName).get(0) : defaultValue;
    }

    public String[] getStrings(String argumentName) {
        if (map.containsKey(argumentName))
            return map.get(argumentName).toArray(new String[0]);
        else
            return new String[0];
    }

    public int getInt(String keyName, int defaultValue) {
        int retValue = 0;
        try {
            if (hasKey(keyName))
                retValue = Integer.parseInt(map.get(keyName).get(0));
        } catch (Exception ex) {
            retValue = defaultValue;
        }
        return retValue;
    }
}