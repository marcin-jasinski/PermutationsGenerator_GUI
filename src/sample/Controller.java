package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Controller {

    public static String output="";

    @FXML
    private TextField numberTextField;
    @FXML
    private TextArea mainTextArea;
    @FXML
    private Label permutationsNumber;
    @FXML
    private Label elapsedTimeLabel;
    @FXML
    private Button generateButton;

    @FXML
    public void initialize(){
        generateButton.setDisable(true);
    }

    @FXML
    public void handleKeyReleased(){
        String text = numberTextField.getText();
        boolean isEmpty = text.isEmpty() || text.trim().isEmpty();
        generateButton.setDisable(isEmpty);
    }

    @FXML
    public void onButtonClicked() throws IOException {

        Runnable task = () -> {
            // output file creation
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("results.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Starting time measurement and permutations generation
            long startTime = System.nanoTime();

            String string = numberTextField.getText();
            int [] factorials = new int[string.length()+1];
            factorials[0] = 1;
            for (int i = 1; i<=string.length();i++) {
                factorials[i] = factorials[i-1] * i;
            }

            for (int i = 0; i < factorials[string.length()]; i++) {
                String temp = string;
                output = "";
                int positionCode = i;
                for (int position = string.length(); position > 0 ;position--){
                    int selected = positionCode / factorials[position-1];
                    output += temp.charAt(selected);
                    positionCode = positionCode % factorials[position-1];
                    temp = temp.substring(0,selected) + temp.substring(selected+1);
                }

                System.out.println(output + " thread numer " + i);
                printWriter.println(output);
                Platform.runLater(() -> mainTextArea.appendText(output + "\n"));
            }

            printWriter.close();

            Platform.runLater(() -> {
                BigDecimal elapsedTime = BigDecimal.valueOf((System.nanoTime() - startTime) / 1000000000.0);
                elapsedTimeLabel.setText(String.valueOf(elapsedTime) + " seconds");
                permutationsNumber.setText(factorial(BigInteger.valueOf((numberTextField.getText().length()))).toString());
            });
        };

        new Thread(task).start();
    }

    private BigInteger factorial(BigInteger number) {
        BigInteger result = BigInteger.valueOf(1);

        for (long factor = 2; factor <= number.longValue(); factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }

        return result;
    }

    /*
    * This is a previously used permutations algorithm based on a recursive solution for Integer array.
    * New algorithm was designed for a String, however Integer implementation should not be a problem.
    */
    /*@FXML
    private void permute_recursive(List<Integer> numbers, int index, PrintWriter printWriter) {

        StringBuilder sb = new StringBuilder();
        if(index == numbers.size()){
            for (int i = 0; i < numbers.size(); i++){
                sb.append(numbers.get(i) + " ");
            }
            final String output = sb.toString();
            printWriter.println(output);
            mainTextArea.appendText(output + "\n");
            //System.out.println(output);
            return;
        }

        for(int i = index; i < numbers.size(); i++){
            Collections.swap(numbers, i, index);
            permute_recursive(numbers, index+1, printWriter);
            Collections.swap(numbers, i, index);
        }
    }*/
}
