package pidev.javafx.tools.user;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

public class windowController {



        static List<Stage> windows = new ArrayList<>();

     public static void addWindow(Stage newWindow) {
            windows.add(newWindow);

        }

        public static void deleteLastWindow() {
            int lastIndex = windows.size() - 1;
            System.out.println(lastIndex);
            if (lastIndex >= 0) {
                Stage lastWindow = windows.remove(lastIndex);
                lastWindow.close();
            }
        }
    public static void delete() {

        System.out.println(windows.size());
         for(int i=0;i< windows.size();i++) {
             windows.remove(i);
             windows.get(i).close();
         }

        }


    }


