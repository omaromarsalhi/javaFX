package pidev.javafx.tools;

public interface MyListener<T> {
    default void onClickListener(T arg){}
     default void exit(){}
}
