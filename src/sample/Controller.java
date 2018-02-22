package sample;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {

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
        runMainTask(Integer.parseInt(numberTextField.getText()));
    }

    @FXML
    private void runMainTask(int N) throws IOException {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            numbers.add(i + 1);
        }

        // output file creation
        FileWriter fileWriter = new FileWriter("results.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Starting time measurement and permutations generation
        long startTime = System.nanoTime();
        generatePermutations(numbers, 0, printWriter);
        BigDecimal estimatedTime = BigDecimal.valueOf((System.nanoTime() - startTime) / 1000000000.0);
        printWriter.close();
        
        elapsedTimeLabel.setText(String.valueOf(estimatedTime) + " seconds");
        permutationsNumber.setText(factorial(BigInteger.valueOf(Integer.parseInt(numberTextField.getText()))).toString());
    }

    @FXML
    private void generatePermutations(List<Integer> numbers, int index, PrintWriter printWriter) {

        StringBuilder sb = new StringBuilder();
        if(index == numbers.size()){
            for (int i = 0; i < numbers.size(); i++){
                sb.append(numbers.get(i) + " ");
            }
            final String output = sb.toString();
            printWriter.println(output);
            mainTextArea.appendText(output + "\n");
            System.out.println(output);
            return;
        }

        for(int i = index; i < numbers.size(); i++){
            Collections.swap(numbers, i, index);
            generatePermutations(numbers, index+1, printWriter);
            Collections.swap(numbers, i, index);
        }
    }

    private BigInteger factorial(BigInteger number) {
        BigInteger result = BigInteger.valueOf(1);

        for (long factor = 2; factor <= number.longValue(); factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }

        return result;
    }
}
