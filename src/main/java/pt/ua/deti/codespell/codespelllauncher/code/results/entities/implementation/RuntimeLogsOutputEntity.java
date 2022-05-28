package pt.ua.deti.codespell.codespelllauncher.code.results.entities.implementation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.ua.deti.codespell.codespelllauncher.code.results.entities.CodeResultEntity;
import pt.ua.deti.codespell.codespelllauncher.response.CodeLauncherResponse;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RuntimeLogsOutputEntity extends CodeResultEntity {

    private List<String> runtimeLogs;

    @Override
    public CodeLauncherResponse toCodeLauncherResponse() {
        return null;
    }

}