package ru.qq.entity;

public enum CommandsEnum {
    LOAD_CONST("LOAD_CONST"),
    READ_FROM_MEMORY("READ_FROM_MEMORY"),
    WRITE_TO_MEMORY("WRITE_TO_MEMORY"),
    POP_CNT("POP_CNT");

    private String val;

    CommandsEnum(String val) {
        this.val = val;
    }


    public static CommandsEnum fromString(String value) {
        for (CommandsEnum command : CommandsEnum.values()) {
            if (command.val.equals(value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown command: " + value);
    }
}
