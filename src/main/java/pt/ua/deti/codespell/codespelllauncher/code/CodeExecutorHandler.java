package pt.ua.deti.codespell.codespelllauncher.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import pt.ua.deti.codespell.codespelllauncher.analyser.CodeAnalysisResult;
import pt.ua.deti.codespell.codespelllauncher.model.CodeExecutionInstance;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Log4j2
public class CodeExecutorHandler {

    private final CodeExecutionInstance codeExecutionInstance;

    public CodeExecutorHandler(CodeExecutionInstance codeExecutionInstance) {
        this.codeExecutionInstance = codeExecutionInstance;
    }

    public static boolean initialCheck() {
        File centralDirectory = new File(FileUtils.getUserDirectoryPath() + File.separator + "Code_Spell" + File.separator + "Launcher" + File.separator + "CentralRepository");
        return centralDirectory.isDirectory() && centralDirectory.exists();
    }

    public boolean initDirectory() {

        File newDirectory = new File(FileUtils.getUserDirectoryPath() + File.separator + "Code_Spell" + File.separator + "Launcher" + File.separator + "Temp" + File.separator + codeExecutionInstance.getCodeUniqueId());

        boolean directoryCreated = newDirectory.mkdirs();

        if (!directoryCreated) return false;

        try {
            FileUtils.copyDirectory(getCentralRepository(), newDirectory);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean injectCode(int chapter, int level) {

        File newDirectory = new File(FileUtils.getUserDirectoryPath() + File.separator + "Code_Spell" + File.separator + "Launcher" + File.separator + "Temp" + File.separator + codeExecutionInstance.getCodeUniqueId());

        if (!newDirectory.exists()) return false;

        File mainClassFile = new File(newDirectory.getAbsolutePath() + String.format("/code-spell-code-executor/src/main/java/pt/ua/deti/codespell/chapters/chapter_%d/Level_%d.java", chapter, level));

        if (!mainClassFile.exists() || !mainClassFile.isFile()) return false;

        Path path = mainClassFile.toPath();
        Charset charset = StandardCharsets.UTF_8;

        String content;
        try {
            content = Files.readString(path, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!content.contains("// INJECT CODE HERE")) {
            log.warn("Unable to find inject pattern in the target class.");
            return false;
        }

        content = content.replaceAll("// INJECT CODE HERE", codeExecutionInstance.getCode());

        try {
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    public CodeAnalysisResult getCodeAnalysisResult() throws IOException {
        File analysisOutputFile = getAnalysisOutputFile();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(analysisOutputFile, CodeAnalysisResult.class);
    }

    private File getCentralRepository() {
        return new File(FileUtils.getUserDirectoryPath() + File.separator + "Code_Spell" + File.separator + "Launcher" + File.separator + "CentralRepository");
    }

    private File getAnalysisOutputFile() {
        return new File(FileUtils.getUserDirectoryPath() + File.separator + "Code_Spell" + File.separator + "Launcher" + File.separator + "Output" + File.separator + codeExecutionInstance.getCodeUniqueId().toString() + File.separator + "analysis.txt");
    }

}
