package com.jobportal.jobservice.fulltext;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.SqlTypes;

import java.util.List;

public class FullTextSQLFunction extends StandardSQLFunction {
    private final int REQUIRED_ARGS = 2;
    private static final BasicTypeReference<Boolean> RETURN_TYPE = new BasicTypeReference<>("boolean", Boolean.class, SqlTypes.BOOLEAN);

    FullTextSQLFunction(final String functionName) {
        super(functionName, false, RETURN_TYPE);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> arguments, SqlAstTranslator<?> translator) {
        if (arguments.size() != REQUIRED_ARGS)
            throw new IllegalArgumentException(String.format("Function requires exactly %d arguments", REQUIRED_ARGS));

        sqlAppender.append("to_tsvector(");
        arguments.get(0).accept(translator);
        sqlAppender.append(") @@ to_tsquery(");
        arguments.get(1).accept(translator);
        sqlAppender.append(")");
    }
}
