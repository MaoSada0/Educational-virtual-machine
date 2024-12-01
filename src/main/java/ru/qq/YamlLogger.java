package ru.qq;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class YamlLogger {

    public static void setYamlPath(String yamlPath) {
        YamlLogger.yamlPath = yamlPath;
    }

    private static String yamlPath;


    public static void logStackAndMemory(Deque<Integer> stack, int[] memory, int step) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        List<String> memoryItems = new ArrayList<>();

        for (int i = 0; i < memory.length; i++) {
            if (memory[i] != 0) {
                memoryItems.add(String.format("memory[%d]: %d", i, memory[i]));
            }
        }

        String memoryStr = "{";

        for (int i = 0; i < memoryItems.size(); i++) {
            memoryStr += memoryItems.get(i);

            if(i != memoryItems.size() - 1) memoryStr += ", ";
        }

        memoryStr += "}";

        String stackStr = "{";

        List<Integer> stackList = stack.stream().toList();

        for (int i = 0; i < stackList.size(); i++) {
            stackStr += stackList.get(i);

            if(i != stackList.size() - 1) stackStr += ", ";
        }
        stackStr += "}";

        try (FileWriter writer = new FileWriter(yamlPath, true)) {
            yaml.dump(
                    Map.of(
                            String.format("Step[%d] | Stack: ", step),
                            stackStr
                    ),
                    writer
            );
            yaml.dump(
                    Map.of(
                            String.format("Step[%d] | Memory: ", step),
                            memoryStr
                    ),
                    writer
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void clearYamlFile() {
        try (FileWriter writer = new FileWriter(yamlPath)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
