package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    public static final String FILE_NAME = "tasks.csv";

    public static String[][] tasks;

    public static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        readFile(FILE_NAME);
        printOptions(OPTIONS);
        readOption();


    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] readFile(String fileName) {
        tasks = new String[0][];

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = line.split(";");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
        return tasks;
    }

    public static void readOption() {

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks,getTheNumber());
                    break;
                case "list":
                    listTasks(tasks);
                    break;
                case "exit":
                    saveToFile(FILE_NAME,tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select correct option.");
            }
            printOptions(OPTIONS);
        }

    }

    private static void saveToFile(String fileName, String[][] tab) {
        try(PrintWriter printWriter = new PrintWriter(fileName)){
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[i].length; j++) {
                    printWriter.write(tab[i][j] + ";");
                }
                printWriter.write("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }

    }


    private static void removeTask(String[][] tab, int index){
        try {
            tasks = ArrayUtils.remove(tab, index);
            System.out.println("Value was successfully deleted");
        } catch (IndexOutOfBoundsException e){
            System.out.println("Element does not exist in tab");
        }
    }

    private static int getTheNumber() {
        System.out.println("Please enter task number to remove");
        String userNumber = scanner.nextLine();
        while (!isNumberCorrect(userNumber)){
            System.out.println("Incorrect argument. Please enter correct number");
            userNumber = scanner.nextLine();
        }
        return Integer.parseInt(userNumber);
    }

    public static boolean isNumberCorrect(String input)  {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static void listTasks(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDescription = scanner.nextLine();
        System.out.println("Please add task due date");
        String taskDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isTaskImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[]{taskDescription,taskDate,isTaskImportant};
    }

}
