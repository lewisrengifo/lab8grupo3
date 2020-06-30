package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.PostComment;
import sw2.lab6.teletok.entity.Token;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class ControlleerPost {
    @Autowired
    PostCommentRepository postCommentRepository;
    @Autowired
    TokenRepository tokenRepository;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarComentario(@RequestParam("postid")int postId , @RequestParam("token") String token,
                                            @RequestParam("mesaage")String message,@RequestParam(value = "fetchId", required = false) boolean fetchId) {
        HashMap<String, Object> responseMap = new HashMap<>();
        PostComment postComment = new PostComment();
        Token auxtoken= tokenRepository.darToken(token);
        if(auxtoken!=null){
            if(postId>0 && message!=null){
                postComment.getPost().setId(postId);
                postComment.setMessage(message);
                postComment.getUser().setId(auxtoken.getUser().getId());
                postCommentRepository.save(postComment);
                responseMap.put("id",postComment.getId());
                return new ResponseEntity(responseMap, HttpStatus.CREATED);
            }
        }
        else{
            responseMap.put("error", "TOKEN_INVALID");
            return new ResponseEntity(responseMap, HttpStatus.CREATED);
        }
        responseMap.put("error", "POST_NOT_FOUND");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un Comentario Completo");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }*/
}
