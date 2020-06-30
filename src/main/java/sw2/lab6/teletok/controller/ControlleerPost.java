package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.Dtos.Coment;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.PostComment;
import sw2.lab6.teletok.entity.Token;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostRepository;
import sw2.lab6.teletok.repository.TokenRepository;

import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class ControlleerPost {
    @Autowired
    PostCommentRepository postCommentRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarComentario(@RequestBody Coment coment) {
        HashMap<String, Object> responseMap = new HashMap<>();
        PostComment postComment = new PostComment();

        Optional<Post> paux= postRepository.findById(coment.getPostId());
        Token auxtoken= tokenRepository.darToken(coment.getToken());
        if(auxtoken!=null && paux!=null ){
            if(coment.getPostId()>0 && coment.getMessage()!=null){

                postComment.setPost(paux.get());
                postComment.setMessage(coment.getMessage());

                postComment.setUser(auxtoken.getUser());
                postCommentRepository.save(postComment);
                responseMap.put("id",postComment.getId());
                return new ResponseEntity(responseMap, HttpStatus.CREATED);
            }
        }
        else if (paux==null){
            responseMap.put("error", "POST_NOT_FOUND");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
        else{
            responseMap.put("error", "TOKEN_INVALID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
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
