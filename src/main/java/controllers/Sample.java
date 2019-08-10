package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Task;
import model.TaskRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Sample implements Initializable {
    @FXML private ListView<VBox> allTasks;
    private MultipleSelectionModel<VBox> selectionModel;


    @FXML
    public void openAddTaskWindow() throws IOException {
        System.out.println("Add Task");
        FXMLLoader loader = new FXMLLoader(AddTask.class.getResource("/views/addTask.fxml"));
        Parent root = loader.load();
        AddTask addTaskController = loader.getController();

        Stage addTaskWindow = new Stage();
        addTaskWindow.initModality(Modality.APPLICATION_MODAL);
        addTaskWindow.setTitle("Добавление задачи");
        addTaskWindow.setScene(new Scene(root));
        addTaskWindow.showAndWait();

        updateScene(addTaskController.getTask());
    }
    private void updateScene(Task task) throws FileNotFoundException {
        if (task == null){
            System.out.println("Задача не передана");
            return;
        }

        Label title = new Label(task.getTitle());
        Label description = new Label(task.getDescription());
        Label date = new Label(task.getDate());

        // отображаем картинку
        FileInputStream inputStream = new FileInputStream(task.getImagePath());
        // получили картинку
        Image image = new Image(inputStream);
        // создаем поле для изображения
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);


        VBox vBox = new VBox(title, description, date, imageView);
        vBox.setAlignment(Pos.TOP_CENTER);

        allTasks.getItems().add(vBox);


    }

    @FXML private void closeTasks(){
        ObservableList<VBox> selectedTasks = selectionModel.getSelectedItems();
        if (selectedTasks.size() !=0){
            for (VBox vBox: selectedTasks){
                allTasks.getItems().remove(vBox);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectionModel = allTasks.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        // чтобы были видны все задачи
        // получаем все задачи
        TaskRequest taskRequest = new TaskRequest();
        try {
            List<Task> tasks = taskRequest.getRequest(); // тут будут все задачи
            // добавляем все задачи на экран
            for (Task task : tasks) {
                updateScene(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML public void showChart() throws IOException {
        FXMLLoader loader = new FXMLLoader(Chart.class.getResource("/views/chart.fxml"));
        Parent root = loader.load();

        Stage chartWindow = new Stage();
        chartWindow.initModality(Modality.APPLICATION_MODAL);
        chartWindow.setTitle("Chart");
        chartWindow.setScene(new Scene(root));
        chartWindow.show();
    }
}
