package pidev.javafx.tools.marketPlace;

public interface MyListener<T> {
    default void onClickListener(T arg){}
     default void exit(){}
}
