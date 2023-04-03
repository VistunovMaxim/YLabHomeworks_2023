package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@ComponentScan
public class MessageProcessorImpl implements MessageProcessor {

    String selectSQL = "select obs_word from words where (obs_word = ?)";
    private PreparedStatement prSelect;

    @Autowired
    public MessageProcessorImpl(DataSource dataSource) {
        try {
            Connection connectionBd = dataSource.getConnection();
            PreparedStatement psSelect = connectionBd.prepareStatement(selectSQL);
            this.prSelect = psSelect;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String messageProcessing(String message) throws SQLException {
        String[] str = message.split("[,?.!;\n ]\\s?");
        ResultSet rs;
        for (String word : str) {
            prSelect.setString(1, word.toLowerCase().trim());
            rs = prSelect.executeQuery();
            if (rs.next()) {
                char[] censoredWord = word.toCharArray();
                for (int i = 1; i < censoredWord.length - 1; i++) {
                    censoredWord[i] = '*';
                }
                message = message.replaceFirst(word, new String(censoredWord));
            }
        }
        return message;
    }
}