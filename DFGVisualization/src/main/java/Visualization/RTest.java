package Visualization;

import javax.script.*;

/**
 * Created by Jake on 6/30/2015.
 */
public class RTest {

    public static void main(String[] args) throws Exception {
        // create a script engine manager:
        ScriptEngineManager manager = new ScriptEngineManager();
        // create a Renjin engine:
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // check if the engine has loaded correctly:
        if(engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }

        // ... put your Java code here ...
        engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
        engine.eval("print(df)");
        engine.eval("print(lm(y ~ x, df))");
    }
}
