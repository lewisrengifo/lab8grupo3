package sw2.lab6.teletok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw2.lab6.teletok.entity.Token;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByCode(String code);

    @Query(value = "select * from teletok.token where token.code=?1", nativeQuery = true)
    Token darToken(String cod);

}
