package carrental.local.carrental;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api")
public class BaseController {
    private final JdbcTemplate jdbcTemplate;

    public BaseController (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("test")
    public String test() {
        String sql = "CREATE TABLE IF NOT EXIsTS test_table ("
        +  "id INT AUTO_INCREMENT PRIMARY KEY,"+
        "name VARCHAR(255) NOT NULL" +
        ")";
        jdbcTemplate.execute(sql);
        return "tao thanh cond";
    }
}
