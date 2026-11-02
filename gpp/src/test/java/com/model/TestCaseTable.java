package com.model;

final class TestCaseTable {
    private static final String TEST_HEADER = "Test";
    private static final String REASONING_HEADER = "Reasoning";

    private TestCaseTable() {
    }

    static void print(String[][] rows) {
        int testWidth = TEST_HEADER.length();
        int reasoningWidth = REASONING_HEADER.length();

        for (String[] row : rows) {
            testWidth = Math.max(testWidth, row[0].length());
            reasoningWidth = Math.max(reasoningWidth, row[1].length());
        }

        printBorder(testWidth, reasoningWidth);
        System.out.printf("| %-" + testWidth + "s | %-" + reasoningWidth + "s |%n",
                TEST_HEADER, REASONING_HEADER);
        printBorder(testWidth, reasoningWidth);
        for (String[] row : rows) {
            System.out.printf("| %-" + testWidth + "s | %-" + reasoningWidth + "s |%n",
                    row[0], row[1]);
        }
        printBorder(testWidth, reasoningWidth);
    }

    private static void printBorder(int testWidth, int reasoningWidth) {
        System.out.print("+");
        printDashes(testWidth + 2);
        System.out.print("+");
        printDashes(reasoningWidth + 2);
        System.out.println("+");
    }

    private static void printDashes(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("-");
        }
    }
}
