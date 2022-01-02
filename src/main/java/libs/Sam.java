package libs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Sam class only focus on calling Sam.exe and generated a .wav file
 */
public class Sam {
    static private final String samAbsPath = generatePath("");


    static public String createWAV(String cmd) throws IllegalArgumentException{
        cmd = reformatCmd(cmd);

        executeSam(cmd);

        if (!cmd.contains("-wav")) return null;

        return getFilepath(cmd);
    }

    static private void executeSam(String cmd) {
        ProcessBuilder builder = new ProcessBuilder();

        builder.command("cmd.exe", "/c", cmd);
        builder.directory(new File(samAbsPath));

        try {
            Process process = builder.start();
            StreamReader streamReader = new StreamReader(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamReader);

            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (IOException | InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
    }

    /**
     * Obtain the path of Sam.exe
     * @return
     */
    static private String generatePath(String filename) {
        Path projectPath = Paths.get("");
        Path samPath = Paths.get(projectPath.toAbsolutePath().toString(), "SAM", filename);
        return samPath.toString();
    }

    static private String getFilepath(String cmd) throws IllegalArgumentException{
        Pattern pattern = Pattern.compile("(?<=-wav )(?<filename>\\w+)(?=\\.wav)");
        Matcher matcher= pattern.matcher(cmd);
        String filename;
        if (matcher.find())
            filename = matcher.group() + ".wav";
        else
            throw new IllegalArgumentException("Invalid filename or file extension");

        return generatePath(filename);
    }

    static private String reformatCmd(String cmd) {
        StringBuilder strBuilder = new StringBuilder(cmd);
        strBuilder.deleteCharAt(0);

        return strBuilder.toString();
    }

}
