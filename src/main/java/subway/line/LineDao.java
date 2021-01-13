package subway.line;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LineDao {
    private Long seq = 0L;
    private List<Line> lines = new ArrayList<>();

    public Line save(Line line) {
        if (isExist(line.getName())) {
            throw new IllegalStateException("이미 등록된 지하철 노선 입니다.");
        }

        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private boolean isExist(String name) {
        return lines.stream()
                .anyMatch(line -> line.getName().equals(name));
    }

    public List<Line> findAll() {
        return lines;
    }

    public void deleteById(Long id) {
        lines.removeIf(it -> it.getId().equals(id));
    }

    private Line createNewObject(Line line) {
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }
}
