package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value="SELECT * FROM post where description like %?1% or user_id=(select id from user where username like %?1%)",nativeQuery=true)
    ArrayList<Post> listaDePost(String buscar);
}
