package puzzle;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Puzzle {
    public void killAll() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        List<String> kill = Arrays.asList("Kill");

        es.submit(() -> Files.write(Paths.get("file.txt"), kill));               // 1
       // es.submit(() -> { Files.write(Paths.get("file.txt"), kill); });          // 2

    }
}
