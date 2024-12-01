package ru.qq;

import ru.qq.entity.CodeContainer;
import ru.qq.entity.Command;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import static ru.qq.Assembler.assemble;
import static ru.qq.Interpreter.interpret;

public class Executor {

    public static void main(String[] args) {
        String binPath = "binary.bin";
        String yamlPath = "log.yaml";
        String txtPath = "instructions.txt";

        List<String> instructions = getInstructions(txtPath);

        if (args.length == 3) {
            instructions = getInstructions(args[0]);
            binPath = args[1];
            yamlPath = args[2];
        }


        YamlLogger.setYamlPath(yamlPath);
        YamlLogger.clearYamlFile();

        List<CodeContainer> codes = new ArrayList<>();
        assemble(instructions, codes);

        writeToBin(codes, binPath);

        System.out.println("Hex codes");
        codes.forEach(c -> System.out.println(c.hex));

        List<Command> commands = new ArrayList<>();
        interpret(codes, commands);
    }

    private static List<String> getInstructions(String txtPath) {
        List<String> instructions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                instructions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instructions;
    }

    private static void writeToBin(List<CodeContainer> codes, String binPath) {
        StringBuilder fullCode = new StringBuilder();
        codes.forEach(c -> fullCode.append(c.hex));

        try (FileOutputStream fos = new FileOutputStream(binPath)) {
            for (int i = 0; i < fullCode.length(); i += 2) {
                String byteHex = fullCode.substring(i, i + 2);
                byte byteValue = (byte) Integer.parseInt(byteHex, 16);
                fos.write(byteValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
