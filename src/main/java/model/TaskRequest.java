package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class TaskRequest {

    private String url = "http://localhost:8080/tasks";

    // для работы с jackson библиотекой
    // формирует json
    private ObjectMapper mapper = new ObjectMapper();
    // чтобы библиотека понимала объекты какого типа формировать
    private TypeReference<Task> taskTypeReference = new TypeReference<Task>() {};
    private TypeReference<List<Task>> taskTypeList = new TypeReference<List<Task>>() {};

    // http client
    // обработчик ответов
    private ResponseHandler<String> getResponseHandler () {
        return response -> {
            int status = response.getStatusLine().getStatusCode(); // статус ответа
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity !=null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Status error " + status);
            }
        };
    }

    // запросы на гет пост

    public  Task postRequest(Task task) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){ // для соединения с сервером
            HttpPost httpPost = new HttpPost(url); // формируем объект запроса
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            // возьмем объект и сформируем джейсон строчку для передачи на сервер
            String jsonTask = mapper.writeValueAsString(task); // это строчка с запросом, пойдет на сервер

            // в запрос добавляем строчку
            httpPost.setEntity(new StringEntity(jsonTask));

            // выполняем наш запрос и передаем обработчик ответа
            String responseBody = httpClient.execute(httpPost, getResponseHandler());
            return mapper.readValue(responseBody, taskTypeReference);

        }
    }

    public Task getRequest (int id) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url + "/" + id);
            // сформируется только тогда, когда придет ответ от сервера
            // тут будет джейсон строчка
            String responseBody = httpClient.execute(httpGet, getResponseHandler());
            // делаем из строчки объект
            return mapper.readValue(responseBody, taskTypeReference);
        }
    }


    // для получения всего списка задач
    public List<Task> getRequest () throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            String responseBody = httpClient.execute(httpGet, getResponseHandler());
            // считываем лист с задачами из строчки
            return mapper.readValue(responseBody, taskTypeList);
        }
    }

}
