package ru.qq.entity;

public class Command {
    public CommandsEnum command;
    public Integer value;

    public Command(Integer value, CommandsEnum command) {
        this.value = value;
        this.command = command;
    }

    @Override
    public String toString() {
        return command + " " + value;
    }
}
