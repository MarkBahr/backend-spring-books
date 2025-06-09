package com.secure.books.security.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class used to send the user any kind of message. Used with Response Body of signup requests.
 */
@Setter
@Getter
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
