package ru.qq;

import ru.qq.entity.CodeContainer;
import ru.qq.entity.CommandsEnum;

import java.util.List;

public class Assembler {
    public static void assemble(List<String> instructions, List<CodeContainer> codes) {
        instructions.forEach(
                inst -> {
                    var parts = inst.split(" ");
                    if(parts.length != 2) throw new RuntimeException("Provided instructions are bad");

                    switch (CommandsEnum.fromString(parts[0])) {
                        case LOAD_CONST -> {
                            codes.add(
                                    new CodeContainer(
                                            getBinary("10000", "0000", 23, parts[1]),
                                            getHex(
                                                    getBinary("0000", "10000", 23, parts[1])
                                            )
                                    )
                            );
                        }
                        case READ_FROM_MEMORY-> {
                            codes.add(
                                    new CodeContainer(
                                            getBinary( "00111", "0000000000000000000",8, parts[1]),
                                            getHex(
                                                    getBinary("0000000000000000000", "00111", 8, parts[1])
                                            )
                                    )
                            );
                        }
                        case WRITE_TO_MEMORY -> {
                            codes.add(
                                    new CodeContainer(
                                            getBinary( "10111", "0000000000000000000", 8, parts[1]),
                                            getHex(
                                                    getBinary("0000000000000000000", "10111", 8, parts[1])
                                            )
                                    )
                            );
                        }
                        case POP_CNT -> {
                            codes.add(
                                    new CodeContainer(
                                            getBinary( "01010", "0",26, parts[1]),
                                            getHex(
                                                    getBinary("0", "01010", 26, parts[1])
                                            )
                                    )
                            );
                        }
                        default -> System.out.println(String.format("Nt found command %s", parts[0]));
                    }
                }
        );
    }

    public static String getBinary(String before, String after, int n, String input) {
        return before +
                String.format("%" + n + "s", Integer.toBinaryString(Integer.parseInt(input))).replace(' ', '0')
                + after;
    }

    public static String getHex(String binaryNumber) {

        int length = binaryNumber.length();
        int paddingLength = (4 - length % 4) % 4;
        String paddedBinary = "0".repeat(paddingLength) + binaryNumber;

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < paddedBinary.length(); i += 4) {
            String fourBits = paddedBinary.substring(i, i + 4);
            int decimalValue = Integer.parseInt(fourBits, 2);
            String hexValue = Integer.toHexString(decimalValue).toUpperCase();
            hexString.append(hexValue);
        }

        StringBuilder reversedHex = new StringBuilder();
        for (int i = hexString.length(); i > 0; i -= 2) {
            reversedHex.append(hexString, i - 2, i);
        }

        return reversedHex.toString();
    }
}
