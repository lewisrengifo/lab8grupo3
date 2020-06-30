package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.Token;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.PostRepository;
import sw2.lab6.teletok.repository.TokenRepository;
import sw2.lab6.teletok.repository.UserRepository;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @GetMapping(value = {"", "/"})
    public String listPost(){
        return "post/list";
    }

    @GetMapping("/post/new")
    public String newPost(){
        return "post/new";
    }

    @PostMapping(value = "/post/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePost(@RequestParam(value = "token", required = true) String token, @RequestParam(value = "description", required = true) String description, @RequestParam(value = "media", required = true)File foto) {
        HashMap<String, Object> responseMap = new HashMap<>();
        Token token1 =tokenRepository.findByCode(token);

        if (token1!=null){
            if(foto!=null){
                User user = token1.getUser();
                Post post = new Post();
                post.setDescription(description);
                Date date = new Date();
                post.setCreationDate(date);
                post.setMediaUrl(foto.getName());
                post.setUser(user);
                postRepository.save(post);
                responseMap.put("postId", postRepository.save(post).getId());
                responseMap.put("status", "postCreated");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            }else {
                responseMap.put("error","EMPTY_FILE");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }

        }else {
            responseMap.put("error", "TOKEN_INVALID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/post/file/{media_url}")
    public String getFile() {
        return "";
    }

    @GetMapping("/post/{id}")
    public String viewPost() {
        return "post/view";
    }

    @PostMapping("/post/comment")
    public String postComment() {
        return "";
    }

    @PostMapping("/post/like")
    public String postLike() {
        return "";
    }
}
