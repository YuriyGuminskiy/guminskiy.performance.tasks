import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logging {
    public static String logLine = "";
    public static String usersMessage;

    public static void main(String[] args) {
        selectionTypeOfInformationViewing();
    }

    public static String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" MM.dd.yyyy: HH.mm:ss.SSS");
        return dtf.format(LocalDateTime.now());
    }

    public static void selectionTypeOfInformationViewing() {
        while (true) {
            System.out.println("Please make a choice and input in console one of Type of logs information viewing: " +
                    "CONSOLE or FILE.\nIf you want to close this program input EXIT (case is not important)");

            String choiceInformationLook = new Scanner(System.in).nextLine().trim();
            LogsViewing logsViewing;

            if (choiceInformationLook.equalsIgnoreCase("exit")) {
                System.out.println("\nProgram is closed! Bye!");
                break;
            }

            try {
                logsViewing = LogsViewing.valueOf(choiceInformationLook.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("You input incorrect command. Try again.\n");
                continue;
            }

            switch (logsViewing) {
                case CONSOLE:
                    writeLog();
                    if (!logLine.equals("")) {
                        System.out.println(logLine);
                        continue;
                    } else
                        break;
                case FILE:
                    System.out.println("Input file's name:");
                    String fileName = new Scanner(System.in).nextLine().trim();
                    Path path = Path.of("c:\\PerformanceLogs\\" + fileName + ".txt");
                    try {
                        Files.createDirectories(path.getParent());
                        if (!Files.exists(path)){
                            Files.createFile(path);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    writeLog();
                    try {
                        if (!logLine.equals("")) {
                            Files.writeString(path, logLine, StandardOpenOption.APPEND);
                            System.out.println("Log have been written to file by path: " + path + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static void writeLog(){
        while (true) {
            System.out.println("\nWhat is level of logs do you want: WARNING, ERROR or DEBUG?\n" +
                    "If you want to return to the previous menu - input BACK.");
            String choiceTypeOfLogs = new Scanner(System.in).nextLine();

            if (choiceTypeOfLogs.equalsIgnoreCase("back")) {
                logLine = "";
                System.out.println("We are returned to menu about choice of Type of logs information viewing\n");
                break;
            }

            LogsType logsType;
            try {
                logsType = LogsType.valueOf(choiceTypeOfLogs.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("You input incorrect command. Try again.\n");
                continue;
            }
            inputMessage();
            logLine = (logsType + currentTime() + usersMessage);
            break;
        }
    }

    public static void inputMessage() {
        System.out.println("Input your message about this log:");
        usersMessage = (" - " + new Scanner(System.in).nextLine().trim() + "\n");
    }
}
