package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.PostRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class ListaWebService {
    @Autowired
    PostRepository postRepository;
    @GetMapping(value = "/post/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProductos(@RequestParam("query") String query) {

        if (query.isEmpty()){
            return new ResponseEntity(postRepository.findAll(), HttpStatus.OK);

        }else {
            ArrayList<Post> posts = postRepository.listaDePost(query);
            if (posts.toArray().length<1){
                return new ResponseEntity(posts.toArray().length,HttpStatus.OK);
            }
                return new ResponseEntity(posts, HttpStatus.OK);

        }
    }

    @GetMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPost(@PathVariable("id") int idpost, @RequestParam("token") String token, HttpSession session){
        HashMap<String, Object> responseMap = new HashMap<>();
        User user = (User) session.getAttribute("user");
        

        try{
            Optional<Post> postId = postRepository.findById(idpost);
            if(postId.isPresent()){
                responseMap.put("post",postId.get());
                return new ResponseEntity(responseMap,HttpStatus.OK);
            }else{
                responseMap.put("error","POST_NOT_FOUND");
                return new ResponseEntity(responseMap,HttpStatus.BAD_REQUEST);
            }

        }catch (NumberFormatException er){
            return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
        }

    }


}
