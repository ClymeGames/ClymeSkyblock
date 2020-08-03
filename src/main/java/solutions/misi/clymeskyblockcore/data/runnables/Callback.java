package solutions.misi.clymeskyblockcore.data.runnables;

public interface Callback<V extends Object, T extends Throwable> {
    void call(V result, T thrown);
}