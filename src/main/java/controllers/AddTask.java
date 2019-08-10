package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Task;
import model.TaskRequest;

import java.io.File;
import java.io.IOException;


public class AddTask {
    private Task task;

    @FXML private TextField taskTitle; // название
    @FXML private TextArea taskDescription; // описание
    @FXML private DatePicker taskDate; // дата
    @FXML private Label imagePath;

    // позволяет открыть окошко для выбора файлов
    FileChooser fileChooser = new FileChooser();

    private TaskRequest taskRequest = new TaskRequest();

    @FXML
    public void addTask() throws IOException {
        String title = taskTitle.getText();
        String description = taskDescription.getText();
        String date = taskDate.getValue().toString();
        Task task = new Task(title, description, date);
        task.setImagePath(imagePath.getText());

        // отправка на сервер для сохранения в бд
        taskRequest.postRequest(task);


        setTask(task);
        // у любого поля берем
        taskDate.getScene().getWindow().hide();
    }

    @FXML
    public void addImage () {
        fileChooser.setTitle("add image");
        // ограничение на файлы
        // будут отображаться только эти
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image", "*.png", "*.jpg"));

        // открываем относительно текущего окна
        File file = fileChooser.showOpenDialog(taskTitle.getScene().getWindow());
        // получаем путь до файла
        imagePath.setText(file.getAbsolutePath());
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
