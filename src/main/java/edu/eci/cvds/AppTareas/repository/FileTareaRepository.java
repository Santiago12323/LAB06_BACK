package edu.eci.cvds.AppTareas.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.eci.cvds.AppTareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("file")
public class FileTareaRepository implements TareaPersistence {

    
    private final String filePath = "/home/site/wwwroot/App_Data/tareas.json"; 
    private final Gson gson = new Gson();
    private final Type listType = new TypeToken<ArrayList<Tarea>>() {}.getType();

    public FileTareaRepository() {
        inicializarArchivo();  // Verifica si el archivo existe, de lo contrario lo crea
    }

    private void inicializarArchivo() {
        File archivo = new File(filePath);
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs(); 
                archivo.createNewFile(); 
                escribirEnArchivo(new ArrayList<>()); // Inicializa el archivo vacío con una lista vacía
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear el archivo: " + filePath, e);
            }
        }
    }

    @Override
    public Tarea save(Tarea tarea) {
        List<Tarea> tareas = findAll();

        
        Optional<Tarea> tareaExistente = tareas.stream()
                .filter(t -> t.getId().equals(tarea.getId()))
                .findFirst();

        if (tareaExistente.isPresent()) {
            
            int index = tareas.indexOf(tareaExistente.get());
            tareas.set(index, tarea);
        } else {
            
            tareas.add(tarea);
        }

        escribirEnArchivo(tareas); 
        return tarea;
    }

    @Override
    public Optional<Tarea> findById(String id) {
        return findAll().stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public List<Tarea> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String content = reader.lines().reduce("", (acc, line) -> acc + line);
            if (content.isEmpty()) {
                return new ArrayList<>();  
            }
            return gson.fromJson(content, listType);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer tareas desde archivo", e);
        }
    }

    @Override
    public void deleteById(String id) {
        List<Tarea> tareas = findAll();
        tareas.removeIf(t -> t.getId().equals(id));
        escribirEnArchivo(tareas);
    }

    private void escribirEnArchivo(List<Tarea> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = gson.toJson(tareas);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir tareas en el archivo", e);
        }
    }
}
