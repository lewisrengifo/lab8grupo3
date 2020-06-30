package sw2.lab6.teletok.Dtos;

import javax.persistence.Entity;
import javax.persistence.Id;


public class Coment {

    private int postId;
    private String token;
    private String message;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
