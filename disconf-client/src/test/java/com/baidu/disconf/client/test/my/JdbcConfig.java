package com.baidu.disconf.client.test.my;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Scope
@DisconfFile(filename = "jdbc-mysql.properties")
public class JdbcConfig {
    private String host;
    private String username;
    private String password;

    @DisconfFileItem(associateField="host", name="jdbc.db_0.url")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @DisconfFileItem(associateField = "username", name = "jdbc.db_0.username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DisconfFileItem(associateField="password", name = "jdbc.db_0.password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
