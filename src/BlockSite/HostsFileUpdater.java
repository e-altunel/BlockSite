package BlockSite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostsFileUpdater {

    static final String regex = "(?:\\d{1,3}\\.){3}\\d{1,3}[ \\t]+(.*)[ \\t]+#BlockSite";
    static final String fileName = "C:\\Windows\\System32\\drivers\\etc\\hosts";

    public static void addWebsiteToHostsFile(String website) {
        addWebsiteToHostsFile(website, "127.0.0.1");
    }

    public static void addWebsiteToHostsFile(String website, String ipAddress) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            addWebsiteToHostsFile(bufferedWriter, website, ipAddress);
            bufferedWriter.close();
            System.out.println("Website added successfully to hosts file.");
        } catch (IOException e) {
            System.out.println("Error occurred while adding website to hosts file: " + e.getMessage());
        }
    }

    public static void addWebsiteToHostsFile(List<String> websites) {
        addWebsiteToHostsFile(websites, "127.0.0.1");
    }

    public static void addWebsiteToHostsFile(List<String> websites, String ipAddress) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            for (String website : websites) {
                addWebsiteToHostsFile(bufferedWriter, website, ipAddress);
            }
            bufferedWriter.close();
            System.out.println("Website added successfully to hosts file.");
        } catch (IOException e) {
            System.out.println("Error occurred while adding website to hosts file: " + e.getMessage());
        }
    }

    private static void addWebsiteToHostsFile(BufferedWriter bufferedWriter, String website, String ipAddress) {
        try {
            bufferedWriter.write(ipAddress + "\t" + website + "\t#BlockSite");
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Error occurred while adding website to hosts file: " + e.getMessage());
        }
    }

    private static Pattern patterCreate(String website) {
        return Pattern.compile("(?:\\d{1,3}\\.){3}\\d{1,3}[ \\t]+" + website + "[ \\t]+#BlockSite[\r\n]+");
    }

    public static void removeWebsiteFromHostsFile(String website) {
        try {
            // Open the input file
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the contents of the input file
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            // Replace using regex pattern
            Pattern pattern = patterCreate(website);
            Matcher matcher = pattern.matcher(content.toString());
            String modifiedContent = matcher.replaceAll("");

            // Close the input file
            bufferedReader.close();
            fileReader.close();

            // Open the output file for writing
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the modified content to the output file
            bufferedWriter.write(modifiedContent);

            // Close the output file
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Text replaced successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeWebsiteFromHostsFile() {
        try {
            // Open the input file
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the contents of the input file
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            // Replace using regex pattern
            Pattern pattern = Pattern.compile(regex + "[\r\n]+");
            Matcher matcher = pattern.matcher(content.toString());
            String modifiedContent = matcher.replaceAll("");

            // Close the input file
            bufferedReader.close();
            fileReader.close();

            // Open the output file for writing
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the modified content to the output file
            bufferedWriter.write(modifiedContent);

            // Close the output file
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Text replaced successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getWebsitesFromHostsFile() {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> list = new ArrayList<>();

            String line;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher;

            while ((line = bufferedReader.readLine()) != null) {
                matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String extractedText = matcher.group(1);
                    list.add(extractedText);
                }
            }
            bufferedReader.close();
            fileReader.close();

            return list;
        } catch (IOException e) {
            System.out.println("Error occurred while adding website to hosts file: " + e.getMessage());
            return null;
        }
    }
}
