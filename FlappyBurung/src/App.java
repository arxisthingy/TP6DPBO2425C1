import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Logic logic = new Logic(); // instantiate Logic
        // instantiate View
        View view = new View(logic);
        logic.setView(view); // set View to Logic

        // make sure View has focus to receive key events

        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        view.requestFocus();
    }
}
