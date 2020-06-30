package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "SELECT * FROM token where user_id =?1",nativeQuery = true)
    Token buscarTokenPorUserid(int id);
}
