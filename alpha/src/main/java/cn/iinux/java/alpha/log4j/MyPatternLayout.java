package cn.iinux.java.alpha.log4j;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;

@Plugin(name = "MyPatternLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class MyPatternLayout extends AbstractStringLayout {
    public String getHi() {
        return hi;
    }

    public void setHi(String hi) {
        this.hi = hi;
    }

    String hi;

    protected MyPatternLayout(Charset charset) {
        super(charset);
    }

    @PluginFactory
    public static MyPatternLayout create(
            @PluginAttribute("hi") final String hi
    ) {
        MyPatternLayout p = new MyPatternLayout(Charset.defaultCharset());
        p.setHi(hi);
        return p;
    }

    @Override
    public String toSerializable(LogEvent event) {
        return hi + "null\n";
    }
}
