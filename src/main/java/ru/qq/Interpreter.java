package ru.qq;

import ru.qq.entity.CodeContainer;
import ru.qq.entity.Command;
import ru.qq.entity.CommandsEnum;

import java.util.*;

import static ru.qq.YamlLogger.logStackAndMemory;


public class Interpreter {

    private static final Deque<Integer> stack = new ArrayDeque<>();
    private static final int[] memory = new int[10000];

    public static void interpret(List<CodeContainer> codes, List<Command> commands) {
        List<String> input = codes.stream().map(x -> x.binary).toList();

        input.forEach(
                x -> {
                    int commandInt = Integer.parseInt(x.substring(0, 5), 2);
                    switch (commandInt){
                        case 16 -> commands.add(
                                new Command(
                                        Integer.parseInt(x.substring(5, 28), 2),
                                        CommandsEnum.LOAD_CONST
                                )
                        );
                        case 7 -> commands.add(
                                new Command(
                                        Integer.parseInt(x.substring(5, 13), 2),
                                        CommandsEnum.READ_FROM_MEMORY
                                )
                        );
                        case 23 -> commands.add(
                                new Command(
                                        Integer.parseInt(x.substring(5, 13), 2),
                                        CommandsEnum.WRITE_TO_MEMORY
                                )
                        );
                        case 10 -> commands.add(
                                new Command(
                                        Integer.parseInt(x.substring(5, 31), 2),
                                        CommandsEnum.POP_CNT
                                )
                        );
                    }
                }
        );

        next(commands);
    }

    public static void next(List<Command> commands) {

        Map<String, String> logs = new HashMap<>();

        int index = 1;

        for(var command: commands) {

            switch (command.command) {
                case LOAD_CONST -> stack.addLast(command.value);
                case READ_FROM_MEMORY -> stack.push(
                        memory[Optional.ofNullable(stack.pollLast()).orElse(0) + command.value]
                );
                case WRITE_TO_MEMORY ->
                        memory[
                                command.value + Optional.ofNullable(stack.peekLast()).orElse(0)
                                ] = Optional.ofNullable(stack.pollLast()).orElse(0);
                case POP_CNT -> memory[command.value] = Optional.ofNullable(stack.pollLast()).orElse(0);
            }

            logStackAndMemory(stack, memory, index);
            index++;
        }


        System.out.println("Memory: ");
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] != 0) {
                System.out.println(String.format("memory[%d]: [%d]", i, memory[i]));
            }
        }
        System.out.println("Stack: ");
        for (int i = 0; !stack.isEmpty(); i++) {
            System.out.println(String.format("stack[%d]: [%d]", i, stack.pollLast()));
        }
    }
}
