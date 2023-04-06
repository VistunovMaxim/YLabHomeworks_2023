package io.ylab.intensive.lesson05.messagefilter;

import java.sql.SQLException;

public interface MessageProcessor {

    String messageProcessing (String message) throws SQLException;
}
