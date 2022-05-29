package pt.ua.deti.codespell.codespelllauncher.code.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import pt.ua.deti.codespell.codespelllauncher.code.results.status.AnalysisStatus;
import pt.ua.deti.codespell.codespelllauncher.code.results.status.ExecutionStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("CodeExecReport")
public class CodeExecReport {

    private String id;
    private AnalysisStatus analysisStatus;
    private ExecutionStatus executionStatus;
    private int score;
    private List<Step> steps;
    private List<String> tips;
    private List<String> output;
    private List<String> errors;
    private List<String> runtimeLogs;

}
