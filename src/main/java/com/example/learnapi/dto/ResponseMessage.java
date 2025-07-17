package com.example.learnapi.dto;

import com.example.learnapi.model.Model;
import com.example.learnapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseMessage<T> {

    private T data;
    private String message;

//    public ResponseMessage() {
//        this.message = "";
//        this.data = new Model();
//    }

}
