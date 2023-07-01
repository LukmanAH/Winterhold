package com.winterhold.utility;

import com.winterhold.dto.utility.ErrorDTO;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MapperHelper {
    private static <T> Object getFieldValue(Object object, String fieldName){
        try{
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            var value = field.get(object);
            return value;
        }catch (Exception exception){
        }
        return null;
    }

    public static Boolean getBooleanField(Object object, Integer index){
        try{
            return (Boolean) ((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Boolean getBooleanField(T object, String fieldName){
        try{
            return (Boolean) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Integer getIntegerField(Object object, Integer index){
        try{
            return (Integer)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Integer getIntegerField(T object, String fieldName){
        try{
            return (Integer) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Long getLongField(Object object, Integer index){
        try{
            return (Long)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Long getLongField(T object, String fieldName){
        try{
            return (Long) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Double getDoubleField(Object object, Integer index){
        try{
            return (Double)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Double getDoubleField(T object, String fieldName){
        try{
            return (Double) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> String getStringField(T object, String fieldName){
        try{
            return getFieldValue(object, fieldName).toString();
        }catch (Exception exception){
            return null;
        }
    }

    public static String getStringField(Object object, Integer index){
        try{
            return ((Object[])object)[index].toString();
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> LocalDate getLocalDateField(T object, String fieldName){
        try{
            var stringValue = getStringField(object, fieldName);
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var date = LocalDate.parse(stringValue, formatter);
            return date;
        }catch (Exception exception){
            return null;
        }
    }

    public static LocalDate getLocalDateField(Object object, Integer index){
        try{
            var stringValue = getStringField(object, index);
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var date = LocalDate.parse(stringValue, formatter);
            return date;
        }catch (Exception exception){
            return null;
        }
    }

    public static void setGridViewModel(List<Object> grid, Integer page, Integer totalPages, String breadCrumbs, Model model){
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", breadCrumbs);
    }

    public static void setDetailGridViewModel(Object header, List<Object> grid, Integer page, Integer totalPages, String breadCrumbs, Model model){
        setGridViewModel(grid, page, totalPages, breadCrumbs, model);
        model.addAttribute("header", header);
    }

    public static Integer getAge(LocalDate date){
        return date.until(LocalDate.now()).getYears();
    }

    public static String getAuthorStatus(LocalDate deceasedDate){
        return deceasedDate == null ? "Alive" : "Deceased";
    }

    public static ResponseEntity<Object> getResponse(Object data, Integer status){
        var response = new HashMap<String, Object>();
        response.put("data", data);
        response.put("error", null);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<Object> getErrors(List<ObjectError> errors, Integer status){
        var response = new HashMap<String, Object>();
        var dto = new ArrayList<ErrorDTO>();
        for(var error : errors){
            var fieldName = getStringField(error.getArguments()[0], "defaultMessage");
            fieldName = (fieldName.equals("")) ? "object" : fieldName;
            dto.add(new ErrorDTO(fieldName, error.getDefaultMessage()));
        }
        response.put("error", dto);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<Object> getError(Object error, Integer status){
        var response = new HashMap<String, Object>();
        response.put("error", error);
        return ResponseEntity.status(status).body(response);
    }
}
