import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logging {
    public static String logLine;

    public static void main(String[] args) {
        selectionTypeOfInformationViewing();
    }

    public static String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" MM.dd.yyyy: HH.mm:ss.SSS\n");
        return dtf.format(LocalDateTime.now());
    }

    public static void selectionTypeOfInformationViewing() {
        Path path = Path.of("c:\\PerformanceLogs\\log.txt");
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)){
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    System.out.println(logLine);
                    continue;
                case FILE:
                    writeLog();
                    try {
                        Files.writeString(path, logLine, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Log have been written to file\n");
            }
        }
    }

    public static void writeLog(){
        while (true) {
            System.out.println("\nWhat is level of logs do you want: WARNING, ERROR or DEBUG?\n" +
                    "If you want to return to the previous menu - input BACK.");
            String choiceTypeOfLogs = new Scanner(System.in).nextLine();

            if (choiceTypeOfLogs.equalsIgnoreCase("back")) {
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
            logLine = (logsType + currentTime());
            break;
        }
    }
}
