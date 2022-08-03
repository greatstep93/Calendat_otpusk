package ru.greatstep.spring_sqlite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.greatstep.spring_sqlite.service.absctract.UserService;
import ru.greatstep.spring_sqlite.service.impl.UserServiceImp;

import java.io.IOException;
import java.text.ParseException;


class BeanInitMethodImpl {

    @Autowired
    UserServiceImp userServiceImp;
    public void runAfterObjectCreated() throws ParseException {
        userServiceImp.initializationTestUsers();
    }
}

@SpringBootApplication
public class SpringSqliteApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringSqliteApplication.class, args);
        openHomePage();
    }
    @Bean(initMethod = "runAfterObjectCreated")
    public BeanInitMethodImpl getFunnyBean(){
        return new BeanInitMethodImpl();
    }

    private static void openHomePage() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8000/admin");
    }

}
