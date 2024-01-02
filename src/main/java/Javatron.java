import dev.webview.Webview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Javatron {
    private final Webview _webview;
    private final List<Runnable> _beforeStartCallbacks;
    private final List<Runnable> _onCloseCallbacks;
    private final List<Object> _bindObjects;

    public Javatron() {
        this(true);
    }

    public Javatron(boolean withDevTools) {
        final int DEFAULT_HEIGHT = 600;
        final int DEFAULT_WIDTH = 800;

        _webview = new Webview(withDevTools);
        _beforeStartCallbacks = new ArrayList<>();
        _onCloseCallbacks = new ArrayList<>();
        _bindObjects = new ArrayList<>();

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void setTitle(String title) {
        _webview.setTitle(title);
    }

    public void setSize(int width, int height) {
        _webview.setSize(width, height);
    }

    public void setMinSize(int minWidth, int minHeight) {
        _webview.setMinSize(minWidth, minHeight);
    }

    public void setMaxSize(int maxWidth, int maxHeight) {
        _webview.setMaxSize(maxWidth, maxHeight);
    }

    public void setFixedSize(int fixedWidth, int fixedHeight) {
        _webview.setFixedSize(fixedWidth, fixedHeight);
    }

    public void bind(Object ...objects) {
        _bindObjects.addAll(Arrays.stream(objects).toList());
    }

    public void invoke(String event) {
        _webview.eval("window["+event+"]()");
    }

    public void addBeforeStartCallback(Runnable r) {
        _beforeStartCallbacks.add(r);
    }

    public void addOnCloseCallback(Runnable r) {
        _onCloseCallbacks.add(r);
    }

    public void run() {
        TypeConverter.generateTypes(_bindObjects.toArray());
        _beforeStartCallbacks.forEach(Runnable::run);
        _webview.run();
        _onCloseCallbacks.forEach(Runnable::run);
    }
}