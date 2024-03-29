package pidev.javafx.tools.marketPlace;

public class ResultHolder {
        private String result;

        public synchronized void setResult(String result) {
            this.result = result;
            notify(); // Notify waiting threads
        }

        public synchronized String getResult() throws InterruptedException {
            while (result == null) {
                System.out.println("waiting");
                wait(); // Wait until result is set
            }
            return result;
        }
}
